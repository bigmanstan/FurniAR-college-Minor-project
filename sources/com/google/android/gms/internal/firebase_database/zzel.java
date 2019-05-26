package com.google.android.gms.internal.firebase_database;

import java.util.List;
import java.util.concurrent.Callable;

final class zzel implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ long zzjh;
    private final /* synthetic */ boolean zzkx;
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzbv zzlj;
    private final /* synthetic */ zzbv zzlk;

    zzel(zzee zzee, boolean z, zzch zzch, zzbv zzbv, long j, zzbv zzbv2) {
        this.zzlb = zzee;
        this.zzkx = z;
        this.zzgy = zzch;
        this.zzlj = zzbv;
        this.zzjh = j;
        this.zzlk = zzbv2;
    }

    public final /* synthetic */ Object call() throws Exception {
        if (this.zzkx) {
            this.zzlb.zzkp.zza(this.zzgy, this.zzlj, this.zzjh);
        }
        this.zzlb.zzkr.zza(this.zzgy, this.zzlk, Long.valueOf(this.zzjh));
        return this.zzlb.zza(new zzfk(zzfn.zzmu, this.zzgy, this.zzlk));
    }
}
