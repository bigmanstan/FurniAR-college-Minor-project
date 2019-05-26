package com.google.ar.sceneform.rendering;

import android.support.annotation.Nullable;
import com.google.android.filament.Engine;
import com.google.android.filament.Material;
import com.google.ar.sceneform.resources.SharedReference;
import com.google.ar.sceneform.utilities.AndroidPreconditions;

class MaterialInternalData extends SharedReference {
    @Nullable
    private Material filamentMaterial;

    MaterialInternalData(Material filamentMaterial) {
        this.filamentMaterial = filamentMaterial;
    }

    Material getFilamentMaterial() {
        if (this.filamentMaterial != null) {
            return this.filamentMaterial;
        }
        throw new AssertionError("Filament Material is null.");
    }

    protected void onDispose() {
        AndroidPreconditions.checkUiThread();
        Engine engine = EngineInstance.getEngine();
        Material material = this.filamentMaterial;
        this.filamentMaterial = null;
        if (material != null && engine != null && engine.isValid()) {
            engine.destroyMaterial(material);
        }
    }
}
