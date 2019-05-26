package com.google.ar.core;

import com.google.ar.core.Session.C0372a;
import com.google.ar.core.exceptions.DeadlineExceededException;

/* renamed from: com.google.ar.core.C */
enum C0525C extends C0372a {
    C0525C(String str, int i, int i2) {
        super(str, 10, -10);
    }

    /* renamed from: a */
    public final void mo1548a() {
        throw new DeadlineExceededException();
    }
}
