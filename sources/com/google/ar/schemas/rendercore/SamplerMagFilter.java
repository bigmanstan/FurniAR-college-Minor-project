package com.google.ar.schemas.rendercore;

public final class SamplerMagFilter {
    public static final short Linear = (short) 1;
    public static final short Nearest = (short) 0;
    public static final String[] names = new String[]{"Nearest", "Linear"};

    private SamplerMagFilter() {
    }

    public static String name(int i) {
        return names[i];
    }
}
