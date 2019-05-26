package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public class IndirectLight {
    long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder = IndirectLight.nCreateBuilder();

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    IndirectLight.nDestroyBuilder(this.mNativeObject);
                }
                IndirectLight.nDestroyBuilder(this.mNativeObject);
            }
        }

        @NonNull
        public Builder reflections(@NonNull Texture cubemap) {
            IndirectLight.nBuilderReflections(this.mNativeBuilder, cubemap.getNativeObject());
            return this;
        }

        @NonNull
        public Builder irradiance(@IntRange(from = 1, to = 3) int bands, @NonNull float[] sh) {
            switch (bands) {
                case 1:
                    if (sh.length < 3) {
                        throw new ArrayIndexOutOfBoundsException("1 band SH, array must be at least 1 x float3");
                    }
                    break;
                case 2:
                    if (sh.length >= 12) {
                        break;
                    }
                    throw new ArrayIndexOutOfBoundsException("2 bands SH, array must be at least 4 x float3");
                case 3:
                    if (sh.length >= 27) {
                        break;
                    }
                    throw new ArrayIndexOutOfBoundsException("3 bands SH, array must be at least 9 x float3");
                default:
                    throw new IllegalArgumentException("bands must be 1, 2 or 3");
            }
            IndirectLight.nIrradiance(this.mNativeBuilder, bands, sh);
            return this;
        }

        @NonNull
        public Builder irradiance(@NonNull Texture cubemap) {
            IndirectLight.nIrradianceAsTexture(this.mNativeBuilder, cubemap.getNativeObject());
            return this;
        }

        @NonNull
        public Builder intensity(float envIntensity) {
            IndirectLight.nIntensity(this.mNativeBuilder, envIntensity);
            return this;
        }

        @NonNull
        public IndirectLight build(@NonNull Engine engine) {
            long nativeIndirectLight = IndirectLight.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject());
            if (nativeIndirectLight != 0) {
                return new IndirectLight(nativeIndirectLight);
            }
            throw new IllegalStateException("Couldn't create IndirectLight");
        }
    }

    private static native long nBuilderBuild(long j, long j2);

    private static native void nBuilderReflections(long j, long j2);

    private static native long nCreateBuilder();

    private static native void nDestroyBuilder(long j);

    private static native float nGetIntensity(long j);

    private static native void nIntensity(long j, float f);

    private static native void nIrradiance(long j, int i, float[] fArr);

    private static native void nIrradianceAsTexture(long j, long j2);

    private static native void nSetIntensity(long j, float f);

    private IndirectLight(long indirectLight) {
        this.mNativeObject = indirectLight;
    }

    public void setIntensity(float intensity) {
        nSetIntensity(getNativeObject(), intensity);
    }

    public float getIntensity() {
        return nGetIntensity(getNativeObject());
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed IndirectLight");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
