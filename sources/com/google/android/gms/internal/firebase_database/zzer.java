package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

final class zzer implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzex zzlc;

    zzer(zzee zzee, zzex zzex) {
        this.zzlb = zzee;
        this.zzlc = zzex;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzhh zza = this.zzlb.zzb(this.zzlc);
        if (zza == null) {
            return Collections.emptyList();
        }
        this.zzlb.zzkp.zzi(zza);
        return this.zzlb.zza(zza, new zzfj(zzfn.zzc(zza.zzen()), zzch.zzbt()));
    }
}
