package com.google.ar.sceneform.ux;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import com.google.ar.sceneform.math.Vector3;
import java.util.HashSet;

public class GesturePointersUtility {
    private final DisplayMetrics displayMetrics;
    private final HashSet<Integer> retainedPointerIds = new HashSet();

    public GesturePointersUtility(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    public void retainPointerId(int pointerId) {
        if (!isPointerIdRetained(pointerId)) {
            this.retainedPointerIds.add(Integer.valueOf(pointerId));
        }
    }

    public void releasePointerId(int pointerId) {
        this.retainedPointerIds.remove(Integer.valueOf(pointerId));
    }

    public boolean isPointerIdRetained(int pointerId) {
        return this.retainedPointerIds.contains(Integer.valueOf(pointerId));
    }

    public float inchesToPixels(float inches) {
        return TypedValue.applyDimension(4, inches, this.displayMetrics);
    }

    public float pixelsToInches(float pixels) {
        return pixels / TypedValue.applyDimension(4, 1.0f, this.displayMetrics);
    }

    public static Vector3 motionEventToPosition(MotionEvent me, int pointerId) {
        int index = me.findPointerIndex(pointerId);
        return new Vector3(me.getX(index), me.getY(index), 0.0f);
    }
}
