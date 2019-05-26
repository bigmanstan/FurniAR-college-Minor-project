package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Surface;
import android.view.SurfaceView;
import com.google.android.filament.Camera;
import com.google.android.filament.Engine;
import com.google.android.filament.IndirectLight;
import com.google.android.filament.Scene;
import com.google.android.filament.SwapChain;
import com.google.android.filament.TransformManager;
import com.google.android.filament.View;
import com.google.android.filament.View.DynamicResolutionOptions;
import com.google.android.filament.Viewport;
import com.google.android.filament.android.UiHelper;
import com.google.android.filament.android.UiHelper.ContextErrorPolicy;
import com.google.android.filament.android.UiHelper.RendererCallback;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class Renderer implements RendererCallback {
    private static final float CAMERA_APERATURE = 4.0f;
    private static final float CAMERA_ISO = 320.0f;
    private static final float CAMERA_SHUTTER_SPEED = 0.033333335f;
    private static final Color DEFAULT_CLEAR_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    private static final int MAXIMUM_RESOLUTION = 1080;
    @Deprecated
    public static final int SCREENSHOT_BYTES_PER_COLOR = 4;
    private Camera camera;
    private final double[] cameraProjectionMatrix = new double[16];
    @Nullable
    private CameraProvider cameraProvider;
    private View emptyView;
    private UiHelper filamentHelper;
    private final ArrayList<LightInstance> lightInstances = new ArrayList();
    private boolean recreateSwapChain;
    private final ArrayList<RenderableInstance> renderableInstances = new ArrayList();
    private com.google.android.filament.Renderer renderer;
    private Scene scene;
    @Nullable
    private Screenshot screenshot = null;
    private Surface surface;
    private final SurfaceView surfaceView;
    @Nullable
    private SwapChain swapChain;
    private View view;
    private final ViewAttachmentManager viewAttachmentManager;

    @Deprecated
    public interface OnScreenshotListener {
        void onScreenshotResult(ByteBuffer byteBuffer, int i, int i2);
    }

    @Deprecated
    public enum ScreenshotType {
        DEFAULT,
        CAMERA_STREAM
    }

    @RequiresApi(api = 24)
    public Renderer(SurfaceView view) {
        Preconditions.checkNotNull(view, "Parameter \"view\" was null.");
        AndroidPreconditions.checkMinAndroidApiLevel();
        this.surfaceView = view;
        this.viewAttachmentManager = new ViewAttachmentManager(getContext(), view);
        initialize();
    }

    public SurfaceView getSurfaceView() {
        return this.surfaceView;
    }

    public void setClearColor(Color color) {
        this.view.setClearColor(color.f126r, color.f125g, color.f124b, color.f123a);
    }

    public void setDefaultClearColor() {
        setClearColor(DEFAULT_CLEAR_COLOR);
    }

    public void setCameraProvider(@Nullable CameraProvider cameraProvider) {
        this.cameraProvider = cameraProvider;
    }

    public void onPause() {
        this.viewAttachmentManager.onPause();
    }

    public void onResume() {
        this.viewAttachmentManager.onResume();
    }

    public void render(boolean debugEnabled) {
        synchronized (this) {
            int i = 0;
            if (this.recreateSwapChain) {
                Engine engine = EngineInstance.getEngine();
                if (this.swapChain != null) {
                    engine.destroySwapChain(this.swapChain);
                }
                this.swapChain = engine.createSwapChain(this.surface);
                this.recreateSwapChain = false;
            }
        }
        ResourceManager.getInstance().getAssetLoader().createAssets();
        if (this.filamentHelper.isReadyToRender()) {
            updateInstances();
            updateLights();
            CameraProvider cameraProvider = this.cameraProvider;
            if (cameraProvider != null) {
                float[] projectionMatrixData = cameraProvider.getProjectionMatrix().data;
                while (i < 16) {
                    this.cameraProjectionMatrix[i] = (double) projectionMatrixData[i];
                    i++;
                }
                this.camera.setModelMatrix(cameraProvider.getWorldModelMatrix().data);
                this.camera.setCustomProjection(this.cameraProjectionMatrix, (double) cameraProvider.getNearClipPlane(), (double) cameraProvider.getFarClipPlane());
                SwapChain swapChainLocal = this.swapChain;
                if (swapChainLocal == null) {
                    throw new AssertionError("Internal Error: Failed to get swap chain");
                } else if (this.renderer.beginFrame(swapChainLocal)) {
                    if (cameraProvider.isActive()) {
                        this.renderer.render(this.view);
                    } else {
                        this.renderer.render(this.emptyView);
                    }
                    if (this.screenshot != null && this.screenshot.isDirty()) {
                        this.screenshot.capture();
                    }
                    this.renderer.endFrame();
                }
            }
        }
    }

    public void dispose() {
        this.filamentHelper.detach();
        Engine engine = EngineInstance.getEngine();
        engine.destroyRenderer(this.renderer);
        engine.destroyView(this.view);
    }

    public Context getContext() {
        return getSurfaceView().getContext();
    }

    @Deprecated
    public void initializeScreenshot(CameraStream cameraStream) {
        this.screenshot = new Screenshot(this.renderer, cameraStream, getSurfaceView());
    }

    @Deprecated
    public boolean captureScreenshot(ScreenshotType type, OnScreenshotListener listener) {
        if (this.screenshot != null) {
            return this.screenshot.queueCapture(type, listener);
        }
        return false;
    }

    @Deprecated
    public boolean captureScreenshot(OnScreenshotListener listener) {
        return captureScreenshot(ScreenshotType.DEFAULT, listener);
    }

    public void setLightProbe(LightProbe lightProbe) {
        if (lightProbe != null) {
            IndirectLight filamentData = lightProbe.getData();
            if (filamentData != null) {
                this.scene.setIndirectLight(filamentData);
                return;
            }
            return;
        }
        throw new AssertionError("Passed in an invalid light probe.");
    }

    public void setDesiredSize(int width, int height) {
        int minor = Math.min(width, height);
        int major = Math.max(width, height);
        if (minor > MAXIMUM_RESOLUTION) {
            major = (major * MAXIMUM_RESOLUTION) / minor;
            minor = MAXIMUM_RESOLUTION;
        }
        if (width < height) {
            int t = minor;
            minor = major;
            major = t;
        }
        this.filamentHelper.setDesiredSize(major, minor);
    }

    public int getDesiredWidth() {
        return this.filamentHelper.getDesiredWidth();
    }

    public int getDesiredHeight() {
        return this.filamentHelper.getDesiredHeight();
    }

    public void onNativeWindowChanged(Surface surface) {
        synchronized (this) {
            this.surface = surface;
            this.recreateSwapChain = true;
        }
    }

    public void onDetachedFromSurface() {
        SwapChain swapChainLocal = this.swapChain;
        if (swapChainLocal != null) {
            Engine engine = EngineInstance.getEngine();
            engine.destroySwapChain(swapChainLocal);
            engine.flushAndWait();
            this.swapChain = null;
        }
    }

    public void onResized(int width, int height) {
        this.view.setViewport(new Viewport(0, 0, width, height));
        this.emptyView.setViewport(new Viewport(0, 0, width, height));
    }

    void addLight(LightInstance instance) {
        this.scene.addEntity(instance.getEntity());
        this.lightInstances.add(instance);
    }

    void removeLight(LightInstance instance) {
        this.scene.remove(instance.getEntity());
        this.lightInstances.remove(instance);
    }

    void addInstance(RenderableInstance instance) {
        this.scene.addEntity(instance.entity);
        this.renderableInstances.add(instance);
    }

    void removeInstance(RenderableInstance instance) {
        this.scene.remove(instance.entity);
        this.renderableInstances.remove(instance);
    }

    Scene getFilamentScene() {
        return this.scene;
    }

    ViewAttachmentManager getViewAttachmentManager() {
        return this.viewAttachmentManager;
    }

    private void initialize() {
        SurfaceView surfaceView = getSurfaceView();
        this.filamentHelper = new UiHelper(ContextErrorPolicy.DONT_CHECK);
        this.filamentHelper.setRenderCallback(this);
        this.filamentHelper.attachTo(surfaceView);
        Engine engine = EngineInstance.getEngine();
        this.renderer = engine.createRenderer();
        this.scene = engine.createScene();
        this.view = engine.createView();
        this.emptyView = engine.createView();
        this.camera = engine.createCamera();
        this.camera.setExposure(CAMERA_APERATURE, CAMERA_SHUTTER_SPEED, CAMERA_ISO);
        setDefaultClearColor();
        this.view.setCamera(this.camera);
        this.view.setScene(this.scene);
        DynamicResolutionOptions options = new DynamicResolutionOptions();
        options.enabled = true;
        this.view.setDynamicResolutionOptions(options);
        this.emptyView.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.emptyView.setCamera(engine.createCamera());
        this.emptyView.setScene(engine.createScene());
    }

    private void updateInstances() {
        TransformManager transformManager = EngineInstance.getEngine().getTransformManager();
        transformManager.openLocalTransformTransaction();
        Iterator it = this.renderableInstances.iterator();
        while (it.hasNext()) {
            RenderableInstance renderableInstance = (RenderableInstance) it.next();
            renderableInstance.prepareForDraw();
            renderableInstance.setModelMatrix(transformManager, renderableInstance.getWorldModelMatrix().data);
        }
        transformManager.commitLocalTransformTransaction();
    }

    private void updateLights() {
        Iterator it = this.lightInstances.iterator();
        while (it.hasNext()) {
            ((LightInstance) it.next()).updateTransform();
        }
    }
}
