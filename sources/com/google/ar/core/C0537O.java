package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableException;

/* renamed from: com.google.ar.core.O */
enum C0537O extends C0372a {
    C0537O(String str, int i, int i2) {
        super(str, 21, -100);
    }

    /* renamed from: a */
    public final void mo1548a() throws UnavailableException {
        throw new UnavailableArcoreNotInstalledException();
    }
}
