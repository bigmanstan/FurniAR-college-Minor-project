package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.util.Log;
import com.google.android.filament.Box;
import com.google.android.filament.Engine;
import com.google.android.filament.Entity;
import com.google.android.filament.EntityManager;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.RenderableManager;
import com.google.android.filament.RenderableManager.Builder;
import com.google.android.filament.RenderableManager.PrimitiveType;
import com.google.android.filament.TransformManager;
import com.google.android.filament.VertexBuffer;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.ChangeId;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;

public class RenderableInstance {
    private static final String TAG = RenderableInstance.class.getSimpleName();
    @Nullable
    private Renderer attachedRenderer;
    @Entity
    int entity;
    private final Renderable renderable;
    int renderableId = 0;
    private final TransformProvider transformProvider;

    public RenderableInstance(TransformProvider transformProvider, Renderable renderable) {
        Preconditions.checkNotNull(transformProvider, "Parameter \"transformProvider\" was null.");
        Preconditions.checkNotNull(renderable, "Parameter \"renderable\" was null.");
        this.transformProvider = transformProvider;
        this.renderable = renderable;
        this.entity = createFilamentEntity(EngineInstance.getEngine());
    }

    public Renderable getRenderable() {
        return this.renderable;
    }

    void setModelMatrix(TransformManager transformManager, @Size(min = 16) float[] transform) {
        transformManager.setTransform(transformManager.getInstance(this.entity), transform);
    }

    public Matrix getWorldModelMatrix() {
        return this.renderable.getFinalModelMatrix(this.transformProvider.getWorldModelMatrix());
    }

    public void prepareForDraw() {
        this.renderable.prepareForDraw();
        ChangeId changeId = this.renderable.getId();
        if (changeId.checkChanged(this.renderableId)) {
            buildInstanceData();
            this.renderableId = changeId.get();
        }
    }

    public void attachToRenderer(Renderer renderer) {
        renderer.addInstance(this);
        this.attachedRenderer = renderer;
        this.renderable.attachToRenderer(renderer);
    }

    public void detachFromRenderer() {
        if (this.attachedRenderer != null) {
            this.attachedRenderer.removeInstance(this);
            this.renderable.detatchFromRenderer();
        }
    }

