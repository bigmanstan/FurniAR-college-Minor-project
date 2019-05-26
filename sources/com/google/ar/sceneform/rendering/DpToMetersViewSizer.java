package com.google.ar.sceneform.rendering;

import android.view.View;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.Preconditions;

public class DpToMetersViewSizer implements ViewSizer {
    private static final float DEFAULT_SIZE_Z = 0.0f;
    private final int dpPerMeters;

    public DpToMetersViewSizer(int dpPerMeters) {
        if (dpPerMeters > 0) {
            this.dpPerMeters = dpPerMeters;
            return;
        }
        throw new IllegalArgumentException("dpPerMeters must be greater than zero.");
    }

    public int getDpPerMeters() {
        return this.dpPerMeters;
    }

    public Vector3 getSize(View view) {
        Preconditions.checkNotNull(view, "Parameter \"view\" was null.");
        return new Vector3(ViewRenderableHelpers.convertPxToDp(view.getWidth()) / ((float) this.dpPerMeters), ViewRenderableHelpers.convertPxToDp(view.getHeight()) / ((float) this.dpPerMeters), 0.0f);
    }
}
