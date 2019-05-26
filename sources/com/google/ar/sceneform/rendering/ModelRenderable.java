package com.google.ar.sceneform.rendering;

import android.support.annotation.RequiresApi;
import com.google.ar.sceneform.resources.ResourceRegistry;
import com.google.ar.sceneform.utilities.AndroidPreconditions;
import java.util.concurrent.CompletableFuture;

@RequiresApi(api = 24)
public class ModelRenderable extends Renderable {

    public static final class Builder extends Builder<ModelRenderable, Builder> {
        public /* bridge */ /* synthetic */ CompletableFuture build() {
            return super.build();
        }

        public /* bridge */ /* synthetic */ Boolean hasSource() {
            return super.hasSource();
        }

        protected ModelRenderable makeRenderable() {
            return new ModelRenderable();
        }

        protected Class<ModelRenderable> getRenderableClass() {
            return ModelRenderable.class;
        }

        protected ResourceRegistry<ModelRenderable> getRenderableRegistry() {
            return ResourceManager.getInstance().getModelRenderableRegistry();
        }

        protected Builder getSelf() {
            return this;
        }
    }

    private ModelRenderable(Builder builder) {
        super((Builder) builder);
    }

    private ModelRenderable(ModelRenderable other) {
        super((Renderable) other);
    }

    public ModelRenderable makeCopy() {
        return new ModelRenderable(this);
    }

    public static Builder builder() {
        AndroidPreconditions.checkMinAndroidApiLevel();
        return new Builder();
    }
}
