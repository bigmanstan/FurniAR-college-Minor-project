package com.google.android.gms.internal.firebase_database;

final class zzin implements zzio {
    private final long zzrs;

    public zzin(zzja zzja) {
        this.zzrs = Math.max(512, (long) Math.sqrt((double) (zzkl.zzn(zzja) * 100)));
    }

    public final boolean zze(zzim zzim) {
        return ((long) zzim.zzfp()) > this.zzrs && (zzim.zzfq().isEmpty() || !zzim.zzfq().zzbz().equals(zzid.zzfe()));
    }
}
