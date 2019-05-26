package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MaterialFactory$99RavRBvl6JufnmZIBwMQ9oC1s0 implements Function {
    private final /* synthetic */ Color f$0;

    public /* synthetic */ -$$Lambda$MaterialFactory$99RavRBvl6JufnmZIBwMQ9oC1s0(Color color) {
        this.f$0 = color;
    }

    public final Object apply(Object obj) {
        return MaterialFactory.lambda$makeOpaqueWithColor$0(this.f$0, (Material) obj);
    }
}
