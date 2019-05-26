package com.google.android.gms.internal.firebase_database;

import java.util.List;
import java.util.concurrent.Callable;

final class zzeq implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzee zzlb;

    zzeq(zzee zzee, zzch zzch) {
        this.zzlb = zzee;
        this.zzgy = zzch;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzlb.zzkp.zzi(zzhh.zzal(this.zzgy));
        return this.zzlb.zza(new zzfj(zzfn.zzmv, this.zzgy));
    }
}
