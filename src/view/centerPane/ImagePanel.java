package view.centerPane;

import static controller.UtilityClass.getTagValue;
import static controller.UtilityClass.stringToDouble;

import controller.Configuration;
import controller.QuickHull;
import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import enums.DicomTags;
import ij.plugin.DICOM;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.ImagePanelModel;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanel extends JPanel {


    private int x_offset, y_offset, img_width, img_height, basic_size;
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
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        ImagePaneController ctrl = (ImagePaneController) MainWindow
            .getController(Controllers.IMAGE_PANE_CTRL);

        if (ctrl != null) {
            ImagePanelModel model = (ImagePanelModel) ctrl.getModel();

            if (model != null) {
                if (model.getActualSnapshot() != -1) {

                    try {
                        DICOM dcm = model.getMriDicom().get(model.getActualSnapshot());
                        BufferedImage img = dcm.getBufferedImage();

                        //Prvni parametr svetlost... 0.0 nic neni, 1.0 puvodni, 2.0 cerno
                        //druhy parametr kontrast...
                        RescaleOp resOp = new RescaleOp(model.convertBrightness(),
                            model.convertContrast(), null);

                        resOp.filter(img, img);

                        double widthRatio = (double) this.getWidth() / img.getWidth(null);
                        double heightRatio = (double) this.getHeight() / img.getHeight(null);

                        this.ratio = Math.min(widthRatio, heightRatio);

                        this.img_width = (int) (img.getWidth(null) * ratio);
                        this.img_height = (int) (img.getHeight(null) * ratio);

                        this.x_offset = (int) (this.getWidth() - img_width) / 2;
                        this.y_offset = (int) (this.getHeight() - img_height) / 2;

                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        //g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.drawImage(img, this.x_offset, this.y_offset, this.img_width,
                            this.img_height, null);

                        for (GroupModel group : model.getGroups()) {
                            if (group.getPoints().size() != 0) {

                                ArrayList<MyPoint> pointsToDraw = group
                                    .getPointFromLayer(model.getActualSnapshot());

                                g2.setColor(group.getColor());

                                for (MyPoint myPoint : pointsToDraw) {

                                    g2.fillOval((int) (myPoint.getX() * ratio) + this.x_offset,
                                        (int) (myPoint.getY() * ratio + this.y_offset),
                                        (int) (myPoint.getWidth() * ratio),
                                        (int) (myPoint.getHeight() * ratio));
                                }

                                //vytvoreni obalky, musi obsahovat minimalne 3 body
                                if (pointsToDraw.size() >= 3) {
                                    if (!(group.getName().equals(Configuration.UNASSIGN_GROUP)
                                        || group.getName().equals(Configuration.IGNORE_GROUP))) {

                                        ArrayList<MyPoint> hull = new QuickHull()
                                            .quickHull(pointsToDraw);
                                        for (int i = 0; i < hull.size(); i++) {
                                            g2.setStroke(new BasicStroke(3));
                                            g2.drawLine((int) (hull.get(i).getCenterX() * ratio
                                                    + this.x_offset),
                                                (int) (hull.get(i).getCenterY() * ratio
                                                    + this.y_offset),
                                                (int) (hull.get((i + 1) % hull.size()).getCenterX()
                                                    * ratio + x_offset),
                                                (int) (hull.get((i + 1) % hull.size()).getCenterY()
                                                    * ratio + y_offset));
                                        }

                                        double area = 0.0;

                                        String pixelSpace = getTagValue(dcm,
                                            DicomTags.PIXEL_SPACING);
                                        pixelSpace = pixelSpace
                                            .substring(0, pixelSpace.indexOf("\\"));

                                        double scale = stringToDouble(pixelSpace);

                                        for (int i = 0; i < hull.size(); i++) {
                                            MyPoint fromPoint = hull.get(i % hull.size());
                                            MyPoint toPoint = hull.get((i + 1) % hull.size());
                                            MyPoint overPoint = hull.get((i + 2) % hull.size());

                                            //TODO roznasobit pomerem pixel na mm

                                            double i1_x = toPoint.getCenterX() * scale;
                                            double i2_y = overPoint.getCenterY() * scale;
                                            double i0_y = fromPoint.getCenterY() * scale;

                                            area += (i1_x * (i2_y - i0_y)) / 2;
                                        }

                                        area = Math.abs(area);

                                        double maxX = hull.get(0).getCenterX();
                                        double maxY = hull.get(0).getCenterY();
                                        for (MyPoint myPoint : hull) {
                                            if (maxX < myPoint.getCenterX()) {
                                                maxX = myPoint.getCenterX();
                                            }
                                            if (maxY < myPoint.getCenterY()) {
                                                maxY = myPoint.getCenterY();
                                            }
                                        }

                                        g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                                        g2.drawString(String.format("%.2f mm\u00b2", area),
                                            (int) ((maxX + 10) * ratio + this.x_offset),
                                            (int) ((maxY + 10) * ratio + this.y_offset));
                                    }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        // TODO: handle exception
                    }
                }
            }
        }
    }

    public void saveImages() {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        File folderPath = UtilityClass.chooseSaveLocation();
        ImagePaneController ipc = (ImagePaneController) MainWindow
            .getController(Controllers.IMAGE_PANE_CTRL);

        try {
            //TODO dodelat ukladani
            for (int i = 0; i < ipc.getModel().getMriDicom().size(); i++) {
                ipc.getModel().setActualSnapshot(i);
                //String path = folderPath.getPath() + "//" + "img_" + ipc.getModel().getActualSnapshot() + ".png";
                String path = new File(folderPath,
                    "img_" + ipc.getModel().getActualSnapshot() + ".png").toString();
                paint(img.getGraphics());
                ImageIO.write(img, "png", new File(path));
            }

            System.out.println("panel saved as image");
        } catch (NullPointerException npe) {
            throw new NullPointerException();
        } catch (Exception e) {
            System.out.println("panel not saved" + e.getMessage());
        }
    }

    public void saveImg() throws NullPointerException, FileNotFoundException {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        paint(img.getGraphics());
        try {
            //TODO dodelat ukladani
            ImagePaneController ipc = (ImagePaneController) MainWindow
                .getController(Controllers.IMAGE_PANE_CTRL);

            File folderPath = UtilityClass.chooseSaveLocation();
            //String path = folderPath.getPath() + "//" + "img_" + ipc.getModel().getActualSnapshot() + ".png";
            String path = new File(folderPath, "img_" + ipc.getModel().getActualSnapshot() + ".png")
                .toString();

            ImageIO.write(img, "png", new File(path));
            System.out.println("panel saved as image");
        } catch (NullPointerException npe) {
            throw new NullPointerException();
        } catch (Exception e) {
            System.out.println("panel not saved" + e.getMessage());
        }
    }

    public String getPaneInfo() {
        return "x_offset: " + this.x_offset + ", y_offset: " + this.y_offset + ", width: "
            + this.img_width + ", height: " + this.img_height;
    }

    public int getX_offset() {
        return this.x_offset;
    }

    public int getY_offset() {
        return this.y_offset;
    }

    public int getImg_height() {
        return img_height;
    }

    public int getImg_width() {
        return img_width;
    }

    public double getRatio() {
        return this.ratio;
    }
}
