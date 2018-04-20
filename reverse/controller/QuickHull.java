/*
 * Decompiled with CFR 0_123.
 */
package controller;

import java.util.ArrayList;
import model.MyPoint;

public class QuickHull {
    public ArrayList<MyPoint> quickHull(ArrayList<MyPoint> points) {
        ArrayList<MyPoint> convexHull = new ArrayList<MyPoint>();
        if (points.size() < 3) {
            return (ArrayList)points.clone();
        }
        int minPoint = -1;
        int maxPoint = -1;
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        int i = 0;
        while (i < points.size()) {
            if (points.get(i).getCenterX() < minX) {
                minX = points.get(i).getCenterX();
                minPoint = i;
            }
            if (points.get(i).getCenterX() > maxX) {
                maxX = points.get(i).getCenterX();
                maxPoint = i;
            }
            ++i;
        }
        MyPoint A = points.get(minPoint);
        MyPoint B = points.get(maxPoint);
        convexHull.add(A);
        convexHull.add(B);
        points.remove(A);
        points.remove(B);
        ArrayList<MyPoint> leftSet = new ArrayList<MyPoint>();
        ArrayList<MyPoint> rightSet = new ArrayList<MyPoint>();
        int i2 = 0;
        while (i2 < points.size()) {
            MyPoint p = points.get(i2);
            if (this.pointLocation(A, B, p) == -1.0) {
                leftSet.add(p);
            } else if (this.pointLocation(A, B, p) == 1.0) {
                rightSet.add(p);
            }
            ++i2;
        }
        this.hullSet(A, B, rightSet, convexHull);
        this.hullSet(B, A, leftSet, convexHull);
        return convexHull;
    }

    public double distance(MyPoint A, MyPoint B, MyPoint C) {
        double ABx = B.getCenterX() - A.getCenterX();
        double ABy = B.getCenterY() - A.getCenterY();
        double num = ABx * (A.getCenterY() - C.getCenterY()) - ABy * (A.getCenterX() - C.getCenterX());
        return Math.abs(num);
    }

    public void hullSet(MyPoint A, MyPoint B, ArrayList<MyPoint> set, ArrayList<MyPoint> hull) {
        int insertPosition = hull.indexOf(B);
        if (set.size() == 0) {
            return;
        }
        if (set.size() == 1) {
            MyPoint p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        double dist = Double.MIN_VALUE;
        int furthestPoint = -1;
        int i = 0;
        while (i < set.size()) {
            MyPoint p = set.get(i);
            double distance = this.distance(A, B, p);
            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }
            ++i;
        }
        MyPoint P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);
        ArrayList<MyPoint> leftSetAP = new ArrayList<MyPoint>();
        int i2 = 0;
        while (i2 < set.size()) {
            MyPoint M = set.get(i2);
            if (this.pointLocation(A, P, M) == 1.0) {
                leftSetAP.add(M);
            }
            ++i2;
        }
        ArrayList<MyPoint> leftSetPB = new ArrayList<MyPoint>();
        int i3 = 0;
        while (i3 < set.size()) {
            MyPoint M = set.get(i3);
            if (this.pointLocation(P, B, M) == 1.0) {
                leftSetPB.add(M);
            }
            ++i3;
        }
        this.hullSet(A, P, leftSetAP, hull);
        this.hullSet(P, B, leftSetPB, hull);
    }

    public double pointLocation(MyPoint A, MyPoint B, MyPoint P) {
        double cp1 = (B.getCenterX() - A.getCenterX()) * (P.getCenterY() - A.getCenterY()) - (B.getCenterY() - A.getCenterY()) * (P.getCenterX() - A.getCenterX());
        if (cp1 > 0.0) {
            return 1.0;
        }
        if (cp1 == 0.0) {
            return 0.0;
        }
        return -1.0;
    }
}

