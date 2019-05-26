package com.google.ar.sceneform.rendering;

import android.view.View;
import com.google.ar.sceneform.math.Vector3;

public interface ViewSizer {
    Vector3 getSize(View view);
}
