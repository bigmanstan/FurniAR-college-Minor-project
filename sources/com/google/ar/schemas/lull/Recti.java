package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class Recti extends Struct {
    public static int createRecti(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4) {
        flatBufferBuilder.prep(4, 16);
        flatBufferBuilder.putInt(i4);
        flatBufferBuilder.putInt(i3);
        flatBufferBuilder.putInt(i2);
        flatBufferBuilder.putInt(i);
        return flatBufferBuilder.offset();
    }

    public final Recti __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    /* renamed from: h */
    public final int m194h() {
        return this.bb.getInt(this.bb_pos + 12);
    }

    /* renamed from: w */
    public final int m195w() {
        return this.bb.getInt(this.bb_pos + 8);
    }

    /* renamed from: x */
    public final int m196x() {
        return this.bb.getInt(this.bb_pos);
    }

    /* renamed from: y */
    public final int m197y() {
        return this.bb.getInt(this.bb_pos + 4);
    }
}
