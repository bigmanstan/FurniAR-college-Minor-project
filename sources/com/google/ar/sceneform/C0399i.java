package com.google.ar.sceneform;

import android.util.Log;
import java.util.function.Function;

/* renamed from: com.google.ar.sceneform.i */
final /* synthetic */ class C0399i implements Function {
    /* renamed from: a */
    static final Function f115a = new C0399i();

    private C0399i() {
    }

    public final Object apply(Object obj) {
        return Log.e(Scene.TAG, "Failed to create the default Light Probe: ", (Throwable) obj);
    }
}
