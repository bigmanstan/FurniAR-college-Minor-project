package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class SubmeshAabb extends Table {
    public static void addMaxPosition(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addStruct(1, i, 0);
    }

    public static void addMinPosition(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addStruct(0, i, 0);
    }

    public static int endSubmeshAabb(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static SubmeshAabb getRootAsSubmeshAabb(ByteBuffer byteBuffer) {
        return getRootAsSubmeshAabb(byteBuffer, new SubmeshAabb());
    }

    public static SubmeshAabb getRootAsSubmeshAabb(ByteBuffer byteBuffer, SubmeshAabb submeshAabb) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return submeshAabb.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startSubmeshAabb(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final SubmeshAabb __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final Vec3 maxPosition() {
        return maxPosition(new Vec3());
    }

    public final Vec3 maxPosition(Vec3 vec3) {
        int __offset = __offset(6);
        return __offset != 0 ? vec3.__assign(__offset + this.bb_pos, this.bb) : null;
    }

    public final Vec3 minPosition() {
        return minPosition(new Vec3());
    }

    public final Vec3 minPosition(Vec3 vec3) {
        int __offset = __offset(4);
        return __offset != 0 ? vec3.__assign(__offset + this.bb_pos, this.bb) : null;
    }
}
