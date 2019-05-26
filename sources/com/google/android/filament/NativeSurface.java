package com.google.android.filament;

public class NativeSurface {
    private final int mHeight;
    private final long mNativeObject;
    private final int mWidth;

    private static native long nCreateSurface(int i, int i2);

    private static native void nDestroySurface(long j);

    public NativeSurface(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        this.mNativeObject = nCreateSurface(width, height);
    }

    public void dispose() {
        nDestroySurface(this.mNativeObject);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public long getNativeObject() {
        return this.mNativeObject;
    }
}
