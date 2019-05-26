package com.google.ar.sceneform.math;

import com.google.ar.sceneform.utilities.Preconditions;

public class Quaternion {
    private static final float SLERP_THRESHOLD = 0.9995f;
    /* renamed from: w */
    public float f116w;
    /* renamed from: x */
    public float f117x;
    /* renamed from: y */
    public float f118y;
    /* renamed from: z */
    public float f119z;

    public Quaternion() {
        this.f117x = 0.0f;
        this.f118y = 0.0f;
        this.f119z = 0.0f;
        this.f116w = 1.0f;
    }

    public Quaternion(float f, float f2, float f3, float f4) {
        set(f, f2, f3, f4);
    }

    public Quaternion(Quaternion quaternion) {
        Preconditions.checkNotNull(quaternion, "Parameter \"q\" was null.");
        set(quaternion);
    }

    Quaternion(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"eulerAngles\" was null.");
        set(eulerAngles(vector3));
    }

    public Quaternion(Vector3 vector3, float f) {
        Preconditions.checkNotNull(vector3, "Parameter \"axis\" was null.");
        set(axisAngle(vector3, f));
    }

    static Quaternion add(Quaternion quaternion, Quaternion quaternion2) {
        Preconditions.checkNotNull(quaternion, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(quaternion2, "Parameter \"rhs\" was null.");
        Quaternion quaternion3 = new Quaternion();
        quaternion3.f117x = quaternion.f117x + quaternion2.f117x;
        quaternion3.f118y = quaternion.f118y + quaternion2.f118y;
        quaternion3.f119z = quaternion.f119z + quaternion2.f119z;
        quaternion3.f116w = quaternion.f116w + quaternion2.f116w;
        return quaternion3;
    }

    public static Quaternion axisAngle(Vector3 vector3, float f) {
        Preconditions.checkNotNull(vector3, "Parameter \"axis\" was null.");
        Quaternion quaternion = new Quaternion();
        double toRadians = Math.toRadians((double) f) / 2.0d;
        double sin = Math.sin(toRadians);
        quaternion.f117x = (float) (((double) vector3.f120x) * sin);
        quaternion.f118y = (float) (((double) vector3.f121y) * sin);
        quaternion.f119z = (float) (((double) vector3.f122z) * sin);
        quaternion.f116w = (float) Math.cos(toRadians);
        quaternion.normalize();
        return quaternion;
    }

    static float dot(Quaternion quaternion, Quaternion quaternion2) {
        Preconditions.checkNotNull(quaternion, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(quaternion2, "Parameter \"rhs\" was null.");
        return (((quaternion.f117x * quaternion2.f117x) + (quaternion.f118y * quaternion2.f118y)) + (quaternion.f119z * quaternion2.f119z)) + (quaternion.f116w * quaternion2.f116w);
    }

    public static boolean equals(Quaternion quaternion, Quaternion quaternion2) {
        Preconditions.checkNotNull(quaternion, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(quaternion2, "Parameter \"rhs\" was null.");
        return MathHelper.almostEqualRelativeAndAbs(dot(quaternion, quaternion2), 1.0f);
    }

    static Quaternion eulerAngles(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"eulerAngles\" was null.");
        Quaternion quaternion = new Quaternion(Vector3.right(), vector3.f120x);
        Quaternion quaternion2 = new Quaternion(Vector3.up(), vector3.f121y);
        return multiply(multiply(quaternion, quaternion2), new Quaternion(Vector3.back(), vector3.f122z));
    }

    public static Quaternion identity() {
        return new Quaternion();
    }

    public static Vector3 inverseRotateVector(Quaternion quaternion, Vector3 vector3) {
        Quaternion quaternion2 = quaternion;
        Vector3 vector32 = vector3;
        Preconditions.checkNotNull(quaternion2, "Parameter \"q\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"src\" was null.");
        Vector3 vector33 = new Vector3();
        float f = quaternion2.f116w * quaternion2.f116w;
        float f2 = (-quaternion2.f117x) * (-quaternion2.f117x);
        float f3 = (-quaternion2.f118y) * (-quaternion2.f118y);
        float f4 = (-quaternion2.f119z) * (-quaternion2.f119z);
        float f5 = (-quaternion2.f119z) * quaternion2.f116w;
        float f6 = (-quaternion2.f117x) * (-quaternion2.f118y);
        float f7 = (-quaternion2.f117x) * (-quaternion2.f119z);
        float f8 = (-quaternion2.f118y) * quaternion2.f116w;
        float f9 = (-quaternion2.f118y) * (-quaternion2.f119z);
        float f10 = (-quaternion2.f117x) * quaternion2.f116w;
        float f11 = ((f + f2) - f4) - f3;
        float f12 = ((f6 + f5) + f5) + f6;
        float f13 = ((f7 - f8) + f7) - f8;
        float f14 = (((-f5) + f6) - f5) + f6;
        f5 = ((f3 - f4) + f) - f2;
        f9 += f9;
        f6 = (f9 + f10) + f10;
        float f15 = ((f8 + f7) + f7) + f8;
        f9 = (f9 - f10) - f10;
        f4 = ((f4 - f3) - f2) + f;
        f = vector32.f120x;
        f2 = vector32.f121y;
        float f16 = vector32.f122z;
        vector33.f120x = ((f11 * f) + (f14 * f2)) + (f15 * f16);
        vector33.f121y = ((f12 * f) + (f5 * f2)) + (f9 * f16);
        vector33.f122z = ((f13 * f) + (f6 * f2)) + (f4 * f16);
        return vector33;
    }

    static Quaternion lerp(Quaternion quaternion, Quaternion quaternion2, float f) {
        Preconditions.checkNotNull(quaternion, "Parameter \"a\" was null.");
        Preconditions.checkNotNull(quaternion2, "Parameter \"b\" was null.");
        return new Quaternion(MathHelper.lerp(quaternion.f117x, quaternion2.f117x, f), MathHelper.lerp(quaternion.f118y, quaternion2.f118y, f), MathHelper.lerp(quaternion.f119z, quaternion2.f119z, f), MathHelper.lerp(quaternion.f116w, quaternion2.f116w, f));
    }

    public static Quaternion lookRotation(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"forwardInWorld\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"desiredUpInWorld\" was null.");
        Quaternion rotationBetweenVectors = rotationBetweenVectors(Vector3.forward(), vector3);
        return multiply(rotationBetweenVectors(rotateVector(rotationBetweenVectors, Vector3.up()), Vector3.cross(Vector3.cross(vector3, vector32), vector3)), rotationBetweenVectors);
    }

    public static Quaternion multiply(Quaternion quaternion, Quaternion quaternion2) {
        Preconditions.checkNotNull(quaternion, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(quaternion2, "Parameter \"rhs\" was null.");
        float f = quaternion.f117x;
        float f2 = quaternion.f118y;
        float f3 = quaternion.f119z;
        float f4 = quaternion.f116w;
        float f5 = quaternion2.f117x;
        float f6 = quaternion2.f118y;
        float f7 = quaternion2.f119z;
        float f8 = quaternion2.f116w;
        return new Quaternion((((f4 * f5) + (f * f8)) + (f2 * f7)) - (f3 * f6), (((f4 * f6) - (f * f7)) + (f2 * f8)) + (f3 * f5), (((f4 * f7) + (f * f6)) - (f2 * f5)) + (f3 * f8), (((f4 * f8) - (f * f5)) - (f2 * f6)) - (f3 * f7));
    }

    public static Vector3 rotateVector(Quaternion quaternion, Vector3 vector3) {
        Quaternion quaternion2 = quaternion;
        Vector3 vector32 = vector3;
        Preconditions.checkNotNull(quaternion2, "Parameter \"q\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"src\" was null.");
        Vector3 vector33 = new Vector3();
        float f = quaternion2.f116w * quaternion2.f116w;
        float f2 = quaternion2.f117x * quaternion2.f117x;
        float f3 = quaternion2.f118y * quaternion2.f118y;
        float f4 = quaternion2.f119z * quaternion2.f119z;
        float f5 = quaternion2.f119z * quaternion2.f116w;
        float f6 = quaternion2.f117x * quaternion2.f118y;
        float f7 = quaternion2.f117x * quaternion2.f119z;
        float f8 = quaternion2.f118y * quaternion2.f116w;
        float f9 = quaternion2.f118y * quaternion2.f119z;
        float f10 = quaternion2.f117x * quaternion2.f116w;
        float f11 = ((f + f2) - f4) - f3;
        float f12 = ((f6 + f5) + f5) + f6;
        float f13 = ((f7 - f8) + f7) - f8;
        float f14 = (((-f5) + f6) - f5) + f6;
        f5 = ((f3 - f4) + f) - f2;
        f9 += f9;
        f6 = (f9 + f10) + f10;
        float f15 = ((f8 + f7) + f7) + f8;
        f9 = (f9 - f10) - f10;
        f4 = ((f4 - f3) - f2) + f;
        f = vector32.f120x;
        f2 = vector32.f121y;
        float f16 = vector32.f122z;
        vector33.f120x = ((f11 * f) + (f14 * f2)) + (f15 * f16);
        vector33.f121y = ((f12 * f) + (f5 * f2)) + (f9 * f16);
        vector33.f122z = ((f13 * f) + (f6 * f2)) + (f4 * f16);
        return vector33;
    }

    public static Quaternion rotationBetweenVectors(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"start\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"end\" was null.");
        vector3 = vector3.normalized();
        vector32 = vector32.normalized();
        float dot = Vector3.dot(vector3, vector32);
        if (dot < -0.999f) {
            vector32 = Vector3.cross(Vector3.back(), vector3);
            if (vector32.lengthSquared() < 0.01f) {
                vector32 = Vector3.cross(Vector3.right(), vector3);
            }
            return axisAngle(vector32.normalized(), 180.0f);
        }
        vector3 = Vector3.cross(vector3, vector32);
        float sqrt = (float) Math.sqrt((((double) dot) + 1.0d) * 2.0d);
        dot = 1.0f / sqrt;
        return new Quaternion(vector3.f120x * dot, vector3.f121y * dot, vector3.f122z * dot, sqrt * 0.5f);
    }

    public static Quaternion slerp(Quaternion quaternion, Quaternion quaternion2, float f) {
        Preconditions.checkNotNull(quaternion, "Parameter \"start\" was null.");
        Preconditions.checkNotNull(quaternion2, "Parameter \"end\" was null.");
        quaternion = quaternion.normalized();
        quaternion2 = quaternion2.normalized();
        double dot = (double) dot(quaternion, quaternion2);
        if (dot < 0.0d) {
            quaternion2 = quaternion2.negated();
            dot = -dot;
        }
        if (dot > 0.9994999766349792d) {
            return lerp(quaternion, quaternion2, f);
        }
        dot = Math.max(-1.0d, Math.min(1.0d, dot));
        double acos = Math.acos(dot);
        double d = ((double) f) * acos;
        return add(quaternion.scaled((float) (Math.cos(d) - ((dot * Math.sin(d)) / Math.sin(acos)))), quaternion2.scaled((float) (Math.sin(d) / Math.sin(acos)))).normalized();
    }

    public Quaternion inverted() {
        return new Quaternion(-this.f117x, -this.f118y, -this.f119z, this.f116w);
    }

    Quaternion negated() {
        return new Quaternion(-this.f117x, -this.f118y, -this.f119z, -this.f116w);
    }

    public boolean normalize() {
        float dot = dot(this, this);
        if (MathHelper.almostEqualRelativeAndAbs(dot, 0.0f)) {
            setIdentity();
            return false;
        }
        if (dot != 1.0f) {
            dot = (float) (1.0d / Math.sqrt((double) dot));
            this.f117x *= dot;
            this.f118y *= dot;
            this.f119z *= dot;
            this.f116w *= dot;
        }
        return true;
    }

    public Quaternion normalized() {
        Quaternion quaternion = new Quaternion(this);
        quaternion.normalize();
        return quaternion;
    }

    Quaternion scaled(float f) {
        Quaternion quaternion = new Quaternion();
        quaternion.f117x = this.f117x * f;
        quaternion.f118y = this.f118y * f;
        quaternion.f119z = this.f119z * f;
        quaternion.f116w = this.f116w * f;
        return quaternion;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.f117x = f;
        this.f118y = f2;
        this.f119z = f3;
        this.f116w = f4;
        normalize();
    }

    public void set(Quaternion quaternion) {
        Preconditions.checkNotNull(quaternion, "Parameter \"q\" was null.");
        this.f117x = quaternion.f117x;
        this.f118y = quaternion.f118y;
        this.f119z = quaternion.f119z;
        this.f116w = quaternion.f116w;
        normalize();
    }

    public void set(Vector3 vector3, float f) {
        Preconditions.checkNotNull(vector3, "Parameter \"axis\" was null.");
        set(axisAngle(vector3, f));
    }

    public void setIdentity() {
        this.f117x = 0.0f;
        this.f118y = 0.0f;
        this.f119z = 0.0f;
        this.f116w = 1.0f;
    }

    public String toString() {
        float f = this.f117x;
        float f2 = this.f118y;
        float f3 = this.f119z;
        float f4 = this.f116w;
        StringBuilder stringBuilder = new StringBuilder(76);
        stringBuilder.append("[x=");
        stringBuilder.append(f);
        stringBuilder.append(", y=");
        stringBuilder.append(f2);
        stringBuilder.append(", z=");
        stringBuilder.append(f3);
        stringBuilder.append(", w=");
        stringBuilder.append(f4);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
