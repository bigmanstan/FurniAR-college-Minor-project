package com.google.ar.core;

import com.google.ar.core.Session.C0372a;

/* renamed from: com.google.ar.core.K */
enum C0533K extends C0372a {
    C0533K(String str, int i, int i2) {
        super(str, 18, -20);
    }

    /* renamed from: a */
    public final void mo1548a() {
        throw new IllegalStateException("You have changed the camera configuration. You must call ArImage.close for all acquired images before calling Session.resume.");
    }
}
