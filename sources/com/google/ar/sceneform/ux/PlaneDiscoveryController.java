package com.google.ar.sceneform.ux;

import android.support.annotation.Nullable;
import android.view.View;

public class PlaneDiscoveryController {
    @Nullable
    private View planeDiscoveryView;

    public PlaneDiscoveryController(@Nullable View planeDiscoveryView) {
        this.planeDiscoveryView = planeDiscoveryView;
    }

    public void setInstructionView(View view) {
        this.planeDiscoveryView = view;
    }

    public void show() {
        if (this.planeDiscoveryView != null) {
            this.planeDiscoveryView.setVisibility(0);
        }
    }

    public void hide() {
        if (this.planeDiscoveryView != null) {
            this.planeDiscoveryView.setVisibility(8);
        }
    }
}
