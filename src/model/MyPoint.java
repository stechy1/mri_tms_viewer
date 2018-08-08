package model;
import java.io.Serializable;
import model.dialogWindow.group.GroupModel;
import java.util.Map;
public class MyPoint implements Serializable{
	static final long serialVersionUID = 8581091178539691643L;
	private double x, y, z, height = 0, widht = 0 ;
	private double amplitude,latency;
	private int pixelValue;
	private int minSize = 5;
	private GroupModel group;
	private boolean ignoreSize = true;
	public MyPoint(){}
	private MyPoint(MyPoint mp){
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
		this.ignoreSize = mp.ignoreSize;
	}
	@Override
	public Object clone() throws CloneNotSupportedException{
		return new MyPoint(this);
	}
	public MyResponsePoint toResponsePoint(){
		Response response = new Response();
		Map<String,Double> map = response.getData();
		map.put("amplitude",amplitude);
		map.put("mepMaxTime",latency);
		map.put("pixelValue",(double)pixelValue);
		//double[] matrix = r.getMatrix().getData();
		//matrix[3]=x;
		//matrix[7]=y;
		//matrix[11]=z;
		MyResponsePoint mp = new MyResponsePoint(x,y,height,widht,response);
		mp.setZ(z);
		mp.setMinSize(minSize);
		mp.setGroup(group);
		mp.setIgnoreSize(ignoreSize);
		return mp;
	}	
}
