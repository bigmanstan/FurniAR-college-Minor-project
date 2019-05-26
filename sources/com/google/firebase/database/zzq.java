package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzce;

final class zzq implements Runnable {
    private final /* synthetic */ Query zzaw;
    private final /* synthetic */ zzce zzax;

    zzq(Query query, zzce zzce) {
        this.zzaw = query;
        this.zzax = zzce;
    }

    public final void run() {
        this.zzaw.zzai.zze(this.zzax);
    }
}
