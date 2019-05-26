package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class VersionDef extends Table {
    public static void addMajor(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(0, f, 0.0d);
    }

    public static void addMinor(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(1, i, 0);
    }

    public static int createVersionDef(FlatBufferBuilder flatBufferBuilder, float f, int i) {
        flatBufferBuilder.startObject(2);
        addMinor(flatBufferBuilder, i);
        addMajor(flatBufferBuilder, f);
        return endVersionDef(flatBufferBuilder);
    }

    public static int endVersionDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static VersionDef getRootAsVersionDef(ByteBuffer byteBuffer) {
        return getRootAsVersionDef(byteBuffer, new VersionDef());
    }

    public static VersionDef getRootAsVersionDef(ByteBuffer byteBuffer, VersionDef versionDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return versionDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startVersionDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public final VersionDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final float major() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }

    public final int minor() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 0;
    }
}
