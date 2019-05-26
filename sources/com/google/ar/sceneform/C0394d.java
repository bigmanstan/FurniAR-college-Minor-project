package com.google.ar.sceneform;

import com.google.ar.sceneform.collision.Collider;
import java.util.function.BiConsumer;

/* renamed from: com.google.ar.sceneform.d */
final /* synthetic */ class C0394d implements BiConsumer {
    /* renamed from: a */
    static final BiConsumer f110a = new C0394d();

    private C0394d() {
    }

    public final void accept(Object obj, Object obj2) {
        ((HitTestResult) obj).setNode((Node) ((Collider) obj2).getTransformProvider());
    }
}
