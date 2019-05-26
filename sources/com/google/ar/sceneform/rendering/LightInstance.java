package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.Entity;
import com.google.android.filament.EntityManager;
import com.google.android.filament.LightManager;
import com.google.android.filament.LightManager.Builder;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Light.Type;
import com.google.ar.sceneform.utilities.AndroidPreconditions;

public class LightInstance {
    private static final String TAG = LightInstance.class.getSimpleName();
    private LightInstanceChangeListener changeListener = new LightInstanceChangeListener();
    private boolean dirty;
    @Entity
    private final int entity;
    private final Light light;
    private Vector3 localDirection;
    private Vector3 localPosition;
    @Nullable
    private Renderer renderer;
    @Nullable
    private TransformProvider transformProvider = null;

    private class LightInstanceChangeListener implements LightChangedListener {
        private LightInstanceChangeListener() {
        }

        public void onChange() {
            LightInstance.this.dirty = true;
        }
    }

    LightInstance(Light light, TransformProvider transformProvider) {
        this.light = light;
        this.transformProvider = transformProvider;
        this.localPosition = light.getLocalPosition();
        this.localDirection = light.getLocalDirection();
        this.dirty = false;
        light.addChangedListener(this.changeListener);
        this.entity = EntityManager.get().create();
        Engine engine = EngineInstance.getEngine();
        if (light.getType() == Type.POINT) {
            new Builder(LightManager.Type.POINT).position(light.getLocalPosition().f120x, light.getLocalPosition().f121y, light.getLocalPosition().f122z).color(light.getColor().f126r, light.getColor().f125g, light.getColor().f124b).intensity(light.getIntensity()).falloff(light.getFalloffRadius()).castShadows(light.isShadowCastingEnabled()).build(engine, this.entity);
        } else if (light.getType() == Type.DIRECTIONAL) {
            new Builder(LightManager.Type.DIRECTIONAL).direction(light.getLocalDirection().f120x, light.getLocalDirection().f121y, light.getLocalDirection().f122z).color(light.getColor().f126r, light.getColor().f125g, light.getColor().f124b).intensity(light.getIntensity()).castShadows(light.isShadowCastingEnabled()).build(engine, this.entity);
        } else if (light.getType() == Type.SPOTLIGHT) {
            new Builder(LightManager.Type.SPOT).position(light.getLocalPosition().f120x, light.getLocalPosition().f121y, light.getLocalPosition().f122z).direction(light.getLocalDirection().f120x, light.getLocalDirection().f121y, light.getLocalDirection().f122z).color(light.getColor().f126r, light.getColor().f125g, light.getColor().f124b).intensity(light.getIntensity()).spotLightCone(Math.min(light.getInnerConeAngle(), light.getOuterConeAngle()), light.getOuterConeAngle()).castShadows(light.isShadowCastingEnabled()).build(engine, this.entity);
        } else if (light.getType() == Type.FOCUSED_SPOTLIGHT) {
            new Builder(LightManager.Type.FOCUSED_SPOT).position(light.getLocalPosition().f120x, light.getLocalPosition().f121y, light.getLocalPosition().f122z).direction(light.getLocalDirection().f120x, light.getLocalDirection().f121y, light.getLocalDirection().f122z).color(light.getColor().f126r, light.getColor().f125g, light.getColor().f124b).intensity(light.getIntensity()).spotLightCone(Math.min(light.getInnerConeAngle(), light.getOuterConeAngle()), light.getOuterConeAngle()).castShadows(light.isShadowCastingEnabled()).build(engine, this.entity);
        } else {
            throw new UnsupportedOperationException("Unsupported light type.");
        }
    }

    public void updateTransform() {
        updateProperties();
        if (this.transformProvider != null) {
            Vector3 position;
            LightManager lightManager = EngineInstance.getEngine().getLightManager();
            int instance = lightManager.getInstance(this.entity);
            Matrix transform = this.transformProvider.getWorldModelMatrix();
            if (lightTypeRequiresPosition(this.light.getType())) {
                position = transform.transformPoint(this.localPosition);
                lightManager.setPosition(instance, position.f120x, position.f121y, position.f122z);
            }
            if (lightTypeRequiresDirection(this.light.getType())) {
                position = transform.transformDirection(this.localDirection);
                lightManager.setDirection(instance, position.f120x, position.f121y, position.f122z);
            }
        }
    }

    public void attachToRenderer(Renderer renderer) {
        renderer.addLight(this);
        this.renderer = renderer;
    }

    public void detachFromRenderer() {
        if (this.renderer != null) {
            this.renderer.removeLight(this);
        }
    }

    public Light getLight() {
        return this.light;
    }

    @Entity
    int getEntity() {
        return this.entity;
    }

    public void dispose() {
        AndroidPreconditions.checkUiThread();
        if (this.light != null) {
            this.light.removeChangedListener(this.changeListener);
            this.changeListener = null;
        }
        Engine engine = EngineInstance.getEngine();
        if (engine != null && engine.isValid()) {
            engine.getLightManager().destroy(this.entity);
            EntityManager.get().destroy(this.entity);
        }
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$LightInstance$VQVPwtNewJV1X6-FQZVAEXQ5taU());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Light Instance.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    private void updateProperties() {
        if (this.dirty) {
            this.dirty = false;
            LightManager lightManager = EngineInstance.getEngine().getLightManager();
            int instance = lightManager.getInstance(this.entity);
            this.localPosition = this.light.getLocalPosition();
            this.localDirection = this.light.getLocalDirection();
            if (this.transformProvider == null) {
                if (lightTypeRequiresPosition(this.light.getType())) {
                    lightManager.setPosition(instance, this.localPosition.f120x, this.localPosition.f121y, this.localPosition.f122z);
                }
                if (lightTypeRequiresDirection(this.light.getType())) {
                    lightManager.setDirection(instance, this.localDirection.f120x, this.localDirection.f121y, this.localDirection.f122z);
                }
            }
            lightManager.setColor(instance, this.light.getColor().f126r, this.light.getColor().f125g, this.light.getColor().f124b);
            lightManager.setIntensity(instance, this.light.getIntensity());
            if (this.light.getType() == Type.POINT) {
                lightManager.setFalloff(instance, this.light.getFalloffRadius());
            } else if (this.light.getType() == Type.SPOTLIGHT || this.light.getType() == Type.FOCUSED_SPOTLIGHT) {
                lightManager.setSpotLightCone(instance, Math.min(this.light.getInnerConeAngle(), this.light.getOuterConeAngle()), this.light.getOuterConeAngle());
            }
        }
    }

    private static boolean lightTypeRequiresPosition(Type type) {
        if (!(type == Type.POINT || type == Type.SPOTLIGHT)) {
            if (type != Type.FOCUSED_SPOTLIGHT) {
                return false;
            }
        }
        return true;
    }

    private static boolean lightTypeRequiresDirection(Type type) {
        if (!(type == Type.SPOTLIGHT || type == Type.FOCUSED_SPOTLIGHT)) {
            if (type != Type.DIRECTIONAL) {
                return false;
            }
        }
        return true;
    }
}
