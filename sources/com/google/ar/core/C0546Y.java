package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.TextureNotSetException;

/* renamed from: com.google.ar.core.Y */
enum C0546Y extends C0372a {
    C0546Y(String str, int i, int i2) {
        super(str, 6, -6);
    }

    /* renamed from: a */
    public final void mo1548a() {
        throw new TextureNotSetException();
    }
}
