package view.centerPane;
import static controller.UtilityClass.getTagValue;
import static controller.UtilityClass.stringToDouble;
import static model.ImagePanelModel.*;

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
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.ImagePanelModel;
import model.MyResponsePoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanel extends JPanel{


	private int x_offset, y_offset, img_width, img_height, basic_size;
	private int x_cursor, y_cursor;
	private double ratio;
	private Image img;
	private boolean hard_repaint = true;
	private List<MyResponsePoint> visible_points = new ArrayList<MyResponsePoint>();

	public ImagePanel(ImagePanelModel model) {


		ImagePaneController controller = new ImagePaneController(this, model);
		MainWindow.addController(controller);

		this.addMouseWheelListener(controller);
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
	}

	public ImagePanel() {
		this(new ImagePanelModel());
	}

	public List<MyResponsePoint> getVisiblePoints(){
		return visible_points;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		if(hard_repaint){
			this.img = createImage(this.getWidth(),this.getHeight());
			Graphics g_img = img.getGraphics();
			paintBuffer((Graphics2D)g_img);
		}
		if(img!=null){
			g.drawImage(img,0,0,null);
		}
		if(Configuration.showCoords){
			drawPosition(g2d,x_cursor,y_cursor);
		}
		if(Configuration.explode){
			drawFieldOfAction(g2d,x_cursor,y_cursor);
		}
	}
	public void setCursorPosition(int x,int y){
		this.x_cursor = x;
		this.y_cursor = y;
		if(Configuration.showCoords || Configuration.explode){
			hard_repaint = false;
			repaint();
			hard_repaint = true;
		}
	}
	public void drawFieldOfAction(Graphics2D g2,int x,int y){
		g2.setColor(Color.BLUE);
		int d = (int)(2*Configuration.MIN_ALLOWED_DISTANCE*ratio);
		int r = d>>1;
		g2.drawOval(x-r,y-r,d,d);
	}
	public void drawPosition(Graphics2D g2,int x,int y){
		g2.drawString("[ "+ImagePanelModel.getXAxis()+": "+String.format("%.2f",(x-this.x_offset)*ImagePanelModel.getXSpacing()/ratio)
				+"mm; "+ImagePanelModel.getYAxis()+": "+String.format("%.2f",
					(img_height-y+this.y_offset)*ImagePanelModel.getYSpacing()/ratio)+"mm]",x,y); 
	}
	public void paintBuffer(Graphics2D g2) {
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
							if(Configuration.threshold){
								threshold(img);
							}else{
								//Prvni parametr svetlost... 0.0 nic neni, 1.0 puvodni, 2.0 cerno
								//druhy parametr kontrast... 
								RescaleOp resOp = new RescaleOp(model.convertBrightness(), model.convertContrast(), null);
								resOp.filter(img, img);
							}
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

						visible_points.clear();
						for (GroupModel	group : model.getGroups()) {
							if(group.getPoints().size() != 0){
								
								//vykresleni bodu z vrstvy
								ArrayList<MyResponsePoint> pointsInLayer = group.getPointFromLayer(model.getActualSnapshot());
								visible_points.addAll(pointsInLayer);
								
								g2.setColor(group.getLayerColor());

								g2.translate(0,this.getHeight());
								g2.scale(1,-1);
								drawPoints(g2, pointsInLayer);
								drawConnections(g2, pointsInLayer);
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

	public void threshold(BufferedImage img){
		byte[] data = UtilityClass.getRaster(img);
		for(int a=0; a<data.length; a++){
			if((((int)data[a])&0xff)>Configuration.WHITE_MRI_PIXEL_THRESHOLD){
				data[a]=-1;
			}else{
				data[a]=0;
			}
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
		g2.drawString(ImagePanelModel.getXAxis()+"",this.x_offset+this.img_width-baseline-5,this.y_offset+3*baseline);
		g2.drawString(ImagePanelModel.getYAxis()+"",this.x_offset+3*baseline,this.y_offset+this.img_height-5);
	}
	private void drawConvexCover(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		for (int i = 0; i < points.size(); i++) {
			MyResponsePoint p = points.get(i);
			MyResponsePoint n = points.get((i+1)%points.size());
			//g2.setStroke(new BasicStroke(3));
			g2.drawLine((int) (p.getCenterX() * ratio + this.x_offset), 
					(int) (p.getCenterY() * ratio + this.y_offset), 
					(int) (n.getCenterX() * ratio + this.x_offset), 
					(int) (n.getCenterY() * ratio + this.y_offset));
		}
	}

	private void drawConnections(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		g2.setColor(Color.GREEN);
		for (MyResponsePoint myPoint : points) {
			Map<String,Double> mp = myPoint.getResponse().getData();
			double px,py,pz;
			if(mp.containsKey(Configuration.X_STRING)){
				px = mp.get(Configuration.X_STRING)/ImagePanelModel.getXSpacing();
			}else{
				continue;
			}
			if(mp.containsKey(Configuration.Y_STRING)){
				py = mp.get(Configuration.Y_STRING)/ImagePanelModel.getYSpacing();
			}else{
				continue;
			}
			if(mp.containsKey(Configuration.Z_STRING)){
				pz = mp.get(Configuration.Z_STRING)/ImagePanelModel.getZSpacing();
			}else{
				continue;
			}
			double cx = myPoint.getRealX();
			double cy = myPoint.getRealY();
			double cz = myPoint.getRealZ();
			switch(ImagePanelModel.getType()){
				case DICOM + AXIS_Z:
					g2.drawLine((int)(px*ratio+x_offset),(int)(py*ratio+y_offset),
						(int)(cx*ratio+x_offset),(int)(cy*ratio+y_offset));
					break;
				case DICOM + AXIS_X:
					g2.drawLine((int)(py*ratio+x_offset),(int)(pz*ratio+y_offset),
						(int)(cy*ratio+x_offset),(int)(cz*ratio+y_offset));
					break;
				case DICOM + AXIS_Y:
					g2.drawLine((int)(px*ratio+x_offset),(int)(pz*ratio+y_offset),
						(int)(cx*ratio+x_offset),(int)(cz*ratio+y_offset));
					break;
				case TMS   + AXIS_Z:
					g2.drawLine((int)(px*ratio+x_offset),(int)(py*ratio+y_offset),
						(int)(cx*ratio+x_offset),(int)(cy*ratio+y_offset));
					break;
				case TMS   + AXIS_X:
					g2.drawLine((int)(py*ratio+x_offset),(int)(pz*ratio+y_offset),
						(int)(cy*ratio+x_offset),(int)(cz*ratio+y_offset));
					break;
				case TMS   + AXIS_Y:
					g2.drawLine((int)(px*ratio+x_offset),(int)(pz*ratio+y_offset),
						(int)(cx*ratio+x_offset),(int)(cz*ratio+y_offset));
					break;
			}
		}
	}
	private void drawPoints(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		int len = points.size();
		for (int a=0; a<len; a++) {
			MyResponsePoint myPoint1 = points.get(a);
			int x = (int) ((myPoint1.getCenterX()-myPoint1.getWidth()/2) * ratio + this.x_offset); 
			int y = (int) ((myPoint1.getCenterY()-myPoint1.getHeight()/2) * ratio + this.y_offset); 
			int rx = (int) (myPoint1.getHeight() * ratio); 
			int ry = (int) (myPoint1.getWidth() * ratio); 
				g2.fillOval(x,y,rx,ry);
		}
	}
	private void drawCoords(Graphics2D g2, ArrayList<MyResponsePoint> points) {
		if(!Configuration.showCoords){
			return;
		}
		g2.setColor(Color.BLACK);
		for (MyResponsePoint myPoint : points) {
			g2.drawString("[ "+ImagePanelModel.getXAxis()+": "+String.format("%.2f",myPoint.getX()*ImagePanelModel.getXSpacing())+
					"mm; "+ImagePanelModel.getYAxis()+": "+String.format("%.2f",myPoint.getY()*ImagePanelModel.getYSpacing())
					+"mm]",
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
