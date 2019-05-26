package com.google.p002a.p003a.p004a.p005a.p006a;

/* renamed from: com.google.a.a.a.a.a.e */
final class C0517e extends C0349b {
    /* renamed from: a */
    private final C0350c f155a = new C0350c();

    C0517e() {
    }

    /* renamed from: a */
    public final void mo1248a(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 != null) {
            this.f155a.m6a(th, true).add(th2);
        } else {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
    }
}
