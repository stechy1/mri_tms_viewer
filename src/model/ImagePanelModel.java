package model;

import controller.Configuration;
import enums.Controllers;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanelModel {

	public static final int AXIS_Z = 0;
	public static final int AXIS_X = 1;
	public static final int AXIS_Y = 2;

	public static final int DICOM = 0;
	public static final int TMS = 3;
	private List<MyDicom> mriDicom;
	private List<MyDicom> tmsDicom;
	
	private ArrayList<GroupModel> groups;
	private BufferedImage[] across_x_mri;
	private BufferedImage[] across_y_mri;
	private BufferedImage[] across_x_tms;
	private BufferedImage[] across_y_tms;
	
	
	public static String mriPath;
	public static String tmsPath;
	
	private static int type = 0;
	private static int[] remember = new int[6];
	private static int actualSnapshot=-1;
	
	//Hodnoty jsou v procentech a nasledne prepocteny
	private int brightness = 50;
	private int contrast = 50;
	
	private float minBrightness = 0.0f, maxBrightness = 2.0f, minContrast = 0, maxContrast = 256; 
	
	

	public ImagePanelModel() {
		this.mriDicom = new ArrayList<MyDicom>();
		this.tmsDicom = new ArrayList<MyDicom>();
		this.groups = new ArrayList<GroupModel>();
		for(int a=0; a<remember.length; a++){
			remember[a] = -1;
		}
	}

	/*-------------------*
	 * Pristupove metody *
	 *-------------------*/

	public List<MyDicom> getMriDicom() {
		return mriDicom;
	}

	public static double getXSpacing(){
		return Configuration.pixelSpace;
	}

	public static double getYSpacing(){
		switch(type){
			case DICOM + AXIS_Z: return Configuration.pixelSpace;
			case TMS   + AXIS_Z: return Configuration.pixelSpace;
			default: return Configuration.sliceThickness;
		}
	}
	public static double getZSpacing(){
		switch(type){
			case DICOM + AXIS_Z: return Configuration.sliceThickness;
			case TMS   + AXIS_Z: return Configuration.sliceThickness;
			default: return Configuration.pixelSpace;
		}
	}
	public void remember(MyResponsePoint p){
		remember[DICOM + AXIS_X]=(int)p.getRealX();
		remember[DICOM + AXIS_Y]=(int)p.getRealY();
		remember[DICOM + AXIS_Z]=(int)p.getRealZ();
		remember[TMS   + AXIS_X]=(int)p.getRealX();
		remember[TMS   + AXIS_Y]=(int)p.getRealY();
		remember[TMS   + AXIS_Z]=(int)p.getRealZ();
	}

	public boolean isDicom(){
		return (type == DICOM + AXIS_Z) || (type == TMS + AXIS_Z);
	}
	public BufferedImage getActualImage() {
		try{
			switch(type){
				case DICOM + AXIS_Z: return mriDicom.get(actualSnapshot).getBufferedImage();
				case DICOM + AXIS_X: return across_x_mri[actualSnapshot];
				case DICOM + AXIS_Y: return across_y_mri[actualSnapshot];
				case TMS   + AXIS_Z: return tmsDicom.get(actualSnapshot).getBufferedImage();
				case TMS   + AXIS_X: return across_x_tms[actualSnapshot];
				case TMS   + AXIS_Y: return across_y_tms[actualSnapshot];
				default: return null;
			}
		}catch(NullPointerException e){
			return null;
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	public int getNumberOfImages(){
		try{
			switch(type){
				case DICOM + AXIS_Z: return mriDicom.size();
				case DICOM + AXIS_X: return across_x_mri.length;
				case DICOM + AXIS_Y: return across_y_mri.length;
				case TMS   + AXIS_Z: return tmsDicom.size();
				case TMS   + AXIS_X: return across_x_tms.length;
				case TMS   + AXIS_Y: return across_y_tms.length;
				default: return 0;
			}
		}catch(NullPointerException e){
			return 0;
		}
	}
	public static char getYAxis(){
		switch(type){
			case DICOM + AXIS_Z: return 'Y';
			case DICOM + AXIS_X: return 'Z';
			case DICOM + AXIS_Y: return 'Z';
			case TMS   + AXIS_Z: return 'Y';
			case TMS   + AXIS_X: return 'Z';
			case TMS   + AXIS_Y: return 'Z';
			default: return '?';
		}
	}
	public static char getXAxis(){
		switch(type){
			case DICOM + AXIS_Z: return 'X';
			case DICOM + AXIS_X: return 'Y';
			case DICOM + AXIS_Y: return 'X';
			case TMS   + AXIS_Z: return 'X';
			case TMS   + AXIS_X: return 'Y';
			case TMS   + AXIS_Y: return 'X';
			default: return '?';
		}
	}
	public static char getZAxis(){
		switch(type){
			case DICOM + AXIS_Z: return 'Z';
			case DICOM + AXIS_X: return 'X';
			case DICOM + AXIS_Y: return 'Y';
			case TMS   + AXIS_Z: return 'Z';
			case TMS   + AXIS_X: return 'X';
			case TMS   + AXIS_Y: return 'Y';
			default: return '?';
		}
	}
	public static void setType(int c_type){
		remember[type]=actualSnapshot;
		type = c_type;
		actualSnapshot=remember[type];
	}
	public static int getType(){
		return type;
	}
	public static int getTypes(){
		return remember.length;
	}
	public void setAcrossXMri(BufferedImage[] across_x_mri){
		this.across_x_mri = across_x_mri;
	}	

	public void setAcrossYMri(BufferedImage[] across_y_mri){
		this.across_y_mri = across_y_mri;
	}	

	public BufferedImage[] getAcrossXMri(){
		return across_x_mri;
	}

	public BufferedImage[] getAcrossYMri(){
		return across_y_mri;
	}

	public void setAcrossXTms(BufferedImage[] across_x_tms){
		this.across_x_tms = across_x_tms;
	}	

	public void setAcrossYTms(BufferedImage[] across_y_tms){
		this.across_y_tms = across_y_tms;
	}	

	public BufferedImage[] getAcrossXTms(){
		return across_x_tms;
	}

	public BufferedImage[] getAcrossYTms(){
		return across_y_tms;
	}

	public void initMriDicomList(){
		this.mriDicom = new ArrayList<MyDicom>();
		mriPath = null;
	}
	
	public void initTmsDicomList(){
		this.tmsDicom = new ArrayList<MyDicom>();
		tmsPath = null;
	}
	
	public List<MyDicom> getTmsDicom() {
		return tmsDicom;
	}
	
	public ArrayList<GroupModel> getGroups() {
		return groups;
	}
	
	public void setGroups(ArrayList<GroupModel> groups) {
		this.groups = groups;
	}

	public int getActualSnapshot() {
		return actualSnapshot;
	}
	
	public int getBrightness() {
		return brightness;
	}
	
	public int getContrast() {
		return contrast;
	}
	
	public void setActualSnapshot(int actualSnapshot) {
		if(this.mriDicom != null){
			if(this.mriDicom.size() != 0){
				int num = getNumberOfImages();
				if(actualSnapshot >= num){
					actualSnapshot = num-1;
				}
				if(actualSnapshot < 0){
					this.actualSnapshot = -1;
				}else{
					this.actualSnapshot = actualSnapshot;
				}
				MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
				MainWindow.getController(Controllers.SNAPSHOT_PANE_CTRL).notifyController();
				MainWindow.getController(Controllers.PATIENT_INFO_PANE_CTRL).notifyController();
				MainWindow.getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL).notifyController();
			}
		}
	}
	
	public void setBrightness(int brightness) {
		if(brightness >=0 && brightness <=100){
			this.brightness = brightness;
			
			MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
			MainWindow.getController(Controllers.ENHANCE_IMAGE_PANE_CTRL).notifyController();
		}
		
		
		this.brightness = brightness;
	}
	
	public void setContrast(int contrast) {
		if(contrast >=0 && contrast <= 100){
			this.contrast = contrast;
			
			MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
			MainWindow.getController(Controllers.ENHANCE_IMAGE_PANE_CTRL).notifyController();
		}
	}
	
	public float convertContrast(){
		return ((maxContrast - minContrast) / 100 * this.contrast);
	}
	
	public float convertBrightness(){
		return ((maxBrightness - minBrightness) / 100 * this.brightness);
	}

	public void createGroups(int count) {
		
		//vsechny body budou v jednom poli... projekce do roviny.. az na ignore
		
		ArrayList<MyResponsePoint> listOfPoint = new ArrayList<MyResponsePoint>();
		
		for (GroupModel group : groups) {
			if(!group.getName().equals(Configuration.IGNORE_GROUP)){
				for (MyResponsePoint myPoint : group.getPoints()) {
					listOfPoint.add(myPoint);
				}
				if(group.getName().equals(Configuration.UNASSIGN_GROUP)){
					group.getPoints().clear();
				}
			}
		}
		
		for(int i = 0 ; i < groups.size(); i++){
			
			if(! (groups.get(i).getName().equals(Configuration.IGNORE_GROUP) || groups.get(i).getName().equals(Configuration.UNASSIGN_GROUP))){
				groups.remove(i);
				i--;
			}
		}
		
		if(listOfPoint != null){
			if(listOfPoint.size() != 0){
				
				//TODO osetrit velke mnozstvi skupin
				for (int i = 0; i < count; i++) {
					Random ran = new Random();
					int index = ran.nextInt(999) * (i+11) % listOfPoint.size();
					
					MyResponsePoint newCentroid = listOfPoint.get(index);
					GroupModel newGroup = new GroupModel("Skupina " + (i+1) , newCentroid); 
					this.groups.add(newGroup);
				}
				assignPointToGroups(listOfPoint);
			}
		}
	}
	
	public GroupModel getGroup(String retGroup){
		for (GroupModel group : groups) {
			if(group.getName().equals(retGroup)){
				return group;
			}
		}
		return null;
	}

	//kmeans
	private void assignPointToGroups(ArrayList<MyResponsePoint> list) {
		//TODO pokracovat zde na prirazovani bodu ke skupinam... pozor na 2 zakladni skupiny
		int countOfChanges = Integer.MAX_VALUE;
		
		while(countOfChanges != 0){
			//TODO zdokonalit kmeans
			countOfChanges = 0;
			if(this.groups != null){
				for (GroupModel group : this.groups) {
					group.computeCentroids();
					group.getPoints().clear();
				}
			}
			
			for (MyResponsePoint myPoint : list) {
				double minDist = Double.MAX_VALUE;
				GroupModel oldGroup = myPoint.getGroup();
				GroupModel newGroup = myPoint.getGroup();
				
				//vypocteni k jakemu centroidu je bod nejblize
				for (GroupModel group : groups) {
					if(! (group.getName().equals(Configuration.UNASSIGN_GROUP) || group.getName().equals(Configuration.IGNORE_GROUP))){
						double dist = group.getCentroid().distance(myPoint);
						if(dist < minDist){
							minDist = dist;
							newGroup = group;
						}
					}
				}
				
				if(!oldGroup.equals(newGroup)){
					countOfChanges++;
				}
				
				myPoint.setGroup(newGroup);	
				newGroup.getPoints().add(myPoint);
			}
		}
		System.out.println("skupiny vytvoreny");
	}	
}
