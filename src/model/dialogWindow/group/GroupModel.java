package model.dialogWindow.group;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


import controller.Configuration;
import model.MyPoint;

import static controller.UtilityClass.*;
import static controller.Configuration.*;

public class GroupModel implements Serializable {

    private int id;
    private String name;
    private MyPoint centroid;
    private Color color;

    private ArrayList<MyPoint> points;


    public GroupModel(String name, MyPoint centroid, Color color) {
        this.points = new ArrayList<MyPoint>();
        this.name = name;
        this.color = color;
        this.centroid = (MyPoint) centroid.clone();
    }

    public GroupModel(String name, MyPoint centroid) {
        this(name, centroid, null);
        Random ran = new Random();
        this.color = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
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
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        if (this.name.equals(Configuration.UNASSIGN_GROUP)) {
            return Configuration.UNASSIGN_COLOR;
        }

        if (this.name.equals(Configuration.IGNORE_GROUP)) {
            return Configuration.IGNORE_COLOR;
        }

        if (this.color != null) {
            return color;
        } else {
            return new Color(250, 100, 100);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyPoint getCentroid() {
        return centroid;
    }

    public void setCentroid(MyPoint centroid) {
        this.centroid = centroid;
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<MyPoint> points) {
        this.points = points;
    }

    public ArrayList<MyPoint> getPointFromLayer(int layer) {
        ArrayList<MyPoint> list = new ArrayList<MyPoint>();
        for (MyPoint myPoint : points) {
            if (myPoint.getZ() == layer) {
                list.add(myPoint);
            }
        }
        return list;
    }

    public void computeCentroids() {
        if (this.points != null) {
            if (this.points.size() != 0) {
                int x = 0, y = 0;
                for (MyPoint myPoint : points) {
                    x += myPoint.getCenterX();
                    y += myPoint.getCenterY();
                }
                this.centroid = new MyPoint(x / points.size(), y / points.size());
            } else {
                if (this.centroid == null) {
                    this.centroid = new MyPoint();
                }
            }
        }
    }

    public String exportGroup() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.name + Configuration.GROUP_SEMICOLON);
        sb.append(this.centroid.getCenterX() + "," + this.centroid.getCenterY() + GROUP_SEMICOLON);
        sb.append(this.color.getRed() + "," + this.color.getGreen() + "," + this.color.getBlue()
            + GROUP_SEMICOLON);

        for (MyPoint myPoint : this.points) {
            sb.append(myPoint.exportPoint());
            sb.append(GROUP_TILDE);
        }

        String ret = sb.toString();
        return ret.substring(0, ret.length() - 1);
    }

    public void importGroup(String line) {
        String[] tokens = line.split(GROUP_SEMICOLON);
        this.name = tokens[0];
        this.centroid = makeCentroid(tokens[1]);
        this.color = makeColor(tokens[2]);
        if (tokens.length > 3) {
            this.points = makePoint(tokens[3]);
        }
    }

    private ArrayList<MyPoint> makePoint(String in) {
        ArrayList<MyPoint> loadedPoints = new ArrayList<MyPoint>();

        String[] retPoints = in.split(GROUP_TILDE);
        for (int i = 0; i < retPoints.length; i++) {
            MyPoint loadedPoint = new MyPoint();
            loadedPoint.setGroup(this);
            loadedPoint.importPoint(retPoints[i]);
            loadedPoints.add(loadedPoint);
        }

        return loadedPoints;
    }

    private Color makeColor(String color) {
        String[] col = color.split(GROUP_COMMA);
        return new Color(stringToInt(col[0]), stringToInt(col[1]), stringToInt(col[2]));
    }

    private MyPoint makeCentroid(String token) {
        String[] coor = token.split(Configuration.GROUP_COMMA);
        return new MyPoint(stringToDouble(coor[0]), stringToDouble(coor[1]));
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
