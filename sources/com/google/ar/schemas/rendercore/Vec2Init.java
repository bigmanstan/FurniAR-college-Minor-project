package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Vec2Init extends Table {
    public static void addX(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(0, f, 0.0d);
    }

    public static void addY(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(1, f, 0.0d);
    }

    public static int createVec2Init(FlatBufferBuilder flatBufferBuilder, float f, float f2) {
        flatBufferBuilder.startObject(2);
        addY(flatBufferBuilder, f2);
        addX(flatBufferBuilder, f);
        return endVec2Init(flatBufferBuilder);
    }

    public static int endVec2Init(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static Vec2Init getRootAsVec2Init(ByteBuffer byteBuffer) {
        return getRootAsVec2Init(byteBuffer, new Vec2Init());
    }

    public static Vec2Init getRootAsVec2Init(ByteBuffer byteBuffer, Vec2Init vec2Init) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return vec2Init.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startVec2Init(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final Vec2Init __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final float m227x() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }

    /* renamed from: y */
    public final float m228y() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }
}
