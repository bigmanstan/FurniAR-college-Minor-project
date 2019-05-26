package com.google.ar.sceneform.utilities;

import java.net.URL;
import java.util.concurrent.Callable;

/* renamed from: com.google.ar.sceneform.utilities.c */
final /* synthetic */ class C0416c implements Callable {
    /* renamed from: a */
    private final URL f141a;

    private C0416c(URL url) {
        this.f141a = url;
    }

    /* renamed from: a */
    static Callable m97a(URL url) {
        return new C0416c(url);
    }

    public final Object call() {
        return this.f141a.openStream();
    }
}
