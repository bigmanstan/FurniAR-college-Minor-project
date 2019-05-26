package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class BoolVec4Init extends Table {
    public static void addW(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(3, z, false);
    }

    public static void addX(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(0, z, false);
    }

    public static void addY(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(1, z, false);
    }

    public static void addZ(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(2, z, false);
    }

    public static int createBoolVec4Init(FlatBufferBuilder flatBufferBuilder, boolean z, boolean z2, boolean z3, boolean z4) {
        flatBufferBuilder.startObject(4);
        addW(flatBufferBuilder, z4);
        addZ(flatBufferBuilder, z3);
        addY(flatBufferBuilder, z2);
        addX(flatBufferBuilder, z);
        return endBoolVec4Init(flatBufferBuilder);
    }

    public static int endBoolVec4Init(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static BoolVec4Init getRootAsBoolVec4Init(ByteBuffer byteBuffer) {
        return getRootAsBoolVec4Init(byteBuffer, new BoolVec4Init());
    }

    public static BoolVec4Init getRootAsBoolVec4Init(ByteBuffer byteBuffer, BoolVec4Init boolVec4Init) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return boolVec4Init.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startBoolVec4Init(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(4);
    }

    public final BoolVec4Init __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: w */
    public final boolean m214w() {
        int __offset = __offset(10);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    /* renamed from: x */
    public final boolean m215x() {
        int __offset = __offset(4);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    /* renamed from: y */
    public final boolean m216y() {
        int __offset = __offset(6);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    /* renamed from: z */
    public final boolean m217z() {
        int __offset = __offset(8);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }
}
