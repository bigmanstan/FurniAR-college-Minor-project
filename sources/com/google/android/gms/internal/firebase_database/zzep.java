package com.google.android.gms.internal.firebase_database;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

final class zzep implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ Map zzld;

    zzep(zzee zzee, Map map, zzch zzch) {
        this.zzlb = zzee;
        this.zzld = map;
        this.zzgy = zzch;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzbv zzg = zzbv.zzg(this.zzld);
        this.zzlb.zzkp.zzd(this.zzgy, zzg);
        return this.zzlb.zza(new zzfk(zzfn.zzmv, this.zzgy, zzg));
    }
}
