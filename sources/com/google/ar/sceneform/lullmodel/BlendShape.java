package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class BlendShape extends Table {
    public static void addName(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(0, (int) j, 0);
    }

    public static void addVertexData(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static int createBlendShape(FlatBufferBuilder flatBufferBuilder, long j, int i) {
        flatBufferBuilder.startObject(2);
        addVertexData(flatBufferBuilder, i);
        addName(flatBufferBuilder, j);
        return endBlendShape(flatBufferBuilder);
    }

    public static int createVertexDataVector(FlatBufferBuilder flatBufferBuilder, byte[] bArr) {
        flatBufferBuilder.startVector(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addByte(bArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int endBlendShape(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static BlendShape getRootAsBlendShape(ByteBuffer byteBuffer) {
        return getRootAsBlendShape(byteBuffer, new BlendShape());
    }

    public static BlendShape getRootAsBlendShape(ByteBuffer byteBuffer, BlendShape blendShape) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return blendShape.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startBlendShape(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public static void startVertexDataVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(1, i, 1);
    }

    public final BlendShape __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final long name() {
        int __offset = __offset(4);
        return __offset != 0 ? ((long) this.bb.getInt(__offset + this.bb_pos)) & 4294967295L : 0;
    }

    public final int vertexData(int i) {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.get(__vector(__offset) + i) & 255 : 0;
    }

    public final ByteBuffer vertexDataAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public final int vertexDataLength() {
        int __offset = __offset(6);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }
}
