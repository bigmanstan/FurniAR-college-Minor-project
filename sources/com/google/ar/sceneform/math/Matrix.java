package com.google.ar.sceneform.math;

import android.util.Log;
import com.google.ar.sceneform.utilities.Preconditions;

public class Matrix {
    public static final float[] IDENTITY_DATA = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final String TAG = Matrix.class.getSimpleName();
    public float[] data;

    public Matrix() {
        this.data = new float[16];
        set(IDENTITY_DATA);
    }

    public Matrix(float[] fArr) {
        this.data = new float[16];
        set(fArr);
    }

    public static boolean equals(Matrix matrix, Matrix matrix2) {
        Preconditions.checkNotNull(matrix, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(matrix2, "Parameter \"rhs\" was null.");
        boolean z = true;
        for (int i = 0; i < 16; i++) {
            z &= MathHelper.almostEqualRelativeAndAbs(matrix.data[i], matrix2.data[i]);
        }
        return z;
    }

    public static boolean invert(Matrix matrix, Matrix matrix2) {
        Matrix matrix3 = matrix;
        Matrix matrix4 = matrix2;
        Preconditions.checkNotNull(matrix3, "Parameter \"matrix\" was null.");
        Preconditions.checkNotNull(matrix4, "Parameter \"dest\" was null.");
        float f = matrix3.data[0];
        float f2 = matrix3.data[1];
        float f3 = matrix3.data[2];
        float f4 = matrix3.data[3];
        float f5 = matrix3.data[4];
        float f6 = matrix3.data[5];
        float f7 = matrix3.data[6];
        float f8 = matrix3.data[7];
        float f9 = matrix3.data[8];
        float f10 = matrix3.data[9];
        float f11 = matrix3.data[10];
        float f12 = matrix3.data[11];
        float f13 = matrix3.data[12];
        float f14 = matrix3.data[13];
        float f15 = f;
        f = matrix3.data[14];
        float f16 = matrix3.data[15];
        float f17 = f4;
        matrix4.data[0] = ((((((f6 * f11) * f16) - ((f6 * f12) * f)) - ((f10 * f7) * f16)) + ((f10 * f8) * f)) + ((f14 * f7) * f12)) - ((f14 * f8) * f11);
        float f18 = f3;
        f3 = -f5;
        float f19 = f5 * f12;
        float f20 = f9 * f7;
        float f21 = f9 * f8;
        float f22 = f13 * f7;
        float f23 = f13 * f8;
        matrix4.data[4] = ((((((f3 * f11) * f16) + (f19 * f)) + (f20 * f16)) - (f21 * f)) - (f22 * f12)) + (f23 * f11);
        float f24 = ((f5 * f10) * f16) - (f19 * f14);
        f19 = f9 * f6;
        f24 = (f24 - (f19 * f16)) + (f21 * f14);
        f21 = f13 * f6;
        matrix4.data[8] = (f24 + (f21 * f12)) - (f23 * f10);
        matrix4.data[12] = ((((((f3 * f10) * f) + ((f5 * f11) * f14)) + (f19 * f)) - (f20 * f14)) - (f21 * f11)) + (f22 * f10);
        f4 = -f2;
        f19 = f10 * f18;
        f20 = f10 * f17;
        f21 = f14 * f18;
        f22 = f14 * f17;
        matrix4.data[1] = ((((((f4 * f11) * f16) + ((f2 * f12) * f)) + (f19 * f16)) - (f20 * f)) - (f21 * f12)) + (f22 * f11);
        f24 = f15 * f11;
        float f25 = f15 * f12;
        float f26 = f9 * f18;
        float f27 = f9 * f17;
        float f28 = f13 * f18;
        float f29 = f13 * f17;
        matrix4.data[5] = (((((f24 * f16) - (f25 * f)) - (f26 * f16)) + (f27 * f)) + (f28 * f12)) - (f29 * f11);
        float f30 = f4;
        float f31 = f5;
        f4 = f15;
        f5 = -f4;
        f9 *= f2;
        f13 *= f2;
        matrix4.data[9] = ((((((f5 * f10) * f16) + (f25 * f14)) + (f9 * f16)) - (f27 * f14)) - (f13 * f12)) + (f29 * f10);
        matrix4.data[13] = ((((((f4 * f10) * f) - (f24 * f14)) - (f9 * f)) + (f26 * f14)) + (f13 * f11)) - (f28 * f10);
        float f32 = f2 * f8;
        float f33 = f6 * f18;
        f15 = f6 * f17;
        matrix4.data[2] = ((((((f2 * f7) * f16) - (f32 * f)) - (f33 * f16)) + (f15 * f)) + (f21 * f8)) - (f22 * f7);
        float f34 = f4 * f8;
        f24 = f31 * f18;
        f21 = f31 * f17;
        matrix4.data[6] = ((((((f5 * f7) * f16) + (f34 * f)) + (f24 * f16)) - (f21 * f)) - (f28 * f8)) + (f29 * f7);
        float f35 = f4 * f6;
        f22 = f31 * f2;
        matrix4.data[10] = (((((f35 * f16) - (f34 * f14)) - (f16 * f22)) + (f21 * f14)) + (f13 * f8)) - (f29 * f6);
        f5 *= f6;
        float f36 = f4 * f7;
        matrix4.data[14] = (((((f5 * f) + (f36 * f14)) + (f * f22)) - (f14 * f24)) - (f13 * f7)) + (f28 * f6);
        matrix4.data[3] = ((((((f30 * f7) * f12) + (f32 * f11)) + (f33 * f12)) - (f15 * f11)) - (f19 * f8)) + (f20 * f7);
        matrix4.data[7] = (((((f36 * f12) - (f34 * f11)) - (f24 * f12)) + (f21 * f11)) + (f26 * f8)) - (f27 * f7);
        matrix4.data[11] = (((((f5 * f12) + (f34 * f10)) + (f12 * f22)) - (f21 * f10)) - (f8 * f9)) + (f27 * f6);
        matrix4.data[15] = (((((f35 * f11) - (f36 * f10)) - (f22 * f11)) + (f24 * f10)) + (f9 * f7)) - (f26 * f6);
        f = (((f4 * matrix4.data[0]) + (f2 * matrix4.data[4])) + (f18 * matrix4.data[8])) + (f17 * matrix4.data[12]);
        if (f == 0.0f) {
            return false;
        }
        f16 = 1.0f / f;
        for (int i = 0; i < 16; i++) {
            float[] fArr = matrix4.data;
            fArr[i] = fArr[i] * f16;
        }
        return true;
    }

    public static void multiply(Matrix matrix, Matrix matrix2, Matrix matrix3) {
        Matrix matrix4 = matrix;
        Matrix matrix5 = matrix2;
        Matrix matrix6 = matrix3;
        Preconditions.checkNotNull(matrix4, "Parameter \"lhs\" was null.");
        Preconditions.checkNotNull(matrix5, "Parameter \"rhs\" was null.");
        float f = 0.0f;
        float f2 = f;
        float f3 = f2;
        float f4 = f3;
        float f5 = f4;
        float f6 = f5;
        float f7 = f6;
        float f8 = f7;
        float f9 = f8;
        float f10 = f9;
        float f11 = f10;
        float f12 = f11;
        float f13 = f12;
        float f14 = f13;
        float f15 = f14;
        float f16 = f15;
        int i = 0;
        while (i < 4) {
            int i2 = i << 2;
            int i3 = i2 + 0;
            int i4 = i + 0;
            int i5 = i2 + 1;
            float f17 = f + (matrix4.data[i3] * matrix5.data[i4]);
            int i6 = i2 + 2;
            f3 += matrix4.data[i6] * matrix5.data[i4];
            i2 += 3;
            f4 += matrix4.data[i2] * matrix5.data[i4];
            int i7 = i + 4;
            f5 += matrix4.data[i3] * matrix5.data[i7];
            f6 += matrix4.data[i5] * matrix5.data[i7];
            f7 += matrix4.data[i6] * matrix5.data[i7];
            f8 += matrix4.data[i2] * matrix5.data[i7];
            i7 = i + 8;
            f9 += matrix4.data[i3] * matrix5.data[i7];
            f10 += matrix4.data[i5] * matrix5.data[i7];
            f11 += matrix4.data[i6] * matrix5.data[i7];
            f12 += matrix4.data[i2] * matrix5.data[i7];
            i7 = i + 12;
            f13 += matrix4.data[i3] * matrix5.data[i7];
            f14 += matrix4.data[i5] * matrix5.data[i7];
            f15 += matrix4.data[i6] * matrix5.data[i7];
            f16 += matrix4.data[i2] * matrix5.data[i7];
            i++;
            f = f17;
            f2 += matrix4.data[i5] * matrix5.data[i4];
            matrix6 = matrix3;
        }
        matrix6.data[0] = f;
        matrix6.data[1] = f2;
        matrix6.data[2] = f3;
        matrix6.data[3] = f4;
        matrix6.data[4] = f5;
        matrix6.data[5] = f6;
        matrix6.data[6] = f7;
        matrix6.data[7] = f8;
        matrix6.data[8] = f9;
        matrix6.data[9] = f10;
        matrix6.data[10] = f11;
        matrix6.data[11] = f12;
        matrix6.data[12] = f13;
        matrix6.data[13] = f14;
        matrix6.data[14] = f15;
        matrix6.data[15] = f16;
    }

    public void decompose(Vector3 vector3, Vector3 vector32, Quaternion quaternion) {
        Preconditions.checkNotNull(vector3, "Parameter \"destTranslation\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"destScale\" was null.");
        Preconditions.checkNotNull(quaternion, "Parameter \"destQuaternion\" was null.");
        vector3.set(extractTranslation());
        vector32.set(extractScale());
        Matrix matrix = new Matrix();
        extractRotation(matrix);
        quaternion.set(matrix.extractQuaternion());
    }

    public Quaternion extractQuaternion() {
        float f;
        Quaternion quaternion = new Quaternion();
        float f2 = (this.data[0] + this.data[5]) + this.data[10];
        if (f2 > 0.0f) {
            f2 = ((float) Math.sqrt(((double) f2) + 1.0d)) * 2.0f;
            quaternion.f116w = 0.25f * f2;
            quaternion.f117x = (this.data[6] - this.data[9]) / f2;
            quaternion.f118y = (this.data[8] - this.data[2]) / f2;
            f = this.data[1] - this.data[4];
        } else {
            float f3;
            if (this.data[0] > this.data[5] && this.data[0] > this.data[10]) {
                f2 = ((float) Math.sqrt((double) (((this.data[0] + 1.0f) - this.data[5]) - this.data[10]))) * 2.0f;
                quaternion.f116w = (this.data[6] - this.data[9]) / f2;
                quaternion.f117x = 0.25f * f2;
                quaternion.f118y = (this.data[4] + this.data[1]) / f2;
                f = this.data[8];
                f3 = this.data[2];
            } else if (this.data[5] > this.data[10]) {
                f2 = ((float) Math.sqrt((double) (((this.data[5] + 1.0f) - this.data[0]) - this.data[10]))) * 2.0f;
                quaternion.f116w = (this.data[8] - this.data[2]) / f2;
                quaternion.f117x = (this.data[4] + this.data[1]) / f2;
                quaternion.f118y = 0.25f * f2;
                f = this.data[9];
                f3 = this.data[6];
            } else {
                f2 = ((float) Math.sqrt((double) (((this.data[10] + 1.0f) - this.data[0]) - this.data[5]))) * 2.0f;
                quaternion.f116w = (this.data[1] - this.data[4]) / f2;
                quaternion.f117x = (this.data[8] + this.data[2]) / f2;
                quaternion.f118y = (this.data[9] + this.data[6]) / f2;
                quaternion.f119z = f2 * 0.25f;
                quaternion.normalize();
                return quaternion;
            }
            f += f3;
        }
        quaternion.f119z = f / f2;
        quaternion.normalize();
        return quaternion;
    }

    public void extractRotation(Matrix matrix) {
        int i;
        Preconditions.checkNotNull(matrix, "Parameter \"dest\" was null.");
        Vector3 extractScale = extractScale();
        if (extractScale.f120x != 0.0f) {
            for (i = 0; i < 3; i++) {
                matrix.data[i] = this.data[i] / extractScale.f120x;
            }
        }
        matrix.data[3] = 0.0f;
        if (extractScale.f121y != 0.0f) {
            for (i = 4; i < 7; i++) {
                matrix.data[i] = this.data[i] / extractScale.f121y;
            }
        }
        matrix.data[7] = 0.0f;
        if (extractScale.f122z != 0.0f) {
            for (i = 8; i < 11; i++) {
                matrix.data[i] = this.data[i] / extractScale.f122z;
            }
        }
        matrix.data[11] = 0.0f;
        matrix.data[12] = 0.0f;
        matrix.data[13] = 0.0f;
        matrix.data[14] = 0.0f;
        matrix.data[15] = 0.0f;
    }

    public Vector3 extractScale() {
        Vector3 vector3 = new Vector3();
        Vector3 vector32 = new Vector3();
        vector32.set(this.data[0], this.data[1], this.data[2]);
        vector3.f120x = vector32.length();
        vector32.set(this.data[4], this.data[5], this.data[6]);
        vector3.f121y = vector32.length();
        vector32.set(this.data[8], this.data[9], this.data[10]);
        vector3.f122z = vector32.length();
        return vector3;
    }

    public Vector3 extractTranslation() {
        return new Vector3(this.data[12], this.data[13], this.data[14]);
    }

    public void makeRotation(Quaternion quaternion) {
        Preconditions.checkNotNull(quaternion, "Parameter \"rotation\" was null.");
        set(IDENTITY_DATA);
        quaternion.normalize();
        float f = quaternion.f117x * quaternion.f117x;
        float f2 = quaternion.f117x * quaternion.f118y;
        float f3 = quaternion.f117x * quaternion.f119z;
        float f4 = quaternion.f117x * quaternion.f116w;
        float f5 = quaternion.f118y * quaternion.f118y;
        float f6 = quaternion.f118y * quaternion.f119z;
        float f7 = quaternion.f118y * quaternion.f116w;
        float f8 = quaternion.f119z * quaternion.f119z;
        float f9 = quaternion.f119z * quaternion.f116w;
        this.data[0] = 1.0f - ((f5 + f8) * 2.0f);
        this.data[4] = (f2 - f9) * 2.0f;
        this.data[8] = (f3 + f7) * 2.0f;
        this.data[1] = (f2 + f9) * 2.0f;
        this.data[5] = 1.0f - ((f8 + f) * 2.0f);
        this.data[9] = (f6 - f4) * 2.0f;
        this.data[2] = (f3 - f7) * 2.0f;
        this.data[6] = (f6 + f4) * 2.0f;
        this.data[10] = 1.0f - ((f + f5) * 2.0f);
    }

    public void makeScale(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"scale\" was null.");
        set(IDENTITY_DATA);
        this.data[0] = vector3.f120x;
        this.data[5] = vector3.f121y;
        this.data[10] = vector3.f122z;
    }

