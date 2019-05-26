package com.google.ar.sceneform.rendering;

import com.google.ar.schemas.rendercore.RendercoreBundleDef;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$LoadRenderableFromRcbTask$nxSmjws024HLog7iE8ClKkyoOQY implements Function {
    private final /* synthetic */ LoadRenderableFromRcbTask f$0;

    public /* synthetic */ -$$Lambda$LoadRenderableFromRcbTask$nxSmjws024HLog7iE8ClKkyoOQY(LoadRenderableFromRcbTask loadRenderableFromRcbTask) {
        this.f$0 = loadRenderableFromRcbTask;
    }

    public final Object apply(Object obj) {
        return LoadRenderableFromRcbTask.lambda$downloadAndProcessRenderable$1(this.f$0, (RendercoreBundleDef) obj);
    }
}
