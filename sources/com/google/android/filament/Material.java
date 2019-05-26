package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import com.google.android.filament.Colors.RgbType;
import com.google.android.filament.Colors.RgbaType;
import com.google.android.filament.MaterialInstance.BooleanElement;
import com.google.android.filament.MaterialInstance.FloatElement;
import com.google.android.filament.MaterialInstance.IntElement;
import com.google.android.filament.VertexBuffer.VertexAttribute;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Material {
    private final MaterialInstance mDefaultInstance;
    private long mNativeObject;
    private Set<VertexAttribute> mRequiredAttributes;

    public enum BlendingMode {
        OPAQUE,
        TRANSPARENT,
        ADD,
        MODULATE,
        MASKED
    }

    public static class Builder {
        private Buffer mBuffer;
        private int mSize;

        @NonNull
        public Builder payload(@NonNull Buffer buffer, @IntRange(from = 0) int size) {
            this.mBuffer = buffer;
            this.mSize = size;
            return this;
        }

        @NonNull
        public Material build(@NonNull Engine engine) {
            long nativeMaterial = Material.nBuilderBuild(engine.getNativeObject(), this.mBuffer, this.mSize);
            if (nativeMaterial != 0) {
                return new Material(nativeMaterial, Material.nGetDefaultInstance(nativeMaterial));
            }
            throw new IllegalStateException("Couldn't create Material");
        }
    }

    public enum CullingMode {
        NONE,
        FRONT,
        BACK,
        FRONT_AND_BACK
    }

    public enum Interpolation {
        SMOOTH,
        FLAT
    }

    public static class Parameter {
        private static final int SAMPLER_OFFSET = (Type.MAT4.ordinal() + 1);
        @IntRange(from = 1)
        public final int count;
        @NonNull
        public final String name;
        @NonNull
        public final Precision precision;
        @NonNull
        public final Type type;

        public enum Precision {
            LOW,
            MEDIUM,
            HIGH,
            DEFAULT
        }

        public enum Type {
            BOOL,
            BOOL2,
            BOOL3,
            BOOL4,
            FLOAT,
            FLOAT2,
            FLOAT3,
            FLOAT4,
            INT,
            INT2,
            INT3,
            INT4,
            UINT,
            UINT2,
            UINT3,
            UINT4,
            MAT3,
            MAT4,
            SAMPLER_2D,
            SAMPLER_CUBEMAP,
            SAMPLER_EXTERNAL
        }

        private Parameter(@NonNull String name, @NonNull Type type, @NonNull Precision precision, @IntRange(from = 1) int count) {
            this.name = name;
            this.type = type;
            this.precision = precision;
            this.count = count;
        }

        private static void add(@NonNull List<Parameter> parameters, @NonNull String name, @IntRange(from = 0) int type, @IntRange(from = 0) int precision, @IntRange(from = 1) int count) {
            parameters.add(new Parameter(name, Type.values()[type], Precision.values()[precision], count));
        }
    }

    public enum Shading {
        UNLIT,
        LIT,
        SUBSURFACE,
        CLOTH
    }

    public enum VertexDomain {
        OBJECT,
        WORLD,
        VIEW,
        DEVICE
    }

    private static native long nBuilderBuild(long j, @NonNull Buffer buffer, int i);

    private static native long nCreateInstance(long j);

    private static native int nGetBlendingMode(long j);

    private static native int nGetCullingMode(long j);

    private static native long nGetDefaultInstance(long j);

    private static native int nGetInterpolation(long j);

    private static native float nGetMaskThreshold(long j);

    private static native String nGetName(long j);

    private static native int nGetParameterCount(long j);

    private static native void nGetParameters(long j, @NonNull List<Parameter> list, @IntRange(from = 1) int i);

    private static native int nGetRequiredAttributes(long j);

    private static native int nGetShading(long j);

    private static native int nGetVertexDomain(long j);

    private static native boolean nHasParameter(long j, @NonNull String str);

    private static native boolean nIsColorWriteEnabled(long j);

    private static native boolean nIsDepthCullingEnabled(long j);

    private static native boolean nIsDepthWriteEnabled(long j);

    private static native boolean nIsDoubleSided(long j);

    private Material(long nativeMaterial, long nativeDefaultInstance) {
        this.mNativeObject = nativeMaterial;
        this.mDefaultInstance = new MaterialInstance(this, nativeDefaultInstance);
    }

    @NonNull
    public MaterialInstance createInstance() {
        long nativeInstance = nCreateInstance(getNativeObject());
        if (nativeInstance != 0) {
            return new MaterialInstance(this, nativeInstance);
        }
        throw new IllegalStateException("Couldn't create MaterialInstance");
    }

    @NonNull
    public MaterialInstance getDefaultInstance() {
        return this.mDefaultInstance;
    }

    public String getName() {
        return nGetName(getNativeObject());
    }

    public Shading getShading() {
        return Shading.values()[nGetShading(getNativeObject())];
    }

    public Interpolation getInterpolation() {
        return Interpolation.values()[nGetInterpolation(getNativeObject())];
    }

    public BlendingMode getBlendingMode() {
        return BlendingMode.values()[nGetBlendingMode(getNativeObject())];
    }

    public VertexDomain getVertexDomain() {
        return VertexDomain.values()[nGetVertexDomain(getNativeObject())];
    }

    public CullingMode getCullingMode() {
        return CullingMode.values()[nGetCullingMode(getNativeObject())];
    }

    public boolean isColorWriteEnabled() {
        return nIsColorWriteEnabled(getNativeObject());
    }

    public boolean isDepthWriteEnabled() {
        return nIsDepthWriteEnabled(getNativeObject());
    }

    public boolean isDepthCullingEnabled() {
        return nIsDepthCullingEnabled(getNativeObject());
    }

    public boolean isDoubleSided() {
        return nIsDoubleSided(getNativeObject());
    }

    public float getMaskThreshold() {
        return nGetMaskThreshold(getNativeObject());
    }

    public Set<VertexAttribute> getRequiredAttributes() {
        if (this.mRequiredAttributes == null) {
            int bitSet = nGetRequiredAttributes(getNativeObject());
            this.mRequiredAttributes = EnumSet.noneOf(VertexAttribute.class);
            VertexAttribute[] values = VertexAttribute.values();
            for (int i = 0; i < values.length; i++) {
                if (((1 << i) & bitSet) != 0) {
                    this.mRequiredAttributes.add(values[i]);
                }
            }
            this.mRequiredAttributes = Collections.unmodifiableSet(this.mRequiredAttributes);
        }
        return this.mRequiredAttributes;
    }

    int getRequiredAttributesAsInt() {
        return nGetRequiredAttributes(getNativeObject());
    }

    public int getParameterCount() {
        return nGetParameterCount(getNativeObject());
    }

    public List<Parameter> getParameters() {
        int count = getParameterCount();
        List<Parameter> parameters = new ArrayList(count);
        if (count > 0) {
            nGetParameters(getNativeObject(), parameters, count);
        }
        return parameters;
    }

    public boolean hasParameter(@NonNull String name) {
        return nHasParameter(getNativeObject(), name);
    }

    public void setDefaultParameter(@NonNull String name, boolean x) {
        this.mDefaultInstance.setParameter(name, x);
    }

    public void setDefaultParameter(@NonNull String name, float x) {
        this.mDefaultInstance.setParameter(name, x);
    }

    public void setDefaultParameter(@NonNull String name, int x) {
        this.mDefaultInstance.setParameter(name, x);
    }

    public void setDefaultParameter(@NonNull String name, boolean x, boolean y) {
        this.mDefaultInstance.setParameter(name, x, y);
    }

    public void setDefaultParameter(@NonNull String name, float x, float y) {
        this.mDefaultInstance.setParameter(name, x, y);
    }

    public void setDefaultParameter(@NonNull String name, int x, int y) {
        this.mDefaultInstance.setParameter(name, x, y);
    }

    public void setDefaultParameter(@NonNull String name, boolean x, boolean y, boolean z) {
        this.mDefaultInstance.setParameter(name, x, y, z);
    }

    public void setDefaultParameter(@NonNull String name, float x, float y, float z) {
        this.mDefaultInstance.setParameter(name, x, y, z);
    }

    public void setDefaultParameter(@NonNull String name, int x, int y, int z) {
        this.mDefaultInstance.setParameter(name, x, y, z);
    }

    public void setDefaultParameter(@NonNull String name, boolean x, boolean y, boolean z, boolean w) {
        this.mDefaultInstance.setParameter(name, x, y, z, w);
    }

    public void setDefaultParameter(@NonNull String name, float x, float y, float z, float w) {
        this.mDefaultInstance.setParameter(name, x, y, z, w);
    }

    public void setDefaultParameter(@NonNull String name, int x, int y, int z, int w) {
        this.mDefaultInstance.setParameter(name, x, y, z, w);
    }

    public void setDefaultParameter(@NonNull String name, @NonNull BooleanElement type, @Size(min = 1) @NonNull boolean[] v, @IntRange(from = 0) int offset, @IntRange(from = 1) int count) {
        this.mDefaultInstance.setParameter(name, type, v, offset, count);
    }

    public void setDefaultParameter(@NonNull String name, @NonNull IntElement type, @Size(min = 1) @NonNull int[] v, @IntRange(from = 0) int offset, @IntRange(from = 1) int count) {
        this.mDefaultInstance.setParameter(name, type, v, offset, count);
    }

    public void setDefaultParameter(@NonNull String name, @NonNull FloatElement type, @Size(min = 1) @NonNull float[] v, @IntRange(from = 0) int offset, @IntRange(from = 1) int count) {
        this.mDefaultInstance.setParameter(name, type, v, offset, count);
    }

    public void setDefaultParameter(@NonNull String name, @NonNull RgbType type, float r, float g, float b) {
        this.mDefaultInstance.setParameter(name, type, r, g, b);
    }

    public void setDefaultParameter(@NonNull String name, @NonNull RgbaType type, float r, float g, float b, float a) {
        this.mDefaultInstance.setParameter(name, type, r, g, b, a);
    }

    public void setDefaultParameter(@NonNull String name, @NonNull Texture texture, @NonNull TextureSampler sampler) {
        this.mDefaultInstance.setParameter(name, texture, sampler);
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Material");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
