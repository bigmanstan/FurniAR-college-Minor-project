package com.google.android.gms.internal.firebase_database;

public final class zzfp extends zzfl {
    private final zzja zznc;

    public zzfp(zzfn zzfn, zzch zzch, zzja zzja) {
        super(zzfm.Overwrite, zzfn, zzch);
        this.zznc = zzja;
    }

    public final String toString() {
        return String.format("Overwrite { path=%s, source=%s, snapshot=%s }", new Object[]{this.zzap, this.zzmo, this.zznc});
    }

    public final zzfl zzc(zzid zzid) {
        return this.zzap.isEmpty() ? new zzfp(this.zzmo, zzch.zzbt(), this.zznc.zzm(zzid)) : new zzfp(this.zzmo, this.zzap.zzbx(), this.zznc);
    }

    public final zzja zzdd() {
        return this.zznc;
    }
}
