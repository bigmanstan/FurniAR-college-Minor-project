package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class NullInit extends Table {
    public static int endNullInit(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static NullInit getRootAsNullInit(ByteBuffer byteBuffer) {
        return getRootAsNullInit(byteBuffer, new NullInit());
    }

    public static NullInit getRootAsNullInit(ByteBuffer byteBuffer, NullInit nullInit) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return nullInit.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startNullInit(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(0);
    }

    public final NullInit __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }
}
