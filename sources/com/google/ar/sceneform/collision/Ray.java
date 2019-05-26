package com.google.ar.sceneform.collision;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class Ray {
    private Vector3 direction = Vector3.forward();
    private Vector3 origin = new Vector3();

    public Ray(Vector3 vector3, Vector3 vector32) {
        Preconditions.checkNotNull(vector3, "Parameter \"origin\" was null.");
        Preconditions.checkNotNull(vector32, "Parameter \"direction\" was null.");
        setOrigin(vector3);
        setDirection(vector32);
    }

    public Vector3 getDirection() {
        return new Vector3(this.direction);
    }

    public Vector3 getOrigin() {
        return new Vector3(this.origin);
    }

    public Vector3 getPoint(float f) {
        return Vector3.add(this.origin, this.direction.scaled(f));
    }

    public void setDirection(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"direction\" was null.");
        this.direction.set(vector3.normalized());
    }

    public void setOrigin(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"origin\" was null.");
        this.origin.set(vector3);
    }

    public String toString() {
        String valueOf = String.valueOf(this.origin);
        String valueOf2 = String.valueOf(this.direction);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 21) + String.valueOf(valueOf2).length());
        stringBuilder.append("[Origin:");
        stringBuilder.append(valueOf);
        stringBuilder.append(", Direction:");
        stringBuilder.append(valueOf2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
