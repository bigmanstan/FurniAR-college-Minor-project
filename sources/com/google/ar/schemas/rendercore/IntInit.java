package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class IntInit extends Table {
    public static void addValue(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(0, i, 0);
    }

    public static int createIntInit(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startObject(1);
        addValue(flatBufferBuilder, i);
        return endIntInit(flatBufferBuilder);
    }

    public static int endIntInit(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static IntInit getRootAsIntInit(ByteBuffer byteBuffer) {
        return getRootAsIntInit(byteBuffer, new IntInit());
    }

    public static IntInit getRootAsIntInit(ByteBuffer byteBuffer, IntInit intInit) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return intInit.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startIntInit(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final IntInit __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final int value() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }
}
