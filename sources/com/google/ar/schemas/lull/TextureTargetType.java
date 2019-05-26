package com.google.ar.schemas.lull;

public final class TextureTargetType {
    public static final short CubeMap = (short) 1;
    public static final short Standard2d = (short) 0;
    public static final String[] names = new String[]{"Standard2d", "CubeMap"};

    private TextureTargetType() {
    }

    public static String name(int i) {
        return names[i];
    }
}
