package com.google.ar.sceneform.rendering;

import android.support.annotation.RequiresApi;
import com.google.android.filament.Colors;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import java.util.ArrayList;
import java.util.Iterator;

@RequiresApi(api = 24)
public class Light {
    private static final float MIN_LIGHT_INTENSITY = 1.0E-4f;
    private final ArrayList<LightChangedListener> changedListeners;
    private final Color color;
    private Vector3 direction;
    private final boolean enableShadows;
    private float falloffRadius;
    private float intensity;
    private Vector3 position;
    private float spotlightConeInner;
    private float spotlightConeOuter;
    private final Type type;

    public static final class Builder {
        private static final float DEFAULT_DIRECTIONAL_INTENSITY = 420.0f;
        private Color color;
        private Vector3 direction;
        private boolean enableShadows;
        private float falloffRadius;
        private float intensity;
        private Vector3 position;
        private float spotlightConeInner;
        private float spotlightConeOuter;
        private final Type type;

        private Builder(Type type) {
            this.enableShadows = false;
            this.position = new Vector3(0.0f, 0.0f, 0.0f);
            this.direction = new Vector3(0.0f, 0.0f, -1.0f);
            this.color = new Color(1.0f, 1.0f, 1.0f);
            this.intensity = 2500.0f;
            this.falloffRadius = 10.0f;
            this.spotlightConeInner = 0.5f;
            this.spotlightConeOuter = 0.6f;
            this.type = type;
            if (type == Type.DIRECTIONAL) {
                this.intensity = DEFAULT_DIRECTIONAL_INTENSITY;
            }
        }

        public Builder setShadowCastingEnabled(boolean enableShadows) {
            this.enableShadows = enableShadows;
            return this;
        }

        public Builder setColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder setColorTemperature(float temperature) {
            float[] rgbColor = Colors.cct(temperature);
            setColor(new Color(rgbColor[0], rgbColor[1], rgbColor[2]));
            return this;
        }

        public Builder setIntensity(float intensity) {
            this.intensity = intensity;
            return this;
        }

        public Builder setFalloffRadius(float falloffRadius) {
            this.falloffRadius = falloffRadius;
            return this;
        }

        public Builder setInnerConeAngle(float coneInner) {
            this.spotlightConeInner = coneInner;
            return this;
        }

        public Builder setOuterConeAngle(float coneOuter) {
            this.spotlightConeOuter = coneOuter;
            return this;
        }

        public Light build() {
            return new Light();
        }
    }

    interface LightChangedListener {
        void onChange();
    }

    public enum Type {
        POINT,
        DIRECTIONAL,
        SPOTLIGHT,
        FOCUSED_SPOTLIGHT
    }

    public static Builder builder(Type type) {
        AndroidPreconditions.checkMinAndroidApiLevel();
        return new Builder(type);
    }

    public void setColor(Color color) {
        this.color.set(color);
        fireChangedListeners();
    }

    public void setColorTemperature(float temperature) {
        float[] rgbColor = Colors.cct(temperature);
        setColor(new Color(rgbColor[0], rgbColor[1], rgbColor[2]));
    }

    public void setIntensity(float intensity) {
        this.intensity = Math.max(intensity, MIN_LIGHT_INTENSITY);
        fireChangedListeners();
    }

    public void setFalloffRadius(float falloffRadius) {
        this.falloffRadius = falloffRadius;
        fireChangedListeners();
    }

    public void setInnerConeAngle(float coneInner) {
        this.spotlightConeInner = coneInner;
        fireChangedListeners();
    }

    public void setOuterConeAngle(float coneOuter) {
        this.spotlightConeOuter = coneOuter;
        fireChangedListeners();
    }

    public Type getType() {
        return this.type;
    }

    public boolean isShadowCastingEnabled() {
        return this.enableShadows;
    }

    public Vector3 getLocalPosition() {
        return new Vector3(this.position);
    }

    public Vector3 getLocalDirection() {
        return new Vector3(this.direction);
    }

    public Color getColor() {
        return new Color(this.color);
    }

    public float getIntensity() {
        return this.intensity;
    }

    public float getFalloffRadius() {
        return this.falloffRadius;
    }

    public float getInnerConeAngle() {
        return this.spotlightConeInner;
    }

    public float getOuterConeAngle() {
        return this.spotlightConeOuter;
    }

    public LightInstance createInstance(TransformProvider transformProvider) {
        return new LightInstance(this, transformProvider);
    }

    void addChangedListener(LightChangedListener listener) {
        this.changedListeners.add(listener);
    }

    void removeChangedListener(LightChangedListener listener) {
        this.changedListeners.remove(listener);
    }

    private Light(Builder builder) {
        this.changedListeners = new ArrayList();
        this.type = builder.type;
        this.enableShadows = builder.enableShadows;
        this.position = builder.position;
        this.direction = builder.direction;
        this.color = builder.color;
        this.intensity = builder.intensity;
        this.falloffRadius = builder.falloffRadius;
        this.spotlightConeInner = builder.spotlightConeInner;
        this.spotlightConeOuter = builder.spotlightConeOuter;
    }

    private void fireChangedListeners() {
        Iterator it = this.changedListeners.iterator();
        while (it.hasNext()) {
            ((LightChangedListener) it.next()).onChange();
        }
    }
}
