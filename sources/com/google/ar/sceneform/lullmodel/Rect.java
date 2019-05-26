package com.google.ar.sceneform.lullmodel;

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
    public final float m162h() {
        return this.bb.getFloat(this.bb_pos + 12);
    }

    /* renamed from: w */
    public final float m163w() {
        return this.bb.getFloat(this.bb_pos + 8);
    }

    /* renamed from: x */
    public final float m164x() {
        return this.bb.getFloat(this.bb_pos);
    }

    /* renamed from: y */
    public final float m165y() {
        return this.bb.getFloat(this.bb_pos + 4);
    }
}
