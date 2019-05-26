package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class BoolInit extends Table {
    public static void addValue(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(0, z, false);
    }

    public static int createBoolInit(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.startObject(1);
        addValue(flatBufferBuilder, z);
        return endBoolInit(flatBufferBuilder);
    }

    public static int endBoolInit(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static BoolInit getRootAsBoolInit(ByteBuffer byteBuffer) {
        return getRootAsBoolInit(byteBuffer, new BoolInit());
    }

    public static BoolInit getRootAsBoolInit(ByteBuffer byteBuffer, BoolInit boolInit) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return boolInit.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startBoolInit(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final BoolInit __assign(int i, ByteBuffer byteBuffer) {
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
