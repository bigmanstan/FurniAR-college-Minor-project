package com.google.ar.schemas.lull;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class VariantMapDef extends Table {
    public static void addValues(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addOffset(0, i, 0);
    }

    public static int createValuesVector(FlatBufferBuilder flatBufferBuilder, int[] iArr) {
        flatBufferBuilder.startVector(4, iArr.length, 4);
        for (int length = iArr.length - 1; length >= 0; length--) {
            flatBufferBuilder.addOffset(iArr[length]);
        }
        return flatBufferBuilder.endVector();
    }

    public static int createVariantMapDef(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startObject(1);
        addValues(flatBufferBuilder, i);
        return endVariantMapDef(flatBufferBuilder);
    }

    public static int endVariantMapDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static VariantMapDef getRootAsVariantMapDef(ByteBuffer byteBuffer) {
        return getRootAsVariantMapDef(byteBuffer, new VariantMapDef());
    }

    public static VariantMapDef getRootAsVariantMapDef(ByteBuffer byteBuffer, VariantMapDef variantMapDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return variantMapDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startValuesVector(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.startVector(4, i, 4);
    }

    public static void startVariantMapDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(1);
    }

    public final VariantMapDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final KeyVariantPairDef values(int i) {
        return values(new KeyVariantPairDef(), i);
    }

    public final KeyVariantPairDef values(KeyVariantPairDef keyVariantPairDef, int i) {
        int __offset = __offset(4);
        return __offset != 0 ? keyVariantPairDef.__assign(__indirect(__vector(__offset) + (i << 2)), this.bb) : null;
    }

    public final int valuesLength() {
        int __offset = __offset(4);
        return __offset != 0 ? __vector_len(__offset) : 0;
    }
}
