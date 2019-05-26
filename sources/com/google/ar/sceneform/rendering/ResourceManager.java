package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.ar.sceneform.resources.ResourceRegistry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ResourceManager {
    @Nullable
    private static ResourceManager instance = null;
    private final AssetLoader assetLoader = new AssetLoader(this.loadExecutor);
    private final ExecutorService loadExecutor = Executors.newSingleThreadExecutor();
    private final ResourceRegistry<Material> materialRegistry = new ResourceRegistry();
    private final ResourceRegistry<ModelRenderable> modelRenderableRegistry = new ResourceRegistry();
    private final ResourceRegistry<Texture> textureRegistry = new ResourceRegistry();
    private final ResourceRegistry<ViewRenderable> viewRenderableRegistry = new ResourceRegistry();

    AssetLoader getAssetLoader() {
        return this.assetLoader;
    }

    ResourceRegistry<Texture> getTextureRegistry() {
        return this.textureRegistry;
    }

    ResourceRegistry<Material> getMaterialRegistry() {
        return this.materialRegistry;
    }

    ResourceRegistry<ModelRenderable> getModelRenderableRegistry() {
        return this.modelRenderableRegistry;
    }

    ResourceRegistry<ViewRenderable> getViewRenderableRegistry() {
        return this.viewRenderableRegistry;
    }

    static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    private ResourceManager() {
    }
}
