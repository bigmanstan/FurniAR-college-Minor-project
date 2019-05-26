package com.google.ar.sceneform.rendering;

import android.view.View;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class FixedHeightViewSizer implements ViewSizer {
    private static final float DEFAULT_SIZE_Z = 0.0f;
    private final float heightMeters;

    public FixedHeightViewSizer(float heightMeters) {
        if (heightMeters > 0.0f) {
            this.heightMeters = heightMeters;
            return;
        }
        throw new IllegalArgumentException("heightMeters must be greater than zero.");
    }

    public float getHeight() {
        return this.heightMeters;
    }

    public Vector3 getSize(View view) {
        Preconditions.checkNotNull(view, "Parameter \"view\" was null.");
        float aspectRatio = ViewRenderableHelpers.getAspectRatio(view);
        if (aspectRatio == 0.0f) {
            return Vector3.zero();
        }
        return new Vector3(this.heightMeters * aspectRatio, this.heightMeters, 0.0f);
    }
}
