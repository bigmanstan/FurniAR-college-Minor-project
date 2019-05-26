package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Debug;
import android.os.DropBoxManager;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.annotation.concurrent.GuardedBy;

public final class CrashUtils {
    private static final String[] zzzc = new String[]{"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager zzzd = null;
    private static boolean zzze = false;
    private static boolean zzzf;
    private static boolean zzzg;
    private static int zzzh = -1;
    @GuardedBy("CrashUtils.class")
    private static int zzzi = 0;
    @GuardedBy("CrashUtils.class")
    private static int zzzj = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorDialogData {
        public static final int AVG_CRASH_FREQ = 2;
        public static final int BINDER_CRASH = 268435456;
        public static final int DYNAMITE_CRASH = 536870912;
        public static final int FORCED_SHUSHED_BY_WRAPPER = 4;
        public static final int NONE = 0;
        public static final int POPUP_FREQ = 1;
        public static final int SUPPRESSED = 1073741824;
    }

    public static boolean addDynamiteErrorToDropBox(Context context, Throwable th) {
        return addErrorToDropBoxInternal(context, th, ErrorDialogData.DYNAMITE_CRASH);
    }

    @Deprecated
    public static boolean addErrorToDropBox(Context context, Throwable th) {
        return addDynamiteErrorToDropBox(context, th);
    }

    public static boolean addErrorToDropBoxInternal(Context context, String str, String str2, int i) {
        return zza(context, str, str2, i, null);
    }

    public static boolean addErrorToDropBoxInternal(Context context, Throwable th, int i) {
        boolean zzdb;
        try {
            Preconditions.checkNotNull(context);
            Preconditions.checkNotNull(th);
            if (!isPackageSide()) {
                return false;
            }
            if (!zzdb()) {
                th = zza(th);
                if (th == null) {
                    return false;
                }
            }
            return zza(context, Log.getStackTraceString(th), ProcessUtils.getMyProcessName(), i, th);
        } catch (Throwable e) {
            try {
                zzdb = zzdb();
            } catch (Throwable th2) {
                Log.e("CrashUtils", "Error determining which process we're running in!", th2);
                zzdb = false;
            }
            if (zzdb) {
                throw e;
            }
            Log.e("CrashUtils", "Error adding exception to DropBox!", e);
            return false;
        }
    }

    private static boolean isPackageSide() {
        return zzze ? zzzf : false;
    }

    public static boolean isSystemClassPrefixInternal(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String startsWith : zzzc) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    public static synchronized void setTestVariables(DropBoxManager dropBoxManager, boolean z, boolean z2, int i) {
        synchronized (CrashUtils.class) {
            zzze = true;
            zzzd = dropBoxManager;
            zzzg = z;
            zzzf = z2;
            zzzh = i;
            zzzi = 0;
            zzzj = 0;
        }
    }

    @VisibleForTesting
    private static synchronized String zza(Context context, String str, String str2, int i) {
        Throwable th;
        int i2;
        InputStreamReader inputStreamReader;
        Process start;
        InputStreamReader inputStreamReader2;
        char[] cArr;
        int read;
        Throwable e;
        String stringBuilder;
        synchronized (CrashUtils.class) {
            Object concat;
            StringBuilder stringBuilder2 = new StringBuilder(1024);
            stringBuilder2.append("Process: ");
            stringBuilder2.append(Strings.nullToEmpty(str2));
            stringBuilder2.append("\n");
            stringBuilder2.append("Package: com.google.android.gms");
            int i3 = 12451009;
            String str3 = "12.4.51 (020308-{{cl}})";
            if (zzdb()) {
                try {
                    PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(context.getPackageName(), 0);
                    int i4 = packageInfo.versionCode;
                    try {
                        if (packageInfo.versionName != null) {
                            str3 = packageInfo.versionName;
                        }
                        i3 = i4;
                    } catch (Throwable e2) {
                        th = e2;
                        i3 = i4;
                        Log.w("CrashUtils", "Error while trying to get the package information! Using static version.", th);
                        stringBuilder2.append(" v");
                        stringBuilder2.append(i3);
                        if (!TextUtils.isEmpty(str3)) {
                            if (str3.endsWith("-")) {
                                concat = String.valueOf(str3).concat("111111111");
                            }
                            str3 = String.valueOf(concat).concat(")");
                            stringBuilder2.append(" (");
                            stringBuilder2.append(str3);
                            stringBuilder2.append(")");
                        }
                        stringBuilder2.append("\n");
                        stringBuilder2.append("Build: ");
                        stringBuilder2.append(Build.FINGERPRINT);
                        stringBuilder2.append("\n");
                        if (Debug.isDebuggerConnected()) {
                            stringBuilder2.append("Debugger: Connected\n");
                        }
                        if (i != 0) {
                            stringBuilder2.append("DD-EDD: ");
                            stringBuilder2.append(i);
                            stringBuilder2.append("\n");
                        }
                        stringBuilder2.append("\n");
                        if (!TextUtils.isEmpty(str)) {
                            stringBuilder2.append(str);
                        }
                        if (zzdb()) {
                            i2 = 0;
                        } else {
                            i2 = zzzh >= 0 ? Secure.getInt(context.getContentResolver(), "logcat_for_system_app_crash", 0) : zzzh;
                        }
                        if (i2 > 0) {
                            stringBuilder2.append("\n");
                            inputStreamReader = null;
                            try {
                                start = new ProcessBuilder(new String[]{"/system/bin/logcat", "-v", "time", "-b", "events", "-b", "system", "-b", "main", "-b", "crash", "-t", String.valueOf(i2)}).redirectErrorStream(true).start();
                                try {
                                    start.getOutputStream().close();
                                } catch (IOException e3) {
                                }
                                try {
                                    start.getErrorStream().close();
                                } catch (IOException e4) {
                                }
                                inputStreamReader2 = new InputStreamReader(start.getInputStream());
                                try {
                                    cArr = new char[8192];
                                    while (true) {
                                        read = inputStreamReader2.read(cArr);
                                        if (read > 0) {
                                            try {
                                                break;
                                            } catch (IOException e5) {
                                            }
                                        } else {
                                            stringBuilder2.append(cArr, 0, read);
                                        }
                                    }
                                    inputStreamReader2.close();
                                } catch (IOException e6) {
                                    e = e6;
                                    inputStreamReader = inputStreamReader2;
                                    try {
                                        Log.e("CrashUtils", "Error running logcat", e);
                                        if (inputStreamReader != null) {
                                            inputStreamReader.close();
                                        }
                                        stringBuilder = stringBuilder2.toString();
                                        return stringBuilder;
                                    } catch (Throwable th2) {
                                        e = th2;
                                        if (inputStreamReader != null) {
                                            try {
                                                inputStreamReader.close();
                                            } catch (IOException e7) {
                                            }
                                        }
                                        throw e;
                                    }
                                } catch (Throwable th3) {
                                    e = th3;
                                    inputStreamReader = inputStreamReader2;
                                    if (inputStreamReader != null) {
                                        inputStreamReader.close();
                                    }
                                    throw e;
                                }
                            } catch (IOException e8) {
                                e = e8;
                                Log.e("CrashUtils", "Error running logcat", e);
                                if (inputStreamReader != null) {
                                    inputStreamReader.close();
                                }
                                stringBuilder = stringBuilder2.toString();
                                return stringBuilder;
                            }
                        }
                        stringBuilder = stringBuilder2.toString();
                        return stringBuilder;
                    }
                } catch (Exception e9) {
                    th = e9;
                    Log.w("CrashUtils", "Error while trying to get the package information! Using static version.", th);
                    stringBuilder2.append(" v");
                    stringBuilder2.append(i3);
                    if (TextUtils.isEmpty(str3)) {
                        if (str3.endsWith("-")) {
                            concat = String.valueOf(str3).concat("111111111");
                        }
                        str3 = String.valueOf(concat).concat(")");
                        stringBuilder2.append(" (");
                        stringBuilder2.append(str3);
                        stringBuilder2.append(")");
                    }
                    stringBuilder2.append("\n");
                    stringBuilder2.append("Build: ");
                    stringBuilder2.append(Build.FINGERPRINT);
                    stringBuilder2.append("\n");
                    if (Debug.isDebuggerConnected()) {
                        stringBuilder2.append("Debugger: Connected\n");
                    }
                    if (i != 0) {
                        stringBuilder2.append("DD-EDD: ");
                        stringBuilder2.append(i);
                        stringBuilder2.append("\n");
                    }
                    stringBuilder2.append("\n");
                    if (TextUtils.isEmpty(str)) {
                        stringBuilder2.append(str);
                    }
                    if (zzdb()) {
                        if (zzzh >= 0) {
                        }
                    } else {
                        i2 = 0;
                    }
                    if (i2 > 0) {
                        stringBuilder2.append("\n");
                        inputStreamReader = null;
                        start = new ProcessBuilder(new String[]{"/system/bin/logcat", "-v", "time", "-b", "events", "-b", "system", "-b", "main", "-b", "crash", "-t", String.valueOf(i2)}).redirectErrorStream(true).start();
                        start.getOutputStream().close();
                        start.getErrorStream().close();
                        inputStreamReader2 = new InputStreamReader(start.getInputStream());
                        cArr = new char[8192];
                        while (true) {
                            read = inputStreamReader2.read(cArr);
                            if (read > 0) {
                                break;
                            }
                            stringBuilder2.append(cArr, 0, read);
                        }
                        inputStreamReader2.close();
                    }
                    stringBuilder = stringBuilder2.toString();
                    return stringBuilder;
                }
            }
            stringBuilder2.append(" v");
            stringBuilder2.append(i3);
            if (TextUtils.isEmpty(str3)) {
                if (str3.contains("(") && !str3.contains(")")) {
                    if (str3.endsWith("-")) {
                        concat = String.valueOf(str3).concat("111111111");
                    }
                    str3 = String.valueOf(concat).concat(")");
                }
                stringBuilder2.append(" (");
                stringBuilder2.append(str3);
                stringBuilder2.append(")");
            }
            stringBuilder2.append("\n");
            stringBuilder2.append("Build: ");
            stringBuilder2.append(Build.FINGERPRINT);
            stringBuilder2.append("\n");
            if (Debug.isDebuggerConnected()) {
                stringBuilder2.append("Debugger: Connected\n");
            }
            if (i != 0) {
                stringBuilder2.append("DD-EDD: ");
                stringBuilder2.append(i);
                stringBuilder2.append("\n");
            }
            stringBuilder2.append("\n");
            if (TextUtils.isEmpty(str)) {
                stringBuilder2.append(str);
            }
            if (zzdb()) {
                if (zzzh >= 0) {
                }
            } else {
                i2 = 0;
            }
            if (i2 > 0) {
                stringBuilder2.append("\n");
                inputStreamReader = null;
                start = new ProcessBuilder(new String[]{"/system/bin/logcat", "-v", "time", "-b", "events", "-b", "system", "-b", "main", "-b", "crash", "-t", String.valueOf(i2)}).redirectErrorStream(true).start();
                start.getOutputStream().close();
                start.getErrorStream().close();
                inputStreamReader2 = new InputStreamReader(start.getInputStream());
                cArr = new char[8192];
                while (true) {
                    read = inputStreamReader2.read(cArr);
                    if (read > 0) {
                        break;
                    }
                    stringBuilder2.append(cArr, 0, read);
                }
                inputStreamReader2.close();
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    @VisibleForTesting
    private static synchronized Throwable zza(Throwable th) {
        synchronized (CrashUtils.class) {
            LinkedList linkedList = new LinkedList();
            while (th != null) {
                linkedList.push(th);
                th = th.getCause();
            }
            Throwable th2 = null;
            int i = 0;
            while (!linkedList.isEmpty()) {
                Throwable th3 = (Throwable) linkedList.pop();
                StackTraceElement[] stackTrace = th3.getStackTrace();
                ArrayList arrayList = new ArrayList();
                arrayList.add(new StackTraceElement(th3.getClass().getName(), "<filtered>", "<filtered>", 1));
                int i2 = i;
                for (Object obj : stackTrace) {
                    Object obj2;
                    String className = obj2.getClassName();
                    Object fileName = obj2.getFileName();
                    int i3 = (TextUtils.isEmpty(fileName) || !fileName.startsWith(":com.google.android.gms")) ? 0 : 1;
                    i2 |= i3;
                    if (i3 == 0 && !isSystemClassPrefixInternal(className)) {
                        obj2 = new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1);
                    }
                    arrayList.add(obj2);
                }
                th2 = th2 == null ? new Throwable("<filtered>") : new Throwable("<filtered>", th2);
                th2.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[0]));
                i = i2;
            }
            if (i == 0) {
                return null;
            }
            return th2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized boolean zza(android.content.Context r4, java.lang.String r5, java.lang.String r6, int r7, java.lang.Throwable r8) {
        /*
        r0 = com.google.android.gms.common.util.CrashUtils.class;
        monitor-enter(r0);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r4);	 Catch:{ all -> 0x0059 }
        r1 = isPackageSide();	 Catch:{ all -> 0x0059 }
        r2 = 0;
        if (r1 == 0) goto L_0x0057;
    L_0x000d:
        r1 = com.google.android.gms.common.util.Strings.isEmptyOrWhitespace(r5);	 Catch:{ all -> 0x0059 }
        if (r1 == 0) goto L_0x0014;
    L_0x0013:
        goto L_0x0057;
    L_0x0014:
        r1 = r5.hashCode();	 Catch:{ all -> 0x0059 }
        if (r8 != 0) goto L_0x001d;
    L_0x001a:
        r8 = zzzj;	 Catch:{ all -> 0x0059 }
        goto L_0x0021;
    L_0x001d:
        r8 = r8.hashCode();	 Catch:{ all -> 0x0059 }
    L_0x0021:
        r3 = zzzi;	 Catch:{ all -> 0x0059 }
        if (r3 != r1) goto L_0x002b;
    L_0x0025:
        r3 = zzzj;	 Catch:{ all -> 0x0059 }
        if (r3 != r8) goto L_0x002b;
    L_0x0029:
        monitor-exit(r0);
        return r2;
    L_0x002b:
        zzzi = r1;	 Catch:{ all -> 0x0059 }
        zzzj = r8;	 Catch:{ all -> 0x0059 }
        r8 = zzzd;	 Catch:{ all -> 0x0059 }
        if (r8 == 0) goto L_0x0036;
    L_0x0033:
        r8 = zzzd;	 Catch:{ all -> 0x0059 }
        goto L_0x003e;
    L_0x0036:
        r8 = "dropbox";
        r8 = r4.getSystemService(r8);	 Catch:{ all -> 0x0059 }
        r8 = (android.os.DropBoxManager) r8;	 Catch:{ all -> 0x0059 }
    L_0x003e:
        if (r8 == 0) goto L_0x0055;
    L_0x0040:
        r1 = "system_app_crash";
        r1 = r8.isTagEnabled(r1);	 Catch:{ all -> 0x0059 }
        if (r1 != 0) goto L_0x0049;
    L_0x0048:
        goto L_0x0055;
    L_0x0049:
        r4 = zza(r4, r5, r6, r7);	 Catch:{ all -> 0x0059 }
        r5 = "system_app_crash";
        r8.addText(r5, r4);	 Catch:{ all -> 0x0059 }
        r4 = 1;
        monitor-exit(r0);
        return r4;
    L_0x0055:
        monitor-exit(r0);
        return r2;
    L_0x0057:
        monitor-exit(r0);
        return r2;
    L_0x0059:
        r4 = move-exception;
        monitor-exit(r0);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.CrashUtils.zza(android.content.Context, java.lang.String, java.lang.String, int, java.lang.Throwable):boolean");
    }

    private static boolean zzdb() {
        return zzze ? zzzg : false;
    }
}
