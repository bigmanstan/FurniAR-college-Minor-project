package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import com.google.android.filament.Colors.RgbType;
import com.google.android.filament.Colors.RgbaType;

public class MaterialInstance {
    private final Material mMaterial;
    private long mNativeObject;

    public enum BooleanElement {
        BOOL,
        BOOL2,
        BOOL3,
        BOOL4
    }

    public enum FloatElement {
        FLOAT,
        FLOAT2,
        FLOAT3,
        FLOAT4,
        MAT3,
        MAT4
    }

    public enum IntElement {
        INT,
        INT2,
        INT3,
        INT4
    }

    private static native void nSetBooleanParameterArray(long j, @NonNull String str, int i, @Size(min = 1) @NonNull boolean[] zArr, @IntRange(from = 0) int i2, @IntRange(from = 1) int i3);

    private static native void nSetFloatParameterArray(long j, @NonNull String str, int i, @Size(min = 1) @NonNull float[] fArr, @IntRange(from = 0) int i2, @IntRange(from = 1) int i3);

    private static native void nSetIntParameterArray(long j, @NonNull String str, int i, @Size(min = 1) @NonNull int[] iArr, @IntRange(from = 0) int i2, @IntRange(from = 1) int i3);

    private static native void nSetParameterBool(long j, @NonNull String str, boolean z);

    private static native void nSetParameterBool2(long j, @NonNull String str, boolean z, boolean z2);

    private static native void nSetParameterBool3(long j, @NonNull String str, boolean z, boolean z2, boolean z3);

    private static native void nSetParameterBool4(long j, @NonNull String str, boolean z, boolean z2, boolean z3, boolean z4);

    private static native void nSetParameterFloat(long j, @NonNull String str, float f);

    private static native void nSetParameterFloat2(long j, @NonNull String str, float f, float f2);

    private static native void nSetParameterFloat3(long j, @NonNull String str, float f, float f2, float f3);

    private static native void nSetParameterFloat4(long j, @NonNull String str, float f, float f2, float f3, float f4);

    private static native void nSetParameterInt(long j, @NonNull String str, int i);

    private static native void nSetParameterInt2(long j, @NonNull String str, int i, int i2);

    private static native void nSetParameterInt3(long j, @NonNull String str, int i, int i2, int i3);

    private static native void nSetParameterInt4(long j, @NonNull String str, int i, int i2, int i3, int i4);

    private static native void nSetParameterTexture(long j, @NonNull String str, long j2, int i);

    private static native void nSetScissor(long j, @IntRange(from = 0) int i, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4);

    private static native void nUnsetScissor(long j);

    MaterialInstance(@NonNull Material material, long nativeMaterialInstance) {
        this.mMaterial = material;
        this.mNativeObject = nativeMaterialInstance;
    }

    @NonNull
    public Material getMaterial() {
        return this.mMaterial;
    }

    public void setParameter(@NonNull String name, boolean x) {
        nSetParameterBool(getNativeObject(), name, x);
    }

    public void setParameter(@NonNull String name, float x) {
        nSetParameterFloat(getNativeObject(), name, x);
    }

    public void setParameter(@NonNull String name, int x) {
        nSetParameterInt(getNativeObject(), name, x);
    }

    public void setParameter(@NonNull String name, boolean x, boolean y) {
        nSetParameterBool2(getNativeObject(), name, x, y);
    }

    public void setParameter(@NonNull String name, float x, float y) {
        nSetParameterFloat2(getNativeObject(), name, x, y);
    }

    public void setParameter(@NonNull String name, int x, int y) {
        nSetParameterInt2(getNativeObject(), name, x, y);
    }

    public void setParameter(@NonNull String name, boolean x, boolean y, boolean z) {
        nSetParameterBool3(getNativeObject(), name, x, y, z);
    }

    public void setParameter(@NonNull String name, float x, float y, float z) {
        nSetParameterFloat3(getNativeObject(), name, x, y, z);
    }

    public void setParameter(@NonNull String name, int x, int y, int z) {
        nSetParameterInt3(getNativeObject(), name, x, y, z);
    }

    public void setParameter(@NonNull String name, boolean x, boolean y, boolean z, boolean w) {
        nSetParameterBool4(getNativeObject(), name, x, y, z, w);
    }

    public void setParameter(@NonNull String name, float x, float y, float z, float w) {
        nSetParameterFloat4(getNativeObject(), name, x, y, z, w);
    }

    public void setParameter(@NonNull String name, int x, int y, int z, int w) {
        nSetParameterInt4(getNativeObject(), name, x, y, z, w);
    }

    public void setParameter(@NonNull String name, @NonNull Texture texture, @NonNull TextureSampler sampler) {
        nSetParameterTexture(getNativeObject(), name, texture.getNativeObject(), sampler.mSampler);
    }

    public void setParameter(@NonNull String name, @NonNull BooleanElement type, @NonNull boolean[] v, @IntRange(from = 0) int offset, @IntRange(from = 1) int count) {
        nSetBooleanParameterArray(getNativeObject(), name, type.ordinal(), v, offset, count);
    }

    public void setParameter(@NonNull String name, @NonNull IntElement type, @NonNull int[] v, @IntRange(from = 0) int offset, @IntRange(from = 1) int count) {
        nSetIntParameterArray(getNativeObject(), name, type.ordinal(), v, offset, count);
    }

    public void setParameter(@NonNull String name, @NonNull FloatElement type, @NonNull float[] v, @IntRange(from = 0) int offset, @IntRange(from = 1) int count) {
        nSetFloatParameterArray(getNativeObject(), name, type.ordinal(), v, offset, count);
    }

    public void setParameter(@NonNull String name, @NonNull RgbType type, float r, float g, float b) {
        setParameter(name, FloatElement.FLOAT3, Colors.toLinear(type, r, g, b), 0, 1);
    }

    public void setParameter(@NonNull String name, @NonNull RgbaType type, float r, float g, float b, float a) {
        setParameter(name, FloatElement.FLOAT4, Colors.toLinear(type, r, g, b, a), 0, 1);
    }

    public void setScissor(@IntRange(from = 0) int left, @IntRange(from = 0) int bottom, @IntRange(from = 0) int width, @IntRange(from = 0) int height) {
        nSetScissor(getNativeObject(), left, bottom, width, height);
    }

    public void unsetScissor() {
        nUnsetScissor(getNativeObject());
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed MaterialInstance");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
