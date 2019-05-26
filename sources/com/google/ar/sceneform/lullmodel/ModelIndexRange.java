package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class ModelIndexRange extends Struct {
    public static int createModelIndexRange(FlatBufferBuilder flatBufferBuilder, long j, long j2) {
        flatBufferBuilder.prep(4, 8);
        flatBufferBuilder.putInt((int) j2);
        flatBufferBuilder.putInt((int) j);
        return flatBufferBuilder.offset();
    }

    public final ModelIndexRange __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final long end() {
        return ((long) this.bb.getInt(this.bb_pos + 4)) & 4294967295L;
    }

    public final long start() {
        return ((long) this.bb.getInt(this.bb_pos)) & 4294967295L;
    }
}
