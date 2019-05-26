package com.google.ar.core;

import android.view.View;
import android.view.View.OnClickListener;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

/* renamed from: com.google.ar.core.i */
final class C0374i implements OnClickListener {
    /* renamed from: a */
    private final /* synthetic */ InstallActivity f62a;

    C0374i(InstallActivity installActivity) {
        this.f62a = installActivity;
    }

    public final void onClick(View view) {
        this.f62a.finishWithFailure(new UnavailableUserDeclinedInstallationException());
    }
}
