package model;
import static controller.UtilityClass.stringToDouble;

import controller.Configuration;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Map;
import model.dialogWindow.group.GroupModel;
public class MyPoint extends Ellipse2D implements Serializable{
	static final long serialVersionUID = 8581091178539691643L;
	private double x, y, z, height = 0, widht = 0 ;
	private double amplitude,latency;
	private int pixelValue;
	private int minSize = 5;
	private GroupModel group;
	private transient boolean active = false;
	private transient double back_x,back_y,back_z;
	public MyPoint(double x, double y, double height, double width) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.widht = width;
		if(this.height < minSize){
			this.height = minSize;
		}
		if(this.widht < minSize){
			this.widht = minSize;
		}
	}
	public void restoreCoords(){
		x=back_x;
		y=back_y;
		z=back_z;
	}
	public void backupCoords(){
		back_x=x;
		back_y=y;
		back_z=z;
	}
	protected MyPoint(MyPoint mp){
		this.x = mp.x;
		this.y = mp.y;
		this.z = mp.z;
		this.height = mp.height;
		this.widht = mp.widht;
		this.amplitude = mp.amplitude;
		this.latency = mp.latency;
		this.pixelValue = mp.pixelValue;
		this.minSize = mp.minSize;
		this.group = mp.group;
	}
	public void setRealX(double x) {
		this.x = x;
	}
	public void setRealY(double y) {
		this.y = y;
	}
	public void setRealZ(double z) {
		this.z = z;
	}
	public double getRealX(){
		return x;
	}	
	public double getRealY(){
		return y;
	}	
	public double getRealZ(){
		return z;
	}	
	public void setX(double x) {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): this.y=x; break;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): this.y=x; break;
			default: this.x=x;
		}
	}
	@Override
	public double getX() {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): return y;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): return y;
			default: return x;
		}
	}

	public void setY(double y) {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Z): this.y=y; break;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Z): this.y=y; break;
			default: this.z=y;
		}
	}
	@Override
	public double getY() {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Z): return y;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Z): return y;
			default: return z;
		}
	}
	public double getZ() {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): return x;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): return x;
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Y): return y;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Y): return y;
			default: return z;
		}
	}
	public void setZ(double z) {
		switch(ImagePanelModel.getType()){
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_X): this.x=z; break;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_X): this.x=z; break;
			case (ImagePanelModel.DICOM+ImagePanelModel.AXIS_Y): this.y=z; break;
			case (ImagePanelModel.TMS+ImagePanelModel.AXIS_Y): this.y=z; break;
			default: this.z=z;
		}
	}
	@Override
	public Object clone(){
		return new MyPoint(this);
	}
	@Override
	public Rectangle2D getBounds2D() {
		return null;
	}
	@Override
	public double getHeight() {
		if(isActive()){
			return height*2;
		}else{
			return height;
		}
	}
	public boolean isActive(){
		return this.active;
	}
	@Override
	public double getWidth() {
		if(isActive()){
			return widht*2;
		}else{
			return widht;
		}
	}
	@Override
	public boolean isEmpty() {
		return false;
	}
	@Override
	public double getCenterX(){
		return this.getX();
	}
	@Override
	public double getCenterY(){
		return this.getY();
	}
	public void setMinSize(int minSize){
		this.minSize = minSize;
	}
	public GroupModel getGroup() {
		return this.group;
	}
	public void setActive(boolean active){
		this.active = active;
	}
	@Override
	public void setFrame(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.widht = w;
		this.height = h;
	}
	public void importPoint(String in) {
		String[] tokens = in.split(Configuration.GROUP_COMMA);
		for (int i = 0; i < tokens.length; i++) {
			this.setRealX(stringToDouble(tokens[0]));
			this.setRealY(stringToDouble(tokens[1]));
			this.height = stringToDouble(tokens[2]);
		}
	}
	public void setGroup(GroupModel group) {
		this.group = group;
	}
	public double distance(MyPoint point){
		double x = this.x - point.x;
		double y = this.y - point.y;
		double z = this.z - point.z;
		return x*x+y*y+z*z;
	}
	@Override
	public String toString() {
		return "x: " + this.x + ", y: " + this.y + ", z: " + this.z;
	}

	public String exportPoint() {
		return this.getX() + "," + this.getY() + "," + this.getHeight() + "," +
					this.getGroup().getName();
	}
	public MyResponsePoint toResponsePoint(){
		Response response = new Response();
		Map<String,java.lang.Double> map = response.getData();
		map.put("amplitude",amplitude);
		map.put("mepMaxTime",latency);
		map.put("pixelValue",(double)pixelValue);
		//double[] matrix = r.getMatrix().getData();
		//matrix[3]=x;
		//matrix[7]=y;
		//matrix[11]=z;
		MyResponsePoint mp = new MyResponsePoint(x,y,height,widht,response);
		mp.setRealZ(z);
		mp.setMinSize(minSize);
		mp.setGroup(group);
		return mp;
	}	
}
