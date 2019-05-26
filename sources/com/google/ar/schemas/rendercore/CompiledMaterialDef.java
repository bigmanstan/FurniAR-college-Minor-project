package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class CompiledMaterialDef extends Table {
    public static void addCompiledMaterial(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addCompressedMaterial(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void addDecl(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addSha1sum(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static int createCompiledMaterialDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4) {
        flatBufferBuilder.startObject(4);
        addCompressedMaterial(flatBufferBuilder, i4);
        addDecl(flatBufferBuilder, i3);
        addSha1sum(flatBufferBuilder, i2);
        addCompiledMaterial(flatBufferBuilder, i);
        return endCompiledMaterialDef(flatBufferBuilder);
    }

    public static int createCompiledMaterialVector(FlatBufferBuilder flatBufferBuilder, byte[] bArr) {
        flatBufferBuilder.startVector(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addByte(bArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int endCompiledMaterialDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static CompiledMaterialDef getRootAsCompiledMaterialDef(ByteBuffer byteBuffer) {
        return getRootAsCompiledMaterialDef(byteBuffer, new CompiledMaterialDef());
    }

    public static CompiledMaterialDef getRootAsCompiledMaterialDef(ByteBuffer byteBuffer, CompiledMaterialDef compiledMaterialDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return compiledMaterialDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startCompiledMaterialDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(4);
    }

    public static void startCompiledMaterialVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(1, i, 1);
    }

    public final CompiledMaterialDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final int compiledMaterial(int i) {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.get(__vector(__offset) + i) & 255 : 0;
    }

    public final ByteBuffer compiledMaterialAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public final int compiledMaterialLength() {
        int __offset = __offset(4);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final String compressedMaterial() {
        int __offset = __offset(10);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer compressedMaterialAsByteBuffer() {
        return __vector_as_bytebuffer(10, 1);
    }

    public final CompiledMaterialDeclDef decl() {
        return decl(new CompiledMaterialDeclDef());
    }

    public final CompiledMaterialDeclDef decl(CompiledMaterialDeclDef compiledMaterialDeclDef) {
        int __offset = __offset(8);
        return __offset != 0 ? compiledMaterialDeclDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }

    public final String sha1sum() {
        int __offset = __offset(6);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer sha1sumAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }
}
