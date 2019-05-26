package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class ArcDef extends Struct {
    public static int createArcDef(FlatBufferBuilder flatBufferBuilder, float f, float f2, float f3, float f4, int i) {
        flatBufferBuilder.prep(4, 20);
        flatBufferBuilder.putInt(i);
        flatBufferBuilder.putFloat(f4);
        flatBufferBuilder.putFloat(f3);
        flatBufferBuilder.putFloat(f2);
        flatBufferBuilder.putFloat(f);
        return flatBufferBuilder.offset();
    }

    public final ArcDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final float angleSize() {
        return this.bb.getFloat(this.bb_pos + 4);
    }

    public final float innerRadius() {
        return this.bb.getFloat(this.bb_pos + 8);
    }

    public final int numSamples() {
        return this.bb.getInt(this.bb_pos + 16);
    }

    public final float outerRadius() {
        return this.bb.getFloat(this.bb_pos + 12);
    }

    public final float startAngle() {
        return this.bb.getFloat(this.bb_pos);
    }
}
