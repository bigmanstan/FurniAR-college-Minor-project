package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class Rect extends Struct {
    public static int createRect(FlatBufferBuilder flatBufferBuilder, float f, float f2, float f3, float f4) {
        flatBufferBuilder.prep(4, 16);
        flatBufferBuilder.putFloat(f4);
        flatBufferBuilder.putFloat(f3);
        flatBufferBuilder.putFloat(f2);
        flatBufferBuilder.putFloat(f);
        return flatBufferBuilder.offset();
    }

    public final Rect __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: h */
    public final float m190h() {
        return this.bb.getFloat(this.bb_pos + 12);
    }

    /* renamed from: w */
    public final float m191w() {
        return this.bb.getFloat(this.bb_pos + 8);
    }

    /* renamed from: x */
    public final float m192x() {
        return this.bb.getFloat(this.bb_pos);
    }

    /* renamed from: y */
    public final float m193y() {
        return this.bb.getFloat(this.bb_pos + 4);
    }
}
