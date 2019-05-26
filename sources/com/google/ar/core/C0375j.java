package com.google.ar.core;

import android.view.View;
import android.view.View.OnClickListener;

/* renamed from: com.google.ar.core.j */
final class C0375j implements OnClickListener {
    /* renamed from: a */
    private final /* synthetic */ InstallActivity f63a;

    C0375j(InstallActivity installActivity) {
        this.f63a = installActivity;
    }

    public final void onClick(View view) {
        this.f63a.animateToSpinner();
        this.f63a.startInstaller();
    }
}
