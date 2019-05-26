package com.google.ar.core;

public class Camera {
    private static final String TAG = "ARCore-Camera";
    long nativeHandle;
    final Session session;

    protected Camera() {
        this.session = null;
        this.nativeHandle = 0;
    }

    Camera(Session session, Frame frame) {
        this.session = session;
        this.nativeHandle = nativeAcquireCamera(session.nativeHandle, frame.nativeHandle);
    }

    private static native long nativeAcquireCamera(long j, long j2);

    private native long nativeCreateCameraIntrinsics(long j, long j2);

    private native Pose nativeDisplayOrientedPose(long j, long j2);

    private native void nativeGetImageIntrinsics(long j, long j2, long j3);

    private native Pose nativeGetPose(long j, long j2);

    private native void nativeGetProjectionMatrix(long j, long j2, float[] fArr, int i, float f, float f2);

    private native void nativeGetTextureIntrinsics(long j, long j2, long j3);

    private native int nativeGetTrackingState(long j, long j2);

    private native void nativeGetViewMatrix(long j, long j2, float[] fArr, int i);

    private static native void nativeReleaseCamera(long j);

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (((Camera) obj).nativeHandle == this.nativeHandle) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeReleaseCamera(this.nativeHandle);
        }
        super.finalize();
    }

    public Pose getDisplayOrientedPose() {
        return nativeDisplayOrientedPose(this.session.nativeHandle, this.nativeHandle);
    }

    public CameraIntrinsics getImageIntrinsics() {
        CameraIntrinsics cameraIntrinsics = new CameraIntrinsics(nativeCreateCameraIntrinsics(this.session.nativeHandle, this.nativeHandle), this.session);
        nativeGetImageIntrinsics(this.session.nativeHandle, this.nativeHandle, cameraIntrinsics.nativeHandle);
        return cameraIntrinsics;
    }

    public Pose getPose() {
        return nativeGetPose(this.session.nativeHandle, this.nativeHandle);
    }

    public void getProjectionMatrix(float[] fArr, int i, float f, float f2) {
        nativeGetProjectionMatrix(this.session.nativeHandle, this.nativeHandle, fArr, i, f, f2);
    }

    public CameraIntrinsics getTextureIntrinsics() {
        CameraIntrinsics cameraIntrinsics = new CameraIntrinsics(nativeCreateCameraIntrinsics(this.session.nativeHandle, this.nativeHandle), this.session);
        nativeGetTextureIntrinsics(this.session.nativeHandle, this.nativeHandle, cameraIntrinsics.nativeHandle);
        return cameraIntrinsics;
    }

    public TrackingState getTrackingState() {
        return TrackingState.forNumber(nativeGetTrackingState(this.session.nativeHandle, this.nativeHandle));
    }

    public void getViewMatrix(float[] fArr, int i) {
        nativeGetViewMatrix(this.session.nativeHandle, this.nativeHandle, fArr, i);
    }

    public int hashCode() {
        return Long.valueOf(this.nativeHandle).hashCode();
    }
}
