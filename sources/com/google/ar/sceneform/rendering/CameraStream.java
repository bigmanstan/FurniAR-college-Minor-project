package com.google.ar.sceneform.rendering;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.EntityManager;
import com.google.android.filament.IndexBuffer;
import com.google.android.filament.IndexBuffer.Builder;
import com.google.android.filament.IndexBuffer.Builder.IndexType;
import com.google.android.filament.RenderableManager;
import com.google.android.filament.RenderableManager.PrimitiveType;
import com.google.android.filament.Scene;
import com.google.android.filament.Texture.PixelBufferDescriptor;
import com.google.android.filament.VertexBuffer;
import com.google.android.filament.VertexBuffer.AttributeType;
import com.google.android.filament.VertexBuffer.VertexAttribute;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.Preconditions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class CameraStream {
    private static final float BLUR_MAX_RADIUS = 24.0f;
    private static final float BLUR_THRESHOLD = 1.0E-5f;
    private static final short[] CAMERA_INDICES = new short[]{(short) 0, (short) 1, (short) 2};
    private static final float[] CAMERA_UVS = new float[]{0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 0.0f};
    private static final float[] CAMERA_VERTICES = new float[]{-1.0f, 1.0f, 1.0f, -1.0f, -3.0f, 1.0f, 3.0f, 1.0f, 1.0f};
    public static final String DEFAULT_MATERIAL = "";
    private static final int FLOAT_SIZE_IN_BYTES = 4;
    private static final String MATERIAL_CAMERA_TEXTURE = "cameraTexture";
    private static final int POSITION_BUFFER_INDEX = 0;
    private static final String TAG = CameraStream.class.getSimpleName();
    private static final int UV_BUFFER_INDEX = 1;
    private static final int VERTEX_COUNT = 3;
    private float blurFactor;
    @Nullable
    private Material blurMaterial;
    @Nullable
    private IndexBuffer cameraIndexBuffer;
    @Nullable
    private Material cameraMaterial;
    private int cameraStreamRenderable;
    @Nullable
    private ExternalTexture cameraTexture;
    private final int cameraTextureId;
    @Nullable
    private FloatBuffer cameraUvCoords;
    @Nullable
    private VertexBuffer cameraVertexBuffer;
    private int height;
    private boolean isSetup;
    private final Scene scene;
    @Nullable
    private FloatBuffer transformedCameraUvCoords;
    private int width;

    public CameraStream(int cameraTextureId, Renderer renderer) {
        this(cameraTextureId, renderer, "");
    }

    public CameraStream(int cameraTextureId, Renderer renderer, @Nullable String materialPath) {
        this.blurFactor = 0.0f;
        this.cameraStreamRenderable = -1;
        this.cameraMaterial = null;
        this.blurMaterial = null;
        this.cameraTexture = null;
        this.cameraIndexBuffer = null;
        this.cameraVertexBuffer = null;
        this.cameraUvCoords = null;
        this.transformedCameraUvCoords = null;
        this.isSetup = false;
        this.scene = renderer.getFilamentScene();
        Engine engine = EngineInstance.getEngine();
        this.cameraTextureId = cameraTextureId;
        ShortBuffer indexBufferData = ShortBuffer.allocate(CAMERA_INDICES.length);
        indexBufferData.put(CAMERA_INDICES);
        this.cameraIndexBuffer = new Builder().indexCount(indexBufferData.capacity()).bufferType(IndexType.USHORT).build(engine);
        indexBufferData.rewind();
        ((IndexBuffer) Preconditions.checkNotNull(this.cameraIndexBuffer)).setBuffer(engine, indexBufferData);
        this.cameraUvCoords = createCameraUVBuffer();
        this.transformedCameraUvCoords = createCameraUVBuffer();
        FloatBuffer vertexBufferData = FloatBuffer.allocate(CAMERA_VERTICES.length);
        vertexBufferData.put(CAMERA_VERTICES);
        this.cameraVertexBuffer = new VertexBuffer.Builder().vertexCount(3).bufferCount(2).attribute(VertexAttribute.POSITION, 0, AttributeType.FLOAT3, 0, (CAMERA_VERTICES.length / 3) * 4).attribute(VertexAttribute.UV0, 1, AttributeType.FLOAT2, 0, (CAMERA_UVS.length / 3) * 4).build(engine);
        vertexBufferData.rewind();
        ((VertexBuffer) Preconditions.checkNotNull(this.cameraVertexBuffer)).setBufferAt(engine, 0, vertexBufferData);
        adjustCameraUvsForOpenGL();
        this.cameraVertexBuffer.setBufferAt(engine, 1, this.transformedCameraUvCoords);
        Material.Builder materialBuilder = Material.builder();
        if (TextUtils.isEmpty(materialPath)) {
            materialBuilder.setSource(renderer.getContext(), C0023R.raw.sceneform_camera_material);
        } else {
            materialBuilder.setSource(renderer.getContext(), Uri.parse(materialPath));
        }
        materialBuilder.build().thenAcceptBoth(Material.builder().setSource(renderer.getContext(), C0023R.raw.sceneform_blur_material).build(), new -$$Lambda$CameraStream$hB5IMXnUbD_txq6ZpBclKaTVSi4()).exceptionally(-$$Lambda$CameraStream$6NeEfGsokSMapjkmD-_mfMJJYUI.INSTANCE);
    }

    public static /* synthetic */ void lambda$new$0(CameraStream cameraStream, Material material, Material blurMaterial) {
        cameraStream.cameraMaterial = material;
        cameraStream.blurMaterial = blurMaterial;
        cameraStream.initializeTextureAndRenderable();
    }

    public boolean captureCameraImage(PixelBufferDescriptor capturePbo) {
        ((ExternalTexture) Preconditions.checkNotNull(this.cameraTexture)).getFilamentStream().readPixels(0, 0, getCameraWidth(), getCameraHeight(), capturePbo);
        return true;
    }

    public int getCameraWidth() {
        return this.width;
    }

    public int getCameraHeight() {
        return this.height;
    }

    public boolean isSetup() {
        return this.isSetup;
    }

    public void setup(Frame frame) {
        if (!isSetup()) {
            updateSize(frame);
            this.isSetup = true;
            initializeTextureAndRenderable();
        }
    }

    public void setCameraBlur(float newBlurFactor) {
        if (newBlurFactor != this.blurFactor) {
            RenderableManager renderableManager = EngineInstance.getEngine().getRenderableManager();
            int renderableInstance = renderableManager.getInstance(this.cameraStreamRenderable);
            if (newBlurFactor < BLUR_THRESHOLD) {
                this.blurFactor = 0.0f;
                if (this.cameraMaterial != null) {
                    renderableManager.setMaterialInstanceAt(renderableInstance, 0, this.cameraMaterial.getFilamentMaterialInstance());
                }
            } else {
                this.blurFactor = Math.min(1.0f, Math.max(0.0f, newBlurFactor));
                if (this.blurMaterial != null) {
                    this.blurMaterial.setFloat2(PlaneRenderer.MATERIAL_SPOTLIGHT_RADIUS, (this.blurFactor * BLUR_MAX_RADIUS) / ((float) this.width), (this.blurFactor * BLUR_MAX_RADIUS) / ((float) this.height));
                    renderableManager.setMaterialInstanceAt(renderableInstance, 0, this.blurMaterial.getFilamentMaterialInstance());
                }
            }
        }
    }

    public void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        if (engine != null && engine.isValid()) {
            if (this.cameraStreamRenderable != -1) {
                this.scene.remove(this.cameraStreamRenderable);
                this.cameraStreamRenderable = -1;
            }
            if (this.cameraMaterial != null) {
                this.cameraMaterial.dispose();
                this.cameraMaterial = null;
            }
            if (this.cameraTexture != null) {
                this.cameraTexture.dispose();
                this.cameraTexture = null;
            }
            if (this.cameraIndexBuffer != null) {
                engine.destroyIndexBuffer(this.cameraIndexBuffer);
                this.cameraIndexBuffer = null;
            }
            if (this.cameraVertexBuffer != null) {
                engine.destroyVertexBuffer(this.cameraVertexBuffer);
                this.cameraVertexBuffer = null;
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$CameraStream$GVO0-8Fdyd6C6SluD_6zPtfcyBI());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Camera Stream.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    public void recalculateCameraUvs(Frame frame) {
        Engine engine = EngineInstance.getEngine();
        FloatBuffer cameraUvCoords = this.cameraUvCoords;
        FloatBuffer transformedCameraUvCoords = this.transformedCameraUvCoords;
        VertexBuffer cameraVertexBuffer = this.cameraVertexBuffer;
        if (cameraVertexBuffer != null && cameraUvCoords != null && transformedCameraUvCoords != null) {
            frame.transformDisplayUvCoords(cameraUvCoords, transformedCameraUvCoords);
            adjustCameraUvsForOpenGL();
            cameraVertexBuffer.setBufferAt(engine, 1, transformedCameraUvCoords);
        }
    }

    private void initializeTextureAndRenderable() {
        if (this.cameraTexture == null && isSetup()) {
            Material cameraMaterial = this.cameraMaterial;
            if (cameraMaterial != null) {
                Material blurMaterial = this.blurMaterial;
                if (blurMaterial != null) {
                    Engine engine = EngineInstance.getEngine();
                    this.cameraTexture = new ExternalTexture(this.cameraTextureId, this.width, this.height);
                    cameraMaterial.setExternalTexture(MATERIAL_CAMERA_TEXTURE, this.cameraTexture);
                    blurMaterial.setExternalTexture(MATERIAL_CAMERA_TEXTURE, this.cameraTexture);
                    this.cameraStreamRenderable = EntityManager.get().create();
                    RenderableManager.Builder builder = new RenderableManager.Builder(1);
                    builder.castShadows(false).receiveShadows(false).culling(false);
                    builder.priority(7);
                    builder.geometry(0, PrimitiveType.TRIANGLES, (VertexBuffer) Preconditions.checkNotNull(this.cameraVertexBuffer), (IndexBuffer) Preconditions.checkNotNull(this.cameraIndexBuffer));
                    builder.material(0, cameraMaterial.getFilamentMaterialInstance());
                    builder.build(engine, this.cameraStreamRenderable);
                    this.scene.addEntity(this.cameraStreamRenderable);
                }
            }
        }
    }

    private void adjustCameraUvsForOpenGL() {
        FloatBuffer transformedCameraUvCoords = this.transformedCameraUvCoords;
        if (transformedCameraUvCoords != null) {
            for (int i = 1; i < 6; i += 2) {
                transformedCameraUvCoords.put(i, 1.0f - transformedCameraUvCoords.get(i));
            }
        }
    }

    private static FloatBuffer createCameraUVBuffer() {
        FloatBuffer buffer = ByteBuffer.allocateDirect(CAMERA_UVS.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(CAMERA_UVS);
        buffer.rewind();
        return buffer;
    }

    private void updateSize(Frame frame) {
        int[] dimensions = frame.getCamera().getTextureIntrinsics().getImageDimensions();
        this.width = dimensions[0];
        this.height = dimensions[1];
    }
}
