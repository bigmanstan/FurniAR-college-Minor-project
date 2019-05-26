package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;

final class zzdb implements Runnable {
    private final /* synthetic */ zzdl zziv;
    private final /* synthetic */ DatabaseError zziy;

    zzdb(zzck zzck, zzdl zzdl, DatabaseError databaseError) {
        this.zziv = zzdl;
        this.zziy = databaseError;
    }

    public final void run() {
        this.zziv.zzjj.onComplete(this.zziy, false, null);
    }
}
