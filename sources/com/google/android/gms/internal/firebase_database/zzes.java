package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

final class zzes implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzex zzlc;
    private final /* synthetic */ zzja zzln;

    zzes(zzee zzee, zzex zzex, zzch zzch, zzja zzja) {
        this.zzlb = zzee;
        this.zzlc = zzex;
        this.zzgy = zzch;
        this.zzln = zzja;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzhh zza = this.zzlb.zzb(this.zzlc);
        if (zza == null) {
            return Collections.emptyList();
        }
        zzch zza2 = zzch.zza(zza.zzg(), this.zzgy);
        this.zzlb.zzkp.zza(zza2.isEmpty() ? zza : zzhh.zzal(this.zzgy), this.zzln);
        return this.zzlb.zza(zza, new zzfp(zzfn.zzc(zza.zzen()), zza2, this.zzln));
    }
}
