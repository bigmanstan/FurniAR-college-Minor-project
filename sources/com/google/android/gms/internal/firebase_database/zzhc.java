package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.List;

public final class zzhc {
    private final zzhz zzbs;
    private final zzcg zzhf;

    public zzhc(zzbz zzbz) {
        this.zzhf = zzbz.zzbp();
        this.zzbs = zzbz.zzp("EventRaiser");
    }

    public final void zze(List<? extends zzgy> list) {
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            int size = list.size();
            StringBuilder stringBuilder = new StringBuilder(28);
            stringBuilder.append("Raising ");
            stringBuilder.append(size);
            stringBuilder.append(" event(s)");
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        this.zzhf.zza(new zzhd(this, new ArrayList(list)));
    }
}
