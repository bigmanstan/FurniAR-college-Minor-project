package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class Plane {
    private static final double NEAR_ZERO_THRESHOLD = 1.0E-6d;
    private final Vector3 center = new Vector3();
    private final Vector3 normal = new Vector3();

    public Plane(Vector3 vector3, Vector3 vector32) {
        setCenter(vector3);
        setNormal(vector32);
    }

    public Vector3 getCenter() {
        return new Vector3(this.center);
    }

    public Vector3 getNormal() {
        return new Vector3(this.normal);
    }

    public boolean rayIntersection(Ray ray, RayHit rayHit) {
        Preconditions.checkNotNull(ray, "Parameter \"ray\" was null.");
        Preconditions.checkNotNull(rayHit, "Parameter \"result\" was null.");
        Vector3 direction = ray.getDirection();
        Vector3 origin = ray.getOrigin();
        float dot = Vector3.dot(this.normal, direction);
        if (((double) dot) > NEAR_ZERO_THRESHOLD) {
            float dot2 = Vector3.dot(Vector3.subtract(this.center, origin), this.normal) / dot;
            if (dot2 >= 0.0f) {
                rayHit.setDistance(dot2);
                rayHit.setPoint(ray.getPoint(rayHit.getDistance()));
                return true;
            }
        }
        return false;
    }

    public void setCenter(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"center\" was null.");
        this.center.set(vector3);
    }

    public void setNormal(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"normal\" was null.");
        this.normal.set(vector3.normalized());
    }
}
