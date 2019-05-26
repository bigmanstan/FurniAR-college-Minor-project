package com.google.ar.core;

import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

/* renamed from: com.google.ar.core.o */
class C0380o {
    /* renamed from: a */
    boolean f81a = false;
    /* renamed from: b */
    final /* synthetic */ InstallActivity f82b;

    C0380o(InstallActivity installActivity) {
        this.f82b = installActivity;
    }

    /* renamed from: a */
    public void m80a(C0379n c0379n) {
        synchronized (this.f82b) {
            if (this.f81a) {
                return;
            }
            this.f82b.lastEvent = c0379n;
            switch (c0379n.ordinal()) {
                case 0:
                    return;
                case 1:
                    this.f82b.finishWithFailure(new UnavailableUserDeclinedInstallationException());
                    break;
                case 2:
                    if (!this.f82b.waitingForCompletion) {
                        this.f82b.closeInstaller();
                    }
                    this.f82b.finishWithFailure(null);
                    break;
                default:
                    break;
            }
            this.f81a = true;
        }
    }

    /* renamed from: a */
    public void m81a(Exception exception) {
        synchronized (this.f82b) {
            if (this.f81a) {
                return;
            }
            this.f81a = true;
            this.f82b.lastEvent = C0379n.CANCELLED;
            this.f82b.finishWithFailure(exception);
        }
    }
}
