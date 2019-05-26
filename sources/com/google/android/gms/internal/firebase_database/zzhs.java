package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;

public final class zzhs implements zzht {
    private final int limit;
    private final zzis zzpd;
    private final zzhv zzqh;
    private final boolean zzqi;

    public zzhs(zzhe zzhe) {
        this.zzqh = new zzhv(zzhe);
        this.zzpd = zzhe.zzeg();
        this.limit = zzhe.getLimit();
        this.zzqi = zzhe.zzei() ^ 1;
    }

    public final zzit zza(zzit zzit, zzid zzid, zzja zzja, zzch zzch, zzhu zzhu, zzhq zzhq) {
        if (!this.zzqh.zza(new zziz(zzid, zzja))) {
            zzja = zzir.zzfv();
        }
        zzja zzja2 = zzja;
        if (zzit.zzd().zzm(zzid).equals(zzja2)) {
            return zzit;
        }
        if (zzit.zzd().getChildCount() < this.limit) {
            return this.zzqh.zzew().zza(zzit, zzid, zzja2, zzch, zzhu, zzhq);
        }
        zziz zziz = new zziz(zzid, zzja2);
        zziz zzfz = this.zzqi ? zzit.zzfz() : zzit.zzga();
        boolean zza = this.zzqh.zza(zziz);
        if (zzit.zzd().zzk(zzid)) {
            zzja zzm = zzit.zzd().zzm(zzid);
            while (true) {
                zzfz = zzhu.zza(this.zzpd, zzfz, this.zzqi);
                if (zzfz != null) {
                    if (!zzfz.zzge().equals(zzid)) {
                        if (!zzit.zzd().zzk(zzfz.zzge())) {
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            Object obj = 1;
            Object obj2 = (!zza || zzja2.isEmpty() || (zzfz == null ? 1 : this.zzpd.zza(zzfz, zziz, this.zzqi)) < 0) ? null : 1;
            if (obj2 != null) {
                if (zzhq != null) {
                    zzhq.zza(zzgw.zza(zzid, zzja2, zzm));
                }
                return zzit.zzg(zzid, zzja2);
            }
            if (zzhq != null) {
                zzhq.zza(zzgw.zzd(zzid, zzm));
            }
            zzit = zzit.zzg(zzid, zzir.zzfv());
            if (zzfz == null || !this.zzqh.zza(zzfz)) {
                obj = null;
            }
            if (obj != null) {
                if (zzhq != null) {
                    zzhq.zza(zzgw.zzc(zzfz.zzge(), zzfz.zzd()));
                }
                zzit = zzit.zzg(zzfz.zzge(), zzfz.zzd());
            }
            return zzit;
        }
        if (!zzja2.isEmpty() && zza && this.zzpd.zza(zzfz, zziz, this.zzqi) >= 0) {
            if (zzhq != null) {
                zzhq.zza(zzgw.zzd(zzfz.zzge(), zzfz.zzd()));
                zzhq.zza(zzgw.zzc(zzid, zzja2));
            }
            zzit = zzit.zzg(zzid, zzja2).zzg(zzfz.zzge(), zzir.zzfv());
        }
        return zzit;
    }

    public final zzit zza(zzit zzit, zzit zzit2, zzhq zzhq) {
        zzit zzk;
        if (!zzit2.zzd().zzfk()) {
            if (!zzit2.zzd().isEmpty()) {
                Iterator reverseIterator;
                Object zzey;
                int i;
                zzk = zzit2.zzk(zzir.zzfv());
                Object zzez;
                if (this.zzqi) {
                    reverseIterator = zzit2.reverseIterator();
                    zzez = this.zzqh.zzez();
                    zzey = this.zzqh.zzey();
                    i = -1;
                } else {
                    reverseIterator = zzit2.iterator();
                    zzez = this.zzqh.zzey();
                    zzey = this.zzqh.zzez();
                    i = 1;
                }
                int i2 = 0;
                int i3 = i2;
                while (reverseIterator.hasNext()) {
                    zziz zziz = (zziz) reverseIterator.next();
                    if (i2 == 0 && this.zzpd.compare(r1, zziz) * i <= 0) {
                        i2 = 1;
                    }
                    Object obj = (i2 == 0 || i3 >= this.limit || this.zzpd.compare(zziz, zzey) * i > 0) ? null : 1;
                    if (obj != null) {
                        i3++;
                    } else {
                        zzk = zzk.zzg(zziz.zzge(), zzir.zzfv());
                    }
                }
                return this.zzqh.zzew().zza(zzit, zzk, zzhq);
            }
        }
        zzk = zzit.zza(zzir.zzfv(), this.zzpd);
        return this.zzqh.zzew().zza(zzit, zzk, zzhq);
    }

    public final zzit zza(zzit zzit, zzja zzja) {
        return zzit;
    }

    public final zzis zzeg() {
        return this.zzpd;
    }

    public final zzht zzew() {
        return this.zzqh.zzew();
    }

    public final boolean zzex() {
        return true;
    }
}
