/*
 * Decompiled with CFR 0_123.
 */
package view.centerPane;

import controller.Configuration;
import controller.QuickHull;
import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import enums.DicomTags;
import ij.plugin.DICOM;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.ImagePanelModel;
import model.MyDicom;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanel
extends JPanel {
    private int x_offset;
    private int y_offset;
    private int img_width;
    private int img_height;
    private int basic_size;
    private double ratio;

    public ImagePanel(ImagePanelModel model) {
        ImagePaneController controller = new ImagePaneController(this, model);
        MainWindow.addController(controller);
        this.addMouseWheelListener(controller);
        this.addMouseListener(controller);
    }

    public ImagePanel() {
        this(new ImagePanelModel());
    }

    @Override
    public void paint(Graphics g) {
        ImagePanelModel model;
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        ImagePaneController ctrl = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
        if (ctrl != null && (model = ctrl.getModel()) != null && model.getActualSnapshot() != -1) {
            try {
                DICOM dcm = model.getMriDicom().get(model.getActualSnapshot());
                BufferedImage img = dcm.getBufferedImage();
                String pixSpaceRet = UtilityClass.getTagValue(dcm, DicomTags.PIXEL_SPACING);
                Configuration.pixelSpace = UtilityClass.stringToDouble(pixSpaceRet.substring(0, pixSpaceRet.indexOf("\\")));
                Configuration.sliceThickness = UtilityClass.stringToDouble(UtilityClass.getTagValue(dcm, DicomTags.SLICE_THICKNESS));
                RescaleOp resOp = new RescaleOp(model.convertBrightness(), model.convertContrast(), null);
                resOp.filter(img, img);
                double widthRatio = (double)this.getWidth() / (double)img.getWidth(null);
                double heightRatio = (double)this.getHeight() / (double)img.getHeight(null);
                this.ratio = Math.min(widthRatio, heightRatio);
                this.img_width = (int)((double)img.getWidth(null) * this.ratio);
                this.img_height = (int)((double)img.getHeight(null) * this.ratio);
                this.x_offset = (this.getWidth() - this.img_width) / 2;
                this.y_offset = (this.getHeight() - this.img_height) / 2;
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(img, this.x_offset, this.y_offset, this.img_width, this.img_height, null);
                int groupIndex = 0;
                int rowHeight = 16;
                int rowSpace = 5;
                for (GroupModel group : model.getGroups()) {
                    if (group.getPoints().size() == 0) continue;
                    ArrayList<MyPoint> pointsInLayer = group.getPointFromLayer(model.getActualSnapshot());
                    g2.setColor(group.getLayerColor());
                    this.drawPoints(g2, pointsInLayer);
                    if (pointsInLayer.size() >= 3 && !group.getName().equals("nep\u0159i\u0159azen\u00e9 body") && !group.getName().equals("ignorovan\u00e9 body")) {
                        ArrayList<MyPoint> hullPoint = new QuickHull().quickHull(pointsInLayer);
                        this.drawPoints(g2, hullPoint);
                        this.drawConvexCover(g2, hullPoint);
                        group.setArea(model.getActualSnapshot(), hullPoint);
                        this.drawAreaLabel(g2, hullPoint, group.getArea(model.getActualSnapshot()));
                    }
                    ArrayList<MyPoint> pointsInGroup = group.getCopyOfPoints();
                    g2.setColor(group.getGroupColor());
                    if (group.getPoints().size() < 3 || group.getName().equals("nep\u0159i\u0159azen\u00e9 body") || group.getName().equals("ignorovan\u00e9 body")) continue;
                    ArrayList<MyPoint> hullPoint = new QuickHull().quickHull(pointsInGroup);
                    this.drawConvexCover(g2, hullPoint);
                    g2.setFont(new Font("TimesRoman", 0, 15));
                    g2.drawString(String.format("%s: %.2f mm\u00b2", group.getName(), group.computeArea(hullPoint)), (int)((double)this.x_offset + (double)rowSpace * this.ratio), (int)((double)this.y_offset + (double)((rowSpace + rowHeight) * (groupIndex + 1)) * this.ratio));
                    ++groupIndex;
                }
            }
            catch (IndexOutOfBoundsException dcm) {
		dcm.printStackTrace();
                // empty catch block
            }
        }
    }

    private void drawAreaLabel(Graphics2D g2, ArrayList<MyPoint> points, double area) {
        double maxX = points.get(0).getCenterX();
        double maxY = points.get(0).getCenterY();
        for (MyPoint myPoint : points) {
            if (maxX < myPoint.getCenterX()) {
                maxX = myPoint.getCenterX();
            }
            if (maxY >= myPoint.getCenterY()) continue;
            maxY = myPoint.getCenterY();
        }
        g2.setFont(new Font("TimesRoman", 0, 15));
        g2.drawString(String.format("%.2f mm\u00b2", area), (int)((maxX + 10.0) * this.ratio + (double)this.x_offset), (int)((maxY + 10.0) * this.ratio + (double)this.y_offset));
    }

    private void drawConvexCover(Graphics2D g2, ArrayList<MyPoint> Points) {
        int i = 0;
        while (i < Points.size()) {
            g2.setStroke(new BasicStroke(3.0f));
            g2.drawLine((int)(Points.get(i).getCenterX() * this.ratio + (double)this.x_offset), (int)(Points.get(i).getCenterY() * this.ratio + (double)this.y_offset), (int)(Points.get((i + 1) % Points.size()).getCenterX() * this.ratio + (double)this.x_offset), (int)(Points.get((i + 1) % Points.size()).getCenterY() * this.ratio + (double)this.y_offset));
            ++i;
        }
    }

    private void drawPoints(Graphics2D g2, ArrayList<MyPoint> points) {
        for (MyPoint myPoint : points) {
            g2.fillOval((int)(myPoint.getX() * this.ratio) + this.x_offset, (int)(myPoint.getY() * this.ratio + (double)this.y_offset), (int)(myPoint.getWidth() * this.ratio), (int)(myPoint.getHeight() * this.ratio));
        }
    }

    public void saveImages() {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), 1);
        File folderPath = UtilityClass.chooseSaveLocation();
        ImagePaneController ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
        try {
            int i = 0;
            while (i < ipc.getModel().getMriDicom().size()) {
                ipc.getModel().setActualSnapshot(i);
                String path = String.valueOf(folderPath.getPath()) + File.separator + "img_" + ipc.getModel().getActualSnapshot() + ".png";
                this.paint(img.getGraphics());
                ImageIO.write((RenderedImage)img, "png", new File(path));
                ++i;
            }
            System.out.println("panel saved as image");
        }
        catch (NullPointerException npe) {
            throw new NullPointerException();
        }
        catch (Exception e) {
            System.out.println("panel not saved" + e.getMessage());
        }
    }

    public void saveImg() throws NullPointerException, FileNotFoundException {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), 1);
        this.paint(img.getGraphics());
        try {
            ImagePaneController ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
            File folderPath = UtilityClass.chooseSaveLocation();
            String path = String.valueOf(folderPath.getPath()) + File.separator + "img_" + ipc.getModel().getActualSnapshot() + ".png";
            ImageIO.write((RenderedImage)img, "png", new File(path));
            System.out.println("panel saved as image");
        }
        catch (NullPointerException npe) {
            throw new NullPointerException();
        }
        catch (Exception e) {
            System.out.println("panel not saved" + e.getMessage());
        }
    }

    public String getPaneInfo() {
        return "x_offset: " + this.x_offset + ", y_offset: " + this.y_offset + ", width: " + this.img_width + ", height: " + this.img_height;
    }

    public int getX_offset() {
        return this.x_offset;
    }

    public int getY_offset() {
        return this.y_offset;
    }

    public int getImg_height() {
        return this.img_height;
    }

    public int getImg_width() {
        return this.img_width;
    }

    public double getRatio() {
        return this.ratio;
    }
}

