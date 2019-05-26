package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

/* renamed from: com.google.ar.core.T */
enum C0541T extends C0372a {
    C0541T(String str, int i, int i2) {
        super(str, 25, -105);
    }

    /* renamed from: a */
    public final void mo1548a() throws UnavailableException {
        throw new UnavailableUserDeclinedInstallationException();
    }
}
