package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MaterialFactory$tpcGClrps36DtB215p9pCRIImLg implements Function {
    private final /* synthetic */ Texture f$0;

    public /* synthetic */ -$$Lambda$MaterialFactory$tpcGClrps36DtB215p9pCRIImLg(Texture texture) {
        this.f$0 = texture;
    }

    public final Object apply(Object obj) {
        return MaterialFactory.lambda$makeOpaqueWithTexture$2(this.f$0, (Material) obj);
    }
}
