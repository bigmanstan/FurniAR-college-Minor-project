package com.google.ar.sceneform.ux;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Node.LifecycleListener;
import com.google.ar.sceneform.ux.BaseGesture.OnGestureEventListener;
import com.google.ar.sceneform.ux.BaseGestureRecognizer.OnGestureStartedListener;

public abstract class BaseTransformationController<T extends BaseGesture<T>> implements OnGestureStartedListener<T>, OnGestureEventListener<T>, LifecycleListener {
    private boolean activeAndEnabled;
    @Nullable
    private T activeGesture;
    private boolean enabled;
    private final BaseGestureRecognizer<T> gestureRecognizer;
    private final BaseTransformableNode transformableNode;

    protected abstract boolean canStartTransformation(T t);

    protected abstract void onContinueTransformation(T t);

    protected abstract void onEndTransformation(T t);

    public BaseTransformationController(BaseTransformableNode transformableNode, BaseGestureRecognizer<T> gestureRecognizer) {
        this.transformableNode = transformableNode;
        this.transformableNode.addLifecycleListener(this);
        this.gestureRecognizer = gestureRecognizer;
        setEnabled(true);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Nullable
    public T getActiveGesture() {
        return this.activeGesture;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateActiveAndEnabled();
    }

    public boolean isTransforming() {
        return this.activeGesture != null;
    }

    public BaseTransformableNode getTransformableNode() {
        return this.transformableNode;
    }

    @CallSuper
    public void onActivated(Node node) {
        updateActiveAndEnabled();
    }

    public void onUpdated(Node node, FrameTime frameTime) {
    }

    @CallSuper
    public void onDeactivated(Node node) {
        updateActiveAndEnabled();
    }

    public void onGestureStarted(T gesture) {
        if (!isTransforming() && canStartTransformation(gesture)) {
            setActiveGesture(gesture);
        }
    }

    public void onUpdated(T gesture) {
        onContinueTransformation(gesture);
    }

    public void onFinished(T gesture) {
        onEndTransformation(gesture);
        setActiveGesture(null);
    }

    private void setActiveGesture(@Nullable T gesture) {
        if (this.activeGesture != null) {
            this.activeGesture.setGestureEventListener(null);
        }
        this.activeGesture = gesture;
        if (this.activeGesture != null) {
            this.activeGesture.setGestureEventListener(this);
        }
    }

    private void updateActiveAndEnabled() {
        boolean newActiveAndEnabled = getTransformableNode().isActive() && this.enabled;
        if (newActiveAndEnabled != this.activeAndEnabled) {
            this.activeAndEnabled = newActiveAndEnabled;
            if (this.activeAndEnabled) {
                connectToRecognizer();
            } else {
                disconnectFromRecognizer();
                if (this.activeGesture != null) {
                    this.activeGesture.cancel();
                }
            }
        }
    }

    private void connectToRecognizer() {
        this.gestureRecognizer.addOnGestureStartedListener(this);
    }

    private void disconnectFromRecognizer() {
        this.gestureRecognizer.removeOnGestureStartedListener(this);
    }
}
