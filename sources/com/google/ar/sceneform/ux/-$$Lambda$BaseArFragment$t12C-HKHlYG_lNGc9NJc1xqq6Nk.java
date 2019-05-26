package com.google.ar.sceneform.ux;

import com.google.ar.sceneform.rendering.ModelRenderable;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$BaseArFragment$t12C-HKHlYG_lNGc9NJc1xqq6Nk implements Consumer {
    private final /* synthetic */ FootprintSelectionVisualizer f$0;

    public /* synthetic */ -$$Lambda$BaseArFragment$t12C-HKHlYG_lNGc9NJc1xqq6Nk(FootprintSelectionVisualizer footprintSelectionVisualizer) {
        this.f$0 = footprintSelectionVisualizer;
    }

    public final void accept(Object obj) {
        BaseArFragment.lambda$makeTransformationSystem$1(this.f$0, (ModelRenderable) obj);
    }
}
