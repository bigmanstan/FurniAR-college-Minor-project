package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableException;

/* renamed from: com.google.ar.core.Q */
enum C0539Q extends C0372a {
    C0539Q(String str, int i, int i2) {
        super(str, 23, -103);
    }

    /* renamed from: a */
    public final void mo1548a() throws UnavailableException {
        throw new UnavailableApkTooOldException();
    }
}
