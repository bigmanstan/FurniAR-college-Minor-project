package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$Renderable$Builder$Y96LkBnazwfNXU6Pxfl1RHbjLMA implements Function {
    private final /* synthetic */ Builder f$0;

    public /* synthetic */ -$$Lambda$Renderable$Builder$Y96LkBnazwfNXU6Pxfl1RHbjLMA(Builder builder) {
        this.f$0 = builder;
    }

    public final Object apply(Object obj) {
        return ((Renderable) this.f$0.getRenderableClass().cast(((Renderable) obj).makeCopy()));
    }
}
