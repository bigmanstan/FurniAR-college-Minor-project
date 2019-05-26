package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;

public class PinchGestureRecognizer extends BaseGestureRecognizer<PinchGesture> {

    public interface OnGestureStartedListener extends com.google.ar.sceneform.ux.BaseGestureRecognizer.OnGestureStartedListener<PinchGesture> {
    }

    public PinchGestureRecognizer(GesturePointersUtility gesturePointersUtility) {
        super(gesturePointersUtility);
    }

    protected void tryCreateGestures(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() >= 2) {
            boolean touchBegan;
            int pointerId;
            int actionId = motionEvent.getPointerId(motionEvent.getActionIndex());
            int action = motionEvent.getActionMasked();
            int i = 0;
            if (action != 0) {
                if (action != 5) {
                    touchBegan = false;
                    if (touchBegan) {
                        if (this.gesturePointersUtility.isPointerIdRetained(actionId)) {
                            while (i < motionEvent.getPointerCount()) {
                                pointerId = motionEvent.getPointerId(i);
                                if (pointerId == actionId) {
                                    if (this.gesturePointersUtility.isPointerIdRetained(pointerId)) {
                                        this.gestures.add(new PinchGesture(this.gesturePointersUtility, motionEvent, pointerId));
                                    }
                                }
                                i++;
                            }
                        }
                    }
                }
            }
            touchBegan = true;
            if (touchBegan) {
                if (this.gesturePointersUtility.isPointerIdRetained(actionId)) {
                    while (i < motionEvent.getPointerCount()) {
                        pointerId = motionEvent.getPointerId(i);
                        if (pointerId == actionId) {
                            if (this.gesturePointersUtility.isPointerIdRetained(pointerId)) {
                                this.gestures.add(new PinchGesture(this.gesturePointersUtility, motionEvent, pointerId));
                            }
                        }
                        i++;
                    }
                }
            }
        }
    }
}
