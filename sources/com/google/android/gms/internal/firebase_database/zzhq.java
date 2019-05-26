package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzhq {
    private final Map<zzid, zzgw> zzqg = new HashMap();

    public final void zza(zzgw zzgw) {
        zzgz zzdt = zzgw.zzdt();
        zzid zzds = zzgw.zzds();
        if (this.zzqg.containsKey(zzds)) {
            zzgw zzgw2 = (zzgw) this.zzqg.get(zzds);
            zzgz zzdt2 = zzgw2.zzdt();
            if (zzdt == zzgz.CHILD_ADDED && zzdt2 == zzgz.CHILD_REMOVED) {
                this.zzqg.put(zzgw.zzds(), zzgw.zza(zzds, zzgw.zzdq(), zzgw2.zzdq()));
                return;
            } else if (zzdt == zzgz.CHILD_REMOVED && zzdt2 == zzgz.CHILD_ADDED) {
                this.zzqg.remove(zzds);
                return;
            } else if (zzdt == zzgz.CHILD_REMOVED && zzdt2 == zzgz.CHILD_CHANGED) {
                this.zzqg.put(zzds, zzgw.zzb(zzds, zzgw2.zzdv()));
                return;
            } else if (zzdt == zzgz.CHILD_CHANGED && zzdt2 == zzgz.CHILD_ADDED) {
                this.zzqg.put(zzds, zzgw.zza(zzds, zzgw.zzdq()));
                return;
            } else if (zzdt == zzgz.CHILD_CHANGED && zzdt2 == zzgz.CHILD_CHANGED) {
                this.zzqg.put(zzds, zzgw.zza(zzds, zzgw.zzdq(), zzgw2.zzdv()));
                return;
            } else {
                String valueOf = String.valueOf(zzgw);
                String valueOf2 = String.valueOf(zzgw2);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 48) + String.valueOf(valueOf2).length());
                stringBuilder.append("Illegal combination of changes: ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" occurred after ");
                stringBuilder.append(valueOf2);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        this.zzqg.put(zzgw.zzds(), zzgw);
    }

    public final List<zzgw> zzev() {
        return new ArrayList(this.zzqg.values());
    }
}
