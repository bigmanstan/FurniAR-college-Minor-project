package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseException;

public final class zzjg {
    public static zzja zzc(zzch zzch, Object obj) {
        zzja zza = zzjd.zza(obj, zzir.zzfv());
        if (zza instanceof zziy) {
            zza = new zziq(Double.valueOf((double) ((Long) zza.getValue()).longValue()), zzir.zzfv());
        }
        if (zzl(zza)) {
            return zza;
        }
        Object stringBuilder;
        if (zzch != null) {
            String valueOf = String.valueOf(zzch);
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf).length() + 7);
            stringBuilder2.append("Path '");
            stringBuilder2.append(valueOf);
            stringBuilder2.append("'");
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = "Node";
        }
        throw new DatabaseException(String.valueOf(stringBuilder).concat(" contains invalid priority: Must be a string, double, ServerValue, or null"));
    }

    public static boolean zzl(zzja zzja) {
        return zzja.zzfl().isEmpty() && (zzja.isEmpty() || (zzja instanceof zziq) || (zzja instanceof zzji) || (zzja instanceof zzip));
    }
}
