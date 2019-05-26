package com.google.ar.sceneform.lullmodel;

public final class TextureWrap {
    public static final short ClampToBorder = (short) 0;
    public static final short ClampToEdge = (short) 1;
    public static final short MirrorClampToEdge = (short) 3;
    public static final short MirroredRepeat = (short) 2;
    public static final short Repeat = (short) 4;
    public static final String[] names = new String[]{"ClampToBorder", "ClampToEdge", "MirroredRepeat", "MirrorClampToEdge", "Repeat"};

    private TextureWrap() {
    }

    public static String name(int i) {
        return names[i];
    }
}
