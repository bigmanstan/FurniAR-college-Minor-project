package com.google.ar.sceneform;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Light;
import com.google.ar.sceneform.rendering.Light.Type;
import com.google.ar.sceneform.utilities.Preconditions;

public class Sun extends Node {
    static final int DEFAULT_SUNLIGHT_COLOR = -863292;
    static final Vector3 DEFAULT_SUNLIGHT_DIRECTION = new Vector3(0.7f, -1.0f, -0.8f);
    private static final float LIGHT_ESTIMATE_OFFSET = 0.0f;
    private static final float LIGHT_ESTIMATE_SCALE = 1.8f;
    private float baseIntensity = 0.0f;

    Sun() {
    }

    Sun(Scene scene) {
        Preconditions.checkNotNull(scene, "Parameter \"scene\" was null.");
        super.setParent(scene);
        setupDefaultLighting(scene.getView());
    }

    private void setupDefaultLighting(SceneView sceneView) {
        Preconditions.checkNotNull(sceneView, "Parameter \"view\" was null.");
        Color color = new Color((int) DEFAULT_SUNLIGHT_COLOR);
        setLookDirection(DEFAULT_SUNLIGHT_DIRECTION.normalized());
        Light build = Light.builder(Type.DIRECTIONAL).setColor(color).setShadowCastingEnabled(true).build();
        if (build != null) {
            setLight(build);
            return;
        }
        throw new AssertionError("Failed to create the default sunlight.");
    }

    void setLightEstimate(float f) {
        Light light = getLight();
        if (light != null) {
            if (this.baseIntensity == 0.0f) {
                this.baseIntensity = light.getIntensity();
            }
            light.setIntensity(this.baseIntensity * Math.min((f * LIGHT_ESTIMATE_SCALE) + 0.0f, 1.0f));
        }
    }

    public void setParent(NodeParent nodeParent) {
        throw new UnsupportedOperationException("Camera's parent cannot be changed, it is always the scene.");
    }
}
