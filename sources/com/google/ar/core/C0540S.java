package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;

/* renamed from: com.google.ar.core.S */
enum C0540S extends C0372a {
    C0540S(String str, int i, int i2) {
        super(str, 24, -104);
    }

    /* renamed from: a */
    public final void mo1548a() throws UnavailableException {
        throw new UnavailableSdkTooOldException();
    }
}
