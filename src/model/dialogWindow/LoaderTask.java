package model.dialogWindow;
import javax.swing.SwingWorker;
import javax.swing.JProgressBar;
import controller.centerPane.ImagePaneController;
import controller.Configuration;
import controller.UtilityClass;
import enums.Controllers;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import model.ImagePanelModel;
import model.MyDicom;
import model.MyPoint;
import model.Response;
import model.TriggerMarkers;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import javax.swing.JFrame;
import java.util.List;
public class LoaderTask extends SwingWorker<Void,Integer>{
	private File folder;
	private ImagePanelModel model;
	private boolean mri = false;
	private ImagePaneController ipc;
	private JFrame jf = null;
	public LoaderTask(File folder,ImagePanelModel model,boolean mri){
		this.folder = folder;
		this.model = model;
		this.mri = mri;
		this.ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
	}
	public void linkToJFrame(JFrame jf){
		this.jf=jf;
	}
	@Override
	public Void doInBackground(){
		if(mri){
			loadMriFiles();
		}else{
			loadTmsFiles();
		}
		return null;
	}
	@Override
	protected void done(){
		setProgress(100);
		if(jf!=null){
			jf.dispose();
		}
	}
	private double getDiameter(int i, int j) {
		return (i+j) / 2 ;
	}
	/** Metoda, ve ktere si zvolim prvni bod z mnoziny a najdu jeho sousedy + vytvorim jeden finalni bod
	 * @param area 
	 * @return
	 */
	/*private MyPoint createPoint(ArrayList<MyPoint> area) {
		ArrayList<MyPoint> pointsInPoint = new ArrayList<MyPoint>();
		Stack<MyPoint> stack = new Stack<MyPoint>();
		stack.push(area.remove(0));
		//set of points in point.
		while(!stack.isEmpty()) {
			MyPoint refPoint = stack.pop();
			if(!pointsInPoint.contains(refPoint)) {
				pointsInPoint.add(refPoint);
			}

			int distX, distY;
			for(int i = 0; i < area.size(); i++) {
				distX = Math.abs((int) (refPoint.getCenterX() - area.get(i).getCenterX()));
				distY = Math.abs((int) (refPoint.getCenterY() - area.get(i).getCenterY()));
				if(distX <= 1 && distY <= 1) {
					stack.add(area.remove(i));
				}
			}
		}
		int x1=384, x2=0, y1=384, y2=0;
		//zde dochazi k vytvoreni jednoho bodu ze sady pixelu
		int totalPixelValue = 0;
		for(int i = 0 ; i < pointsInPoint.size() ; i++) {
			MyPoint out = pointsInPoint.get(i);
			totalPixelValue += out.getPixelValue();
			double outX = out.getCenterX();
			double outY = out.getCenterY();
			//ohraniceni vsech bodu... 
			if(outX < x1) x1 = (int) outX;
			if(outX > x2) x2 = (int) outX;
			if(outY < y1) y1 = (int) outY;
			if(outY > y2) y2 = (int) outY;
		}
		double diameter = getDiameter(x2-x1, y2-y1);
		int pixelValue = 0;
		if(pointsInPoint.size() != 0) {
			pixelValue = (int) totalPixelValue / pointsInPoint.size();
		}
		return new MyPoint<Integer>(x1, y1, diameter, pixelValue);
	}*/
	//Metoda, ktera z DICOM snimku vrati seznam bodu, ktere se na snimku vyskytuji
	/*private ArrayList<MyPoint> convertTMSDicomToAreaPoints(MyDicom dcm) {
		ArrayList<MyPoint> area;
		BufferedImage img = dcm.getBufferedImage();
		//Krok jedna: ziskat vsechny svetle body
		area = getArrayOfPixels(img);
		//krok dva: sousedni body spojit do jednoho
		area = getArrayOfPoints(area);
		return area;
	}*/
	/*private ArrayList<MyPoint> getArrayOfPixels(BufferedImage img) {
		ArrayList<MyPoint> area = new ArrayList<MyPoint>();
		for(int x = 0; x < img.getWidth() ; x ++){
			for(int y = 0 ; y < img.getHeight() ; y++){
				int color = img.getRGB(x, y);
				int  red = (color & 0x00ff0000) >> 16;
				int  green = (color & 0x0000ff00) >> 8;
				int  blue = color & 0x000000ff;
				int total = (int) (red+green+blue)/3;
				if(total > Configuration.WHITE_PIXEL_TRESSHOLD){
					area.add(new MyPoint<Integer>(x, y, 1, total));
				}
			}
		}
		return area;
	}*/
	/** list pixelu, ktere maji hodnotu vetsi nez tresshold z configu. 
	 * @param area
	 * @return
	 */
	/*private ArrayList<MyPoint> getArrayOfPoints(ArrayList<MyPoint> area) {
		ArrayList<MyPoint> retArea = new ArrayList<MyPoint>();
		while(area.size() != 0){
			retArea.add(createPoint(area));
		}
		return retArea;
	}*/
	private void calculateTMSPoints() {
		this.model.setGroups(new ArrayList<GroupModel>());
		GroupModel group = new GroupModel(Configuration.IGNORE_GROUP);
		this.model.getGroups().add(group);
		group = new GroupModel(Configuration.UNASSIGN_GROUP);
		this.model.getGroups().add(group);
		/*for (int i = 0 ; i < this.model.getTmsDicom().size(); i++){
			for (MyPoint myPoint : convertTMSDicomToAreaPoints(this.model.getTmsDicom().get(i))) {
				myPoint.setZ(i);
				myPoint.setGroup(group);
				group.getPoints().add(myPoint);
			}
		}
		AssignAmplitudesToPoints(group.getPoints());*/
		TriggerMarkers markers = new TriggerMarkers();
		double[] coords = new double[4];
		for(Response r: markers.getResponses()){
			MyDicom dicom = this.model.getMriDicom().get(this.model.getActualSnapshot());
			//MyDicom dicom = this.model.getTmsDicom().get(this.model.getActualSnapshot()); //TODO
			r.calculateCoords(dicom,coords);
			MyPoint<Response> point = new MyPoint<>((int)coords[0],(int)coords[1],1,r);
			point.setZ((int)coords[2]);
			point.setGroup(group);
			group.getPoints().add(point);
		}
	}
	/*private void AssignAmplitudesToPoints(ArrayList<MyPoint> points) {
		int minValue = 255, maxValue = 0;
		for (MyPoint myPoint : points) {
			if(myPoint.getPixelValue() < minValue)
				minValue = myPoint.getPixelValue();
			if(myPoint.getPixelValue() > maxValue)
				maxValue = myPoint.getPixelValue();
		}
		TriggerMarkers markers = new TriggerMarkers();
		int minResponse = (int) markers.getMinValue();
		int maxResponse = (int) markers.getMaxValue();
		for (MyPoint myPoint : points) {
			myPoint.calculateAmplitude(maxValue, minValue, minResponse, maxResponse);
		}
	}*/
	public void linkToProgressBar(JProgressBar jpb){
		addPropertyChangeListener((e)->{
			if("progress".equals(e.getPropertyName())){
				jpb.setValue((Integer)e.getNewValue());
			}
		});
	}
	public MyDicom loadDicom(String filePath){
		MyDicom dcm = new MyDicom();
		dcm.open(filePath);
		return dcm;
	}
	@SuppressWarnings("unchecked")
	private void importGroupsFromFile(File f){
		ObjectInputStream objectinputstream = null;
		if(model.getGroups() != null){

			InputStream is = null;
			ObjectInputStream ois = null;
			try {
				is = new FileInputStream(f);
				ois = new ObjectInputStream(is);
				this.model.setGroups((ArrayList<GroupModel>) ois.readObject());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(objectinputstream != null){
					try {
						is.close();
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			}
		}
	}
	public int getFileCount(){
		int count = 0;
		for (String fileName : folder.list()) {
			if(fileName.endsWith(".dcm")){
				count++;
			}
		}
		return count;
	}
	public void loadMriFiles(){

		if(folder != null){
			this.model.initMriDicomList();
			this.model.initTmsDicomList();

			int count1=getFileCount();
			int count2=0;

			for (String fileName : folder.list()) {
				if(fileName.endsWith(".dcm")){
					this.model.getMriDicom().add(loadDicom(folder.getAbsolutePath() + File.separator + fileName));
					setProgress(100*(count2++)/count1);
				}
			}
			Collections.sort(this.model.getMriDicom());
			this.model.setActualSnapshot(0);
			try{
				constructSides(this.model.getMriDicom());
			}catch(Exception e){e.printStackTrace();}
			ipc.notifyController();
		}
	}
	public void constructSides(List<MyDicom> from){
		BufferedImage active = from.get(0).getBufferedImage();
		int X = active.getWidth(null);
		int Y = active.getHeight(null);
		int Z = this.model.getMriDicom().size();
		BufferedImage[] across_x = new BufferedImage[X];
		BufferedImage[] across_y = new BufferedImage[Y];
		byte[][] active_buffers = new byte[Z][];
		for(int z=0; z<Z; z++){
			active_buffers[z] = getRaster(from.get(z).getBufferedImage());
		}
		for(int x=0; x<X; x++){
			across_x[x] = new BufferedImage(Y,Z,active.getType());
			byte[] new_buffer = getRaster(across_x[x]);
			for(int z=0; z<Z; z++){
				int move_zY = z*Y;
				for(int y=0; y<Y; y++){
					int move_yX = y*X;
					new_buffer[y+move_zY] = active_buffers[z][x+move_yX];
				}
			}
		}
		for(int y=0; y<Y; y++){
			across_y[y] = new BufferedImage(X,Z,active.getType());
			byte[] new_buffer = getRaster(across_y[y]);	
			int move_yX = y*X;
			for(int z=0; z<Z; z++){
				int move_zX = z*X;
				for(int x=0; x<X; x++){
					new_buffer[x+move_zX] = active_buffers[z][x+move_yX];
				}
			}
		}
		if(from == this.model.getMriDicom()){
			this.model.setAcrossXMri(across_x);
			this.model.setAcrossYMri(across_y);	
		}else{
			this.model.setAcrossXTms(across_x);
			this.model.setAcrossYTms(across_y);	
		}
	}
	public static byte[] getRaster(BufferedImage buf){
		return ((DataBufferByte)buf.getRaster().getDataBuffer()).getData();
	}
	public void loadTmsFiles(){
		if(folder != null){
			this.model.initTmsDicomList();
			ImagePanelModel.tmsPath = folder.getPath();
			int count1=getFileCount();
			int count2=0;
			ImagePanelModel.mriPath = folder.getPath();

			for (String fileName : folder.list()) {
				if(fileName.endsWith(".dcm")){
					this.model.getTmsDicom().add(loadDicom(folder.getAbsolutePath() + File.separator + fileName));
					setProgress(100*(count2++)/count1);
				}
			}
			Collections.sort(this.model.getTmsDicom());
			File f = new File(ImagePanelModel.tmsPath + File.separator + Configuration.GROUPS);
			if(f.exists()){
				if(UtilityClass.showConfirmDialog("Chcete načíst data ze souboru")){
					importGroupsFromFile(f);
				}
				else{
					calculateTMSPoints();
				}
			}
			else{
				calculateTMSPoints();
			}
			try{
				constructSides(this.model.getTmsDicom());
			}catch(Exception e){e.printStackTrace();}
			ipc.notifyController();
		}
	}
}
