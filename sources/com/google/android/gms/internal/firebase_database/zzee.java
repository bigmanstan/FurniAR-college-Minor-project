package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzee {
    private final zzhz zzbs;
    private final zzfv zzkp;
    private zzgj<zzed> zzkq = zzgj.zzdl();
    private final zzfd zzkr = new zzfd();
    private final Map<zzex, zzhh> zzks = new HashMap();
    private final Map<zzhh, zzex> zzkt = new HashMap();
    private final Set<zzhh> zzku = new HashSet();
    private final zzew zzkv;
    private long zzkw = 1;

    public zzee(zzbz zzbz, zzfv zzfv, zzew zzew) {
        this.zzkv = zzew;
        this.zzkp = zzfv;
        this.zzbs = zzbz.zzp("SyncTree");
    }

    private final List<zzgy> zza(zzfl zzfl) {
        return zza(zzfl, this.zzkq, null, this.zzkr.zzt(zzch.zzbt()));
    }

    private final List<zzgy> zza(zzfl zzfl, zzgj<zzed> zzgj, zzja zzja, zzfg zzfg) {
        if (zzfl.zzg().isEmpty()) {
            return zzb(zzfl, zzgj, zzja, zzfg);
        }
        zzed zzed = (zzed) zzgj.getValue();
        if (zzja == null && zzed != null) {
            zzja = zzed.zzr(zzch.zzbt());
        }
        List<zzgy> arrayList = new ArrayList();
        zzid zzbw = zzfl.zzg().zzbw();
        zzfl zzc = zzfl.zzc(zzbw);
        zzgj zzgj2 = (zzgj) zzgj.zzdm().get(zzbw);
        if (!(zzgj2 == null || zzc == null)) {
            arrayList.addAll(zza(zzc, zzgj2, zzja != null ? zzja.zzm(zzbw) : null, zzfg.zzb(zzbw)));
        }
        if (zzed != null) {
            arrayList.addAll(zzed.zza(zzfl, zzfg, zzja));
        }
        return arrayList;
    }

    private final List<zzhi> zza(zzgj<zzed> zzgj) {
        List arrayList = new ArrayList();
        zza((zzgj) zzgj, arrayList);
        return arrayList;
    }

    private final List<? extends zzgy> zza(zzhh zzhh, zzfl zzfl) {
        zzch zzg = zzhh.zzg();
        return ((zzed) this.zzkq.zzai(zzg)).zza(zzfl, this.zzkr.zzt(zzg), null);
    }

    private final void zza(zzgj<zzed> zzgj, List<zzhi> list) {
        zzed zzed = (zzed) zzgj.getValue();
        if (zzed == null || !zzed.zzci()) {
            if (zzed != null) {
                list.addAll(zzed.zzch());
            }
            Iterator it = zzgj.zzdm().iterator();
            while (it.hasNext()) {
                zza((zzgj) ((Entry) it.next()).getValue(), (List) list);
            }
            return;
        }
        list.add(zzed.zzcj());
    }

    private final void zza(zzhh zzhh, zzhi zzhi) {
        zzch zzg = zzhh.zzg();
        zzex zze = zze(zzhh);
        Object zzev = new zzev(this, zzhi);
        this.zzkv.zza(zzd(zzhh), zze, zzev, zzev);
        zzgj zzag = this.zzkq.zzag(zzg);
        if (zze == null) {
            zzag.zza(new zzej(this));
        }
    }

    private final zzhh zzb(zzex zzex) {
        return (zzhh) this.zzks.get(zzex);
    }

    private final List<zzgy> zzb(zzfl zzfl, zzgj<zzed> zzgj, zzja zzja, zzfg zzfg) {
        zzed zzed = (zzed) zzgj.getValue();
        if (zzja == null && zzed != null) {
            zzja = zzed.zzr(zzch.zzbt());
        }
        List<zzgy> arrayList = new ArrayList();
        zzgj.zzdm().inOrderTraversal(new zzek(this, zzja, zzfg, zzfl, arrayList));
        if (zzed != null) {
            arrayList.addAll(zzed.zza(zzfl, zzfg, zzja));
        }
        return arrayList;
    }

    private final List<zzgy> zzb(zzhh zzhh, zzce zzce, DatabaseError databaseError) {
        return (List) this.zzkp.zza(new zzei(this, zzhh, zzce, databaseError));
    }

    private final zzex zzcl() {
        long j = this.zzkw;
        this.zzkw = 1 + j;
        return new zzex(j);
    }

    private static zzhh zzd(zzhh zzhh) {
        return (!zzhh.zzek() || zzhh.isDefault()) ? zzhh : zzhh.zzal(zzhh.zzg());
    }

    private final void zzd(List<zzhh> list) {
        for (zzhh zzhh : list) {
            if (!zzhh.zzek()) {
                zzex zze = zze(zzhh);
                this.zzkt.remove(zzhh);
                this.zzks.remove(zze);
            }
        }
    }

    private final zzex zze(zzhh zzhh) {
        return (zzex) this.zzkt.get(zzhh);
    }

    public final boolean isEmpty() {
        return this.zzkq.isEmpty();
    }

    public final List<? extends zzgy> zza(long j, boolean z, boolean z2, zzkf zzkf) {
        return (List) this.zzkp.zza(new zzem(this, z2, j, z, zzkf));
    }

    public final List<? extends zzgy> zza(zzch zzch, zzbv zzbv, zzbv zzbv2, long j, boolean z) {
        return (List) this.zzkp.zza(new zzel(this, z, zzch, zzbv, j, zzbv2));
    }

    public final List<? extends zzgy> zza(zzch zzch, zzja zzja, zzex zzex) {
        return (List) this.zzkp.zza(new zzes(this, zzex, zzch, zzja));
    }

    public final List<? extends zzgy> zza(zzch zzch, zzja zzja, zzja zzja2, long j, boolean z, boolean z2) {
        boolean z3;
        if (!z) {
            if (z2) {
                z3 = false;
                zzkq.zza(z3, "We shouldn't be persisting non-visible writes.");
                return (List) this.zzkp.zza(new zzef(this, z2, zzch, zzja, j, zzja2, z));
            }
        }
        z3 = true;
        zzkq.zza(z3, "We shouldn't be persisting non-visible writes.");
        return (List) this.zzkp.zza(new zzef(this, z2, zzch, zzja, j, zzja2, z));
    }

    public final List<? extends zzgy> zza(zzch zzch, List<zzjh> list, zzex zzex) {
        zzhh zzb = zzb(zzex);
        if (zzb == null) {
            return Collections.emptyList();
        }
        zzja zzep = ((zzed) this.zzkq.zzai(zzb.zzg())).zzb(zzb).zzep();
        for (zzjh zzm : list) {
            zzep = zzm.zzm(zzep);
        }
        return zza(zzch, zzep, zzex);
    }

    public final List<? extends zzgy> zza(zzch zzch, Map<zzch, zzja> map) {
        return (List) this.zzkp.zza(new zzep(this, map, zzch));
    }

    public final List<? extends zzgy> zza(zzch zzch, Map<zzch, zzja> map, zzex zzex) {
        return (List) this.zzkp.zza(new zzeg(this, zzex, zzch, map));
    }

    public final List<? extends zzgy> zza(zzex zzex) {
        return (List) this.zzkp.zza(new zzer(this, zzex));
    }

    public final List<zzgy> zza(zzhh zzhh, DatabaseError databaseError) {
        return zzb(zzhh, null, databaseError);
    }

    public final void zza(zzhh zzhh, boolean z) {
        if (!z || this.zzku.contains(zzhh)) {
            if (!z && this.zzku.contains(zzhh)) {
                zzh(new zzeu(zzhh));
                this.zzku.remove(zzhh);
            }
            return;
        }
        zzg(new zzeu(zzhh));
        this.zzku.add(zzhh);
    }

    public final List<? extends zzgy> zzb(zzch zzch, List<zzjh> list) {
        zzed zzed = (zzed) this.zzkq.zzai(zzch);
        if (zzed == null) {
            return Collections.emptyList();
        }
        zzhi zzcj = zzed.zzcj();
        if (zzcj == null) {
            return Collections.emptyList();
        }
        zzja zzep = zzcj.zzep();
        for (zzjh zzm : list) {
            zzep = zzm.zzm(zzep);
        }
        return zzi(zzch, zzep);
    }

    public final zzja zzc(zzch zzch, List<Long> list) {
        zzgj zzgj = this.zzkq;
        zzgj.getValue();
        zzch zzbt = zzch.zzbt();
        zzja zzja = null;
        zzgj zzgj2 = zzgj;
        zzch zzch2 = zzch;
        do {
            zzid zzbw = zzch2.zzbw();
            zzch2 = zzch2.zzbx();
            zzbt = zzbt.zza(zzbw);
            zzch zza = zzch.zza(zzbt, zzch);
            zzgj2 = zzbw != null ? zzgj2.zze(zzbw) : zzgj.zzdl();
            zzed zzed = (zzed) zzgj2.getValue();
            if (zzed != null) {
                zzja = zzed.zzr(zza);
            }
            if (zzch2.isEmpty()) {
                break;
            }
        } while (zzja == null);
        return this.zzkr.zza(zzch, zzja, (List) list, true);
    }

    public final List<? extends zzgy> zzck() {
        return (List) this.zzkp.zza(new zzen(this));
    }

    public final List<? extends zzgy> zzg(zzce zzce) {
        return (List) this.zzkp.zza(new zzeh(this, zzce));
    }

    public final List<zzgy> zzh(zzce zzce) {
        return zzb(zzce.zzbe(), zzce, null);
    }

    public final List<? extends zzgy> zzi(zzch zzch, zzja zzja) {
        return (List) this.zzkp.zza(new zzeo(this, zzch, zzja));
    }

    public final List<? extends zzgy> zzs(zzch zzch) {
        return (List) this.zzkp.zza(new zzeq(this, zzch));
    }
}
