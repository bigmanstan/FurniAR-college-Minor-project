package com.google.ar.schemas.rendercore;

public final class ParameterInitDefType {
    public static final byte BoolInit = (byte) 7;
    public static final byte BoolVec2Init = (byte) 8;
    public static final byte BoolVec3Init = (byte) 9;
    public static final byte BoolVec4Init = (byte) 10;
    public static final byte CubemapSamplerInit = (byte) 15;
    public static final byte ExternalSamplerInit = (byte) 16;
    public static final byte IntInit = (byte) 11;
    public static final byte IntVec2Init = (byte) 12;
    public static final byte IntVec3Init = (byte) 13;
    public static final byte IntVec4Init = (byte) 14;
    public static final byte NONE = (byte) 0;
    public static final byte NullInit = (byte) 1;
    public static final byte SamplerInit = (byte) 5;
    public static final byte ScalarInit = (byte) 2;
    public static final byte Vec2Init = (byte) 6;
    public static final byte Vec3Init = (byte) 3;
    public static final byte Vec4Init = (byte) 4;
    public static final String[] names = new String[]{"NONE", "NullInit", "ScalarInit", "Vec3Init", "Vec4Init", "SamplerInit", "Vec2Init", "BoolInit", "BoolVec2Init", "BoolVec3Init", "BoolVec4Init", "IntInit", "IntVec2Init", "IntVec3Init", "IntVec4Init", "CubemapSamplerInit", "ExternalSamplerInit"};

    private ParameterInitDefType() {
    }

    public static String name(int i) {
        return names[i];
    }
}
