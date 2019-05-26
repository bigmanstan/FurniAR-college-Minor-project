package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.List;

public final class zzag {
    public static void zza(boolean z, String str, Object... objArr) {
        if (!z) {
            String str2 = "hardAssert failed: ";
            str = String.valueOf(String.format(str, objArr));
            throw new AssertionError(str.length() != 0 ? str2.concat(str) : new String(str2));
        }
    }

    public static Long zzb(Object obj) {
        return obj instanceof Integer ? Long.valueOf((long) ((Integer) obj).intValue()) : obj instanceof Long ? (Long) obj : null;
    }

    public static String zzb(List<String> list) {
        if (list.isEmpty()) {
            return "/";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (String str : list) {
            if (obj == null) {
                stringBuilder.append("/");
            }
            obj = null;
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    public static List<String> zzg(String str) {
        List<String> arrayList = new ArrayList();
        String[] split = str.split("/");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].isEmpty()) {
                arrayList.add(split[i]);
            }
        }
        return arrayList;
    }
}
