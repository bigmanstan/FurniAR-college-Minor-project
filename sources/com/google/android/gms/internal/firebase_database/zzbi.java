package com.google.android.gms.internal.firebase_database;

final class zzbi implements Runnable {
    private final /* synthetic */ zzbh zzfh;

    zzbi(zzbh zzbh) {
        this.zzfh = zzbh;
    }

    public final void run() {
        this.zzfh.zzff.zzfd.cancel(false);
        this.zzfh.zzff.zzex = true;
        if (this.zzfh.zzff.zzbs.zzfa()) {
            this.zzfh.zzff.zzbs.zza("websocket opened", null, new Object[0]);
        }
        this.zzfh.zzff.zzas();
    }
}
