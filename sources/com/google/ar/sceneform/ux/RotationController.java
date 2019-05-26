package com.google.ar.sceneform.ux;

import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;

public class RotationController extends BaseTransformationController<TwistGesture> {
    private float rotationRateDegrees = 2.5f;

    public RotationController(BaseTransformableNode transformableNode, TwistGestureRecognizer gestureRecognizer) {
        super(transformableNode, gestureRecognizer);
    }

    public void setRotationRateDegrees(float rotationRateDegrees) {
        this.rotationRateDegrees = rotationRateDegrees;
    }

    public float getRotationRateDegrees() {
        return this.rotationRateDegrees;
    }

    public boolean canStartTransformation(TwistGesture gesture) {
        return getTransformableNode().isSelected();
    }

    public void onContinueTransformation(TwistGesture gesture) {
        getTransformableNode().setLocalRotation(Quaternion.multiply(getTransformableNode().getLocalRotation(), new Quaternion(Vector3.up(), (-gesture.getDeltaRotationDegrees()) * this.rotationRateDegrees)));
    }

    public void onEndTransformation(TwistGesture gesture) {
    }
}
