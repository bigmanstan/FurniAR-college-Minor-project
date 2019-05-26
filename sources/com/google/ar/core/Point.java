package com.google.ar.core;

import com.google.ar.core.exceptions.FatalException;
import java.util.Collection;

public class Point extends TrackableBase {
    static final int AR_POINT_ORIENTATION_ESTIMATED_SURFACE_NORMAL = 1;
    static final int AR_POINT_ORIENTATION_INITIALIZED_TO_IDENTITY = 0;

    public enum OrientationMode {
        INITIALIZED_TO_IDENTITY(0),
        ESTIMATED_SURFACE_NORMAL(1);
        
        private final int nativeCode;

        private OrientationMode(int i) {
            this.nativeCode = i;
        }

        static OrientationMode fromNumber(int i) {
            for (OrientationMode orientationMode : values()) {
                if (orientationMode.nativeCode == i) {
                    return orientationMode;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(69);
            stringBuilder.append("Unexpected value for native Point Orientation Mode, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    protected Point() {
        super(0, null);
    }

    Point(long j, Session session) {
        super(j, session);
    }

    private native int nativeGetOrientationMode(long j, long j2);

    private native Pose nativeGetPose(long j, long j2);

    public /* bridge */ /* synthetic */ Anchor createAnchor(Pose pose) {
        return super.createAnchor(pose);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ Collection getAnchors() {
        return super.getAnchors();
    }

    public OrientationMode getOrientationMode() {
        return OrientationMode.fromNumber(nativeGetOrientationMode(this.session.nativeHandle, this.nativeHandle));
    }

    Pose getPose() {
        return nativeGetPose(this.session.nativeHandle, this.nativeHandle);
    }

    public /* bridge */ /* synthetic */ TrackingState getTrackingState() {
        return super.getTrackingState();
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
