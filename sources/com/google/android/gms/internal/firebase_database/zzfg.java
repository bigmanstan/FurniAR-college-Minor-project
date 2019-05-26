package com.google.android.gms.internal.firebase_database;

import java.util.Collections;

public final class zzfg {
    private final zzch zzmg;
    private final zzfd zzmh;

    public zzfg(zzch zzch, zzfd zzfd) {
        this.zzmg = zzch;
        this.zzmh = zzfd;
    }

    public final zziz zza(zzja zzja, zziz zziz, boolean z, zzis zzis) {
        return this.zzmh.zza(this.zzmg, zzja, zziz, z, zzis);
    }

    public final zzja zza(zzch zzch, zzja zzja, zzja zzja2) {
        return this.zzmh.zza(this.zzmg, zzch, zzja, zzja2);
    }

    public final zzja zza(zzid zzid, zzgu zzgu) {
        return this.zzmh.zza(this.zzmg, zzid, zzgu);
    }

    public final zzfg zzb(zzid zzid) {
        return new zzfg(this.zzmg.zza(zzid), this.zzmh);
    }

    public final zzja zzc(zzja zzja) {
        return this.zzmh.zza(this.zzmg, zzja, Collections.emptyList(), false);
    }

    public final zzja zzd(zzja zzja) {
        return this.zzmh.zzj(this.zzmg, zzja);
    }

    public final zzja zzu(zzch zzch) {
        return this.zzmh.zzu(this.zzmg.zzh(zzch));
    }
}
