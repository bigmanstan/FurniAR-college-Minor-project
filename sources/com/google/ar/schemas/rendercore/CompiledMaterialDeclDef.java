package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class CompiledMaterialDeclDef extends Table {
    public static void addMatSha1sum(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addSource(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createCompiledMaterialDeclDef(FlatBufferBuilder flatBufferBuilder, int i, int i2) {
        flatBufferBuilder.startObject(2);
        addMatSha1sum(flatBufferBuilder, i2);
        addSource(flatBufferBuilder, i);
        return endCompiledMaterialDeclDef(flatBufferBuilder);
    }

    public static int endCompiledMaterialDeclDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static CompiledMaterialDeclDef getRootAsCompiledMaterialDeclDef(ByteBuffer byteBuffer) {
        return getRootAsCompiledMaterialDeclDef(byteBuffer, new CompiledMaterialDeclDef());
    }

    public static CompiledMaterialDeclDef getRootAsCompiledMaterialDeclDef(ByteBuffer byteBuffer, CompiledMaterialDeclDef compiledMaterialDeclDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return compiledMaterialDeclDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startCompiledMaterialDeclDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final CompiledMaterialDeclDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final String matSha1sum() {
        int __offset = __offset(6);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer matSha1sumAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public final String source() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer sourceAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }
}
