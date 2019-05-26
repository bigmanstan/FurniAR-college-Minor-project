package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Vec3Init extends Table {
    public static void addX(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(0, f, 0.0d);
    }

    public static void addY(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(1, f, 0.0d);
    }

    public static void addZ(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(2, f, 0.0d);
    }

    public static int createVec3Init(FlatBufferBuilder flatBufferBuilder, float f, float f2, float f3) {
        flatBufferBuilder.startObject(3);
        addZ(flatBufferBuilder, f3);
        addY(flatBufferBuilder, f2);
        addX(flatBufferBuilder, f);
        return endVec3Init(flatBufferBuilder);
    }

    public static int endVec3Init(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static Vec3Init getRootAsVec3Init(ByteBuffer byteBuffer) {
        return getRootAsVec3Init(byteBuffer, new Vec3Init());
    }

    public static Vec3Init getRootAsVec3Init(ByteBuffer byteBuffer, Vec3Init vec3Init) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return vec3Init.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startVec3Init(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(3);
    }

    public final Vec3Init __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final float m229x() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }

    /* renamed from: y */
    public final float m230y() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }

    /* renamed from: z */
    public final float m231z() {
        int __offset = __offset(8);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }
}
