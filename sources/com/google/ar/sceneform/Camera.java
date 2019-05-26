package com.google.ar.sceneform;

import android.view.MotionEvent;
import android.view.View;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.MathHelper;
import com.google.ar.sceneform.math.Matrix;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.CameraProvider;
import com.google.ar.sceneform.utilities.Preconditions;

public class Camera extends Node implements CameraProvider {
    private static final float DEFAULT_FAR_PLANE = 30.0f;
    private static final float DEFAULT_NEAR_PLANE = 0.01f;
    private static final int FALLBACK_VIEW_HEIGHT = 1080;
    private static final int FALLBACK_VIEW_WIDTH = 1920;
    private float farPlane = DEFAULT_FAR_PLANE;
    private boolean isArCamera = false;
    private float nearPlane = DEFAULT_NEAR_PLANE;
    private final Matrix projectionMatrix = new Matrix();
    private final Matrix viewMatrix = new Matrix();

    Camera(Scene scene) {
        Preconditions.checkNotNull(scene, "Parameter \"scene\" was null.");
        super.setParent(scene);
        this.isArCamera = scene.getView() instanceof ArSceneView;
        if (!this.isArCamera) {
            scene.getView().addOnLayoutChangeListener(new C0390b(this));
        }
    }

    Camera(boolean z) {
        this.isArCamera = z;
    }

    private int getViewHeight() {
        Scene scene = getScene();
        return scene == null ? FALLBACK_VIEW_HEIGHT : scene.getView().getHeight();
    }

    private int getViewWidth() {
        Scene scene = getScene();
        return scene == null ? FALLBACK_VIEW_WIDTH : scene.getView().getWidth();
    }

    private void refreshProjectionMatrix() {
        if (!this.isArCamera) {
            int viewWidth = getViewWidth();
            int viewHeight = getViewHeight();
            if (viewWidth != 0) {
                if (viewHeight != 0) {
                    setPerspective(90.0f, ((float) viewWidth) / ((float) viewHeight), this.nearPlane, this.farPlane);
                }
            }
        }
    }

    private void setPerspective(float f, float f2, float f3, float f4) {
        if (f <= 0.0f || f >= 180.0f) {
            throw new IllegalArgumentException("Parameter \"verticalFovInDegrees\" is out of the valid range of (0, 180) degrees.");
        } else if (f2 > 0.0f) {
            float tan = ((float) Math.tan(Math.toRadians((double) f) * 0.5d)) * f3;
            float f5 = tan * f2;
            setPerspective(-f5, f5, -tan, tan, f3, f4);
        } else {
            throw new IllegalArgumentException("Parameter \"aspect\" must be greater than zero.");
        }
    }

    private void setPerspective(float f, float f2, float f3, float f4, float f5, float f6) {
        float[] fArr = this.projectionMatrix.data;
        if (f == f2 || f3 == f4 || f5 <= 0.0f || f6 <= f5) {
            throw new IllegalArgumentException("Invalid parameters to setPerspective, valid values:  width != height, bottom != top, near > 0.0f, far > near");
        }
        float f7 = 1.0f / (f2 - f);
        float f8 = 1.0f / (f4 - f3);
        float f9 = 1.0f / (f6 - f5);
        float f10 = 2.0f * f5;
        fArr[0] = f10 * f7;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        fArr[5] = f10 * f8;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = (f2 + f) * f7;
        fArr[9] = (f4 + f3) * f8;
        fArr[10] = (-(f6 + f5)) * f9;
        fArr[11] = -1.0f;
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = ((-2.0f * f6) * f5) * f9;
        fArr[15] = 0.0f;
        this.nearPlane = f5;
        this.farPlane = f6;
    }

    private boolean unproject(float f, float f2, float f3, Vector3 vector3) {
        Preconditions.checkNotNull(vector3, "Parameter \"dest\" was null.");
        Matrix matrix = new Matrix();
        Matrix.multiply(this.projectionMatrix, this.viewMatrix, matrix);
        Matrix.invert(matrix, matrix);
        float viewHeight = (float) getViewHeight();
        f = ((f / ((float) getViewWidth())) * 2.0f) - 1.0f;
        f2 = (((viewHeight - f2) / viewHeight) * 2.0f) - 1.0f;
        f3 = (f3 * 2.0f) - 1.0f;
        vector3.f120x = (((matrix.data[0] * f) + (matrix.data[4] * f2)) + (matrix.data[8] * f3)) + (matrix.data[12] * 1.0f);
        vector3.f121y = (((matrix.data[1] * f) + (matrix.data[5] * f2)) + (matrix.data[9] * f3)) + (matrix.data[13] * 1.0f);
        vector3.f122z = (((matrix.data[2] * f) + (matrix.data[6] * f2)) + (matrix.data[10] * f3)) + (matrix.data[14] * 1.0f);
        f = (((f * matrix.data[3]) + (f2 * matrix.data[7])) + (f3 * matrix.data[11])) + (matrix.data[15] * 1.0f);
        if (MathHelper.almostEqualRelativeAndAbs(f, 0.0f)) {
            vector3.set(0.0f, 0.0f, 0.0f);
            return false;
        }
        vector3.set(vector3.scaled(1.0f / f));
        return true;
    }

