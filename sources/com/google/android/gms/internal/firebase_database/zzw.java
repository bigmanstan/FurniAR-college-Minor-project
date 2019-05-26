package com.google.android.gms.internal.firebase_database;

import java.util.List;

final class zzw implements zzgm<Void, Void> {
    private final /* synthetic */ zzgj zzbv;
    private final /* synthetic */ List zzbw;
    private final /* synthetic */ zzch zzbx;
    private final /* synthetic */ zzja zzby;

    zzw(zzu zzu, zzgj zzgj, List list, zzch zzch, zzja zzja) {
        this.zzbv = zzgj;
        this.zzbw = list;
        this.zzbx = zzch;
        this.zzby = zzja;
    }

    public final /* synthetic */ Object zza(zzch zzch, Object obj, Object obj2) {
        if (this.zzbv.zzai(zzch) == null) {
            this.zzbw.add(new zzkn(this.zzbx.zzh(zzch), this.zzby.zzam(zzch)));
        }
        return null;
    }
}
