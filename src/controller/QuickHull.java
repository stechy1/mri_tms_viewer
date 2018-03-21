package controller;

import java.util.ArrayList;

import model.MyPoint;

public class QuickHull{

	
	
	
	public ArrayList<MyPoint> quickHull(ArrayList<MyPoint> points){

        ArrayList<MyPoint> convexHull = new ArrayList<MyPoint>();

        if (points.size() < 3)
        	return (ArrayList<MyPoint>) points.clone();

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

        MyPoint A = points.get(minPoint);
        MyPoint B = points.get(maxPoint);

        convexHull.add(A);
        convexHull.add(B);

        points.remove(A);
        points.remove(B);

        ArrayList<MyPoint> leftSet = new ArrayList<MyPoint>();
        ArrayList<MyPoint> rightSet = new ArrayList<MyPoint>();

        for (int i = 0; i < points.size(); i++){
        	
        	MyPoint p = points.get(i);
        	if (pointLocation(A, B, p) == -1)
        		leftSet.add(p);
        	else if (pointLocation(A, B, p) == 1)
        		rightSet.add(p);
        }

        hullSet(A, B, rightSet, convexHull);
        hullSet(B, A, leftSet, convexHull);

        return convexHull;

    }

    public double distance(MyPoint A, MyPoint B, MyPoint C){

        double ABx = B.getCenterX() - A.getCenterX();
        double ABy = B.getCenterY() - A.getCenterY();
        double num = ABx * (A.getCenterY() - C.getCenterY()) - ABy * (A.getCenterX() - C.getCenterX());
        
        return Math.abs(num);
    }

    public void hullSet(MyPoint A, MyPoint B, ArrayList<MyPoint> set, ArrayList<MyPoint> hull) {

        int insertPosition = hull.indexOf(B);

        if (set.size() == 0)
        	return;

        if (set.size() == 1){

            MyPoint p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }

        double dist = Double.MIN_VALUE;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++)

        {

            MyPoint p = set.get(i);

            double distance = distance(A, B, p);

            if (distance > dist)

            {
            	dist = distance;
            	furthestPoint = i;
            }

        }

        MyPoint P = set.get(furthestPoint);

        set.remove(furthestPoint);

        hull.add(insertPosition, P);

 
        // Determine who's to the left of AP

        ArrayList<MyPoint> leftSetAP = new ArrayList<MyPoint>();

        for (int i = 0; i < set.size(); i++)

        {

            MyPoint M = set.get(i);

            if (pointLocation(A, P, M) == 1)

            {
            	leftSetAP.add(M);
            }
        }

 

        // Determine who's to the left of PB

        ArrayList<MyPoint> leftSetPB = new ArrayList<MyPoint>();

        for (int i = 0; i < set.size(); i++){

            MyPoint M = set.get(i);

            if (pointLocation(P, B, M) == 1){
            	leftSetPB.add(M);
            }
        }

        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);
    }

    public double pointLocation(MyPoint A, MyPoint B, MyPoint P) {

        double cp1 = (B.getCenterX() - A.getCenterX()) * (P.getCenterY() - A.getCenterY()) - (B.getCenterY() - A.getCenterY()) * (P.getCenterX() - A.getCenterX());

        if (cp1 > 0)

            return 1;

        else if (cp1 == 0)

            return 0;

        else

            return -1;

    }
  }