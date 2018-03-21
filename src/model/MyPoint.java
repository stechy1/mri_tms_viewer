package model;

import java.awt.geom.Ellipse2D;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import controller.Configuration;
import model.dialogWindow.group.GroupModel;

import static controller.Configuration.SELECTION_TRESSHOLD;
import static controller.UtilityClass.*;

public class MyPoint extends Ellipse2D implements Serializable {

    private double x, y, z, height = 0, widht = 0;
    private double amplitude, latency;

    private int minSize = 5;

    private GroupModel group;

    private boolean ignoreSize = true;


    public MyPoint(double x, double y, double height, double width) {
        super();
        this.x = x;
        this.y = y;

        if (!ignoreSize) {
            this.height = height;
            this.widht = width;
        }

		if (this.height < minSize) {
			this.height = minSize;
		}
		if (this.widht < minSize) {
			this.widht = minSize;
		}
    }

    public MyPoint(double x, double y, double radius) {
        this(x, y, radius, radius);
    }

    public MyPoint(double x, double y) {
        this(x, y, 0);
    }

    public MyPoint() {
        this(0, 0);
    }


    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(this.getX() - SELECTION_TRESSHOLD,
            this.getY() - SELECTION_TRESSHOLD,
            this.getWidth() + SELECTION_TRESSHOLD, this.getHeight() + SELECTION_TRESSHOLD);
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return widht;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.widht = w;
        this.height = h;
    }

    public GroupModel getGroup() {
        return this.group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
    }

    public double distance(MyPoint point) {

        double x = this.getCenterX() - point.getCenterX();
        double y = this.getCenterY() - point.getCenterY();

        double ret = Math.hypot(x, y);

        //double ret = Math.pow(x*x + y*y, 1/2);

        return Math.abs(ret);
    }

    @Override
    public String toString() {
        return "x: " + this.getCenterX() + ", y: " + this.getCenterY();
    }

    public String exportPoint() {
        return this.getX() + "," + this.getY() + "," + this.getHeight() + "," +
            this.getGroup().getName();

    }

    public void importPoint(String in) {
        String[] tokens = in.split(Configuration.GROUP_COMMA);
        for (int i = 0; i < tokens.length; i++) {
            this.setX(stringToDouble(tokens[0]));
            this.setY(stringToDouble(tokens[1]));
            this.height = stringToDouble(tokens[2]);
        }
    }


}
