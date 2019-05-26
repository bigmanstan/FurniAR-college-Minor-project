package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.ImageInsufficientQualityException;

/* renamed from: com.google.ar.core.H */
enum C0530H extends C0372a {
    C0530H(String str, int i, int i2) {
        super(str, 15, -17);
    }

    /* renamed from: a */
    public final void mo1548a() {
        throw new ImageInsufficientQualityException();
    }
}
