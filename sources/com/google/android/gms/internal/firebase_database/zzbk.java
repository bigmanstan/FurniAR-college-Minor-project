package com.google.android.gms.internal.firebase_database;

final class zzbk implements Runnable {
    private final /* synthetic */ zzbh zzfh;

    zzbk(zzbh zzbh) {
        this.zzfh = zzbh;
    }

    public final void run() {
        if (this.zzfh.zzff.zzbs.zzfa()) {
            this.zzfh.zzff.zzbs.zza("closed", null, new Object[0]);
        }
        this.zzfh.zzff.zzat();
    }
}
