package com.google.android.gms.internal.firebase_database;

public final class zzhr implements zzht {
    private final zzis zzpd;

    public zzhr(zzis zzis) {
        this.zzpd = zzis;
    }

    public final zzit zza(zzit zzit, zzid zzid, zzja zzja, zzch zzch, zzhu zzhu, zzhq zzhq) {
        zzja zzd = zzit.zzd();
        zzja zzm = zzd.zzm(zzid);
        if (zzm.zzam(zzch).equals(zzja.zzam(zzch)) && zzm.isEmpty() == zzja.isEmpty()) {
            return zzit;
        }
        if (zzhq != null) {
            zzgw zzc;
            if (!zzja.isEmpty()) {
                zzc = zzm.isEmpty() ? zzgw.zzc(zzid, zzja) : zzgw.zza(zzid, zzja, zzm);
            } else if (zzd.zzk(zzid)) {
                zzc = zzgw.zzd(zzid, zzm);
            }
            zzhq.zza(zzc);
        }
        return (zzd.zzfk() && zzja.isEmpty()) ? zzit : zzit.zzg(zzid, zzja);
    }

    public final zzit zza(zzit zzit, zzit zzit2, zzhq zzhq) {
        if (zzhq != null) {
            for (zziz zziz : zzit.zzd()) {
                if (!zzit2.zzd().zzk(zziz.zzge())) {
                    zzhq.zza(zzgw.zzd(zziz.zzge(), zziz.zzd()));
                }
            }
            if (!zzit2.zzd().zzfk()) {
                for (zziz zziz2 : zzit2.zzd()) {
                    zzgw zza;
                    if (zzit.zzd().zzk(zziz2.zzge())) {
                        zzja zzm = zzit.zzd().zzm(zziz2.zzge());
                        if (!zzm.equals(zziz2.zzd())) {
                            zza = zzgw.zza(zziz2.zzge(), zziz2.zzd(), zzm);
                        }
                    } else {
                        zza = zzgw.zzc(zziz2.zzge(), zziz2.zzd());
                    }
                    zzhq.zza(zza);
                }
            }
        }
        return zzit2;
    }

    public final zzit zza(zzit zzit, zzja zzja) {
        return zzit.zzd().isEmpty() ? zzit : zzit.zzk(zzja);
    }

    public final zzis zzeg() {
        return this.zzpd;
    }

    public final zzht zzew() {
        return this;
    }

    public final boolean zzex() {
        return false;
    }
}
