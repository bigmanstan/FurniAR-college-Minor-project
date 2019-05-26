package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class IntVec4Init extends Table {
    public static void addW(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(3, i, 0);
    }

    public static void addX(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(0, i, 0);
    }

    public static void addY(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(1, i, 0);
    }

    public static void addZ(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(2, i, 0);
    }

    public static int createIntVec4Init(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4) {
        flatBufferBuilder.startObject(4);
        addW(flatBufferBuilder, i4);
        addZ(flatBufferBuilder, i3);
        addY(flatBufferBuilder, i2);
        addX(flatBufferBuilder, i);
        return endIntVec4Init(flatBufferBuilder);
    }

    public static int endIntVec4Init(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static IntVec4Init getRootAsIntVec4Init(ByteBuffer byteBuffer) {
        return getRootAsIntVec4Init(byteBuffer, new IntVec4Init());
    }

    public static IntVec4Init getRootAsIntVec4Init(ByteBuffer byteBuffer, IntVec4Init intVec4Init) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return intVec4Init.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startIntVec4Init(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(4);
    }

    public final IntVec4Init __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: w */
    public final int m223w() {
        int __offset = __offset(10);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }

    /* renamed from: x */
    public final int m224x() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }

    /* renamed from: y */
    public final int m225y() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }

    /* renamed from: z */
    public final int m226z() {
        int __offset = __offset(8);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }
}
