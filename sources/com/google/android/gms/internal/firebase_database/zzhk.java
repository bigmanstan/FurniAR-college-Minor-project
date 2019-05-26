package com.google.android.gms.internal.firebase_database;

public final class zzhk {
    private final zzgu zzpz;
    private final zzgu zzqa;

    public zzhk(zzgu zzgu, zzgu zzgu2) {
        this.zzpz = zzgu;
        this.zzqa = zzgu2;
    }

    public final zzhk zza(zzit zzit, boolean z, boolean z2) {
        return new zzhk(new zzgu(zzit, z, z2), this.zzqa);
    }

    public final zzhk zzb(zzit zzit, boolean z, boolean z2) {
        return new zzhk(this.zzpz, new zzgu(zzit, z, z2));
    }

    public final zzgu zzer() {
        return this.zzpz;
    }

    public final zzja zzes() {
        return this.zzpz.zzdo() ? this.zzpz.zzd() : null;
    }

    public final zzgu zzet() {
        return this.zzqa;
    }

    public final zzja zzeu() {
        return this.zzqa.zzdo() ? this.zzqa.zzd() : null;
    }
}
