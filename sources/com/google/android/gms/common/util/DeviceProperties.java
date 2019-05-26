package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Objects;

public final class DeviceProperties {
    public static final String FEATURE_AUTO = "android.hardware.type.automotive";
    public static final String FEATURE_CHROME_OS = "org.chromium.arc";
    public static final String FEATURE_EMBEDDED = "android.hardware.type.embedded";
    public static final String FEATURE_IOT = "android.hardware.type.iot";
    public static final String FEATURE_LATCHSKY = "cn.google.services";
    public static final String FEATURE_PIXEL = "com.google.android.feature.PIXEL_EXPERIENCE";
    public static final String FEATURE_SIDEWINDER = "cn.google";
    public static final String FEATURE_TV_1 = "com.google.android.tv";
    public static final String FEATURE_TV_2 = "android.hardware.type.television";
    public static final String FEATURE_TV_3 = "android.software.leanback";
    private static Boolean zzzl;
    private static Boolean zzzm;
    private static Boolean zzzn;
    private static Boolean zzzo;
    private static Boolean zzzp;
    private static Boolean zzzq;
    private static Boolean zzzr;
    private static Boolean zzzs;
    private static Boolean zzzt;
    private static Boolean zzzu;
    private static Boolean zzzv;

    private DeviceProperties() {
    }

    public static boolean isAuto(Context context) {
        if (zzzt == null) {
            boolean z = PlatformVersion.isAtLeastO() && context.getPackageManager().hasSystemFeature(FEATURE_AUTO);
            zzzt = Boolean.valueOf(z);
        }
        return zzzt.booleanValue();
    }

    public static boolean isChromeOsDevice(Context context) {
        if (zzzs == null) {
            zzzs = Boolean.valueOf(context.getPackageManager().hasSystemFeature(FEATURE_CHROME_OS));
        }
        return zzzs.booleanValue();
    }

    public static boolean isIoT(Context context) {
        if (zzzr == null) {
            boolean z;
            if (!context.getPackageManager().hasSystemFeature(FEATURE_IOT)) {
                if (!context.getPackageManager().hasSystemFeature(FEATURE_EMBEDDED)) {
                    z = false;
                    zzzr = Boolean.valueOf(z);
                }
            }
            z = true;
            zzzr = Boolean.valueOf(z);
        }
        return zzzr.booleanValue();
    }

    public static boolean isLatchsky(Context context) {
        if (zzzp == null) {
            boolean z = PlatformVersion.isAtLeastM() && context.getPackageManager().hasSystemFeature(FEATURE_LATCHSKY);
            zzzp = Boolean.valueOf(z);
        }
        return zzzp.booleanValue();
    }

    public static boolean isLowRamOrPreKitKat(Context context) {
        if (VERSION.SDK_INT < 19) {
            return true;
        }
        if (zzzq == null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager != null) {
                zzzq = Boolean.valueOf(activityManager.isLowRamDevice());
            }
        }
        return Objects.equal(zzzq, Boolean.TRUE);
    }

    public static boolean isPixelDevice(Context context) {
        if (zzzv == null) {
            zzzv = Boolean.valueOf(context.getPackageManager().hasSystemFeature(FEATURE_PIXEL));
        }
        return zzzv.booleanValue();
    }

    @TargetApi(21)
    public static boolean isSidewinder(Context context) {
        if (zzzo == null) {
            boolean z = PlatformVersion.isAtLeastLollipop() && context.getPackageManager().hasSystemFeature("cn.google");
            zzzo = Boolean.valueOf(z);
        }
        return zzzo.booleanValue();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isTablet(android.content.res.Resources r4) {
        /*
        r0 = 0;
        if (r4 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = zzzl;
        if (r1 != 0) goto L_0x0045;
    L_0x0008:
        r1 = r4.getConfiguration();
        r1 = r1.screenLayout;
        r1 = r1 & 15;
        r2 = 3;
        r3 = 1;
        if (r1 <= r2) goto L_0x0016;
    L_0x0014:
        r1 = r3;
        goto L_0x0017;
    L_0x0016:
        r1 = r0;
    L_0x0017:
        if (r1 != 0) goto L_0x003e;
    L_0x0019:
        r1 = zzzm;
        if (r1 != 0) goto L_0x0036;
    L_0x001d:
        r4 = r4.getConfiguration();
        r1 = r4.screenLayout;
        r1 = r1 & 15;
        if (r1 > r2) goto L_0x002f;
    L_0x0027:
        r4 = r4.smallestScreenWidthDp;
        r1 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
        if (r4 < r1) goto L_0x002f;
    L_0x002d:
        r4 = r3;
        goto L_0x0030;
    L_0x002f:
        r4 = r0;
    L_0x0030:
        r4 = java.lang.Boolean.valueOf(r4);
        zzzm = r4;
    L_0x0036:
        r4 = zzzm;
        r4 = r4.booleanValue();
        if (r4 == 0) goto L_0x003f;
    L_0x003e:
        r0 = r3;
    L_0x003f:
        r4 = java.lang.Boolean.valueOf(r0);
        zzzl = r4;
    L_0x0045:
        r4 = zzzl;
        r4 = r4.booleanValue();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.DeviceProperties.isTablet(android.content.res.Resources):boolean");
    }

    public static boolean isTv(Context context) {
        if (zzzu == null) {
            boolean z;
            PackageManager packageManager = context.getPackageManager();
            if (!(packageManager.hasSystemFeature(FEATURE_TV_1) || packageManager.hasSystemFeature(FEATURE_TV_2))) {
                if (!packageManager.hasSystemFeature(FEATURE_TV_3)) {
                    z = false;
                    zzzu = Boolean.valueOf(z);
                }
            }
            z = true;
            zzzu = Boolean.valueOf(z);
        }
        return zzzu.booleanValue();
    }

    public static boolean isUserBuild() {
        return GooglePlayServicesUtilLight.sIsTestMode ? GooglePlayServicesUtilLight.sTestIsUserBuild : "user".equals(Build.TYPE);
    }

    @TargetApi(20)
    public static boolean isWearable(Context context) {
        if (zzzn == null) {
            boolean z = PlatformVersion.isAtLeastKitKatWatch() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
            zzzn = Boolean.valueOf(z);
        }
        return zzzn.booleanValue();
    }

    @TargetApi(24)
    public static boolean isWearableWithoutPlayStore(Context context) {
        return (!PlatformVersion.isAtLeastN() || isSidewinder(context)) && isWearable(context);
    }

    @VisibleForTesting
    public static void resetForTest() {
        zzzm = null;
        zzzl = null;
        zzzn = null;
        zzzo = null;
        zzzp = null;
        zzzq = null;
        zzzr = null;
        zzzs = null;
        zzzt = null;
        zzzu = null;
        zzzv = null;
    }

    @VisibleForTesting
    public static void setIsAutoForTest(boolean z) {
        zzzt = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsIoTForTest(boolean z) {
        zzzr = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsLatchskyForTest(boolean z) {
        zzzp = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsLowRamForTest(boolean z) {
        zzzq = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsPixelForTest(boolean z) {
        zzzv = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsSideWinderForTest(boolean z) {
        zzzo = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsTvForTest(boolean z) {
        zzzu = Boolean.valueOf(z);
    }

    @VisibleForTesting
    public static void setIsWearableForTest(boolean z) {
        zzzn = Boolean.valueOf(z);
    }
}
