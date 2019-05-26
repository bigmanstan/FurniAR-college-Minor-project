package com.google.firebase.database;

final class zzg implements Runnable {
    private final /* synthetic */ FirebaseDatabase zzaj;

    zzg(FirebaseDatabase firebaseDatabase) {
        this.zzaj = firebaseDatabase;
    }

    public final void run() {
        this.zzaj.zzai.purgeOutstandingWrites();
    }
}
