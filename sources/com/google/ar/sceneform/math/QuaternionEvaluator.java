package com.google.ar.sceneform.math;

import android.animation.TypeEvaluator;

public class QuaternionEvaluator implements TypeEvaluator<Quaternion> {
    public Quaternion evaluate(float f, Quaternion quaternion, Quaternion quaternion2) {
        return Quaternion.slerp(quaternion, quaternion2, f);
    }
}
