package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.nio.Buffer;
import java.nio.BufferOverflowException;

public class VertexBuffer {
    private long mNativeObject;

    public enum AttributeType {
        BYTE,
        BYTE2,
        BYTE3,
        BYTE4,
        UBYTE,
        UBYTE2,
        UBYTE3,
        UBYTE4,
        SHORT,
        SHORT2,
        SHORT3,
        SHORT4,
        USHORT,
        USHORT2,
        USHORT3,
        USHORT4,
        INT,
        UINT,
        FLOAT,
        FLOAT2,
        FLOAT3,
        FLOAT4,
        HALF,
        HALF2,
        HALF3,
        HALF4
    }

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder = VertexBuffer.nCreateBuilder();

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    VertexBuffer.nDestroyBuilder(this.mNativeObject);
                }
                VertexBuffer.nDestroyBuilder(this.mNativeObject);
            }
        }

        @NonNull
        public Builder vertexCount(@IntRange(from = 1) int vertexCount) {
            VertexBuffer.nBuilderVertexCount(this.mNativeBuilder, vertexCount);
            return this;
        }

        @NonNull
        public Builder bufferCount(@IntRange(from = 1) int bufferCount) {
            VertexBuffer.nBuilderBufferCount(this.mNativeBuilder, bufferCount);
            return this;
        }

        @NonNull
        public Builder attribute(@NonNull VertexAttribute attribute, @IntRange(from = 1) int bufferIndex, @NonNull AttributeType attributeType, @IntRange(from = 0) int byteOffset, @IntRange(from = 0) int byteStride) {
            VertexBuffer.nBuilderAttribute(this.mNativeBuilder, attribute.ordinal(), bufferIndex, attributeType.ordinal(), byteOffset, byteStride);
            return this;
        }

        @NonNull
        public Builder attribute(@NonNull VertexAttribute attribute, @IntRange(from = 1) int bufferIndex, @NonNull AttributeType attributeType) {
            return attribute(attribute, bufferIndex, attributeType, 0, 0);
        }

        @NonNull
        public Builder normalized(@NonNull VertexAttribute attribute) {
            VertexBuffer.nBuilderNormalized(this.mNativeBuilder, attribute.ordinal());
            return this;
        }

        @NonNull
        public VertexBuffer build(@NonNull Engine engine) {
            long nativeVertexBuffer = VertexBuffer.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject());
            if (nativeVertexBuffer != 0) {
                return new VertexBuffer(nativeVertexBuffer);
            }
            throw new IllegalStateException("Couldn't create VertexBuffer");
        }
    }

    public enum VertexAttribute {
        POSITION,
        TANGENTS,
        COLOR,
        UV0,
        UV1,
        BONE_INDICES,
        BONE_WEIGHTS
    }

    private static native void nBuilderAttribute(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nBuilderBufferCount(long j, int i);

    private static native long nBuilderBuild(long j, long j2);

    private static native void nBuilderNormalized(long j, int i);

    private static native void nBuilderVertexCount(long j, int i);

    private static native long nCreateBuilder();

    private static native void nDestroyBuilder(long j);

    private static native int nGetVertexCount(long j);

    private static native int nSetBufferAt(long j, long j2, int i, Buffer buffer, int i2, int i3, int i4, Object obj, Runnable runnable);

    private VertexBuffer(long nativeVertexBuffer) {
        this.mNativeObject = nativeVertexBuffer;
    }

    @IntRange(from = 0)
    public int getVertexCount() {
        return nGetVertexCount(getNativeObject());
    }

    public void setBufferAt(@NonNull Engine engine, int bufferIndex, @NonNull Buffer buffer) {
        setBufferAt(engine, bufferIndex, buffer, 0, 0, null, null);
    }

    public void setBufferAt(@NonNull Engine engine, int bufferIndex, @NonNull Buffer buffer, @IntRange(from = 0) int destOffsetInBytes, @IntRange(from = 0) int count) {
        setBufferAt(engine, bufferIndex, buffer, destOffsetInBytes, count, null, null);
    }

    public void setBufferAt(@NonNull Engine engine, int bufferIndex, @NonNull Buffer buffer, @IntRange(from = 0) int destOffsetInBytes, @IntRange(from = 0) int count, @Nullable Object handler, @Nullable Runnable callback) {
        int remaining;
        int result = getNativeObject();
        long nativeObject = engine.getNativeObject();
        int remaining2 = buffer.remaining();
        if (count == 0) {
            remaining = buffer.remaining();
        } else {
            remaining = count;
        }
        if (nSetBufferAt(result, nativeObject, bufferIndex, buffer, remaining2, destOffsetInBytes, remaining, handler, callback) < 0) {
            throw new BufferOverflowException();
        }
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed VertexBuffer");
    }

    void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
