package com.google.android.filament;

import android.support.annotation.NonNull;

public class TextureSampler {
    int mSampler;

    /* renamed from: com.google.android.filament.TextureSampler$1 */
    static /* synthetic */ class C03661 {
        static final /* synthetic */ int[] $SwitchMap$com$google$android$filament$TextureSampler$MagFilter = new int[MagFilter.values().length];

        static {
            try {
                $SwitchMap$com$google$android$filament$TextureSampler$MagFilter[MagFilter.NEAREST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$android$filament$TextureSampler$MagFilter[MagFilter.LINEAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public enum CompareFunction {
        LESS_EQUAL,
        GREATER_EQUAL,
        LESS,
        GREATER,
        EQUAL,
        NOT_EQUAL,
        ALWAYS,
        NEVER
    }

    public enum CompareMode {
        NONE,
        COMPARE_TO_TEXTURE
    }

    public enum MagFilter {
        NEAREST,
        LINEAR
    }

    public enum MinFilter {
        NEAREST,
        LINEAR,
        NEAREST_MIPMAP_NEAREST,
        LINEAR_MIPMAP_NEAREST,
        NEAREST_MIPMAP_LINEAR,
        LINEAR_MIPMAP_LINEAR
    }

    public enum WrapMode {
        CLAMP_TO_EDGE,
        REPEAT,
        MIRRORED_REPEAT
    }

    private static native int nCreateCompareSampler(int i, int i2);

    private static native int nCreateSampler(int i, int i2, int i3, int i4, int i5);

    private static native float nGetAnisotropy(int i);

    private static native int nGetCompareFunction(int i);

    private static native int nGetCompareMode(int i);

    private static native int nGetMagFilter(int i);

    private static native int nGetMinFilter(int i);

    private static native int nGetWrapModeR(int i);

    private static native int nGetWrapModeS(int i);

    private static native int nGetWrapModeT(int i);

    private static native int nSetAnisotropy(int i, float f);

    private static native int nSetCompareFunction(int i, int i2);

    private static native int nSetCompareMode(int i, int i2);

    private static native int nSetMagFilter(int i, int i2);

    private static native int nSetMinFilter(int i, int i2);

    private static native int nSetWrapModeR(int i, int i2);

    private static native int nSetWrapModeS(int i, int i2);

    private static native int nSetWrapModeT(int i, int i2);

    public TextureSampler() {
        this.mSampler = 0;
    }

    public TextureSampler(@NonNull MagFilter minMag) {
        this(minMag, WrapMode.CLAMP_TO_EDGE);
    }

    public TextureSampler(@NonNull MagFilter minMag, @NonNull WrapMode wrap) {
        this(minFilterFromMagFilter(minMag), minMag, wrap);
    }

    public TextureSampler(@NonNull MinFilter min, @NonNull MagFilter mag, @NonNull WrapMode wrap) {
        this(min, mag, wrap, wrap, wrap);
    }

    public TextureSampler(@NonNull MinFilter min, @NonNull MagFilter mag, @NonNull WrapMode s, @NonNull WrapMode t, @NonNull WrapMode r) {
        this.mSampler = 0;
        this.mSampler = nCreateSampler(min.ordinal(), mag.ordinal(), s.ordinal(), t.ordinal(), r.ordinal());
    }

    public TextureSampler(@NonNull CompareMode mode) {
        this(mode, CompareFunction.LESS_EQUAL);
    }

    public TextureSampler(@NonNull CompareMode mode, @NonNull CompareFunction function) {
        this.mSampler = 0;
        this.mSampler = nCreateCompareSampler(mode.ordinal(), function.ordinal());
    }

    public MinFilter getMinFilter() {
        return MinFilter.values()[nGetMinFilter(this.mSampler)];
    }

    public void setMinFilter(MinFilter filter) {
        this.mSampler = nSetMinFilter(this.mSampler, filter.ordinal());
    }

    public MagFilter getMagFilter() {
        return MagFilter.values()[nGetMagFilter(this.mSampler)];
    }

    public void setMagFilter(MagFilter filter) {
        this.mSampler = nSetMagFilter(this.mSampler, filter.ordinal());
    }

    public WrapMode getWrapModeS() {
        return WrapMode.values()[nGetWrapModeS(this.mSampler)];
    }

    public void setWrapModeS(WrapMode mode) {
        this.mSampler = nSetWrapModeS(this.mSampler, mode.ordinal());
    }

    public WrapMode getWrapModeT() {
        return WrapMode.values()[nGetWrapModeT(this.mSampler)];
    }

    public void setWrapModeT(WrapMode mode) {
        this.mSampler = nSetWrapModeT(this.mSampler, mode.ordinal());
    }

    public WrapMode getWrapModeR() {
        return WrapMode.values()[nGetWrapModeR(this.mSampler)];
    }

    public void setWrapModeR(WrapMode mode) {
        this.mSampler = nSetWrapModeR(this.mSampler, mode.ordinal());
    }

    public float getAnisotropy() {
        return nGetAnisotropy(this.mSampler);
    }

    public void setAnisotropy(float anisotropy) {
        this.mSampler = nSetAnisotropy(this.mSampler, anisotropy);
    }

    public CompareMode getCompareMode() {
        return CompareMode.values()[nGetCompareMode(this.mSampler)];
    }

    public void setCompareMode(CompareMode mode) {
        this.mSampler = nSetCompareMode(this.mSampler, mode.ordinal());
    }

    public CompareFunction getCompareFunction() {
        return CompareFunction.values()[nGetCompareFunction(this.mSampler)];
    }

    public void setCompareFunction(CompareFunction function) {
        this.mSampler = nSetCompareFunction(this.mSampler, function.ordinal());
    }

    private static MinFilter minFilterFromMagFilter(@NonNull MagFilter minMag) {
        if (C03661.$SwitchMap$com$google$android$filament$TextureSampler$MagFilter[minMag.ordinal()] != 1) {
            return MinFilter.LINEAR;
        }
        return MinFilter.NEAREST;
    }
}
