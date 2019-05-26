package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.math.Vector3;

public class PinchGesture extends BaseGesture<PinchGesture> {
    private static final boolean PINCH_GESTURE_DEBUG = false;
    private static final float SLOP_INCHES = 0.05f;
    private static final float SLOP_MOTION_DIRECTION_DEGREES = 30.0f;
    private static final String TAG = PinchGesture.class.getSimpleName();
    private float gap;
    private float gapDelta;
    private final int pointerId1;
    private final int pointerId2;
    private final Vector3 previousPosition1 = new Vector3(this.startPosition1);
    private final Vector3 previousPosition2 = new Vector3(this.startPosition2);
    private final Vector3 startPosition1;
    private final Vector3 startPosition2;

    public interface OnGestureEventListener extends com.google.ar.sceneform.ux.BaseGesture.OnGestureEventListener<PinchGesture> {
    }

    public PinchGesture(GesturePointersUtility gesturePointersUtility, MotionEvent motionEvent, int pointerId2) {
        super(gesturePointersUtility);
        this.pointerId1 = motionEvent.getPointerId(motionEvent.getActionIndex());
        this.pointerId2 = pointerId2;
        this.startPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1);
        this.startPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent, pointerId2);
        debugLog("Created");
    }

    public float getGap() {
        return this.gap;
    }

    public float gapInches() {
        return this.gesturePointersUtility.pixelsToInches(getGap());
    }

    public float getGapDelta() {
        return this.gapDelta;
    }

    public float gapDeltaInches() {
        return this.gesturePointersUtility.pixelsToInches(getGapDelta());
    }

    protected boolean canStart(HitTestResult hitTestResult, MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        if (!this.gesturePointersUtility.isPointerIdRetained(this.pointerId1)) {
            if (!r0.gesturePointersUtility.isPointerIdRetained(r0.pointerId2)) {
                int actionId = motionEvent2.getPointerId(motionEvent.getActionIndex());
                int action = motionEvent.getActionMasked();
                if (action == 3) {
                    cancel();
                    return false;
                }
                boolean touchEnded;
                Vector3 firstToSecond;
                Vector3 firstToSecondDirection;
                Vector3 newPosition1;
                Vector3 newPosition2;
                Vector3 deltaPosition1;
                Vector3 deltaPosition2;
                float dot1;
                float dot2;
                float dotThreshold;
                float startGap;
                if (action != 1) {
                    if (action != 6) {
                        touchEnded = false;
                        if (!touchEnded && (actionId == r0.pointerId1 || actionId == r0.pointerId2)) {
                            cancel();
                            return false;
                        } else if (action != 2) {
                            return false;
                        } else {
                            firstToSecond = Vector3.subtract(r0.startPosition1, r0.startPosition2);
                            firstToSecondDirection = firstToSecond.normalized();
                            newPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent2, r0.pointerId1);
                            newPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent2, r0.pointerId2);
                            deltaPosition1 = Vector3.subtract(newPosition1, r0.previousPosition1);
                            deltaPosition2 = Vector3.subtract(newPosition2, r0.previousPosition2);
                            r0.previousPosition1.set(newPosition1);
                            r0.previousPosition2.set(newPosition2);
                            dot1 = Vector3.dot(deltaPosition1.normalized(), firstToSecondDirection.negated());
                            dot2 = Vector3.dot(deltaPosition2.normalized(), firstToSecondDirection);
                            dotThreshold = (float) Math.cos(Math.toRadians(30.0d));
                            if (Vector3.equals(deltaPosition1, Vector3.zero()) && Math.abs(dot1) < dotThreshold) {
                                return false;
                            }
                            if (Vector3.equals(deltaPosition2, Vector3.zero()) && Math.abs(dot2) < dotThreshold) {
                                return false;
                            }
                            startGap = firstToSecond.length();
                            r0.gap = Vector3.subtract(newPosition1, newPosition2).length();
                            if (Math.abs(r0.gap - startGap) >= r0.gesturePointersUtility.inchesToPixels(SLOP_INCHES)) {
                                return false;
                            }
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
                firstToSecond = Vector3.subtract(r0.startPosition1, r0.startPosition2);
                firstToSecondDirection = firstToSecond.normalized();
                newPosition1 = GesturePointersUtility.motionEventToPosition(motionEvent2, r0.pointerId1);
                newPosition2 = GesturePointersUtility.motionEventToPosition(motionEvent2, r0.pointerId2);
                deltaPosition1 = Vector3.subtract(newPosition1, r0.previousPosition1);
                deltaPosition2 = Vector3.subtract(newPosition2, r0.previousPosition2);
                r0.previousPosition1.set(newPosition1);
                r0.previousPosition2.set(newPosition2);
                dot1 = Vector3.dot(deltaPosition1.normalized(), firstToSecondDirection.negated());
                dot2 = Vector3.dot(deltaPosition2.normalized(), firstToSecondDirection);
                dotThreshold = (float) Math.cos(Math.toRadians(30.0d));
                if (Vector3.equals(deltaPosition1, Vector3.zero())) {
                }
                if (Vector3.equals(deltaPosition2, Vector3.zero())) {
                }
                startGap = firstToSecond.length();
                r0.gap = Vector3.subtract(newPosition1, newPosition2).length();
                if (Math.abs(r0.gap - startGap) >= r0.gesturePointersUtility.inchesToPixels(SLOP_INCHES)) {
                    return true;
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
        float newGap;
        if (action != 1) {
            if (action != 6) {
                touchEnded = false;
                if (!touchEnded && (actionId == this.pointerId1 || actionId == this.pointerId2)) {
                    complete();
                    return false;
                } else if (action != 2) {
                    return false;
                } else {
                    newGap = Vector3.subtract(GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1), GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId2)).length();
                    if (newGap == this.gap) {
                        return false;
                    }
                    this.gapDelta = newGap - this.gap;
                    this.gap = newGap;
                    float f = this.gapDelta;
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
        newGap = Vector3.subtract(GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId1), GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId2)).length();
        if (newGap == this.gap) {
            return false;
        }
        this.gapDelta = newGap - this.gap;
        this.gap = newGap;
        float f2 = this.gapDelta;
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

    protected PinchGesture getSelf() {
        return this;
    }

    private static void debugLog(String log) {
    }
}
