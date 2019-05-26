package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.utilities.ChangeId;

public abstract class CollisionShape {
    private final ChangeId changeId = new ChangeId();

    CollisionShape() {
        this.changeId.update();
    }

    protected abstract boolean boxIntersection(Box box);

    ChangeId getId() {
        return this.changeId;
    }

    public abstract CollisionShape makeCopy();

    protected void onChanged() {
        this.changeId.update();
    }

    protected abstract boolean rayIntersection(Ray ray, RayHit rayHit);

    protected abstract boolean shapeIntersection(CollisionShape collisionShape);

    protected abstract boolean sphereIntersection(Sphere sphere);

    abstract CollisionShape transform(TransformProvider transformProvider);

    abstract void transform(TransformProvider transformProvider, CollisionShape collisionShape);
}
