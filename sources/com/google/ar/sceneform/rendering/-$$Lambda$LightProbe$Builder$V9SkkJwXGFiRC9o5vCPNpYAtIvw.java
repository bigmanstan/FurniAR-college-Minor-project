package com.google.ar.sceneform.rendering;

import com.google.ar.schemas.rendercore.LightingDef;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$LightProbe$Builder$V9SkkJwXGFiRC9o5vCPNpYAtIvw implements Function {
    private final /* synthetic */ LightProbe f$0;

    public /* synthetic */ -$$Lambda$LightProbe$Builder$V9SkkJwXGFiRC9o5vCPNpYAtIvw(LightProbe lightProbe) {
        this.f$0 = lightProbe;
    }

    public final Object apply(Object obj) {
        return this.f$0.buildFilamentResource((LightingDef) obj);
    }
}
