package com.google.ar.sceneform.math;

import com.google.ar.sceneform.utilities.Preconditions;

public class Vector3 {
    /* renamed from: x */
    public float f120x;
    /* renamed from: y */
    public float f121y;
    /* renamed from: z */
    public float f122z;

    public Vector3() {
        this.f120x = 0.0f;
        this.f121y = 0.0f;
        this.f122z = 0.0f;
    }

    public Vector3(float f, float f2, float f3) {
        this.f120x = f;
        this.f121y = f2;
        this.f122z = f3;
    }

    public Vector3(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"v\" was null.");
        set(vector3);
    }

    public static Vector3 add(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        return new Vector3(vector3.f120x + vector32.f120x, vector3.f121y + vector32.f121y, vector3.f122z + vector32.f122z);
    }

    public static float angleBetweenVectors(Vector3 vector3, Vector3 vector32) {
        float length = vector3.length() * vector32.length();
        return MathHelper.almostEqualRelativeAndAbs(length, 0.0f) ? 0.0f : (float) Math.toDegrees((double) ((float) Math.acos((double) MathHelper.clamp(dot(vector3, vector32) / length, -1.0f, 1.0f))));
    }

    public static Vector3 back() {
        Vector3 vector3 = new Vector3();
        vector3.setBack();
        return vector3;
    }

    static float componentMax(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"a\" was null.");
        return Math.max(Math.max(vector3.f120x, vector3.f121y), vector3.f122z);
    }

    static float componentMin(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"a\" was null.");
        return Math.min(Math.min(vector3.f120x, vector3.f121y), vector3.f122z);
    }

    public static Vector3 cross(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        float f4 = vector32.f120x;
        float f5 = vector32.f121y;
        float f6 = vector32.f122z;
        return new Vector3((f2 * f6) - (f3 * f5), (f3 * f4) - (f6 * f), (f * f5) - (f2 * f4));
    }

    public static float dot(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        return ((vector3.f120x * vector32.f120x) + (vector3.f121y * vector32.f121y)) + (vector3.f122z * vector32.f122z);
    }

    public static Vector3 down() {
        Vector3 vector3 = new Vector3();
        vector3.setDown();
        return vector3;
    }

    public static boolean equals(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        return MathHelper.almostEqualRelativeAndAbs(vector3.f122z, vector32.f122z) & ((MathHelper.almostEqualRelativeAndAbs(vector3.f120x, vector32.f120x) & 1) & MathHelper.almostEqualRelativeAndAbs(vector3.f121y, vector32.f121y));
    }

    public static Vector3 forward() {
        Vector3 vector3 = new Vector3();
        vector3.setForward();
        return vector3;
    }

    public static Vector3 left() {
        Vector3 vector3 = new Vector3();
        vector3.setLeft();
        return vector3;
    }

    public static Vector3 lerp(Vector3 vector3, Vector3 vector32, float f) {
        Preconditions.checkNotNull(vector3, "Parameter \"a\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"b\" was null.");
        return new Vector3(MathHelper.lerp(vector3.f120x, vector32.f120x, f), MathHelper.lerp(vector3.f121y, vector32.f121y, f), MathHelper.lerp(vector3.f122z, vector32.f122z, f));
    }

    public static Vector3 max(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        return new Vector3(Math.max(vector3.f120x, vector32.f120x), Math.max(vector3.f121y, vector32.f121y), Math.max(vector3.f122z, vector32.f122z));
    }

    public static Vector3 min(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        return new Vector3(Math.min(vector3.f120x, vector32.f120x), Math.min(vector3.f121y, vector32.f121y), Math.min(vector3.f122z, vector32.f122z));
    }

    public static Vector3 one() {
        Vector3 vector3 = new Vector3();
        vector3.setOne();
        return vector3;
    }

    public static Vector3 right() {
        Vector3 vector3 = new Vector3();
        vector3.setRight();
        return vector3;
    }

    public static Vector3 subtract(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"rhs\" was null.");
        return new Vector3(vector3.f120x - vector32.f120x, vector3.f121y - vector32.f121y, vector3.f122z - vector32.f122z);
    }

    public static Vector3 up() {
        Vector3 vector3 = new Vector3();
        vector3.setUp();
        return vector3;
    }

    public static Vector3 zero() {
        return new Vector3();
    }

    public float length() {
        return (float) Math.sqrt((double) lengthSquared());
    }

    float lengthSquared() {
        return ((this.f120x * this.f120x) + (this.f121y * this.f121y)) + (this.f122z * this.f122z);
    }

    public Vector3 negated() {
        return new Vector3(-this.f120x, -this.f121y, -this.f122z);
    }

    public Vector3 normalized() {
        Vector3 vector3 = new Vector3(this);
        float dot = dot(this, this);
        if (MathHelper.almostEqualRelativeAndAbs(dot, 0.0f)) {
            vector3.setZero();
        } else if (dot != 1.0f) {
            vector3.set(scaled((float) (1.0d / Math.sqrt((double) dot))));
        }
        return vector3;
    }

    public Vector3 scaled(float f) {
        return new Vector3(this.f120x * f, this.f121y * f, this.f122z * f);
    }

    public void set(float f, float f2, float f3) {
        this.f120x = f;
        this.f121y = f2;
        this.f122z = f3;
    }

    public void set(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"v\" was null.");
        this.f120x = vector3.f120x;
        this.f121y = vector3.f121y;
        this.f122z = vector3.f122z;
    }

    void setBack() {
        set(0.0f, 0.0f, 1.0f);
    }

    void setDown() {
        set(0.0f, -1.0f, 0.0f);
    }

    void setForward() {
        set(0.0f, 0.0f, -1.0f);
    }

    void setLeft() {
        set(-1.0f, 0.0f, 0.0f);
    }

    void setOne() {
        set(1.0f, 1.0f, 1.0f);
    }

    void setRight() {
        set(1.0f, 0.0f, 0.0f);
    }

    void setUp() {
        set(0.0f, 1.0f, 0.0f);
    }

    void setZero() {
        set(0.0f, 0.0f, 0.0f);
    }

    public String toString() {
        float f = this.f120x;
        float f2 = this.f121y;
        float f3 = this.f122z;
        StringBuilder stringBuilder = new StringBuilder(57);
        stringBuilder.append("[x=");
        stringBuilder.append(f);
        stringBuilder.append(", y=");
        stringBuilder.append(f2);
        stringBuilder.append(", z=");
        stringBuilder.append(f3);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
