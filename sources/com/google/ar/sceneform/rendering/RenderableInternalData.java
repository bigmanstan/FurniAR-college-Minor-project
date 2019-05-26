package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.VertexBuffer;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

class RenderableInternalData {
    private static final String TAG = RenderableInternalData.class.getSimpleName();
    private final Vector3 centerAabb = Vector3.zero();
    private final Vector3 extentsAabb = Vector3.zero();
    @Nullable
    private IndexBuffer indexBuffer;
    private final ArrayList<MeshData> meshes = new ArrayList();
    @Nullable
    private FloatBuffer rawColorBuffer;
    @Nullable
    private IntBuffer rawIndexBuffer;
    @Nullable
    private FloatBuffer rawPositionBuffer;
    @Nullable
    private FloatBuffer rawTangentsBuffer;
    @Nullable
    private FloatBuffer rawUvBuffer;
    @Nullable
    private VertexBuffer vertexBuffer;

    static class MeshData {
        int indexEnd;
        int indexStart;

        MeshData() {
        }
    }

    RenderableInternalData() {
    }

    void setCenterAabb(Vector3 minAabb) {
        this.centerAabb.set(minAabb);
    }

    Vector3 getCenterAabb() {
        return new Vector3(this.centerAabb);
    }

    void setExtentsAabb(Vector3 maxAabb) {
        this.extentsAabb.set(maxAabb);
    }

    Vector3 getExtentsAabb() {
        return new Vector3(this.extentsAabb);
    }

    Vector3 getSizeAabb() {
        return this.extentsAabb.scaled(2.0f);
    }

    ArrayList<MeshData> getMeshes() {
        return this.meshes;
    }

    void setIndexBuffer(@Nullable IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    @Nullable
    IndexBuffer getIndexBuffer() {
        return this.indexBuffer;
    }

    void setVertexBuffer(@Nullable VertexBuffer vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    @Nullable
    VertexBuffer getVertexBuffer() {
        return this.vertexBuffer;
    }

    void setRawIndexBuffer(@Nullable IntBuffer rawIndexBuffer) {
        this.rawIndexBuffer = rawIndexBuffer;
    }

    @Nullable
    IntBuffer getRawIndexBuffer() {
        return this.rawIndexBuffer;
    }

    void setRawPositionBuffer(@Nullable FloatBuffer rawPositionBuffer) {
        this.rawPositionBuffer = rawPositionBuffer;
    }

    @Nullable
    FloatBuffer getRawPositionBuffer() {
        return this.rawPositionBuffer;
    }

    void setRawTangentsBuffer(@Nullable FloatBuffer rawTangentsBuffer) {
        this.rawTangentsBuffer = rawTangentsBuffer;
    }

    @Nullable
    FloatBuffer getRawTangentsBuffer() {
        return this.rawTangentsBuffer;
    }

    void setRawUvBuffer(@Nullable FloatBuffer rawUvBuffer) {
        this.rawUvBuffer = rawUvBuffer;
    }

    @Nullable
    FloatBuffer getRawUvBuffer() {
        return this.rawUvBuffer;
    }

    void setRawColorBuffer(@Nullable FloatBuffer rawColorBuffer) {
        this.rawColorBuffer = rawColorBuffer;
    }

    @Nullable
    FloatBuffer getRawColorBuffer() {
        return this.rawColorBuffer;
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$RenderableInternalData$7NZum-j5KvN6T3tZK-ZvNupPZ1Y());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Renderable Internal Data.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        if (engine != null) {
            if (engine.isValid()) {
                if (this.vertexBuffer != null) {
                    engine.destroyVertexBuffer(this.vertexBuffer);
                    this.vertexBuffer = null;
                }
                if (this.indexBuffer != null) {
                    engine.destroyIndexBuffer(this.indexBuffer);
                    this.indexBuffer = null;
                }
            }
        }
    }
}
