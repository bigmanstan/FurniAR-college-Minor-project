package com.google.ar.core;

import com.google.ar.core.annotations.UsedByNative;

@UsedByNative("session_jni_wrapper.cc")
class Quaternion {
    /* renamed from: a */
    public static final Quaternion f23a = new Quaternion();
    @UsedByNative("session_jni_wrapper.cc")
    /* renamed from: w */
    private float f24w = 1.0f;
    @UsedByNative("session_jni_wrapper.cc")
    /* renamed from: x */
    private float f25x = 0.0f;
    @UsedByNative("session_jni_wrapper.cc")
    /* renamed from: y */
    private float f26y = 0.0f;
    @UsedByNative("session_jni_wrapper.cc")
    /* renamed from: z */
    private float f27z = 0.0f;

    public Quaternion() {
        m45a(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @UsedByNative("session_jni_wrapper.cc")
    public Quaternion(float f, float f2, float f3, float f4) {
        m45a(f, f2, f3, f4);
    }

    private Quaternion(Quaternion quaternion) {
        m45a(quaternion.f25x, quaternion.f26y, quaternion.f27z, quaternion.f24w);
    }

    /* renamed from: a */
    public static Quaternion m44a(Quaternion quaternion, Quaternion quaternion2, float f) {
        float sin;
        Quaternion quaternion3 = new Quaternion();
        float f2 = (((quaternion.f25x * quaternion2.f25x) + (quaternion.f26y * quaternion2.f26y)) + (quaternion.f27z * quaternion2.f27z)) + (quaternion.f24w * quaternion2.f24w);
        if (f2 < 0.0f) {
            Quaternion quaternion4 = new Quaternion(quaternion2);
            f2 = -f2;
            quaternion4.f25x = -quaternion4.f25x;
            quaternion4.f26y = -quaternion4.f26y;
            quaternion4.f27z = -quaternion4.f27z;
            quaternion4.f24w = -quaternion4.f24w;
            quaternion2 = quaternion4;
        }
        float acos = (float) Math.acos((double) f2);
        f2 = (float) Math.sqrt((double) (1.0f - (f2 * f2)));
        if (((double) Math.abs(f2)) > 0.001d) {
            f2 = 1.0f / f2;
            sin = ((float) Math.sin((double) ((1.0f - f) * acos))) * f2;
            f = ((float) Math.sin((double) (f * acos))) * f2;
        } else {
            sin = 1.0f - f;
        }
        quaternion3.f25x = (quaternion.f25x * sin) + (quaternion2.f25x * f);
        quaternion3.f26y = (quaternion.f26y * sin) + (quaternion2.f26y * f);
        quaternion3.f27z = (quaternion.f27z * sin) + (quaternion2.f27z * f);
        quaternion3.f24w = (sin * quaternion.f24w) + (f * quaternion2.f24w);
        float sqrt = (float) (1.0d / Math.sqrt((double) ((((quaternion3.f25x * quaternion3.f25x) + (quaternion3.f26y * quaternion3.f26y)) + (quaternion3.f27z * quaternion3.f27z)) + (quaternion3.f24w * quaternion3.f24w))));
        quaternion3.f25x *= sqrt;
        quaternion3.f26y *= sqrt;
        quaternion3.f27z *= sqrt;
        quaternion3.f24w *= sqrt;
        return quaternion3;
    }

    /* renamed from: a */
    private final void m45a(float f, float f2, float f3, float f4) {
        this.f25x = f;
        this.f26y = f2;
        this.f27z = f3;
        this.f24w = f4;
    }

    /* renamed from: a */
    public static void m46a(Quaternion quaternion, float[] fArr, int i, float[] fArr2, int i2) {
        float f = fArr[i];
        float f2 = fArr[i + 1];
        float f3 = fArr[i + 2];
        float f4 = quaternion.f25x;
        float f5 = quaternion.f26y;
        float f6 = quaternion.f27z;
        float f7 = quaternion.f24w;
        float f8 = ((f7 * f) + (f5 * f3)) - (f6 * f2);
        float f9 = ((f7 * f2) + (f6 * f)) - (f4 * f3);
        float f10 = ((f7 * f3) + (f4 * f2)) - (f5 * f);
        f4 = -f4;
        f = ((f * f4) - (f2 * f5)) - (f3 * f6);
        f2 = -f6;
        f5 = -f5;
        fArr2[i2] = (((f8 * f7) + (f * f4)) + (f9 * f2)) - (f10 * f5);
        fArr2[i2 + 1] = (((f9 * f7) + (f * f5)) + (f10 * f4)) - (f8 * f2);
        fArr2[i2 + 2] = (((f10 * f7) + (f * f2)) + (f8 * f5)) - (f9 * f4);
    }

    /* renamed from: a */
    public final float m47a() {
        return this.f25x;
    }

    /* renamed from: a */
    public final Quaternion m48a(Quaternion quaternion) {
        Quaternion quaternion2 = new Quaternion();
        quaternion2.f25x = (((this.f25x * quaternion.f24w) + (this.f26y * quaternion.f27z)) - (this.f27z * quaternion.f26y)) + (this.f24w * quaternion.f25x);
        quaternion2.f26y = ((((-this.f25x) * quaternion.f27z) + (this.f26y * quaternion.f24w)) + (this.f27z * quaternion.f25x)) + (this.f24w * quaternion.f26y);
        quaternion2.f27z = (((this.f25x * quaternion.f26y) - (this.f26y * quaternion.f25x)) + (this.f27z * quaternion.f24w)) + (this.f24w * quaternion.f27z);
        quaternion2.f24w = ((((-this.f25x) * quaternion.f25x) - (this.f26y * quaternion.f26y)) - (this.f27z * quaternion.f27z)) + (this.f24w * quaternion.f24w);
        return quaternion2;
    }

    /* renamed from: a */
    public final void m49a(float[] fArr, int i) {
        fArr[i] = this.f25x;
        fArr[i + 1] = this.f26y;
        fArr[i + 2] = this.f27z;
        fArr[i + 3] = this.f24w;
    }

    /* renamed from: a */
    public final void m50a(float[] fArr, int i, int i2) {
        float f = (((this.f25x * this.f25x) + (this.f26y * this.f26y)) + (this.f27z * this.f27z)) + (this.f24w * this.f24w);
        float f2 = 0.0f;
        if (f > 0.0f) {
            f2 = 2.0f / f;
        }
        f = this.f25x * f2;
        float f3 = this.f26y * f2;
        float f4 = this.f27z * f2;
        f2 = this.f24w * f;
        float f5 = this.f24w * f3;
        float f6 = this.f24w * f4;
        float f7 = this.f25x * f;
        f = this.f25x * f3;
        float f8 = this.f25x * f4;
        float f9 = this.f26y * f3;
        f3 = this.f26y * f4;
        float f10 = this.f27z * f4;
        fArr[i] = 1.0f - (f9 + f10);
        fArr[i + 4] = f - f6;
        fArr[i + 8] = f8 + f5;
        int i3 = i + 1;
        fArr[i3] = f + f6;
        fArr[i3 + 4] = 1.0f - (f10 + f7);
        fArr[i3 + 8] = f3 - f2;
        i += 2;
        fArr[i] = f8 - f5;
        fArr[i + 4] = f3 + f2;
        fArr[i + 8] = 1.0f - (f7 + f9);
    }

    /* renamed from: b */
    public final float m51b() {
        return this.f26y;
    }

    /* renamed from: c */
    public final float m52c() {
        return this.f27z;
    }

    /* renamed from: d */
    public final float m53d() {
        return this.f24w;
    }

    /* renamed from: e */
    public final Quaternion m54e() {
        return new Quaternion(-this.f25x, -this.f26y, -this.f27z, this.f24w);
    }

    public String toString() {
        return String.format("[%.3f, %.3f, %.3f, %.3f]", new Object[]{Float.valueOf(this.f25x), Float.valueOf(this.f26y), Float.valueOf(this.f27z), Float.valueOf(this.f24w)});
    }
}
