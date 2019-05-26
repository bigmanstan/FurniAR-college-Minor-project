package com.google.android.gms.internal.firebase_database;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class zzkh implements zzdt {
    private ScheduledThreadPoolExecutor zzui = new zzki(this, 1, new zzkj());

    public zzkh() {
        this.zzui.setKeepAliveTime(3, TimeUnit.SECONDS);
    }

    public final void restart() {
        this.zzui.setCorePoolSize(1);
    }

    public final void shutdown() {
        this.zzui.setCorePoolSize(0);
    }

    public abstract void zza(Throwable th);

    public final void zzc(Runnable runnable) {
        this.zzui.execute(runnable);
    }

    public final ScheduledExecutorService zzs() {
        return this.zzui;
    }
}
