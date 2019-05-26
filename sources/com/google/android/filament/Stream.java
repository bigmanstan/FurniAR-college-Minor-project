package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import com.google.android.filament.Texture.PixelBufferDescriptor;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ReadOnlyBufferException;

public class Stream {
    private long mNativeEngine;
    private long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder = Stream.nCreateBuilder();

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    Stream.nDestroyBuilder(this.mNativeObject);
                }
                Stream.nDestroyBuilder(this.mNativeObject);
            }
        }

        @NonNull
        public Builder stream(@NonNull Object streamSource) {
            if (Platform.get().validateStreamSource(streamSource)) {
                Stream.nBuilderStreamSource(this.mNativeBuilder, streamSource);
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid stream source: ");
            stringBuilder.append(streamSource);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @NonNull
        public Builder stream(long externalTextureId) {
            Stream.nBuilderStream(this.mNativeBuilder, externalTextureId);
            return this;
        }

        @NonNull
        public Builder width(int width) {
            Stream.nBuilderWidth(this.mNativeBuilder, width);
            return this;
        }

        @NonNull
        public Builder height(int height) {
            Stream.nBuilderHeight(this.mNativeBuilder, height);
            return this;
        }

        @NonNull
        public Stream build(@NonNull Engine engine) {
            long nativeStream = Stream.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject());
            if (nativeStream != 0) {
                return new Stream(nativeStream, engine);
            }
            throw new IllegalStateException("Couldn't create Stream");
        }
    }

    private static native long nBuilderBuild(long j, long j2);

    private static native void nBuilderHeight(long j, int i);

    private static native void nBuilderStream(long j, long j2);

    private static native void nBuilderStreamSource(long j, Object obj);

    private static native void nBuilderWidth(long j, int i);

    private static native long nCreateBuilder();

    private static native void nDestroyBuilder(long j);

    private static native boolean nIsNative(long j);

    private static native int nReadPixels(long j, long j2, int i, int i2, int i3, int i4, Buffer buffer, int i5, int i6, int i7, int i8, int i9, int i10, int i11, Object obj, Runnable runnable);

    private static native void nSetDimensions(long j, int i, int i2);

    Stream(long nativeStream, Engine engine) {
        this.mNativeObject = nativeStream;
        this.mNativeEngine = engine.getNativeObject();
    }

    public boolean isNative() {
        return nIsNative(getNativeObject());
    }

    public void setDimensions(@IntRange(from = 0) int width, @IntRange(from = 0) int height) {
        nSetDimensions(getNativeObject(), width, height);
    }

    public void readPixels(@IntRange(from = 0) int xoffset, @IntRange(from = 0) int yoffset, @IntRange(from = 0) int width, @IntRange(from = 0) int height, @NonNull PixelBufferDescriptor buffer) {
        PixelBufferDescriptor pixelBufferDescriptor = buffer;
        if (pixelBufferDescriptor.storage.isReadOnly()) {
            Stream stream = this;
            throw new ReadOnlyBufferException();
        }
        if (nReadPixels(getNativeObject(), this.mNativeEngine, xoffset, yoffset, width, height, pixelBufferDescriptor.storage, pixelBufferDescriptor.storage.remaining(), pixelBufferDescriptor.left, pixelBufferDescriptor.top, pixelBufferDescriptor.type.ordinal(), pixelBufferDescriptor.alignment, pixelBufferDescriptor.stride, pixelBufferDescriptor.format.ordinal(), pixelBufferDescriptor.handler, pixelBufferDescriptor.callback) < 0) {
            throw new BufferOverflowException();
        }
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Stream");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
