package com.google.ar.sceneform.utilities;

public class MovingAverage {
    public static final double DEFAULT_WEIGHT = 0.8999999761581421d;
    private double average;
    private final double weight;

    public MovingAverage(double d) {
        this(d, 0.8999999761581421d);
    }

    public MovingAverage(double d, double d2) {
        this.average = d;
        this.weight = d2;
    }

    public void addSample(double d) {
        this.average = (this.weight * this.average) + ((1.0d - this.weight) * d);
    }

    public double getAverage() {
        return this.average;
    }
}
