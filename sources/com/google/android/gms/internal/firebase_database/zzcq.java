package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Transaction.Handler;

final class zzcq implements Runnable {
    private final /* synthetic */ Handler zzac;
    private final /* synthetic */ DatabaseError zziq;
    private final /* synthetic */ DataSnapshot zzir;

    zzcq(zzck zzck, Handler handler, DatabaseError databaseError, DataSnapshot dataSnapshot) {
        this.zzac = handler;
        this.zziq = databaseError;
        this.zzir = dataSnapshot;
    }

    public final void run() {
        this.zzac.onComplete(this.zziq, false, this.zzir);
    }
}
