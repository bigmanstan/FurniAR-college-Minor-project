package com.google.android.filament;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

public class TransformManager {
    private long mNativeObject;

    private static native void nCommitLocalTransformTransaction(long j);

    private static native int nCreate(long j, int i);

    private static native int nCreate(long j, int i, int i2, float[] fArr);

    private static native void nDestroy(long j, int i);

    private static native int nGetInstance(long j, int i);

    private static native void nGetTransform(long j, int i, float[] fArr);

    private static native void nGetWorldTransform(long j, int i, float[] fArr);

    private static native boolean nHasComponent(long j, int i);

    private static native void nOpenLocalTransformTransaction(long j);

    private static native void nSetParent(long j, int i, int i2);

    private static native void nSetTransform(long j, int i, float[] fArr);

    TransformManager(long nativeTransformManager) {
        this.mNativeObject = nativeTransformManager;
    }

    public boolean hasComponent(@Entity int entity) {
        return nHasComponent(this.mNativeObject, entity);
    }

    @EntityInstance
    public int getInstance(@Entity int entity) {
        return nGetInstance(this.mNativeObject, entity);
    }

    @EntityInstance
    public int create(@Entity int entity) {
        return nCreate(this.mNativeObject, entity);
    }

    @EntityInstance
    public int create(@Entity int entity, @EntityInstance int parent, @Nullable @Size(min = 16) float[] localTransform) {
        return nCreate(this.mNativeObject, entity, parent, localTransform);
    }

    public void destroy(@Entity int entity) {
        nDestroy(this.mNativeObject, entity);
    }

    public void setParent(@EntityInstance int i, @EntityInstance int newParent) {
        nSetParent(this.mNativeObject, i, newParent);
    }

    public void setTransform(@EntityInstance int i, @Size(min = 16) @NonNull float[] localTransform) {
        if (localTransform.length >= 16) {
            nSetTransform(this.mNativeObject, i, localTransform);
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Array length must be at least 16");
    }

    @Size(min = 16)
    @NonNull
    public float[] getTransform(@EntityInstance int i, @Nullable @Size(min = 16) float[] outLocalTransform) {
        outLocalTransform = assertMat4f(outLocalTransform);
        nGetTransform(this.mNativeObject, i, outLocalTransform);
        return outLocalTransform;
    }

    @Size(min = 16)
    @NonNull
    public float[] getWorldTransform(@EntityInstance int i, @Nullable @Size(min = 16) float[] outWorldTransform) {
        outWorldTransform = assertMat4f(outWorldTransform);
        nGetWorldTransform(this.mNativeObject, i, outWorldTransform);
        return outWorldTransform;
    }

    public void openLocalTransformTransaction() {
        nOpenLocalTransformTransaction(this.mNativeObject);
    }

    public void commitLocalTransformTransaction() {
        nCommitLocalTransformTransaction(this.mNativeObject);
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
}
