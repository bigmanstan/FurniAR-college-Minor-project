package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class BoolVec3Init extends Table {
    public static void addX(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(0, z, false);
    }

    public static void addY(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(1, z, false);
    }

    public static void addZ(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(2, z, false);
    }

    public static int createBoolVec3Init(FlatBufferBuilder flatBufferBuilder, boolean z, boolean z2, boolean z3) {
        flatBufferBuilder.startObject(3);
        addZ(flatBufferBuilder, z3);
        addY(flatBufferBuilder, z2);
        addX(flatBufferBuilder, z);
        return endBoolVec3Init(flatBufferBuilder);
    }

    public static int endBoolVec3Init(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static BoolVec3Init getRootAsBoolVec3Init(ByteBuffer byteBuffer) {
        return getRootAsBoolVec3Init(byteBuffer, new BoolVec3Init());
    }

    public static BoolVec3Init getRootAsBoolVec3Init(ByteBuffer byteBuffer, BoolVec3Init boolVec3Init) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return boolVec3Init.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startBoolVec3Init(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(3);
    }

    public final BoolVec3Init __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final boolean m211x() {
        int __offset = __offset(4);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    /* renamed from: y */
    public final boolean m212y() {
        int __offset = __offset(6);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    /* renamed from: z */
    public final boolean m213z() {
        int __offset = __offset(8);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }
}
