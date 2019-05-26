package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CollisionSystem {
    private static final String TAG = CollisionSystem.class.getSimpleName();
    private final ArrayList<Collider> colliders = new ArrayList();

    public void addCollider(Collider collider) {
        Preconditions.checkNotNull(collider, "Parameter \"collider\" was null.");
        this.colliders.add(collider);
    }

    public Collider intersects(Collider collider) {
        Preconditions.checkNotNull(collider, "Parameter \"collider\" was null.");
        CollisionShape transformedShape = collider.getTransformedShape();
        if (transformedShape == null) {
            return null;
        }
        ArrayList arrayList = this.colliders;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Collider collider2 = (Collider) obj;
            if (collider2 != collider) {
                CollisionShape transformedShape2 = collider2.getTransformedShape();
                if (transformedShape2 != null && transformedShape.shapeIntersection(transformedShape2)) {
                    return collider2;
                }
            }
        }
        return null;
    }

    public void intersectsAll(Collider collider, Consumer<Collider> consumer) {
        Preconditions.checkNotNull(collider, "Parameter \"collider\" was null.");
        Preconditions.checkNotNull(consumer, "Parameter \"processResult\" was null.");
        CollisionShape transformedShape = collider.getTransformedShape();
        if (transformedShape != null) {
            ArrayList arrayList = this.colliders;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Collider collider2 = (Collider) obj;
                if (collider2 != collider) {
                    CollisionShape transformedShape2 = collider2.getTransformedShape();
                    if (transformedShape2 != null && transformedShape.shapeIntersection(transformedShape2)) {
                        consumer.accept(collider2);
                    }
                }
            }
        }
    }

    public Collider raycast(Ray ray, RayHit rayHit) {
        Preconditions.checkNotNull(ray, "Parameter \"ray\" was null.");
        Preconditions.checkNotNull(rayHit, "Parameter \"resultHit\" was null.");
        rayHit.reset();
        RayHit rayHit2 = new RayHit();
        ArrayList arrayList = this.colliders;
        int size = arrayList.size();
        Collider collider = null;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Collider collider2 = (Collider) obj;
            CollisionShape transformedShape = collider2.getTransformedShape();
            if (transformedShape != null && transformedShape.rayIntersection(ray, rayHit2) && rayHit2.getDistance() < rayHit.getDistance()) {
                rayHit.set(rayHit2);
                collider = collider2;
            }
        }
        return collider;
    }

    public <T extends RayHit> int raycastAll(Ray ray, ArrayList<T> arrayList, BiConsumer<T, Collider> biConsumer, Supplier<T> supplier) {
        Preconditions.checkNotNull(ray, "Parameter \"ray\" was null.");
        Preconditions.checkNotNull(arrayList, "Parameter \"resultBuffer\" was null.");
        Preconditions.checkNotNull(supplier, "Parameter \"allocateResult\" was null.");
        RayHit rayHit = new RayHit();
        ArrayList arrayList2 = this.colliders;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            Collider collider = (Collider) obj;
            CollisionShape transformedShape = collider.getTransformedShape();
            if (transformedShape != null && transformedShape.rayIntersection(ray, rayHit)) {
                RayHit rayHit2;
                i2++;
                if (arrayList.size() >= i2) {
                    rayHit2 = (RayHit) arrayList.get(i2 - 1);
                } else {
                    rayHit2 = (RayHit) supplier.get();
                    arrayList.add(rayHit2);
                }
                rayHit2.reset();
                rayHit2.set(rayHit);
                if (biConsumer != null) {
                    biConsumer.accept(rayHit2, collider);
                }
            }
        }
        for (int i3 = i2; i3 < arrayList.size(); i3++) {
            ((RayHit) arrayList.get(i3)).reset();
        }
        Collections.sort(arrayList, C0392a.f109a);
        return i2;
    }

    public void removeCollider(Collider collider) {
        Preconditions.checkNotNull(collider, "Parameter \"collider\" was null.");
        this.colliders.remove(collider);
    }
}
