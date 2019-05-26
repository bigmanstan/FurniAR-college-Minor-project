package com.google.android.filament;

import android.support.annotation.NonNull;

public class Fence {
    public static final long WAIT_FOR_EVER = -1;
    private long mNativeObject;

    public enum FenceStatus {
        ERROR,
        CONDITION_SATISFIED,
        TIMEOUT_EXPIRED
    }

    public enum Mode {
        FLUSH,
        DONT_FLUSH
    }

    public enum Type {
        SOFT,
        HARD
    }

    private static native int nWait(long j, int i, long j2);

    private static native int nWaitAndDestroy(long j, int i);

    Fence(long nativeFence) {
        this.mNativeObject = nativeFence;
    }

    public FenceStatus wait(@NonNull Mode mode, long timeoutNanoSeconds) {
        switch (nWait(getNativeObject(), mode.ordinal(), timeoutNanoSeconds)) {
            case -1:
                return FenceStatus.ERROR;
            case 0:
                return FenceStatus.CONDITION_SATISFIED;
            case 1:
                return FenceStatus.TIMEOUT_EXPIRED;
            default:
                return FenceStatus.ERROR;
        }
    }

    public static FenceStatus waitAndDestroy(@NonNull Fence fence, @NonNull Mode mode) {
        switch (nWaitAndDestroy(fence.getNativeObject(), mode.ordinal())) {
            case -1:
                return FenceStatus.ERROR;
            case 0:
                return FenceStatus.CONDITION_SATISFIED;
            default:
                return FenceStatus.ERROR;
        }
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Fence");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
