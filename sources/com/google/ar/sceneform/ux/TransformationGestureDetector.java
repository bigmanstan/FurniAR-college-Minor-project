package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;

@Deprecated
public interface TransformationGestureDetector {
    DragGestureRecognizer getDragRecognizer();

    PinchGestureRecognizer getPinchRecognizer();

    TwistGestureRecognizer getTwistRecognizer();

    void onTouch(HitTestResult hitTestResult, MotionEvent motionEvent);
}
