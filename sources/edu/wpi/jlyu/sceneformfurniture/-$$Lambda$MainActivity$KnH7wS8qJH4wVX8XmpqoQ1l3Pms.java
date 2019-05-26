package edu.wpi.jlyu.sceneformfurniture;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MainActivity$KnH7wS8qJH4wVX8XmpqoQ1l3Pms implements Consumer {
    private final /* synthetic */ MainActivity f$0;
    private final /* synthetic */ ArFragment f$1;
    private final /* synthetic */ Anchor f$2;

    public /* synthetic */ -$$Lambda$MainActivity$KnH7wS8qJH4wVX8XmpqoQ1l3Pms(MainActivity mainActivity, ArFragment arFragment, Anchor anchor) {
        this.f$0 = mainActivity;
        this.f$1 = arFragment;
        this.f$2 = anchor;
    }

    public final void accept(Object obj) {
        this.f$0.addNodeToScene(this.f$1, this.f$2, (ModelRenderable) obj);
    }
}
