package JUnit;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Test;

import controller.QuickHull;
import model.MyPoint;

public class HullTest {

	@Test
	public void test() {
		ArrayList<MyPoint> points = new ArrayList<MyPoint>();
		points.add(new MyPoint(10, 10, 3));
		points.add(new MyPoint(20, 20, 3));
		points.add(new MyPoint(90, 20, 3));
		points.add(new MyPoint(50, 30, 3));
		points.add(new MyPoint(10, 80, 3));
		points.add(new MyPoint(10, 10, 3));
		points.add(new MyPoint(40, 60, 3));
		points.add(new MyPoint(80, 80, 3));
		
		ArrayList<MyPoint> hull = new QuickHull().quickHull(points);
		System.out.println("hotovo");
	}

}
