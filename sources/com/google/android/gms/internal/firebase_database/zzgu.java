package com.google.android.gms.internal.firebase_database;

public final class zzgu {
    private final zzit zzol;
    private final boolean zzom;
    private final boolean zzon;

    public zzgu(zzit zzit, boolean z, boolean z2) {
        this.zzol = zzit;
        this.zzom = z;
        this.zzon = z2;
    }

    public final boolean zzak(zzch zzch) {
        return zzch.isEmpty() ? this.zzom && !this.zzon : zzf(zzch.zzbw());
    }

    public final zzja zzd() {
        return this.zzol.zzd();
    }

    public final boolean zzdo() {
        return this.zzom;
    }

    public final boolean zzdp() {
        return this.zzon;
    }

    public final zzit zzdq() {
        return this.zzol;
    }

    public final boolean zzf(zzid zzid) {
        return (this.zzom && !this.zzon) || this.zzol.zzd().zzk(zzid);
    }
}
