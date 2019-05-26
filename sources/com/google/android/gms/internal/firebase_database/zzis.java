package com.google.android.gms.internal.firebase_database;

import java.util.Comparator;

public abstract class zzis implements Comparator<zziz> {
    public final int zza(zziz zziz, zziz zziz2, boolean z) {
        return z ? compare(zziz2, zziz) : compare(zziz, zziz2);
    }

    public abstract zziz zzf(zzid zzid, zzja zzja);

    public abstract zziz zzfw();

    public abstract String zzfx();

    public abstract boolean zzi(zzja zzja);
}
