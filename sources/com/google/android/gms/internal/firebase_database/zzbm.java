package com.google.android.gms.internal.firebase_database;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class zzbm {
    private final ScheduledExecutorService zzbc;
    private final zzhz zzbs;
    private final long zzgb;
    private final long zzgc;
    private final double zzgd;
    private final double zzge;
    private final Random zzgf;
    private ScheduledFuture<?> zzgg;
    private long zzgh;
    private boolean zzgi;

    private zzbm(ScheduledExecutorService scheduledExecutorService, zzhz zzhz, long j, long j2, double d, double d2) {
        this.zzgf = new Random();
        this.zzgi = true;
        this.zzbc = scheduledExecutorService;
        this.zzbs = zzhz;
        this.zzgb = j;
        this.zzgc = j2;
        this.zzge = d;
        this.zzgd = d2;
    }

    public final void cancel() {
        if (this.zzgg != null) {
            this.zzbs.zza("Cancelling existing retry attempt", null, new Object[0]);
            this.zzgg.cancel(false);
            this.zzgg = null;
        } else {
            this.zzbs.zza("No existing retry attempt to cancel", null, new Object[0]);
        }
        this.zzgh = 0;
    }

    public final void zzax() {
        this.zzgi = true;
        this.zzgh = 0;
    }

    public final void zzay() {
        this.zzgh = this.zzgc;
    }

    public final void zzb(Runnable runnable) {
        Runnable zzbn = new zzbn(this, runnable);
        if (this.zzgg != null) {
            this.zzbs.zza("Cancelling previous scheduled retry", null, new Object[0]);
            this.zzgg.cancel(false);
            this.zzgg = null;
        }
        long j = 0;
        if (!this.zzgi) {
            this.zzgh = this.zzgh == 0 ? this.zzgb : Math.min((long) (((double) this.zzgh) * this.zzge), this.zzgc);
            j = (long) (((1.0d - this.zzgd) * ((double) this.zzgh)) + ((this.zzgd * ((double) this.zzgh)) * this.zzgf.nextDouble()));
        }
        this.zzgi = false;
        this.zzbs.zza("Scheduling retry in %dms", null, Long.valueOf(j));
        this.zzgg = this.zzbc.schedule(zzbn, j, TimeUnit.MILLISECONDS);
    }
}
