package com.google.ar.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.ar.core.exceptions.FatalException;

/* renamed from: com.google.ar.core.s */
final class C0383s extends BroadcastReceiver {
    /* renamed from: a */
    private final /* synthetic */ C0380o f87a;
    /* renamed from: b */
    private final /* synthetic */ C0378m f88b;

    C0383s(C0378m c0378m, C0380o c0380o) {
        this.f88b = c0378m;
        this.f87a = c0380o;
    }

    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        if ("com.google.android.play.core.install.ACTION_INSTALL_STATUS".equals(action) && extras != null && extras.containsKey("install.status")) {
            this.f88b.m73c();
            int i = extras.getInt("install.status");
            if (i != 6) {
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                        this.f87a.m80a(C0379n.ACCEPTED);
                        return;
                    case 4:
                        this.f87a.m80a(C0379n.COMPLETED);
                        return;
                    default:
                        this.f87a.m81a(new FatalException("Unknown error from install service."));
                        return;
                }
            }
            this.f87a.m80a(C0379n.CANCELLED);
            return;
        }
        this.f87a.m81a(new FatalException("Unknown error from install service."));
    }
}
