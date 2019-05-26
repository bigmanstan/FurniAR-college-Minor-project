package com.google.ar.core;

import android.content.Context;

/* renamed from: com.google.ar.core.x */
final class C0387x extends Thread {
    /* renamed from: a */
    private final Context f98a;
    /* renamed from: b */
    private final C0380o f99b;
    /* renamed from: c */
    private volatile boolean f100c;

    C0387x(Context context, C0380o c0380o) {
        this.f98a = context;
        this.f99b = c0380o;
    }

    /* renamed from: a */
    final void m82a() {
        this.f100c = true;
    }

    public final void run() {
        while (!this.f100c) {
            if (C0555h.m144a().m152b(this.f98a)) {
                this.f99b.m80a(C0379n.COMPLETED);
                return;
            }
            try {
                C0387x.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }
}
