package com.google.ar.sceneform.ux;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Vector3;

public class ScaleController extends BaseTransformationController<PinchGesture> {
    public static final float DEFAULT_ELASTICITY = 0.15f;
    public static final float DEFAULT_MAX_SCALE = 1.75f;
    public static final float DEFAULT_MIN_SCALE = 0.75f;
    public static final float DEFAULT_SENSITIVITY = 0.75f;
    private static final float ELASTIC_RATIO_LIMIT = 0.8f;
    private static final float LERP_SPEED = 8.0f;
    private float currentScaleRatio;
    private float elasticity = 0.15f;
    private float maxScale = 1.75f;
    private float minScale = 0.75f;
    private float sensitivity = 0.75f;

    public ScaleController(BaseTransformableNode transformableNode, PinchGestureRecognizer gestureRecognizer) {
        super(transformableNode, gestureRecognizer);
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public float getMinScale() {
        return this.minScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public float getMaxScale() {
        return this.maxScale;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public float getSensitivity() {
        return this.sensitivity;
    }

    public void setElasticity(float elasticity) {
        this.elasticity = elasticity;
    }

    public float getElasticity() {
        return this.elasticity;
    }

    public void onActivated(Node node) {
        super.onActivated(node);
        this.currentScaleRatio = (getTransformableNode().getLocalScale().f120x - this.minScale) / getScaleDelta();
    }

    public void onUpdated(Node node, FrameTime frameTime) {
        if (!isTransforming()) {
            this.currentScaleRatio = MathHelper.lerp(this.currentScaleRatio, getClampedScaleRatio(), MathHelper.clamp(frameTime.getDeltaSeconds() * LERP_SPEED, 0.0f, 1.0f));
            float finalScaleValue = getFinalScale();
            getTransformableNode().setLocalScale(new Vector3(finalScaleValue, finalScaleValue, finalScaleValue));
        }
    }

    public boolean canStartTransformation(PinchGesture gesture) {
        return getTransformableNode().isSelected();
    }

    public void onContinueTransformation(PinchGesture gesture) {
        this.currentScaleRatio += gesture.gapDeltaInches() * this.sensitivity;
        float finalScaleValue = getFinalScale();
        getTransformableNode().setLocalScale(new Vector3(finalScaleValue, finalScaleValue, finalScaleValue));
        if (this.currentScaleRatio < -0.8f || this.currentScaleRatio > 1.8f) {
            gesture.cancel();
        }
    }

    public void onEndTransformation(PinchGesture gesture) {
    }

    private float getScaleDelta() {
        float scaleDelta = this.maxScale - this.minScale;
        if (scaleDelta > 0.0f) {
            return scaleDelta;
        }
        throw new IllegalStateException("maxScale must be greater than minScale.");
    }

    private float getClampedScaleRatio() {
        return Math.min(1.0f, Math.max(0.0f, this.currentScaleRatio));
    }

    private float getFinalScale() {
        return this.minScale + (getScaleDelta() * (getClampedScaleRatio() + getElasticDelta()));
    }

    private float getElasticDelta() {
        float overRatio;
        if (this.currentScaleRatio > 1.0f) {
            overRatio = this.currentScaleRatio - 1.0f;
        } else if (this.currentScaleRatio >= 0.0f) {
            return 0.0f;
        } else {
            overRatio = this.currentScaleRatio;
        }
        return (1.0f - (1.0f / ((Math.abs(overRatio) * this.elasticity) + 1.0f))) * Math.signum(overRatio);
    }
}
