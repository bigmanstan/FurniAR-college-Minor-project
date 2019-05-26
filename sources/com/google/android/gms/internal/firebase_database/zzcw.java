package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

final class zzcw implements Runnable {
    private final /* synthetic */ zzdl zziv;
    private final /* synthetic */ DatabaseError zziw;
    private final /* synthetic */ DataSnapshot zzix;

    zzcw(zzck zzck, zzdl zzdl, DatabaseError databaseError, DataSnapshot dataSnapshot) {
        this.zziv = zzdl;
        this.zziw = databaseError;
        this.zzix = dataSnapshot;
    }

    public final void run() {
        this.zziv.zzjj.onComplete(this.zziw, false, this.zzix);
    }
}
