package com.google.ar.core;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.google.ar.core.ArCoreApk.Availability;
import com.google.ar.core.ArCoreApk.C0370a;
import com.google.ar.core.ArCoreApk.InstallBehavior;
import com.google.ar.core.ArCoreApk.InstallStatus;
import com.google.ar.core.ArCoreApk.UserMessageType;
import com.google.ar.core.exceptions.FatalException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

/* renamed from: com.google.ar.core.h */
final class C0555h extends ArCoreApk {
    /* renamed from: b */
    private static final C0555h f160b = new C0555h();
    /* renamed from: a */
    Exception f161a;
    /* renamed from: c */
    private boolean f162c;
    /* renamed from: d */
    private int f163d;
    /* renamed from: e */
    private long f164e;
    /* renamed from: f */
    private Availability f165f;
    /* renamed from: g */
    private boolean f166g;
    /* renamed from: h */
    private C0378m f167h;
    /* renamed from: i */
    private boolean f168i;
    /* renamed from: j */
    private boolean f169j;
    /* renamed from: k */
    private int f170k;

    C0555h() {
    }

    /* renamed from: a */
    private static InstallStatus m143a(Activity activity) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        PendingIntent b = ae.m140b(activity);
        if (b != null) {
            try {
                Log.i("ARCore-ArCoreApk", "Starting setup activity");
                activity.startIntentSender(b.getIntentSender(), null, 0, 0, 0);
                return InstallStatus.INSTALL_REQUESTED;
            } catch (Throwable e) {
                Log.w("ARCore-ArCoreApk", "Setup activity launch failed", e);
            }
        }
        return InstallStatus.INSTALLED;
    }

    /* renamed from: a */
    public static C0555h m144a() {
        return f160b;
    }

    /* renamed from: c */
    private static boolean m146c() {
        return VERSION.SDK_INT >= 24;
    }

    /* renamed from: c */
    private final boolean m147c(Context context) {
        m149e(context);
        return this.f169j;
    }

    /* renamed from: d */
    private static int m148d(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.google.ar.core", 4);
            int i = packageInfo.versionCode;
            return (i == 0 && (packageInfo.services == null || packageInfo.services.length == 0)) ? -1 : i;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    /* renamed from: e */
    private final synchronized void m149e(Context context) {
        if (!this.f168i) {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            try {
                Bundle bundle = packageManager.getApplicationInfo(packageName, 128).metaData;
                if (bundle.containsKey("com.google.ar.core")) {
                    this.f169j = bundle.getString("com.google.ar.core").equals("required");
                    if (bundle.containsKey("com.google.ar.core.min_apk_version")) {
                        this.f170k = bundle.getInt("com.google.ar.core.min_apk_version");
                        ActivityInfo[] activityInfoArr = packageManager.getPackageInfo(packageName, 1).activities;
                        String canonicalName = InstallActivity.class.getCanonicalName();
                        boolean z = false;
                        for (ActivityInfo activityInfo : activityInfoArr) {
                            if (canonicalName.equals(activityInfo.name)) {
                                z = true;
                                break;
                            }
                        }
                        if (z) {
                            this.f168i = true;
                            return;
                        }
                        String str = "Application manifest must contain activity ";
                        canonicalName = String.valueOf(canonicalName);
                        throw new FatalException(canonicalName.length() != 0 ? str.concat(canonicalName) : new String(str));
                    }
                    throw new FatalException("Application manifest must contain meta-data com.google.ar.core.min_apk_version");
                }
                throw new FatalException("Application manifest must contain meta-data com.google.ar.core");
            } catch (Throwable e) {
                throw new FatalException("Could not load application package metadata", e);
            } catch (Throwable e2) {
                throw new FatalException("Could not load application package info", e2);
            }
        }
    }

    /* renamed from: a */
    final synchronized C0378m m150a(Context context) {
        if (this.f167h == null) {
            C0378m c0378m = new C0378m((byte) 0);
            c0378m.m78a(context.getApplicationContext());
            this.f167h = c0378m;
        }
        return this.f167h;
    }

    /* renamed from: b */
    final synchronized void m151b() {
        if (this.f161a == null) {
            this.f163d = 0;
        }
        this.f162c = false;
        if (this.f167h != null) {
            this.f167h.m76a();
            this.f167h = null;
        }
    }

    /* renamed from: b */
    final boolean m152b(Context context) {
        m149e(context);
        if (C0555h.m148d(context) != 0) {
            if (C0555h.m148d(context) < this.f170k) {
                return false;
            }
        }
        return true;
    }

    public final Availability checkAvailability(Context context) {
        if (!C0555h.m146c()) {
            return Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE;
        }
        try {
            if (m152b(context)) {
                m151b();
                return ae.m139a(context);
            }
            synchronized (this) {
                Availability availability;
                if ((this.f165f == null || this.f165f.isUnknown()) && !this.f166g) {
                    this.f166g = true;
                    C0370a aeVar = new ae(this);
                    if (m152b(context)) {
                        availability = Availability.SUPPORTED_INSTALLED;
                    } else if (C0555h.m148d(context) != -1) {
                        availability = Availability.SUPPORTED_APK_TOO_OLD;
                    } else if (m147c(context)) {
                        availability = Availability.SUPPORTED_NOT_INSTALLED;
                    } else {
                        m150a(context).m79a(context, aeVar);
                    }
                    aeVar.mo1554a(availability);
                }
                if (this.f165f != null) {
                    availability = this.f165f;
                    return availability;
                } else if (this.f166g) {
                    availability = Availability.UNKNOWN_CHECKING;
                    return availability;
                } else {
                    Log.e("ARCore-ArCoreApk", "request not running but result is null?");
                    availability = Availability.UNKNOWN_ERROR;
                    return availability;
                }
            }
        } catch (Throwable e) {
            Log.e("ARCore-ArCoreApk", "Error while checking app details and ARCore status", e);
            return Availability.UNKNOWN_ERROR;
        }
    }

    public final InstallStatus requestInstall(Activity activity, boolean z) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        return requestInstall(activity, z, m147c(activity) ? InstallBehavior.REQUIRED : InstallBehavior.OPTIONAL, m147c(activity) ? UserMessageType.APPLICATION : UserMessageType.FEATURE);
    }

    public final InstallStatus requestInstall(Activity activity, boolean z, InstallBehavior installBehavior, UserMessageType userMessageType) throws UnavailableDeviceNotCompatibleException, UnavailableUserDeclinedInstallationException {
        if (!C0555h.m146c()) {
            throw new UnavailableDeviceNotCompatibleException();
        } else if (m152b(activity)) {
            m151b();
            return C0555h.m143a(activity);
        } else if (this.f162c) {
            return InstallStatus.INSTALL_REQUESTED;
        } else {
            if (this.f161a != null) {
                if (z) {
                    Log.w("ARCore-ArCoreApk", "Clearing previous failure: ", this.f161a);
                    this.f161a = null;
                } else if (this.f161a instanceof UnavailableDeviceNotCompatibleException) {
                    throw ((UnavailableDeviceNotCompatibleException) this.f161a);
                } else if (this.f161a instanceof UnavailableUserDeclinedInstallationException) {
                    throw ((UnavailableUserDeclinedInstallationException) this.f161a);
                } else if (this.f161a instanceof RuntimeException) {
                    throw ((RuntimeException) this.f161a);
                } else {
                    throw new RuntimeException("Unexpected exception type", this.f161a);
                }
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - this.f164e > 5000) {
                this.f163d = 0;
            }
            this.f163d++;
            this.f164e = uptimeMillis;
            if (this.f163d <= 2) {
                try {
                    activity.startActivity(new Intent(activity, InstallActivity.class).putExtra("message", userMessageType).putExtra("behavior", installBehavior));
                    this.f162c = true;
                    return InstallStatus.INSTALL_REQUESTED;
                } catch (Throwable e) {
                    throw new FatalException("Failed to launch InstallActivity", e);
                }
            }
            throw new FatalException("Requesting ARCore installation too rapidly.");
        }
    }
}
