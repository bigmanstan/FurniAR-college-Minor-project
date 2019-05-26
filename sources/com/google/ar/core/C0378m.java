package com.google.ar.core;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.C0370a;
import com.google.ar.core.exceptions.FatalException;
import com.google.p002a.p007b.p008a.p009a.p010a.C0356a;
import com.google.p002a.p007b.p008a.p009a.p010a.C0519b;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.ar.core.m */
class C0378m {
    /* renamed from: a */
    private final Queue<Runnable> f69a;
    /* renamed from: b */
    private Context f70b;
    /* renamed from: c */
    private volatile int f71c;
    /* renamed from: d */
    private C0356a f72d;
    /* renamed from: e */
    private BroadcastReceiver f73e;
    /* renamed from: f */
    private Context f74f;
    /* renamed from: g */
    private final ServiceConnection f75g;
    /* renamed from: h */
    private final AtomicReference<C0387x> f76h;

    C0378m() {
    }

    C0378m(byte b) {
        this();
        this.f69a = new ArrayDeque();
        this.f71c = C0386w.f94a;
        this.f75g = new C0381p(this);
        this.f76h = new AtomicReference();
    }

    /* renamed from: a */
    private static void m62a(Activity activity, Bundle bundle, C0380o c0380o) {
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("resolution.intent");
        if (pendingIntent != null) {
            try {
                activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 1234, new Intent(activity, activity.getClass()), 0, 0, 0);
                return;
            } catch (Throwable e) {
                c0380o.m81a(new FatalException("Installation Intent failed", e));
                return;
            }
        }
        Log.e("ARCore-InstallService", "Did not get pending intent.");
        c0380o.m81a(new FatalException("Installation intent failed to unparcel."));
    }

    /* renamed from: a */
    private synchronized void m63a(IBinder iBinder) {
        C0356a a = C0519b.m105a(iBinder);
        Log.i("ARCore-InstallService", "Install service connected");
        this.f72d = a;
        this.f71c = C0386w.f96c;
        for (Runnable run : this.f69a) {
            run.run();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private synchronized void m68a(java.lang.Runnable r2) throws com.google.ar.core.C0388y {
        /*
        r1 = this;
        monitor-enter(r1);
        r0 = r1.f71c;	 Catch:{ all -> 0x001c }
        r0 = r0 + -1;
        switch(r0) {
            case 0: goto L_0x0014;
            case 1: goto L_0x000d;
            case 2: goto L_0x0009;
            default: goto L_0x0008;
        };	 Catch:{ all -> 0x001c }
    L_0x0008:
        goto L_0x001a;
    L_0x0009:
        r2.run();	 Catch:{ all -> 0x001c }
        goto L_0x001a;
    L_0x000d:
        r0 = r1.f69a;	 Catch:{ all -> 0x001c }
        r0.offer(r2);	 Catch:{ all -> 0x001c }
        monitor-exit(r1);
        return;
    L_0x0014:
        r2 = new com.google.ar.core.y;	 Catch:{ all -> 0x001c }
        r2.<init>();	 Catch:{ all -> 0x001c }
        throw r2;	 Catch:{ all -> 0x001c }
    L_0x001a:
        monitor-exit(r1);
        return;
    L_0x001c:
        r2 = move-exception;
        monitor-exit(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ar.core.m.a(java.lang.Runnable):void");
    }

    /* renamed from: b */
    private static Bundle m69b() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("package.name", "com.google.ar.core");
        return bundle;
    }

    /* renamed from: b */
    private static void m71b(Activity activity, C0380o c0380o) {
        try {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.ar.core")));
        } catch (Throwable e) {
            c0380o.m81a(new FatalException("Failed to launch installer.", e));
        }
    }

    /* renamed from: c */
    private void m73c() {
        C0387x c0387x = (C0387x) this.f76h.getAndSet(null);
        if (c0387x != null) {
            c0387x.m82a();
        }
    }

    /* renamed from: d */
    private synchronized void m74d() {
        Log.i("ARCore-InstallService", "Install service disconnected");
        this.f71c = C0386w.f94a;
        this.f72d = null;
        m73c();
    }

    /* renamed from: a */
    public synchronized void m76a() {
        m73c();
        switch (this.f71c - 1) {
            case 0:
                break;
            case 1:
            case 2:
                this.f70b.unbindService(this.f75g);
                this.f70b = null;
                this.f71c = C0386w.f94a;
                break;
            default:
                break;
        }
        if (this.f73e != null) {
            this.f74f.unregisterReceiver(this.f73e);
        }
    }

    /* renamed from: a */
    public void m77a(Activity activity, C0380o c0380o) {
        C0387x c0387x = new C0387x(activity, c0380o);
        C0387x c0387x2 = (C0387x) this.f76h.getAndSet(c0387x);
        if (c0387x2 != null) {
            c0387x2.m82a();
        }
        c0387x.start();
        if (this.f73e == null) {
            this.f73e = new C0383s(this, c0380o);
            this.f74f = activity;
            this.f74f.registerReceiver(this.f73e, new IntentFilter("com.google.android.play.core.install.ACTION_INSTALL_STATUS"));
        }
        try {
            m68a(new C0384t(this, activity, c0380o));
        } catch (C0388y e) {
            Log.w("ARCore-InstallService", "requestInstall bind failed, launching fullscreen.");
            C0378m.m71b(activity, c0380o);
        }
    }

    /* renamed from: a */
    public synchronized void m78a(Context context) {
        this.f70b = context;
        if (context.bindService(new Intent("com.google.android.play.core.install.BIND_INSTALL_SERVICE").setPackage("com.android.vending"), this.f75g, 1)) {
            this.f71c = C0386w.f95b;
            return;
        }
        this.f71c = C0386w.f94a;
        this.f70b = null;
        Log.w("ARCore-InstallService", "bindService returned false.");
        context.unbindService(this.f75g);
    }

    /* renamed from: a */
    public synchronized void m79a(Context context, C0370a c0370a) {
        try {
            m68a(new C0382q(this, context, c0370a));
        } catch (C0388y e) {
            Log.e("ARCore-InstallService", "Play Store install service could not be bound.");
            c0370a.mo1554a(Availability.UNKNOWN_ERROR);
        }
    }
}
