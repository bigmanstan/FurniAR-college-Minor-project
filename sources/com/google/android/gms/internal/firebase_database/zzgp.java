package com.google.android.gms.internal.firebase_database;

import java.util.Map.Entry;

public final class zzgp<T> {
    private zzid zzog;
    private zzgp<T> zzoh;
    private zzgt<T> zzoi;

    public zzgp() {
        this(null, null, new zzgt());
    }

    private zzgp(zzid zzid, zzgp<T> zzgp, zzgt<T> zzgt) {
        this.zzog = zzid;
        this.zzoh = zzgp;
        this.zzoi = zzgt;
    }

    private final void zzdn() {
        zzgp zzgp = this;
        while (zzgp.zzoh != null) {
            zzgp zzgp2 = zzgp.zzoh;
            zzid zzid = zzgp.zzog;
            Object obj = (zzgp.zzoi.value == null && zzgp.zzoi.zzkk.isEmpty()) ? 1 : null;
            boolean containsKey = zzgp2.zzoi.zzkk.containsKey(zzid);
            if (obj != null && containsKey) {
                zzgp2.zzoi.zzkk.remove(zzid);
            } else if (obj == null && !containsKey) {
                zzgp2.zzoi.zzkk.put(zzid, zzgp.zzoi);
            } else {
                return;
            }
            zzgp = zzgp2;
        }
    }

    public final T getValue() {
        return this.zzoi.value;
    }

    public final boolean hasChildren() {
        return !this.zzoi.zzkk.isEmpty();
    }

    public final void setValue(T t) {
        this.zzoi.value = t;
        zzdn();
    }

    public final String toString() {
        String str = "";
        String zzfg = this.zzog == null ? "<anon>" : this.zzog.zzfg();
        String zzgt = this.zzoi.toString(String.valueOf(str).concat("\t"));
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 1) + String.valueOf(zzfg).length()) + String.valueOf(zzgt).length());
        stringBuilder.append(str);
        stringBuilder.append(zzfg);
        stringBuilder.append("\n");
        stringBuilder.append(zzgt);
        return stringBuilder.toString();
    }

    public final void zza(zzgs<T> zzgs) {
        Object[] toArray = this.zzoi.zzkk.entrySet().toArray();
        for (Object obj : toArray) {
            Entry entry = (Entry) obj;
            zzgs.zzd(new zzgp((zzid) entry.getKey(), this, (zzgt) entry.getValue()));
        }
    }

    public final void zza(zzgs<T> zzgs, boolean z, boolean z2) {
        if (z && !z2) {
            zzgs.zzd(this);
        }
        zza(new zzgq(this, zzgs, z2));
        if (z && z2) {
            zzgs.zzd(this);
        }
    }

    public final boolean zza(zzgr<T> zzgr, boolean z) {
        for (zzgp zzgp = this.zzoh; zzgp != null; zzgp = zzgp.zzoh) {
            zzgr.zze(zzgp);
        }
        return false;
    }

    public final zzgp<T> zzaj(zzch zzch) {
        zzid zzbw = zzch.zzbw();
        zzch zzch2 = zzch;
        zzgp<T> zzgp = this;
        while (zzbw != null) {
            zzgp<T> zzgp2 = new zzgp(zzbw, zzgp, zzgp.zzoi.zzkk.containsKey(zzbw) ? (zzgt) zzgp.zzoi.zzkk.get(zzbw) : new zzgt());
            zzch2 = zzch2.zzbx();
            zzbw = zzch2.zzbw();
            zzgp = zzgp2;
        }
        return zzgp;
    }

    public final zzch zzg() {
        if (this.zzoh != null) {
            return this.zzoh.zzg().zza(this.zzog);
        }
        if (this.zzog == null) {
            return zzch.zzbt();
        }
        return new zzch(this.zzog);
    }
}
