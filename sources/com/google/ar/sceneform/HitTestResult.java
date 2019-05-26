package com.google.ar.sceneform;

import com.google.ar.sceneform.collision.RayHit;

public class HitTestResult extends RayHit {
    private Node node;

    public Node getNode() {
        return this.node;
    }

    public void reset() {
        super.reset();
        this.node = null;
    }

    public void set(HitTestResult hitTestResult) {
        super.set(hitTestResult);
        setNode(hitTestResult.node);
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
