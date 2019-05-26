package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

public class View {
    private Camera mCamera;
    private DynamicResolutionOptions mDynamicResolution;
    private String mName;
    private long mNativeObject;
    private Scene mScene;
    private Viewport mViewport;

    public enum AntiAliasing {
        NONE,
        FXAA
    }

    public static class DynamicResolutionOptions {
        public boolean enabled = false;
        public float headRoomRatio = 0.0f;
        public int history = 9;
        public boolean homogeneousScaling = false;
        public float maxScale = 1.0f;
        public float minScale = 0.5f;
        public float scaleRate = 0.125f;
        public float targetFrameTimeMilli = 16.666666f;
    }

    private static native int nGetAntiAliasing(long j);

    private static native void nGetClearColor(long j, float[] fArr);

    private static native int nGetSampleCount(long j);

    private static native void nSetAntiAliasing(long j, int i);

    private static native void nSetCamera(long j, long j2);

    private static native void nSetClearColor(long j, float f, float f2, float f3, float f4);

    private static native void nSetClearTargets(long j, boolean z, boolean z2, boolean z3);

    private static native void nSetDynamicResolutionOptions(long j, boolean z, boolean z2, float f, float f2, float f3, float f4, float f5, int i);

    private static native void nSetName(long j, String str);

    private static native void nSetSampleCount(long j, int i);

    private static native void nSetScene(long j, long j2);

    private static native void nSetShadowsEnabled(long j, boolean z);

    private static native void nSetViewport(long j, int i, int i2, int i3, int i4);

    private static native void nSetVisibleLayers(long j, int i, int i2);

    View(long nativeView) {
        this.mNativeObject = nativeView;
    }

    public void setName(@NonNull String name) {
        this.mName = name;
        nSetName(getNativeObject(), name);
    }

    @Nullable
    public String getName() {
        return this.mName;
    }

    public void setScene(@Nullable Scene scene) {
        this.mScene = scene;
        nSetScene(getNativeObject(), scene == null ? 0 : scene.getNativeObject());
    }

    @Nullable
    public Scene getScene() {
        return this.mScene;
    }

    public void setCamera(@Nullable Camera camera) {
        this.mCamera = camera;
        nSetCamera(getNativeObject(), camera == null ? 0 : camera.getNativeObject());
    }

    @Nullable
    public Camera getCamera() {
        return this.mCamera;
    }

    public void setViewport(@NonNull Viewport viewport) {
        this.mViewport = viewport;
        nSetViewport(getNativeObject(), this.mViewport.left, this.mViewport.bottom, this.mViewport.width, this.mViewport.height);
    }

    @Nullable
    public Viewport getViewport() {
        return this.mViewport;
    }

    public void setClearColor(float r, float g, float b, float a) {
        nSetClearColor(getNativeObject(), r, g, b, a);
    }

    @Size(min = 4)
    @NonNull
    public float[] getClearColor(@Size(min = 4) @NonNull float[] out) {
        out = assertFloat4(out);
        nGetClearColor(getNativeObject(), out);
        return out;
    }

    public void setClearTargets(boolean color, boolean depth, boolean stencil) {
        nSetClearTargets(getNativeObject(), color, depth, stencil);
    }

    public void setVisibleLayers(@IntRange(from = 0, to = 255) int select, @IntRange(from = 0, to = 255) int values) {
        nSetVisibleLayers(getNativeObject(), select & 255, values & 255);
    }

    public void setShadowsEnabled(boolean enabled) {
        nSetShadowsEnabled(getNativeObject(), enabled);
    }

    public void setSampleCount(int count) {
        nSetSampleCount(getNativeObject(), count);
    }

    public int getSampleCount() {
        return nGetSampleCount(getNativeObject());
    }

    public void setAntiAliasing(@NonNull AntiAliasing type) {
        nSetAntiAliasing(getNativeObject(), type.ordinal());
    }

    @NonNull
    public AntiAliasing getAntiAliasing() {
        return AntiAliasing.values()[nGetAntiAliasing(getNativeObject())];
    }

    public void setDynamicResolutionOptions(@NonNull DynamicResolutionOptions options) {
        this.mDynamicResolution = options;
        nSetDynamicResolutionOptions(getNativeObject(), options.enabled, options.homogeneousScaling, options.targetFrameTimeMilli, options.headRoomRatio, options.scaleRate, options.minScale, options.maxScale, options.history);
    }

    @NonNull
    public DynamicResolutionOptions getDynamicResolutionOptions() {
        if (this.mDynamicResolution == null) {
            this.mDynamicResolution = new DynamicResolutionOptions();
        }
        return this.mDynamicResolution;
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed View");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }

    @Size(min = 4)
    @NonNull
    private static float[] assertFloat4(@Nullable float[] out) {
        if (out == null) {
            return new float[4];
        }
        if (out.length >= 4) {
            return out;
        }
        throw new ArrayIndexOutOfBoundsException("Array length must be at least 4");
    }
}
