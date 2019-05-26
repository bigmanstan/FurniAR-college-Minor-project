package com.google.android.gms.internal.firebase_database;

public final class zzkl {
    private static long zzc(zziv<?> zziv) {
        long j = 8;
        if (!(zziv instanceof zziq)) {
            if (!(zziv instanceof zziy)) {
                if (zziv instanceof zzic) {
                    j = 4;
                } else if (zziv instanceof zzji) {
                    j = (long) (((String) zziv.getValue()).length() + 2);
                } else {
                    String valueOf = String.valueOf(zziv.getClass());
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 24);
                    stringBuilder.append("Unknown leaf node type: ");
                    stringBuilder.append(valueOf);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
        }
        return zziv.zzfl().isEmpty() ? j : (j + 24) + zzc((zziv) zziv.zzfl());
    }

    public static long zzn(zzja zzja) {
        if (zzja.isEmpty()) {
            return 4;
        }
        if (zzja.zzfk()) {
            return zzc((zziv) zzja);
        }
        long j = 1;
        for (zziz zziz : zzja) {
            j = ((j + ((long) zziz.zzge().zzfg().length())) + 4) + zzn(zziz.zzd());
        }
        if (!zzja.zzfl().isEmpty()) {
            j = (j + 12) + zzc((zziv) zzja.zzfl());
        }
        return j;
    }

    public static int zzo(zzja zzja) {
        int i = 0;
        if (zzja.isEmpty()) {
            return 0;
        }
        if (zzja.zzfk()) {
            return 1;
        }
        for (zziz zzd : zzja) {
            i += zzo(zzd.zzd());
        }
        return i;
    }
}
