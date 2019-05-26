package com.google.ar.core;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* renamed from: com.google.ar.core.p */
final class C0381p implements ServiceConnection {
    /* renamed from: a */
    private final /* synthetic */ C0378m f83a;

    C0381p(C0378m c0378m) {
        this.f83a = c0378m;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f83a.m63a(iBinder);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.f83a.m74d();
    }
}
