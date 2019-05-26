package com.google.android.filament;

import android.support.annotation.NonNull;
import com.google.android.filament.Fence.Mode;
import com.google.android.filament.Fence.Type;

public class Engine {
    @NonNull
    private final LightManager mLightManager;
    private long mNativeObject;
    @NonNull
    private final RenderableManager mRenderableManager;
    @NonNull
    private final TransformManager mTransformManager;

    private static native long nCreateCamera(long j);

    private static native long nCreateCameraWithEntity(long j, int i);

    private static native long nCreateEngine(long j);

    private static native long nCreateFence(long j, int i);

    private static native long nCreateRenderer(long j);

    private static native long nCreateScene(long j);

    private static native long nCreateSwapChain(long j, Object obj, long j2);

    private static native long nCreateSwapChainFromRawPointer(long j, long j2, long j3);

    private static native long nCreateView(long j);

    private static native void nDestroyCamera(long j, long j2);

    private static native void nDestroyEngine(long j);

    private static native void nDestroyFence(long j, long j2);

    private static native void nDestroyIndexBuffer(long j, long j2);

    private static native void nDestroyIndirectLight(long j, long j2);

    private static native void nDestroyMaterial(long j, long j2);

    private static native void nDestroyMaterialInstance(long j, long j2);

    private static native void nDestroyRenderer(long j, long j2);

    private static native void nDestroyScene(long j, long j2);

    private static native void nDestroySkybox(long j, long j2);

    private static native void nDestroyStream(long j, long j2);

    private static native void nDestroySwapChain(long j, long j2);

    private static native void nDestroyTexture(long j, long j2);

    private static native void nDestroyVertexBuffer(long j, long j2);

    private static native void nDestroyView(long j, long j2);

    private static native long nGetLightManager(long j);

    private static native long nGetRenderableManager(long j);

    private static native long nGetTransformManager(long j);

    private Engine(long nativeEngine) {
        this.mNativeObject = nativeEngine;
        this.mTransformManager = new TransformManager(nGetTransformManager(nativeEngine));
        this.mLightManager = new LightManager(nGetLightManager(nativeEngine));
        this.mRenderableManager = new RenderableManager(nGetRenderableManager(nativeEngine));
    }

    @NonNull
    public static Engine create() {
        long nativeEngine = nCreateEngine(0);
        if (nativeEngine != 0) {
            return new Engine(nativeEngine);
        }
        throw new IllegalStateException("Couldn't create Engine");
    }

