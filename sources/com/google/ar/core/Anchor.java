package com.google.ar.core;

import com.google.ar.core.exceptions.FatalException;

public class Anchor {
    long nativeHandle;
    private final Session session;

    public enum CloudAnchorState {
        NONE(0),
        TASK_IN_PROGRESS(1),
        SUCCESS(2),
        ERROR_INTERNAL(-1),
        ERROR_NOT_AUTHORIZED(-2),
        ERROR_SERVICE_UNAVAILABLE(-3),
        ERROR_RESOURCE_EXHAUSTED(-4),
        ERROR_HOSTING_DATASET_PROCESSING_FAILED(-5),
        ERROR_CLOUD_ID_NOT_FOUND(-6),
        ERROR_RESOLVING_LOCALIZATION_NO_MATCH(-7),
        ERROR_RESOLVING_SDK_VERSION_TOO_OLD(-8),
        ERROR_RESOLVING_SDK_VERSION_TOO_NEW(-9);
        
        final int nativeCode;

        private CloudAnchorState(int i) {
            this.nativeCode = i;
        }

        public static CloudAnchorState forNumber(int i) {
            for (CloudAnchorState cloudAnchorState : values()) {
                if (cloudAnchorState.nativeCode == i) {
                    return cloudAnchorState;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(63);
            stringBuilder.append("Unexpected value for native CloudAnchorState, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }

        public final boolean isError() {
            return this.nativeCode < 0;
        }
    }

    protected Anchor() {
        this.session = null;
        this.nativeHandle = 0;
    }

    Anchor(long j, Session session) {
        this.session = session;
        this.nativeHandle = j;
    }

    private native void nativeDetach(long j, long j2);

    private native String nativeGetCloudAnchorId(long j, long j2);

    private native int nativeGetCloudAnchorState(long j, long j2);

    private native Pose nativeGetPose(long j, long j2);

    private native int nativeGetTrackingState(long j, long j2);

    private static native void nativeReleaseAnchor(long j);

    public void detach() {
        nativeDetach(this.session.nativeHandle, this.nativeHandle);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (((Anchor) obj).nativeHandle == this.nativeHandle) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeReleaseAnchor(this.nativeHandle);
        }
        super.finalize();
    }

    public String getCloudAnchorId() {
        return nativeGetCloudAnchorId(this.session.nativeHandle, this.nativeHandle);
    }

    public CloudAnchorState getCloudAnchorState() {
        return CloudAnchorState.forNumber(nativeGetCloudAnchorState(this.session.nativeHandle, this.nativeHandle));
    }

    public Pose getPose() {
        return nativeGetPose(this.session.nativeHandle, this.nativeHandle);
    }

    public TrackingState getTrackingState() {
        return TrackingState.forNumber(nativeGetTrackingState(this.session.nativeHandle, this.nativeHandle));
    }

    public int hashCode() {
        return Long.valueOf(this.nativeHandle).hashCode();
    }
}
