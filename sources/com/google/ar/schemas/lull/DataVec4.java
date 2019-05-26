package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DataVec4 extends Table {
    public static void addValue(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addStruct(0, i, 0);
    }

    public static int endDataVec4(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static DataVec4 getRootAsDataVec4(ByteBuffer byteBuffer) {
        return getRootAsDataVec4(byteBuffer, new DataVec4());
    }

    public static DataVec4 getRootAsDataVec4(ByteBuffer byteBuffer, DataVec4 dataVec4) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return dataVec4.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startDataVec4(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final DataVec4 __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final Vec4 value() {
        return value(new Vec4());
    }

    public final Vec4 value(Vec4 vec4) {
        int __offset = __offset(4);
        return __offset != 0 ? vec4.__assign(__offset + this.bb_pos, this.bb) : null;
    }
}
