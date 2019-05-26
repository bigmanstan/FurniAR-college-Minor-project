package com.google.ar.sceneform;

import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.google.ar.sceneform.collision.Collider;
import com.google.ar.sceneform.collision.CollisionShape;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Light;
import com.google.ar.sceneform.rendering.LightInstance;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.RenderableInstance;
import com.google.ar.sceneform.rendering.Renderer;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Node extends NodeParent implements TransformProvider {
    private static final String DEFAULT_NAME = "Node";
    private static final int DEFAULT_TOUCH_SLOP = 8;
    private static final float DIRECTION_UP_EPSILON = 0.99f;
    private static final Matrix tempMatrix = new Matrix();
    private boolean active = false;
    private final Matrix cachedWorldModelMatrix = new Matrix();
    private final Matrix cachedWorldModelMatrixInverse = new Matrix();
    private final Vector3 cachedWorldPosition = new Vector3();
    private final Quaternion cachedWorldRotation = new Quaternion();
    private final Vector3 cachedWorldScale = new Vector3();
    private Collider collider;
    private CollisionShape collisionShape;
    private boolean enabled = true;
    private boolean isCachedWorldTransformationDirty = true;
    private final ArrayList<LifecycleListener> lifecycleListeners = new ArrayList();
    private LightInstance lightInstance;
    private final Matrix localModelMatrix = new Matrix();
    private final Vector3 localPosition = new Vector3();
    private final Quaternion localRotation = new Quaternion();
    private final Vector3 localScale = new Vector3();
    private String name = DEFAULT_NAME;
    private int nameHash = DEFAULT_NAME.hashCode();
    private OnTapListener onTapListener;
    private OnTouchListener onTouchListener;
    NodeParent parent;
    private Node parentAsNode;
    private int renderableId = 0;
    private RenderableInstance renderableInstance;
    private Scene scene;
    private TapTrackingData tapTrackingData = null;

    public interface LifecycleListener {
        void onActivated(Node node);

        void onDeactivated(Node node);

        void onUpdated(Node node, FrameTime frameTime);
    }

    public interface OnTapListener {
        void onTap(HitTestResult hitTestResult, MotionEvent motionEvent);
    }

    public interface OnTouchListener {
        boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent);
    }

    static class TapTrackingData {
        /* renamed from: a */
        private final Node f101a;
        /* renamed from: b */
        private final Vector3 f102b;

        private TapTrackingData(Node node, Vector3 vector3) {
            this.f101a = node;
            this.f102b = new Vector3(vector3);
        }
    }

    public Node() {
        AndroidPreconditions.checkUiThread();
        this.localScale.set(1.0f, 1.0f, 1.0f);
        this.cachedWorldScale.set(this.localScale);
    }

    private void activate() {
        AndroidPreconditions.checkUiThread();
        if (this.active) {
            throw new AssertionError("Cannot call activate while already active.");
        }
        this.active = true;
        if (this.renderableInstance != null) {
            this.renderableInstance.attachToRenderer(getRendererOrDie());
        }
        if (this.lightInstance != null) {
            this.lightInstance.attachToRenderer(getRendererOrDie());
        }
        if (!(this.collider == null || this.scene == null)) {
            this.collider.setAttachedCollisionSystem(this.scene.collisionSystem);
        }
        onActivate();
        ArrayList arrayList = this.lifecycleListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((LifecycleListener) obj).onActivated(this);
        }
    }

    private final void applyTransformUpdate() {
        boolean z;
        updateLocalModelMatrix();
        if (this.parentAsNode == null) {
            this.cachedWorldModelMatrix.set(this.localModelMatrix.data);
            this.cachedWorldPosition.set(this.localPosition);
            this.cachedWorldRotation.set(this.localRotation);
            this.cachedWorldScale.set(this.localScale);
            z = false;
        } else {
            Matrix.multiply(this.parentAsNode.cachedWorldModelMatrix, this.localModelMatrix, this.cachedWorldModelMatrix);
            z = true;
        }
        this.isCachedWorldTransformationDirty = z;
        Matrix.invert(this.cachedWorldModelMatrix, this.cachedWorldModelMatrixInverse);
        if (this.collider != null) {
            this.collider.updateCachedWorldShape();
        }
    }

    private final void applyTransformUpdateRecursively() {
        applyTransformUpdate();
        for (Node applyTransformUpdateRecursively : getChildren()) {
            applyTransformUpdateRecursively.applyTransformUpdateRecursively();
        }
    }

    private void createLightInstance(Light light) {
        this.lightInstance = light.createInstance(this);
        if (this.lightInstance == null) {
            throw new NullPointerException("light.createInstance() failed - which should not happen.");
        } else if (this.active) {
            this.lightInstance.attachToRenderer(getRendererOrDie());
        }
    }

    private void deactivate() {
        AndroidPreconditions.checkUiThread();
        if (this.active) {
            int i = 0;
            this.active = false;
            if (this.renderableInstance != null) {
                this.renderableInstance.detachFromRenderer();
            }
            if (this.lightInstance != null) {
                this.lightInstance.detachFromRenderer();
            }
            if (this.collider != null) {
                this.collider.setAttachedCollisionSystem(null);
            }
            onDeactivate();
            ArrayList arrayList = this.lifecycleListeners;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((LifecycleListener) obj).onDeactivated(this);
            }
            return;
        }
        throw new AssertionError("Cannot call deactivate while already inactive.");
    }

    private void destroyLightInstance() {
        if (this.lightInstance != null) {
            if (this.active) {
                this.lightInstance.detachFromRenderer();
            }
            this.lightInstance.dispose();
            this.lightInstance = null;
        }
    }

    private Renderer getRendererOrDie() {
        if (this.scene != null) {
            return (Renderer) Preconditions.checkNotNull(this.scene.getView().getRenderer());
        }
        throw new IllegalStateException("Unable to get Renderer.");
    }

    private int getScaledTouchSlop() {
        Scene scene = getScene();
        if (scene != null && AndroidPreconditions.isAndroidApiAvailable()) {
            if (!AndroidPreconditions.isUnderTesting()) {
                return ViewConfiguration.get(scene.getView().getContext()).getScaledTouchSlop();
            }
        }
        return 8;
    }

    private Vector3 getWorldPositionInternal() {
        updateCachedWorldTransformation();
        return this.cachedWorldPosition;
    }

    private Quaternion getWorldRotationInternal() {
        updateCachedWorldTransformation();
        return this.cachedWorldRotation;
    }

    private Vector3 getWorldScaleInternal() {
        updateCachedWorldTransformation();
        return this.cachedWorldScale;
    }

    private void refreshCollider() {
        CollisionShape collisionShape = this.collisionShape;
        Renderable renderable = getRenderable();
        if (collisionShape == null && renderable != null) {
            collisionShape = renderable.getCollisionShape();
        }
        if (collisionShape != null) {
            if (this.collider == null) {
                this.collider = new Collider(this, collisionShape);
                if (this.active && this.scene != null) {
                    this.collider.setAttachedCollisionSystem(this.scene.collisionSystem);
                }
            } else if (this.collider.getShape() != collisionShape) {
                this.collider.setShape(collisionShape);
            }
        } else if (this.collider != null) {
            this.collider.setAttachedCollisionSystem(null);
            this.collider = null;
        }
    }

    private void setSceneRecursivelyInternal(Scene scene) {
        this.scene = scene;
        for (Node sceneRecursively : getChildren()) {
            sceneRecursively.setSceneRecursively(scene);
        }
    }

    private boolean shouldBeActive() {
        return (this.enabled && this.scene != null) ? this.parentAsNode == null || this.parentAsNode.isActive() : false;
    }

    private void updateActiveStatusRecursively() {
        boolean shouldBeActive = shouldBeActive();
        if (this.active != shouldBeActive) {
            if (shouldBeActive) {
                activate();
            } else {
                deactivate();
            }
        }
        for (Node updateActiveStatusRecursively : getChildren()) {
            updateActiveStatusRecursively.updateActiveStatusRecursively();
        }
    }

    private void updateCachedWorldTransformation() {
        if (this.isCachedWorldTransformationDirty) {
            this.cachedWorldModelMatrix.decompose(this.cachedWorldPosition, this.cachedWorldScale, this.cachedWorldRotation);
            this.isCachedWorldTransformationDirty = false;
        }
    }

    private final void updateLocalModelMatrix() {
        tempMatrix.makeTranslation(this.localPosition);
        this.localModelMatrix.makeRotation(this.localRotation);
        Matrix.multiply(tempMatrix, this.localModelMatrix, this.localModelMatrix);
        tempMatrix.makeScale(this.localScale);
        Matrix.multiply(this.localModelMatrix, tempMatrix, this.localModelMatrix);
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        if (!this.lifecycleListeners.contains(lifecycleListener)) {
            this.lifecycleListeners.add(lifecycleListener);
        }
    }

    public void callOnHierarchy(Consumer<Node> consumer) {
        consumer.accept(this);
        super.callOnHierarchy(consumer);
    }

    protected final boolean canAddChild(Node node, StringBuilder stringBuilder) {
        if (!super.canAddChild(node, stringBuilder)) {
            return false;
        }
        if (!isDescendantOf(node)) {
            return true;
        }
        stringBuilder.append("Cannot add child: A node's parent cannot be one of its descendants.");
        return false;
    }

    boolean dispatchTouchEvent(HitTestResult hitTestResult, MotionEvent motionEvent) {
        Preconditions.checkNotNull(hitTestResult, "Parameter \"hitTestResult\" was null.");
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        return !isActive() ? false : C0389a.m88a(this, motionEvent) ? true : (this.onTouchListener == null || !this.onTouchListener.onTouch(hitTestResult, motionEvent)) ? onTouchEvent(hitTestResult, motionEvent) : true;
    }

    final void dispatchUpdate(FrameTime frameTime) {
        if (isActive()) {
            Renderable renderable = getRenderable();
            if (renderable != null && renderable.getId().checkChanged(this.renderableId)) {
                refreshCollider();
                this.renderableId = renderable.getId().get();
            }
            if (this.collider != null) {
                this.collider.onUpdate();
            }
            onUpdate(frameTime);
            ArrayList arrayList = this.lifecycleListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((LifecycleListener) obj).onUpdated(this, frameTime);
            }
        }
    }

    public Node findInHierarchy(Predicate<Node> predicate) {
        return predicate.test(this) ? this : super.findInHierarchy(predicate);
    }

    public final Vector3 getBack() {
        return localToWorldDirection(Vector3.back());
    }

    final Collider getCollider() {
        return this.collider;
    }

    public CollisionShape getCollisionShape() {
        return this.collider != null ? this.collider.getShape() : null;
    }

    public final Vector3 getDown() {
        return localToWorldDirection(Vector3.down());
    }

    public final Vector3 getForward() {
        return localToWorldDirection(Vector3.forward());
    }

    public final Vector3 getLeft() {
        return localToWorldDirection(Vector3.left());
    }

    public Light getLight() {
        return this.lightInstance != null ? this.lightInstance.getLight() : null;
    }

    public final Vector3 getLocalPosition() {
        return new Vector3(this.localPosition);
    }

    public final Quaternion getLocalRotation() {
        return new Quaternion(this.localRotation);
    }

    public final Vector3 getLocalScale() {
        return new Vector3(this.localScale);
    }

    public final String getName() {
        return this.name;
    }

    int getNameHash() {
        return this.nameHash;
    }

    final NodeParent getNodeParent() {
        return this.parent;
    }

    public final Node getParent() {
        return this.parentAsNode;
    }

    public Renderable getRenderable() {
        return this.renderableInstance == null ? null : this.renderableInstance.getRenderable();
    }

    public final Vector3 getRight() {
        return localToWorldDirection(Vector3.right());
    }

    public final Scene getScene() {
        return this.scene;
    }

    public final Vector3 getUp() {
        return localToWorldDirection(Vector3.up());
    }

    public final Matrix getWorldModelMatrix() {
        return this.cachedWorldModelMatrix;
    }

    public final Vector3 getWorldPosition() {
        return new Vector3(getWorldPositionInternal());
    }

    public final Quaternion getWorldRotation() {
        return new Quaternion(getWorldRotationInternal());
    }

    public final Vector3 getWorldScale() {
        return new Vector3(getWorldScaleInternal());
    }

    public final boolean isActive() {
        return this.active;
    }

    public final boolean isDescendantOf(NodeParent nodeParent) {
        Preconditions.checkNotNull(nodeParent, "Parameter \"ancestor\" was null.");
        NodeParent nodeParent2 = this.parent;
        Node node = this.parentAsNode;
        while (nodeParent2 != null) {
            if (nodeParent2 != nodeParent) {
                if (node == null) {
                    break;
                }
                nodeParent2 = node.parent;
                node = node.parentAsNode;
            } else {
                return true;
            }
        }
        return false;
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    public boolean isTopLevel() {
        if (this.parent != null) {
            if (this.parent != this.scene) {
                return false;
            }
        }
        return true;
    }

    public final Vector3 localToWorldDirection(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"direction\" was null.");
        return Quaternion.rotateVector(getWorldRotationInternal(), vector3);
    }

    public final Vector3 localToWorldPoint(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"point\" was null.");
        return this.cachedWorldModelMatrix.transformPoint(vector3);
    }

    public void onActivate() {
    }

    protected final void onAddChild(Node node) {
        super.onAddChild(node);
        node.parentAsNode = this;
        node.applyTransformUpdateRecursively();
        node.setSceneRecursively(this.scene);
    }

    public void onDeactivate() {
    }

    protected final void onRemoveChild(Node node) {
        super.onRemoveChild(node);
        node.parentAsNode = null;
        node.applyTransformUpdateRecursively();
        node.setSceneRecursively(null);
    }

    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
    }

    public boolean onTouchEvent(HitTestResult hitTestResult, MotionEvent motionEvent) {
        Preconditions.checkNotNull(hitTestResult, "Parameter \"hitTestResult\" was null.");
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0 || !isActive()) {
            this.tapTrackingData = null;
        }
        boolean z = false;
        switch (actionMasked) {
            case 0:
                if (this.onTapListener == null) {
                    return false;
                }
                Node node = hitTestResult.getNode();
                if (node != null) {
                    this.tapTrackingData = new TapTrackingData(new Vector3(motionEvent.getX(), motionEvent.getY(), 0.0f));
                    break;
                }
                return false;
            case 1:
            case 2:
                TapTrackingData tapTrackingData = this.tapTrackingData;
                if (tapTrackingData == null) {
                    return false;
                }
                boolean z2;
                float scaledTouchSlop = (float) getScaledTouchSlop();
                float length = Vector3.subtract(tapTrackingData.f102b, new Vector3(motionEvent.getX(), motionEvent.getY(), 0.0f)).length();
                if (!(hitTestResult.getNode() == tapTrackingData.f101a)) {
                    if (length >= scaledTouchSlop) {
                        z2 = false;
                        if (z2) {
                            if (actionMasked == 1 && this.onTapListener != null) {
                                this.onTapListener.onTap(hitTestResult, motionEvent);
                                z = true;
                            }
                        }
                        this.tapTrackingData = null;
                        return z;
                    }
                }
                z2 = true;
                if (z2) {
                    this.onTapListener.onTap(hitTestResult, motionEvent);
                    z = true;
                    break;
                }
                this.tapTrackingData = null;
                return z;
            default:
                return false;
        }
        return true;
    }

    public void onUpdate(FrameTime frameTime) {
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        this.lifecycleListeners.remove(lifecycleListener);
    }

    public void setCollisionShape(CollisionShape collisionShape) {
        AndroidPreconditions.checkUiThread();
        this.collisionShape = collisionShape;
        refreshCollider();
    }

    public final void setEnabled(boolean z) {
        AndroidPreconditions.checkUiThread();
        if (this.enabled != z) {
            this.enabled = z;
            updateActiveStatusRecursively();
        }
    }

    public void setLight(Light light) {
        if (getLight() != light) {
            destroyLightInstance();
            if (light != null) {
                createLightInstance(light);
            }
        }
    }

    public void setLocalPosition(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"position\" was null.");
        this.localPosition.set(vector3);
        applyTransformUpdateRecursively();
    }

    public void setLocalRotation(Quaternion quaternion) {
        Preconditions.checkNotNull(quaternion, "Parameter \"rotation\" was null.");
        this.localRotation.set(quaternion);
        applyTransformUpdateRecursively();
    }

    public void setLocalScale(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"scale\" was null.");
        this.localScale.set(vector3);
        applyTransformUpdateRecursively();
    }

    public final void setLookDirection(Vector3 vector3) {
        Vector3 up = Vector3.up();
        if (Math.abs(Vector3.dot(vector3, up)) > DIRECTION_UP_EPSILON) {
            up = new Vector3(0.0f, 0.0f, 1.0f);
        }
        setLookDirection(vector3, up);
    }

    public final void setLookDirection(Vector3 vector3, Vector3 vector32) {
        setWorldRotation(Quaternion.lookRotation(vector3, vector32));
    }

    public final void setName(String str) {
        Preconditions.checkNotNull(str, "Parameter \"name\" was null.");
        this.name = str;
        this.nameHash = str.hashCode();
    }

    public void setOnTapListener(OnTapListener onTapListener) {
        if (onTapListener != this.onTapListener) {
            this.tapTrackingData = null;
        }
        this.onTapListener = onTapListener;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void setParent(NodeParent nodeParent) {
        AndroidPreconditions.checkUiThread();
        if (nodeParent != this.parent) {
            if (nodeParent != null) {
                nodeParent.addChild(this);
                return;
            }
            if (this.parent != null) {
                this.parent.removeChild(this);
            }
        }
    }

    public void setRenderable(Renderable renderable) {
        AndroidPreconditions.checkUiThread();
        if (this.renderableInstance == null || this.renderableInstance.getRenderable() != renderable) {
            int i;
            if (this.renderableInstance != null) {
                if (this.active) {
                    this.renderableInstance.detachFromRenderer();
                }
                this.renderableInstance.dispose();
                this.renderableInstance = null;
            }
            if (renderable != null) {
                this.renderableInstance = renderable.createInstance(this);
                if (this.active) {
                    this.renderableInstance.attachToRenderer(getRendererOrDie());
                }
                i = renderable.getId().get();
            } else {
                i = 0;
            }
            this.renderableId = i;
            refreshCollider();
        }
    }

    final void setSceneRecursively(Scene scene) {
        AndroidPreconditions.checkUiThread();
        setSceneRecursivelyInternal(scene);
        updateActiveStatusRecursively();
    }

    public void setWorldPosition(Vector3 vector3) {
        Vector3 vector32;
        Preconditions.checkNotNull(vector3, "Parameter \"position\" was null.");
        if (this.parentAsNode == null) {
            vector32 = this.localPosition;
        } else {
            vector32 = this.localPosition;
            vector3 = this.parentAsNode.worldToLocalPoint(vector3);
        }
        vector32.set(vector3);
        applyTransformUpdateRecursively();
    }

    public void setWorldRotation(Quaternion quaternion) {
        Quaternion quaternion2;
        Preconditions.checkNotNull(quaternion, "Parameter \"rotation\" was null.");
        if (this.parentAsNode == null) {
            quaternion2 = this.localRotation;
        } else {
            quaternion2 = this.localRotation;
            quaternion = Quaternion.multiply(this.parentAsNode.getWorldRotationInternal().inverted(), quaternion);
        }
        quaternion2.set(quaternion);
        applyTransformUpdateRecursively();
    }

    public void setWorldScale(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"scale\" was null.");
        this.localScale.set(1.0f, 1.0f, 1.0f);
        updateLocalModelMatrix();
        if (this.parentAsNode != null) {
            Matrix.multiply(this.parentAsNode.cachedWorldModelMatrix, this.localModelMatrix, this.cachedWorldModelMatrix);
            Matrix matrix = this.localModelMatrix;
            matrix.makeScale(vector3);
            Matrix matrix2 = this.cachedWorldModelMatrix;
            Matrix.invert(this.cachedWorldModelMatrix, matrix2);
            Matrix.multiply(matrix2, matrix, matrix2);
            setLocalScale(matrix2.extractScale());
            return;
        }
        setLocalScale(vector3);
    }

    public String toString() {
        String str = this.name;
        String obj = super.toString();
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 2) + String.valueOf(obj).length());
        stringBuilder.append(str);
        stringBuilder.append("(");
        stringBuilder.append(obj);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final Vector3 worldToLocalDirection(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"direction\" was null.");
        return Quaternion.inverseRotateVector(getWorldRotationInternal(), vector3);
    }

    public final Vector3 worldToLocalPoint(Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"point\" was null.");
        return this.cachedWorldModelMatrixInverse.transformPoint(vector3);
    }
}
