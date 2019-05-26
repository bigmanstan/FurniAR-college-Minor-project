package com.google.ar.sceneform.ux;

import android.support.annotation.Nullable;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class FootprintSelectionVisualizer implements SelectionVisualizer {
    private final Node footprintNode = new Node();
    @Nullable
    private ModelRenderable footprintRenderable;

    public void setFootprintRenderable(ModelRenderable renderable) {
        ModelRenderable copyRenderable = renderable.makeCopy();
        this.footprintNode.setRenderable(copyRenderable);
        copyRenderable.setCollisionShape(null);
        this.footprintRenderable = copyRenderable;
    }

    @Nullable
    public ModelRenderable getFootprintRenderable() {
        return this.footprintRenderable;
    }

    public void applySelectionVisual(BaseTransformableNode node) {
        this.footprintNode.setParent(node);
    }

    public void removeSelectionVisual(BaseTransformableNode node) {
        this.footprintNode.setParent(null);
    }
}
