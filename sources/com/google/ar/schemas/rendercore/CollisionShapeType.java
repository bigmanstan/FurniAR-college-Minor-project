package com.google.ar.schemas.rendercore;

public final class CollisionShapeType {
    public static final int Box = 0;
    public static final int Sphere = 1;
    public static final String[] names = new String[]{"Box", "Sphere"};

    private CollisionShapeType() {
    }

    public static String name(int i) {
        return names[i];
    }
}