    public float getFarClipPlane() {
        return this.farPlane;
    }

    public float getNearClipPlane() {
        return this.nearPlane;
    }

    public Matrix getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix getViewMatrix() {
        return this.viewMatrix;
    }

    final /* synthetic */ void lambda$new$0$Camera(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        refreshProjectionMatrix();
    }

    Ray motionEventToRay(MotionEvent motionEvent) {
        Preconditions.checkNotNull(motionEvent, "Parameter \"motionEvent\" was null.");
        int actionIndex = motionEvent.getActionIndex();
        return screenPointToRay(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
    }

    public Ray screenPointToRay(float f, float f2) {
        Vector3 vector3 = new Vector3();
        Vector3 vector32 = new Vector3();
        unproject(f, f2, 0.0f, vector3);
        unproject(f, f2, 1.0f, vector32);
        return new Ray(vector3, Vector3.subtract(vector32, vector3));
    }

    public void setFarClipPlane(float f) {
        this.farPlane = f;
        if (!this.isArCamera) {
            refreshProjectionMatrix();
        }
    }

    public void setLocalPosition(Vector3 vector3) {
        if (this.isArCamera) {
            throw new UnsupportedOperationException("Camera's position cannot be changed, it is controller by the ARCore camera pose.");
        }
        super.setLocalPosition(vector3);
        Matrix.invert(getWorldModelMatrix(), this.viewMatrix);
    }

    public void setLocalRotation(Quaternion quaternion) {
        if (this.isArCamera) {
            throw new UnsupportedOperationException("Camera's rotation cannot be changed, it is controller by the ARCore camera pose.");
        }
        super.setLocalRotation(quaternion);
        Matrix.invert(getWorldModelMatrix(), this.viewMatrix);
    }

    public void setNearClipPlane(float f) {
        this.nearPlane = f;
        if (!this.isArCamera) {
            refreshProjectionMatrix();
        }
    }

    public void setParent(NodeParent nodeParent) {
        throw new UnsupportedOperationException("Camera's parent cannot be changed, it is always the scene.");
    }

    public void setProjectionMatrix(Matrix matrix) {
        this.projectionMatrix.set(matrix.data);
    }

    public void setWorldPosition(Vector3 vector3) {
        if (this.isArCamera) {
            throw new UnsupportedOperationException("Camera's position cannot be changed, it is controller by the ARCore camera pose.");
        }
        super.setWorldPosition(vector3);
        Matrix.invert(getWorldModelMatrix(), this.viewMatrix);
    }

    public void setWorldRotation(Quaternion quaternion) {
        if (this.isArCamera) {
            throw new UnsupportedOperationException("Camera's rotation cannot be changed, it is controller by the ARCore camera pose.");
        }
        super.setWorldRotation(quaternion);
        Matrix.invert(getWorldModelMatrix(), this.viewMatrix);
    }

    public void updateTrackedPose(com.google.ar.core.Camera camera) {
        Preconditions.checkNotNull(camera, "Parameter \"camera\" was null.");
        camera.getProjectionMatrix(this.projectionMatrix.data, 0, this.nearPlane, this.farPlane);
        camera.getViewMatrix(this.viewMatrix.data, 0);
        Pose displayOrientedPose = camera.getDisplayOrientedPose();
        Vector3 a = C0389a.m86a(displayOrientedPose);
        Quaternion b = C0389a.m89b(displayOrientedPose);
        super.setWorldPosition(a);
        super.setWorldRotation(b);
    }

    public Vector3 worldToScreenPoint(Vector3 vector3) {
        Matrix matrix = new Matrix();
        Matrix.multiply(this.projectionMatrix, this.viewMatrix, matrix);
        int viewWidth = getViewWidth();
        int viewHeight = getViewHeight();
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        Vector3 vector32 = new Vector3();
        vector32.f120x = (((matrix.data[0] * f) + (matrix.data[4] * f2)) + (matrix.data[8] * f3)) + (matrix.data[12] * 1.0f);
        vector32.f121y = (((matrix.data[1] * f) + (matrix.data[5] * f2)) + (matrix.data[9] * f3)) + (matrix.data[13] * 1.0f);
        f = (((f * matrix.data[3]) + (f2 * matrix.data[7])) + (f3 * matrix.data[11])) + (matrix.data[15] * 1.0f);
        vector32.f120x = ((vector32.f120x / f) + 1.0f) * 0.5f;
        vector32.f121y = ((vector32.f121y / f) + 1.0f) * 0.5f;
        vector32.f120x *= (float) viewWidth;
        float f4 = (float) viewHeight;
        vector32.f121y *= f4;
        vector32.f121y = f4 - vector32.f121y;
        return vector32;
    }
}
