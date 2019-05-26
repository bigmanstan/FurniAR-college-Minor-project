package com.google.ar.sceneform.collision;

import java.util.Comparator;

/* renamed from: com.google.ar.sceneform.collision.a */
final /* synthetic */ class C0392a implements Comparator {
    /* renamed from: a */
    static final Comparator f109a = new C0392a();

    private C0392a() {
    }

    public final int compare(Object obj, Object obj2) {
        return Float.compare(((RayHit) obj).getDistance(), ((RayHit) obj2).getDistance());
    }
}
