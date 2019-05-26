package com.google.ar.sceneform;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.WindowManager;
import com.google.ar.core.Camera;
import com.google.ar.core.Config;
import com.google.ar.core.Config.UpdateMode;
import com.google.ar.core.Frame;
import com.google.ar.core.LightEstimate;
import com.google.ar.core.LightEstimate.State;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.rendering.CameraStream;
import com.google.ar.sceneform.rendering.GLHelper;
import com.google.ar.sceneform.rendering.PlaneRenderer;
import com.google.ar.sceneform.rendering.Renderer;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.ArCoreVersion;
import com.google.ar.sceneform.utilities.Preconditions;

public class ArSceneView extends SceneView {
    private static final float DEFAULT_LIGHT_ESTIMATE = 1.0f;
    private static final String REPORTED_ENGINE_TYPE = "Sceneform";
    private static final String REPORTED_ENGINE_VERSION = "1.3";
    private static final String TAG = ArSceneView.class.getSimpleName();
    private Config cachedConfig;
    private CameraStream cameraStream;
    private int cameraTextureId;
    private Frame currentFrame;
    private Display display;
    private float lastValidLightEstimate = DEFAULT_LIGHT_ESTIMATE;
    private boolean lightEstimationEnabled = true;
    private int minArCoreVersionCode;
    private PlaneRenderer planeRenderer;
    private Session session;

    public ArSceneView(Context context) {
        super(context);
        initializeAr();
    }

    public ArSceneView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeAr();
    }

    private void ensureUpdateMode() {
        if (this.session != null && this.minArCoreVersionCode >= ArCoreVersion.VERSION_CODE_1_3) {
            if (this.cachedConfig == null) {
                this.cachedConfig = this.session.getConfig();
            } else {
                this.session.getConfig(this.cachedConfig);
            }
            UpdateMode updateMode = this.cachedConfig.getUpdateMode();
            if (updateMode != UpdateMode.LATEST_CAMERA_IMAGE) {
                String valueOf = String.valueOf(updateMode);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 123);
                stringBuilder.append("Invalid ARCore UpdateMode ");
                stringBuilder.append(valueOf);
                stringBuilder.append(", Sceneform requires that the ARCore session is configured to the UpdateMode LATEST_CAMERA_IMAGE.");
                throw new RuntimeException(stringBuilder.toString());
            }
        }
    }

    private void initializeAr() {
        this.minArCoreVersionCode = ArCoreVersion.getMinArCoreVersionCode(getContext());
        this.display = ((WindowManager) getContext().getSystemService(WindowManager.class)).getDefaultDisplay();
        initializePlaneRenderer();
        initializeCameraStream();
    }

    private void initializeCameraStream() {
        this.cameraTextureId = GLHelper.createCameraTexture();
        Renderer renderer = (Renderer) Preconditions.checkNotNull(getRenderer());
        this.cameraStream = new CameraStream(this.cameraTextureId, renderer);
        renderer.initializeScreenshot(this.cameraStream);
    }

    private void initializePlaneRenderer() {
        this.planeRenderer = new PlaneRenderer((Renderer) Preconditions.checkNotNull(getRenderer()));
    }

    private static native void nativeReportEngineType(Session session, String str, String str2);

    private void reportEngineType() {
        try {
            System.loadLibrary("arsceneview_jni");
            if (this.session != null) {
                nativeReportEngineType(this.session, REPORTED_ENGINE_TYPE, REPORTED_ENGINE_VERSION);
            } else {
                Log.e(TAG, "Session is null, reportEngineType failed.");
            }
        } catch (Throwable th) {
            Log.e(TAG, "Error loading libarsceneview_jni.so", th);
        }
    }

    private boolean shouldRecalculateCameraUvs(Frame frame) {
        return frame.hasDisplayGeometryChanged();
    }

    private void updateLightEstimate(Frame frame) {
        Preconditions.checkNotNull(frame, "Parameter \"frame\" was null.");
        if (this.lightEstimationEnabled) {
            LightEstimate lightEstimate = frame.getLightEstimate();
            float f = this.lastValidLightEstimate;
            if (lightEstimate.getState() == State.VALID) {
                f = Math.max(lightEstimate.getPixelIntensity(), 0.0f);
            }
            getScene().setLightEstimate(f);
            this.lastValidLightEstimate = f;
        }
    }

    public void destroy() {
        this.cameraStream.dispose();
        super.destroy();
    }

    public Frame getArFrame() {
        return this.currentFrame;
    }

    public PlaneRenderer getPlaneRenderer() {
        return this.planeRenderer;
    }

    public Session getSession() {
        return this.session;
    }

    public boolean isLightEstimationEnabled() {
        return this.lightEstimationEnabled;
    }

    protected boolean onBeginFrame() {
        Session session = this.session;
        if (session == null) {
            return false;
        }
        ensureUpdateMode();
        boolean z = true;
        try {
            Frame update = session.update();
            if (update == null) {
                return false;
            }
            if (!this.cameraStream.isSetup()) {
                this.cameraStream.setup(update);
            }
            if (shouldRecalculateCameraUvs(update)) {
                this.cameraStream.recalculateCameraUvs(update);
            }
            if (this.currentFrame != null && this.currentFrame.getTimestamp() == update.getTimestamp()) {
                z = false;
            }
            this.currentFrame = update;
            Camera camera = this.currentFrame.getCamera();
            if (camera == null) {
                return false;
            }
            if (z) {
                getScene().getCamera().updateTrackedPose(camera);
                update = this.currentFrame;
                if (update != null) {
                    updateLightEstimate(update);
                    this.planeRenderer.update(update, getWidth(), getHeight());
                }
            }
            return z;
        } catch (Throwable e) {
            Log.w(TAG, "Exception updating ARCore session", e);
            return false;
        }
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.session != null) {
            this.session.setDisplayGeometry(this.display.getRotation(), i3 - i, i4 - i2);
        }
    }

    public void pause() {
        Choreographer.getInstance().removeFrameCallback(this);
        super.pause();
        if (this.session != null) {
            this.session.pause();
        }
    }

    public void resume() throws CameraNotAvailableException {
        Session session = this.session;
        if (session != null) {
            reportEngineType();
            session.resume();
            super.resume();
        }
    }

    public void setLightEstimationEnabled(boolean z) {
        this.lightEstimationEnabled = z;
        if (!this.lightEstimationEnabled) {
            getScene().setLightEstimate(DEFAULT_LIGHT_ESTIMATE);
            this.lastValidLightEstimate = DEFAULT_LIGHT_ESTIMATE;
        }
    }

    public void setupSession(Session session) {
        if (this.session != null) {
            Log.w(TAG, "The session has already been setup, cannot set it up again.");
            return;
        }
        AndroidPreconditions.checkMinAndroidApiLevel();
        this.session = session;
        Renderer renderer = (Renderer) Preconditions.checkNotNull(getRenderer());
        int desiredWidth = renderer.getDesiredWidth();
        int desiredHeight = renderer.getDesiredHeight();
        if (!(desiredWidth == 0 || desiredHeight == 0)) {
            session.setDisplayGeometry(this.display.getRotation(), desiredWidth, desiredHeight);
        }
        session.setCameraTextureName(this.cameraTextureId);
        Choreographer.getInstance().postFrameCallback(this);
    }
}
