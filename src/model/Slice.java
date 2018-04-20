/*
 * Decompiled with CFR 0_123.
 */
package model;

import java.io.Serializable;

public class Slice
implements Comparable<Slice>,
Serializable {
    private int index;
    private double area;

    public Slice(int index, double area) {
        this.index = index;
        this.area = area;
    }

    public int getSliceIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getArea() {
        return this.area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public int compareTo(Slice o) {
        return Integer.compare(this.index, o.getSliceIndex());
    }
}

