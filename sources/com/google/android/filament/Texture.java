package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class Texture {
    public static final int BASE_LEVEL = 0;
    private long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder = Texture.nCreateBuilder();

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    Texture.nDestroyBuilder(this.mNativeObject);
                }
                Texture.nDestroyBuilder(this.mNativeObject);
            }
        }

        @NonNull
        public Builder width(@IntRange(from = 1) int width) {
            Texture.nBuilderWidth(this.mNativeBuilder, width);
            return this;
        }

        @NonNull
        public Builder height(@IntRange(from = 1) int height) {
            Texture.nBuilderHeight(this.mNativeBuilder, height);
            return this;
        }

        @NonNull
        public Builder depth(@IntRange(from = 1) int depth) {
            Texture.nBuilderDepth(this.mNativeBuilder, depth);
            return this;
        }

        @NonNull
        public Builder levels(@IntRange(from = 1) int levels) {
            Texture.nBuilderLevels(this.mNativeBuilder, levels);
            return this;
        }

        @NonNull
        public Builder sampler(@NonNull Sampler target) {
            Texture.nBuilderSampler(this.mNativeBuilder, target.ordinal());
            return this;
        }

        @NonNull
        public Builder format(@NonNull InternalFormat format) {
            Texture.nBuilderFormat(this.mNativeBuilder, format.ordinal());
            return this;
        }

        @NonNull
        public Texture build(@NonNull Engine engine) {
            long nativeTexture = Texture.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject());
            if (nativeTexture != 0) {
                return new Texture(nativeTexture);
            }
            throw new IllegalStateException("Couldn't create Texture");
        }
    }

    public enum CompressedFormat {
        EAC_R11,
        EAC_R11_SIGNED,
        EAC_RG11,
        EAC_RG11_SIGNED,
        ETC2_RGB8,
        ETC2_SRGB8,
        ETC2_RGB8_A1,
        ETC2_SRGB8_A1,
        ETC2_EAC_RGBA8,
        ETC2_EAC_SRGBA8,
        DXT1_RGB,
        DXT1_RGBA,
        DXT3_RGBA,
        DXT5_RGBA
    }

    public enum CubemapFace {
        POSITIVE_X,
        NEGATIVE_X,
        POSITIVE_Y,
        NEGATIVE_Y,
        POSITIVE_Z,
        NEGATIVE_Z
    }

    public enum Format {
        R,
        R_INTEGER,
        RG,
        RG_INTEGER,
        RGB,
        RGB_INTEGER,
        RGBA,
        RGBA_INTEGER,
        RGBM,
        DEPTH_COMPONENT,
        DEPTH_STENCIL,
        STENCIL_INDEX,
        ALPHA
    }

    public enum InternalFormat {
        R8,
        R8_SNORM,
        R8UI,
        R8I,
        STENCIL8,
        R16F,
        R16UI,
        R16I,
        RG8,
        RG8_SNORM,
        RG8UI,
        RG8I,
        RGB565,
        RGB9_E5,
        RGB5_A1,
        RGBA4,
        DEPTH16,
        RGB8,
        SRGB8,
        RGB8_SNORM,
        RGB8UI,
        RGB8I,
        DEPTH24,
        R32F,
        R32UI,
        R32I,
        RG16F,
        RG16UI,
        RG16I,
        R11F_G11F_B10F,
        RGBA8,
        SRGB8_A8,
        RGBA8_SNORM,
        RGBM,
        RGB10_A2,
        RGBA8UI,
        RGBA8I,
        DEPTH32F,
        DEPTH24_STENCIL8,
        DEPTH32F_STENCIL8,
        RGB16F,
        RGB16UI,
        RGB16I,
        RG32F,
        RG32UI,
        RG32I,
        RGBA16F,
        RGBA16UI,
        RGBA16I,
        RGB32F,
        RGB32UI,
        RGB32I,
        RGBA32F,
        RGBA32UI,
        RGBA32I,
        EAC_R11,
        EAC_R11_SIGNED,
        EAC_RG11,
        EAC_RG11_SIGNED,
        ETC2_RGB8,
        ETC2_SRGB8,
        ETC2_RGB8_A1,
        ETC2_SRGB8_A1,
        ETC2_EAC_RGBA8,
        ETC2_EAC_SRGBA8,
        DXT1_RGB,
        DXT1_RGBA,
        DXT3_RGBA,
        DXT5_RGBA
    }

    public static class PixelBufferDescriptor {
        public int alignment;
        @Nullable
        public Runnable callback;
        public CompressedFormat compressedFormat;
        public int compressedSizeInBytes;
        public Format format;
        @Nullable
        public Object handler;
        public int left;
        public Buffer storage;
        public int stride;
        public int top;
        public Type type;

        public PixelBufferDescriptor(@NonNull Buffer storage, @NonNull Format format, @NonNull Type type, @IntRange(from = 1, to = 8) int alignment, @IntRange(from = 0) int left, @IntRange(from = 0) int top, @IntRange(from = 0) int stride, @Nullable Object handler, @Nullable Runnable callback) {
            this.alignment = 1;
            this.left = 0;
            this.top = 0;
            this.stride = 0;
            this.storage = storage;
            this.left = left;
            this.top = top;
            this.type = type;
            this.alignment = alignment;
            this.stride = stride;
            this.format = format;
            this.handler = handler;
            this.callback = callback;
        }

        public PixelBufferDescriptor(@NonNull Buffer storage, @NonNull Format format, @NonNull Type type) {
            this(storage, format, type, 1, 0, 0, 0, null, null);
        }

        public PixelBufferDescriptor(@NonNull Buffer storage, @NonNull Format format, @NonNull Type type, @IntRange(from = 1, to = 8) int alignment) {
            this(storage, format, type, alignment, 0, 0, 0, null, null);
        }

        public PixelBufferDescriptor(@NonNull Buffer storage, @NonNull Format format, @NonNull Type type, @IntRange(from = 1, to = 8) int alignment, @IntRange(from = 0) int left, @IntRange(from = 0) int top) {
            this(storage, format, type, alignment, left, top, 0, null, null);
        }

        public PixelBufferDescriptor(@NonNull ByteBuffer storage, @NonNull CompressedFormat format, @IntRange(from = 0) int compressedSizeInBytes) {
            this.alignment = 1;
            this.left = 0;
            this.top = 0;
            this.stride = 0;
            this.storage = storage;
            this.type = Type.COMPRESSED;
            this.alignment = 1;
            this.compressedFormat = format;
            this.compressedSizeInBytes = compressedSizeInBytes;
        }

        public void setCallback(@Nullable Object handler, @Nullable Runnable callback) {
            this.handler = handler;
            this.callback = callback;
        }

        static int computeDataSize(@NonNull Format format, @NonNull Type type, int stride, int height, @IntRange(from = 1, to = 8) int alignment) {
            if (type == Type.COMPRESSED) {
                return 0;
            }
            int n = 0;
            switch (format) {
                case R:
                case R_INTEGER:
                case DEPTH_COMPONENT:
                case ALPHA:
                    n = 1;
                    break;
                case RG:
                case RG_INTEGER:
                case DEPTH_STENCIL:
                case STENCIL_INDEX:
                    n = 2;
                    break;
                case RGB:
                case RGB_INTEGER:
                    n = 3;
                    break;
                case RGBA:
                case RGBA_INTEGER:
                case RGBM:
                    n = 4;
                    break;
                default:
                    break;
            }
            int bpp = n;
            switch (type) {
                case UBYTE:
                case BYTE:
                    break;
                case USHORT:
                case SHORT:
                case HALF:
                    bpp *= 2;
                    break;
                case UINT:
                case INT:
                case FLOAT:
                    bpp *= 4;
                    break;
                default:
                    break;
            }
            return (((alignment - 1) + (bpp * stride)) & (-alignment)) * height;
        }
    }

    public enum Sampler {
        SAMPLER_2D,
        SAMPLER_CUBEMAP,
        SAMPLER_EXTERNAL
    }

    public enum Type {
        UBYTE,
        BYTE,
        USHORT,
        SHORT,
        UINT,
        INT,
        HALF,
        FLOAT,
        COMPRESSED
    }

    private static native long nBuilderBuild(long j, long j2);

    private static native void nBuilderDepth(long j, int i);

    private static native void nBuilderFormat(long j, int i);

    private static native void nBuilderHeight(long j, int i);

    private static native void nBuilderLevels(long j, int i);

    private static native void nBuilderSampler(long j, int i);

    private static native void nBuilderWidth(long j, int i);

    private static native long nCreateBuilder();

    private static native void nDestroyBuilder(long j);

    private static native void nGenerateMipmaps(long j, long j2);

    private static native int nGetDepth(long j, int i);

    private static native int nGetHeight(long j, int i);

    private static native int nGetInternalFormat(long j);

    private static native int nGetLevels(long j);

    private static native int nGetTarget(long j);

    private static native int nGetWidth(long j, int i);

    private static native boolean nIsStreamValidForTexture(long j, long j2);

    private static native boolean nIsTextureFormatSupported(long j, int i);

    private static native void nSetExternalImage(long j, long j2, long j3);

    private static native void nSetExternalStream(long j, long j2, long j3);

    private static native int nSetImage(long j, long j2, int i, int i2, int i3, int i4, int i5, Buffer buffer, int i6, int i7, int i8, int i9, int i10, int i11, int i12, Object obj, Runnable runnable);

    private static native int nSetImageCompressed(long j, long j2, int i, int i2, int i3, int i4, int i5, Buffer buffer, int i6, int i7, int i8, int i9, int i10, int i11, int i12, Object obj, Runnable runnable);

    private static native int nSetImageCubemap(long j, long j2, int i, Buffer buffer, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, Object obj, Runnable runnable);

    private static native int nSetImageCubemapCompressed(long j, long j2, int i, Buffer buffer, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, Object obj, Runnable runnable);

    Texture(long nativeTexture) {
        this.mNativeObject = nativeTexture;
    }

    public static boolean isTextureFormatSupported(@NonNull Engine engine, @NonNull InternalFormat format) {
        return nIsTextureFormatSupported(engine.getNativeObject(), format.ordinal());
    }

    public int getWidth(@IntRange(from = 0) int level) {
        return nGetWidth(getNativeObject(), level);
    }

    public int getHeight(@IntRange(from = 0) int level) {
        return nGetHeight(getNativeObject(), level);
    }

    public int getDepth(@IntRange(from = 0) int level) {
        return nGetDepth(getNativeObject(), level);
    }

    public int getLevels() {
        return nGetLevels(getNativeObject());
    }

    @NonNull
    public Sampler getTarget() {
        return Sampler.values()[nGetTarget(getNativeObject())];
    }

    @NonNull
    public InternalFormat getFormat() {
        return InternalFormat.values()[nGetInternalFormat(getNativeObject())];
    }

    public void setImage(@NonNull Engine engine, @IntRange(from = 0) int level, @NonNull PixelBufferDescriptor buffer) {
        setImage(engine, level, 0, 0, getWidth(level), getHeight(level), buffer);
    }

    public void setImage(@NonNull Engine engine, @IntRange(from = 0) int level, @IntRange(from = 0) int xoffset, @IntRange(from = 0) int yoffset, @IntRange(from = 0) int width, @IntRange(from = 0) int height, @NonNull PixelBufferDescriptor buffer) {
        int result;
        PixelBufferDescriptor pixelBufferDescriptor = buffer;
        if (pixelBufferDescriptor.type == Type.COMPRESSED) {
            result = nSetImageCompressed(getNativeObject(), engine.getNativeObject(), level, xoffset, yoffset, width, height, pixelBufferDescriptor.storage, pixelBufferDescriptor.storage.remaining(), pixelBufferDescriptor.left, pixelBufferDescriptor.top, pixelBufferDescriptor.type.ordinal(), pixelBufferDescriptor.alignment, pixelBufferDescriptor.compressedSizeInBytes, pixelBufferDescriptor.compressedFormat.ordinal(), pixelBufferDescriptor.handler, pixelBufferDescriptor.callback);
        } else {
            result = nSetImage(getNativeObject(), engine.getNativeObject(), level, xoffset, yoffset, width, height, pixelBufferDescriptor.storage, pixelBufferDescriptor.storage.remaining(), pixelBufferDescriptor.left, pixelBufferDescriptor.top, pixelBufferDescriptor.type.ordinal(), pixelBufferDescriptor.alignment, pixelBufferDescriptor.stride, pixelBufferDescriptor.format.ordinal(), pixelBufferDescriptor.handler, pixelBufferDescriptor.callback);
        }
        if (result < 0) {
            throw new BufferOverflowException();
        }
    }

    public void setImage(@NonNull Engine engine, @IntRange(from = 0) int level, @NonNull PixelBufferDescriptor buffer, @Size(min = 6) @NonNull int[] faceOffsetsInBytes) {
        int result;
        PixelBufferDescriptor pixelBufferDescriptor = buffer;
        if (pixelBufferDescriptor.type == Type.COMPRESSED) {
            result = nSetImageCubemapCompressed(getNativeObject(), engine.getNativeObject(), level, pixelBufferDescriptor.storage, pixelBufferDescriptor.storage.remaining(), pixelBufferDescriptor.left, pixelBufferDescriptor.top, pixelBufferDescriptor.type.ordinal(), pixelBufferDescriptor.alignment, pixelBufferDescriptor.compressedSizeInBytes, pixelBufferDescriptor.compressedFormat.ordinal(), faceOffsetsInBytes, pixelBufferDescriptor.handler, pixelBufferDescriptor.callback);
        } else {
            result = nSetImageCubemap(getNativeObject(), engine.getNativeObject(), level, pixelBufferDescriptor.storage, pixelBufferDescriptor.storage.remaining(), pixelBufferDescriptor.left, pixelBufferDescriptor.top, pixelBufferDescriptor.type.ordinal(), pixelBufferDescriptor.alignment, pixelBufferDescriptor.stride, pixelBufferDescriptor.format.ordinal(), faceOffsetsInBytes, pixelBufferDescriptor.handler, pixelBufferDescriptor.callback);
        }
        if (result < 0) {
            throw new BufferOverflowException();
        }
    }

    public void setExternalImage(@NonNull Engine engine, long eglImage) {
        nSetExternalImage(getNativeObject(), engine.getNativeObject(), eglImage);
    }

    public void setExternalStream(@NonNull Engine engine, @NonNull Stream stream) {
        long nativeObject = getNativeObject();
        long streamNativeObject = stream.getNativeObject();
        if (nIsStreamValidForTexture(nativeObject, streamNativeObject)) {
            nSetExternalStream(nativeObject, engine.getNativeObject(), streamNativeObject);
            return;
        }
        throw new IllegalStateException("Invalid texture sampler: When used with a stream, a texture must use a SAMPLER_EXTERNAL");
    }

    public void generateMipmaps(@NonNull Engine engine) {
        nGenerateMipmaps(getNativeObject(), engine.getNativeObject());
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Texture");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
