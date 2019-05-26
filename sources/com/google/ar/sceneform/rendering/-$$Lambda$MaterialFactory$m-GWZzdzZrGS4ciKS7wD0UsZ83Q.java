package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MaterialFactory$m-GWZzdzZrGS4ciKS7wD0UsZ83Q implements Function {
    private final /* synthetic */ Texture f$0;

    public /* synthetic */ -$$Lambda$MaterialFactory$m-GWZzdzZrGS4ciKS7wD0UsZ83Q(Texture texture) {
        this.f$0 = texture;
    }

    public final Object apply(Object obj) {
        return MaterialFactory.lambda$makeTransparentWithTexture$3(this.f$0, (Material) obj);
    }
}
