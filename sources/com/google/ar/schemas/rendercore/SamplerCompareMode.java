package com.google.ar.schemas.rendercore;

public final class SamplerCompareMode {
    public static final short CompareToTexture = (short) 1;
    public static final short None = (short) 0;
    public static final String[] names = new String[]{"None", "CompareToTexture"};

    private SamplerCompareMode() {
    }

    public static String name(int i) {
        return names[i];
    }
}
