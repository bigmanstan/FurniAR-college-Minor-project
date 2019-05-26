package com.google.ar.core;

import java.util.Collection;

public class AugmentedImage extends TrackableBase {
    AugmentedImage(long j, Session session) {
        super(j, session);
    }

    private native Pose nativeGetCenterPose(long j, long j2);

    private native float nativeGetExtentX(long j, long j2);

    private native float nativeGetExtentZ(long j, long j2);

    private native int nativeGetIndex(long j, long j2);

    private native String nativeGetName(long j, long j2);

    public /* bridge */ /* synthetic */ Anchor createAnchor(Pose pose) {
        return super.createAnchor(pose);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ Collection getAnchors() {
        return super.getAnchors();
    }

    public Pose getCenterPose() {
        return nativeGetCenterPose(this.session.nativeHandle, this.nativeHandle);
    }

    public float getExtentX() {
        return nativeGetExtentX(this.session.nativeHandle, this.nativeHandle);
    }

    public float getExtentZ() {
        return nativeGetExtentZ(this.session.nativeHandle, this.nativeHandle);
    }

    public int getIndex() {
        return nativeGetIndex(this.session.nativeHandle, this.nativeHandle);
    }

    public String getName() {
        return nativeGetName(this.session.nativeHandle, this.nativeHandle);
    }

    public /* bridge */ /* synthetic */ TrackingState getTrackingState() {
        return super.getTrackingState();
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
