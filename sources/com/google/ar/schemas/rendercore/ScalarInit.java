package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ScalarInit extends Table {
    public static void addValue(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(0, f, 0.0d);
    }

    public static int createScalarInit(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.startObject(1);
        addValue(flatBufferBuilder, f);
        return endScalarInit(flatBufferBuilder);
    }

    public static int endScalarInit(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ScalarInit getRootAsScalarInit(ByteBuffer byteBuffer) {
        return getRootAsScalarInit(byteBuffer, new ScalarInit());
    }

    public static ScalarInit getRootAsScalarInit(ByteBuffer byteBuffer, ScalarInit scalarInit) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return scalarInit.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startScalarInit(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final ScalarInit __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final float value() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }
}
