package com.google.android.gms.common.util;

public class Hex {
    private static final char[] zzaaa = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] zzzz = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytesToColonDelimitedStringLowercase(byte[] bArr) {
        if (bArr.length == 0) {
            return new String();
        }
        char[] cArr = new char[((bArr.length * 3) - 1)];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length - 1) {
            int i3 = bArr[i] & 255;
            int i4 = i2 + 1;
            cArr[i2] = zzaaa[i3 >>> 4];
            i2 = i4 + 1;
            cArr[i4] = zzaaa[i3 & 15];
            i3 = i2 + 1;
            cArr[i2] = ':';
            i++;
            i2 = i3;
        }
        int i5 = bArr[bArr.length - 1] & 255;
        i = i2 + 1;
        cArr[i2] = zzaaa[i5 >>> 4];
        cArr[i] = zzaaa[i5 & 15];
        return new String(cArr);
    }

    public static String bytesToColonDelimitedStringUppercase(byte[] bArr) {
        if (bArr.length == 0) {
            return new String();
        }
        char[] cArr = new char[((bArr.length * 3) - 1)];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length - 1) {
            int i3 = bArr[i] & 255;
            int i4 = i2 + 1;
            cArr[i2] = zzzz[i3 >>> 4];
            i2 = i4 + 1;
            cArr[i4] = zzzz[i3 & 15];
            i3 = i2 + 1;
            cArr[i2] = ':';
            i++;
            i2 = i3;
        }
        int i5 = bArr[bArr.length - 1] & 255;
        i = i2 + 1;
        cArr[i2] = zzzz[i5 >>> 4];
        cArr[i] = zzzz[i5 & 15];
        return new String(cArr);
    }

    public static String bytesToStringLowercase(byte[] bArr) {
        char[] cArr = new char[(bArr.length << 1)];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = bArr[i] & 255;
            int i4 = i2 + 1;
            cArr[i2] = zzaaa[i3 >>> 4];
            i2 = i4 + 1;
            cArr[i4] = zzaaa[i3 & 15];
            i++;
        }
        return new String(cArr);
    }

    public static String bytesToStringUppercase(byte[] bArr) {
        return bytesToStringUppercase(bArr, false);
    }

    public static String bytesToStringUppercase(byte[] bArr, boolean z) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length << 1);
        int i = 0;
        while (i < length && (!z || i != length - 1 || (bArr[i] & 255) != 0)) {
            stringBuilder.append(zzzz[(bArr[i] & 240) >>> 4]);
            stringBuilder.append(zzzz[bArr[i] & 15]);
            i++;
        }
        return stringBuilder.toString();
    }

    public static byte[] colonDelimitedStringToBytes(String str) {
        return stringToBytes(str.replace(":", ""));
    }

    public static byte[] stringToBytes(String str) throws IllegalArgumentException {
        int length = str.length();
        if (length % 2 == 0) {
            byte[] bArr = new byte[(length / 2)];
            int i = 0;
            while (i < length) {
                int i2 = i + 2;
                bArr[i / 2] = (byte) Integer.parseInt(str.substring(i, i2), 16);
                i = i2;
            }
            return bArr;
        }
        throw new IllegalArgumentException("Hex string has odd number of characters");
    }
}
