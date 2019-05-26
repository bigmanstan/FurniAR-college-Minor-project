package com.google.ar.core;

import android.media.Image;
import android.view.MotionEvent;
import com.google.ar.core.exceptions.NotYetAvailableException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Frame {
    static final ArrayList<Anchor> ANCHORS_EMPTY_LIST = new ArrayList();
    static final ArrayList<Plane> PLANES_EMPTY_LIST = new ArrayList();
    private static final String TAG = "ARCore-Frame";
    private final LightEstimate lightEstimate;
    long nativeHandle;
    final Session session;

    protected Frame() {
        this.nativeHandle = 0;
        this.session = null;
        this.nativeHandle = 0;
        this.lightEstimate = null;
    }

    Frame(Session session) {
        this.nativeHandle = 0;
        this.session = session;
        this.nativeHandle = nativeCreateFrame(session.nativeHandle);
        this.lightEstimate = new LightEstimate(session);
    }

    private List<HitResult> convertNativeHitResultsToList(long[] jArr) {
        List arrayList = new ArrayList(jArr.length);
        for (long hitResult : jArr) {
            HitResult hitResult2 = new HitResult(hitResult, this.session);
            if (hitResult2.getTrackable() != null) {
                arrayList.add(hitResult2);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private native long nativeAcquireCameraImage(long j, long j2);

    private native long nativeAcquireImageMetadata(long j, long j2);

    private native long[] nativeAcquireUpdatedAnchors(long j, long j2);

    private static native long nativeCreateFrame(long j);

    private static native void nativeDestroyFrame(long j);

    private native Pose nativeGetAndroidSensorPose(long j, long j2);

    private native void nativeGetLightEstimate(long j, long j2, long j3);

    private native long nativeGetTimestamp(long j, long j2);

    private native boolean nativeHasDisplayGeometryChanged(long j, long j2);

    private native void nativeTransformDisplayUvCoords(long j, long j2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2);

    public Image acquireCameraImage() throws NotYetAvailableException {
        return new ArImage(nativeAcquireCameraImage(this.session.nativeHandle, this.nativeHandle));
    }

    public PointCloud acquirePointCloud() {
        return new PointCloud(this.session, nativeAcquirePointCloud(this.session.nativeHandle, this.nativeHandle));
    }

    protected void finalize() throws Throwable {
        if (this.nativeHandle != 0) {
            nativeDestroyFrame(this.nativeHandle);
        }
        super.finalize();
    }

    public Pose getAndroidSensorPose() {
        return nativeGetAndroidSensorPose(this.session.nativeHandle, this.nativeHandle);
    }

    public Camera getCamera() {
        return new Camera(this.session, this);
    }

    public ImageMetadata getImageMetadata() throws NotYetAvailableException {
        return new ImageMetadata(nativeAcquireImageMetadata(this.session.nativeHandle, this.nativeHandle), this.session);
    }

    public LightEstimate getLightEstimate() {
        nativeGetLightEstimate(this.session.nativeHandle, this.nativeHandle, this.lightEstimate.nativeHandle);
        return this.lightEstimate;
    }

    public long getTimestamp() {
        return nativeGetTimestamp(this.session.nativeHandle, this.nativeHandle);
    }

    public Collection<Anchor> getUpdatedAnchors() {
        return this.session.convertNativeAnchorsToCollection(nativeAcquireUpdatedAnchors(this.session.nativeHandle, this.nativeHandle));
    }

    public <T extends Trackable> Collection<T> getUpdatedTrackables(Class<T> cls) {
        int nativeTrackableFilterFromClass = Session.getNativeTrackableFilterFromClass(cls);
        if (nativeTrackableFilterFromClass == -1) {
            return Collections.emptyList();
        }
        return this.session.convertNativeTrackablesToCollection(cls, nativeAcquireUpdatedTrackables(this.session.nativeHandle, this.nativeHandle, nativeTrackableFilterFromClass));
    }

    public boolean hasDisplayGeometryChanged() {
        return nativeHasDisplayGeometryChanged(this.session.nativeHandle, this.nativeHandle);
    }

    public List<HitResult> hitTest(float f, float f2) {
        return convertNativeHitResultsToList(nativeHitTest(this.session.nativeHandle, this.nativeHandle, f, f2));
    }

    public List<HitResult> hitTest(MotionEvent motionEvent) {
        return hitTest(motionEvent.getX(), motionEvent.getY());
    }

    public List<HitResult> hitTest(float[] fArr, int i, float[] fArr2, int i2) {
        return convertNativeHitResultsToList(nativeHitTestRay(this.session.nativeHandle, this.nativeHandle, fArr, i, fArr2, i2));
    }

    native long nativeAcquirePointCloud(long j, long j2);

    native long[] nativeAcquireUpdatedTrackables(long j, long j2, int i);

    native long[] nativeHitTest(long j, long j2, float f, float f2);

    native long[] nativeHitTestRay(long j, long j2, float[] fArr, int i, float[] fArr2, int i2);

    public void transformDisplayUvCoords(FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (floatBuffer.isDirect() && floatBuffer2.isDirect()) {
            nativeTransformDisplayUvCoords(this.session.nativeHandle, this.nativeHandle, floatBuffer, floatBuffer2);
            return;
        }
        throw new IllegalArgumentException("transformDisplayUvCoords currently requires direct buffers.");
    }
}
