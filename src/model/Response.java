package model;

public class Response {

	private double amplitude;
	
	public Response(double amplitude) {
		this.amplitude = amplitude;
	}
	
	public Response() {
		this(0.0);
	}
	
	public double getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	@Override
	public String toString() {
		return Double.toString(amplitude);
	}
}
