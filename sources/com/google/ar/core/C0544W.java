package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.SessionNotPausedException;

/* renamed from: com.google.ar.core.W */
enum C0544W extends C0372a {
    C0544W(String str, int i, int i2) {
        super(str, 4, -4);
    }

    /* renamed from: a */
    public final void mo1548a() {
        throw new SessionNotPausedException();
    }
}
