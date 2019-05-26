package edu.wpi.jlyu.sceneformfurniture;

import android.view.MotionEvent;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.ux.BaseArFragment.OnTapArPlaneListener;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$MainActivity$2COOJD9VFYJTiaFnLKjjG9uK5fo implements OnTapArPlaneListener {
    private final /* synthetic */ MainActivity f$0;

    public /* synthetic */ -$$Lambda$MainActivity$2COOJD9VFYJTiaFnLKjjG9uK5fo(MainActivity mainActivity) {
        this.f$0 = mainActivity;
    }

    public final void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        MainActivity.lambda$onCreate$0(this.f$0, hitResult, plane, motionEvent);
    }
}
