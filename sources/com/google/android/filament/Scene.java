package com.google.android.filament;

import android.support.annotation.NonNull;

public class Scene {
    private long mNativeObject;

    private static native void nAddEntity(long j, int i);

    private static native int nGetLightCount(long j);

    private static native int nGetRenderableCount(long j);

    private static native void nRemove(long j, int i);

    private static native void nSetIndirectLight(long j, long j2);

    private static native void nSetSkybox(long j, long j2);

    Scene(long nativeScene) {
        this.mNativeObject = nativeScene;
    }

    public void setSkybox(@NonNull Skybox skybox) {
        nSetSkybox(getNativeObject(), skybox.getNativeObject());
    }

    public void setIndirectLight(@NonNull IndirectLight ibl) {
        nSetIndirectLight(getNativeObject(), ibl.getNativeObject());
    }

    public void addEntity(@Entity int entity) {
        nAddEntity(getNativeObject(), entity);
    }

    public void remove(@Entity int entity) {
        nRemove(getNativeObject(), entity);
    }

    public int getRenderableCount() {
        return nGetRenderableCount(getNativeObject());
    }

    public int getLightCount() {
        return nGetLightCount(getNativeObject());
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Scene");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
