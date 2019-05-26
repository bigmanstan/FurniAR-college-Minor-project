package com.google.ar.sceneform.ux;

import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import java.util.ArrayList;

public abstract class BaseGestureRecognizer<T extends BaseGesture<T>> {
    protected final GesturePointersUtility gesturePointersUtility;
    private final ArrayList<OnGestureStartedListener<T>> gestureStartedListeners;
    protected final ArrayList<T> gestures = new ArrayList();

    public interface OnGestureStartedListener<T extends BaseGesture<T>> {
        void onGestureStarted(T t);
    }

    protected abstract void tryCreateGestures(HitTestResult hitTestResult, MotionEvent motionEvent);

    public BaseGestureRecognizer(GesturePointersUtility gesturePointersUtility) {
        this.gesturePointersUtility = gesturePointersUtility;
        this.gestureStartedListeners = new ArrayList();
    }

    public void addOnGestureStartedListener(OnGestureStartedListener<T> listener) {
        if (!this.gestureStartedListeners.contains(listener)) {
            this.gestureStartedListeners.add(listener);
        }
    }

    public void removeOnGestureStartedListener(OnGestureStartedListener<T> listener) {
        this.gestureStartedListeners.remove(listener);
    }

    public void onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
        tryCreateGestures(hitTestResult, motionEvent);
        for (int i = 0; i < this.gestures.size(); i++) {
            BaseGesture gesture = (BaseGesture) this.gestures.get(i);
            gesture.onTouch(hitTestResult, motionEvent);
            if (gesture.justStarted()) {
                dispatchGestureStarted(gesture);
            }
        }
        removeFinishedGestures();
    }

    private void dispatchGestureStarted(T gesture) {
        for (int i = 0; i < this.gestureStartedListeners.size(); i++) {
            ((OnGestureStartedListener) this.gestureStartedListeners.get(i)).onGestureStarted(gesture);
        }
    }

    private void removeFinishedGestures() {
        for (int i = this.gestures.size() - 1; i >= 0; i--) {
            if (((BaseGesture) this.gestures.get(i)).hasFinished()) {
                this.gestures.remove(i);
            }
        }
    }
}
