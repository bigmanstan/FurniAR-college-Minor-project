package com.google.android.gms.internal.firebase_database;

final class zzhp implements zzhu {
    private final zzhk zzpu;
    private final zzfg zzqe;
    private final zzja zzqf;

    public zzhp(zzfg zzfg, zzhk zzhk, zzja zzja) {
        this.zzqe = zzfg;
        this.zzpu = zzhk;
        this.zzqf = zzja;
    }

    public final zziz zza(zzis zzis, zziz zziz, boolean z) {
        return this.zzqe.zza(this.zzqf != null ? this.zzqf : this.zzpu.zzeu(), zziz, z, zzis);
    }

    public final zzja zzh(zzid zzid) {
        zzgu zzer = this.zzpu.zzer();
        if (zzer.zzf(zzid)) {
            return zzer.zzd().zzm(zzid);
        }
        return this.zzqe.zza(zzid, this.zzqf != null ? new zzgu(zzit.zza(this.zzqf, zziu.zzgb()), true, false) : this.zzpu.zzet());
    }
}
