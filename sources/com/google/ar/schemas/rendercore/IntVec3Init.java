package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class IntVec3Init extends Table {
    public static void addX(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(0, i, 0);
    }

    public static void addY(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(1, i, 0);
    }

    public static void addZ(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(2, i, 0);
    }

    public static int createIntVec3Init(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3) {
        flatBufferBuilder.startObject(3);
        addZ(flatBufferBuilder, i3);
        addY(flatBufferBuilder, i2);
        addX(flatBufferBuilder, i);
        return endIntVec3Init(flatBufferBuilder);
    }

    public static int endIntVec3Init(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static IntVec3Init getRootAsIntVec3Init(ByteBuffer byteBuffer) {
        return getRootAsIntVec3Init(byteBuffer, new IntVec3Init());
    }

    public static IntVec3Init getRootAsIntVec3Init(ByteBuffer byteBuffer, IntVec3Init intVec3Init) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return intVec3Init.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startIntVec3Init(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(3);
    }

    public final IntVec3Init __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final int m220x() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }

    /* renamed from: y */
    public final int m221y() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }

    /* renamed from: z */
    public final int m222z() {
        int __offset = __offset(8);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }
}
