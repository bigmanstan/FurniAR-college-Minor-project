package com.google.ar.core;

import android.util.Size;

public class CameraConfig {
    long nativeHandle;
    final Session session;

    protected CameraConfig() {
        this.session = null;
        this.nativeHandle = 0;
    }

    protected CameraConfig(Session session, long j) {
        this.session = session;
        this.nativeHandle = j;
    }

    private static native void nativeDestroyCameraConfig(long j);

    private native void nativeGetImageDimensions(long j, long j2, int[] iArr);

    private native void nativeGetTextureDimensions(long j, long j2, int[] iArr);

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroyCameraConfig(this.nativeHandle);
            this.nativeHandle = 0;
        }
        super.finalize();
    }

    public Size getImageSize() {
        int[] iArr = new int[2];
        nativeGetImageDimensions(this.session.nativeHandle, this.nativeHandle, iArr);
        return new Size(iArr[0], iArr[1]);
    }

    public Size getTextureSize() {
        int[] iArr = new int[2];
        nativeGetTextureDimensions(this.session.nativeHandle, this.nativeHandle, iArr);
        return new Size(iArr[0], iArr[1]);
    }
}
