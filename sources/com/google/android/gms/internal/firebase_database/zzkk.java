package com.google.android.gms.internal.firebase_database;

import java.lang.Thread.UncaughtExceptionHandler;

final class zzkk implements UncaughtExceptionHandler {
    private final /* synthetic */ zzkj zzuk;

    zzkk(zzkj zzkj) {
        this.zzuk = zzkj;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        this.zzuk.zzuj.zza(th);
    }
}
