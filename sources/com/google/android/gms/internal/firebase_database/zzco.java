package com.google.android.gms.internal.firebase_database;

import java.util.List;

final class zzco implements zzec {
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ List zzip;

    zzco(zzck zzck, List list) {
        this.zzil = zzck;
        this.zzip = list;
    }

    public final void zzf(zzch zzch, zzja zzja) {
        this.zzip.addAll(this.zzil.zzih.zzi(zzch, zzja));
        this.zzil.zzn(this.zzil.zzb(zzch, -9));
    }
}
