package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ParameterInitDef extends Table {
    public static void addInit(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addInitType(FlatBufferBuilder flatBufferBuilder, byte b) {
        flatBufferBuilder.addByte(0, b, 0);
    }

    public static int createParameterInitDef(FlatBufferBuilder flatBufferBuilder, byte b, int i) {
        flatBufferBuilder.startObject(2);
        addInit(flatBufferBuilder, i);
        addInitType(flatBufferBuilder, b);
        return endParameterInitDef(flatBufferBuilder);
    }

    public static int endParameterInitDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ParameterInitDef getRootAsParameterInitDef(ByteBuffer byteBuffer) {
        return getRootAsParameterInitDef(byteBuffer, new ParameterInitDef());
    }

    public static ParameterInitDef getRootAsParameterInitDef(ByteBuffer byteBuffer, ParameterInitDef parameterInitDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return parameterInitDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startParameterInitDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final ParameterInitDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final Table init(Table table) {
        int __offset = __offset(6);
        return __offset != 0 ? __union(table, __offset) : null;
    }

    public final byte initType() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.get(__offset + this.bb_pos) : (byte) 0;
    }
}
