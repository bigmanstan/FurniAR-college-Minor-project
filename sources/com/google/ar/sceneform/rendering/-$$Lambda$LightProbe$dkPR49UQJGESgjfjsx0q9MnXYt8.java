package com.google.ar.sceneform.rendering;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$LightProbe$dkPR49UQJGESgjfjsx0q9MnXYt8 implements Supplier {
    private final /* synthetic */ LightProbe f$0;
    private final /* synthetic */ Callable f$1;

    public /* synthetic */ -$$Lambda$LightProbe$dkPR49UQJGESgjfjsx0q9MnXYt8(LightProbe lightProbe, Callable callable) {
        this.f$0 = lightProbe;
        this.f$1 = callable;
    }

    public final Object get() {
        return LightProbe.lambda$loadInBackground$1(this.f$0, this.f$1);
    }
}
