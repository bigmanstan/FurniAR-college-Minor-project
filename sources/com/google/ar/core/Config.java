package com.google.ar.core;

import com.google.ar.core.exceptions.FatalException;

public class Config {
    static final int AR_FOCUS_MODE_AUTO = 1;
    static final int AR_FOCUS_MODE_FIXED = 0;
    static final int AR_LIGHT_ESTIMATION_MODE_AMBIENT_INTENSITY = 1;
    static final int AR_LIGHT_ESTIMATION_MODE_DISABLED = 0;
    static final int AR_PLANE_FINDING_MODE_DISABLED = 0;
    static final int AR_PLANE_FINDING_MODE_HORIZONTAL = 1;
    static final int AR_PLANE_FINDING_MODE_HORIZONTAL_AND_VERTICAL = 3;
    static final int AR_PLANE_FINDING_MODE_VERTICAL = 2;
    static final int AR_UPDATE_MODE_BLOCKING = 0;
    static final int AR_UPDATE_MODE_LATEST_CAMERA_IMAGE = 1;
    private static final String TAG = "ARCore-Config";
    long nativeHandle;
    final Session session;

    public enum CloudAnchorMode {
        DISABLED(0),
        ENABLED(1);
        
        final int nativeCode;

        private CloudAnchorMode(int i) {
            this.nativeCode = i;
        }

        static CloudAnchorMode forNumber(int i) {
            for (CloudAnchorMode cloudAnchorMode : values()) {
                if (cloudAnchorMode.nativeCode == i) {
                    return cloudAnchorMode;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("Unexpected value for native AnchorHostingMode, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    public enum FocusMode {
        FIXED(0),
        AUTO(1);
        
        final int nativeCode;

        private FocusMode(int i) {
            this.nativeCode = i;
        }

        static FocusMode forNumber(int i) {
            for (FocusMode focusMode : values()) {
                if (focusMode.nativeCode == i) {
                    return focusMode;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(56);
            stringBuilder.append("Unexpected value for native FocusMode, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    public enum LightEstimationMode {
        DISABLED(0),
        AMBIENT_INTENSITY(1);
        
        final int nativeCode;

        private LightEstimationMode(int i) {
            this.nativeCode = i;
        }

        static LightEstimationMode forNumber(int i) {
            for (LightEstimationMode lightEstimationMode : values()) {
                if (lightEstimationMode.nativeCode == i) {
                    return lightEstimationMode;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(66);
            stringBuilder.append("Unexpected value for native LightEstimationMode, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    public enum PlaneFindingMode {
        DISABLED(0),
        HORIZONTAL(1),
        VERTICAL(2),
        HORIZONTAL_AND_VERTICAL(3);
        
        final int nativeCode;

        private PlaneFindingMode(int i) {
            this.nativeCode = i;
        }

        static PlaneFindingMode forNumber(int i) {
            for (PlaneFindingMode planeFindingMode : values()) {
                if (planeFindingMode.nativeCode == i) {
                    return planeFindingMode;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(63);
            stringBuilder.append("Unexpected value for native PlaneFindingMode, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    public enum UpdateMode {
        BLOCKING(0),
        LATEST_CAMERA_IMAGE(1);
        
        final int nativeCode;

        private UpdateMode(int i) {
            this.nativeCode = i;
        }

        static UpdateMode forNumber(int i) {
            for (UpdateMode updateMode : values()) {
                if (updateMode.nativeCode == i) {
                    return updateMode;
                }
            }
            StringBuilder stringBuilder = new StringBuilder(57);
            stringBuilder.append("Unexpected value for native UpdateMode, value=");
            stringBuilder.append(i);
            throw new FatalException(stringBuilder.toString());
        }
    }

    protected Config() {
        this.session = null;
        this.nativeHandle = 0;
    }

    public Config(Session session) {
        this.session = session;
        this.nativeHandle = nativeCreate(session.nativeHandle);
    }

    Config(Session session, long j) {
        this.session = session;
        this.nativeHandle = j;
    }

    private static native long nativeCreate(long j);

    private static native void nativeDestroy(long j);

    private native long nativeGetAugmentedImageDatabase(long j, long j2);

    private native int nativeGetCloudAnchorMode(long j, long j2);

    private native int nativeGetFocusMode(long j, long j2);

    private native int nativeGetLightEstimationMode(long j, long j2);

    private native int nativeGetPlaneFindingMode(long j, long j2);

    private native int nativeGetUpdateMode(long j, long j2);

    private native void nativeSetAugmentedImageDatabase(long j, long j2, long j3);

    private native void nativeSetCloudAnchorMode(long j, long j2, int i);

    private native void nativeSetFocusMode(long j, long j2, int i);

    private native void nativeSetLightEstimationMode(long j, long j2, int i);

    private native void nativeSetPlaneFindingMode(long j, long j2, int i);

    private native void nativeSetUpdateMode(long j, long j2, int i);

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroy(this.nativeHandle);
        }
        super.finalize();
    }

    public AugmentedImageDatabase getAugmentedImageDatabase() {
        return new AugmentedImageDatabase(this.session.nativeHandle, nativeGetAugmentedImageDatabase(this.session.nativeHandle, this.nativeHandle));
    }

    public CloudAnchorMode getCloudAnchorMode() {
        return CloudAnchorMode.forNumber(nativeGetCloudAnchorMode(this.session.nativeHandle, this.nativeHandle));
    }

    public FocusMode getFocusMode() {
        return FocusMode.forNumber(nativeGetFocusMode(this.session.nativeHandle, this.nativeHandle));
    }

    public LightEstimationMode getLightEstimationMode() {
        return LightEstimationMode.forNumber(nativeGetLightEstimationMode(this.session.nativeHandle, this.nativeHandle));
    }

    public PlaneFindingMode getPlaneFindingMode() {
        return PlaneFindingMode.forNumber(nativeGetPlaneFindingMode(this.session.nativeHandle, this.nativeHandle));
    }

    public UpdateMode getUpdateMode() {
        return UpdateMode.forNumber(nativeGetUpdateMode(this.session.nativeHandle, this.nativeHandle));
    }

    public void setAugmentedImageDatabase(AugmentedImageDatabase augmentedImageDatabase) {
        nativeSetAugmentedImageDatabase(this.session.nativeHandle, this.nativeHandle, augmentedImageDatabase.nativeHandle);
    }

    public void setCloudAnchorMode(CloudAnchorMode cloudAnchorMode) {
        nativeSetCloudAnchorMode(this.session.nativeHandle, this.nativeHandle, cloudAnchorMode.nativeCode);
    }

    public void setFocusMode(FocusMode focusMode) {
        nativeSetFocusMode(this.session.nativeHandle, this.nativeHandle, focusMode.nativeCode);
    }

    public void setLightEstimationMode(LightEstimationMode lightEstimationMode) {
        nativeSetLightEstimationMode(this.session.nativeHandle, this.nativeHandle, lightEstimationMode.nativeCode);
    }

    public void setPlaneFindingMode(PlaneFindingMode planeFindingMode) {
        nativeSetPlaneFindingMode(this.session.nativeHandle, this.nativeHandle, planeFindingMode.nativeCode);
    }

    public void setUpdateMode(UpdateMode updateMode) {
        nativeSetUpdateMode(this.session.nativeHandle, this.nativeHandle, updateMode.nativeCode);
    }
}
