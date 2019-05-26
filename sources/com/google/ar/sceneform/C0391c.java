package com.google.ar.sceneform;

import java.util.function.Predicate;

/* renamed from: com.google.ar.sceneform.c */
final /* synthetic */ class C0391c implements Predicate {
    /* renamed from: a */
    private final int f107a;
    /* renamed from: b */
    private final String f108b;

    C0391c(int i, String str) {
        this.f107a = i;
        this.f108b = str;
    }

    public final boolean test(Object obj) {
        return NodeParent.lambda$findByName$0$NodeParent(this.f107a, this.f108b, (Node) obj);
    }
}
