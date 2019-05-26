package com.google.ar.sceneform.ux;

import android.support.annotation.Nullable;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Plane.Type;
import com.google.ar.core.Pose;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;
import java.util.EnumSet;
import java.util.List;

public class TranslationController extends BaseTransformationController<DragGesture> {
    private static final float LERP_SPEED = 12.0f;
    private static final float POSITION_LENGTH_THRESHOLD = 0.01f;
    private static final float ROTATION_DOT_THRESHOLD = 0.99f;
    private EnumSet<Type> allowedPlaneTypes = EnumSet.allOf(Type.class);
    @Nullable
    private Vector3 desiredLocalPosition;
    @Nullable
    private Quaternion desiredLocalRotation;
    private final Vector3 initialForwardInLocal = new Vector3();
    @Nullable
    private HitResult lastArHitResult;

    public TranslationController(BaseTransformableNode transformableNode, DragGestureRecognizer gestureRecognizer) {
        super(transformableNode, gestureRecognizer);
    }

    public void setAllowedPlaneTypes(EnumSet<Type> allowedPlaneTypes) {
        this.allowedPlaneTypes = allowedPlaneTypes;
    }

    public EnumSet<Type> getAllowedPlaneTypes() {
        return this.allowedPlaneTypes;
    }

    public void onUpdated(Node node, FrameTime frameTime) {
        updatePosition(frameTime);
        updateRotation(frameTime);
    }

    public boolean isTransforming() {
        if (!super.isTransforming() && this.desiredLocalRotation == null) {
            if (this.desiredLocalPosition == null) {
                return false;
            }
        }
        return true;
    }

    public boolean canStartTransformation(DragGesture gesture) {
        Node targetNode = gesture.getTargetNode();
        if (targetNode == null) {
            return false;
        }
        Node transformableNode = getTransformableNode();
        if (targetNode != transformableNode && !targetNode.isDescendantOf(transformableNode)) {
            return false;
        }
        if (!transformableNode.isSelected() && !transformableNode.select()) {
            return false;
        }
        Vector3 initialForwardInWorld = transformableNode.getForward();
        Node parent = transformableNode.getParent();
        if (parent != null) {
            this.initialForwardInLocal.set(parent.worldToLocalDirection(initialForwardInWorld));
        } else {
            this.initialForwardInLocal.set(initialForwardInWorld);
        }
        return true;
    }

    public void onContinueTransformation(DragGesture gesture) {
        TranslationController translationController = this;
        Scene scene = getTransformableNode().getScene();
        if (scene != null) {
            Frame frame = ((ArSceneView) scene.getView()).getArFrame();
            if (frame != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
                Vector3 position = gesture.getPosition();
                List<HitResult> hitResultList = frame.hitTest(position.f120x, position.f121y);
                for (int i = 0; i < hitResultList.size(); i++) {
                    HitResult hit = (HitResult) hitResultList.get(i);
                    Trackable trackable = hit.getTrackable();
                    Pose pose = hit.getHitPose();
                    if (trackable instanceof Plane) {
                        Plane plane = (Plane) trackable;
                        if (plane.isPoseInPolygon(pose) && translationController.allowedPlaneTypes.contains(plane.getType())) {
                            translationController.desiredLocalPosition = new Vector3(pose.tx(), pose.ty(), pose.tz());
                            translationController.desiredLocalRotation = new Quaternion(pose.qx(), pose.qy(), pose.qz(), pose.qw());
                            Node parent = getTransformableNode().getParent();
                            if (!(parent == null || translationController.desiredLocalPosition == null || translationController.desiredLocalRotation == null)) {
                                translationController.desiredLocalPosition = parent.worldToLocalPoint(translationController.desiredLocalPosition);
                                translationController.desiredLocalRotation = Quaternion.multiply(parent.getWorldRotation().inverted(), (Quaternion) Preconditions.checkNotNull(translationController.desiredLocalRotation));
                            }
                            translationController.desiredLocalRotation = calculateFinalDesiredLocalRotation((Quaternion) Preconditions.checkNotNull(translationController.desiredLocalRotation));
                            translationController.lastArHitResult = hit;
                        }
                    }
                }
            }
        }
    }

