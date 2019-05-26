package com.google.ar.core;

public class HitResult {
    long nativeHandle;
    private final Session session;

    protected HitResult() {
        this.session = null;
        this.nativeHandle = 0;
    }

    HitResult(long j, Session session) {
        this.session = session;
        this.nativeHandle = j;
    }

    private native long nativeCreateAnchor(long j, long j2);

    private static native void nativeDestroyHitResult(long j);

    private native float nativeGetDistance(long j, long j2);

    private native Pose nativeGetPose(long j, long j2);

    public Anchor createAnchor() {
        return new Anchor(nativeCreateAnchor(this.session.nativeHandle, this.nativeHandle), this.session);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (((HitResult) obj).nativeHandle == this.nativeHandle) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroyHitResult(this.nativeHandle);
        }
        super.finalize();
    }

    public float getDistance() {
        return nativeGetDistance(this.session.nativeHandle, this.nativeHandle);
    }

    public Pose getHitPose() {
        return nativeGetPose(this.session.nativeHandle, this.nativeHandle);
    }

    public Trackable getTrackable() {
        return this.session.createTrackable(nativeAcquireTrackable(this.session.nativeHandle, this.nativeHandle));
    }

    public int hashCode() {
        return Long.valueOf(this.nativeHandle).hashCode();
    }

    native long nativeAcquireTrackable(long j, long j2);
}
