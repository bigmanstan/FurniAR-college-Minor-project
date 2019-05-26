package com.google.ar.core;

import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.ar.core.v */
final class C0385v implements Runnable {
    /* renamed from: a */
    private final /* synthetic */ AtomicBoolean f92a;
    /* renamed from: b */
    private final /* synthetic */ C0384t f93b;

    C0385v(C0384t c0384t, AtomicBoolean atomicBoolean) {
        this.f93b = c0384t;
        this.f92a = atomicBoolean;
    }

    public final void run() {
        if (!this.f92a.getAndSet(true)) {
            Log.w("ARCore-InstallService", "requestInstall timed out, launching fullscreen.");
            C0378m.m71b(this.f93b.f89a, this.f93b.f90b);
        }
    }
}
