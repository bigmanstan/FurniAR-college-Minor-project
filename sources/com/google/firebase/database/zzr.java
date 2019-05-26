package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzce;

final class zzr implements Runnable {
    private final /* synthetic */ Query zzaw;
    private final /* synthetic */ zzce zzay;

    zzr(Query query, zzce zzce) {
        this.zzaw = query;
        this.zzay = zzce;
    }

    public final void run() {
        this.zzaw.zzai.zzf(this.zzay);
    }
}
