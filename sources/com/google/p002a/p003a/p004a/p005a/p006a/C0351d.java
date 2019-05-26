package com.google.p002a.p003a.p004a.p005a.p006a;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* renamed from: com.google.a.a.a.a.a.d */
final class C0351d extends WeakReference<Throwable> {
    /* renamed from: a */
    private final int f14a;

    public C0351d(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th != null) {
            this.f14a = System.identityHashCode(th);
            return;
        }
        throw new NullPointerException("The referent cannot be null");
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                if (this == obj) {
                    return true;
                }
                C0351d c0351d = (C0351d) obj;
                return this.f14a == c0351d.f14a && get() == c0351d.get();
            }
        }
    }

    public final int hashCode() {
        return this.f14a;
    }
}
