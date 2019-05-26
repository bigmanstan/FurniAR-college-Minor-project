package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.nio.Buffer;
import java.nio.BufferOverflowException;

public class IndexBuffer {
    private long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder = IndexBuffer.nCreateBuilder();

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    IndexBuffer.nDestroyBuilder(this.mNativeObject);
                }
                IndexBuffer.nDestroyBuilder(this.mNativeObject);
            }
        }

        public enum IndexType {
            USHORT,
            UINT
        }

        @NonNull
        public Builder indexCount(@IntRange(from = 1) int indexCount) {
            IndexBuffer.nBuilderIndexCount(this.mNativeBuilder, indexCount);
            return this;
        }

        @NonNull
        public Builder bufferType(@NonNull IndexType indexType) {
            IndexBuffer.nBuilderBufferType(this.mNativeBuilder, indexType.ordinal());
            return this;
        }

        @NonNull
        public IndexBuffer build(@NonNull Engine engine) {
            long nativeIndexBuffer = IndexBuffer.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject());
            if (nativeIndexBuffer != 0) {
                return new IndexBuffer(nativeIndexBuffer);
            }
            throw new IllegalStateException("Couldn't create IndexBuffer");
        }
    }

    private static native void nBuilderBufferType(long j, int i);

    private static native long nBuilderBuild(long j, long j2);

    private static native void nBuilderIndexCount(long j, int i);

    private static native long nCreateBuilder();

    private static native void nDestroyBuilder(long j);

    private static native int nGetIndexCount(long j);

    private static native int nSetBuffer(long j, long j2, Buffer buffer, int i, int i2, int i3, Object obj, Runnable runnable);

    private IndexBuffer(long nativeIndexBuffer) {
        this.mNativeObject = nativeIndexBuffer;
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed IndexBuffer");
    }

    @IntRange(from = 0)
    public int getIndexCount() {
        return nGetIndexCount(getNativeObject());
    }

    public void setBuffer(@NonNull Engine engine, @NonNull Buffer buffer) {
        setBuffer(engine, buffer, 0, 0, null, null);
    }

    public void setBuffer(@NonNull Engine engine, @NonNull Buffer buffer, @IntRange(from = 0) int destOffsetInBytes, @IntRange(from = 0) int count) {
        setBuffer(engine, buffer, destOffsetInBytes, count, null, null);
    }

    public void setBuffer(@NonNull Engine engine, @NonNull Buffer buffer, @IntRange(from = 0) int destOffsetInBytes, @IntRange(from = 0) int count, @Nullable Object handler, @Nullable Runnable callback) {
        int remaining;
        int result = getNativeObject();
        long nativeObject = engine.getNativeObject();
        int remaining2 = buffer.remaining();
        if (count == 0) {
            remaining = buffer.remaining();
        } else {
            remaining = count;
        }
        if (nSetBuffer(result, nativeObject, buffer, remaining2, destOffsetInBytes, remaining, handler, callback) < 0) {
            throw new BufferOverflowException();
        }
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
