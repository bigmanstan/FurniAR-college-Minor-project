package com.google.ar.core;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.C0370a;
import com.google.p002a.p007b.p008a.p009a.p010a.C0521e;

/* renamed from: com.google.ar.core.r */
final class C0583r extends C0521e {
    /* renamed from: a */
    private final /* synthetic */ C0382q f202a;

    C0583r(C0382q c0382q) {
        this.f202a = c0382q;
    }

    /* renamed from: a */
    public final void mo1967a() throws RemoteException {
    }

    /* renamed from: a */
    public final void mo1968a(Bundle bundle) throws RemoteException {
    }

    /* renamed from: b */
    public final void mo1969b(Bundle bundle) throws RemoteException {
        C0370a c0370a;
        Availability availability;
        int i = bundle.getInt("error.code", -100);
        if (i != -5) {
            if (i == -3) {
                Log.e("ARCore-InstallService", "The Google Play application must be updated.");
            } else if (i != 0) {
                StringBuilder stringBuilder = new StringBuilder(33);
                stringBuilder.append("requestInfo returned: ");
                stringBuilder.append(i);
                Log.e("ARCore-InstallService", stringBuilder.toString());
            } else {
                c0370a = this.f202a.f84a;
                availability = Availability.SUPPORTED_NOT_INSTALLED;
            }
            c0370a = this.f202a.f84a;
            availability = Availability.UNKNOWN_ERROR;
        } else {
            Log.e("ARCore-InstallService", "The device is not supported.");
            c0370a = this.f202a.f84a;
            availability = Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE;
        }
        c0370a.mo1554a(availability);
    }
}
