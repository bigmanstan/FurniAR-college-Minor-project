package com.google.ar.sceneform.rendering;

import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Texture.Sampler;
import com.google.ar.sceneform.rendering.Texture.Sampler.MagFilter;
import com.google.ar.sceneform.rendering.Texture.Sampler.WrapMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public class PlaneRenderer {
    private static final float BASE_UV_SCALE = 8.0f;
    private static final float DEFAULT_TEXTURE_HEIGHT = 513.0f;
    private static final float DEFAULT_TEXTURE_WIDTH = 293.0f;
    public static final String MATERIAL_COLOR = "color";
    private static final String MATERIAL_SPOTLIGHT_FOCUS_POINT = "focusPoint";
    public static final String MATERIAL_SPOTLIGHT_RADIUS = "radius";
    public static final String MATERIAL_TEXTURE = "texture";
    public static final String MATERIAL_UV_SCALE = "uvScale";
    private static final float SPOTLIGHT_RADIUS = 0.5f;
    private static final String TAG = PlaneRenderer.class.getSimpleName();
    private boolean isEnabled = true;
    private boolean isShadowReceiver = true;
    private boolean isVisible = true;
    private float lastPlaneHitDistance = 4.0f;
    private CompletableFuture<Material> planeMaterialFuture;
    private final Renderer renderer;
    private Material shadowMaterial;
    private final Map<Plane, PlaneVisualizer> visualizerMap = new HashMap();

    public void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            for (PlaneVisualizer visualizer : this.visualizerMap.values()) {
                visualizer.setEnabled(this.isEnabled);
            }
        }
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setShadowReceiver(boolean shadowReceiver) {
        if (this.isShadowReceiver != shadowReceiver) {
            this.isShadowReceiver = shadowReceiver;
            for (PlaneVisualizer visualizer : this.visualizerMap.values()) {
                visualizer.setShadowReceiver(this.isShadowReceiver);
            }
        }
    }

    public boolean isShadowReceiver() {
        return this.isShadowReceiver;
    }

    public void setVisible(boolean visible) {
        if (this.isVisible != visible) {
            this.isVisible = visible;
            for (PlaneVisualizer visualizer : this.visualizerMap.values()) {
                visualizer.setVisible(this.isVisible);
            }
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public CompletableFuture<Material> getMaterial() {
        return this.planeMaterialFuture;
    }

    public PlaneRenderer(Renderer renderer) {
        this.renderer = renderer;
        loadPlaneMaterial();
        loadShadowMaterial();
    }

    public void update(Frame frame, int viewWidth, int viewHeight) {
        Collection<Plane> updatedPlanes = frame.getUpdatedTrackables(Plane.class);
        Vector3 focusPoint = getFocusPoint(frame, viewWidth, viewHeight);
        Material planeMaterial = (Material) this.planeMaterialFuture.getNow(null);
        if (planeMaterial != null) {
            planeMaterial.setFloat3(MATERIAL_SPOTLIGHT_FOCUS_POINT, focusPoint);
            planeMaterial.setFloat(MATERIAL_SPOTLIGHT_RADIUS, SPOTLIGHT_RADIUS);
        }
        for (Plane plane : updatedPlanes) {
            PlaneVisualizer planeVisualizer;
            if (this.visualizerMap.containsKey(plane)) {
                planeVisualizer = (PlaneVisualizer) this.visualizerMap.get(plane);
            } else {
                planeVisualizer = new PlaneVisualizer(plane, this.renderer);
                if (planeMaterial != null) {
                    planeVisualizer.setPlaneMaterial(planeMaterial);
                }
                if (this.shadowMaterial != null) {
                    planeVisualizer.setShadowMaterial(this.shadowMaterial);
                }
                planeVisualizer.setShadowReceiver(this.isShadowReceiver);
                planeVisualizer.setVisible(this.isVisible);
                planeVisualizer.setEnabled(this.isEnabled);
                this.visualizerMap.put(plane, planeVisualizer);
            }
            planeVisualizer.updatePlane();
        }
        Iterator<Entry<Plane, PlaneVisualizer>> iter = this.visualizerMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Plane, PlaneVisualizer> entry = (Entry) iter.next();
            Plane plane2 = (Plane) entry.getKey();
            PlaneVisualizer planeVisualizer2 = (PlaneVisualizer) entry.getValue();
            if (plane2.getSubsumedBy() == null) {
                if (plane2.getTrackingState() == TrackingState.STOPPED) {
                }
            }
            planeVisualizer2.release();
            iter.remove();
        }
    }

    private void loadShadowMaterial() {
        Material.builder().setSource(this.renderer.getContext(), C0023R.raw.sceneform_plane_shadow_material).build().thenAccept(new -$$Lambda$PlaneRenderer$hmQrwToB7YwdF-q1njkq_x48cMM()).exceptionally(-$$Lambda$PlaneRenderer$BJkx3ea8pCqgTOCdKZ3jxSlCrLw.INSTANCE);
    }

    public static /* synthetic */ void lambda$loadShadowMaterial$0(PlaneRenderer planeRenderer, Material material) {
        planeRenderer.shadowMaterial = material;
        for (PlaneVisualizer visualizer : planeRenderer.visualizerMap.values()) {
            visualizer.setShadowMaterial(planeRenderer.shadowMaterial);
        }
    }

    private void loadPlaneMaterial() {
        this.planeMaterialFuture = Material.builder().setSource(this.renderer.getContext(), C0023R.raw.sceneform_plane_material).build().thenCombine(Texture.builder().setSource(this.renderer.getContext(), C0023R.drawable.sceneform_plane).setSampler(Sampler.builder().setMinMagFilter(MagFilter.LINEAR).setWrapMode(WrapMode.REPEAT).build()).build(), new -$$Lambda$PlaneRenderer$0-wguS_hFsU2yDEaA2R4qSJuqEs());
    }

    public static /* synthetic */ Material lambda$loadPlaneMaterial$2(PlaneRenderer planeRenderer, Material material, Texture texture) {
        material.setTexture("texture", texture);
        material.setFloat3("color", 1.0f, 1.0f, 1.0f);
        material.setFloat2(MATERIAL_UV_SCALE, BASE_UV_SCALE, BASE_UV_SCALE * 0.5711501f);
        for (PlaneVisualizer visualizer : planeRenderer.visualizerMap.values()) {
            visualizer.setPlaneMaterial(material);
        }
        return material;
    }

    private Vector3 getFocusPoint(Frame frame, int width, int height) {
        List<HitResult> hits = frame.hitTest((float) (width / 2), (float) (height / 2));
        if (!(hits == null || hits.isEmpty())) {
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                Pose hitPose = hit.getHitPose();
                if ((trackable instanceof Plane) && ((Plane) trackable).isPoseInPolygon(hitPose)) {
                    Vector3 focusPoint = new Vector3(hitPose.tx(), hitPose.ty(), hitPose.tz());
                    this.lastPlaneHitDistance = hit.getDistance();
                    return focusPoint;
                }
            }
        }
        Pose cameraPose = frame.getCamera().getPose();
        Vector3 cameraPosition = new Vector3(cameraPose.tx(), cameraPose.ty(), cameraPose.tz());
        float[] zAxis = cameraPose.getZAxis();
        return Vector3.add(cameraPosition, new Vector3(zAxis[0], zAxis[1], zAxis[2]).scaled(-this.lastPlaneHitDistance));
    }
}
