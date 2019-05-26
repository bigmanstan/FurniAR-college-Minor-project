package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ParameterDef extends Table {
    public static void addId(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addInitialValue(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static int createParameterDef(FlatBufferBuilder flatBufferBuilder, int i, int i2) {
        flatBufferBuilder.startObject(2);
        addInitialValue(flatBufferBuilder, i2);
        addId(flatBufferBuilder, i);
        return endParameterDef(flatBufferBuilder);
    }

    public static int endParameterDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ParameterDef getRootAsParameterDef(ByteBuffer byteBuffer) {
        return getRootAsParameterDef(byteBuffer, new ParameterDef());
    }

    public static ParameterDef getRootAsParameterDef(ByteBuffer byteBuffer, ParameterDef parameterDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return parameterDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startParameterDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final ParameterDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final String id() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer idAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public final ParameterInitDef initialValue() {
        return initialValue(new ParameterInitDef());
    }

    public final ParameterInitDef initialValue(ParameterInitDef parameterInitDef) {
        int __offset = __offset(6);
        return __offset != 0 ? parameterInitDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }
}
