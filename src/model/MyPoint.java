/*
 * Decompiled with CFR 0_123.
 */
package model;

import controller.UtilityClass;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import model.dialogWindow.group.GroupModel;

public class MyPoint
extends Ellipse2D
implements Serializable {
    static final long serialVersionUID = 8581091178539691643L;
    private double x;
    private double y;
    private double z;
    private double height = 0.0;
    private double widht = 0.0;
    private double amplitude;
    private double latency;
    private int pixelValue;
    private int minSize = 5;
    private GroupModel group;
    private boolean ignoreSize = true;

    public MyPoint(double x, double y, double height, double width, int pixelValue) {
        this.x = x;
        this.y = y;
        this.pixelValue = pixelValue;
        if (!this.ignoreSize) {
            this.height = height;
            this.widht = width;
        }
        if (this.height < (double)this.minSize) {
            this.height = this.minSize;
        }
        if (this.widht < (double)this.minSize) {
            this.widht = this.minSize;
        }
    }

    public MyPoint(double x, double y, double radius, int pixelValue) {
        this(x, y, radius, radius, pixelValue);
    }

    public MyPoint(double x, double y, double radius) {
        this(x, y, radius, radius, 0);
    }

    public MyPoint(double x, double y) {
        this(x, y, 0.0);
    }

    public MyPoint() {
        this(0.0, 0.0);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(this.getX() - 10.0, this.getY() - 10.0, this.getWidth() + 10.0, this.getHeight() + 10.0);
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public double getWidth() {
        return this.widht;
    }

    public void setActive(boolean active) {
        if(active){
            this.x-=widht/2;
            this.y-=height/2;
            this.height*=2;
            this.widht*=2;
        }else{
	    this.height/=2;
	    this.widht/=2;
            this.x+=widht/2;
            this.y+=height/2;
        }
    } 

    @Override
    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return this.z;
    }

    public int getPixelValue() {
        return this.pixelValue;
    }

    public void setPixelValue(int pixelValue) {
        this.pixelValue = pixelValue;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public void calculateAmplitude(int maxValue, int minValue, int maxResponse, int minResponse) {
        this.amplitude = minResponse + (maxResponse - minResponse) / (maxValue - minValue) * (this.pixelValue - minValue);
    }

    public double getAmplitude() {
        return this.amplitude;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public double getLatency() {
        return this.latency;
    }

    @Override
    public boolean isEmpty() {
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
        return Math.abs(ret);
    }

    public String toString() {
        return "x: " + this.getCenterX() + ", y: " + this.getCenterY();
    }

    public String exportPoint() {
        return String.valueOf(this.getX()) + "," + this.getY() + "," + this.getHeight() + "," + this.getGroup().getName();
    }

    public void importPoint(String in) {
        String[] tokens = in.split(",");
        int i = 0;
        while (i < tokens.length) {
            this.setX(UtilityClass.stringToDouble(tokens[0]));
            this.setY(UtilityClass.stringToDouble(tokens[1]));
            this.height = UtilityClass.stringToDouble(tokens[2]);
            ++i;
        }
    }
}

