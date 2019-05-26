package com.google.android.gms.common.util;

@VisibleForTesting
public class NumberUtils {
    private NumberUtils() {
    }

    public static int compare(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    public static int compare(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        return i < 0 ? -1 : i == 0 ? 0 : 1;
    }

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long parseHexLong(String str) {
        if (str.length() > 16) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 37);
            stringBuilder.append("Invalid input: ");
            stringBuilder.append(str);
            stringBuilder.append(" exceeds 16 characters");
            throw new NumberFormatException(stringBuilder.toString());
        } else if (str.length() != 16) {
            return Long.parseLong(str, 16);
        } else {
            return (Long.parseLong(str.substring(0, 8), 16) << 32) | Long.parseLong(str.substring(8), 16);
        }
    }
}
