package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.math.Vector3;

public class TwistGesture extends BaseGesture<TwistGesture> {
    private static final float SLOP_ROTATION_DEGREES = 15.0f;
    private static final String TAG = TwistGesture.class.getSimpleName();
    private static final boolean TWIST_GESTURE_DEBUG = false;
    private float deltaRotationDegrees;
    private final int pointerId1;
    private final int pointerId2;
    private final Vector3 previousPosition1 = new Vector3(this.startPosition1);
    private final Vector3 previousPosition2 = new Vector3(this.startPosition2);
    private final Vector3 startPosition1;
    private final Vector3 startPosition2;

    public interface OnGestureEventListener extends com.google.ar.sceneform.ux.BaseGesture.OnGestureEventListener<TwistGesture> {
    }

    public TwistGesture(GesturePointersUtility gesturePointersUtility, MotionEvent motionEvent, int pointerId2) {
        super(gesturePointersUtility);
        this.pointerId1 = motionEvent.getPointerId(motionEvent.getActionIndex());
        this.pointerId2 = pointerId2;
        this.startPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1);
        this.startPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent, pointerId2);
        debugLog("Created");
    }

    public float getDeltaRotationDegrees() {
        return this.deltaRotationDegrees;
    }

    protected boolean canStart(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (!this.gesturePointersUtility.isPointerIdRetained(this.pointerId1)) {
            if (!this.gesturePointersUtility.isPointerIdRetained(this.pointerId2)) {
                int actionId = motionEvent.getPointerId(motionEvent.getActionIndex());
                int action = motionEvent.getActionMasked();
                if (action == 3) {
                    cancel();
                    return false;
                }
                boolean touchEnded;
                Vector3 newPosition1;
                Vector3 newPosition2;
                Vector3 deltaPosition1;
                Vector3 deltaPosition2;
                if (action != 1) {
                    if (action != 6) {
                        touchEnded = false;
                        if (!touchEnded && (actionId == this.pointerId1 || actionId == this.pointerId2)) {
                            cancel();
                            return false;
                        } else if (action != 2) {
                            return false;
                        } else {
                            newPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1);
                            newPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId2);
                            deltaPosition1 = Vector3.subtract(newPosition1, this.previousPosition1);
                            deltaPosition2 = Vector3.subtract(newPosition2, this.previousPosition2);
                            this.previousPosition1.set(newPosition1);
                            this.previousPosition2.set(newPosition2);
                            if (!Vector3.equals(deltaPosition1, Vector3.zero())) {
                                if (Vector3.equals(deltaPosition2, Vector3.zero())) {
                                    if (Math.abs(calculateDeltaRotation(newPosition1, newPosition2, this.startPosition1, this.startPosition2)) >= SLOP_ROTATION_DEGREES) {
                                        return false;
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                }
                touchEnded = true;
                if (!touchEnded) {
                }
                if (action != 2) {
                    return false;
                }
                newPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1);
                newPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId2);
                deltaPosition1 = Vector3.subtract(newPosition1, this.previousPosition1);
                deltaPosition2 = Vector3.subtract(newPosition2, this.previousPosition2);
                this.previousPosition1.set(newPosition1);
                this.previousPosition2.set(newPosition2);
                if (Vector3.equals(deltaPosition1, Vector3.zero())) {
                    if (Vector3.equals(deltaPosition2, Vector3.zero())) {
                        if (Math.abs(calculateDeltaRotation(newPosition1, newPosition2, this.startPosition1, this.startPosition2)) >= SLOP_ROTATION_DEGREES) {
                            return true;
                        }
                        return false;
                    }
                }
                return false;
            }
        }
        cancel();
        return false;
    }

    protected void onStart(HitTestResult hitTestResult, MotionEvent motionEvent) {
        debugLog("Started");
        this.gesturePointersUtility.retainPointerId(this.pointerId1);
        this.gesturePointersUtility.retainPointerId(this.pointerId2);
    }

    protected boolean updateGesture(HitTestResult hitTestResult, MotionEvent motionEvent) {
        int actionId = motionEvent.getPointerId(motionEvent.getActionIndex());
        int action = motionEvent.getActionMasked();
        if (action == 3) {
            cancel();
            return false;
        }
        boolean touchEnded;
        if (action != 1) {
            if (action != 6) {
                touchEnded = false;
                if (!touchEnded && (actionId == this.pointerId1 || actionId == this.pointerId2)) {
                    complete();
                    return false;
                } else if (action != 2) {
                    return false;
                } else {
                    Vector3 newPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1);
                    Vector3 newPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId2);
                    this.deltaRotationDegrees = calculateDeltaRotation(newPosition1, newPosition2, this.previousPosition1, this.previousPosition2);
                    this.previousPosition1.set(newPosition1);
                    this.previousPosition2.set(newPosition2);
                    float f = this.deltaRotationDegrees;
                    StringBuilder stringBuilder = new StringBuilder(23);
                    stringBuilder.append("Update: ");
                    stringBuilder.append(f);
                    debugLog(stringBuilder.toString());
                    return true;
                }
            }
        }
        touchEnded = true;
        if (!touchEnded) {
        }
        if (action != 2) {
            return false;
        }
        Vector3 newPosition12 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1);
        Vector3 newPosition22 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId2);
        this.deltaRotationDegrees = calculateDeltaRotation(newPosition12, newPosition22, this.previousPosition1, this.previousPosition2);
        this.previousPosition1.set(newPosition12);
        this.previousPosition2.set(newPosition22);
        float f2 = this.deltaRotationDegrees;
        StringBuilder stringBuilder2 = new StringBuilder(23);
        stringBuilder2.append("Update: ");
        stringBuilder2.append(f2);
        debugLog(stringBuilder2.toString());
        return true;
    }

    protected void onCancel() {
        debugLog("Cancelled");
    }

    protected void onFinish() {
        debugLog("Finished");
        this.gesturePointersUtility.releasePointerId(this.pointerId1);
        this.gesturePointersUtility.releasePointerId(this.pointerId2);
    }

    protected TwistGesture getSelf() {
        return this;
    }

    private static void debugLog(String log) {
    }

    private static float calculateDeltaRotation(Vector3 currentPosition1, Vector3 currentPosition2, Vector3 previousPosition1, Vector3 previousPosition2) {
        Vector3 currentDirection = Vector3.subtract(currentPosition1, currentPosition2).normalized();
        Vector3 previousDirection = Vector3.subtract(previousPosition1, previousPosition2).normalized();
        return Vector3.angleBetweenVectors(currentDirection, previousDirection) * Math.signum((previousDirection.f120x * currentDirection.f121y) - (previousDirection.f121y * currentDirection.f120x));
    }
}
