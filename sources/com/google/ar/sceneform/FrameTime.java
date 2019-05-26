package com.google.ar.sceneform;

public class FrameTime {
    private static final float NANOSECONDS_TO_SECONDS = 1.0E-9f;
    private long deltaNanoseconds = 0;
    private long lastNanoTime = 0;

    FrameTime() {
    }

    public float getDeltaSeconds() {
        return ((float) this.deltaNanoseconds) * NANOSECONDS_TO_SECONDS;
    }

    public float getStartSeconds() {
        return ((float) this.lastNanoTime) * NANOSECONDS_TO_SECONDS;
    }

    void update(long j) {
        this.deltaNanoseconds = j - this.lastNanoTime;
        this.lastNanoTime = j;
    }
}
