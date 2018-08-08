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
import java.awt.Color;
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
import model.MyResponsePoint;
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
						if(img != null){
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
						}

						int groupIndex = 0;
						int rowHeight = 16;
						int rowSpace = 5;

						for (GroupModel	group : model.getGroups()) {
							if(group.getPoints().size() != 0){
								
								//vykresleni bodu z vrstvy
								ArrayList<MyResponsePoint> pointsInLayer = group.getPointFromLayer(model.getActualSnapshot());
								
								g2.setColor(group.getLayerColor());

								g2.translate(0,this.getHeight());
								g2.scale(1,-1);
								drawPoints(g2, pointsInLayer);
								g2.setTransform(at);
								drawCoords(g2, pointsInLayer);

								//vytvoreni obalky, musi obsahovat minimalne 3 body
								if(pointsInLayer.size() >=3){
									if(! (group.getName().equals(Configuration.UNASSIGN_GROUP) || group.getName().equals(Configuration.IGNORE_GROUP))){

										g2.translate(0,this.getHeight());
										g2.scale(1,-1);
										ArrayList<MyResponsePoint> hullPoint = new QuickHull().quickHull(pointsInLayer);
										drawPoints(g2, hullPoint);
										drawConvexCover(g2, hullPoint);
										group.setArea(model.getActualSnapshot(), hullPoint);
										double maxX = hullPoint.get(0).getCenterX();
										double maxY = hullPoint.get(0).getCenterY();
										for (MyResponsePoint myPoint : hullPoint) {
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
								ArrayList<MyResponsePoint> pointsInGroup = group.getCopyOfPoints();
								g2.setColor(group.getGroupColor());
																
//								//vytvoreni obalky, musi obsahovat minimalne 3 body
								if(group.getPoints().size() >=3){
									if(! (group.getName().equals(Configuration.UNASSIGN_GROUP) || group.getName().equals(Configuration.IGNORE_GROUP))){

										g2.translate(0,this.getHeight());
										g2.scale(1,-1);
										ArrayList<MyResponsePoint> hullPoint = new QuickHull().quickHull(pointsInGroup);
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
		if(Configuration.drawRulers){
			drawRulers(g2);
		}
	}

	private void drawRulers(Graphics2D g2){
		int[] mm_per_line = {5,10,50,100};
		int[] line_lengths = {2,4,8,16};
		int[] font_sizes = {0,0,8,14};
		g2.setColor(Color.BLUE);
		final int baseline = 12;
		for(int a=0; a<mm_per_line.length; a++){
			double sp = ImagePanelModel.getXSpacing();
			double distance = mm_per_line[a]/sp*this.ratio;
			int count = (int)(this.img_width/distance);
			outer1:
			for(int b=0; b<=count; b++){
				int x = (int)(distance*b)+this.x_offset;
				g2.setFont(new Font("TimesRoman", Font.PLAIN, font_sizes[a]));
				g2.drawLine(x,baseline+this.y_offset,x,baseline+line_lengths[a]+this.y_offset);
				for(int c=mm_per_line.length-1; c>a; c--){
					if(((mm_per_line[a]*b)%mm_per_line[c])==0){
						continue outer1;
					}
				}
				g2.drawString((b*mm_per_line[a])+"",x,this.y_offset+baseline);
			}
			sp = ImagePanelModel.getYSpacing();
			distance = mm_per_line[a]/sp*this.ratio;
			count = (int)(this.img_height/distance);
			outer2:
			for(int b=0; b<=count; b++){
				int y = this.img_height-(int)(distance*b)+this.y_offset;
				g2.setFont(new Font("TimesRoman", Font.PLAIN, font_sizes[a]));
				g2.drawLine(this.x_offset+baseline,y,line_lengths[a]+this.x_offset+baseline,y);
				for(int c=mm_per_line.length-1; c>a; c--){
					if(((mm_per_line[a]*b)%mm_per_line[c])==0){
						continue outer2;
					}
				}
				g2.drawString((b*mm_per_line[a])+"",this.x_offset,y);
			}
		}
	}
	private void drawConvexCover(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		for (int i = 0; i < points.size(); i++) {
			MyResponsePoint p = points.get(i);
			MyResponsePoint n = points.get((i+1)%points.size());
			g2.setStroke(new BasicStroke(3));
			g2.drawLine((int) (p.getCenterX() * ratio + this.x_offset), 
					(int) (p.getCenterY() * ratio + this.y_offset), 
					(int) (n.getCenterX() * ratio + this.x_offset), 
					(int) (n.getCenterY() * ratio + this.y_offset));
		}
	}

	private void drawPoints(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		for (MyResponsePoint myPoint : points) {
			g2.fillOval((int) ((myPoint.getX()-myPoint.getWidth()/2)*ratio + this.x_offset), 
					(int) ((myPoint.getY()-myPoint.getHeight()/2)*ratio + this.y_offset), 
					(int) (myPoint.getWidth()*ratio), 
					(int) (myPoint.getHeight()*ratio));
		}
	}
	private void drawCoords(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		if(!Configuration.showCoords){
			return;
		}
		g2.setColor(Color.BLACK);
		for (MyResponsePoint myPoint : points) {
			g2.drawString("["+String.format("%.2f",myPoint.getX()*ImagePanelModel.getXSpacing())+";"+String.format("%.2f",myPoint.getY()*ImagePanelModel.getYSpacing())+"]",
				(int) ((myPoint.getX()+myPoint.getWidth()/2)*ratio + this.x_offset), 
				this.getHeight()-(int)((myPoint.getY()-myPoint.getHeight()/2)*ratio + this.y_offset)); 
		}
	}

	public void saveImages() throws NullPointerException {
		BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		File folderPath = UtilityClass.chooseSaveLocation();
		ImagePaneController ipc = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);


		try {
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
			throw npe;
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
