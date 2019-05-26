package com.google.android.filament;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

public class Camera {
    private long mNativeObject;

    public enum Fov {
        VERTICAL,
        HORIZONTAL
    }

    public enum Projection {
        PERSPECTIVE,
        ORTHO
    }

    private static native float nGetAperture(long j);

    private static native float nGetCullingFar(long j);

    private static native void nGetForwardVector(long j, float[] fArr);

    private static native void nGetLeftVector(long j, float[] fArr);

    private static native void nGetModelMatrix(long j, float[] fArr);

    private static native float nGetNear(long j);

    private static native void nGetPosition(long j, float[] fArr);

    private static native void nGetProjectionMatrix(long j, double[] dArr);

    private static native float nGetSensitivity(long j);

    private static native float nGetShutterSpeed(long j);

    private static native void nGetUpVector(long j, float[] fArr);

    private static native void nGetViewMatrix(long j, float[] fArr);

    private static native void nLookAt(long j, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9);

    private static native void nSetCustomProjection(long j, double[] dArr, double d, double d2);

    private static native void nSetExposure(long j, float f, float f2, float f3);

    private static native void nSetLensProjection(long j, double d, double d2, double d3);

    private static native void nSetModelMatrix(long j, float[] fArr);

    private static native void nSetProjection(long j, int i, double d, double d2, double d3, double d4, double d5, double d6);

    private static native void nSetProjectionFov(long j, double d, double d2, double d3, double d4, int i);

    Camera(long nativeCamera) {
        this.mNativeObject = nativeCamera;
    }

    public void setProjection(@NonNull Projection projection, double left, double right, double bottom, double top, double near, double far) {
        nSetProjection(getNativeObject(), projection.ordinal(), left, right, bottom, top, near, far);
    }

    public void setProjection(double fovInDegrees, double aspect, double near, double far, @NonNull Fov direction) {
        nSetProjectionFov(getNativeObject(), fovInDegrees, aspect, near, far, direction.ordinal());
    }

    public void setLensProjection(double focalLength, double near, double far) {
        nSetLensProjection(getNativeObject(), focalLength, near, far);
    }

    public void setCustomProjection(@Size(min = 16) @NonNull double[] inMatrix, double near, double far) {
        assertMat4dIn(inMatrix);
        nSetCustomProjection(getNativeObject(), inMatrix, near, far);
    }

    public void setModelMatrix(@Size(min = 16) @NonNull float[] in) {
        assertMat4fIn(in);
        nSetModelMatrix(getNativeObject(), in);
    }

    public void lookAt(double eyeX, double eyeY, double eyeZ, double centerX, double centerY, double centerZ, double upX, double upY, double upZ) {
        nLookAt(getNativeObject(), eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    public float getNear() {
        return nGetNear(getNativeObject());
    }

    public float getCullingFar() {
        return nGetCullingFar(getNativeObject());
    }

    @Size(min = 16)
    @NonNull
    public double[] getProjectionMatrix(@Nullable @Size(min = 16) double[] out) {
        out = assertMat4d(out);
        nGetProjectionMatrix(getNativeObject(), out);
        return out;
    }

    @Size(min = 16)
    @NonNull
    public float[] getModelMatrix(@Nullable @Size(min = 16) float[] out) {
        out = assertMat4f(out);
        nGetModelMatrix(getNativeObject(), out);
        return out;
    }

    @Size(min = 16)
    @NonNull
    public float[] getViewMatrix(@Nullable @Size(min = 16) float[] out) {
        out = assertMat4f(out);
        nGetViewMatrix(getNativeObject(), out);
        return out;
    }

    @Size(min = 3)
    @NonNull
    public float[] getPosition(@Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetPosition(getNativeObject(), out);
        return out;
    }

    @Size(min = 3)
    @NonNull
    public float[] getLeftVector(@Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetLeftVector(getNativeObject(), out);
        return out;
    }

    @Size(min = 3)
    @NonNull
    public float[] getUpVector(@Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetUpVector(getNativeObject(), out);
        return out;
    }

    @Size(min = 3)
    @NonNull
    public float[] getForwardVector(@Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetForwardVector(getNativeObject(), out);
        return out;
    }

    public void setExposure(float aperture, float shutterSpeed, float sensitivity) {
        nSetExposure(getNativeObject(), aperture, shutterSpeed, sensitivity);
    }

    public float getAperture() {
        return nGetAperture(getNativeObject());
    }

    public float getShutterSpeed() {
        return nGetShutterSpeed(getNativeObject());
    }

    public float getSensitivity() {
        return nGetSensitivity(getNativeObject());
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Camera");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }

    @Size(min = 16)
    @NonNull
    private static double[] assertMat4d(@Nullable double[] out) {
        if (out == null) {
            return new double[16];
        }
        if (out.length >= 16) {
            return out;
        }
        throw new ArrayIndexOutOfBoundsException("Array length must be at least 16");
    }

    @Size(min = 16)
    @NonNull
    private static float[] assertMat4f(@Nullable float[] out) {
        if (out == null) {
            return new float[16];
        }
        if (out.length >= 16) {
            return out;
        }
        throw new ArrayIndexOutOfBoundsException("Array length must be at least 16");
    }

    private static void assertMat4dIn(@Size(min = 16) @NonNull double[] in) {
        if (in.length < 16) {
            throw new ArrayIndexOutOfBoundsException("Array length must be at least 16");
        }
    }

    private static void assertMat4fIn(@Size(min = 16) @NonNull float[] in) {
        if (in.length < 16) {
            throw new ArrayIndexOutOfBoundsException("Array length must be at least 16");
        }
    }

    @Size(min = 3)
    @NonNull
    private static float[] assertFloat3(@Nullable float[] out) {
        if (out == null) {
            return new float[3];
        }
        if (out.length >= 3) {
            return out;
        }
        throw new ArrayIndexOutOfBoundsException("Array length must be at least 3");
    }
}
