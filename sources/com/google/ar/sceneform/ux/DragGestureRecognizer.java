package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;

public class DragGestureRecognizer extends BaseGestureRecognizer<DragGesture> {

    public interface OnGestureStartedListener extends com.google.ar.sceneform.ux.BaseGestureRecognizer.OnGestureStartedListener<DragGesture> {
    }

    public DragGestureRecognizer(GesturePointersUtility gesturePointersUtility) {
        super(gesturePointersUtility);
    }

    protected void tryCreateGestures(HitTestResult hitTestResult, MotionEvent motionEvent) {
        boolean touchBegan;
        int action = motionEvent.getActionMasked();
        int actionId = motionEvent.getPointerId(motionEvent.getActionIndex());
        if (action != 0) {
            if (action != 5) {
                touchBegan = false;
                if (touchBegan && !this.gesturePointersUtility.isPointerIdRetained(actionId)) {
                    this.gestures.add(new DragGesture(this.gesturePointersUtility, hitTestResult, motionEvent));
                    return;
                }
            }
        }
        touchBegan = true;
        if (touchBegan) {
        }
    }
}
