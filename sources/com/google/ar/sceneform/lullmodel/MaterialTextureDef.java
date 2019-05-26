package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class MaterialTextureDef extends Table {
    public static void addName(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addUsage(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(1, i, 0);
    }

    public static int createMaterialTextureDef(FlatBufferBuilder flatBufferBuilder, int i, int i2) {
        flatBufferBuilder.startObject(2);
        addUsage(flatBufferBuilder, i2);
        addName(flatBufferBuilder, i);
        return endMaterialTextureDef(flatBufferBuilder);
    }

    public static int endMaterialTextureDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static MaterialTextureDef getRootAsMaterialTextureDef(ByteBuffer byteBuffer) {
        return getRootAsMaterialTextureDef(byteBuffer, new MaterialTextureDef());
    }

    public static MaterialTextureDef getRootAsMaterialTextureDef(ByteBuffer byteBuffer, MaterialTextureDef materialTextureDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return materialTextureDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startMaterialTextureDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final MaterialTextureDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final String name() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer nameAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public final int usage() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }
}
