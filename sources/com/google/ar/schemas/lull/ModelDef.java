package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelDef extends Table {
    public static void addBoundingBox(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addStruct(3, i, 0);
    }

    public static void addLods(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addSkeleton(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addTextures(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static void addVersion(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(0, i, 1);
    }

    public static int createLodsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createTexturesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int endModelDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static void finishModelDefBuffer(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.finish(i);
    }

    public static ModelDef getRootAsModelDef(ByteBuffer byteBuffer) {
        return getRootAsModelDef(byteBuffer, new ModelDef());
    }

    public static ModelDef getRootAsModelDef(ByteBuffer byteBuffer, ModelDef modelDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return modelDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startLodsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startModelDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(5);
    }

    public static void startTexturesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public final ModelDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final AabbDef boundingBox() {
        return boundingBox(new AabbDef());
    }

    public final AabbDef boundingBox(AabbDef aabbDef) {
        int __offset = __offset(10);
        return __offset != 0 ? aabbDef.__assign(__offset + this.bb_pos, this.bb) : null;
    }

    public final ModelInstanceDef lods(int i) {
        return lods(new ModelInstanceDef(), i);
    }

    public final ModelInstanceDef lods(ModelInstanceDef modelInstanceDef, int i) {
        int __offset = __offset(6);
        return __offset != 0 ? modelInstanceDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int lodsLength() {
        int __offset = __offset(6);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final SkeletonDef skeleton() {
        return skeleton(new SkeletonDef());
    }

    public final SkeletonDef skeleton(SkeletonDef skeletonDef) {
        int __offset = __offset(8);
        return __offset != 0 ? skeletonDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }

    public final TextureDef textures(int i) {
        return textures(new TextureDef(), i);
    }

    public final TextureDef textures(TextureDef textureDef, int i) {
        int __offset = __offset(12);
        return __offset != 0 ? textureDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int texturesLength() {
        int __offset = __offset(12);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final int version() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 1;
    }
}
