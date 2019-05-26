package com.google.ar.sceneform.rendering;

import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$FutureHelper$_bEL0yASDMpXlbrI_eNbaRh7Fn4 implements Function {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ -$$Lambda$FutureHelper$_bEL0yASDMpXlbrI_eNbaRh7Fn4(String str, String str2) {
        this.f$0 = str;
        this.f$1 = str2;
    }

    public final Object apply(Object obj) {
        return FutureHelper.lambda$logOnException$0(this.f$0, this.f$1, (Throwable) obj);
    }
}
