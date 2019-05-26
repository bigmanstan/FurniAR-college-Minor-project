package com.google.android.gms.internal.firebase_database;

import java.util.concurrent.ScheduledExecutorService;

public final class zzbo {
    private final zzhz zzbs;
    private long zzgb = 1000;
    private double zzgd = 0.5d;
    private double zzge = 1.3d;
    private final ScheduledExecutorService zzgl;
    private long zzgm = 30000;

    public zzbo(ScheduledExecutorService scheduledExecutorService, zzia zzia, String str) {
        this.zzgl = scheduledExecutorService;
        this.zzbs = new zzhz(zzia, str);
    }

    public final zzbo zza(double d) {
        this.zzge = 1.3d;
        return this;
    }

    public final zzbm zzaz() {
        return new zzbm(this.zzgl, this.zzbs, this.zzgb, this.zzgm, this.zzge, this.zzgd);
    }

    public final zzbo zzb(double d) {
        this.zzgd = 0.7d;
        return this;
    }

    public final zzbo zzh(long j) {
        this.zzgb = 1000;
        return this;
    }

    public final zzbo zzi(long j) {
        this.zzgm = 30000;
        return this;
    }
}
