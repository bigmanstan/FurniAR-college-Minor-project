package com.google.ar.sceneform.rendering;

import com.google.ar.core.Camera;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;

public interface CameraProvider extends TransformProvider {
    float getFarClipPlane();

    float getNearClipPlane();

    Matrix getProjectionMatrix();

    Matrix getViewMatrix();

    boolean isActive();

    void updateTrackedPose(Camera camera);
}
