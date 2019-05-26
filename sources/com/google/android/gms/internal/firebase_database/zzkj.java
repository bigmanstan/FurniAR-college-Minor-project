package com.google.android.gms.internal.firebase_database;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

final class zzkj implements ThreadFactory {
    final /* synthetic */ zzkh zzuj;

    private zzkj(zzkh zzkh) {
        this.zzuj = zzkh;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
        zzey zzey = zzey.zzlr;
        zzey.zza(newThread, "FirebaseDatabaseWorker");
        zzey.zza(newThread, true);
        zzey.zza(newThread, new zzkk(this));
        return newThread;
    }
}
