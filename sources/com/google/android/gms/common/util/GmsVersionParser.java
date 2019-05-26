package com.google.android.gms.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GmsVersionParser {
    public static final int UNKNOWN = -1;
    private static Pattern zzzy = null;

    private GmsVersionParser() {
    }

    public static int parseBuildMajorVersion(int i) {
        return i == -1 ? -1 : i / 100000;
    }

    public static long parseBuildNumber(String str) {
        if (str == null) {
            return -1;
        }
        Matcher matcher = zzdc().matcher(str);
        if (matcher.find()) {
            try {
                return Long.parseLong(matcher.group(2));
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }

    public static int parseBuildType(String str) {
        long parseVariantCode = parseVariantCode(str);
        return parseVariantCode == -1 ? -1 : (int) (parseVariantCode / 10000);
    }

    public static int parseBuildVersion(int i) {
        return i == -1 ? -1 : i / 1000;
    }

    public static int parseScreenDensity(String str) {
        long parseVariantCode = parseVariantCode(str);
        return parseVariantCode == -1 ? -1 : (int) (parseVariantCode % 100);
    }

    public static int parseTargetArchitecture(String str) {
        long parseVariantCode = parseVariantCode(str);
        return parseVariantCode == -1 ? -1 : (int) ((parseVariantCode / 100) % 100);
    }

    public static long parseVariantCode(String str) {
        if (str == null) {
            return -1;
        }
        Matcher matcher = zzdc().matcher(str);
        if (matcher.find()) {
            try {
                return Long.parseLong(matcher.group(1));
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }

    private static Pattern zzdc() {
        if (zzzy == null) {
            zzzy = Pattern.compile("\\((?:eng-)?(\\d+)-(.+?)[-)$]");
        }
        return zzzy;
    }
}
