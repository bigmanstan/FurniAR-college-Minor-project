package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.ar.core.Plane;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable.Builder;
import com.google.ar.sceneform.rendering.RenderableDefinition.Submesh;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

class PlaneVisualizer implements TransformProvider {
    private static final float FEATHER_LENGTH = 0.2f;
    private static final float FEATHER_SCALE = 0.2f;
    private static final String TAG = PlaneVisualizer.class.getSimpleName();
    private static final int VERTS_PER_BOUNDARY_VERT = 2;
    private boolean isEnabled = false;
    private boolean isPlaneAddedToScene = false;
    private boolean isShadowReceiver = false;
    private boolean isVisible = false;
    private final Plane plane;
    private final Matrix planeMatrix = new Matrix();
    @Nullable
    private ModelRenderable planeRenderable = null;
    @Nullable
    private RenderableInstance planeRenderableInstance;
    @Nullable
    private Submesh planeSubmesh;
    private final RenderableDefinition renderableDefinition;
    private final Renderer renderer;
    @Nullable
    private Submesh shadowSubmesh;
    private final ArrayList<Integer> triangleIndices = new ArrayList();
    private final ArrayList<Vertex> vertices = new ArrayList();

    public void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            updatePlane();
        }
    }

    public void setShadowReceiver(boolean shadowReceiver) {
        if (this.isShadowReceiver != shadowReceiver) {
            this.isShadowReceiver = shadowReceiver;
            updatePlane();
        }
    }

    public void setVisible(boolean visible) {
        if (this.isVisible != visible) {
            this.isVisible = visible;
            updatePlane();
        }
    }

    PlaneVisualizer(Plane plane, Renderer renderer) {
        this.plane = plane;
        this.renderer = renderer;
        this.renderableDefinition = RenderableDefinition.builder().setVertices(this.vertices).build();
    }

    Plane getPlane() {
        return this.plane;
    }

    void setShadowMaterial(Material material) {
        if (this.shadowSubmesh == null) {
            this.shadowSubmesh = Submesh.builder().setTriangleIndices(this.triangleIndices).setMaterial(material).build();
        } else {
            this.shadowSubmesh.setMaterial(material);
        }
        if (this.planeRenderable != null) {
            updateRenderable();
        }
    }

    void setPlaneMaterial(Material material) {
        if (this.planeSubmesh == null) {
            this.planeSubmesh = Submesh.builder().setTriangleIndices(this.triangleIndices).setMaterial(material).build();
        } else {
            this.planeSubmesh.setMaterial(material);
        }
        if (this.planeRenderable != null) {
            updateRenderable();
        }
    }

    public Matrix getWorldModelMatrix() {
        return this.planeMatrix;
    }

    void updatePlane() {
        if (this.isEnabled) {
            if (this.isVisible || this.isShadowReceiver) {
                if (this.plane.getTrackingState() != TrackingState.TRACKING) {
                    removePlaneFromScene();
                    return;
                }
                this.plane.getCenterPose().toMatrix(this.planeMatrix.data, 0);
                if (updateRenderableDefinitionForPlane()) {
                    updateRenderable();
                    addPlaneToScene();
                    return;
                }
                removePlaneFromScene();
                return;
            }
        }
        removePlaneFromScene();
    }

    void updateRenderable() {
        List<Submesh> submeshes = this.renderableDefinition.getSubmeshes();
        submeshes.clear();
        if (this.isVisible && this.planeSubmesh != null) {
            submeshes.add(this.planeSubmesh);
        }
        if (this.isShadowReceiver && this.shadowSubmesh != null) {
            submeshes.add(this.shadowSubmesh);
        }
        if (submeshes.isEmpty()) {
            removePlaneFromScene();
            return;
        }
        if (this.planeRenderable == null) {
            try {
                this.planeRenderable = (ModelRenderable) ((Builder) ModelRenderable.builder().setSource(this.renderableDefinition)).build().get();
                this.planeRenderable.setShadowCaster(false);
                this.planeRenderableInstance = this.planeRenderable.createInstance(this);
            } catch (InterruptedException e) {
                throw new AssertionError("Unable to create plane renderable.");
            }
        }
        this.planeRenderable.updateFromDefinition(this.renderableDefinition);
        if (this.planeRenderableInstance != null && submeshes.size() > 1) {
            this.planeRenderableInstance.setBlendOrderAt(0, 0);
            this.planeRenderableInstance.setBlendOrderAt(1, 1);
        }
    }

    void release() {
        removePlaneFromScene();
        this.planeRenderable = null;
    }

    private void addPlaneToScene() {
        if (!this.isPlaneAddedToScene) {
            if (this.planeRenderableInstance != null) {
                this.renderer.addInstance(this.planeRenderableInstance);
                this.isPlaneAddedToScene = true;
            }
        }
    }

    private void removePlaneFromScene() {
        if (this.isPlaneAddedToScene) {
            if (this.planeRenderableInstance != null) {
                this.renderer.removeInstance(this.planeRenderableInstance);
                this.isPlaneAddedToScene = false;
            }
        }
    }

    private boolean updateRenderableDefinitionForPlane() {
        FloatBuffer boundary = this.plane.getPolygon();
        if (boundary == null) {
            return false;
        }
        boundary.rewind();
        int boundaryVertices = boundary.limit() / 2;
        if (boundaryVertices == 0) {
            return false;
        }
        int i;
        int numVertices = boundaryVertices * 2;
        r0.vertices.clear();
        r0.vertices.ensureCapacity(numVertices);
        int numIndices = (boundaryVertices * 6) + ((boundaryVertices - 2) * 3);
        r0.triangleIndices.clear();
        r0.triangleIndices.ensureCapacity(numIndices);
        Vector3 normal = Vector3.up();
        while (true) {
            float f = 0.0f;
            if (!boundary.hasRemaining()) {
                break;
            }
            r0.vertices.add(Vertex.builder().setPosition(new Vector3(boundary.get(), 0.0f, boundary.get())).setNormal(normal).build());
        }
        boundary.rewind();
        while (boundary.hasRemaining()) {
            float x = boundary.get();
            float z = boundary.get();
            float magnitude = (float) Math.hypot((double) x, (double) z);
            float scale = 0.8f;
            if (magnitude != f) {
                scale = 1.0f - Math.min(0.2f / magnitude, 0.2f);
            }
            r0.vertices.add(Vertex.builder().setPosition(new Vector3(x * scale, 1.0f, z * scale)).setNormal(normal).build());
            f = 0.0f;
        }
        int firstInnerVertex = (short) boundaryVertices;
        for (i = 0; i < boundaryVertices - 2; i++) {
            r0.triangleIndices.add(Integer.valueOf(firstInnerVertex));
            r0.triangleIndices.add(Integer.valueOf((firstInnerVertex + i) + 1));
            r0.triangleIndices.add(Integer.valueOf((firstInnerVertex + i) + 2));
        }
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= boundaryVertices) {
                return true;
            }
            int outerVertex2 = ((i + 1) % boundaryVertices) + 0;
            int innerVertex1 = firstInnerVertex + i;
            int innerVertex2 = ((i + 1) % boundaryVertices) + firstInnerVertex;
            r0.triangleIndices.add(Integer.valueOf(0 + i));
            r0.triangleIndices.add(Integer.valueOf(outerVertex2));
            r0.triangleIndices.add(Integer.valueOf(innerVertex1));
            r0.triangleIndices.add(Integer.valueOf(innerVertex1));
            r0.triangleIndices.add(Integer.valueOf(outerVertex2));
            r0.triangleIndices.add(Integer.valueOf(innerVertex2));
            i2 = i + 1;
        }
    }
}
