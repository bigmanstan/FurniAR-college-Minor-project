package com.google.android.gms.common.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@VisibleForTesting
public class Strings {
    private static final Pattern zzaak = Pattern.compile("\\$\\{(.*?)\\}");

    private Strings() {
    }

    public static String capitalize(@NonNull String str) {
        if (str.length() == 0) {
            return str;
        }
        char charAt = str.charAt(0);
        char toUpperCase = Character.toUpperCase(charAt);
        if (charAt == toUpperCase) {
            return str;
        }
        str = str.substring(1);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 1);
        stringBuilder.append(toUpperCase);
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    @Nullable
    public static String emptyToNull(@Nullable String str) {
        return TextUtils.isEmpty(str) ? null : str;
    }

    public static String format(@NonNull String str, @NonNull Bundle bundle) {
        Matcher matcher = zzaak.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        do {
            str = matcher.group(1);
            Object obj = bundle.get(str);
            str = obj != null ? obj.toString() : bundle.containsKey(str) ? "null" : "";
            matcher.appendReplacement(stringBuffer, str);
        } while (matcher.find());
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static boolean isEmptyOrWhitespace(@Nullable String str) {
        if (str != null) {
            if (!str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static String nullToEmpty(@Nullable String str) {
        return str == null ? "" : str;
    }

    public static String padEnd(@NonNull String str, int i, char c) {
        Preconditions.checkNotNull(str);
        if (str.length() >= i) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(i);
        stringBuilder.append(str);
        for (int length = str.length(); length < i; length++) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
