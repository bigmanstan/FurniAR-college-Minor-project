package com.google.ar.sceneform;

import com.google.ar.sceneform.collision.Collider;
import java.util.ArrayList;
import java.util.function.Consumer;

/* renamed from: com.google.ar.sceneform.f */
final /* synthetic */ class C0396f implements Consumer {
    /* renamed from: a */
    private final ArrayList f112a;

    C0396f(ArrayList arrayList) {
        this.f112a = arrayList;
    }

    public final void accept(Object obj) {
        this.f112a.add((Node) ((Collider) obj).getTransformProvider());
    }
}
