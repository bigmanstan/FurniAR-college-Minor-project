package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

final class zzeg implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzex zzlc;
    private final /* synthetic */ Map zzld;

    zzeg(zzee zzee, zzex zzex, zzch zzch, Map map) {
        this.zzlb = zzee;
        this.zzlc = zzex;
        this.zzgy = zzch;
        this.zzld = map;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzhh zza = this.zzlb.zzb(this.zzlc);
        if (zza == null) {
            return Collections.emptyList();
        }
        zzch zza2 = zzch.zza(zza.zzg(), this.zzgy);
        zzbv zzg = zzbv.zzg(this.zzld);
        this.zzlb.zzkp.zzd(this.zzgy, zzg);
        return this.zzlb.zza(zza, new zzfk(zzfn.zzc(zza.zzen()), zza2, zzg));
    }
}
