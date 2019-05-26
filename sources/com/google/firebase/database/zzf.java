package com.google.firebase.database;

import com.google.firebase.database.Transaction.Handler;

final class zzf implements Runnable {
    private final /* synthetic */ Handler zzac;
    private final /* synthetic */ boolean zzad;
    private final /* synthetic */ DatabaseReference zzy;

    zzf(DatabaseReference databaseReference, Handler handler, boolean z) {
        this.zzy = databaseReference;
        this.zzac = handler;
        this.zzad = z;
    }

    public final void run() {
        this.zzy.zzai.zza(this.zzy.zzap, this.zzac, this.zzad);
    }
}