    public void onEndTransformation(DragGesture gesture) {
        HitResult hitResult = this.lastArHitResult;
        if (hitResult != null) {
            if (hitResult.getTrackable().getTrackingState() == TrackingState.TRACKING) {
                AnchorNode anchorNode = getAnchorNodeOrDie();
                Anchor oldAnchor = anchorNode.getAnchor();
                if (oldAnchor != null) {
                    oldAnchor.detach();
                }
                Anchor newAnchor = hitResult.createAnchor();
                Vector3 worldPosition = getTransformableNode().getWorldPosition();
                Quaternion worldRotation = getTransformableNode().getWorldRotation();
                Quaternion finalDesiredWorldRotation = worldRotation;
                Quaternion desiredLocalRotation = this.desiredLocalRotation;
                if (desiredLocalRotation != null) {
                    getTransformableNode().setLocalRotation(desiredLocalRotation);
                    finalDesiredWorldRotation = getTransformableNode().getWorldRotation();
                }
                anchorNode.setAnchor(newAnchor);
                getTransformableNode().setWorldRotation(finalDesiredWorldRotation);
                this.initialForwardInLocal.set(anchorNode.worldToLocalDirection(getTransformableNode().getForward()));
                getTransformableNode().setWorldRotation(worldRotation);
                getTransformableNode().setWorldPosition(worldPosition);
            }
            this.desiredLocalPosition = Vector3.zero();
            this.desiredLocalRotation = calculateFinalDesiredLocalRotation(Quaternion.identity());
        }
    }

    private AnchorNode getAnchorNodeOrDie() {
        Node parent = getTransformableNode().getParent();
        if (parent instanceof AnchorNode) {
            return (AnchorNode) parent;
        }
        throw new IllegalStateException("TransformableNode must have an AnchorNode as a parent.");
    }

    private void updatePosition(FrameTime frameTime) {
        Vector3 desiredLocalPosition = this.desiredLocalPosition;
        if (desiredLocalPosition != null) {
            Vector3 localPosition = Vector3.lerp(getTransformableNode().getLocalPosition(), desiredLocalPosition, MathHelper.clamp(frameTime.getDeltaSeconds() * LERP_SPEED, 0.0f, 1.0f));
            if (Math.abs(Vector3.subtract(desiredLocalPosition, localPosition).length()) <= POSITION_LENGTH_THRESHOLD) {
                localPosition = desiredLocalPosition;
                this.desiredLocalPosition = null;
            }
            getTransformableNode().setLocalPosition(localPosition);
        }
    }

    private void updateRotation(FrameTime frameTime) {
        Quaternion desiredLocalRotation = this.desiredLocalRotation;
        if (desiredLocalRotation != null) {
            Quaternion localRotation = Quaternion.slerp(getTransformableNode().getLocalRotation(), desiredLocalRotation, MathHelper.clamp(frameTime.getDeltaSeconds() * LERP_SPEED, 0.0f, 1.0f));
            if (Math.abs(dotQuaternion(localRotation, desiredLocalRotation)) >= ROTATION_DOT_THRESHOLD) {
                localRotation = desiredLocalRotation;
                this.desiredLocalRotation = null;
            }
            getTransformableNode().setLocalRotation(localRotation);
        }
    }

    private Quaternion calculateFinalDesiredLocalRotation(Quaternion desiredLocalRotation) {
        return Quaternion.multiply(Quaternion.rotationBetweenVectors(Vector3.up(), Quaternion.rotateVector(desiredLocalRotation, Vector3.up())), Quaternion.rotationBetweenVectors(Vector3.forward(), this.initialForwardInLocal)).normalized();
    }

    private static float dotQuaternion(Quaternion lhs, Quaternion rhs) {
        return (((lhs.f117x * rhs.f117x) + (lhs.f118y * rhs.f118y)) + (lhs.f119z * rhs.f119z)) + (lhs.f116w * rhs.f116w);
    }
}
