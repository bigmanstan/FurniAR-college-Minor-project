package com.google.ar.schemas.lull;

import android.support.v4.internal.view.SupportMenu;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ModelInstanceDef extends Table {
    public static void addAabbs(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(11, i, 0);
    }

    public static void addBlendAttributes(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(10, i, 0);
    }

    public static void addBlendShapes(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(9, i, 0);
    }

    public static void addIndices16(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(1, i, 0);
    }

    public static void addIndices32(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(2, i, 0);
    }

    public static void addInterleaved(FlatBufferBuilder flatBufferBuilder, boolean z) {
        flatBufferBuilder.addBoolean(7, z, true);
    }

    public static void addMaterials(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(4, i, 0);
    }

    public static void addNumVertices(FlatBufferBuilder flatBufferBuilder, long j) {
        flatBufferBuilder.addInt(6, (int) j, 0);
    }

    public static void addRanges(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(3, i, 0);
    }

    public static void addShaderToMeshBones(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(8, i, 0);
    }

    public static void addVertexAttributes(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(5, i, 0);
    }

    public static void addVertexData(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createAabbsVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createBlendShapesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createIndices16Vector(FlatBufferBuilder flatBufferBuilder, short[] sArr) {
        flatBufferBuilder.startVector(2, sArr.length, 2);
        for (int length = sArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addShort(sArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createIndices32Vector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
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

    public static int createModelInstanceDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4, int i5, int i6, long j, boolean z, int i7, int i8, int i9, int i10) {
        flatBufferBuilder.startObject(12);
        addAabbs(flatBufferBuilder, i10);
        addBlendAttributes(flatBufferBuilder, i9);
        addBlendShapes(flatBufferBuilder, i8);
        addShaderToMeshBones(flatBufferBuilder, i7);
        addNumVertices(flatBufferBuilder, j);
        addVertexAttributes(flatBufferBuilder, i6);
        addMaterials(flatBufferBuilder, i5);
        addRanges(flatBufferBuilder, i4);
        addIndices32(flatBufferBuilder, i3);
        addIndices16(flatBufferBuilder, i2);
        addVertexData(flatBufferBuilder, i);
        addInterleaved(flatBufferBuilder, z);
        return endModelInstanceDef(flatBufferBuilder);
    }

    public static int createShaderToMeshBonesVector(FlatBufferBuilder flatBufferBuilder, byte[] bArr) {
        flatBufferBuilder.startVector(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addByte(bArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createVertexDataVector(FlatBufferBuilder flatBufferBuilder, byte[] bArr) {
        flatBufferBuilder.startVector(1, bArr.length, 1);
        for (int length = bArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addByte(bArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int endModelInstanceDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static ModelInstanceDef getRootAsModelInstanceDef(ByteBuffer byteBuffer) {
        return getRootAsModelInstanceDef(byteBuffer, new ModelInstanceDef());
    }

    public static ModelInstanceDef getRootAsModelInstanceDef(ByteBuffer byteBuffer, ModelInstanceDef modelInstanceDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return modelInstanceDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startAabbsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startBlendAttributesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(8, i, 4);
    }

    public static void startBlendShapesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startIndices16Vector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(2, i, 2);
    }

    public static void startIndices32Vector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startMaterialsVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startModelInstanceDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(12);
    }

    public static void startRangesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(8, i, 4);
    }

    public static void startShaderToMeshBonesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(1, i, 1);
    }

    public static void startVertexAttributesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(8, i, 4);
    }

    public static void startVertexDataVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(1, i, 1);
    }

    public final ModelInstanceDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final SubmeshAabb aabbs(int i) {
        return aabbs(new SubmeshAabb(), i);
    }

    public final SubmeshAabb aabbs(SubmeshAabb submeshAabb, int i) {
        int __offset = __offset(26);
        return __offset != 0 ? submeshAabb.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int aabbsLength() {
        int __offset = __offset(26);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final VertexAttribute blendAttributes(int i) {
        return blendAttributes(new VertexAttribute(), i);
    }

    public final VertexAttribute blendAttributes(VertexAttribute vertexAttribute, int i) {
        int __offset = __offset(24);
        return __offset != 0 ? vertexAttribute.__assign(__vector(__offset) + (i << 3), this.bb) : null;
    }

    public final int blendAttributesLength() {
        int __offset = __offset(24);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final BlendShape blendShapes(int i) {
        return blendShapes(new BlendShape(), i);
    }

    public final BlendShape blendShapes(BlendShape blendShape, int i) {
        int __offset = __offset(22);
        return __offset != 0 ? blendShape.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int blendShapesLength() {
        int __offset = __offset(22);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final int indices16(int i) {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getShort(__vector(__offset) + (i << 1)) & SupportMenu.USER_MASK : 0;
    }

    public final ByteBuffer indices16AsByteBuffer() {
        return __vector_as_bytebuffer(6, 2);
    }

    public final int indices16Length() {
        int __offset = __offset(6);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final long indices32(int i) {
        int __offset = __offset(8);
        return __offset != 0 ? ((long) this.bb.getInt(__vector(__offset) + (i << 2))) & 4294967295L : 0;
    }

    public final ByteBuffer indices32AsByteBuffer() {
        return __vector_as_bytebuffer(8, 4);
    }

    public final int indices32Length() {
        int __offset = __offset(8);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final boolean interleaved() {
        int __offset = __offset(18);
        return __offset == 0 || this.bb.get(__offset + this.bb_pos) != (byte) 0;
    }

    public final MaterialDef materials(int i) {
        return materials(new MaterialDef(), i);
    }

    public final MaterialDef materials(MaterialDef materialDef, int i) {
        int __offset = __offset(12);
        return __offset != 0 ? materialDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int materialsLength() {
        int __offset = __offset(12);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final long numVertices() {
        int __offset = __offset(16);
        return __offset != 0 ? ((long) this.bb.getInt(__offset + this.bb_pos)) & 4294967295L : 0;
    }

    public final ModelIndexRange ranges(int i) {
        return ranges(new ModelIndexRange(), i);
    }

    public final ModelIndexRange ranges(ModelIndexRange modelIndexRange, int i) {
        int __offset = __offset(10);
        return __offset != 0 ? modelIndexRange.__assign(__vector(__offset) + (i << 3), this.bb) : null;
    }

    public final int rangesLength() {
        int __offset = __offset(10);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final int shaderToMeshBones(int i) {
        int __offset = __offset(20);
        return __offset != 0 ? this.bb.get(__vector(__offset) + i) & 255 : 0;
    }

    public final ByteBuffer shaderToMeshBonesAsByteBuffer() {
        return __vector_as_bytebuffer(20, 1);
    }

    public final int shaderToMeshBonesLength() {
        int __offset = __offset(20);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final VertexAttribute vertexAttributes(int i) {
        return vertexAttributes(new VertexAttribute(), i);
    }

    public final VertexAttribute vertexAttributes(VertexAttribute vertexAttribute, int i) {
        int __offset = __offset(14);
        return __offset != 0 ? vertexAttribute.__assign(__vector(__offset) + (i << 3), this.bb) : null;
    }

    public final int vertexAttributesLength() {
        int __offset = __offset(14);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }

    public final int vertexData(int i) {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.get(__vector(__offset) + i) & 255 : 0;
    }

    public final ByteBuffer vertexDataAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public final int vertexDataLength() {
        int __offset = __offset(4);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }
}
