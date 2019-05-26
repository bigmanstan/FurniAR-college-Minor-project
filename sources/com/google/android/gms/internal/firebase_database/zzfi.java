package com.google.android.gms.internal.firebase_database;

public final class zzfi extends zzfl {
    private final boolean zzmk;
    private final zzgj<Boolean> zzml;

    public zzfi(zzch zzch, zzgj<Boolean> zzgj, boolean z) {
        super(zzfm.AckUserWrite, zzfn.zzmu, zzch);
        this.zzml = zzgj;
        this.zzmk = z;
    }

    public final String toString() {
        return String.format("AckUserWrite { path=%s, revert=%s, affectedTree=%s }", new Object[]{this.zzap, Boolean.valueOf(this.zzmk), this.zzml});
    }

    public final zzfl zzc(zzid zzid) {
        if (!this.zzap.isEmpty()) {
            zzkq.zza(this.zzap.zzbw().equals(zzid), "operationForChild called for unrelated child.");
            return new zzfi(this.zzap.zzbx(), this.zzml, this.zzmk);
        } else if (this.zzml.getValue() != null) {
            zzkq.zza(this.zzml.zzdm().isEmpty(), "affectedTree should not have overlapping affected paths.");
            return this;
        } else {
            return new zzfi(zzch.zzbt(), this.zzml.zzag(new zzch(zzid)), this.zzmk);
        }
    }

    public final zzgj<Boolean> zzcv() {
        return this.zzml;
    }

    public final boolean zzcw() {
        return this.zzmk;
    }
}
