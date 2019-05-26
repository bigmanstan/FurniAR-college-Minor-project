package com.google.ar.sceneform.rendering;

import android.view.View;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class FixedWidthViewSizer implements ViewSizer {
    private static final float DEFAULT_SIZE_Z = 0.0f;
    private final float widthMeters;

    public FixedWidthViewSizer(float widthMeters) {
        if (widthMeters > 0.0f) {
            this.widthMeters = widthMeters;
            return;
        }
        throw new IllegalArgumentException("widthMeters must be greater than zero.");
    }

    public float getWidth() {
        return this.widthMeters;
    }

    public Vector3 getSize(View view) {
        Preconditions.checkNotNull(view, "Parameter \"view\" was null.");
        float aspectRatio = ViewRenderableHelpers.getAspectRatio(view);
        if (aspectRatio == 0.0f) {
            return Vector3.zero();
        }
        return new Vector3(this.widthMeters, this.widthMeters / aspectRatio, 0.0f);
    }
}
