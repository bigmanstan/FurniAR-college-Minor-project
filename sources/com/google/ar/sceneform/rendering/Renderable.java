package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.collision.CollisionShape;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.resources.ResourceRegistry;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.ChangeId;
import com.google.ar.sceneform.utilities.LoadHelper;
import com.google.ar.sceneform.utilities.Preconditions;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public abstract class Renderable {
    private static final int RENDER_PRIORITY_DEFAULT = 4;
    private static final int RENDER_PRIORITY_MAX = 7;
    private static final int RENDER_PRIORITY_MIN = 0;
    private final ChangeId changeId = new ChangeId();
    @Nullable
    protected CollisionShape collisionShape;
    private boolean isShadowCaster = true;
    private boolean isShadowReceiver = true;
    private final ArrayList<Material> materialBindings = new ArrayList();
    private int renderPriority = 4;
    private final RenderableInternalData renderableData;

    static abstract class Builder<T extends Renderable, B extends Builder<T, B>> {
        @Nullable
        protected Context context = null;
        @Nullable
        private RenderableDefinition definition = null;
        @Nullable
        private Callable<InputStream> inputStreamCreator = null;
        @Nullable
        protected Object registryId = null;
        @Nullable
        private Uri sourceUri = null;

        protected abstract Class<T> getRenderableClass();

        protected abstract ResourceRegistry<T> getRenderableRegistry();

        protected abstract B getSelf();

        protected abstract T makeRenderable();

        protected Builder() {
        }

        public B setSource(Context context, Callable<InputStream> inputStreamCreator) {
            Preconditions.checkNotNull(inputStreamCreator);
            this.sourceUri = null;
            this.inputStreamCreator = inputStreamCreator;
            this.context = context;
            return getSelf();
        }

        public B setSource(Context context, Uri sourceUri) {
            Preconditions.checkNotNull(sourceUri);
            this.sourceUri = sourceUri;
            this.context = context;
            this.inputStreamCreator = null;
            this.registryId = sourceUri;
            return getSelf();
        }

        public B setSource(Context context, int resourceId) {
            setSource(context, LoadHelper.resourceToUri(context, resourceId));
            return getSelf();
        }

        public B setSource(RenderableDefinition definition) {
            this.definition = definition;
            this.registryId = null;
            this.sourceUri = null;
            return getSelf();
        }

        public B setRegistryId(@Nullable Object registryId) {
            this.registryId = registryId;
            return getSelf();
        }

        public Boolean hasSource() {
            boolean z;
            if (this.sourceUri == null && this.inputStreamCreator == null) {
                if (this.definition == null) {
                    z = false;
                    return Boolean.valueOf(z);
                }
            }
            z = true;
            return Boolean.valueOf(z);
        }

        public CompletableFuture<T> build() {
            try {
                checkPreconditions();
                Object registryId = this.registryId;
                if (registryId != null) {
                    CompletableFuture<T> renderableFuture = getRenderableRegistry().get(registryId);
                    if (renderableFuture != null) {
                        return renderableFuture.thenApply(new -$$Lambda$Renderable$Builder$yKLHg_bGVAdReR6224-4zAs04-c());
                    }
                }
                T renderable = makeRenderable();
                if (this.definition != null) {
                    return CompletableFuture.completedFuture(renderable);
                }
                Uri sourceUri = this.sourceUri;
                Callable<InputStream> inputStreamCreator = this.inputStreamCreator;
                Context context = this.context;
                if (context != null) {
                    if (sourceUri != null) {
                        inputStreamCreator = LoadHelper.fromUri(context, sourceUri);
                    }
                    String valueOf;
                    if (inputStreamCreator == null) {
                        CompletableFuture<T> result = new CompletableFuture();
                        result.completeExceptionally(new AssertionError("Input Stream Creator is null."));
                        String simpleName = getRenderableClass().getSimpleName();
                        valueOf = String.valueOf(registryId);
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 39);
                        stringBuilder.append("Unable to load Renderable registryId='");
                        stringBuilder.append(valueOf);
                        stringBuilder.append("'");
                        FutureHelper.logOnException(simpleName, result, stringBuilder.toString());
                        return result;
                    }
                    CompletableFuture<T> result2 = new LoadRenderableFromRcbTask(context, renderable, sourceUri).downloadAndProcessRenderable(inputStreamCreator);
                    if (registryId != null) {
                        getRenderableRegistry().register(registryId, result2);
                    }
                    valueOf = getRenderableClass().getSimpleName();
                    String valueOf2 = String.valueOf(registryId);
                    StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf2).length() + 39);
                    stringBuilder2.append("Unable to load Renderable registryId='");
                    stringBuilder2.append(valueOf2);
                    stringBuilder2.append("'");
                    FutureHelper.logOnException(valueOf, result2, stringBuilder2.toString());
                    return result2.thenApply(new -$$Lambda$Renderable$Builder$Y96LkBnazwfNXU6Pxfl1RHbjLMA());
                }
                throw new IllegalStateException("Context is required to create renderable.");
            } catch (Throwable failedPrecondition) {
                CompletableFuture<T> result3 = new CompletableFuture();
                result3.completeExceptionally(failedPrecondition);
                String simpleName2 = getRenderableClass().getSimpleName();
                String valueOf3 = String.valueOf(this.registryId);
                StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(valueOf3).length() + 39);
                stringBuilder3.append("Unable to load Renderable registryId='");
                stringBuilder3.append(valueOf3);
                stringBuilder3.append("'");
                FutureHelper.logOnException(simpleName2, result3, stringBuilder3.toString());
                return result3;
            }
        }

        protected void checkPreconditions() {
            AndroidPreconditions.checkUiThread();
            if (!hasSource().booleanValue()) {
                throw new AssertionError("ModelRenderable must have a source.");
            }
        }
    }

    public abstract Renderable makeCopy();

    protected Renderable(Builder<? extends Renderable, ? extends Builder<?, ?>> builder) {
        Preconditions.checkNotNull(builder, "Parameter \"builder\" was null.");
        this.renderableData = new RenderableInternalData();
        if (builder.definition != null) {
            updateFromDefinition(builder.definition);
        }
    }

    protected Renderable(Renderable other) {
        if (other.getId().isEmpty()) {
            throw new AssertionError("Cannot copy uninitialized Renderable.");
        }
        this.renderableData = other.renderableData;
        for (int i = 0; i < other.materialBindings.size(); i++) {
            this.materialBindings.add(((Material) other.materialBindings.get(i)).makeCopy());
        }
        this.renderPriority = other.renderPriority;
        this.isShadowCaster = other.isShadowCaster;
        this.isShadowReceiver = other.isShadowReceiver;
        this.collisionShape = other.collisionShape.makeCopy();
        this.changeId.update();
    }

    @Nullable
    public CollisionShape getCollisionShape() {
        return this.collisionShape;
    }

    public void setCollisionShape(@Nullable CollisionShape collisionShape) {
        this.collisionShape = collisionShape;
        this.changeId.update();
    }

    public Material getMaterial() {
        return getMaterial(0);
    }

    public Material getMaterial(int submeshIndex) {
        if (submeshIndex < this.materialBindings.size()) {
            return (Material) this.materialBindings.get(submeshIndex);
        }
        throw makeSubmeshOutOfRangeException(submeshIndex);
    }

    public void setMaterial(Material material) {
        setMaterial(0, material);
    }

    public void setMaterial(int submeshIndex, Material material) {
        if (submeshIndex < this.materialBindings.size()) {
            this.materialBindings.set(submeshIndex, material);
            this.changeId.update();
            return;
        }
        throw makeSubmeshOutOfRangeException(submeshIndex);
    }

    public int getRenderPriority() {
        return this.renderPriority;
    }

    public void setRenderPriority(@IntRange(from = 0, to = 7) int renderPriority) {
        this.renderPriority = Math.min(7, Math.max(0, renderPriority));
        this.changeId.update();
    }

    public boolean isShadowCaster() {
        return this.isShadowCaster;
    }

    public void setShadowCaster(boolean isShadowCaster) {
        this.isShadowCaster = isShadowCaster;
        this.changeId.update();
    }

    public boolean isShadowReceiver() {
        return this.isShadowReceiver;
    }

    public void setShadowReceiver(boolean isShadowReceiver) {
        this.isShadowReceiver = isShadowReceiver;
        this.changeId.update();
    }

    public int getSubmeshCount() {
        return this.renderableData.getMeshes().size();
    }

    public ChangeId getId() {
        return this.changeId;
    }

    public RenderableInstance createInstance(TransformProvider transformProvider) {
        return new RenderableInstance(transformProvider, this);
    }

    public void updateFromDefinition(RenderableDefinition definition) {
        Preconditions.checkState(definition.getSubmeshes().isEmpty() ^ 1);
        this.changeId.update();
        definition.applyDefinitionToData(this.renderableData, this.materialBindings);
        this.collisionShape = new Box(this.renderableData.getSizeAabb(), this.renderableData.getCenterAabb());
    }

    RenderableInternalData getRenderableData() {
        return this.renderableData;
    }

    ArrayList<Material> getMaterialBindings() {
        return this.materialBindings;
    }

    void prepareForDraw() {
        for (int i = 0; i < this.materialBindings.size(); i++) {
            ((Material) this.materialBindings.get(i)).prepareToDraw();
        }
    }

    void attachToRenderer(Renderer renderer) {
    }

    void detatchFromRenderer() {
    }

    public Matrix getFinalModelMatrix(Matrix originalMatrix) {
        Preconditions.checkNotNull(originalMatrix, "Parameter \"originalMatrix\" was null.");
        return originalMatrix;
    }

    private IllegalArgumentException makeSubmeshOutOfRangeException(int submeshIndex) {
        int submeshCount = getSubmeshCount();
        StringBuilder stringBuilder = new StringBuilder(96);
        stringBuilder.append("submeshIndex (");
        stringBuilder.append(submeshIndex);
        stringBuilder.append(") is out of range. It must be less than the submeshCount (");
        stringBuilder.append(submeshCount);
        stringBuilder.append(").");
        return new IllegalArgumentException(stringBuilder.toString());
    }
}
