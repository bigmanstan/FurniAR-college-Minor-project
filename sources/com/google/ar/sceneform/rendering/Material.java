package com.google.ar.sceneform.rendering;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.google.android.filament.Engine;
import com.google.android.filament.MaterialInstance;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Texture.Sampler;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import com.google.ar.sceneform.utilities.LoadHelper;
import com.google.ar.sceneform.utilities.Preconditions;
import com.google.ar.sceneform.utilities.RendercoreBufferUtils;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RequiresApi(api = 24)
public class Material {
    private static final String TAG = Material.class.getSimpleName();
    @Nullable
    private MaterialInstance filamentMaterialInstance;
    @Nullable
    private MaterialInternalData materialData;
    private final MaterialParameters materialParameters;

    public static final class Builder {
        @Nullable
        private Context context;
        @Nullable
        private Callable<InputStream> inputStreamCreator;
        @Nullable
        private Object registryId;
        @Nullable
        ByteBuffer sourceBuffer;
        @Nullable
        Uri sourceUri;

        private Builder() {
            this.context = null;
        }

        public Builder setSource(ByteBuffer materialBuffer) {
            Preconditions.checkNotNull(materialBuffer, "Parameter \"materialBuffer\" was null.");
            this.inputStreamCreator = null;
            this.sourceBuffer = materialBuffer;
            this.sourceUri = null;
            return this;
        }

        public Builder setSource(Context context, Uri sourceUri) {
            Preconditions.checkNotNull(sourceUri, "Parameter \"sourceUri\" was null.");
            this.registryId = sourceUri;
            this.inputStreamCreator = LoadHelper.fromUri(context, sourceUri);
            this.sourceUri = sourceUri;
            this.context = context;
            this.sourceBuffer = null;
            return this;
        }

        public Builder setSource(Context context, int resource) {
            Uri uri = LoadHelper.resourceToUri(context, resource);
            this.sourceUri = uri;
            this.registryId = uri;
            this.inputStreamCreator = LoadHelper.fromUri(context, uri);
            this.context = context;
            this.sourceBuffer = null;
            return this;
        }

        public Builder setSource(Callable<InputStream> inputStreamCreator) {
            Preconditions.checkNotNull(inputStreamCreator, "Parameter \"sourceInputStreamCallable\" was null.");
            this.inputStreamCreator = inputStreamCreator;
            this.sourceUri = null;
            this.sourceBuffer = null;
            return this;
        }

        public Builder setRegistryId(Object registryId) {
            this.registryId = registryId;
            return this;
        }

