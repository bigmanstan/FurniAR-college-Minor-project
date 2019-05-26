package com.google.ar.sceneform.utilities;

public class MovingAverageMillisecondsTracker {
    private static final double NANOSECONDS_TO_MILLISECONDS = 1.0E-6d;
    private long beginSampleTimestampNano;
    private final Clock clock;
    private MovingAverage movingAverage;
    private final double weight;

    interface Clock {
        /* renamed from: a */
        long mo1605a();
    }

    /* renamed from: com.google.ar.sceneform.utilities.MovingAverageMillisecondsTracker$a */
    private static class C0557a implements Clock {
        private C0557a() {
        }

        /* renamed from: a */
        public final long mo1605a() {
            return System.nanoTime();
        }
    }

    public MovingAverageMillisecondsTracker() {
        this(0.8999999761581421d);
    }

    public MovingAverageMillisecondsTracker(double d) {
        this.weight = d;
        this.clock = new C0557a();
    }

    public MovingAverageMillisecondsTracker(Clock clock) {
        this(clock, 0.8999999761581421d);
    }

    public MovingAverageMillisecondsTracker(Clock clock, double d) {
        this.weight = d;
        this.clock = clock;
    }

    public void beginSample() {
        this.beginSampleTimestampNano = this.clock.mo1605a();
    }

    public void endSample() {
        double a = ((double) (this.clock.mo1605a() - this.beginSampleTimestampNano)) * NANOSECONDS_TO_MILLISECONDS;
        if (this.movingAverage == null) {
            this.movingAverage = new MovingAverage(a, this.weight);
        } else {
            this.movingAverage.addSample(a);
        }
    }

    public double getAverage() {
        return this.movingAverage != null ? this.movingAverage.getAverage() : 0.0d;
    }
}
