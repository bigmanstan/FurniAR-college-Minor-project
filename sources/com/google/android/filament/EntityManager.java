package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public class EntityManager {
    private long mNativeObject;

    private static class Holder {
        static final EntityManager INSTANCE = new EntityManager();

        private Holder() {
        }
    }

    private static native int nCreate(long j);

    private static native void nCreate(long j, int i, int[] iArr);

    private static native void nDestroy(long j, int i);

    private static native void nDestroy(long j, int i, int[] iArr);

    private static native long nGetEntityManager();

    private static native boolean nIsAlive(long j, int i);

    private EntityManager() {
        this.mNativeObject = nGetEntityManager();
    }

    @NonNull
    public static EntityManager get() {
        return Holder.INSTANCE;
    }

    @Entity
    public int create() {
        return nCreate(this.mNativeObject);
    }

    public void destroy(@Entity int entity) {
        nDestroy(this.mNativeObject, entity);
    }

    @NonNull
    @Entity
    public int[] create(@IntRange(from = 1) int n) {
        if (n >= 1) {
            int[] entities = new int[n];
            nCreate(this.mNativeObject, n, entities);
            return entities;
        }
        throw new ArrayIndexOutOfBoundsException("n must be at least 1");
    }

    @NonNull
    public int[] create(@NonNull @Entity int[] entities) {
        nCreate(this.mNativeObject, entities.length, entities);
        return entities;
    }

    public void destroy(@NonNull @Entity int[] entities) {
        nDestroy(this.mNativeObject, entities.length, entities);
    }

    public boolean isAlive(@Entity int entity) {
        return nIsAlive(this.mNativeObject, entity);
    }
}
