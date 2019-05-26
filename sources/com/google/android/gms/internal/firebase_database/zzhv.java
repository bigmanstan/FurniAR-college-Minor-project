package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;

public final class zzhv implements zzht {
    private final zzis zzpd;
    private final zzhr zzqj;
    private final zziz zzqk;
    private final zziz zzql;

    public zzhv(zzhe zzhe) {
        zziz zzf;
        zziz zzf2;
        this.zzqj = new zzhr(zzhe.zzeg());
        this.zzpd = zzhe.zzeg();
        if (zzhe.zzdy()) {
            zzf = zzhe.zzeg().zzf(zzhe.zzea(), zzhe.zzdz());
        } else {
            zzhe.zzeg();
            zzf = zziz.zzgc();
        }
        this.zzqk = zzf;
        if (zzhe.zzeb()) {
            zzf2 = zzhe.zzeg().zzf(zzhe.zzed(), zzhe.zzec());
        } else {
            zzf2 = zzhe.zzeg().zzfw();
        }
        this.zzql = zzf2;
    }

    public final zzit zza(zzit zzit, zzid zzid, zzja zzja, zzch zzch, zzhu zzhu, zzhq zzhq) {
        if (!zza(new zziz(zzid, zzja))) {
            zzja = zzir.zzfv();
        }
        return this.zzqj.zza(zzit, zzid, zzja, zzch, zzhu, zzhq);
    }

    public final zzit zza(zzit zzit, zzit zzit2, zzhq zzhq) {
        if (zzit2.zzd().zzfk()) {
            zzit2 = zzit.zza(zzir.zzfv(), this.zzpd);
        } else {
            zzit zzk = zzit2.zzk(zzir.zzfv());
            Iterator it = zzit2.iterator();
            while (it.hasNext()) {
                zziz zziz = (zziz) it.next();
                if (!zza(zziz)) {
                    zzk = zzk.zzg(zziz.zzge(), zzir.zzfv());
                }
            }
            zzit2 = zzk;
        }
        return this.zzqj.zza(zzit, zzit2, zzhq);
    }

    public final zzit zza(zzit zzit, zzja zzja) {
        return zzit;
    }

    public final boolean zza(zziz zziz) {
        return this.zzpd.compare(this.zzqk, zziz) <= 0 && this.zzpd.compare(zziz, this.zzql) <= 0;
    }

    public final zzis zzeg() {
        return this.zzpd;
    }

    public final zzht zzew() {
        return this.zzqj;
    }

    public final boolean zzex() {
        return true;
    }

    public final zziz zzey() {
        return this.zzqk;
    }

    public final zziz zzez() {
        return this.zzql;
    }
}
