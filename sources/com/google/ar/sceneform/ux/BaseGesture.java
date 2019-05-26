package com.google.ar.sceneform.ux;

import android.support.annotation.Nullable;
import android.view.MotionEvent;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;

public abstract class BaseGesture<T extends BaseGesture<T>> {
    @Nullable
    private OnGestureEventListener<T> eventListener;
    protected final GesturePointersUtility gesturePointersUtility;
    private boolean hasFinished;
    private boolean hasStarted;
    private boolean justStarted;
    @Nullable
    protected Node targetNode;
    private boolean wasCancelled;

    public interface OnGestureEventListener<T extends BaseGesture<T>> {
        void onFinished(T t);

        void onUpdated(T t);
    }

    protected abstract boolean canStart(HitTestResult hitTestResult, MotionEvent motionEvent);

    protected abstract T getSelf();

    protected abstract void onCancel();

    protected abstract void onFinish();

    protected abstract void onStart(HitTestResult hitTestResult, MotionEvent motionEvent);

    protected abstract boolean updateGesture(HitTestResult hitTestResult, MotionEvent motionEvent);

    public BaseGesture(GesturePointersUtility gesturePointersUtility) {
        this.gesturePointersUtility = gesturePointersUtility;
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public boolean justStarted() {
        return this.justStarted;
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public boolean wasCancelled() {
        return this.wasCancelled;
    }

    @Nullable
    public Node getTargetNode() {
        return this.targetNode;
    }

    public float inchesToPixels(float inches) {
        return this.gesturePointersUtility.inchesToPixels(inches);
    }

    public float pixelsToInches(float pixels) {
        return this.gesturePointersUtility.pixelsToInches(pixels);
    }

    public void setGestureEventListener(@Nullable OnGestureEventListener<T> listener) {
        this.eventListener = listener;
    }

    public void onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (this.hasStarted || !canStart(hitTestResult, motionEvent)) {
            this.justStarted = false;
            if (this.hasStarted && updateGesture(hitTestResult, motionEvent)) {
                dispatchUpdateEvent();
            }
            return;
        }
        start(hitTestResult, motionEvent);
    }

    protected void cancel() {
        this.wasCancelled = true;
        onCancel();
        complete();
    }

    protected void complete() {
        this.hasFinished = true;
        if (this.hasStarted) {
            onFinish();
            dispatchFinishedEvent();
        }
    }

    private void start(HitTestResult hitTestResult, MotionEvent motionEvent) {
        this.hasStarted = true;
        this.justStarted = true;
        onStart(hitTestResult, motionEvent);
    }

    private void dispatchUpdateEvent() {
        if (this.eventListener != null) {
            this.eventListener.onUpdated(getSelf());
        }
    }

    private void dispatchFinishedEvent() {
        if (this.eventListener != null) {
            this.eventListener.onFinished(getSelf());
        }
    }
}
