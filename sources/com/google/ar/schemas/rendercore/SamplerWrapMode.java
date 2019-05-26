package com.google.ar.schemas.rendercore;

public final class SamplerWrapMode {
    public static final short ClampToEdge = (short) 0;
    public static final short MirroredRepeat = (short) 2;
    public static final short Repeat = (short) 1;
    public static final String[] names = new String[]{"ClampToEdge", "Repeat", "MirroredRepeat"};

    private SamplerWrapMode() {
    }

    public static String name(int i) {
        return names[i];
    }
}
