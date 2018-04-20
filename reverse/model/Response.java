/*
 * Decompiled with CFR 0_123.
 */
package model;

public class Response {
    private double mepMax;

    public Response(double mepMax) {
        this.mepMax = mepMax;
    }

    public Response() {
        this(0.0);
    }

    public double getMepMax() {
        return this.mepMax;
    }

    public void setMepMax(double mepMax) {
        this.mepMax = mepMax;
    }

    public String toString() {
        return String.valueOf(this.mepMax);
    }
}

