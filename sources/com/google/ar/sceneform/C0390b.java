package com.google.ar.sceneform;

import android.view.View;
import android.view.View.OnLayoutChangeListener;

/* renamed from: com.google.ar.sceneform.b */
final /* synthetic */ class C0390b implements OnLayoutChangeListener {
    /* renamed from: a */
    private final Camera f106a;

    C0390b(Camera camera) {
        this.f106a = camera;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f106a.lambda$new$0$Camera(view, i, i2, i3, i4, i5, i6, i7, i8);
    }
}
