package com.google.ar.core;

import android.content.Context;
import android.util.Log;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.C0370a;

/* renamed from: com.google.ar.core.q */
final class C0382q implements Runnable {
    /* renamed from: a */
    final /* synthetic */ C0370a f84a;
    /* renamed from: b */
    private final /* synthetic */ Context f85b;
    /* renamed from: c */
    private final /* synthetic */ C0378m f86c;

    C0382q(C0378m c0378m, Context context, C0370a c0370a) {
        this.f86c = c0378m;
        this.f85b = context;
        this.f84a = c0370a;
    }

    public final void run() {
        try {
            this.f86c.f72d.mo1249a(this.f85b.getApplicationInfo().packageName, C0378m.m69b(), new C0583r(this));
        } catch (Throwable e) {
            Log.e("ARCore-InstallService", "requestInfo threw", e);
            this.f84a.mo1554a(Availability.UNKNOWN_ERROR);
        }
    }
}
