package com.google.ar.sceneform.lullmodel;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelPipelineSkeletonDef extends Table {
    public static void addSource(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createModelPipelineSkeletonDef(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startObject(1);
        addSource(flatBufferBuilder, i);
        return endModelPipelineSkeletonDef(flatBufferBuilder);
    }

    public static int endModelPipelineSkeletonDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ModelPipelineSkeletonDef getRootAsModelPipelineSkeletonDef(ByteBuffer byteBuffer) {
        return getRootAsModelPipelineSkeletonDef(byteBuffer, new ModelPipelineSkeletonDef());
    }

    public static ModelPipelineSkeletonDef getRootAsModelPipelineSkeletonDef(ByteBuffer byteBuffer, ModelPipelineSkeletonDef modelPipelineSkeletonDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return modelPipelineSkeletonDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startModelPipelineSkeletonDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final ModelPipelineSkeletonDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final String source() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer sourceAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }
}
