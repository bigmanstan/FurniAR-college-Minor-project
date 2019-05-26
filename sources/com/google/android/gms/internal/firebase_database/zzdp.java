package com.google.android.gms.internal.firebase_database;

final class zzdp implements Runnable {
    private final /* synthetic */ zzck zzit;

    zzdp(zzck zzck) {
        this.zzit = zzck;
    }

    public final void run() {
        this.zzit.interrupt();
    }
}
