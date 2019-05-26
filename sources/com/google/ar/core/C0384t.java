package com.google.ar.core;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.ar.core.t */
final class C0384t implements Runnable {
    /* renamed from: a */
    final /* synthetic */ Activity f89a;
    /* renamed from: b */
    final /* synthetic */ C0380o f90b;
    /* renamed from: c */
    final /* synthetic */ C0378m f91c;

    C0384t(C0378m c0378m, Activity activity, C0380o c0380o) {
        this.f91c = c0378m;
        this.f89a = activity;
        this.f90b = c0380o;
    }

    public final void run() {
        try {
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            this.f91c.f72d.mo1250a(this.f89a.getApplicationInfo().packageName, Collections.singletonList(C0378m.m69b()), new Bundle(), new C0584u(this, atomicBoolean));
            new Handler().postDelayed(new C0385v(this, atomicBoolean), 3000);
        } catch (Throwable e) {
            Log.w("ARCore-InstallService", "requestInstall threw, launching fullscreen.", e);
            C0378m.m71b(this.f89a, this.f90b);
        }
    }
}