        public CompletableFuture<Material> build() {
            try {
                checkPreconditions();
                Object registryId = this.registryId;
                if (registryId != null) {
                    CompletableFuture<Material> materialFuture = ResourceManager.getInstance().getMaterialRegistry().get(registryId);
                    if (materialFuture != null) {
                        return materialFuture.thenApply(-$$Lambda$Material$Builder$6wTtl6zcVA7p7AMMlFGGM_tNNRw.INSTANCE);
                    }
                }
                CompletableFuture<Material> result;
                if (this.sourceBuffer != null) {
                    Material material = new Material(new MaterialInternalData(createFilamentMaterial(this.sourceBuffer)));
                    if (registryId != null) {
                        ResourceManager.getInstance().getMaterialRegistry().register(registryId, CompletableFuture.completedFuture(material));
                    }
                    result = CompletableFuture.completedFuture(material.makeCopy());
                    String access$100 = Material.TAG;
                    String valueOf = String.valueOf(registryId);
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 37);
                    stringBuilder.append("Unable to load Material registryId='");
                    stringBuilder.append(valueOf);
                    stringBuilder.append("'");
                    FutureHelper.logOnException(access$100, result, stringBuilder.toString());
                    return result;
                }
                Uri sourceUri = this.sourceUri;
                Callable<InputStream> inputStreamCreator = this.inputStreamCreator;
                if (!(sourceUri == null || this.context == null)) {
                    inputStreamCreator = LoadHelper.fromUri(this.context, sourceUri);
                }
                if (inputStreamCreator == null) {
                    result = new CompletableFuture();
                    result.completeExceptionally(new AssertionError("Input Stream Creator is null."));
                    return result;
                }
                CompletableFuture<Material> result2 = CompletableFuture.supplyAsync(new -$$Lambda$Material$Builder$x6YlrhBBfMt-CGpxe3JktNaVuWc(inputStreamCreator), ThreadPools.getThreadPoolExecutor()).thenApplyAsync(new -$$Lambda$Material$Builder$R3Te98lOXUIf_IWegz9bzaqK8FI(), ThreadPools.getMainExecutor());
                if (registryId != null) {
                    ResourceManager.getInstance().getMaterialRegistry().register(registryId, result2);
                }
                return result2.thenApply(-$$Lambda$Material$Builder$a99CGrLmte7XpkNmOLq94O7XGVk.INSTANCE);
            } catch (Throwable failedPrecondition) {
                CompletableFuture<Material> result3 = new CompletableFuture();
                result3.completeExceptionally(failedPrecondition);
                String access$1002 = Material.TAG;
                String valueOf2 = String.valueOf(this.registryId);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf2).length() + 37);
                stringBuilder2.append("Unable to load Material registryId='");
                stringBuilder2.append(valueOf2);
                stringBuilder2.append("'");
                FutureHelper.logOnException(access$1002, result3, stringBuilder2.toString());
                return result3;
            }
        }

        static /* synthetic */ ByteBuffer lambda$build$1(Callable inputStreamCallable) {
            InputStream inputStream;
            try {
                inputStream = (InputStream) inputStreamCallable.call();
                ByteBuffer byteBuffer = RendercoreBufferUtils.readStream(inputStream);
                if (inputStream != null) {
                    inputStream.close();
                }
                ByteBuffer inputStream2 = byteBuffer;
                if (inputStream2 != null) {
                    return inputStream2;
                }
                throw new IllegalStateException("Unable to read data from input stream.");
            } catch (Exception e) {
                throw new CompletionException(e);
            } catch (Throwable th) {
                r1.addSuppressed(th);
            }
        }

        private void checkPreconditions() {
            AndroidPreconditions.checkUiThread();
            if (!hasSource().booleanValue()) {
                throw new AssertionError("Material must have a source.");
            }
        }

        private Boolean hasSource() {
            boolean z;
            if (this.sourceUri == null && this.inputStreamCreator == null) {
                if (this.sourceBuffer == null) {
                    z = false;
                    return Boolean.valueOf(z);
                }
            }
            z = true;
            return Boolean.valueOf(z);
        }

        private com.google.android.filament.Material createFilamentMaterial(ByteBuffer sourceBuffer) {
            try {
                return new com.google.android.filament.Material.Builder().payload(sourceBuffer, sourceBuffer.limit()).build(EngineInstance.getEngine());
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to create material from source byte buffer.");
            }
        }
    }

    public Material makeCopy() {
        return new Material(this);
    }

    public void setBoolean(String name, boolean x) {
        this.materialParameters.setBoolean(name, x);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setBoolean2(String name, boolean x, boolean y) {
        this.materialParameters.setBoolean2(name, x, y);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setBoolean3(String name, boolean x, boolean y, boolean z) {
        this.materialParameters.setBoolean3(name, x, y, z);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setBoolean4(String name, boolean x, boolean y, boolean z, boolean w) {
        this.materialParameters.setBoolean4(name, x, y, z, w);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat(String name, float x) {
        this.materialParameters.setFloat(name, x);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat2(String name, float x, float y) {
        this.materialParameters.setFloat2(name, x, y);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat3(String name, float x, float y, float z) {
        this.materialParameters.setFloat3(name, x, y, z);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat3(String name, Vector3 value) {
        this.materialParameters.setFloat3(name, value);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat3(String name, Color color) {
        this.materialParameters.setFloat3(name, color.f126r, color.f125g, color.f124b);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat4(String name, float x, float y, float z, float w) {
        this.materialParameters.setFloat4(name, x, y, z, w);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setFloat4(String name, Color color) {
        this.materialParameters.setFloat4(name, color.f126r, color.f125g, color.f124b, color.f123a);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setInt(String name, int x) {
        this.materialParameters.setInt(name, x);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setInt2(String name, int x, int y) {
        this.materialParameters.setInt2(name, x, y);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setInt3(String name, int x, int y, int z) {
        this.materialParameters.setInt3(name, x, y, z);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setInt4(String name, int x, int y, int z, int w) {
        this.materialParameters.setInt4(name, x, y, z, w);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setTexture(String name, Texture texture) {
        this.materialParameters.setTexture(name, texture, texture.getSampler());
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    public void setExternalTexture(String name, ExternalTexture externalTexture) {
        this.materialParameters.setExternalTexture(name, externalTexture);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    void setTexture(String name, Texture texture, Sampler sampler) {
        this.materialParameters.setTexture(name, texture, sampler);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    static Builder builder() {
        AndroidPreconditions.checkMinAndroidApiLevel();
        return new Builder();
    }

    void copyMaterialParameters(MaterialParameters materialParameters) {
        this.materialParameters.copyFrom(materialParameters);
        if (this.filamentMaterialInstance != null) {
            this.materialParameters.applyTo(this.filamentMaterialInstance);
        }
    }

    void prepareToDraw() {
        MaterialInstance filamentMaterialInstance = this.filamentMaterialInstance;
        if (filamentMaterialInstance != null && this.materialParameters.isDirty()) {
            this.materialParameters.applyTo(filamentMaterialInstance);
        }
    }

    MaterialInstance getFilamentMaterialInstance() {
        if (this.filamentMaterialInstance != null) {
            return this.filamentMaterialInstance;
        }
        throw new AssertionError("Filament Material Instance is null.");
    }

    private Material(MaterialInternalData materialData) {
        this.materialParameters = new MaterialParameters();
        this.materialData = materialData;
        materialData.retain();
        this.filamentMaterialInstance = materialData.getFilamentMaterial().createInstance();
    }

    private Material(Material other) {
        this(other.materialData);
        copyMaterialParameters(other.materialParameters);
    }

    protected void finalize() throws Throwable {
        try {
            ThreadPools.getMainExecutor().execute(new -$$Lambda$Material$-NObk14nNhAzqCT5EojDc38cs_k());
        } catch (Exception e) {
            Log.e(TAG, "Error while Finalizing Material.", e);
        } catch (Throwable th) {
            super.finalize();
        }
        super.finalize();
    }

    void dispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        MaterialInstance materialInstance = this.filamentMaterialInstance;
        this.filamentMaterialInstance = null;
        if (!(materialInstance == null || engine == null || !engine.isValid())) {
            engine.destroyMaterialInstance(materialInstance);
        }
        if (this.materialData != null) {
            this.materialData.release();
            this.materialData = null;
        }
    }
}