    public void makeTranslation(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"translation\" was null.");
        set(IDENTITY_DATA);
        setTranslation(vector3);
    }

    public void set(Matrix matrix) {
        Preconditions.checkNotNull(matrix, "Parameter \"m\" was null.");
        set(matrix.data);
    }

    public void set(float[] fArr) {
        if (fArr != null) {
            if (fArr.length == 16) {
                for (int i = 0; i < fArr.length; i++) {
                    this.data[i] = fArr[i];
                }
                return;
            }
        }
        Log.w(TAG, "Cannot set Matrix, invalid data.");
    }

    public void setTranslation(Vector3 vector3) {
        this.data[12] = vector3.f120x;
        this.data[13] = vector3.f121y;
        this.data[14] = vector3.f122z;
    }

    public Vector3 transformDirection(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"vector\" was null.");
        Vector3 vector32 = new Vector3();
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        vector32.f120x = this.data[0] * f;
        vector32.f120x += this.data[4] * f2;
        vector32.f120x += this.data[8] * f3;
        vector32.f121y = this.data[1] * f;
        vector32.f121y += this.data[5] * f2;
        vector32.f121y += this.data[9] * f3;
        vector32.f122z = this.data[2] * f;
        vector32.f122z += this.data[6] * f2;
        vector32.f122z += this.data[10] * f3;
        return vector32;
    }

    public Vector3 transformPoint(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"vector\" was null.");
        Vector3 vector32 = new Vector3();
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        vector32.f120x = this.data[0] * f;
        vector32.f120x += this.data[4] * f2;
        vector32.f120x += this.data[8] * f3;
        vector32.f120x += this.data[12];
        vector32.f121y = this.data[1] * f;
        vector32.f121y += this.data[5] * f2;
        vector32.f121y += this.data[9] * f3;
        vector32.f121y += this.data[13];
        vector32.f122z = this.data[2] * f;
        vector32.f122z += this.data[6] * f2;
        vector32.f122z += this.data[10] * f3;
        vector32.f122z += this.data[14];
        return vector32;
    }
}
