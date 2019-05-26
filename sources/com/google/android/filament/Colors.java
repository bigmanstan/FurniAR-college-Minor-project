package com.google.android.filament;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Colors {

    public enum Conversion {
        ACCURATE,
        FAST
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LinearColor {
    }

    public enum RgbType {
        SRGB,
        LINEAR
    }

    public enum RgbaType {
        SRGB,
        LINEAR,
        PREMULTIPLIED_SRGB,
        PREMULTIPLIED_LINEAR
    }

    private static native void nCct(float f, @Size(3) @NonNull float[] fArr);

    private static native void nIlluminantD(float f, @Size(3) @NonNull float[] fArr);

    private Colors() {
    }

    @Size(3)
    @NonNull
    public static float[] toLinear(@NonNull RgbType type, float r, float g, float b) {
        return toLinear(type, new float[]{r, g, b});
    }

    @Size(min = 3)
    @NonNull
    public static float[] toLinear(@NonNull RgbType type, @Size(min = 3) @NonNull float[] rgb) {
        return type == RgbType.LINEAR ? rgb : toLinear(Conversion.ACCURATE, rgb);
    }

    @Size(4)
    @NonNull
    public static float[] toLinear(@NonNull RgbaType type, float r, float g, float b, float a) {
        return toLinear(type, new float[]{r, g, b, a});
    }

    @Size(min = 4)
    @NonNull
    public static float[] toLinear(@NonNull RgbaType type, @Size(min = 4) @NonNull float[] rgba) {
        switch (type) {
            case SRGB:
                toLinear(Conversion.ACCURATE, rgba);
                break;
            case LINEAR:
                break;
            case PREMULTIPLIED_SRGB:
                return toLinear(Conversion.ACCURATE, rgba);
            case PREMULTIPLIED_LINEAR:
                return rgba;
            default:
                return rgba;
        }
        float a = rgba[3];
        rgba[0] = rgba[0] * a;
        rgba[1] = rgba[1] * a;
        rgba[2] = rgba[2] * a;
        return rgba;
    }

    @NonNull
    public static float[] toLinear(@NonNull Conversion conversion, @Size(min = 3) @NonNull float[] rgb) {
        int i = 0;
        int i2;
        switch (conversion) {
            case ACCURATE:
                while (true) {
                    i2 = i;
                    if (i2 >= 3) {
                        break;
                    }
                    float f;
                    if (rgb[i2] <= 0.04045f) {
                        f = rgb[i2] / 12.92f;
                    } else {
                        f = (float) Math.pow((double) ((rgb[i2] + 0.055f) / 1.055f), 2.4000000953674316d);
                    }
                    rgb[i2] = f;
                    i = i2 + 1;
                }
            case FAST:
                while (true) {
                    i2 = i;
                    if (i2 >= 3) {
                        break;
                    }
                    rgb[i2] = (float) Math.sqrt((double) rgb[i2]);
                    i = i2 + 1;
                }
            default:
                break;
        }
        return rgb;
    }

    @Size(3)
    @NonNull
    public static float[] cct(float temperature) {
        float[] color = new float[3];
        nCct(temperature, color);
        return color;
    }

    @Size(3)
    @NonNull
    public static float[] illuminantD(float temperature) {
        float[] color = new float[3];
        nIlluminantD(temperature, color);
        return color;
    }
}
