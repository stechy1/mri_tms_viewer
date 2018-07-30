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
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.ImagePanelModel;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanel extends JPanel{


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
		AffineTransform at = g2.getTransform();

		ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);

		if(ctrl != null){
			ImagePanelModel model = (ImagePanelModel) ctrl.getModel();

			if(model != null){
				if(model.getActualSnapshot() != -1){

					try {
						BufferedImage img = model.getActualImage();
						if(model.isDicom()){
							DICOM dcm = model.getMriDicom().get(model.getActualSnapshot());
							String pixSpaceRet = getTagValue(dcm, DicomTags.PIXEL_SPACING); 
							Configuration.pixelSpace = stringToDouble(pixSpaceRet.substring(0, pixSpaceRet.indexOf("\\")));
							Configuration.sliceThickness = stringToDouble(getTagValue(dcm, DicomTags.SLICE_THICKNESS));
						}else{
							img = new BufferedImage(img.getColorModel(),img.copyData(null),img.getColorModel().isAlphaPremultiplied(),null);
						}

						//Prvni parametr svetlost... 0.0 nic neni, 1.0 puvodni, 2.0 cerno
						//druhy parametr kontrast... 
						RescaleOp resOp = new RescaleOp(model.convertBrightness(), model.convertContrast(), null);

						resOp.filter(img, img);


						double widthRatio = (double) this.getWidth() / img.getWidth(null);
						double heightRatio = (double) this.getHeight() / img.getHeight(null);

						this.ratio = Math.min(widthRatio, heightRatio);

						this.img_width = (int) (img.getWidth(null) * ratio);
						this.img_height = (int)(img.getHeight(null) * ratio);

						this.x_offset = (int)(this.getWidth()-img_width)/2;
						this.y_offset = (int)(this.getHeight()-img_height)/2;


						g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
						//g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
						//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


						g2.translate(0,this.getHeight());
						g2.scale(1,-1);
						g2.drawImage(img, this.x_offset, this.y_offset, this.img_width, this.img_height , null);
						g2.setTransform(at);

						int groupIndex = 0;
						int rowHeight = 16;
						int rowSpace = 5;

						for (GroupModel	group : model.getGroups()) {
							if(group.getPoints().size() != 0){
								
								
								//vykresleni bodu z vrstvy
								ArrayList<MyPoint> pointsInLayer = group.getPointFromLayer(model.getActualSnapshot());
								
								g2.setColor(group.getLayerColor());

								g2.translate(0,this.getHeight());
								g2.scale(1,-1);
								drawPoints(g2, pointsInLayer);
								g2.setTransform(at);

								//vytvoreni obalky, musi obsahovat minimalne 3 body
								if(pointsInLayer.size() >=3){
									if(! (group.getName().equals(Configuration.UNASSIGN_GROUP) || group.getName().equals(Configuration.IGNORE_GROUP))){

										g2.translate(0,this.getHeight());
										g2.scale(1,-1);
										ArrayList<MyPoint> hullPoint = new QuickHull().quickHull(pointsInLayer);
										drawPoints(g2, hullPoint);
										drawConvexCover(g2, hullPoint);
										group.setArea(model.getActualSnapshot(), hullPoint);
										double maxX = hullPoint.get(0).getCenterX();
										double maxY = hullPoint.get(0).getCenterY();
										for (MyPoint myPoint : hullPoint) {
											if(maxX < myPoint.getCenterX())
												maxX = myPoint.getCenterX();
											if(maxY < myPoint.getCenterY())
												maxY = myPoint.getCenterY();
										}
										g2.setTransform(at);
										g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
										g2.drawString(String.format("%.2f mm\u00b2", group.getArea(model.getActualSnapshot())), 
												(int) ((maxX + 10) * ratio + this.x_offset), 
												(int) (this.getHeight() - (maxY + 10) * ratio - this.y_offset));
									}
								}
								
								
//								Vykresleni vsech bodu ze skupiny do jedne roviny
								ArrayList<MyPoint> pointsInGroup = group.getCopyOfPoints();
								g2.setColor(group.getGroupColor());
																
//								//vytvoreni obalky, musi obsahovat minimalne 3 body
								if(group.getPoints().size() >=3){
									if(! (group.getName().equals(Configuration.UNASSIGN_GROUP) || group.getName().equals(Configuration.IGNORE_GROUP))){

										g2.translate(0,this.getHeight());
										g2.scale(1,-1);
										ArrayList<MyPoint> hullPoint = new QuickHull().quickHull(pointsInGroup);
										drawConvexCover(g2, hullPoint);
									
										g2.setTransform(at);
										g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
										g2.drawString(String.format("%s: %.2f mm\u00B2", group.getName(), group.computeArea(hullPoint)), 
												(int) (x_offset + rowSpace * ratio), 
												this.getHeight() - (int)(y_offset + ((rowSpace + rowHeight)  * (groupIndex+1)) * ratio));
										groupIndex++;
									}
								}
							}
						}
					} catch (IndexOutOfBoundsException e) { e.printStackTrace();}
					g2.setTransform(at);
				}
			}
		}
	}

	private void drawConvexCover(Graphics2D g2, ArrayList<MyPoint> Points) {
		for (int i = 0; i < Points.size(); i++) {
			g2.setStroke(new BasicStroke(3));
			g2.drawLine((int) (Points.get(i).getCenterX() * ratio + this.x_offset), 
					(int) (Points.get(i).getCenterY() * ratio + this.y_offset), 
					(int) (Points.get((i+1)%Points.size()).getCenterX() * ratio + x_offset), 
					(int) (Points.get((i+1)%Points.size()).getCenterY() * ratio + y_offset));
		}
	}

	private void drawPoints(Graphics2D g2, ArrayList<MyPoint> points) {
		for (MyPoint myPoint : points) {
			g2.fillOval((int) (myPoint.getX()*ratio) + this.x_offset, 
					(int) (myPoint.getY()*ratio + this.y_offset), 
					(int) (myPoint.getWidth()*ratio), 
					(int) (myPoint.getHeight()*ratio));
		}
	}

	public void saveImages() throws NullPointerException {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		File folderPath = UtilityClass.chooseSaveLocation();
		ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);


		try {
			//TODO dodelat ukladani
			for(int i = 0 ; i < ipc.getModel().getMriDicom().size() ; i++){
				ipc.getModel().setActualSnapshot(i);
				String path = folderPath.getPath() + File.separator + "img_" + ipc.getModel().getActualSnapshot() + ".png";
				paint(img.getGraphics());
				ImageIO.write(img, "png", new File(path));
			}

			System.out.println("panel saved as image");
		} catch(NullPointerException npe){
			throw npe;
		} catch (Exception e) {
			System.out.println("panel not saved" + e.getMessage());
		}
	}

	public void saveImg() throws NullPointerException, FileNotFoundException {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		paint(img.getGraphics());
		try {
			//TODO dodelat ukladani
			ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);

			File folderPath = UtilityClass.chooseSaveLocation();
			String path = folderPath.getPath() + File.separator + "img_" + ipc.getModel().getActualSnapshot() + ".png";

			ImageIO.write(img, "png", new File(path));
			System.out.println("panel saved as image");
		} catch(NullPointerException npe){
			throw new NullPointerException();
		} catch (Exception e) {
			System.out.println("panel not saved" + e.getMessage());
		}
	}

	public String getPaneInfo(){
		return "x_offset: " + this.x_offset + ", y_offset: " + this.y_offset + ", width: " + this.img_width + ", height: " + this.img_height;
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
