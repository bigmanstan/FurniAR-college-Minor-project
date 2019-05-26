package com.google.ar.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* renamed from: com.google.ar.core.l */
final class C0377l extends AnimatorListenerAdapter {
    /* renamed from: a */
    private final /* synthetic */ InstallActivity f68a;

    C0377l(InstallActivity installActivity) {
        this.f68a = installActivity;
    }

    public final void onAnimationEnd(Animator animator) {
        this.f68a.showSpinner();
    }
}
