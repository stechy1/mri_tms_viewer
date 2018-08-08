package JUnit;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Test;

import controller.QuickHull;
import model.MyResponsePoint;

public class HullTest {

	@Test
	public void test() {
		ArrayList<MyResponsePoint> points = new ArrayList<MyResponsePoint>();
		points.add(new MyResponsePoint(10, 10, 3));
		points.add(new MyResponsePoint(20, 20, 3));
		points.add(new MyResponsePoint(90, 20, 3));
		points.add(new MyResponsePoint(50, 30, 3));
		points.add(new MyResponsePoint(10, 80, 3));
		points.add(new MyResponsePoint(10, 10, 3));
		points.add(new MyResponsePoint(40, 60, 3));
		points.add(new MyResponsePoint(80, 80, 3));
		
		ArrayList<MyResponsePoint> hull = new QuickHull().quickHull(points);
		System.out.println("hotovo");
	}

}
