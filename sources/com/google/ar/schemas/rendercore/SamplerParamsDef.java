package com.google.ar.schemas.rendercore;

import android.support.v4.internal.view.SupportMenu;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class SamplerParamsDef extends Table {
    public static void addAnisotropyLog2(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addByte(6, (byte) i, 0);
    }

    public static void addCompareFunc(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(8, (short) i, 0);
    }

    public static void addCompareMode(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(7, (short) i, 0);
    }

    public static void addMagFilter(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(1, (short) i, 0);
    }

    public static void addMinFilter(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(2, (short) i, 0);
    }

    public static void addUsageType(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(0, (short) i, 0);
    }

    public static void addWrapR(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(5, (short) i, 0);
    }

    public static void addWrapS(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(3, (short) i, 0);
    }

    public static void addWrapT(FlatBufferBuilder flatBufferBuilder, int i) {
        flatBufferBuilder.addShort(4, (short) i, 0);
    }

    public static int createSamplerParamsDef(FlatBufferBuilder flatBufferBuilder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        flatBufferBuilder.startObject(9);
        addCompareFunc(flatBufferBuilder, i9);
        addCompareMode(flatBufferBuilder, i8);
        addWrapR(flatBufferBuilder, i6);
        addWrapT(flatBufferBuilder, i5);
        addWrapS(flatBufferBuilder, i4);
        addMinFilter(flatBufferBuilder, i3);
        addMagFilter(flatBufferBuilder, i2);
        addUsageType(flatBufferBuilder, i);
        addAnisotropyLog2(flatBufferBuilder, i7);
        return endSamplerParamsDef(flatBufferBuilder);
    }

    public static int endSamplerParamsDef(FlatBufferBuilder flatBufferBuilder) {
        return flatBufferBuilder.endObject();
    }

    public static SamplerParamsDef getRootAsSamplerParamsDef(ByteBuffer byteBuffer) {
        return getRootAsSamplerParamsDef(byteBuffer, new SamplerParamsDef());
    }

    public static SamplerParamsDef getRootAsSamplerParamsDef(ByteBuffer byteBuffer, SamplerParamsDef samplerParamsDef) {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return samplerParamsDef.__assign(byteBuffer.getInt(byteBuffer.position()) + byteBuffer.position(), byteBuffer);
    }

    public static void startSamplerParamsDef(FlatBufferBuilder flatBufferBuilder) {
        flatBufferBuilder.startObject(9);
    }

    public final SamplerParamsDef __assign(int i, ByteBuffer byteBuffer) {
        __init(i, byteBuffer);
        return this;
    }

    public final void __init(int i, ByteBuffer byteBuffer) {
        this.bb_pos = i;
        this.bb = byteBuffer;
    }

    public final int anisotropyLog2() {
        int __offset = __offset(16);
        return __offset != 0 ? this.bb.get(__offset + this.bb_pos) & 255 : 0;
    }

    public final int compareFunc() {
        int __offset = __offset(20);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int compareMode() {
        int __offset = __offset(18);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int magFilter() {
        int __offset = __offset(6);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int minFilter() {
        int __offset = __offset(8);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int usageType() {
        int __offset = __offset(4);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int wrapR() {
        int __offset = __offset(14);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int wrapS() {
        int __offset = __offset(10);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }

    public final int wrapT() {
        int __offset = __offset(12);
        return __offset != 0 ? this.bb.getShort(__offset + this.bb_pos) & SupportMenu.USER_MASK : 0;
    }
}
