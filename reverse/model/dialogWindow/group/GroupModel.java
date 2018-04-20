/*
 * Decompiled with CFR 0_123.
 */
package model.dialogWindow.group;

import controller.Configuration;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.GroupVolume;
import model.MyPoint;
import model.Slice;

public class GroupModel
implements Serializable {
    private int id;
    private String name;
    private MyPoint centroid;
    private Color layerColor;
    private Color groupColor;
    private GroupVolume volume;
    private ArrayList<MyPoint> points = new ArrayList();

    public GroupModel(String name, MyPoint centroid, Color color) {
        this.name = name;
        this.layerColor = color;
        this.groupColor = color;
        this.centroid = (MyPoint)centroid.clone();
        this.volume = new GroupVolume();
    }

    public GroupModel(String name, MyPoint centroid) {
        this(name, centroid, null);
        Random ran = new Random();
        this.layerColor = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
    }

    public GroupModel(String name) {
        this(name, new MyPoint());
    }

    public GroupModel() {
        this("unknown");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGroupColor(Color groupColor) {
        this.groupColor = groupColor;
    }

    public Color getGroupColor() {
        if (this.name.equals("nep\u0159i\u0159azen\u00e9 body")) {
            return Configuration.UNASSIGN_COLOR;
        }
        if (this.name.equals("ignorovan\u00e9 body")) {
            return Configuration.IGNORE_COLOR;
        }
        if (this.layerColor != null) {
            return this.layerColor;
        }
        return new Color(250, 100, 100);
    }

    public void setLayerColor(Color color) {
        this.layerColor = color;
    }

    public Color getLayerColor() {
        if (this.name.equals("nep\u0159i\u0159azen\u00e9 body")) {
            return Configuration.UNASSIGN_COLOR;
        }
        if (this.name.equals("ignorovan\u00e9 body")) {
            return Configuration.IGNORE_COLOR;
        }
        if (this.layerColor != null) {
            return this.layerColor;
        }
        return new Color(250, 100, 100);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyPoint getCentroid() {
        return this.centroid;
    }

    public void setCentroid(MyPoint centroid) {
        this.centroid = centroid;
    }

    public ArrayList<MyPoint> getPoints() {
        return this.points;
    }

    public void setPoints(ArrayList<MyPoint> points) {
        this.points = points;
    }

    public void setArea(int slice, ArrayList<MyPoint> points) {
        this.volume.updateArea(slice, this.computeArea(points));
    }

    public double computeArea(ArrayList<MyPoint> points) {
        double area = 0.0;
        int i = 0;
        while (i < points.size()) {
            MyPoint fromPoint = points.get(i % points.size());
            MyPoint toPoint = points.get((i + 1) % points.size());
            MyPoint overPoint = points.get((i + 2) % points.size());
            double i1_x = toPoint.getCenterX() * Configuration.pixelSpace;
            double i2_y = overPoint.getCenterY() * Configuration.pixelSpace;
            double i0_y = fromPoint.getCenterY() * Configuration.pixelSpace;
            area += i1_x * (i2_y - i0_y) / 2.0;
            ++i;
        }
        area = Math.abs(area);
        return area;
    }

    public double getArea(int layer) {
        for (Slice slice : this.volume.getAreas()) {
            if (slice.getSliceIndex() != layer) continue;
            return slice.getArea();
        }
        return 0.0;
    }

    public ArrayList<MyPoint> getCopyOfPoints() {
        ArrayList<MyPoint> list = new ArrayList<MyPoint>();
        for (MyPoint myPoint : this.points) {
            list.add(myPoint);
        }
        return list;
    }

    public ArrayList<MyPoint> getPointFromLayer(int layer) {
        ArrayList<MyPoint> list = new ArrayList<MyPoint>();
        for (MyPoint myPoint : this.points) {
            if (myPoint.getZ() != (double)layer) continue;
            list.add(myPoint);
        }
        return list;
    }

    public void computeCentroids() {
        if (this.points != null) {
            if (this.points.size() != 0) {
                int x = 0;
                int y = 0;
                for (MyPoint myPoint : this.points) {
                    x = (int)((double)x + myPoint.getCenterX());
                    y = (int)((double)y + myPoint.getCenterY());
                }
                this.centroid = new MyPoint(x / this.points.size(), y / this.points.size());
            } else if (this.centroid == null) {
                this.centroid = new MyPoint();
            }
        }
    }

    public GroupVolume getVolume() {
        return this.volume;
    }

    public String toString() {
        return this.getName();
    }
}

