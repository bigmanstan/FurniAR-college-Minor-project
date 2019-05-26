package com.google.ar.core;

public class CameraIntrinsics {
    long nativeHandle = 0;
    final Session session;

    CameraIntrinsics(long j, Session session) {
        this.nativeHandle = j;
        this.session = session;
    }

    private native void nativeDestroyCameraIntrinsics(long j);

    private native void nativeGetFocalLength(long j, long j2, float[] fArr, int i);

    private native void nativeGetImageDimensions(long j, long j2, int[] iArr, int i);

    private native void nativeGetPrincipalPoint(long j, long j2, float[] fArr, int i);

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroyCameraIntrinsics(this.nativeHandle);
        }
        super.finalize();
    }

    public void getFocalLength(float[] fArr, int i) {
        nativeGetFocalLength(this.session.nativeHandle, this.nativeHandle, fArr, i);
    }

    public float[] getFocalLength() {
        float[] fArr = new float[2];
        getFocalLength(fArr, 0);
        return fArr;
    }

    public void getImageDimensions(int[] iArr, int i) {
        nativeGetImageDimensions(this.session.nativeHandle, this.nativeHandle, iArr, i);
    }

    public int[] getImageDimensions() {
        int[] iArr = new int[2];
        getImageDimensions(iArr, 0);
        return iArr;
    }

    public void getPrincipalPoint(float[] fArr, int i) {
        nativeGetPrincipalPoint(this.session.nativeHandle, this.nativeHandle, fArr, i);
    }

    public float[] getPrincipalPoint() {
        float[] fArr = new float[2];
        getPrincipalPoint(fArr, 0);
        return fArr;
    }
}
