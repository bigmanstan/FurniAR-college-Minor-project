package com.google.ar.sceneform.collision;

import android.util.Log;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class Box extends CollisionShape {
    private static final String TAG = Box.class.getSimpleName();
    private final Vector3 center;
    private final Matrix rotationMatrix;
    private final Vector3 size;

    public Box() {
        this.center = Vector3.zero();
        this.size = Vector3.one();
        this.rotationMatrix = new Matrix();
    }

    public Box(Vector3 vector3) {
        this(vector3, Vector3.zero());
    }

    public Box(Vector3 vector3, Vector3 vector32) {
        this.center = Vector3.zero();
        this.size = Vector3.one();
        this.rotationMatrix = new Matrix();
        Preconditions.checkNotNull(vector32, "Parameter \"center\" was null.");
        Preconditions.checkNotNull(vector3, "Parameter \"size\" was null.");
        setCenter(vector32);
        setSize(vector3);
    }

    protected boolean boxIntersection(Box box) {
        return C0393b.m92a(this, box);
    }

    public Vector3 getCenter() {
        return new Vector3(this.center);
    }

    public Vector3 getExtents() {
        return getSize().scaled(0.5f);
    }

    Matrix getRawRotationMatrix() {
        return this.rotationMatrix;
    }

    public Quaternion getRotation() {
        return this.rotationMatrix.extractQuaternion();
    }

    public Vector3 getSize() {
        return new Vector3(this.size);
    }

    public Box makeCopy() {
        return new Box(getSize(), getCenter());
    }

    protected boolean rayIntersection(Ray ray, RayHit rayHit) {
        Ray ray2 = ray;
        RayHit rayHit2 = rayHit;
        Preconditions.checkNotNull(ray2, "Parameter \"ray\" was null.");
        Preconditions.checkNotNull(rayHit2, "Parameter \"result\" was null.");
        Vector3 direction = ray.getDirection();
        Vector3 origin = ray.getOrigin();
        Vector3 extents = getExtents();
        Vector3 negated = extents.negated();
        origin = Vector3.subtract(this.center, origin);
        float[] fArr = this.rotationMatrix.data;
        Vector3 vector3 = new Vector3(fArr[0], fArr[1], fArr[2]);
        float dot = Vector3.dot(vector3, origin);
        float dot2 = Vector3.dot(direction, vector3);
        float f = Float.MAX_VALUE;
        float f2 = Float.MIN_VALUE;
        if (MathHelper.almostEqualRelativeAndAbs(dot2, 0.0f)) {
            dot2 = -dot;
            if (negated.f120x + dot2 <= 0.0f) {
                if (dot2 + extents.f120x < 0.0f) {
                }
            }
            return false;
        }
        float f3 = (negated.f120x + dot) / dot2;
        dot2 = (dot + extents.f120x) / dot2;
        if (f3 > dot2) {
            float f4 = f3;
            f3 = dot2;
            dot2 = f4;
        }
        f = Math.min(dot2, Float.MAX_VALUE);
        f2 = Math.max(f3, Float.MIN_VALUE);
        if (f < f2) {
            return false;
        }
        vector3 = new Vector3(fArr[4], fArr[5], fArr[6]);
        dot = Vector3.dot(vector3, origin);
        dot2 = Vector3.dot(direction, vector3);
        if (MathHelper.almostEqualRelativeAndAbs(dot2, 0.0f)) {
            dot2 = -dot;
            if (negated.f121y + dot2 <= 0.0f) {
                if (dot2 + extents.f121y < 0.0f) {
                }
            }
            return false;
        }
        f3 = (negated.f121y + dot) / dot2;
        dot2 = (dot + extents.f121y) / dot2;
        if (f3 > dot2) {
            f4 = f3;
            f3 = dot2;
            dot2 = f4;
        }
        f = Math.min(dot2, f);
        f2 = Math.max(f3, f2);
        if (f < f2) {
            return false;
        }
        vector3 = new Vector3(fArr[8], fArr[9], fArr[10]);
        float dot3 = Vector3.dot(vector3, origin);
        float dot4 = Vector3.dot(direction, vector3);
        if (MathHelper.almostEqualRelativeAndAbs(dot4, 0.0f)) {
            dot3 = -dot3;
            if (negated.f122z + dot3 <= 0.0f) {
                if (dot3 + extents.f122z < 0.0f) {
                }
            }
            return false;
        }
        float f5 = (negated.f122z + dot3) / dot4;
        dot3 = (dot3 + extents.f122z) / dot4;
        if (f5 > dot3) {
            f4 = f5;
            f5 = dot3;
            dot3 = f4;
        }
        dot3 = Math.min(dot3, f);
        f2 = Math.max(f5, f2);
        if (dot3 < f2) {
            return false;
        }
        rayHit2.setDistance(f2);
        rayHit2.setPoint(ray2.getPoint(rayHit.getDistance()));
        return true;
    }

    public void setCenter(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"center\" was null.");
        this.center.set(vector3);
        onChanged();
    }

    public void setRotation(Quaternion quaternion) {
        Preconditions.checkNotNull(quaternion, "Parameter \"rotation\" was null.");
        this.rotationMatrix.makeRotation(quaternion);
        onChanged();
    }

    public void setSize(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"size\" was null.");
        this.size.set(vector3);
        onChanged();
    }

    protected boolean shapeIntersection(CollisionShape collisionShape) {
        Preconditions.checkNotNull(collisionShape, "Parameter \"shape\" was null.");
        return collisionShape.boxIntersection(this);
    }

    protected boolean sphereIntersection(Sphere sphere) {
        return C0393b.m93a(sphere, this);
    }

    CollisionShape transform(TransformProvider transformProvider) {
        Preconditions.checkNotNull(transformProvider, "Parameter \"transformProvider\" was null.");
        CollisionShape box = new Box();
        transform(transformProvider, box);
        return box;
    }

    void transform(TransformProvider transformProvider, CollisionShape collisionShape) {
        Preconditions.checkNotNull(transformProvider, "Parameter \"transformProvider\" was null.");
        Preconditions.checkNotNull(collisionShape, "Parameter \"result\" was null.");
        if (collisionShape instanceof Box) {
            Box box = (Box) collisionShape;
            Matrix worldModelMatrix = transformProvider.getWorldModelMatrix();
            box.center.set(worldModelMatrix.transformPoint(this.center));
            Vector3 extractScale = worldModelMatrix.extractScale();
            box.size.f120x = this.size.f120x * extractScale.f120x;
            box.size.f121y = this.size.f121y * extractScale.f121y;
            box.size.f122z = this.size.f122z * extractScale.f122z;
            Matrix matrix = new Matrix();
            worldModelMatrix.extractRotation(matrix);
            Matrix.multiply(this.rotationMatrix, matrix, box.rotationMatrix);
            return;
        }
        Log.w(TAG, "Cannot pass CollisionShape of a type other than Box into Box.transform.");
    }
}
