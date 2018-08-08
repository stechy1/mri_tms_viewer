package controller;

import static controller.UtilityClass.getTagValue;
import static controller.UtilityClass.stringToDouble;

import java.util.ArrayList;

import enums.DicomTags;
import model.MyResponsePoint;
import model.Response;

public class QuickHull{

	
	public ArrayList<MyResponsePoint> quickHull(ArrayList<MyResponsePoint> points){

        ArrayList<MyResponsePoint> convexHull = new ArrayList<MyResponsePoint>();

        if (points.size() < 3)
        	return new ArrayList<MyResponsePoint>(points);

        int minPoint = -1, maxPoint = -1;
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;

        for (int i = 0; i < points.size(); i++){

            if (points.get(i).getCenterX() < minX){
                minX = points.get(i).getCenterX();
                minPoint = i;
            }

            if (points.get(i).getCenterX() > maxX){
            	maxX = points.get(i).getCenterX();
            	maxPoint = i;
            }
        }

        MyResponsePoint A = points.get(minPoint);
        MyResponsePoint B = points.get(maxPoint);

        convexHull.add(A);
        convexHull.add(B);

        points.remove(A);
        points.remove(B);

        ArrayList<MyResponsePoint> leftSet = new ArrayList<MyResponsePoint>();
        ArrayList<MyResponsePoint> rightSet = new ArrayList<MyResponsePoint>();

        for (int i = 0; i < points.size(); i++){
        	
        	MyResponsePoint p = points.get(i);
        	if (pointLocation(A, B, p) == -1)
        		leftSet.add(p);
        	else if (pointLocation(A, B, p) == 1)
        		rightSet.add(p);
        }

        hullSet(A, B, rightSet, convexHull);
        hullSet(B, A, leftSet, convexHull);

        return convexHull;

    }

    public double distance(MyResponsePoint A, MyResponsePoint B, MyResponsePoint C){

        double ABx = B.getCenterX() - A.getCenterX();
        double ABy = B.getCenterY() - A.getCenterY();
        double num = ABx * (A.getCenterY() - C.getCenterY()) - ABy * (A.getCenterX() - C.getCenterX());
        
        return Math.abs(num);
    }

    public void hullSet(MyResponsePoint A, MyResponsePoint B, ArrayList<MyResponsePoint> set, ArrayList<MyResponsePoint> hull) {

        int insertPosition = hull.indexOf(B);

        if (set.size() == 0)
        	return;

        if (set.size() == 1){

            MyResponsePoint p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }

        double dist = Double.MIN_VALUE;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++)

        {

            MyResponsePoint p = set.get(i);

            double distance = distance(A, B, p);

            if (distance > dist)

            {
            	dist = distance;
            	furthestPoint = i;
            }

        }

        MyResponsePoint P = set.get(furthestPoint);

        set.remove(furthestPoint);

        hull.add(insertPosition, P);

 
        // Determine who's to the left of AP

        ArrayList<MyResponsePoint> leftSetAP = new ArrayList<MyResponsePoint>();

        for (int i = 0; i < set.size(); i++)

        {

            MyResponsePoint M = set.get(i);

            if (pointLocation(A, P, M) == 1)

            {
            	leftSetAP.add(M);
            }
        }

 

        // Determine who's to the left of PB

        ArrayList<MyResponsePoint> leftSetPB = new ArrayList<MyResponsePoint>();

        for (int i = 0; i < set.size(); i++){

            MyResponsePoint M = set.get(i);

            if (pointLocation(P, B, M) == 1){
            	leftSetPB.add(M);
            }
        }

        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);
    }

    public double pointLocation(MyResponsePoint A, MyResponsePoint B, MyResponsePoint P) {

        double cp1 = (B.getCenterX() - A.getCenterX()) * (P.getCenterY() - A.getCenterY()) - (B.getCenterY() - A.getCenterY()) * (P.getCenterX() - A.getCenterX());

        if (cp1 > 0)

            return 1;

        else if (cp1 == 0)

            return 0;

        else

            return -1;

    }
  }
