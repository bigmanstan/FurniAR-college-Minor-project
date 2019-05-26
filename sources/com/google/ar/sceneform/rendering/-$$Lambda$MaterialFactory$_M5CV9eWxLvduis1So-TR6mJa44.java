package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MaterialFactory$_M5CV9eWxLvduis1So-TR6mJa44 implements Function {
    private final /* synthetic */ Color f$0;

    public /* synthetic */ -$$Lambda$MaterialFactory$_M5CV9eWxLvduis1So-TR6mJa44(Color color) {
        this.f$0 = color;
    }

    public final Object apply(Object obj) {
        return MaterialFactory.lambda$makeTransparentWithColor$1(this.f$0, (Material) obj);
    }
}
