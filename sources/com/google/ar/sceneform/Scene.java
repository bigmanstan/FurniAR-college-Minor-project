package com.google.ar.sceneform;

import android.view.MotionEvent;
import com.google.ar.sceneform.collision.Collider;
import com.google.ar.sceneform.collision.CollisionSystem;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.collision.RayHit;
import com.google.ar.sceneform.rendering.LightProbe;
import com.google.ar.sceneform.rendering.Renderer;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;

public class Scene extends NodeParent {
    private static final String DEFAULT_LIGHTPROBE_ASSET_NAME = "indoor_day";
    private static final int DEFAULT_LIGHTPROBE_RESOURCE = C0022R.raw.sceneform_default_light_probes;
    private static final String TAG = Scene.class.getSimpleName();
    private final Camera camera;
    final CollisionSystem collisionSystem;
    private LightProbe lightProbe;
    private boolean lightProbeSet;
    private final ArrayList<OnUpdateListener> onUpdateListeners;
    private final Sun sunlightNode;
    private final TouchEventSystem touchEventSystem;
    private final SceneView view;

    public interface OnPeekTouchListener {
        void onPeekTouch(HitTestResult hitTestResult, MotionEvent motionEvent);
    }

    public interface OnTouchListener {
        boolean onSceneTouch(HitTestResult hitTestResult, MotionEvent motionEvent);
    }

    public interface OnUpdateListener {
        void onUpdate(FrameTime frameTime);
    }

    Scene() {
        this.lightProbeSet = false;
        this.collisionSystem = new CollisionSystem();
        this.touchEventSystem = new TouchEventSystem();
        this.onUpdateListeners = new ArrayList();
        this.view = null;
        this.lightProbe = null;
        this.camera = new Camera(true);
        if (AndroidPreconditions.isMinAndroidApiLevel()) {
            this.sunlightNode = new Sun();
        } else {
            this.sunlightNode = null;
        }
    }

    public Scene(SceneView sceneView) {
        this.lightProbeSet = false;
        this.collisionSystem = new CollisionSystem();
        this.touchEventSystem = new TouchEventSystem();
        this.onUpdateListeners = new ArrayList();
        Preconditions.checkNotNull(sceneView, "Parameter \"view\" was null.");
        this.view = sceneView;
        this.camera = new Camera(this);
        if (AndroidPreconditions.isMinAndroidApiLevel()) {
            this.sunlightNode = new Sun(this);
            setupLightProbe(sceneView);
            return;
        }
        this.sunlightNode = null;
    }

