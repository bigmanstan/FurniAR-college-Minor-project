package com.google.ar.schemas.rendercore;

import com.google.ar.schemas.lull.ModelDef;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class RendercoreBundleDef extends Table {
    public static boolean RendercoreBundleDefBufferHasIdentifier(ByteBuffer byteBuffer) {
        return Table.__has_identifier(byteBuffer, "RBUN");
    }

    public static void addCompiledMaterials(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void addInputs(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(6, i, 0);
    }

    public static void addLightingDefs(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(7, i, 0);
    }

    public static void addMaterials(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addModel(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addSamplers(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(5, i, 0);
    }

    public static void addSuggestedCollisionShape(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static void addVersion(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createCompiledMaterialsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createInputsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createLightingDefsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
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

    public static int createRendercoreBundleDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        flatBufferBuilder.startObject(8);
        addLightingDefs(flatBufferBuilder, i8);
        addInputs(flatBufferBuilder, i7);
        addSamplers(flatBufferBuilder, i6);
        addSuggestedCollisionShape(flatBufferBuilder, i5);
        addCompiledMaterials(flatBufferBuilder, i4);
        addMaterials(flatBufferBuilder, i3);
        addModel(flatBufferBuilder, i2);
        addVersion(flatBufferBuilder, i);
        return endRendercoreBundleDef(flatBufferBuilder);
    }

    public static int createSamplersVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int endRendercoreBundleDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static void finishRendercoreBundleDefBuffer(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.finish(i, "RBUN");
    }

    public static RendercoreBundleDef getRootAsRendercoreBundleDef(ByteBuffer byteBuffer) {
        return getRootAsRendercoreBundleDef(byteBuffer, new RendercoreBundleDef());
    }

    public static RendercoreBundleDef getRootAsRendercoreBundleDef(ByteBuffer byteBuffer, RendercoreBundleDef rendercoreBundleDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return rendercoreBundleDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startCompiledMaterialsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startInputsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startLightingDefsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startMaterialsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startRendercoreBundleDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(8);
    }

    public static void startSamplersVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public final RendercoreBundleDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final CompiledMaterialDef compiledMaterials(int i) {
        return compiledMaterials(new CompiledMaterialDef(), i);
    }

    public final CompiledMaterialDef compiledMaterials(CompiledMaterialDef compiledMaterialDef, int i) {
        int __offset = __offset(10);
        return __offset != 0 ? compiledMaterialDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int compiledMaterialsLength() {
        int __offset = __offset(10);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final InputDef inputs(int i) {
        return inputs(new InputDef(), i);
    }

    public final InputDef inputs(InputDef inputDef, int i) {
        int __offset = __offset(16);
        return __offset != 0 ? inputDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int inputsLength() {
        int __offset = __offset(16);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final LightingDef lightingDefs(int i) {
        return lightingDefs(new LightingDef(), i);
    }

    public final LightingDef lightingDefs(LightingDef lightingDef, int i) {
        int __offset = __offset(18);
        return __offset != 0 ? lightingDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int lightingDefsLength() {
        int __offset = __offset(18);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final MaterialDef materials(int i) {
        return materials(new MaterialDef(), i);
    }

    public final MaterialDef materials(MaterialDef materialDef, int i) {
        int __offset = __offset(8);
        return __offset != 0 ? materialDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int materialsLength() {
        int __offset = __offset(8);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final ModelDef model() {
        return model(new ModelDef());
    }

    public final ModelDef model(ModelDef modelDef) {
        int __offset = __offset(6);
        return __offset != 0 ? modelDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }

    public final SamplerDef samplers(int i) {
        return samplers(new SamplerDef(), i);
    }

    public final SamplerDef samplers(SamplerDef samplerDef, int i) {
        int __offset = __offset(14);
        return __offset != 0 ? samplerDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int samplersLength() {
        int __offset = __offset(14);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final SuggestedCollisionShapeDef suggestedCollisionShape() {
        return suggestedCollisionShape(new SuggestedCollisionShapeDef());
    }

    public final SuggestedCollisionShapeDef suggestedCollisionShape(SuggestedCollisionShapeDef suggestedCollisionShapeDef) {
        int __offset = __offset(12);
        return __offset != 0 ? suggestedCollisionShapeDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }

    public final VersionDef version() {
        return version(new VersionDef());
    }

    public final VersionDef version(VersionDef versionDef) {
        int __offset = __offset(4);
        return __offset != 0 ? versionDef.__assign(__indirect(__offset + this.bb_pos), this.bb) : null;
    }
}
