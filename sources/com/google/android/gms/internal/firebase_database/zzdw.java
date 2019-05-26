package com.google.android.gms.internal.firebase_database;

import java.util.Map;

final class zzdw extends zzii {
    private final /* synthetic */ Map zzkg;
    private final /* synthetic */ zzdx zzkh;

    zzdw(Map map, zzdx zzdx) {
        this.zzkg = map;
        this.zzkh = zzdx;
    }

    public final void zzb(zzid zzid, zzja zzja) {
        zzja zza = zzdu.zza(zzja, this.zzkg);
        if (zza != zzja) {
            this.zzkh.zzg(new zzch(zzid.zzfg()), zza);
        }
    }
}
