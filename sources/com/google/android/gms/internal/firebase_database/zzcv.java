package com.google.android.gms.internal.firebase_database;

final class zzcv implements Runnable {
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ zzdl zziv;

    zzcv(zzck zzck, zzdl zzdl) {
        this.zzil = zzck;
        this.zziv = zzdl;
    }

    public final void run() {
        this.zzil.zze(new zzfc(this.zzil, this.zziv.zzjk, zzhh.zzal(this.zziv.zzap)));
    }
}
