package com.google.android.gms.internal.firebase_database;

public final class zzkm implements zzkf {
    private final zzkf zzul;
    private long zzum = 0;

    public zzkm(zzkf zzkf, long j) {
        this.zzul = zzkf;
        this.zzum = 0;
    }

    public final long millis() {
        return this.zzul.millis() + this.zzum;
    }

    public final void zzn(long j) {
        this.zzum = j;
    }
}
