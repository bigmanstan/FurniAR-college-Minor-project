package com.google.ar.schemas.rendercore;

public final class SamplerCompareFunc {
    public static final short Always = (short) 6;
    public static final short EqualTo = (short) 4;
    public static final short GreaterEqual = (short) 1;
    public static final short GreaterThan = (short) 3;
    public static final short LessEqual = (short) 0;
    public static final short LessThan = (short) 2;
    public static final short Never = (short) 7;
    public static final short NotEqual = (short) 5;
    public static final String[] names = new String[]{"LessEqual", "GreaterEqual", "LessThan", "GreaterThan", "EqualTo", "NotEqual", "Always", "Never"};

    private SamplerCompareFunc() {
    }

    public static String name(int i) {
        return names[i];
    }
}
