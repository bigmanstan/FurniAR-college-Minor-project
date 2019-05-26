package com.google.firebase.database;

final class zzs implements Runnable {
    private final /* synthetic */ Query zzaw;
    private final /* synthetic */ boolean zzaz;

    zzs(Query query, boolean z) {
        this.zzaw = query;
        this.zzaz = z;
    }

    public final void run() {
        this.zzaw.zzai.zza(this.zzaw.zzh(), this.zzaz);
    }
}
