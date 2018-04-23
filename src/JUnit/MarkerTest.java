package JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.TriggerMarkers;

public class MarkerTest {

	
	@Test
	public void test() {
		TriggerMarkers markers = new TriggerMarkers("C:\\Users\\skala\\git\\TMS_17\\data\\Skorepa\\SKOREPA_MILOS_19481015_481015045_679eb763\\BinData\\DICOM\\3e1b3fa4\\5f47e4bb");
		System.out.println("min: " + markers.getMinValue() + ", max: " + markers.getMaxValue());
	}

}
