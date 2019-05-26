package com.google.ar.core;

import com.google.ar.core.annotations.UsedByNative;
import java.util.Collection;

@UsedByNative("trackable_base_jni.cc")
class TrackableBase implements Trackable {
    final long nativeHandle;
    final Session session;

    TrackableBase(long j, Session session) {
        this.session = session;
        this.nativeHandle = j;
    }

    static int internalGetType(long j, long j2) {
        return nativeGetType(j, j2);
    }

    static void internalReleaseNativeHandle(long j) {
        nativeReleaseTrackable(j);
    }

    private native long nativeCreateAnchor(long j, long j2, Pose pose);

    private native long[] nativeGetAnchors(long j, long j2);

    private native int nativeGetTrackingState(long j, long j2);

    private static native int nativeGetType(long j, long j2);

    private static native void nativeReleaseTrackable(long j);

    public Anchor createAnchor(Pose pose) {
        return new Anchor(nativeCreateAnchor(this.session.nativeHandle, this.nativeHandle, pose), this.session);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (((TrackableBase) obj).nativeHandle == this.nativeHandle) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeReleaseTrackable(this.nativeHandle);
        }
        super.finalize();
    }

    public Collection<Anchor> getAnchors() {
        return this.session.convertNativeAnchorsToCollection(nativeGetAnchors(this.session.nativeHandle, this.nativeHandle));
    }

    public TrackingState getTrackingState() {
        return TrackingState.forNumber(nativeGetTrackingState(this.session.nativeHandle, this.nativeHandle));
    }

    public int hashCode() {
        return Long.valueOf(this.nativeHandle).hashCode();
    }
}
