package com.google.ar.sceneform.lullmodel;

public final class VariantDef {
    public static final byte DataBool = (byte) 1;
    public static final byte DataBytes = (byte) 10;
    public static final byte DataFloat = (byte) 3;
    public static final byte DataHashValue = (byte) 5;
    public static final byte DataInt = (byte) 2;
    public static final byte DataQuat = (byte) 9;
    public static final byte DataString = (byte) 4;
    public static final byte DataVec2 = (byte) 6;
    public static final byte DataVec3 = (byte) 7;
    public static final byte DataVec4 = (byte) 8;
    public static final byte NONE = (byte) 0;
    public static final String[] names = new String[]{"NONE", "DataBool", "DataInt", "DataFloat", "DataString", "DataHashValue", "DataVec2", "DataVec3", "DataVec4", "DataQuat", "DataBytes"};

    private VariantDef() {
    }

    public static String name(int i) {
        return names[i];
    }
}
