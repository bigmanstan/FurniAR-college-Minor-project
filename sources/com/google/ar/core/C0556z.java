package com.google.ar.core;

import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.C0370a;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.ar.core.z */
class C0556z implements C0370a {
    /* renamed from: a */
    final /* synthetic */ AtomicReference f171a;

    C0556z(AtomicReference atomicReference) {
        this.f171a = atomicReference;
    }

    /* renamed from: a */
    public void mo1554a(Availability availability) {
        this.f171a.set(availability);
    }
}
