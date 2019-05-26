package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.ar.sceneform.math.Vector3;

public class Vertex {
    @Nullable
    private Color color;
    @Nullable
    private Vector3 normal;
    private final Vector3 position;
    @Nullable
    private UvCoordinate uvCoordinate;

    public static final class Builder {
        @Nullable
        private Color color;
        @Nullable
        private Vector3 normal;
        private final Vector3 position = Vector3.zero();
        @Nullable
        private UvCoordinate uvCoordinate;

        public Builder setPosition(Vector3 position) {
            this.position.set(position);
            return this;
        }

        public Builder setNormal(@Nullable Vector3 normal) {
            this.normal = normal;
            return this;
        }

        public Builder setUvCoordinate(@Nullable UvCoordinate uvCoordinate) {
            this.uvCoordinate = uvCoordinate;
            return this;
        }

        public Builder setColor(@Nullable Color color) {
            this.color = color;
            return this;
        }

        public Vertex build() {
            return new Vertex();
        }
    }

    public static final class UvCoordinate {
        /* renamed from: x */
        public float f130x;
        /* renamed from: y */
        public float f131y;

        public UvCoordinate(float x, float y) {
            this.f130x = x;
            this.f131y = y;
        }
    }

    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public Vector3 getPosition() {
        return this.position;
    }

    public void setNormal(@Nullable Vector3 normal) {
        this.normal = normal;
    }

    @Nullable
    public Vector3 getNormal() {
        return this.normal;
    }

    public void setUvCoordinate(@Nullable UvCoordinate uvCoordinate) {
        this.uvCoordinate = uvCoordinate;
    }

    @Nullable
    public UvCoordinate getUvCoordinate() {
        return this.uvCoordinate;
    }

    public void setColor(@Nullable Color color) {
        this.color = color;
    }

    @Nullable
    public Color getColor() {
        return this.color;
    }

    private Vertex(Builder builder) {
        this.position = Vector3.zero();
        this.position.set(builder.position);
        this.normal = builder.normal;
        this.uvCoordinate = builder.uvCoordinate;
        this.color = builder.color;
    }

    public static Builder builder() {
        return new Builder();
    }
}
