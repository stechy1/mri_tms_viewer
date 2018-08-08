package model.dialogWindow.group;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import controller.Configuration;
import enums.DicomTags;
import model.GroupVolume;
import model.MyResponsePoint;
import model.MyPoint;
import model.Response;
import model.Slice;

import static controller.UtilityClass.*;
import static controller.Configuration.*;

public class GroupModel implements Serializable {
	static final long serialVersionUID = 8173646712421004394L;

	private int id;
	private String name;
	private MyPoint centroid;
	private Color layerColor;
	private Color groupColor;
	
	private GroupVolume volume;
	
	private ArrayList<MyPoint> points;
	private transient ArrayList<MyResponsePoint> points_converted = new ArrayList<>();
	private transient MyResponsePoint centroid_converted;
	
	
	public void convert(){
		if(points_converted==null){
			this.points_converted = new ArrayList<>();
		}
		if(points!=null){
			for(int a=0; a<points.size(); a++){
				this.points_converted.add(points.get(a).toResponsePoint());
			}
		}
		if(centroid!=null){
			this.centroid_converted = centroid.toResponsePoint();
		}
	}
	public GroupModel(String name, MyResponsePoint centroid, Color color) {
		this.name = name;
		this.layerColor = color;
		this.groupColor = color;
		try{
			this.centroid_converted = (MyResponsePoint) centroid.clone();
		}catch(Exception e){
			this.centroid_converted = new MyResponsePoint();
		}
		this.volume = new GroupVolume();
	}

	public GroupModel(String name, MyResponsePoint centroid){
		this(name, centroid, null);
		Random ran = new Random();
		this.layerColor = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
	}
	
	public GroupModel(String name) {
		this(name, new MyResponsePoint());
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
	
	public MyResponsePoint getCentroid() {
		if(centroid_converted==null){
			convert();
		}
		return centroid_converted;
	}
	
	/*public void setCentroid(MyResponsePoint centroid) {
		this.centroid_converted = centroid;
	}*/
	
	public ArrayList<MyResponsePoint> getPoints() {
		if(points_converted==null){
			convert();
		}
		return points_converted;
	}
	
	public void setPoints(ArrayList<MyResponsePoint> points) {
		this.points_converted = points;
	}
	
	public void setArea(int slice, ArrayList<MyResponsePoint> points){
		
		this.volume.updateArea(slice, computeArea(points));
	}
	
	public double computeArea(ArrayList<MyResponsePoint> points) {
		double area = 0.0;
		
		for(int i = 0 ; i < points.size(); i++){
			
			MyResponsePoint fromPoint = points.get(i%points.size());
			MyResponsePoint toPoint = points.get((i+1)%points.size());
			MyResponsePoint overPoint = points.get((i+2)%points.size());
			
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
	
	public ArrayList<MyResponsePoint> getCopyOfPoints(){
		ArrayList<MyResponsePoint> list = new ArrayList<>();
		for (MyResponsePoint myPoint : points_converted) {
			list.add(myPoint);
		}
		return list;
	}
	
	public ArrayList<MyResponsePoint> getPointFromLayer(int layer){
		ArrayList<MyResponsePoint> list = new ArrayList<>();
		for (MyResponsePoint myPoint : points_converted) {
			if((int)myPoint.getZ() == layer){
				list.add(myPoint);
			}
		}
		return list;
	}
	
	public void computeCentroids() {
		if(this.points != null){
			if(this.points.size() != 0){
				int x = 0, y = 0;
				for (MyResponsePoint myPoint : points_converted) {
					x += myPoint.getCenterX();
					y += myPoint.getCenterY();
				}
				this.centroid_converted = new MyResponsePoint(x / points.size(), y / points.size());
			}
			else{
				if(this.centroid == null){
					this.centroid_converted = new MyResponsePoint();
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