    private void setupLightProbe(SceneView sceneView) {
        Preconditions.checkNotNull(sceneView, "Parameter \"view\" was null.");
        try {
            LightProbe.builder().setSource(sceneView.getContext(), DEFAULT_LIGHTPROBE_RESOURCE).setAssetName(DEFAULT_LIGHTPROBE_ASSET_NAME).build().thenAccept(new C0398h(this)).exceptionally(C0399i.f115a);
        } catch (Exception e) {
            String str = "Failed to create the default Light Probe: ";
            String valueOf = String.valueOf(e.getLocalizedMessage());
            throw new AssertionError(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    public void addOnPeekTouchListener(OnPeekTouchListener onPeekTouchListener) {
        this.touchEventSystem.addOnPeekTouchListener(onPeekTouchListener);
    }

    public void addOnUpdateListener(OnUpdateListener onUpdateListener) {
        Preconditions.checkNotNull(onUpdateListener, "Parameter 'onUpdateListener' was null.");
        if (!this.onUpdateListeners.contains(onUpdateListener)) {
            this.onUpdateListeners.add(onUpdateListener);
        }
    }

    void dispatchUpdate(FrameTime frameTime) {
        ArrayList arrayList = this.onUpdateListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((OnUpdateListener) obj).onUpdate(frameTime);
        }
        callOnHierarchy(new C0397g(frameTime));
    }

    public Camera getCamera() {
        return this.camera;
    }

    public LightProbe getLightProbe() {
        if (this.lightProbe != null) {
            return this.lightProbe;
        }
        throw new IllegalStateException("Scene's lightProbe must not be null.");
    }

    public Node getSunlight() {
        return this.sunlightNode;
    }

    public SceneView getView() {
        if (this.view != null) {
            return this.view;
        }
        throw new IllegalStateException("Scene's view must not be null.");
    }

    public HitTestResult hitTest(MotionEvent motionEvent) {
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        return this.camera == null ? new HitTestResult() : hitTest(this.camera.motionEventToRay(motionEvent));
    }

    public HitTestResult hitTest(Ray ray) {
        Preconditions.checkNotNull(ray, "Parameter \"ray\" was null.");
        RayHit hitTestResult = new HitTestResult();
        Collider raycast = this.collisionSystem.raycast(ray, hitTestResult);
        if (raycast != null) {
            hitTestResult.setNode((Node) raycast.getTransformProvider());
        }
        return hitTestResult;
    }

    public ArrayList<HitTestResult> hitTestAll(MotionEvent motionEvent) {
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        return this.camera == null ? new ArrayList() : hitTestAll(this.camera.motionEventToRay(motionEvent));
    }

    public ArrayList<HitTestResult> hitTestAll(Ray ray) {
        Preconditions.checkNotNull(ray, "Parameter \"ray\" was null.");
        ArrayList<HitTestResult> arrayList = new ArrayList();
        this.collisionSystem.raycastAll(ray, arrayList, C0394d.f110a, C0395e.f111a);
        return arrayList;
    }

    final /* synthetic */ void lambda$setupLightProbe$4$Scene(LightProbe lightProbe) {
        if (!this.lightProbeSet) {
            setLightProbe(lightProbe);
        }
    }

    public void onAddChild(Node node) {
        super.onAddChild(node);
        node.setSceneRecursively(this);
    }

    public void onRemoveChild(Node node) {
        super.onRemoveChild(node);
        node.setSceneRecursively(null);
    }

    void onTouchEvent(MotionEvent motionEvent) {
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        this.touchEventSystem.onTouchEvent(hitTest(motionEvent), motionEvent);
    }

    public Node overlapTest(Node node) {
        Preconditions.checkNotNull(node, "Parameter \"node\" was null.");
        Collider collider = node.getCollider();
        if (collider == null) {
            return null;
        }
        collider = this.collisionSystem.intersects(collider);
        return collider == null ? null : (Node) collider.getTransformProvider();
    }

    public ArrayList<Node> overlapTestAll(Node node) {
        Preconditions.checkNotNull(node, "Parameter \"node\" was null.");
        ArrayList<Node> arrayList = new ArrayList();
        Collider collider = node.getCollider();
        if (collider == null) {
            return arrayList;
        }
        this.collisionSystem.intersectsAll(collider, new C0396f(arrayList));
        return arrayList;
    }

    public void removeOnPeekTouchListener(OnPeekTouchListener onPeekTouchListener) {
        this.touchEventSystem.removeOnPeekTouchListener(onPeekTouchListener);
    }

    public void removeOnUpdateListener(OnUpdateListener onUpdateListener) {
        Preconditions.checkNotNull(onUpdateListener, "Parameter 'onUpdateListener' was null.");
        this.onUpdateListeners.remove(onUpdateListener);
    }

    void setLightEstimate(float f) {
        if (this.lightProbe != null) {
            this.lightProbe.setLightEstimate(f);
        }
        if (this.sunlightNode != null) {
            this.sunlightNode.setLightEstimate(f);
        }
    }

    public void setLightProbe(LightProbe lightProbe) {
        Preconditions.checkNotNull(lightProbe, "Parameter \"lightProbe\" was null.");
        this.lightProbe = lightProbe;
        this.lightProbeSet = true;
        if (this.view != null) {
            ((Renderer) Preconditions.checkNotNull(this.view.getRenderer())).setLightProbe(lightProbe);
            return;
        }
        throw new IllegalStateException("Scene's view must not be null.");
    }

    @Deprecated
    public void setOnPeekTouchListener(OnPeekTouchListener onPeekTouchListener) {
        this.touchEventSystem.setOnPeekTouchListener(onPeekTouchListener);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.touchEventSystem.setOnTouchListener(onTouchListener);
    }

    @Deprecated
    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListeners.clear();
        if (onUpdateListener != null) {
            this.onUpdateListeners.add(onUpdateListener);
        }
    }
}
