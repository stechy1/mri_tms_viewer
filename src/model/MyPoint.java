package model;

import static controller.Configuration.SELECTION_TRESSHOLD;
import static controller.UtilityClass.stringToDouble;

import controller.Configuration;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import model.dialogWindow.group.GroupModel;

public class MyPoint<T> extends Ellipse2D implements Serializable {

	static final long serialVersionUID = 8581091178539691643L;
	
	private double x, y, z, height = 0, widht = 0 ;
	private T pixelValue;
	
	private int minSize = 5;
	
	private GroupModel group;
	
	private boolean ignoreSize = true;
	
	public MyPoint(double x, double y, double height, double width, T pixelValue) {
		super();
		this.x = x;
		this.y = y;
		this.pixelValue = pixelValue;
		
		if(!ignoreSize){
			this.height = height;
			this.widht = width;
		}
		if(this.height < minSize) this.height = minSize;
		if(this.widht < minSize) this.widht = minSize;
	}
	
	public MyPoint(double x, double y, double radius, T pixelValue) {
		this(x,y, radius, radius, pixelValue);
	}
	
	public MyPoint(double x, double y, double radius) {
		this(x,y,radius, radius, null);
	}
	
	public MyPoint(double x, double y){
		this(x, y, 0);
	}
	
	public MyPoint(){
		this(0,0);
	}

	public void setActive(boolean active){
		if(active){
			this.height*=2;
			this.widht*=2;
		}else{
			this.height/=2;
			this.widht/=2;
		}
	}

	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(this.getX() - this.widht/2 - SELECTION_TRESSHOLD , this.getY() - this.height/2 - SELECTION_TRESSHOLD, 
				this.widht + 2*SELECTION_TRESSHOLD, this.height + 2*SELECTION_TRESSHOLD);		
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return widht;
	}

	@Override
	public double getX() {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): return y;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): return y;
			default: return x;
		}
	}

	public double getRealX(){
		return x;
	}	
	public double getRealY(){
		return x;
	}	
	public double getRealZ(){
		return x;
	}	
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Z): return y;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Z): return y;
			default: return z;
		}
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getZ() {
		/*double a;
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): a=x;break;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): a=x;break;
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Y): a=y;break;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Y): a=y;break;
			default: a=z;
		}*/
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): return x;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): return x;
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Y): return y;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Y): return y;
			default: return z;
		}
	}
	
	public T getPixelValue() {
		return pixelValue;
	}
	
	public void setPixelValue(T pixelValue) {
		this.pixelValue = pixelValue;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFrame(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.widht = w;
		this.height = h;
	}
	
	public GroupModel getGroup() {
		return this.group;
	}
	
	public void setGroup(GroupModel group) {
		this.group = group;
	}
	
	public double distance(MyPoint point){
		
		double x = this.getCenterX() - point.getCenterX();
		double y = this.getCenterY() - point.getCenterY();
		
		double ret = Math.hypot(x, y);
		
		//double ret = Math.pow(x*x + y*y, 1/2); 
		
		return Math.abs(ret);
	}
	
	@Override
	public String toString() {
		return "x: " + this.getCenterX() + ", y: " + this.getCenterY() + ", z: " + this.getZ();
	}

	public String exportPoint() {
		return this.getX() + "," + this.getY() + "," + this.getHeight() + "," +
					this.getGroup().getName();
		
	}

	public void importPoint(String in) {
		String[] tokens = in.split(Configuration.GROUP_COMMA);
		for (int i = 0; i < tokens.length; i++) {
			this.setX(stringToDouble(tokens[0]));
			this.setY(stringToDouble(tokens[1]));
			this.height = stringToDouble(tokens[2]);
		}
	}
	

}
