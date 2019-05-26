package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class Vec3 extends Struct {
    public static int createVec3(FlatBufferBuilder flatBufferBuilder, float f, float f2, float f3) {
        flatBufferBuilder.prep(4, 12);
        flatBufferBuilder.putFloat(f3);
        flatBufferBuilder.putFloat(f2);
        flatBufferBuilder.putFloat(f);
        return flatBufferBuilder.offset();
    }

    public final Vec3 __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final float m174x() {
        return this.bb.getFloat(this.bb_pos);
    }

    /* renamed from: y */
    public final float m175y() {
        return this.bb.getFloat(this.bb_pos + 4);
    }

    /* renamed from: z */
    public final float m176z() {
        return this.bb.getFloat(this.bb_pos + 8);
    }
}
