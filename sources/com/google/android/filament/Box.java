package com.google.android.filament;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

public class Box {
    private final float[] mCenter;
    private final float[] mHalfExtent;

    public Box() {
        this.mCenter = new float[3];
        this.mHalfExtent = new float[3];
    }

    public Box(float centerX, float centerY, float centerZ, float halfExtentX, float halfExtentY, float halfExtentZ) {
        this.mCenter = new float[3];
        this.mHalfExtent = new float[3];
        this.mCenter[0] = centerX;
        this.mCenter[1] = centerY;
        this.mCenter[2] = centerZ;
        this.mHalfExtent[0] = halfExtentX;
        this.mHalfExtent[1] = halfExtentY;
        this.mHalfExtent[2] = halfExtentZ;
    }

    public Box(@Size(min = 3) @NonNull float[] center, @Size(min = 3) @NonNull float[] halfExtent) {
        this.mCenter = new float[3];
        this.mHalfExtent = new float[3];
        this.mCenter[0] = center[0];
        this.mCenter[1] = center[1];
        this.mCenter[2] = center[2];
        this.mHalfExtent[0] = halfExtent[0];
        this.mHalfExtent[1] = halfExtent[1];
        this.mHalfExtent[2] = halfExtent[2];
    }

    public void setCenter(float centerX, float centerY, float centerZ) {
        this.mCenter[0] = centerX;
        this.mCenter[1] = centerY;
        this.mCenter[2] = centerZ;
    }

    public void setHalfExtent(float halfExtentX, float halfExtentY, float halfExtentZ) {
        this.mHalfExtent[0] = halfExtentX;
        this.mHalfExtent[1] = halfExtentY;
        this.mHalfExtent[2] = halfExtentZ;
    }

    @Size(min = 3)
    @NonNull
    public float[] getCenter() {
        return this.mCenter;
    }

    @Size(min = 3)
    @NonNull
    public float[] getHalfExtent() {
        return this.mHalfExtent;
    }
}
