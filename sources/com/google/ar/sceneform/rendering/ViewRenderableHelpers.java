package com.google.ar.sceneform.rendering;

import android.content.res.Resources;
import android.view.View;

class ViewRenderableHelpers {
    ViewRenderableHelpers() {
    }

    static float getAspectRatio(View view) {
        float viewWidth = (float) view.getWidth();
        float viewHeight = (float) view.getHeight();
        if (viewWidth != 0.0f) {
            if (viewHeight != 0.0f) {
                return viewWidth / viewHeight;
            }
        }
        return 0.0f;
    }

    static float convertPxToDp(int px) {
        return ((float) px) / (Resources.getSystem().getDisplayMetrics().xdpi / 160.0f);
    }
}
