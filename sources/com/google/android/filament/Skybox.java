package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public class Skybox {
    private long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder = Skybox.nCreateBuilder();

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    Skybox.nDestroyBuilder(this.mNativeObject);
                }
                Skybox.nDestroyBuilder(this.mNativeObject);
            }
        }

        @NonNull
        public Builder environment(@NonNull Texture texture) {
            Skybox.nBuilderEnvironment(this.mNativeBuilder, texture.getNativeObject());
            return this;
        }

        @NonNull
        public Builder showSun(boolean show) {
            Skybox.nBuilderShowSun(this.mNativeBuilder, show);
            return this;
        }

        @NonNull
        public Skybox build(@NonNull Engine engine) {
            long nativeSkybox = Skybox.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject());
            if (nativeSkybox != 0) {
                return new Skybox(nativeSkybox);
            }
            throw new IllegalStateException("Couldn't create Skybox");
        }
    }

    private static native long nBuilderBuild(long j, long j2);

    private static native void nBuilderEnvironment(long j, long j2);

    private static native void nBuilderShowSun(long j, boolean z);

    private static native long nCreateBuilder();

    private static native void nDestroyBuilder(long j);

    private static native int nGetLayerMask(long j);

    private static native void nSetLayerMask(long j, int i, int i2);

    private Skybox(long nativeSkybox) {
        this.mNativeObject = nativeSkybox;
    }

    public void setLayerMask(@IntRange(from = 0, to = 255) int select, @IntRange(from = 0, to = 255) int value) {
        nSetLayerMask(getNativeObject(), select & 255, value & 255);
    }

    public int getLayerMask() {
        return nGetLayerMask(getNativeObject());
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Skybox");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
