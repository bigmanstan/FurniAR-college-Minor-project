package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class InputDef extends Table {
    public static void addHash(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addPath(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createInputDef(FlatBufferBuilder flatBufferBuilder, int i, int i2) {
        flatBufferBuilder.startObject(2);
        addHash(flatBufferBuilder, i2);
        addPath(flatBufferBuilder, i);
        return endInputDef(flatBufferBuilder);
    }

    public static int endInputDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static InputDef getRootAsInputDef(ByteBuffer byteBuffer) {
        return getRootAsInputDef(byteBuffer, new InputDef());
    }

    public static InputDef getRootAsInputDef(ByteBuffer byteBuffer, InputDef inputDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return inputDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startInputDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final InputDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final String hash() {
        int __offset = __offset(6);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer hashAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public final String path() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer pathAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }
}
