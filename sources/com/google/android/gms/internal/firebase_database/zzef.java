package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

final class zzef implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ long zzjh;
    private final /* synthetic */ boolean zzkx;
    private final /* synthetic */ zzja zzky;
    private final /* synthetic */ zzja zzkz;
    private final /* synthetic */ boolean zzla;
    private final /* synthetic */ zzee zzlb;

    zzef(zzee zzee, boolean z, zzch zzch, zzja zzja, long j, zzja zzja2, boolean z2) {
        this.zzlb = zzee;
        this.zzkx = z;
        this.zzgy = zzch;
        this.zzky = zzja;
        this.zzjh = j;
        this.zzkz = zzja2;
        this.zzla = z2;
    }

    public final /* synthetic */ Object call() throws Exception {
        if (this.zzkx) {
            this.zzlb.zzkp.zza(this.zzgy, this.zzky, this.zzjh);
        }
        this.zzlb.zzkr.zza(this.zzgy, this.zzkz, Long.valueOf(this.zzjh), this.zzla);
        return !this.zzla ? Collections.emptyList() : this.zzlb.zza(new zzfp(zzfn.zzmu, this.zzgy, this.zzkz));
    }
}
