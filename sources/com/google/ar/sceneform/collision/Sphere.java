package com.google.ar.sceneform.collision;

import android.util.Log;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class Sphere extends CollisionShape {
    private static final String TAG = Sphere.class.getSimpleName();
    private final Vector3 center;
    private float radius;

    public Sphere() {
        this.center = new Vector3();
        this.radius = 1.0f;
    }

    public Sphere(float f) {
        this(f, Vector3.zero());
    }

    public Sphere(float f, Vector3 vector3) {
        this.center = new Vector3();
        this.radius = 1.0f;
        Preconditions.checkNotNull(vector3, "Parameter \"center\" was null.");
        setCenter(vector3);
        setRadius(f);
    }

    protected boolean boxIntersection(Box box) {
        return C0393b.m93a(this, box);
    }

    public Vector3 getCenter() {
        return new Vector3(this.center);
    }

    public float getRadius() {
        return this.radius;
    }

    public Sphere makeCopy() {
        return new Sphere(getRadius(), getCenter());
    }

    protected boolean rayIntersection(Ray ray, RayHit rayHit) {
        Preconditions.checkNotNull(ray, "Parameter \"ray\" was null.");
        Preconditions.checkNotNull(rayHit, "Parameter \"result\" was null.");
        Vector3 direction = ray.getDirection();
        Vector3 subtract = Vector3.subtract(ray.getOrigin(), this.center);
        float dot = Vector3.dot(subtract, direction) * 2.0f;
        float dot2 = (dot * dot) - ((Vector3.dot(subtract, subtract) - (this.radius * this.radius)) * 4.0f);
        if (dot2 < 0.0f) {
            return false;
        }
        dot2 = (float) Math.sqrt((double) dot2);
        dot = -dot;
        float f = (dot - dot2) / 2.0f;
        dot = (dot + dot2) / 2.0f;
        int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        if (i < 0 && dot < 0.0f) {
            return false;
        }
        if (i >= 0 || dot <= 0.0f) {
            rayHit.setDistance(f);
        } else {
            rayHit.setDistance(dot);
        }
        rayHit.setPoint(ray.getPoint(rayHit.getDistance()));
        return true;
    }

    public void setCenter(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"center\" was null.");
        this.center.set(vector3);
        onChanged();
    }

    public void setRadius(float f) {
        this.radius = f;
        onChanged();
    }

    protected boolean shapeIntersection(CollisionShape collisionShape) {
        Preconditions.checkNotNull(collisionShape, "Parameter \"shape\" was null.");
        return collisionShape.sphereIntersection(this);
    }

    protected boolean sphereIntersection(Sphere sphere) {
        Preconditions.checkNotNull(this, "Parameter \"sphere1\" was null.");
        Preconditions.checkNotNull(sphere, "Parameter \"sphere2\" was null.");
        float radius = getRadius() + sphere.getRadius();
        radius *= radius;
        Vector3 subtract = Vector3.subtract(sphere.getCenter(), getCenter());
        float dot = Vector3.dot(subtract, subtract);
        return dot - radius <= 0.0f && dot != 0.0f;
    }

    CollisionShape transform(TransformProvider transformProvider) {
        Preconditions.checkNotNull(transformProvider, "Parameter \"transformProvider\" was null.");
        CollisionShape sphere = new Sphere();
        transform(transformProvider, sphere);
        return sphere;
    }

    void transform(TransformProvider transformProvider, CollisionShape collisionShape) {
        Preconditions.checkNotNull(transformProvider, "Parameter \"transformProvider\" was null.");
        Preconditions.checkNotNull(collisionShape, "Parameter \"result\" was null.");
        if (collisionShape instanceof Sphere) {
            Sphere sphere = (Sphere) collisionShape;
            Matrix worldModelMatrix = transformProvider.getWorldModelMatrix();
            sphere.setCenter(worldModelMatrix.transformPoint(this.center));
            Vector3 extractScale = worldModelMatrix.extractScale();
            sphere.radius = this.radius * Math.max(Math.abs(Math.min(Math.min(extractScale.f120x, extractScale.f121y), extractScale.f122z)), Math.max(Math.max(extractScale.f120x, extractScale.f121y), extractScale.f122z));
            return;
        }
        Log.w(TAG, "Cannot pass CollisionShape of a type other than Sphere into Sphere.transform.");
    }
}
