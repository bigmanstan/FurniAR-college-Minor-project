package com.google.ar.schemas.rendercore;

public final class SamplerUsageType {
    public static final short Color = (short) 0;
    public static final short Data = (short) 2;
    public static final short Normal = (short) 1;
    public static final String[] names = new String[]{"Color", "Normal", "Data"};

    private SamplerUsageType() {
    }

    public static String name(int i) {
        return names[i];
    }
}
