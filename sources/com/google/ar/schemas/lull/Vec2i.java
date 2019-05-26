package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class Vec2i extends Struct {
    public static int createVec2i(FlatBufferBuilder flatBufferBuilder, int i, int i2) {
        flatBufferBuilder.prep(4, 8);
        flatBufferBuilder.putInt(i2);
        flatBufferBuilder.putInt(i);
        return flatBufferBuilder.offset();
    }

    public final Vec2i __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: x */
    public final int m200x() {
        return this.bb.getInt(this.bb_pos);
    }

    /* renamed from: y */
    public final int m201y() {
        return this.bb.getInt(this.bb_pos + 4);
    }
}
