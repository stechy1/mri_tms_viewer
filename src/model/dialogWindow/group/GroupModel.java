package model.dialogWindow.group;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


import controller.Configuration;
import enums.DicomTags;
import model.GroupVolume;
import model.MyPoint;
import model.Slice;

import static controller.UtilityClass.*;
import static controller.Configuration.*;

public class GroupModel implements Serializable {

	private int id;
	private String name;
	private MyPoint centroid;
	private Color layerColor;
	private Color groupColor;
	
	private GroupVolume volume;
	
	private ArrayList<MyPoint> points;
	
	
	public GroupModel(String name, MyPoint centroid, Color color) {
		this.points = new ArrayList<MyPoint>();
		this.name = name;
		this.layerColor = color;
		this.groupColor = color;
		this.centroid = (MyPoint) centroid.clone();
		this.volume = new GroupVolume();
	}
	
	public GroupModel(String name, MyPoint centroid){
		this(name, centroid, null);
		Random ran = new Random();
		this.layerColor = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
	}
	
	public GroupModel(String name) {
		this(name, new MyPoint());
	}
	
	public GroupModel(){
		this("unknown");
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setGroupColor(Color groupColor) {
		this.groupColor = groupColor;
	}
	
	public Color getGroupColor() {
		if(this.name.equals(Configuration.UNASSIGN_GROUP)){
			return Configuration.UNASSIGN_COLOR;
		}
		
		if(this.name.equals(Configuration.IGNORE_GROUP)){
			return Configuration.IGNORE_COLOR;
		}
		
		//TODO zmenit
		if(this.layerColor != null){
			return layerColor;
		}
		else{
			return new Color(250, 100, 100);
		}
	}
	
	public void setLayerColor(Color color) {
		this.layerColor = color;
	}
	
	public Color getLayerColor() {
		if(this.name.equals(Configuration.UNASSIGN_GROUP)){
			return Configuration.UNASSIGN_COLOR;
		}
		
		if(this.name.equals(Configuration.IGNORE_GROUP)){
			return Configuration.IGNORE_COLOR;
		}
		
		if(this.layerColor != null){
			return layerColor;
		}
		else{
			return new Color(250, 100, 100);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public MyPoint getCentroid() {
		return centroid;
	}
	
	public void setCentroid(MyPoint centroid) {
		this.centroid = centroid;
	}
	
	public ArrayList<MyPoint> getPoints() {
		return points;
	}
	
	public void setPoints(ArrayList<MyPoint> points) {
		this.points = points;
	}
	
	public void setArea(int slice, ArrayList<MyPoint> points){
		
		this.volume.updateArea(slice, computeArea(points));
	}
	
	public double computeArea(ArrayList<MyPoint> points) {
		double area = 0.0;
		
		for(int i = 0 ; i < points.size(); i++){
			
			MyPoint fromPoint = points.get(i%points.size());
			MyPoint toPoint = points.get((i+1)%points.size());
			MyPoint overPoint = points.get((i+2)%points.size());
			
			//TODO roznasobit pomerem pixel na mm
			
			double i1_x = toPoint.getCenterX() * pixelSpace;
			double i2_y = overPoint.getCenterY() * pixelSpace;
			double i0_y = fromPoint.getCenterY() * pixelSpace;
			
			area +=  (i1_x * (i2_y - i0_y)) / 2;
		}
		area = Math.abs(area);
		return area;
	}
	
	
	public double getArea(int layer){
		for (Slice slice : this.volume.getAreas()) {
			if(slice.getSliceIndex() == layer){
				return slice.getArea();
			}
		}
		return 0;
	}
	
	public ArrayList<MyPoint> getCopyOfPoints(){
		ArrayList<MyPoint> list = new ArrayList<MyPoint>();
		for (MyPoint myPoint : points) {
			list.add(myPoint);
		}
		return list;
	}
	
	public ArrayList<MyPoint> getPointFromLayer(int layer){
		ArrayList<MyPoint> list = new ArrayList<MyPoint>();
		for (MyPoint myPoint : points) {
			if(myPoint.getZ() == layer){
				list.add(myPoint);
			}
		}
		return list;
	}
	
	public void computeCentroids() {
		if(this.points != null){
			if(this.points.size() != 0){
				int x = 0, y = 0;
				for (MyPoint myPoint : points) {
					x += myPoint.getCenterX();
					y += myPoint.getCenterY();
				}
				this.centroid = new MyPoint(x / points.size(), y / points.size());
			}
			else{
				if(this.centroid == null){
					this.centroid = new MyPoint();
				}
			}
		}
	}
	
	public GroupVolume getVolume() {
		return volume;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}	
}
