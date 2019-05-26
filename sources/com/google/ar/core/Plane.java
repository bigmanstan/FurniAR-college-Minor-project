package com.google.ar.core;

import com.google.ar.core.exceptions.FatalException;
import java.nio.FloatBuffer;
import java.util.Collection;

public class Plane extends TrackableBase {
    static final int AR_PLANE_HORIZONTAL_DOWNWARD_FACING = 1;
    static final int AR_PLANE_HORIZONTAL_UPWARD_FACING = 0;
    static final int AR_PLANE_VERTICAL = 2;

    public enum Type {
        HORIZONTAL_UPWARD_FACING(0),
        HORIZONTAL_DOWNWARD_FACING(1),
        VERTICAL(2);
        
        final int nativeCode;

        private Type(int i) {
            this.nativeCode = i;
        }

        static Type forNumber(int i) {
            for (Type type : values()) {
                if (type.nativeCode == i) {
                    return type;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(57);
            stringBuilder.append("Unexpected value for native Plane.Type, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    protected Plane() {
        super(0, null);
    }

    Plane(long j, Session session) {
        super(j, session);
    }

    private native long nativeAcquireSubsumedBy(long j, long j2);

    private native Pose nativeGetCenterPose(long j, long j2);

    private native float nativeGetExtentX(long j, long j2);

    private native float nativeGetExtentZ(long j, long j2);

    private native float[] nativeGetPolygon(long j, long j2);

    private native int nativeGetType(long j, long j2);

    private native boolean nativeIsPoseInExtents(long j, long j2, Pose pose);

    private native boolean nativeIsPoseInPolygon(long j, long j2, Pose pose);

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

    public FloatBuffer getPolygon() {
        return FloatBuffer.wrap(nativeGetPolygon(this.session.nativeHandle, this.nativeHandle));
    }

    public Plane getSubsumedBy() {
        long nativeAcquireSubsumedBy = nativeAcquireSubsumedBy(this.session.nativeHandle, this.nativeHandle);
        return nativeAcquireSubsumedBy == 0 ? null : new Plane(nativeAcquireSubsumedBy, this.session);
    }

    public /* bridge */ /* synthetic */ TrackingState getTrackingState() {
        return super.getTrackingState();
    }

    public Type getType() {
        return Type.forNumber(nativeGetType(this.session.nativeHandle, this.nativeHandle));
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public boolean isPoseInExtents(Pose pose) {
        return nativeIsPoseInExtents(this.session.nativeHandle, this.nativeHandle, pose);
    }

    public boolean isPoseInPolygon(Pose pose) {
        return nativeIsPoseInPolygon(this.session.nativeHandle, this.nativeHandle, pose);
    }
}
