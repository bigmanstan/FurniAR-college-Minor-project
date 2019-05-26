package com.google.ar.sceneform.rendering;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$LoadRenderableFromRcbTask$qMXQtMDAZ5nF42Dk6jRuCpmhFuI implements Supplier {
    private final /* synthetic */ LoadRenderableFromRcbTask f$0;
    private final /* synthetic */ Callable f$1;

    public /* synthetic */ -$$Lambda$LoadRenderableFromRcbTask$qMXQtMDAZ5nF42Dk6jRuCpmhFuI(LoadRenderableFromRcbTask loadRenderableFromRcbTask, Callable callable) {
        this.f$0 = loadRenderableFromRcbTask;
        this.f$1 = callable;
    }

    public final Object get() {
        return LoadRenderableFromRcbTask.lambda$downloadAndProcessRenderable$0(this.f$0, this.f$1);
    }
}
