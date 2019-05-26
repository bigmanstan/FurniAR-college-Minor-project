package com.google.android.gms.internal.firebase_database;

final class zzbj implements Runnable {
    private final /* synthetic */ zzbh zzfh;
    private final /* synthetic */ String zzfi;

    zzbj(zzbh zzbh, String str) {
        this.zzfh = zzbh;
        this.zzfi = str;
    }

    public final void run() {
        this.zzfh.zzff.zzl(this.zzfi);
    }
}
