package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import controller.Configuration;
import controller.QuickHull;
import enums.Controllers;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;

public class GroupVolume implements Serializable {

	
	private List<Slice> areas;
	private double volume;
	
	public GroupVolume(){
		this.areas = new ArrayList<Slice>();
		this.volume = 0.0;
	}
	
	
	/** Aktualizace 
	 * @param index
	 * @param value
	 */
	public void updateArea(int index, double value){
		for (Slice slice : areas) {
			if(slice.getSliceIndex() == index){
				slice.setArea(value);
				return;
			}
		}
		this.areas.add(new Slice(index, value));
		Collections.sort(this.areas);
	}
	
	/** Spocteni jednotlivych ploch
	 * @param points 
	 */
	public void calculateAreas(GroupModel group){
		
		this.areas = new ArrayList<Slice>();
		
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
		
		for (int i = 0; i < ipm.getMriDicom().size(); i++) {
			ArrayList<MyPoint> hullPoint = new QuickHull().quickHull(group.getPointFromLayer(i));
			group.setArea(i, hullPoint);
		}
		Collections.sort(this.areas);
	}
	
	public void updateVolume(){
		//TODO algoritmus, ktery zprumeruje 2 sousedni vrstvz a na zaklade sirky rezu spocte objem
		
		int start = 0, steps=1, end = 0;
		double volume = 0.0;
		
		while(start != areas.size() -1 ){
			while(areas.get(start).getArea() == 0){
				start++;
			}
			end = start+1;
			while(areas.get(end).getArea() == 0){
				steps++;
			}
			
			double st = areas.get(start).getArea();
			double en = areas.get(end).getArea();
			
			volume += (st + en) / 2 * steps * Configuration.sliceThickness; 
			
			steps = 1;
			start = end;
		}
		this.volume = volume;
	}
	
	public List<Slice> getAreas() {
		return areas;
	}
	
	public double getVolume() {
		updateVolume();
		return volume;
	}
}
