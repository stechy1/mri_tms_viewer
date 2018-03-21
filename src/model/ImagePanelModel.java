package model;

import java.awt.FontFormatException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.Configuration;
import enums.Controllers;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class ImagePanelModel {

	private List<MyDicom> mriDicom;
	private List<MyDicom> tmsDicom;
	
	//private ArrayList<ArrayList<MyPoint>> points; 
	private ArrayList<GroupModel> groups;
	
	public static String mriPath;
	public static String tmsPath;
	

	private int actualSnapshot=-1;
	
	//Hodnoty jsou v procentech a nasledne prepocteny
	private int brightness = 50;
	private int contrast = 50;
	
	private float minBrightness = 0.0f, maxBrightness = 2.0f, minContrast = 0, maxContrast = 256; 
	
	

	public ImagePanelModel() {
		this.mriDicom = new ArrayList<MyDicom>();
		this.tmsDicom = new ArrayList<MyDicom>();
		this.groups = new ArrayList<GroupModel>();
	}

	/*-------------------*
	 * Pristupove metody *
	 *-------------------*/

	public List<MyDicom> getMriDicom() {
		return mriDicom;
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
				if(actualSnapshot <= 0){
					this.actualSnapshot = 0;
				}
				else if(actualSnapshot >= this.mriDicom.size()){
					this.actualSnapshot = this.mriDicom.size()-1;
				}
				else{
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
		
		ArrayList<MyPoint> listOfPoint = new ArrayList<MyPoint>();
		
		for (GroupModel group : groups) {
			if(!group.getName().equals(Configuration.IGNORE_GROUP)){
				for (MyPoint myPoint : group.getPoints()) {
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
					
					MyPoint newCentroid = listOfPoint.get(index);
					GroupModel newGroup = new GroupModel("Skupina " + (i+1) , newCentroid); 
					this.groups.add(newGroup);
				}
				assignPointToGroups(listOfPoint);
			}
		}
		System.out.println();
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
	private void assignPointToGroups(ArrayList<MyPoint> list) {
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
			System.out.println();
			
			for (MyPoint myPoint : list) {
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
