package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class RayHit {
    private float distance = Float.MAX_VALUE;
    private final Vector3 point = new Vector3();

    public float getDistance() {
        return this.distance;
    }

    public Vector3 getPoint() {
        return new Vector3(this.point);
    }

    public void reset() {
        this.distance = Float.MAX_VALUE;
        this.point.set(0.0f, 0.0f, 0.0f);
    }

    public void set(RayHit rayHit) {
        Preconditions.checkNotNull(rayHit, "Parameter \"other\" was null.");
        setDistance(rayHit.distance);
        setPoint(rayHit.point);
    }

    public void setDistance(float f) {
        this.distance = f;
    }

    public void setPoint(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"point\" was null.");
        this.point.set(vector3);
    }
}
