package com.google.ar.sceneform.rendering;

import com.google.ar.sceneform.resources.SharedReference;
import com.google.ar.sceneform.utilities.AndroidPreconditions;

class ViewRenderableInternalData extends SharedReference {
    private final RenderViewToExternalTexture renderView;

    ViewRenderableInternalData(RenderViewToExternalTexture renderView) {
        this.renderView = renderView;
    }

    RenderViewToExternalTexture getRenderView() {
        return this.renderView;
    }

    protected void onDispose() {
        AndroidPreconditions.checkUiThread();
        this.renderView.releaseResources();
    }
}
