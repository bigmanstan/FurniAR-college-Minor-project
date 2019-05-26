package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzgc {
    private static final zzgn<Map<zzhe, zzgb>> zznt = new zzgd();
    private static final zzgn<Map<zzhe, zzgb>> zznu = new zzge();
    private static final zzgn<zzgb> zznv = new zzgf();
    private static final zzgn<zzgb> zznw = new zzgg();
    private final zzhz zzbs;
    private final zzfw zzne;
    private zzgj<Map<zzhe, zzgb>> zznx;
    private final zzkf zzny;
    private long zznz = 0;

    public zzgc(zzfw zzfw, zzhz zzhz, zzkf zzkf) {
        this.zzne = zzfw;
        this.zzbs = zzhz;
        this.zzny = zzkf;
        this.zznx = new zzgj(null);
        try {
            this.zzne.beginTransaction();
            this.zzne.zzc(this.zzny.millis());
            this.zzne.setTransactionSuccessful();
            for (zzgb zzgb : this.zzne.zzl()) {
                this.zznz = Math.max(zzgb.id + 1, this.zznz);
                zzb(zzgb);
            }
        } finally {
            this.zzne.endTransaction();
        }
    }

    private final List<zzgb> zza(zzgn<zzgb> zzgn) {
        List<zzgb> arrayList = new ArrayList();
        Iterator it = this.zznx.iterator();
        while (it.hasNext()) {
            for (zzgb zzgb : ((Map) ((Entry) it.next()).getValue()).values()) {
                if (zzgn.zzd(zzgb)) {
                    arrayList.add(zzgb);
                }
            }
        }
        return arrayList;
    }

    private final void zza(zzgb zzgb) {
        zzb(zzgb);
        this.zzne.zza(zzgb);
    }

    private final boolean zzad(zzch zzch) {
        return this.zznx.zza(zzch, zznt) != null;
    }

    private final void zzb(zzgb zzgb) {
        boolean z;
        Map map;
        zzgb zzgb2;
        zzhh zzhh = zzgb.zznp;
        boolean z2 = false;
        if (zzhh.zzek()) {
            if (!zzhh.isDefault()) {
                z = false;
                zzkq.zza(z, "Can't have tracked non-default query that loads all data");
                map = (Map) this.zznx.zzai(zzgb.zznp.zzg());
                if (map == null) {
                    map = new HashMap();
                    this.zznx = this.zznx.zzb(zzgb.zznp.zzg(), (Object) map);
                }
                zzgb2 = (zzgb) map.get(zzgb.zznp.zzen());
                if (zzgb2 == null || zzgb2.id == zzgb.id) {
                    z2 = true;
                }
                zzkq.zzf(z2);
                map.put(zzgb.zznp.zzen(), zzgb);
            }
        }
        z = true;
        zzkq.zza(z, "Can't have tracked non-default query that loads all data");
        map = (Map) this.zznx.zzai(zzgb.zznp.zzg());
        if (map == null) {
            map = new HashMap();
            this.zznx = this.zznx.zzb(zzgb.zznp.zzg(), (Object) map);
        }
        zzgb2 = (zzgb) map.get(zzgb.zznp.zzen());
        z2 = true;
        zzkq.zzf(z2);
        map.put(zzgb.zznp.zzen(), zzgb);
    }

    private final void zzb(zzhh zzhh, boolean z) {
        zzhh zzj = zzj(zzhh);
        zzgb zzk = zzk(zzj);
        long millis = this.zzny.millis();
        if (zzk != null) {
            zzgb zzgb = new zzgb(zzk.id, zzk.zznp, millis, zzk.zznr, zzk.zzns);
            zzgb zzgb2 = new zzgb(zzgb.id, zzgb.zznp, zzgb.zznq, zzgb.zznr, z);
        } else {
            long j = r0.zznz;
            r0.zznz = 1 + j;
            zzk = new zzgb(j, zzj, millis, false, z);
        }
        zza(zzk);
    }

    private static zzhh zzj(zzhh zzhh) {
        return zzhh.zzek() ? zzhh.zzal(zzhh.zzg()) : zzhh;
    }

    public final zzfx zza(zzfq zzfq) {
        int i;
        List zza = zza(zznv);
        long size = (long) zza.size();
        size -= Math.min((long) Math.floor((double) (((float) size) * (1.0f - zzfq.zzde()))), zzfq.zzdf());
        zzfx zzfx = new zzfx();
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            int size2 = zza.size();
            StringBuilder stringBuilder = new StringBuilder(80);
            stringBuilder.append("Pruning old queries.  Prunable: ");
            stringBuilder.append(size2);
            stringBuilder.append(" Count to prune: ");
            stringBuilder.append(size);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        Collections.sort(zza, new zzgi(this));
        zzfx zzfx2 = zzfx;
        for (i = 0; ((long) i) < size; i++) {
            zzgb zzgb = (zzgb) zza.get(i);
            zzfx2 = zzfx2.zzx(zzgb.zznp.zzg());
            zzhh zzj = zzj(zzgb.zznp);
            this.zzne.zzb(zzk(zzj).id);
            Map map = (Map) this.zznx.zzai(zzj.zzg());
            map.remove(zzj.zzen());
            if (map.isEmpty()) {
                this.zznx = this.zznx.zzah(zzj.zzg());
            }
        }
        for (i = (int) size; i < zza.size(); i++) {
            zzfx2 = zzfx2.zzy(((zzgb) zza.get(i)).zznp.zzg());
        }
        List<zzgb> zza2 = zza(zznw);
        if (this.zzbs.zzfa()) {
            zzhz zzhz2 = this.zzbs;
            int size3 = zza2.size();
            StringBuilder stringBuilder2 = new StringBuilder(31);
            stringBuilder2.append("Unprunable queries: ");
            stringBuilder2.append(size3);
            zzhz2.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        for (zzgb zzgb2 : zza2) {
            zzfx2 = zzfx2.zzy(zzgb2.zznp.zzg());
        }
        return zzfx2;
    }

    public final Set<zzid> zzaa(zzch zzch) {
        Set<zzid> hashSet = new HashSet();
        Set hashSet2 = new HashSet();
        Map map = (Map) this.zznx.zzai(zzch);
        if (map != null) {
            for (zzgb zzgb : map.values()) {
                if (!zzgb.zznp.zzek()) {
                    hashSet2.add(Long.valueOf(zzgb.id));
                }
            }
        }
        if (!hashSet2.isEmpty()) {
            hashSet.addAll(this.zzne.zza(hashSet2));
        }
        Iterator it = this.zznx.zzag(zzch).zzdm().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzid zzid = (zzid) entry.getKey();
            zzgj zzgj = (zzgj) entry.getValue();
            if (zzgj.getValue() != null && zznt.zzd((Map) zzgj.getValue())) {
                hashSet.add(zzid);
            }
        }
        return hashSet;
    }

    public final void zzab(zzch zzch) {
        if (!zzad(zzch)) {
            zzhh zzal = zzhh.zzal(zzch);
            zzgb zzk = zzk(zzal);
            if (zzk == null) {
                long j = this.zznz;
                this.zznz = 1 + j;
                zzgb zzgb = new zzgb(j, zzal, this.zzny.millis(), true, false);
            } else {
                zzk = zzk.zzdi();
            }
            zza(zzk);
        }
    }

    public final boolean zzac(zzch zzch) {
        return this.zznx.zzb(zzch, zznu) != null;
    }

    public final long zzdj() {
        return (long) zza(zznv).size();
    }

    public final void zzg(zzhh zzhh) {
        zzb(zzhh, true);
    }

    public final void zzh(zzhh zzhh) {
        zzb(zzhh, false);
    }

    public final zzgb zzk(zzhh zzhh) {
        zzhh = zzj(zzhh);
        Map map = (Map) this.zznx.zzai(zzhh.zzg());
        return map != null ? (zzgb) map.get(zzhh.zzen()) : null;
    }

    public final void zzl(zzhh zzhh) {
        zzgb zzk = zzk(zzj(zzhh));
        if (zzk != null && !zzk.zznr) {
            zza(zzk.zzdi());
        }
    }

    public final boolean zzm(zzhh zzhh) {
        if (zzad(zzhh.zzg())) {
            return true;
        }
        if (zzhh.zzek()) {
            return false;
        }
        Map map = (Map) this.zznx.zzai(zzhh.zzg());
        return map != null && map.containsKey(zzhh.zzen()) && ((zzgb) map.get(zzhh.zzen())).zznr;
    }

    public final void zzz(zzch zzch) {
        this.zznx.zzag(zzch).zza(new zzgh(this));
    }
}
