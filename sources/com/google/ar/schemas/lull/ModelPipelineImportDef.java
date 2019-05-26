package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelPipelineImportDef extends Table {
    public static void addAxisSystem(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(4, i, -1);
    }

    public static void addFile(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addFlattenHierarchyAndTransformVerticesToRootSpace(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(9, z, false);
    }

    public static void addFlipTextureCoordinates(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(8, z, false);
    }

    public static void addMaxBoneWeights(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addInt(6, i, 4);
    }

    public static void addName(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static void addRecenter(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(2, z, false);
    }

    public static void addReportErrorsToStdout(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(7, z, false);
    }

    public static void addScale(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(3, f, 1.0d);
    }

    public static void addSmoothingAngle(FlatBufferBuilder flatBufferBuilder, float f) {
        flatBufferBuilder.addFloat(5, f, 45.0d);
    }

    public static void addUseSpecularGlossinessTexturesIfPresent(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(10, z, false);
    }

    public static int createModelPipelineImportDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, boolean z, float f, int i3, float f2, int i4, boolean z2, boolean z3, boolean z4, boolean z5) {
        flatBufferBuilder.startObject(11);
        addMaxBoneWeights(flatBufferBuilder, i4);
        addSmoothingAngle(flatBufferBuilder, f2);
        addAxisSystem(flatBufferBuilder, i3);
        addScale(flatBufferBuilder, f);
        addFile(flatBufferBuilder, i2);
        addName(flatBufferBuilder, i);
        addUseSpecularGlossinessTexturesIfPresent(flatBufferBuilder, z5);
        addFlattenHierarchyAndTransformVerticesToRootSpace(flatBufferBuilder, z4);
        addFlipTextureCoordinates(flatBufferBuilder, z3);
        addReportErrorsToStdout(flatBufferBuilder, z2);
        addRecenter(flatBufferBuilder, z);
        return endModelPipelineImportDef(flatBufferBuilder);
    }

    public static int endModelPipelineImportDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ModelPipelineImportDef getRootAsModelPipelineImportDef(ByteBuffer byteBuffer) {
        return getRootAsModelPipelineImportDef(byteBuffer, new ModelPipelineImportDef());
    }

    public static ModelPipelineImportDef getRootAsModelPipelineImportDef(ByteBuffer byteBuffer, ModelPipelineImportDef modelPipelineImportDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return modelPipelineImportDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startModelPipelineImportDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(11);
    }

    public final ModelPipelineImportDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final int axisSystem() {
        int __offset = __offset(12);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : -1;
    }

    public final String file() {
        int __offset = __offset(6);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer fileAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public final boolean flattenHierarchyAndTransformVerticesToRootSpace() {
        int __offset = __offset(22);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    public final boolean flipTextureCoordinates() {
        int __offset = __offset(20);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    public final int maxBoneWeights() {
        int __offset = __offset(16);
        return __offset != 0 ? this.bb.getInt(__offset + this.bb_pos) : 4;
    }

    public final String name() {
        int __offset = __offset(4);
        return __offset != 0 ? __string(__offset + this.bb_pos) : null;
    }

    public final ByteBuffer nameAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public final boolean recenter() {
        int __offset = __offset(8);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    public final boolean reportErrorsToStdout() {
        int __offset = __offset(18);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }

    public final float scale() {
        int __offset = __offset(10);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 1.0f;
    }

    public final float smoothingAngle() {
        int __offset = __offset(14);
        return __offset != 0 ? this.bb.getFloat(__offset + this.bb_pos) : 45.0f;
    }

    public final boolean useSpecularGlossinessTexturesIfPresent() {
        int __offset = __offset(24);
        return (__offset == 0 || this.bb.get(__offset + this.bb_pos) == (byte) 0) ? false : true;
    }
}
