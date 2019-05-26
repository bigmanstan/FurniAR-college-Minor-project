package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$Renderable$Builder$yKLHg_bGVAdReR6224-4zAs04-c implements Function {
    private final /* synthetic */ Builder f$0;

    public /* synthetic */ -$$Lambda$Renderable$Builder$yKLHg_bGVAdReR6224-4zAs04-c(Builder builder) {
        this.f$0 = builder;
    }

    public final Object apply(Object obj) {
        return ((Renderable) this.f$0.getRenderableClass().cast(((Renderable) obj).makeCopy()));
    }
}
