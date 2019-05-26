package com.google.ar.sceneform;

import com.google.ar.sceneform.rendering.LightProbe;
import java.util.function.Consumer;

/* renamed from: com.google.ar.sceneform.h */
final /* synthetic */ class C0398h implements Consumer {
    /* renamed from: a */
    private final Scene f114a;

    C0398h(Scene scene) {
        this.f114a = scene;
    }

    public final void accept(Object obj) {
        this.f114a.lambda$setupLightProbe$4$Scene((LightProbe) obj);
    }
}
