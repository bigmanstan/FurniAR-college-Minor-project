package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelPipelineDef extends Table {
    public static void addCollidable(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addRenderables(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addSkeleton(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void addSources(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addTextures(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static int createModelPipelineDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4, int i5) {
        flatBufferBuilder.startObject(5);
        addTextures(flatBufferBuilder, i5);
        addSkeleton(flatBufferBuilder, i4);
        addCollidable(flatBufferBuilder, i3);
        addRenderables(flatBufferBuilder, i2);
        addSources(flatBufferBuilder, i);
        return endModelPipelineDef(flatBufferBuilder);
    }

    public static int createRenderablesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createSourcesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
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

    public static int endModelPipelineDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ModelPipelineDef getRootAsModelPipelineDef(ByteBuffer byteBuffer) {
        return getRootAsModelPipelineDef(byteBuffer, new ModelPipelineDef());
    }

    public static ModelPipelineDef getRootAsModelPipelineDef(ByteBuffer byteBuffer, ModelPipelineDef modelPipelineDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return modelPipelineDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startModelPipelineDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(5);
    }

    public static void startRenderablesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startSourcesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startTexturesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public final ModelPipelineDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final ModelPipelineCollidableDef collidable() {
        return collidable(new ModelPipelineCollidableDef());
    }

    public final ModelPipelineCollidableDef collidable(ModelPipelineCollidableDef modelPipelineCollidableDef) {
        int __offset = __offset(8);
        return __offset != 0 ? modelPipelineCollidableDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }

    public final ModelPipelineRenderableDef renderables(int i) {
        return renderables(new ModelPipelineRenderableDef(), i);
    }

    public final ModelPipelineRenderableDef renderables(ModelPipelineRenderableDef modelPipelineRenderableDef, int i) {
        int __offset = __offset(6);
        return __offset != 0 ? modelPipelineRenderableDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int renderablesLength() {
        int __offset = __offset(6);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final ModelPipelineSkeletonDef skeleton() {
        return skeleton(new ModelPipelineSkeletonDef());
    }

    public final ModelPipelineSkeletonDef skeleton(ModelPipelineSkeletonDef modelPipelineSkeletonDef) {
        int __offset = __offset(10);
        return __offset != 0 ? modelPipelineSkeletonDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }

    public final ModelPipelineImportDef sources(int i) {
        return sources(new ModelPipelineImportDef(), i);
    }

    public final ModelPipelineImportDef sources(ModelPipelineImportDef modelPipelineImportDef, int i) {
        int __offset = __offset(4);
        return __offset != 0 ? modelPipelineImportDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int sourcesLength() {
        int __offset = __offset(4);
        return __offset != 0 ? __vector_len(__offset) : 0;
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
}
