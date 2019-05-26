package com.google.ar.core;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.util.Log;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.C0370a;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

class ae implements C0370a {
    /* renamed from: a */
    final /* synthetic */ C0555h f159a;

    ae(C0555h c0555h) {
        this.f159a = c0555h;
    }

    /* renamed from: a */
    public static Uri m138a(String str) {
        return new Builder().scheme("content").authority("com.google.ar.core.services.arcorecontentprovider").path(str).build();
    }

    /* renamed from: a */
    static Availability m139a(Context context) {
        try {
            return m140b(context) != null ? Availability.SUPPORTED_APK_TOO_OLD : Availability.SUPPORTED_INSTALLED;
        } catch (UnavailableDeviceNotCompatibleException e) {
            return Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE;
        } catch (UnavailableUserDeclinedInstallationException e2) {
            return Availability.UNKNOWN_ERROR;
        }
    }

    /* renamed from: b */
    static PendingIntent m140b(Context context) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        try {
            Bundle call = context.getContentResolver().call(m138a(""), "getSetupIntent", context.getPackageName(), null);
            if (call == null) {
                return null;
            }
            PendingIntent pendingIntent = (PendingIntent) call.getParcelable("intent");
            if (pendingIntent != null) {
                return pendingIntent;
            }
            String string = call.getString("exceptionType", "");
            if (string.isEmpty()) {
                return null;
            }
            if (string.equals(UnavailableDeviceNotCompatibleException.class.getName())) {
                throw new UnavailableDeviceNotCompatibleException();
            } else if (string.equals(UnavailableUserDeclinedInstallationException.class.getName())) {
                throw new UnavailableUserDeclinedInstallationException();
            } else {
                Object newInstance;
                Class asSubclass = Class.forName(string).asSubclass(RuntimeException.class);
                if (call.getString("exceptionText", null) != null) {
                    newInstance = asSubclass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{r6});
                } else {
                    newInstance = asSubclass.getConstructor(new Class[0]).newInstance(new Object[0]);
                }
                throw ((RuntimeException) newInstance);
            }
        } catch (Throwable e) {
            Log.i("ARCore-SetupContentResolver", "Post-install failed", e);
            return null;
        }
    }

    /* renamed from: a */
    public void mo1554a(Availability availability) {
        synchronized (this.f159a) {
            this.f159a.f165f = availability;
            this.f159a.f166g = false;
        }
    }
}
