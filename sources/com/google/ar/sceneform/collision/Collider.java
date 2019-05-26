package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.utilities.ChangeId;
import com.google.ar.sceneform.utilities.Preconditions;

public class Collider {
    private CollisionSystem attachedCollisionSystem;
    private CollisionShape cachedWorldShape;
    private CollisionShape localShape;
    private int shapeId = 0;
    private TransformProvider transformProvider;

    public Collider(TransformProvider transformProvider, CollisionShape collisionShape) {
        Preconditions.checkNotNull(transformProvider, "Parameter \"transformProvider\" was null.");
        Preconditions.checkNotNull(collisionShape, "Parameter \"localCollisionShape\" was null.");
        this.transformProvider = transformProvider;
        setShape(collisionShape);
    }

    public CollisionShape getShape() {
        return this.localShape;
    }

    public TransformProvider getTransformProvider() {
        return this.transformProvider;
    }

    public CollisionShape getTransformedShape() {
        return this.cachedWorldShape;
    }

    public void onUpdate() {
        ChangeId id = this.localShape.getId();
        if (id.checkChanged(this.shapeId)) {
            updateCachedWorldShape();
            this.shapeId = id.get();
        }
    }

    public void setAttachedCollisionSystem(CollisionSystem collisionSystem) {
        if (this.attachedCollisionSystem != null) {
            this.attachedCollisionSystem.removeCollider(this);
        }
        this.attachedCollisionSystem = collisionSystem;
        if (this.attachedCollisionSystem != null) {
            this.attachedCollisionSystem.addCollider(this);
        }
    }

    public void setShape(CollisionShape collisionShape) {
        Preconditions.checkNotNull(collisionShape, "Parameter \"localCollisionShape\" was null.");
        this.localShape = collisionShape;
        this.cachedWorldShape = null;
        updateCachedWorldShape();
        this.shapeId = this.localShape.getId().get();
    }

    public void updateCachedWorldShape() {
        if (this.localShape != null) {
            if (this.cachedWorldShape == null) {
                this.cachedWorldShape = this.localShape.transform(this.transformProvider);
            } else {
                this.localShape.transform(this.transformProvider, this.cachedWorldShape);
            }
        }
    }
}