    @NonNull
    public static Engine create(@NonNull Object sharedContext) {
        if (Platform.get().validateSharedContext(sharedContext)) {
            long nativeEngine = nCreateEngine(Platform.get().getSharedContextNativeHandle(sharedContext));
            if (nativeEngine != 0) {
                return new Engine(nativeEngine);
            }
            throw new IllegalStateException("Couldn't create Engine");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid shared context ");
        stringBuilder.append(sharedContext);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean isValid() {
        return this.mNativeObject != 0;
    }

    public void destroy() {
        nDestroyEngine(getNativeObject());
        clearNativeObject();
    }

    public SwapChain createSwapChain(@NonNull Object surface) {
        return createSwapChain(surface, 0);
    }

    public SwapChain createSwapChain(@NonNull Object surface, long flags) {
        if (Platform.get().validateSurface(surface)) {
            long nativeSwapChain = nCreateSwapChain(getNativeObject(), surface, flags);
            if (nativeSwapChain != 0) {
                return new SwapChain(nativeSwapChain, surface);
            }
            throw new IllegalStateException("Couldn't create SwapChain");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid surface ");
        stringBuilder.append(surface);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public SwapChain createSwapChainFromNativeSurface(@NonNull NativeSurface surface, long flags) {
        long nativeSwapChain = nCreateSwapChainFromRawPointer(getNativeObject(), surface.getNativeObject(), flags);
        if (nativeSwapChain != 0) {
            return new SwapChain(nativeSwapChain, surface);
        }
        throw new IllegalStateException("Couldn't create SwapChain");
    }

    public void destroySwapChain(@NonNull SwapChain swapChain) {
        nDestroySwapChain(getNativeObject(), swapChain.getNativeObject());
        swapChain.clearNativeObject();
    }

    @NonNull
    public View createView() {
        long nativeView = nCreateView(getNativeObject());
        if (nativeView != 0) {
            return new View(nativeView);
        }
        throw new IllegalStateException("Couldn't create View");
    }

    public void destroyView(@NonNull View view) {
        nDestroyView(getNativeObject(), view.getNativeObject());
        view.clearNativeObject();
    }

    @NonNull
    public Renderer createRenderer() {
        long nativeRenderer = nCreateRenderer(getNativeObject());
        if (nativeRenderer != 0) {
            return new Renderer(this, nativeRenderer);
        }
        throw new IllegalStateException("Couldn't create Renderer");
    }

    public void destroyRenderer(@NonNull Renderer renderer) {
        nDestroyRenderer(getNativeObject(), renderer.getNativeObject());
        renderer.clearNativeObject();
    }

    @NonNull
    public Camera createCamera() {
        long nativeCamera = nCreateCamera(getNativeObject());
        if (nativeCamera != 0) {
            return new Camera(nativeCamera);
        }
        throw new IllegalStateException("Couldn't create Camera");
    }

    @NonNull
    public Camera createCamera(@Entity int entity) {
        long nativeCamera = nCreateCameraWithEntity(getNativeObject(), entity);
        if (nativeCamera != 0) {
            return new Camera(nativeCamera);
        }
        throw new IllegalStateException("Couldn't create Camera");
    }

    public void destroyCamera(@NonNull Camera camera) {
        nDestroyCamera(getNativeObject(), camera.getNativeObject());
        camera.clearNativeObject();
    }

    @NonNull
    public Scene createScene() {
        long nativeScene = nCreateScene(getNativeObject());
        if (nativeScene != 0) {
            return new Scene(nativeScene);
        }
        throw new IllegalStateException("Couldn't create Scene");
    }

    public void destroyScene(@NonNull Scene scene) {
        nDestroyScene(getNativeObject(), scene.getNativeObject());
        scene.clearNativeObject();
    }

    public void destroyStream(@NonNull Stream stream) {
        nDestroyStream(getNativeObject(), stream.getNativeObject());
        stream.clearNativeObject();
    }

    @NonNull
    public Fence createFence(@NonNull Type type) {
        long nativeFence = nCreateFence(getNativeObject(), type.ordinal());
        if (nativeFence != 0) {
            return new Fence(nativeFence);
        }
        throw new IllegalStateException("Couldn't create Fence");
    }

    public void destroyFence(@NonNull Fence fence) {
        nDestroyFence(getNativeObject(), fence.getNativeObject());
        fence.clearNativeObject();
    }

    public void destroyIndexBuffer(@NonNull IndexBuffer indexBuffer) {
        nDestroyIndexBuffer(getNativeObject(), indexBuffer.getNativeObject());
        indexBuffer.clearNativeObject();
    }

    public void destroyVertexBuffer(@NonNull VertexBuffer vertexBuffer) {
        nDestroyVertexBuffer(getNativeObject(), vertexBuffer.getNativeObject());
        vertexBuffer.clearNativeObject();
    }

    public void destroyIndirectLight(@NonNull IndirectLight ibl) {
        nDestroyIndirectLight(getNativeObject(), ibl.getNativeObject());
        ibl.clearNativeObject();
    }

    public void destroyMaterial(@NonNull Material material) {
        nDestroyMaterial(getNativeObject(), material.getNativeObject());
        material.clearNativeObject();
    }

    public void destroyMaterialInstance(@NonNull MaterialInstance materialInstance) {
        nDestroyMaterialInstance(getNativeObject(), materialInstance.getNativeObject());
        materialInstance.clearNativeObject();
    }

    public void destroySkybox(@NonNull Skybox skybox) {
        nDestroySkybox(getNativeObject(), skybox.getNativeObject());
        skybox.clearNativeObject();
    }

    public void destroyTexture(@NonNull Texture texture) {
        nDestroyTexture(getNativeObject(), texture.getNativeObject());
        texture.clearNativeObject();
    }

    @NonNull
    public TransformManager getTransformManager() {
        return this.mTransformManager;
    }

    @NonNull
    public LightManager getLightManager() {
        return this.mLightManager;
    }

    @NonNull
    public RenderableManager getRenderableManager() {
        return this.mRenderableManager;
    }

    public void flushAndWait() {
        Fence.waitAndDestroy(createFence(Type.HARD), Mode.FLUSH);
    }

    long getNativeObject() {
        if (this.mNativeObject != 0) {
            return this.mNativeObject;
        }
        throw new IllegalStateException("Calling method on destroyed Engine");
    }

    private void clearNativeObject() {
        this.mNativeObject = 0;
    }
}
