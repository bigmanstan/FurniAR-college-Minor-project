package com.google.ar.sceneform.rendering;

import java.util.function.BiFunction;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$PlaneRenderer$0-wguS_hFsU2yDEaA2R4qSJuqEs implements BiFunction {
    private final /* synthetic */ PlaneRenderer f$0;

    public /* synthetic */ -$$Lambda$PlaneRenderer$0-wguS_hFsU2yDEaA2R4qSJuqEs(PlaneRenderer planeRenderer) {
        this.f$0 = planeRenderer;
    }

    public final Object apply(Object obj, Object obj2) {
        return PlaneRenderer.lambda$loadPlaneMaterial$2(this.f$0, (Material) obj, (Texture) obj2);
    }
}
