package com.google.android.gms.internal.firebase_database;

public final class zzft implements zzfq {
    private final long zzni;

    public zzft(long j) {
        this.zzni = j;
    }

    public final boolean zza(long j, long j2) {
        if (j <= this.zzni) {
            if (j2 <= 1000) {
                return false;
            }
        }
        return true;
    }

    public final float zzde() {
        return 0.2f;
    }

    public final long zzdf() {
        return 1000;
    }

    public final boolean zzm(long j) {
        return j > 1000;
    }
}
