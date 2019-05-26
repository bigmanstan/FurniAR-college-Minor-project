package com.google.android.gms.internal.firebase_database;

import java.util.concurrent.ScheduledExecutorService;

public final class zzaf {
    private final ScheduledExecutorService zzbc;
    private final zzad zzcn;
    private final zzia zzco;
    private final boolean zzcp;
    private final String zzcq;
    private final String zzcr;
    private final String zzcs;

    public zzaf(zzia zzia, zzad zzad, ScheduledExecutorService scheduledExecutorService, boolean z, String str, String str2, String str3) {
        this.zzco = zzia;
        this.zzcn = zzad;
        this.zzbc = scheduledExecutorService;
        this.zzcp = z;
        this.zzcq = str;
        this.zzcr = str2;
        this.zzcs = str3;
    }

    public final zzia zzq() {
        return this.zzco;
    }

    public final zzad zzr() {
        return this.zzcn;
    }

    public final ScheduledExecutorService zzs() {
        return this.zzbc;
    }

    public final boolean zzt() {
        return this.zzcp;
    }

    public final String zzu() {
        return this.zzcq;
    }

    public final String zzv() {
        return this.zzcr;
    }

    public final String zzw() {
        return this.zzcs;
    }
}
