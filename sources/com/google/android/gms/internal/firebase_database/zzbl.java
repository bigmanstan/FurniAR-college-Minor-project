package com.google.android.gms.internal.firebase_database;

import java.io.EOFException;

final class zzbl implements Runnable {
    private final /* synthetic */ zzbh zzfh;
    private final /* synthetic */ zzjx zzfj;

    zzbl(zzbh zzbh, zzjx zzjx) {
        this.zzfh = zzbh;
        this.zzfj = zzjx;
    }

    public final void run() {
        zzhz zzb;
        String str;
        Throwable th;
        Object[] objArr;
        if (this.zzfj.getCause() == null || !(this.zzfj.getCause() instanceof EOFException)) {
            zzb = this.zzfh.zzff.zzbs;
            str = "WebSocket error.";
            th = this.zzfj;
            objArr = new Object[0];
        } else {
            zzb = this.zzfh.zzff.zzbs;
            str = "WebSocket reached EOF.";
            objArr = new Object[0];
            th = null;
        }
        zzb.zza(str, th, objArr);
        this.zzfh.zzff.zzat();
    }
}
