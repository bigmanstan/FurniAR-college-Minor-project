package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class Vec2 extends Struct {
    public static int createVec2(FlatBufferBuilder flatBufferBuilder, float f, float f2) {
        flatBufferBuilder.prep(4, 8);
        flatBufferBuilder.putFloat(f2);
        flatBufferBuilder.putFloat(f);
        return flatBufferBuilder.offset();
    }

    public final Vec2 __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final float m198x() {
        return this.bb.getFloat(this.bb_pos);
    }

    /* renamed from: y */
    public final float m199y() {
        return this.bb.getFloat(this.bb_pos + 4);
    }
}
