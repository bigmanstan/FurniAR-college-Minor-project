package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

final class zzen implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzee zzlb;

    zzen(zzee zzee) {
        this.zzlb = zzee;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzlb.zzkp.zzm();
        if (this.zzlb.zzkr.zzct().isEmpty()) {
            return Collections.emptyList();
        }
        return this.zzlb.zza(new zzfi(zzch.zzbt(), new zzgj(Boolean.valueOf(true)), true));
    }
}
