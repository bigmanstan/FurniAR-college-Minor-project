package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DataFloat extends Table {
    public static void addValue(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(0, f, 0.0d);
    }

    public static int createDataFloat(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.startObject(1);
        addValue(flatBufferBuilder, f);
        return endDataFloat(flatBufferBuilder);
    }

    public static int endDataFloat(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static DataFloat getRootAsDataFloat(ByteBuffer byteBuffer) {
        return getRootAsDataFloat(byteBuffer, new DataFloat());
    }

    public static DataFloat getRootAsDataFloat(ByteBuffer byteBuffer, DataFloat dataFloat) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return dataFloat.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startDataFloat(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final DataFloat __assign(int i, ByteBuffer byteBuffer) {
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
