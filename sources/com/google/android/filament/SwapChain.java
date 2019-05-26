package com.google.android.filament;

import android.support.annotation.NonNull;

public class SwapChain {
    public static final long CONFIG_TRANSPARENT = 1;
    private long mNativeObject;
    private final Object mSurface;

    SwapChain(long nativeSwapChain, @NonNull Object surface) {
        this.mNativeObject = nativeSwapChain;
        this.mSurface = surface;
    }

    @NonNull
    public Object getNativeWindow() {
        return this.mSurface;
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed SwapChain");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
