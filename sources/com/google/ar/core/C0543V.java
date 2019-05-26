package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.SessionPausedException;

/* renamed from: com.google.ar.core.V */
enum C0543V extends C0372a {
    C0543V(String str, int i, int i2) {
        super(str, 3, -3);
    }

    /* renamed from: a */
    public final void mo1548a() {
        throw new SessionPausedException();
    }
}
