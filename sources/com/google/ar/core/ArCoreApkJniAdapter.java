package com.google.ar.core;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.InstallBehavior;
import com.google.ar.core.ArCoreApk.UserMessageType;
import com.google.ar.core.annotations.UsedByNative;
import com.google.ar.core.exceptions.ResourceExhaustedException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import java.util.HashMap;
import java.util.Map;

@UsedByNative("arcoreapk.cc")
class ArCoreApkJniAdapter {
    /* renamed from: a */
    private static Map<Class<? extends Throwable>, Integer> f19a;

    static {
        Map hashMap = new HashMap();
        f19a = hashMap;
        hashMap.put(IllegalArgumentException.class, Integer.valueOf(-1));
        f19a.put(ResourceExhaustedException.class, Integer.valueOf(-11));
        f19a.put(UnavailableArcoreNotInstalledException.class, Integer.valueOf(-100));
        f19a.put(UnavailableDeviceNotCompatibleException.class, Integer.valueOf(-101));
        f19a.put(UnavailableApkTooOldException.class, Integer.valueOf(-103));
        f19a.put(UnavailableSdkTooOldException.class, Integer.valueOf(-104));
        f19a.put(UnavailableUserDeclinedInstallationException.class, Integer.valueOf(-105));
    }

    ArCoreApkJniAdapter() {
    }

    /* renamed from: a */
    private static int m43a(Throwable th) {
        Log.e("ARCore-ArCoreApkJniAdapter", "Exception details:", th);
        Class cls = th.getClass();
        return f19a.containsKey(cls) ? ((Integer) f19a.get(cls)).intValue() : -2;
    }

    @UsedByNative("arcoreapk.cc")
    static int checkAvailability(Context context) {
        try {
            return ArCoreApk.getInstance().checkAvailability(context).nativeCode;
        } catch (Throwable th) {
            m43a(th);
            return Availability.UNKNOWN_ERROR.nativeCode;
        }
    }

    @UsedByNative("arcoreapk.cc")
    static int requestInstall(Activity activity, boolean z, int[] iArr) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        try {
            iArr[0] = ArCoreApk.getInstance().requestInstall(activity, z).nativeCode;
            return 0;
        } catch (Throwable th) {
            return m43a(th);
        }
    }

    @UsedByNative("arcoreapk.cc")
    static int requestInstallCustom(Activity activity, boolean z, int i, int i2, int[] iArr) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        try {
            iArr[0] = ArCoreApk.getInstance().requestInstall(activity, z, InstallBehavior.forNumber(i), UserMessageType.forNumber(i2)).nativeCode;
            return 0;
        } catch (Throwable th) {
            return m43a(th);
        }
    }
}
