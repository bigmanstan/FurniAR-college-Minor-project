package com.google.android.filament;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

public class LightManager {
    public static final float EFFICIENCY_FLUORESCENT = 0.0878f;
    public static final float EFFICIENCY_HALOGEN = 0.0707f;
    public static final float EFFICIENCY_INCANDESCENT = 0.022f;
    public static final float EFFICIENCY_LED = 0.1171f;
    private long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder;

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    LightManager.nDestroyBuilder(this.mNativeObject);
                }
                LightManager.nDestroyBuilder(this.mNativeObject);
            }
        }

        public Builder(@NonNull Type type) {
            this.mNativeBuilder = LightManager.nCreateBuilder(type.ordinal());
        }

        @NonNull
        public Builder castShadows(boolean enable) {
            LightManager.nBuilderCastShadows(this.mNativeBuilder, enable);
            return this;
        }

        @NonNull
        public Builder shadowOptions(@NonNull ShadowOptions options) {
            LightManager.nBuilderShadowOptions(this.mNativeBuilder, options.mapSize, options.constantBias, options.normalBias, options.shadowFar);
            return this;
        }

        @NonNull
        public Builder castLight(boolean enabled) {
            LightManager.nBuilderCastLight(this.mNativeBuilder, enabled);
            return this;
        }

        @NonNull
        public Builder position(float x, float y, float z) {
            LightManager.nBuilderPosition(this.mNativeBuilder, x, y, z);
            return this;
        }

        @NonNull
        public Builder direction(float x, float y, float z) {
            LightManager.nBuilderDirection(this.mNativeBuilder, x, y, z);
            return this;
        }

        @NonNull
        public Builder color(float linearR, float linearG, float linearB) {
            LightManager.nBuilderColor(this.mNativeBuilder, linearR, linearG, linearB);
            return this;
        }

        @NonNull
        public Builder intensity(float intensity) {
            LightManager.nBuilderIntensity(this.mNativeBuilder, intensity);
            return this;
        }

        @NonNull
        public Builder intensity(float watts, float efficiency) {
            LightManager.nBuilderIntensity(this.mNativeBuilder, watts, efficiency);
            return this;
        }

        @NonNull
        public Builder falloff(float radius) {
            LightManager.nBuilderFalloff(this.mNativeBuilder, radius);
            return this;
        }

        @NonNull
        public Builder spotLightCone(float inner, float outer) {
            LightManager.nBuilderSpotLightCone(this.mNativeBuilder, inner, outer);
            return this;
        }

        @NonNull
        public Builder sunAngularRadius(float angularRadius) {
            LightManager.nBuilderAngularRadius(this.mNativeBuilder, angularRadius);
            return this;
        }

        @NonNull
        public Builder sunHaloSize(float haloSize) {
            LightManager.nBuilderHaloSize(this.mNativeBuilder, haloSize);
            return this;
        }

        @NonNull
        public Builder sunHaloFalloff(float haloFalloff) {
            LightManager.nBuilderHaloFalloff(this.mNativeBuilder, haloFalloff);
            return this;
        }

        public void build(@NonNull Engine engine, @Entity int entity) {
            if (!LightManager.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject(), entity)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't create Light component for entity ");
                stringBuilder.append(entity);
                stringBuilder.append(", see log.");
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    public static class ShadowOptions {
        public float constantBias = 0.05f;
        public int mapSize = 1024;
        public float normalBias = 0.4f;
        public float shadowFar = 0.0f;
    }

    public enum Type {
        SUN,
        DIRECTIONAL,
        POINT,
        FOCUSED_SPOT,
        SPOT
    }

    private static native void nBuilderAngularRadius(long j, float f);

    private static native boolean nBuilderBuild(long j, long j2, int i);

    private static native void nBuilderCastLight(long j, boolean z);

    private static native void nBuilderCastShadows(long j, boolean z);

    private static native void nBuilderColor(long j, float f, float f2, float f3);

    private static native void nBuilderDirection(long j, float f, float f2, float f3);

    private static native void nBuilderFalloff(long j, float f);

    private static native void nBuilderHaloFalloff(long j, float f);

    private static native void nBuilderHaloSize(long j, float f);

    private static native void nBuilderIntensity(long j, float f);

    private static native void nBuilderIntensity(long j, float f, float f2);

    private static native void nBuilderPosition(long j, float f, float f2, float f3);

    private static native void nBuilderShadowOptions(long j, int i, float f, float f2, float f3);

    private static native void nBuilderSpotLightCone(long j, float f, float f2);

    private static native long nCreateBuilder(int i);

    private static native void nDestroy(long j, int i);

    private static native void nDestroyBuilder(long j);

    private static native void nGetColor(long j, int i, float[] fArr);

    private static native void nGetDirection(long j, int i, float[] fArr);

    private static native float nGetFalloff(long j, int i);

    private static native int nGetInstance(long j, int i);

    private static native float nGetIntensity(long j, int i);

    private static native void nGetPosition(long j, int i, float[] fArr);

    private static native float nGetSunAngularRadius(long j, int i);

    private static native float nGetSunHaloFalloff(long j, int i);

    private static native float nGetSunHaloSize(long j, int i);

    private static native boolean nHasComponent(long j, int i);

    private static native void nSetColor(long j, int i, float f, float f2, float f3);

    private static native void nSetDirection(long j, int i, float f, float f2, float f3);

    private static native void nSetFalloff(long j, int i, float f);

    private static native void nSetIntensity(long j, int i, float f);

    private static native void nSetIntensity(long j, int i, float f, float f2);

    private static native void nSetPosition(long j, int i, float f, float f2, float f3);

    private static native void nSetSpotLightCone(long j, int i, float f, float f2);

    private static native void nSetSunAngularRadius(long j, int i, float f);

    private static native void nSetSunHaloFalloff(long j, int i, float f);

    private static native void nSetSunHaloSize(long j, int i, float f);

    LightManager(long nativeLightManager) {
        this.mNativeObject = nativeLightManager;
    }

    public boolean hasComponent(@Entity int entity) {
        return nHasComponent(this.mNativeObject, entity);
    }

    @EntityInstance
    public int getInstance(@Entity int entity) {
        return nGetInstance(this.mNativeObject, entity);
    }

    public void destroy(@Entity int entity) {
        nDestroy(this.mNativeObject, entity);
    }

    public void setPosition(@EntityInstance int i, float x, float y, float z) {
        nSetPosition(this.mNativeObject, i, x, y, z);
    }

    @NonNull
    public float[] getPosition(@EntityInstance int i, @Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetPosition(this.mNativeObject, i, out);
        return out;
    }

    public void setDirection(@EntityInstance int i, float x, float y, float z) {
        nSetDirection(this.mNativeObject, i, x, y, z);
    }

    @NonNull
    public float[] getDirection(@EntityInstance int i, @Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetDirection(this.mNativeObject, i, out);
        return out;
    }

    public void setColor(@EntityInstance int i, float linearR, float linearG, float linearB) {
        nSetColor(this.mNativeObject, i, linearR, linearG, linearB);
    }

    @NonNull
    public float[] getColor(@EntityInstance int i, @Nullable @Size(min = 3) float[] out) {
        out = assertFloat3(out);
        nGetColor(this.mNativeObject, i, out);
        return out;
    }

    public void setIntensity(@EntityInstance int i, float intensity) {
        nSetIntensity(this.mNativeObject, i, intensity);
    }

    public void setIntensity(@EntityInstance int i, float watts, float efficiency) {
        nSetIntensity(this.mNativeObject, i, watts, efficiency);
    }

    public float getIntensity(@EntityInstance int i) {
        return nGetIntensity(this.mNativeObject, i);
    }

    public void setFalloff(@EntityInstance int i, float falloff) {
        nSetFalloff(this.mNativeObject, i, falloff);
    }

    public float getFalloff(@EntityInstance int i) {
        return nGetFalloff(this.mNativeObject, i);
    }

    public void setSpotLightCone(@EntityInstance int i, float inner, float outer) {
        nSetSpotLightCone(this.mNativeObject, i, inner, outer);
    }

    public void setSunAngularRadius(@EntityInstance int i, float angularRadius) {
        nSetSunAngularRadius(this.mNativeObject, i, angularRadius);
    }

    public float getSunAngularRadius(@EntityInstance int i) {
        return nGetSunAngularRadius(this.mNativeObject, i);
    }

    public void setSunHaloSize(@EntityInstance int i, float haloSize) {
        nSetSunHaloSize(this.mNativeObject, i, haloSize);
    }

    public float getSunHaloSize(@EntityInstance int i) {
        return nGetSunHaloSize(this.mNativeObject, i);
    }

    public void setSunHaloFalloff(@EntityInstance int i, float haloFalloff) {
        nSetSunHaloFalloff(this.mNativeObject, i, haloFalloff);
    }

    public float getSunHaloFalloff(@EntityInstance int i) {
        return nGetSunHaloFalloff(this.mNativeObject, i);
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
