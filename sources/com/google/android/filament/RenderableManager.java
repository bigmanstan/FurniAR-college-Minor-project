package com.google.android.filament;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.filament.VertexBuffer.VertexAttribute;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class RenderableManager {
    private static final String LOG_TAG = "Filament";
    private long mNativeObject;

    public static class Builder {
        private final BuilderFinalizer mFinalizer = new BuilderFinalizer(this.mNativeBuilder);
        private final long mNativeBuilder;

        private static class BuilderFinalizer {
            private final long mNativeObject;

            BuilderFinalizer(long nativeObject) {
                this.mNativeObject = nativeObject;
            }

            public void finalize() {
                try {
                    super.finalize();
                } catch (Throwable th) {
                    RenderableManager.nDestroyBuilder(this.mNativeObject);
                }
                RenderableManager.nDestroyBuilder(this.mNativeObject);
            }
        }

        public Builder(@IntRange(from = 1) int count) {
            this.mNativeBuilder = RenderableManager.nCreateBuilder(count);
        }

        @NonNull
        public Builder geometry(@IntRange(from = 0) int index, @NonNull PrimitiveType type, @NonNull VertexBuffer vertices, @NonNull IndexBuffer indices) {
            RenderableManager.nBuilderGeometry(this.mNativeBuilder, index, type.getValue(), vertices.getNativeObject(), indices.getNativeObject());
            return this;
        }

        @NonNull
        public Builder geometry(@IntRange(from = 0) int index, @NonNull PrimitiveType type, @NonNull VertexBuffer vertices, @NonNull IndexBuffer indices, @IntRange(from = 0) int offset, @IntRange(from = 0) int count) {
            RenderableManager.nBuilderGeometry(this.mNativeBuilder, index, type.getValue(), vertices.getNativeObject(), indices.getNativeObject(), offset, count);
            return r0;
        }

        @NonNull
        public Builder geometry(@IntRange(from = 0) int index, @NonNull PrimitiveType type, @NonNull VertexBuffer vertices, @NonNull IndexBuffer indices, @IntRange(from = 0) int offset, @IntRange(from = 0) int minIndex, @IntRange(from = 0) int maxIndex, @IntRange(from = 0) int count) {
            RenderableManager.nBuilderGeometry(this.mNativeBuilder, index, type.getValue(), vertices.getNativeObject(), indices.getNativeObject(), offset, minIndex, maxIndex, count);
            return r0;
        }

        @NonNull
        public Builder material(@IntRange(from = 0) int index, @NonNull MaterialInstance material) {
            RenderableManager.nBuilderMaterial(this.mNativeBuilder, index, material.getNativeObject());
            return this;
        }

        @NonNull
        public Builder blendOrder(@IntRange(from = 0) int index, @IntRange(from = 0, to = 32767) int blendOrder) {
            RenderableManager.nBuilderBlendOrder(this.mNativeBuilder, index, blendOrder);
            return this;
        }

        @NonNull
        public Builder boundingBox(@NonNull Box aabb) {
            RenderableManager.nBuilderBoundingBox(this.mNativeBuilder, aabb.getCenter()[0], aabb.getCenter()[1], aabb.getCenter()[2], aabb.getHalfExtent()[0], aabb.getHalfExtent()[1], aabb.getHalfExtent()[2]);
            return this;
        }

        @NonNull
        public Builder layerMask(@IntRange(from = 0, to = 255) int select, @IntRange(from = 0, to = 255) int value) {
            RenderableManager.nBuilderLayerMask(this.mNativeBuilder, select & 255, value & 255);
            return this;
        }

        @NonNull
        public Builder priority(@IntRange(from = 0, to = 7) int priority) {
            RenderableManager.nBuilderPriority(this.mNativeBuilder, priority);
            return this;
        }

        @NonNull
        public Builder culling(boolean enabled) {
            RenderableManager.nBuilderCulling(this.mNativeBuilder, enabled);
            return this;
        }

        @NonNull
        public Builder castShadows(boolean enabled) {
            RenderableManager.nBuilderCastShadows(this.mNativeBuilder, enabled);
            return this;
        }

        @NonNull
        public Builder receiveShadows(boolean enabled) {
            RenderableManager.nBuilderReceiveShadows(this.mNativeBuilder, enabled);
            return this;
        }

        @NonNull
        public Builder skinning(@IntRange(from = 0, to = 255) int boneCount) {
            RenderableManager.nBuilderSkinning(this.mNativeBuilder, boneCount);
            return this;
        }

        @NonNull
        public Builder skinning(@IntRange(from = 0, to = 255) int boneCount, @NonNull Buffer bones) {
            if (RenderableManager.nBuilderSkinningBones(this.mNativeBuilder, boneCount, bones, bones.remaining()) >= 0) {
                return this;
            }
            throw new BufferOverflowException();
        }

        public void build(@NonNull Engine engine, @Entity int entity) {
            if (!RenderableManager.nBuilderBuild(this.mNativeBuilder, engine.getNativeObject(), entity)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't create Renderable component for entity ");
                stringBuilder.append(entity);
                stringBuilder.append(", see log.");
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    public enum PrimitiveType {
        POINTS(0),
        LINES(1),
        TRIANGLES(4);
        
        private final int mType;

        private PrimitiveType(int value) {
            this.mType = value;
        }

        int getValue() {
            return this.mType;
        }
    }

    private static native void nBuilderBlendOrder(long j, int i, int i2);

    private static native void nBuilderBoundingBox(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native boolean nBuilderBuild(long j, long j2, int i);

    private static native void nBuilderCastShadows(long j, boolean z);

    private static native void nBuilderCulling(long j, boolean z);

    private static native void nBuilderGeometry(long j, int i, int i2, long j2, long j3);

    private static native void nBuilderGeometry(long j, int i, int i2, long j2, long j3, int i3, int i4);

    private static native void nBuilderGeometry(long j, int i, int i2, long j2, long j3, int i3, int i4, int i5, int i6);

    private static native void nBuilderLayerMask(long j, int i, int i2);

    private static native void nBuilderMaterial(long j, int i, long j2);

    private static native void nBuilderPriority(long j, int i);

    private static native void nBuilderReceiveShadows(long j, boolean z);

    private static native void nBuilderSkinning(long j, int i);

    private static native int nBuilderSkinningBones(long j, int i, Buffer buffer, int i2);

    private static native long nCreateBuilder(int i);

    private static native void nDestroy(long j, int i);

    private static native void nDestroyBuilder(long j);

    private static native void nGetAxisAlignedBoundingBox(long j, int i, float[] fArr, float[] fArr2);

    private static native int nGetEnabledAttributesAt(long j, int i, int i2);

    private static native int nGetInstance(long j, int i);

    private static native int nGetPrimitiveCount(long j, int i);

    private static native boolean nHasComponent(long j, int i);

    private static native boolean nIsShadowCaster(long j, int i);

    private static native boolean nIsShadowReceiver(long j, int i);

    private static native void nSetAxisAlignedBoundingBox(long j, int i, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nSetBlendOrderAt(long j, int i, int i2, int i3);

    private static native int nSetBonesAsMatrices(long j, int i, Buffer buffer, int i2, int i3, int i4);

    private static native int nSetBonesAsQuaternions(long j, int i, Buffer buffer, int i2, int i3, int i4);

    private static native void nSetCastShadows(long j, int i, boolean z);

    private static native void nSetGeometryAt(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nSetGeometryAt(long j, int i, int i2, int i3, long j2, long j3, int i4, int i5);

    private static native void nSetLayerMask(long j, int i, int i2, int i3);

    private static native void nSetMaterialInstanceAt(long j, int i, int i2, long j2);

    private static native void nSetPriority(long j, int i, int i2);

    private static native void nSetReceiveShadows(long j, int i, boolean z);

    RenderableManager(long nativeRenderableManager) {
        this.mNativeObject = nativeRenderableManager;
    }

    public boolean hasComponent(@Entity int entity) {
        return nHasComponent(this.mNativeObject, entity);
    }

    @EntityInstance
    public int getInstance(@Entity int entity) {
        return nGetInstance(this.mNativeObject, entity);
    }

    public void destroy(@Entity int entity) {
        nDestroy(this.mNativeObject, entity);
    }

    public void setBonesAsMatrices(@EntityInstance int i, @NonNull Buffer matrices, @IntRange(from = 0, to = 255) int boneCount, @IntRange(from = 0) int offset) {
        if (nSetBonesAsMatrices(this.mNativeObject, i, matrices, matrices.remaining(), boneCount, offset) < 0) {
            throw new BufferOverflowException();
        }
    }

    public void setBonesAsQuaternions(@EntityInstance int i, @NonNull Buffer quaternions, @IntRange(from = 0, to = 255) int boneCount, @IntRange(from = 0) int offset) {
        if (nSetBonesAsQuaternions(this.mNativeObject, i, quaternions, quaternions.remaining(), boneCount, offset) < 0) {
            throw new BufferOverflowException();
        }
    }

    public void setAxisAlignedBoundingBox(@EntityInstance int i, @NonNull Box aabb) {
        nSetAxisAlignedBoundingBox(this.mNativeObject, i, aabb.getCenter()[0], aabb.getCenter()[1], aabb.getCenter()[2], aabb.getHalfExtent()[0], aabb.getHalfExtent()[1], aabb.getHalfExtent()[2]);
    }

    public void setLayerMask(@EntityInstance int i, @IntRange(from = 0, to = 255) int select, @IntRange(from = 0, to = 255) int value) {
        nSetLayerMask(this.mNativeObject, i, select, value);
    }

    public void setPriority(@EntityInstance int i, @IntRange(from = 0, to = 7) int priority) {
        nSetPriority(this.mNativeObject, i, priority);
    }

    public void setCastShadows(@EntityInstance int i, boolean enabled) {
        nSetCastShadows(this.mNativeObject, i, enabled);
    }

    public void setReceiveShadows(@EntityInstance int i, boolean enabled) {
        nSetReceiveShadows(this.mNativeObject, i, enabled);
    }

    public boolean isShadowCaster(@EntityInstance int i) {
        return nIsShadowCaster(this.mNativeObject, i);
    }

    public boolean isShadowReceiver(@EntityInstance int i) {
        return nIsShadowReceiver(this.mNativeObject, i);
    }

    @NonNull
    public Box getAxisAlignedBoundingBox(@EntityInstance int i, @Nullable Box out) {
        if (out == null) {
            out = new Box();
        }
        nGetAxisAlignedBoundingBox(this.mNativeObject, i, out.getCenter(), out.getHalfExtent());
        return out;
    }

    @IntRange(from = 0)
    public int getPrimitiveCount(@EntityInstance int i) {
        return nGetPrimitiveCount(this.mNativeObject, i);
    }

    public void setMaterialInstanceAt(@EntityInstance int i, @IntRange(from = 0) int primitiveIndex, @NonNull MaterialInstance materialInstance) {
        int required = materialInstance.getMaterial().getRequiredAttributesAsInt();
        if ((nGetEnabledAttributesAt(this.mNativeObject, i, primitiveIndex) & required) != required) {
            Platform platform = Platform.get();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setMaterialInstanceAt() on primitive ");
            stringBuilder.append(primitiveIndex);
            stringBuilder.append(" of Renderable at ");
            stringBuilder.append(i);
            stringBuilder.append(": declared attributes ");
            stringBuilder.append(getEnabledAttributesAt(i, primitiveIndex));
            stringBuilder.append(" do no satisfy required attributes ");
            stringBuilder.append(materialInstance.getMaterial().getRequiredAttributes());
            platform.warn(stringBuilder.toString());
        }
        nSetMaterialInstanceAt(this.mNativeObject, i, primitiveIndex, materialInstance.getNativeObject());
    }

    public void setGeometryAt(@EntityInstance int i, @IntRange(from = 0) int primitiveIndex, @NonNull PrimitiveType type, @NonNull VertexBuffer vertices, @NonNull IndexBuffer indices, @IntRange(from = 0) int offset, @IntRange(from = 0) int count) {
        nSetGeometryAt(this.mNativeObject, i, primitiveIndex, type.getValue(), vertices.getNativeObject(), indices.getNativeObject(), offset, count);
    }

    public void setGeometryAt(@EntityInstance int i, @IntRange(from = 0) int primitiveIndex, @NonNull PrimitiveType type, @NonNull VertexBuffer vertices, @NonNull IndexBuffer indices) {
        nSetGeometryAt(this.mNativeObject, i, primitiveIndex, type.getValue(), vertices.getNativeObject(), indices.getNativeObject(), 0, indices.getIndexCount());
    }

    public void setGeometryAt(@EntityInstance int i, @IntRange(from = 0) int primitiveIndex, @NonNull PrimitiveType type, @IntRange(from = 0) int offset, @IntRange(from = 0) int count) {
        nSetGeometryAt(this.mNativeObject, i, primitiveIndex, type.getValue(), offset, count);
    }

    public void setBlendOrderAt(@EntityInstance int i, @IntRange(from = 0) int primitiveIndex, @IntRange(from = 0, to = 65535) int blendOrder) {
        nSetBlendOrderAt(this.mNativeObject, i, primitiveIndex, blendOrder);
    }

    public Set<VertexAttribute> getEnabledAttributesAt(@EntityInstance int i, @IntRange(from = 0) int primitiveIndex) {
        int bitSet = nGetEnabledAttributesAt(this.mNativeObject, i, primitiveIndex);
        Set<VertexAttribute> requiredAttributes = EnumSet.noneOf(VertexAttribute.class);
        VertexAttribute[] values = VertexAttribute.values();
        for (int j = 0; j < values.length; j++) {
            if (((1 << j) & bitSet) != 0) {
                requiredAttributes.add(values[j]);
            }
        }
        return Collections.unmodifiableSet(requiredAttributes);
    }
}
