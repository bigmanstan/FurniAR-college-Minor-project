package com.google.android.gms.internal.firebase_database;

import java.util.Random;

public final class zzkp {
    private static final Random zzun = new Random();
    private static long zzuo = 0;
    private static final int[] zzup = new int[12];

    public static synchronized String zzo(long j) {
        String stringBuilder;
        synchronized (zzkp.class) {
            int i = 0;
            int i2 = j == zzuo ? 1 : 0;
            zzuo = j;
            char[] cArr = new char[8];
            StringBuilder stringBuilder2 = new StringBuilder(20);
            for (int i3 = 7; i3 >= 0; i3--) {
                cArr[i3] = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt((int) (j % 64));
                j /= 64;
            }
            stringBuilder2.append(cArr);
            int i4;
            if (i2 == 0) {
                for (i4 = 0; i4 < 12; i4++) {
                    zzup[i4] = zzun.nextInt(64);
                }
            } else {
                for (i4 = 11; i4 >= 0; i4--) {
                    if (zzup[i4] != 63) {
                        zzup[i4] = zzup[i4] + 1;
                        break;
                    }
                    zzup[i4] = 0;
                }
            }
            while (i < 12) {
                stringBuilder2.append("-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt(zzup[i]));
                i++;
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }
}
