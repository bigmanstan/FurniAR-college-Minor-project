package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzbv implements Iterable<Entry<zzch, zzja>> {
    private static final zzbv zzgw = new zzbv(new zzgj(null));
    private final zzgj<zzja> zzgx;

    private zzbv(zzgj<zzja> zzgj) {
        this.zzgx = zzgj;
    }

    private final zzja zza(zzch zzch, zzgj<zzja> zzgj, zzja zzja) {
        if (zzgj.getValue() != null) {
            return zzja.zzl(zzch, (zzja) zzgj.getValue());
        }
        zzja zzja2 = null;
        Iterator it = zzgj.zzdm().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzgj zzgj2 = (zzgj) entry.getValue();
            zzid zzid = (zzid) entry.getKey();
            if (zzid.zzfh()) {
                zzja2 = (zzja) zzgj2.getValue();
            } else {
                zzja = zza(zzch.zza(zzid), zzgj2, zzja);
            }
        }
        if (!(zzja.zzam(zzch).isEmpty() || zzja2 == null)) {
            zzja = zzja.zzl(zzch.zza(zzid.zzfe()), zzja2);
        }
        return zzja;
    }

    public static zzbv zzbf() {
        return zzgw;
    }

    public static zzbv zzf(Map<String, Object> map) {
        zzgj zzdl = zzgj.zzdl();
        for (Entry entry : map.entrySet()) {
            zzdl = zzdl.zza(new zzch((String) entry.getKey()), new zzgj(zzjd.zza(entry.getValue(), zzir.zzfv())));
        }
        return new zzbv(zzdl);
    }

    public static zzbv zzg(Map<zzch, zzja> map) {
        zzgj zzdl = zzgj.zzdl();
        for (Entry entry : map.entrySet()) {
            zzdl = zzdl.zza((zzch) entry.getKey(), new zzgj((zzja) entry.getValue()));
        }
        return new zzbv(zzdl);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                return ((zzbv) obj).zzd(true).equals(zzd(true));
            }
        }
        return false;
    }

    public final int hashCode() {
        return zzd(true).hashCode();
    }

    public final boolean isEmpty() {
        return this.zzgx.isEmpty();
    }

    public final Iterator<Entry<zzch, zzja>> iterator() {
        return this.zzgx.iterator();
    }

    public final String toString() {
        String obj = zzd(true).toString();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(obj).length() + 15);
        stringBuilder.append("CompoundWrite{");
        stringBuilder.append(obj);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final zzbv zza(zzid zzid, zzja zzja) {
        return zze(new zzch(zzid), zzja);
    }

    public final zzbv zzb(zzch zzch, zzbv zzbv) {
        return (zzbv) zzbv.zzgx.zzb((Object) this, new zzbw(this, zzch));
    }

    public final zzja zzb(zzja zzja) {
        return zza(zzch.zzbt(), this.zzgx, zzja);
    }

    public final zzja zzbg() {
        return (zzja) this.zzgx.getValue();
    }

    public final List<zziz> zzbh() {
        List<zziz> arrayList = new ArrayList();
        Iterator it;
        if (this.zzgx.getValue() != null) {
            for (zziz zziz : (zzja) this.zzgx.getValue()) {
                arrayList.add(new zziz(zziz.zzge(), zziz.zzd()));
            }
        } else {
            it = this.zzgx.zzdm().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                zzgj zzgj = (zzgj) entry.getValue();
                if (zzgj.getValue() != null) {
                    arrayList.add(new zziz((zzid) entry.getKey(), (zzja) zzgj.getValue()));
                }
            }
        }
        return arrayList;
    }

    public final Map<zzid, zzbv> zzbi() {
        Map<zzid, zzbv> hashMap = new HashMap();
        Iterator it = this.zzgx.zzdm().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            hashMap.put((zzid) entry.getKey(), new zzbv((zzgj) entry.getValue()));
        }
        return hashMap;
    }

    public final zzbv zzd(zzch zzch) {
        return zzch.isEmpty() ? zzgw : new zzbv(this.zzgx.zza(zzch, zzgj.zzdl()));
    }

    public final Map<String, Object> zzd(boolean z) {
        Map<String, Object> hashMap = new HashMap();
        this.zzgx.zza(new zzbx(this, hashMap, true));
        return hashMap;
    }

    public final zzbv zze(zzch zzch, zzja zzja) {
        if (zzch.isEmpty()) {
            return new zzbv(new zzgj(zzja));
        }
        zzch zzae = this.zzgx.zzae(zzch);
        if (zzae != null) {
            zzch = zzch.zza(zzae, zzch);
            zzja zzja2 = (zzja) this.zzgx.zzai(zzae);
            zzid zzbz = zzch.zzbz();
            if (zzbz != null && zzbz.zzfh() && zzja2.zzam(zzch.zzby()).isEmpty()) {
                return this;
            }
            return new zzbv(this.zzgx.zzb(zzae, zzja2.zzl(zzch, zzja)));
        }
        return new zzbv(this.zzgx.zza(zzch, new zzgj(zzja)));
    }

    public final boolean zze(zzch zzch) {
        return zzf(zzch) != null;
    }

    public final zzja zzf(zzch zzch) {
        zzch zzae = this.zzgx.zzae(zzch);
        return zzae != null ? ((zzja) this.zzgx.zzai(zzae)).zzam(zzch.zza(zzae, zzch)) : null;
    }

    public final zzbv zzg(zzch zzch) {
        if (zzch.isEmpty()) {
            return this;
        }
        zzja zzf = zzf(zzch);
        return zzf != null ? new zzbv(new zzgj(zzf)) : new zzbv(this.zzgx.zzag(zzch));
    }
}
