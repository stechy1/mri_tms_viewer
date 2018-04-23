package model;

public class Response {

	private double mepMax;
	
	public Response(double mepMax) {
		this.mepMax = mepMax;
	}
	
	public Response() {
		this(0.0);
	}
	
	public double getMepMax() {
		return mepMax;
	}
	
	public void setMepMax(double mepMax) {
		this.mepMax = mepMax;
	}
	
	@Override
	public String toString() {
		return mepMax + "";
	}
}
