package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.math.Vector3;

public class DragGesture extends BaseGesture<DragGesture> {
    private static final boolean DRAG_GESTURE_DEBUG = false;
    private static final float SLOP_INCHES = 0.1f;
    private static final String TAG = DragGesture.class.getSimpleName();
    private final Vector3 delta = Vector3.zero();
    private final int pointerId;
    private final Vector3 position = new Vector3(this.startPosition);
    private final Vector3 startPosition;

    public interface OnGestureEventListener extends com.google.ar.sceneform.ux.BaseGesture.OnGestureEventListener<DragGesture> {
    }

    public DragGesture(GesturePointersUtility gesturePointersUtility, HitTestResult hitTestResult, MotionEvent motionEvent) {
        super(gesturePointersUtility);
        this.pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
        this.startPosition = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId);
        this.targetNode = hitTestResult.getNode();
        int i = this.pointerId;
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append("Created: ");
        stringBuilder.append(i);
        debugLog(stringBuilder.toString());
    }

    public Vector3 getPosition() {
        return new Vector3(this.position);
    }

    public Vector3 getDelta() {
        return new Vector3(this.delta);
    }

    protected boolean canStart(HitTestResult hitTestResult, MotionEvent motionEvent) {
        int actionId = motionEvent.getPointerId(motionEvent.getActionIndex());
        int action = motionEvent.getActionMasked();
        if (this.gesturePointersUtility.isPointerIdRetained(this.pointerId)) {
            cancel();
            return false;
        } else if (actionId == this.pointerId && (action == 1 || action == 6)) {
            cancel();
            return false;
        } else if (action == 3) {
            cancel();
            return false;
        } else if (action != 2) {
            return false;
        } else {
            if (motionEvent.getPointerCount() > 1) {
                for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                    int id = motionEvent.getPointerId(i);
                    if (id != this.pointerId && !this.gesturePointersUtility.isPointerIdRetained(id)) {
                        return false;
                    }
                }
            }
            if (Vector3.subtract(GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId), this.startPosition).length() >= this.gesturePointersUtility.inchesToPixels(SLOP_INCHES)) {
                return true;
            }
            return false;
        }
    }

    protected void onStart(HitTestResult hitTestResult, MotionEvent motionEvent) {
        int i = this.pointerId;
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append("Started: ");
        stringBuilder.append(i);
        debugLog(stringBuilder.toString());
        this.position.set(GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId));
        this.gesturePointersUtility.retainPointerId(this.pointerId);
    }

    protected boolean updateGesture(HitTestResult hitTestResult, MotionEvent motionEvent) {
        int actionId = motionEvent.getPointerId(motionEvent.getActionIndex());
        int action = motionEvent.getActionMasked();
        if (action == 2) {
            Vector3 newPosition = GesturePointersUtility.motionEventToPosition(motionEvent, this.pointerId);
            if (!Vector3.equals(newPosition, this.position)) {
                this.delta.set(Vector3.subtract(newPosition, this.position));
                this.position.set(newPosition);
                int i = this.pointerId;
                String valueOf = String.valueOf(this.position);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 23);
                stringBuilder.append("Updated: ");
                stringBuilder.append(i);
                stringBuilder.append(" : ");
                stringBuilder.append(valueOf);
                debugLog(stringBuilder.toString());
                return true;
            }
        } else if (actionId == this.pointerId && (action == 1 || action == 6)) {
            complete();
        } else if (action == 3) {
            cancel();
        }
        return false;
    }

    protected void onCancel() {
        int i = this.pointerId;
        StringBuilder stringBuilder = new StringBuilder(22);
        stringBuilder.append("Cancelled: ");
        stringBuilder.append(i);
        debugLog(stringBuilder.toString());
    }

    protected void onFinish() {
        int i = this.pointerId;
        StringBuilder stringBuilder = new StringBuilder(21);
        stringBuilder.append("Finished: ");
        stringBuilder.append(i);
        debugLog(stringBuilder.toString());
        this.gesturePointersUtility.releasePointerId(this.pointerId);
    }

    protected DragGesture getSelf() {
        return this;
    }

    private static void debugLog(String log) {
    }
}
