package com.google.ar.sceneform.math;

public class MathHelper {
    static final float FLT_EPSILON = 1.1920929E-7f;
    static final float MAX_DELTA = 1.0E-10f;

    public static boolean almostEqualRelativeAndAbs(float f, float f2) {
        float abs = Math.abs(f - f2);
        return abs <= MAX_DELTA || abs <= Math.max(Math.abs(f), Math.abs(f2)) * FLT_EPSILON;
    }

    public static float clamp(float f, float f2, float f3) {
        return Math.min(f3, Math.max(f2, f));
    }

    static float clamp01(float f) {
        return clamp(f, 0.0f, 1.0f);
    }

    public static float lerp(float f, float f2, float f3) {
        return f + (f3 * (f2 - f));
    }
}
