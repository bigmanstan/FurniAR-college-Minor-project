package edu.wpi.jlyu.sceneformfurniture;

import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MainActivity$tKIJdiKn1c_N3WVITzufthU5Qys implements OnClickListener {
    private final /* synthetic */ MainActivity f$0;

    public /* synthetic */ -$$Lambda$MainActivity$tKIJdiKn1c_N3WVITzufthU5Qys(MainActivity mainActivity) {
        this.f$0 = mainActivity;
    }

    public final void onClick(View view) {
        this.f$0.selectedObject = Uri.parse("sofa.sfb");
    }
}
