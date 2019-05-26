package com.google.ar.schemas.rendercore;

public final class LightingCubeFaceType {
    public static final String[] names = new String[]{"nx", "ny", "nz", "px", "py", "pz"};
    public static final int nx = 0;
    public static final int ny = 1;
    public static final int nz = 2;
    public static final int px = 3;
    public static final int py = 4;
    public static final int pz = 5;

    private LightingCubeFaceType() {
    }

    public static String name(int i) {
        return names[i];
    }
}
