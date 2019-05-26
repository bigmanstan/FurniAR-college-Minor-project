package com.google.ar.schemas.rendercore;

import com.google.ar.schemas.lull.Vec3;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class SuggestedCollisionShapeDef extends Table {
    public static void addCenter(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addStruct(1, i, 0);
    }

    public static void addSize(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addStruct(2, i, 0);
    }

    public static void addType(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(0, i, 0);
    }

    public static int endSuggestedCollisionShapeDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static SuggestedCollisionShapeDef getRootAsSuggestedCollisionShapeDef(ByteBuffer byteBuffer) {
        return getRootAsSuggestedCollisionShapeDef(byteBuffer, new SuggestedCollisionShapeDef());
    }

    public static SuggestedCollisionShapeDef getRootAsSuggestedCollisionShapeDef(ByteBuffer byteBuffer, SuggestedCollisionShapeDef suggestedCollisionShapeDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return suggestedCollisionShapeDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startSuggestedCollisionShapeDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(3);
    }

    public final SuggestedCollisionShapeDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final Vec3 center() {
        return center(new Vec3());
    }

    public final Vec3 center(Vec3 vec3) {
        int __offset = __offset(6);
        return __offset != 0 ? vec3.__assign(__offset + this.bb_pos, this.bb) : null;
    }

    public final Vec3 size() {
        return size(new Vec3());
    }

    public final Vec3 size(Vec3 vec3) {
        int __offset = __offset(8);
        return __offset != 0 ? vec3.__assign(__offset + this.bb_pos, this.bb) : null;
    }

    public final int type() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }
}
