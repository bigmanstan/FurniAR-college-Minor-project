package com.google.ar.sceneform.resources;

public abstract class SharedReference {
    private int referenceCount = 0;

    private void dispose() {
        if (this.referenceCount <= 0) {
            onDispose();
        }
    }

    protected abstract void onDispose();

    public void release() {
        this.referenceCount--;
        dispose();
    }

    public void retain() {
        this.referenceCount++;
    }
}
