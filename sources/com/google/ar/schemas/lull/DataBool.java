package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DataBool extends Table {
    public static void addValue(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(0, z, false);
    }

    public static int createDataBool(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.startObject(1);
        addValue(flatBufferBuilder, z);
        return endDataBool(flatBufferBuilder);
    }

    public static int endDataBool(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static DataBool getRootAsDataBool(ByteBuffer byteBuffer) {
        return getRootAsDataBool(byteBuffer, new DataBool());
    }

    public static DataBool getRootAsDataBool(ByteBuffer byteBuffer, DataBool dataBool) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return dataBool.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startDataBool(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final DataBool __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final boolean value() {
        int __offset = __offset(4);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }
}
