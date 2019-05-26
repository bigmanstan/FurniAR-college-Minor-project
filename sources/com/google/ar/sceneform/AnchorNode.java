package com.google.ar.sceneform;

import android.util.Log;
import com.google.ar.core.Anchor;
import com.google.ar.core.Pose;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import java.util.List;

public class AnchorNode extends Node {
    private static final float SMOOTH_FACTOR = 12.0f;
    private static final String TAG = AnchorNode.class.getSimpleName();
    private Anchor anchor;
    private boolean isSmoothed = true;
    private boolean wasTracking;

    public AnchorNode(Anchor anchor) {
        setAnchor(anchor);
    }

    private void setChildrenEnabled(boolean z) {
        List children = getChildren();
        for (int i = 0; i < children.size(); i++) {
            ((Node) children.get(i)).setEnabled(z);
        }
    }

    private void updateTrackedPose(float f, boolean z) {
        boolean isTracking = isTracking();
        if (isTracking != this.wasTracking) {
            boolean z2;
            if (!isTracking) {
                if (this.anchor != null) {
                    z2 = false;
                    setChildrenEnabled(z2);
                }
            }
            z2 = true;
            setChildrenEnabled(z2);
        }
        if (this.anchor != null) {
            if (isTracking) {
                Pose pose = this.anchor.getPose();
                Vector3 a = C0389a.m86a(pose);
                Quaternion b = C0389a.m89b(pose);
                if (!this.isSmoothed || z) {
                    super.setWorldPosition(a);
                    super.setWorldRotation(b);
                } else {
                    Vector3 worldPosition = getWorldPosition();
                    f = MathHelper.clamp(f * SMOOTH_FACTOR, 0.0f, 1.0f);
                    worldPosition.set(Vector3.lerp(worldPosition, a, f));
                    super.setWorldPosition(worldPosition);
                    super.setWorldRotation(Quaternion.slerp(getWorldRotation(), b, f));
                }
                this.wasTracking = isTracking;
                return;
            }
        }
        this.wasTracking = isTracking;
    }

    public Anchor getAnchor() {
        return this.anchor;
    }

    public boolean isSmoothed() {
        return this.isSmoothed;
    }

    public boolean isTracking() {
        if (this.anchor != null) {
            if (this.anchor.getTrackingState() == TrackingState.TRACKING) {
                return true;
            }
        }
        return false;
    }

    public void onUpdate(FrameTime frameTime) {
        updateTrackedPose(frameTime.getDeltaSeconds(), false);
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
        boolean z = true;
        if (this.anchor != null) {
            updateTrackedPose(0.0f, true);
        }
        this.wasTracking = isTracking();
        if (!this.wasTracking) {
            if (anchor != null) {
                z = false;
            }
        }
        setChildrenEnabled(z);
    }

    public void setLocalPosition(Vector3 vector3) {
        if (this.anchor != null) {
            Log.w(TAG, "Cannot call setLocalPosition on AnchorNode while it is anchored.");
        } else {
            super.setLocalPosition(vector3);
        }
    }

    public void setLocalRotation(Quaternion quaternion) {
        if (this.anchor != null) {
            Log.w(TAG, "Cannot call setLocalRotation on AnchorNode while it is anchored.");
        } else {
            super.setLocalRotation(quaternion);
        }
    }

    public void setSmoothed(boolean z) {
        this.isSmoothed = z;
    }

    public void setWorldPosition(Vector3 vector3) {
        if (this.anchor != null) {
            Log.w(TAG, "Cannot call setWorldPosition on AnchorNode while it is anchored.");
        } else {
            super.setWorldPosition(vector3);
        }
    }

    public void setWorldRotation(Quaternion quaternion) {
        if (this.anchor != null) {
            Log.w(TAG, "Cannot call setWorldRotation on AnchorNode while it is anchored.");
        } else {
            super.setWorldRotation(quaternion);
        }
    }
}
