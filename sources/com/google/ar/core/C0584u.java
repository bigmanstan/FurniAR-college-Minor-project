package com.google.ar.core;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.google.ar.core.exceptions.FatalException;
import com.google.p002a.p007b.p008a.p009a.p010a.C0521e;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.ar.core.u */
final class C0584u extends C0521e {
    /* renamed from: a */
    private final /* synthetic */ AtomicBoolean f203a;
    /* renamed from: b */
    private final /* synthetic */ C0384t f204b;

    C0584u(C0384t c0384t, AtomicBoolean atomicBoolean) {
        this.f204b = c0384t;
        this.f203a = atomicBoolean;
    }

    /* renamed from: a */
    public final void mo1967a() throws RemoteException {
    }

    /* renamed from: a */
    public final void mo1968a(Bundle bundle) throws RemoteException {
        if (!this.f203a.getAndSet(true)) {
            int i = bundle.getInt("error.code", -100);
            int i2 = bundle.getInt("install.status", 0);
            if (i2 == 4) {
                this.f204b.f90b.m80a(C0379n.COMPLETED);
            } else if (i != 0) {
                StringBuilder stringBuilder = new StringBuilder(51);
                stringBuilder.append("requestInstall = ");
                stringBuilder.append(i);
                stringBuilder.append(", launching fullscreen.");
                Log.w("ARCore-InstallService", stringBuilder.toString());
                C0378m.m71b(this.f204b.f89a, this.f204b.f90b);
            } else if (bundle.containsKey("resolution.intent")) {
                C0378m.m62a(this.f204b.f89a, bundle, this.f204b.f90b);
            } else if (i2 != 10) {
                switch (i2) {
                    case 1:
                    case 2:
                    case 3:
                        this.f204b.f90b.m80a(C0379n.ACCEPTED);
                        return;
                    case 4:
                        this.f204b.f90b.m80a(C0379n.COMPLETED);
                        return;
                    case 5:
                        this.f204b.f90b.m81a(new FatalException("Unexpected FAILED install status without error."));
                        return;
                    case 6:
                        this.f204b.f90b.m80a(C0379n.CANCELLED);
                        return;
                    default:
                        C0380o c0380o = this.f204b.f90b;
                        StringBuilder stringBuilder2 = new StringBuilder(38);
                        stringBuilder2.append("Unexpected install status: ");
                        stringBuilder2.append(i2);
                        c0380o.m81a(new FatalException(stringBuilder2.toString()));
                        return;
                }
            } else {
                this.f204b.f90b.m81a(new FatalException("Unexpected REQUIRES_UI_INTENT install status without an intent."));
            }
        }
    }

    /* renamed from: b */
    public final void mo1969b(Bundle bundle) throws RemoteException {
    }
}
