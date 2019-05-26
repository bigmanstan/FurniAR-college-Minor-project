package com.google.android.gms.internal.firebase_database;

final class zzv implements zzgm<Void, Integer> {
    private final /* synthetic */ zzgj zzbv;

    zzv(zzu zzu, zzgj zzgj) {
        this.zzbv = zzgj;
    }

    public final /* synthetic */ Object zza(zzch zzch, Object obj, Object obj2) {
        Integer num = (Integer) obj2;
        return Integer.valueOf(this.zzbv.zzai(zzch) == null ? num.intValue() + 1 : num.intValue());
    }
}
