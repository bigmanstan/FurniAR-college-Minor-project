package com.google.ar.schemas.rendercore;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class MaterialDef extends Table {
    public static void addCompiledIndex(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addByte(1, (byte) i, 0);
    }

    public static void addParameters(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createMaterialDef(FlatBufferBuilder flatBufferBuilder, int i, int i2) {
        flatBufferBuilder.startObject(2);
        addParameters(flatBufferBuilder, i);
        addCompiledIndex(flatBufferBuilder, i2);
        return endMaterialDef(flatBufferBuilder);
    }

    public static int createParametersVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int endMaterialDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static MaterialDef getRootAsMaterialDef(ByteBuffer byteBuffer) {
        return getRootAsMaterialDef(byteBuffer, new MaterialDef());
    }

    public static MaterialDef getRootAsMaterialDef(ByteBuffer byteBuffer, MaterialDef materialDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return materialDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startMaterialDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(2);
    }

    public static void startParametersVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public final MaterialDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final int compiledIndex() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.get(__offset + this.bb_pos) & 255 : 0;
    }

    public final ParameterDef parameters(int i) {
        return parameters(new ParameterDef(), i);
    }

    public final ParameterDef parameters(ParameterDef parameterDef, int i) {
        int __offset = __offset(4);
        return __offset != 0 ? parameterDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int parametersLength() {
        int __offset = __offset(4);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }
}
