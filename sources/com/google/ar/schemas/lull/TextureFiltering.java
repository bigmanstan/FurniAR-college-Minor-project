package com.google.ar.schemas.lull;

public final class TextureFiltering {
    public static final short Linear = (short) 1;
    public static final short LinearMipmapLinear = (short) 5;
    public static final short LinearMipmapNearest = (short) 3;
    public static final short Nearest = (short) 0;
    public static final short NearestMipmapLinear = (short) 4;
    public static final short NearestMipmapNearest = (short) 2;
    public static final String[] names = new String[]{"Nearest", "Linear", "NearestMipmapNearest", "LinearMipmapNearest", "NearestMipmapLinear", "LinearMipmapLinear"};

    private TextureFiltering() {
    }

    public static String name(int i) {
        return names[i];
    }
}
