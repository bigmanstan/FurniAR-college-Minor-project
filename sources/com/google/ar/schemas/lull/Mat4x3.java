package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;
import java.nio.ByteBuffer;

public final class Mat4x3 extends Struct {
    public static int createMat4x3(FlatBufferBuilder flatBufferBuilder, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        flatBufferBuilder.prep(4, 48);
        flatBufferBuilder.prep(4, 16);
        flatBufferBuilder.putFloat(f12);
        flatBufferBuilder.putFloat(f11);
        flatBufferBuilder.putFloat(f10);
        flatBufferBuilder.putFloat(f9);
        flatBufferBuilder.prep(4, 16);
        flatBufferBuilder.putFloat(f8);
        flatBufferBuilder.putFloat(f7);
        flatBufferBuilder.putFloat(f6);
        flatBufferBuilder.putFloat(f5);
        flatBufferBuilder.prep(4, 16);
        flatBufferBuilder.putFloat(f4);
        flatBufferBuilder.putFloat(f3);
        flatBufferBuilder.putFloat(f2);
        flatBufferBuilder.putFloat(f);
        return flatBufferBuilder.offset();
    }

    public final Mat4x3 __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final Vec4 c0() {
        return c0(new Vec4());
    }

    public final Vec4 c0(Vec4 vec4) {
        return vec4.__assign(this.bb_pos, this.bb);
    }

    public final Vec4 c1() {
        return c1(new Vec4());
    }

    public final Vec4 c1(Vec4 vec4) {
        return vec4.__assign(this.bb_pos + 16, this.bb);
    }

    public final Vec4 c2() {
        return c2(new Vec4());
    }

    public final Vec4 c2(Vec4 vec4) {
        return vec4.__assign(this.bb_pos + 32, this.bb);
    }
}
