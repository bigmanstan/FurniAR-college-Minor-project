package com.google.ar.core;

import com.google.ar.core.annotations.UsedByNative;
import java.util.Locale;

@UsedByNative("session_jni_wrapper.cc")
public class Pose {
    static final Pose GL_WORLD_T_START_OF_SERVICE = new Pose(new float[]{0.0f, 0.0f, 0.0f}, new float[]{((float) (-Math.sqrt(2.0d))) / 2.0f, 0.0f, 0.0f, ((float) Math.sqrt(2.0d)) / 2.0f});
    public static final Pose IDENTITY = new Pose(new float[]{0.0f, 0.0f, 0.0f}, Quaternion.f23a);
    static final Pose START_OF_SERVICE_T_GL_WORLD = new Pose(new float[]{0.0f, 0.0f, 0.0f}, new float[]{((float) Math.sqrt(2.0d)) / 2.0f, 0.0f, 0.0f, ((float) Math.sqrt(2.0d)) / 2.0f});
    @UsedByNative("session_jni_wrapper.cc")
    private final Quaternion quaternion;
    @UsedByNative("session_jni_wrapper.cc")
    private final float[] translation;

    private Pose(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.quaternion = new Quaternion(f4, f5, f6, f7);
        this.translation = new float[]{f, f2, f3};
    }

    @UsedByNative("session_jni_wrapper.cc")
    private Pose(float[] fArr, Quaternion quaternion) {
        this.translation = fArr;
        this.quaternion = quaternion;
    }

    public Pose(float[] fArr, float[] fArr2) {
        this(fArr[0], fArr[1], fArr[2], fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
    }

    public static Pose makeInterpolated(Pose pose, Pose pose2, float f) {
        if (f == 0.0f) {
            return pose;
        }
        if (f == 1.0f) {
            return pose2;
        }
        float[] fArr = new float[3];
        for (int i = 0; i < 3; i++) {
            fArr[i] = (pose.translation[i] * (1.0f - f)) + (pose2.translation[i] * f);
        }
        return new Pose(fArr, Quaternion.m44a(pose.quaternion, pose2.quaternion, f));
    }

    public static Pose makeRotation(float f, float f2, float f3, float f4) {
        return new Pose(IDENTITY.translation, new Quaternion(f, f2, f3, f4));
    }

    public static Pose makeRotation(float[] fArr) {
        return makeRotation(fArr[0], fArr[1], fArr[2], fArr[3]);
    }

    public static Pose makeTranslation(float f, float f2, float f3) {
        return new Pose(new float[]{f, f2, f3}, IDENTITY.quaternion);
    }

    public static Pose makeTranslation(float[] fArr) {
        return makeTranslation(fArr[0], fArr[1], fArr[2]);
    }

    public Pose compose(Pose pose) {
        r0 = new float[3];
        Quaternion.m46a(this.quaternion, pose.translation, 0, r0, 0);
        r0[0] = r0[0] + this.translation[0];
        r0[1] = r0[1] + this.translation[1];
        r0[2] = r0[2] + this.translation[2];
        return new Pose(r0, this.quaternion.m48a(pose.quaternion));
    }

    public Pose extractRotation() {
        return new Pose(IDENTITY.translation, this.quaternion);
    }

    public Pose extractTranslation() {
        return new Pose(this.translation, IDENTITY.quaternion);
    }

    Quaternion getQuaternion() {
        return this.quaternion;
    }

    public void getRotationQuaternion(float[] fArr, int i) {
        this.quaternion.m49a(fArr, i);
    }

    public float[] getRotationQuaternion() {
        float[] fArr = new float[4];
        getRotationQuaternion(fArr, 0);
        return fArr;
    }

    public void getTransformedAxis(int i, float f, float[] fArr, int i2) {
        Quaternion quaternion = this.quaternion;
        float[] fArr2 = new float[]{0.0f, 0.0f, 0.0f};
        fArr2[i] = f;
        Quaternion.m46a(quaternion, fArr2, 0, fArr, i2);
    }

    public float[] getTransformedAxis(int i, float f) {
        float[] fArr = new float[3];
        getTransformedAxis(i, f, fArr, 0);
        return fArr;
    }

    public void getTranslation(float[] fArr, int i) {
        System.arraycopy(this.translation, 0, fArr, i, 3);
    }

    public float[] getTranslation() {
        float[] fArr = new float[3];
        getTranslation(fArr, 0);
        return fArr;
    }

    public float[] getXAxis() {
        return getTransformedAxis(0, 1.0f);
    }

    public float[] getYAxis() {
        return getTransformedAxis(1, 1.0f);
    }

    public float[] getZAxis() {
        return getTransformedAxis(2, 1.0f);
    }

    public Pose inverse() {
        r0 = new float[3];
        Quaternion e = this.quaternion.m54e();
        Quaternion.m46a(e, this.translation, 0, r0, 0);
        r0[0] = -r0[0];
        r0[1] = -r0[1];
        r0[2] = -r0[2];
        return new Pose(r0, e);
    }

    public float qw() {
        return this.quaternion.m53d();
    }

    public float qx() {
        return this.quaternion.m47a();
    }

    public float qy() {
        return this.quaternion.m51b();
    }

    public float qz() {
        return this.quaternion.m52c();
    }

    public void rotateVector(float[] fArr, int i, float[] fArr2, int i2) {
        Quaternion.m46a(this.quaternion, fArr, i, fArr2, i2);
    }

    public float[] rotateVector(float[] fArr) {
        float[] fArr2 = new float[3];
        rotateVector(fArr, 0, fArr2, 0);
        return fArr2;
    }

    public void toMatrix(float[] fArr, int i) {
        this.quaternion.m50a(fArr, i, 4);
        fArr[i + 12] = this.translation[0];
        fArr[(i + 1) + 12] = this.translation[1];
        fArr[(i + 2) + 12] = this.translation[2];
        fArr[i + 3] = 0.0f;
        fArr[i + 7] = 0.0f;
        fArr[i + 11] = 0.0f;
        fArr[i + 15] = 1.0f;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "t:[x:%.3f, y:%.3f, z:%.3f], q:[x:%.2f, y:%.2f, z:%.2f, w:%.2f]", new Object[]{Float.valueOf(this.translation[0]), Float.valueOf(this.translation[1]), Float.valueOf(this.translation[2]), Float.valueOf(this.quaternion.m47a()), Float.valueOf(this.quaternion.m51b()), Float.valueOf(this.quaternion.m52c()), Float.valueOf(this.quaternion.m53d())});
    }

    public void transformPoint(float[] fArr, int i, float[] fArr2, int i2) {
        rotateVector(fArr, i, fArr2, i2);
        for (int i3 = 0; i3 < 3; i3++) {
            i = i3 + i2;
            fArr2[i] = fArr2[i] + this.translation[i3];
        }
    }

    public float[] transformPoint(float[] fArr) {
        float[] fArr2 = new float[3];
        transformPoint(fArr, 0, fArr2, 0);
        return fArr2;
    }

    public float tx() {
        return this.translation[0];
    }

    public float ty() {
        return this.translation[1];
    }

    public float tz() {
        return this.translation[2];
    }
}
