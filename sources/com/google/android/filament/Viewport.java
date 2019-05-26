package com.google.android.filament;

import android.support.annotation.IntRange;

public class Viewport {
    public int bottom;
    @IntRange(from = 0)
    public int height;
    public int left;
    @IntRange(from = 0)
    public int width;

    public Viewport(int left, int bottom, @IntRange(from = 0) int width, @IntRange(from = 0) int height) {
        this.left = left;
        this.bottom = bottom;
        this.width = width;
        this.height = height;
    }
}
