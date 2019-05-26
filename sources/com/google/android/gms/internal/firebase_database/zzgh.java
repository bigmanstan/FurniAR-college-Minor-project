package com.google.android.gms.internal.firebase_database;

import java.util.Map;
import java.util.Map.Entry;

final class zzgh implements zzgm<Map<zzhe, zzgb>, Void> {
    private final /* synthetic */ zzgc zzoa;

    zzgh(zzgc zzgc) {
        this.zzoa = zzgc;
    }

    public final /* synthetic */ Object zza(zzch zzch, Object obj, Object obj2) {
        for (Entry value : ((Map) obj).entrySet()) {
            zzgb zzgb = (zzgb) value.getValue();
            if (!zzgb.zznr) {
                this.zzoa.zza(zzgb.zzdi());
            }
        }
        return null;
    }
}