    public void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        if (engine != null) {
            if (engine.isValid()) {
                engine.getRenderableManager().destroy(this.entity);
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$RenderableInstance$ztSgErjo0ZD8Ys_G0RHKIIqBqdY());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Renderable Instance.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    private void buildInstanceData() {
        int renderableInstance;
        Box box;
        PrimitiveType primitiveType;
        int mesh;
        MeshData meshData;
        VertexBuffer vertexBuffer;
        IndexBuffer indexBuffer;
        MeshData meshData2;
        int i;
        Box box2;
        Box filamentBox;
        RenderableInternalData renderableData = this.renderable.getRenderableData();
        ArrayList<Material> materialBindings = this.renderable.getMaterialBindings();
        RenderableManager renderableManager = EngineInstance.getEngine().getRenderableManager();
        int renderableInstance2 = renderableManager.getInstance(this.entity);
        int meshCount = renderableData.getMeshes().size();
        if (renderableInstance2 != 0) {
            if (renderableManager.getPrimitiveCount(renderableInstance2) == meshCount) {
                renderableManager.setPriority(renderableInstance2, r0.renderable.getRenderPriority());
                renderableManager.setCastShadows(renderableInstance2, r0.renderable.isShadowCaster());
                renderableManager.setReceiveShadows(renderableInstance2, r0.renderable.isShadowReceiver());
                renderableInstance = renderableInstance2;
                Vector3 extents = renderableData.getExtentsAabb();
                Vector3 center = renderableData.getCenterAabb();
                box = new Box(center.f120x, center.f121y, center.f122z, extents.f120x, extents.f121y, extents.f122z);
                renderableManager.setAxisAlignedBoundingBox(renderableInstance, box);
                if (materialBindings.size() != meshCount) {
                    primitiveType = PrimitiveType.TRIANGLES;
                    renderableInstance2 = 0;
                    while (true) {
                        mesh = renderableInstance2;
                        if (mesh < meshCount) {
                            meshData = (MeshData) renderableData.getMeshes().get(mesh);
                            vertexBuffer = renderableData.getVertexBuffer();
                            indexBuffer = renderableData.getIndexBuffer();
                            if (vertexBuffer == null || indexBuffer == null) {
                                meshData2 = meshData;
                                i = mesh;
                                box2 = filamentBox;
                            } else {
                                i = mesh;
                                mesh = meshData.indexStart;
                                box2 = filamentBox;
                                renderableManager.setGeometryAt(renderableInstance, mesh, primitiveType, vertexBuffer, indexBuffer, mesh, meshData.indexEnd - meshData.indexStart);
                                renderableManager.setMaterialInstanceAt(renderableInstance, i, ((Material) materialBindings.get(i)).getFilamentMaterialInstance());
                                renderableInstance2 = i + 1;
                                filamentBox = box2;
                                i = this;
                            }
                        } else {
                            return;
                        }
                    }
                    meshData2 = meshData;
                    i = mesh;
                    box2 = filamentBox;
                    throw new AssertionError("Internal Error: Failed to get vertex or index buffer");
                }
                box2 = box;
                throw new AssertionError("Material Bindings are out of sync with meshes.");
            }
        }
        if (renderableInstance2 != 0) {
            renderableManager.destroy(r0.entity);
        }
        new Builder(meshCount).priority(r0.renderable.getRenderPriority()).castShadows(r0.renderable.isShadowCaster()).receiveShadows(r0.renderable.isShadowReceiver()).build(EngineInstance.getEngine(), r0.entity);
        renderableInstance2 = renderableManager.getInstance(r0.entity);
        if (renderableInstance2 != 0) {
            renderableInstance = renderableInstance2;
            Vector3 extents2 = renderableData.getExtentsAabb();
            Vector3 center2 = renderableData.getCenterAabb();
            box = new Box(center2.f120x, center2.f121y, center2.f122z, extents2.f120x, extents2.f121y, extents2.f122z);
            renderableManager.setAxisAlignedBoundingBox(renderableInstance, box);
            if (materialBindings.size() != meshCount) {
                box2 = box;
                throw new AssertionError("Material Bindings are out of sync with meshes.");
            }
            primitiveType = PrimitiveType.TRIANGLES;
            renderableInstance2 = 0;
            while (true) {
                mesh = renderableInstance2;
                if (mesh < meshCount) {
                    meshData = (MeshData) renderableData.getMeshes().get(mesh);
                    vertexBuffer = renderableData.getVertexBuffer();
                    indexBuffer = renderableData.getIndexBuffer();
                    if (vertexBuffer == null) {
                        break;
                    }
                    break;
                }
                return;
                i = mesh;
                mesh = meshData.indexStart;
                box2 = filamentBox;
                renderableManager.setGeometryAt(renderableInstance, mesh, primitiveType, vertexBuffer, indexBuffer, mesh, meshData.indexEnd - meshData.indexStart);
                renderableManager.setMaterialInstanceAt(renderableInstance, i, ((Material) materialBindings.get(i)).getFilamentMaterialInstance());
                renderableInstance2 = i + 1;
                filamentBox = box2;
                i = this;
            }
            meshData2 = meshData;
            i = mesh;
            box2 = filamentBox;
            throw new AssertionError("Internal Error: Failed to get vertex or index buffer");
        }
        throw new AssertionError("Unable to create RenderableInstance.");
    }

    void setBlendOrderAt(int index, int blendOrder) {
        RenderableManager renderableManager = EngineInstance.getEngine().getRenderableManager();
        renderableManager.setBlendOrderAt(renderableManager.getInstance(this.entity), index, blendOrder);
    }

    @Entity
    private static int createFilamentEntity(Engine engine) {
        int entity = EntityManager.get().create();
        engine.getTransformManager().create(entity);
        return entity;
    }
}
