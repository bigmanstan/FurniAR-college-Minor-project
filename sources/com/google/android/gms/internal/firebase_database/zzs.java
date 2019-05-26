package com.google.android.gms.internal.firebase_database;

final class zzs implements Runnable {
    private final /* synthetic */ String zzbn;
    private final /* synthetic */ Throwable zzbo;

    zzs(zzr zzr, String str, Throwable th) {
        this.zzbn = str;
        this.zzbo = th;
    }

    public final void run() {
        throw new RuntimeException(this.zzbn, this.zzbo);
    }
}
