package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class zzjh {
    private final zzch zzso;
    private final zzch zzsp;
    private final zzja zzsq;

    public zzjh(zzba zzba) {
        List zzap = zzba.zzap();
        zzch zzch = null;
        this.zzso = zzap != null ? new zzch(zzap) : null;
        zzap = zzba.zzaq();
        if (zzap != null) {
            zzch = new zzch(zzap);
        }
        this.zzsp = zzch;
        this.zzsq = zzjd.zza(zzba.zzar(), zzir.zzfv());
    }

    private final zzja zzb(zzch zzch, zzja zzja, zzja zzja2) {
        int zzj = this.zzso == null ? 1 : zzch.zzj(this.zzso);
        int zzj2 = this.zzsp == null ? -1 : zzch.zzj(this.zzsp);
        int i = 0;
        int i2 = (this.zzso == null || !zzch.zzi(this.zzso)) ? 0 : 1;
        int i3 = (this.zzsp == null || !zzch.zzi(this.zzsp)) ? 0 : 1;
        if (zzj > 0 && zzj2 < 0 && i3 == 0) {
            return zzja2;
        }
        if (zzj > 0 && i3 != 0 && zzja2.zzfk()) {
            return zzja2;
        }
        if (zzj > 0 && zzj2 == 0) {
            return zzja.zzfk() ? zzir.zzfv() : zzja;
        } else {
            if (i2 == 0) {
                if (i3 == 0) {
                    return zzja;
                }
            }
            Collection hashSet = new HashSet();
            for (zziz zzge : zzja) {
                hashSet.add(zzge.zzge());
            }
            for (zziz zzge2 : zzja2) {
                hashSet.add(zzge2.zzge());
            }
            List arrayList = new ArrayList(hashSet.size() + 1);
            arrayList.addAll(hashSet);
            if (!(zzja2.zzfl().isEmpty() && zzja.zzfl().isEmpty())) {
                arrayList.add(zzid.zzfe());
            }
            ArrayList arrayList2 = (ArrayList) arrayList;
            zzj = arrayList2.size();
            zzja zzja3 = zzja;
            while (i < zzj) {
                Object obj = arrayList2.get(i);
                i++;
                zzid zzid = (zzid) obj;
                zzja zzm = zzja.zzm(zzid);
                zzja zzb = zzb(zzch.zza(zzid), zzja.zzm(zzid), zzja2.zzm(zzid));
                if (zzb != zzm) {
                    zzja3 = zzja3.zze(zzid, zzb);
                }
            }
            return zzja3;
        }
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzso);
        String valueOf2 = String.valueOf(this.zzsp);
        String valueOf3 = String.valueOf(this.zzsq);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(valueOf).length() + 55) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length());
        stringBuilder.append("RangeMerge{optExclusiveStart=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", optInclusiveEnd=");
        stringBuilder.append(valueOf2);
        stringBuilder.append(", snap=");
        stringBuilder.append(valueOf3);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public final zzja zzm(zzja zzja) {
        return zzb(zzch.zzbt(), zzja, this.zzsq);
    }
}
