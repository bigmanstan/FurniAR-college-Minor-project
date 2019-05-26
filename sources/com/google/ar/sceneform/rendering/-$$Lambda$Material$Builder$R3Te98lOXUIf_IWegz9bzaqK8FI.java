package com.google.ar.sceneform.rendering;

import com.google.ar.sceneform.rendering.Material.Builder;
import java.nio.ByteBuffer;
import java.util.function.Function;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$Material$Builder$R3Te98lOXUIf_IWegz9bzaqK8FI implements Function {
    private final /* synthetic */ Builder f$0;

    public /* synthetic */ -$$Lambda$Material$Builder$R3Te98lOXUIf_IWegz9bzaqK8FI(Builder builder) {
        this.f$0 = builder;
    }

    public final Object apply(Object obj) {
        return new Material(new MaterialInternalData(this.f$0.createFilamentMaterial((ByteBuffer) obj)));
    }
}
