package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class zzhi {
    private final zzhh zzpc;
    private final zzhl zzpt;
    private zzhk zzpu;
    private final List<zzce> zzpv = new ArrayList();
    private final zzha zzpw;

    public zzhi(zzhh zzhh, zzhk zzhk) {
        this.zzpc = zzhh;
        zzhr zzhr = new zzhr(zzhh.zzeg());
        zzht zzem = zzhh.zzen().zzem();
        this.zzpt = new zzhl(zzem);
        zzgu zzet = zzhk.zzet();
        zzgu zzer = zzhk.zzer();
        zzit zza = zzit.zza(zzir.zzfv(), zzhh.zzeg());
        zzit zza2 = zzhr.zza(zza, zzet.zzdq(), null);
        zza = zzem.zza(zza, zzer.zzdq(), null);
        this.zzpu = new zzhk(new zzgu(zza, zzer.zzdo(), zzem.zzex()), new zzgu(zza2, zzet.zzdo(), false));
        this.zzpw = new zzha(zzhh);
    }

    private final List<zzgx> zza(List<zzgw> list, zzit zzit, zzce zzce) {
        List list2;
        if (zzce == null) {
            list2 = this.zzpv;
        } else {
            list2 = Arrays.asList(new zzce[]{zzce});
        }
        return this.zzpw.zza(list, zzit, list2);
    }

    public final boolean isEmpty() {
        return this.zzpv.isEmpty();
    }

    public final List<zzgy> zza(zzce zzce, DatabaseError databaseError) {
        List<zzgy> arrayList;
        if (databaseError != null) {
            arrayList = new ArrayList();
            zzch zzg = this.zzpc.zzg();
            for (zzce zzgv : this.zzpv) {
                zzce zzgv2;
                arrayList.add(new zzgv(zzgv2, databaseError, zzg));
            }
        } else {
            arrayList = Collections.emptyList();
        }
        if (zzce != null) {
            int i = 0;
            int i2 = -1;
            while (i < this.zzpv.size()) {
                zzgv2 = (zzce) this.zzpv.get(i);
                if (zzgv2.zzc(zzce)) {
                    if (zzgv2.zzbs()) {
                        break;
                    }
                    i2 = i;
                }
                i++;
            }
            i = i2;
            if (i != -1) {
                zzce = (zzce) this.zzpv.get(i);
                this.zzpv.remove(i);
                zzce.zzbr();
            }
        } else {
            for (zzce zzbr : this.zzpv) {
                zzbr.zzbr();
            }
            this.zzpv.clear();
        }
        return arrayList;
    }

    public final zzhj zzb(zzfl zzfl, zzfg zzfg, zzja zzja) {
        if (zzfl.zzcz() == zzfm.Merge) {
            zzfl.zzcy().zzdc();
        }
        zzho zza = this.zzpt.zza(this.zzpu, zzfl, zzfg, zzja);
        this.zzpu = zza.zzpu;
        return new zzhj(zza(zza.zzpy, zza.zzpu.zzer().zzdq(), null), zza.zzpy);
    }

    public final void zzb(zzce zzce) {
        this.zzpv.add(zzce);
    }

    public final zzhh zzeo() {
        return this.zzpc;
    }

    public final zzja zzep() {
        return this.zzpu.zzet().zzd();
    }

    public final zzja zzeq() {
        return this.zzpu.zzer().zzd();
    }

    public final List<zzgx> zzk(zzce zzce) {
        zzgu zzer = this.zzpu.zzer();
        List arrayList = new ArrayList();
        for (zziz zziz : zzer.zzd()) {
            arrayList.add(zzgw.zzc(zziz.zzge(), zziz.zzd()));
        }
        if (zzer.zzdo()) {
            arrayList.add(zzgw.zza(zzer.zzdq()));
        }
        return zza(arrayList, zzer.zzdq(), zzce);
    }

    public final zzja zzr(zzch zzch) {
        zzja zzeu = this.zzpu.zzeu();
        return (zzeu == null || (!this.zzpc.zzek() && (zzch.isEmpty() || zzeu.zzm(zzch.zzbw()).isEmpty()))) ? null : zzeu.zzam(zzch);
    }
}
