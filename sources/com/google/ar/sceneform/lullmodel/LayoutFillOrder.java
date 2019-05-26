package com.google.ar.sceneform.lullmodel;

public final class LayoutFillOrder {
    public static final int DownLeft = 3;
    public static final int DownRight = 2;
    public static final int LeftDown = 1;
    public static final int LeftUp = 5;
    public static final int RightDown = 0;
    public static final int RightUp = 4;
    public static final int UpLeft = 7;
    public static final int UpRight = 6;
    public static final String[] names = new String[]{"RightDown", "LeftDown", "DownRight", "DownLeft", "RightUp", "LeftUp", "UpRight", "UpLeft"};

    private LayoutFillOrder() {
    }

    public static String name(int i) {
        return names[i];
    }
}
