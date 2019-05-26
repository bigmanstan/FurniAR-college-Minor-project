package com.google.android.gms.internal.firebase_database;

final class zzbe implements Runnable {
    private final /* synthetic */ zzbc zzff;

    zzbe(zzbc zzbc) {
        this.zzff = zzbc;
    }

    public final void run() {
        if (this.zzff.zzew != null) {
            this.zzff.zzew.zzm("0");
            this.zzff.zzas();
        }
    }
}
