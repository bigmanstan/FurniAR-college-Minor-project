package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableException;

/* renamed from: com.google.ar.core.P */
enum C0538P extends C0372a {
    C0538P(String str, int i, int i2) {
        super(str, 22, -101);
    }

    /* renamed from: a */
    public final void mo1548a() throws UnavailableException {
        throw new UnavailableDeviceNotCompatibleException();
    }
}
