package com.google.ar.schemas.rendercore;

import com.google.ar.schemas.lull.Vec3;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class LightingDef extends Table {
    public static void addCubeLevels(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addName(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addScale(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(1, f, 0.0d);
    }

    public static void addShCoefficients(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static int createCubeLevelsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createLightingDef(FlatBufferBuilder flatBufferBuilder, int i, float f, int i2, int i3) {
        flatBufferBuilder.startObject(4);
        addShCoefficients(flatBufferBuilder, i3);
        addCubeLevels(flatBufferBuilder, i2);
        addScale(flatBufferBuilder, f);
        addName(flatBufferBuilder, i);
        return endLightingDef(flatBufferBuilder);
    }

    public static int endLightingDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static LightingDef getRootAsLightingDef(ByteBuffer byteBuffer) {
        return getRootAsLightingDef(byteBuffer, new LightingDef());
    }

    public static LightingDef getRootAsLightingDef(ByteBuffer byteBuffer, LightingDef lightingDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return lightingDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startCubeLevelsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startLightingDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(4);
    }

    public static void startShCoefficientsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(12, i, 4);
    }

    public final LightingDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final LightingCubeDef cubeLevels(int i) {
        return cubeLevels(new LightingCubeDef(), i);
    }

    public final LightingCubeDef cubeLevels(LightingCubeDef lightingCubeDef, int i) {
        int __offset = __offset(8);
        return __offset != 0 ? lightingCubeDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int cubeLevelsLength() {
        int __offset = __offset(8);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final String name() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer nameAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public final float scale() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 0.0f;
    }

    public final Vec3 shCoefficients(int i) {
        return shCoefficients(new Vec3(), i);
    }

    public final Vec3 shCoefficients(Vec3 vec3, int i) {
        int __offset = __offset(10);
        return __offset != 0 ? vec3.__assign(__vector(__offset) + (i * 12), this.bb) : null;
    }

    public final int shCoefficientsLength() {
        int __offset = __offset(10);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }
}
