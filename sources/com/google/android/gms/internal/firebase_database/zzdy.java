package com.google.android.gms.internal.firebase_database;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class zzdy {
    private zzja zzkj = null;
    private Map<zzid, zzdy> zzkk = null;

    public final void zza(zzch zzch, zzec zzec) {
        if (this.zzkj != null) {
            zzec.zzf(zzch, this.zzkj);
            return;
        }
        zzeb zzea = new zzea(this, zzch, zzec);
        if (this.zzkk != null) {
            for (Entry entry : this.zzkk.entrySet()) {
                zzea.zza((zzid) entry.getKey(), (zzdy) entry.getValue());
            }
        }
    }

    public final void zzh(zzch zzch, zzja zzja) {
        zzdy zzdy = this;
        while (!zzch.isEmpty()) {
            if (zzdy.zzkj != null) {
                zzdy.zzkj = zzdy.zzkj.zzl(zzch, zzja);
                return;
            }
            if (zzdy.zzkk == null) {
                zzdy.zzkk = new HashMap();
            }
            zzid zzbw = zzch.zzbw();
            if (!zzdy.zzkk.containsKey(zzbw)) {
                zzdy.zzkk.put(zzbw, new zzdy());
            }
            zzdy = (zzdy) zzdy.zzkk.get(zzbw);
            zzch = zzch.zzbx();
        }
        zzdy.zzkj = zzja;
        zzdy.zzkk = null;
    }

    public final boolean zzq(zzch zzch) {
        while (!zzch.isEmpty()) {
            if (this.zzkj != null) {
                if (this.zzkj.zzfk()) {
                    return false;
                }
                zzif zzif = (zzif) this.zzkj;
                this.zzkj = null;
                zzif.zza(new zzdz(this, zzch), false);
            } else if (this.zzkk == null) {
                return true;
            } else {
                zzid zzbw = zzch.zzbw();
                zzch = zzch.zzbx();
                if (this.zzkk.containsKey(zzbw) && ((zzdy) this.zzkk.get(zzbw)).zzq(zzch)) {
                    this.zzkk.remove(zzbw);
                }
                if (!this.zzkk.isEmpty()) {
                    return false;
                }
                this.zzkk = null;
                return true;
            }
        }
        this.zzkj = null;
        this.zzkk = null;
        return true;
    }
}
