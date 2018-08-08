package model;
public class MyResponsePoint extends MyPoint {
	private Response response;
	public MyResponsePoint(double x, double y, double height, double width, Response response) {
		super(x,y,height,width);
		this.response = response;
	}
	public MyResponsePoint(double x, double y, double radius, Response response) {
		this(x,y, radius, radius, response);
	}
	
	public MyResponsePoint(double x, double y, double radius) {
		this(x,y,radius, radius, null);
	}
	
	public MyResponsePoint(double x, double y){
		this(x, y, 0);
	}
	
	public MyResponsePoint(){
		this(0,0);
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
}
