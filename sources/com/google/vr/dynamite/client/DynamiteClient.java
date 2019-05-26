package com.google.vr.dynamite.client;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UsedByNative
public final class DynamiteClient {
    /* renamed from: a */
    private static final ArrayMap<C0427e, C0426d> f145a = new ArrayMap();

    private DynamiteClient() {
    }

    @UsedByNative
    public static synchronized int checkVersion(Context context, String str, String str2, String str3) {
        StringBuilder stringBuilder;
        synchronized (DynamiteClient.class) {
            C0428f c0428f = null;
            if (str3 != null) {
                if (!str3.isEmpty()) {
                    Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)").matcher(str3);
                    if (matcher.matches()) {
                        c0428f = new C0428f(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                    } else {
                        String str4 = "Version";
                        String str5 = "Failed to parse version from: ";
                        String valueOf = String.valueOf(str3);
                        Log.w(str4, valueOf.length() != 0 ? str5.concat(valueOf) : new String(str5));
                    }
                }
            }
            if (c0428f == null) {
                str = "Improperly formatted minVersion string: ";
                str2 = String.valueOf(str3);
                throw new IllegalArgumentException(str2.length() != 0 ? str.concat(str2) : new String(str));
            }
            C0427e c0427e = new C0427e(str, str2);
            C0426d remoteLibraryLoaderFromInfo = getRemoteLibraryLoaderFromInfo(c0427e);
            try {
                INativeLibraryLoader newNativeLibraryLoader = remoteLibraryLoaderFromInfo.m99a(context).newNativeLibraryLoader(ObjectWrapper.m242a(remoteLibraryLoaderFromInfo.m100b(context)), ObjectWrapper.m242a(context));
                if (newNativeLibraryLoader == null) {
                    str = String.valueOf(c0427e);
                    stringBuilder = new StringBuilder(String.valueOf(str).length() + 72);
                    stringBuilder.append("Failed to load native library ");
                    stringBuilder.append(str);
                    stringBuilder.append(" from remote package: no loader available.");
                    Log.e("DynamiteClient", stringBuilder.toString());
                    return -1;
                }
                int checkVersion = newNativeLibraryLoader.checkVersion(str3);
                return checkVersion;
            } catch (Throwable e) {
                str3 = String.valueOf(c0427e);
                stringBuilder = new StringBuilder(String.valueOf(str3).length() + 54);
                stringBuilder.append("Failed to load native library ");
                stringBuilder.append(str3);
                stringBuilder.append(" from remote package:\n  ");
                Log.e("DynamiteClient", stringBuilder.toString(), e);
                return -1;
            }
        }
    }

    @UsedByNative
    public static synchronized ClassLoader getRemoteClassLoader(Context context, String str, String str2) {
        synchronized (DynamiteClient.class) {
            C0427e c0427e = new C0427e(str, str2);
            try {
                context = getRemoteLibraryLoaderFromInfo(c0427e).m100b(context);
                if (context != null) {
                    ClassLoader classLoader = context.getClassLoader();
                    return classLoader;
                }
                return null;
            } catch (Throwable e) {
                String valueOf = String.valueOf(c0427e);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 52);
                stringBuilder.append("Failed to get remote Context");
                stringBuilder.append(valueOf);
                stringBuilder.append(" from remote package:\n  ");
                Log.e("DynamiteClient", stringBuilder.toString(), e);
                return null;
            }
        }
    }

    @UsedByNative
    private static synchronized C0426d getRemoteLibraryLoaderFromInfo(C0427e c0427e) {
        C0426d c0426d;
        synchronized (DynamiteClient.class) {
            c0426d = (C0426d) f145a.get(c0427e);
            if (c0426d == null) {
                c0426d = new C0426d(c0427e);
                f145a.put(c0427e, c0426d);
            }
        }
        return c0426d;
    }

    @UsedByNative
    public static synchronized long loadNativeRemoteLibrary(Context context, String str, String str2) {
        StringBuilder stringBuilder;
        synchronized (DynamiteClient.class) {
            C0427e c0427e = new C0427e(str, str2);
            C0426d remoteLibraryLoaderFromInfo = getRemoteLibraryLoaderFromInfo(c0427e);
            try {
                INativeLibraryLoader newNativeLibraryLoader = remoteLibraryLoaderFromInfo.m99a(context).newNativeLibraryLoader(ObjectWrapper.m242a(remoteLibraryLoaderFromInfo.m100b(context)), ObjectWrapper.m242a(context));
                if (newNativeLibraryLoader == null) {
                    str = String.valueOf(c0427e);
                    stringBuilder = new StringBuilder(String.valueOf(str).length() + 72);
                    stringBuilder.append("Failed to load native library ");
                    stringBuilder.append(str);
                    stringBuilder.append(" from remote package: no loader available.");
                    Log.e("DynamiteClient", stringBuilder.toString());
                    return 0;
                }
                long initializeAndLoadNativeLibrary = newNativeLibraryLoader.initializeAndLoadNativeLibrary(str2);
                return initializeAndLoadNativeLibrary;
            } catch (Throwable e) {
                str2 = String.valueOf(c0427e);
                stringBuilder = new StringBuilder(String.valueOf(str2).length() + 54);
                stringBuilder.append("Failed to load native library ");
                stringBuilder.append(str2);
                stringBuilder.append(" from remote package:\n  ");
                Log.e("DynamiteClient", stringBuilder.toString(), e);
                return 0;
            }
        }
    }
}
