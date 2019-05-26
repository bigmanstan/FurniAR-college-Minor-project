package com.google.ar.sceneform.resources;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/* renamed from: com.google.ar.sceneform.resources.a */
final /* synthetic */ class C0413a implements Consumer {
    /* renamed from: a */
    private final ResourceRegistry f134a;
    /* renamed from: b */
    private final Object f135b;
    /* renamed from: c */
    private final CompletableFuture f136c;

    C0413a(ResourceRegistry resourceRegistry, Object obj, CompletableFuture completableFuture) {
        this.f134a = resourceRegistry;
        this.f135b = obj;
        this.f136c = completableFuture;
    }

    public final void accept(Object obj) {
        this.f134a.lambda$register$0$ResourceRegistry(this.f135b, this.f136c, obj);
    }
}
