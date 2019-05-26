package com.google.ar.sceneform.common;

import com.google.ar.sceneform.math.Matrix;

public interface TransformProvider {
    Matrix getWorldModelMatrix();
}
