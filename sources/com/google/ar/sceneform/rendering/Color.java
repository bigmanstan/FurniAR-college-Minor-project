package com.google.ar.sceneform.rendering;

import android.support.annotation.ColorInt;
import com.google.android.filament.Colors;
import com.google.android.filament.Colors.RgbType;

public class Color {
    private static final float INT_COLOR_SCALE = 0.003921569f;
    /* renamed from: a */
    public float f123a;
    /* renamed from: b */
    public float f124b;
    /* renamed from: g */
    public float f125g;
    /* renamed from: r */
    public float f126r;

    public Color() {
        setWhite();
    }

    public Color(Color color) {
        set(color);
    }

    public Color(float r, float g, float b) {
        set(r, g, b);
    }

    public Color(float r, float g, float b, float a) {
        set(r, g, b, a);
    }

    public Color(@ColorInt int argb) {
        set(argb);
    }

    public void set(Color color) {
        set(color.f126r, color.f125g, color.f124b, color.f123a);
    }

    public void set(float r, float g, float b) {
        set(r, g, b, 1.0f);
    }

    public void set(float r, float g, float b, float a) {
        this.f126r = r;
        this.f125g = g;
        this.f124b = b;
        this.f123a = a;
    }

    public void set(@ColorInt int argb) {
        int red = android.graphics.Color.red(argb);
        int green = android.graphics.Color.green(argb);
        int blue = android.graphics.Color.blue(argb);
        int alpha = android.graphics.Color.alpha(argb);
        float[] linearColor = Colors.toLinear(RgbType.SRGB, ((float) red) * INT_COLOR_SCALE, ((float) green) * INT_COLOR_SCALE, ((float) blue) * INT_COLOR_SCALE);
        this.f126r = linearColor[0];
        this.f125g = linearColor[1];
        this.f124b = linearColor[2];
        this.f123a = ((float) alpha) * INT_COLOR_SCALE;
    }

    private void setWhite() {
        set(1.0f, 1.0f, 1.0f);
    }
}
