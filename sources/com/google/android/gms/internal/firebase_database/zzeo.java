package com.google.android.gms.internal.firebase_database;

import java.util.List;
import java.util.concurrent.Callable;

final class zzeo implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzja zzkz;
    private final /* synthetic */ zzee zzlb;

    zzeo(zzee zzee, zzch zzch, zzja zzja) {
        this.zzlb = zzee;
        this.zzgy = zzch;
        this.zzkz = zzja;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzlb.zzkp.zza(zzhh.zzal(this.zzgy), this.zzkz);
        return this.zzlb.zza(new zzfp(zzfn.zzmv, this.zzgy, this.zzkz));
    }
}
