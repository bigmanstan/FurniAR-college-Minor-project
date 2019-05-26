package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzdh implements Runnable {
    private final /* synthetic */ CompletionListener zzio;
    private final /* synthetic */ DatabaseError zzjf;
    private final /* synthetic */ DatabaseReference zzjg;

    zzdh(zzck zzck, CompletionListener completionListener, DatabaseError databaseError, DatabaseReference databaseReference) {
        this.zzio = completionListener;
        this.zzjf = databaseError;
        this.zzjg = databaseReference;
    }

    public final void run() {
        this.zzio.onComplete(this.zzjf, this.zzjg);
    }
}
