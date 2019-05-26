package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelPipelineRenderableDef extends Table {
    public static void addAttributes(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addMaterials(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addSource(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createAttributesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addInt(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createMaterialsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createModelPipelineRenderableDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3) {
        flatBufferBuilder.startObject(3);
        addAttributes(flatBufferBuilder, i3);
        addMaterials(flatBufferBuilder, i2);
        addSource(flatBufferBuilder, i);
        return endModelPipelineRenderableDef(flatBufferBuilder);
    }

    public static int endModelPipelineRenderableDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ModelPipelineRenderableDef getRootAsModelPipelineRenderableDef(ByteBuffer byteBuffer) {
        return getRootAsModelPipelineRenderableDef(byteBuffer, new ModelPipelineRenderableDef());
    }

    public static ModelPipelineRenderableDef getRootAsModelPipelineRenderableDef(ByteBuffer byteBuffer, ModelPipelineRenderableDef modelPipelineRenderableDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return modelPipelineRenderableDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startAttributesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startMaterialsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startModelPipelineRenderableDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(3);
    }

    public final ModelPipelineRenderableDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final int attributes(int i) {
        int __offset = __offset(8);
        return __offset != 0 ? this.bb.getInt(__vector(__offset) + (i << 2)) : 0;
    }

    public final ByteBuffer attributesAsByteBuffer() {
        return __vector_as_bytebuffer(8, 4);
    }

    public final int attributesLength() {
        int __offset = __offset(8);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final MaterialDef materials(int i) {
        return materials(new MaterialDef(), i);
    }

    public final MaterialDef materials(MaterialDef materialDef, int i) {
        int __offset = __offset(6);
        return __offset != 0 ? materialDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int materialsLength() {
        int __offset = __offset(6);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final String source() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer sourceAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }
}
