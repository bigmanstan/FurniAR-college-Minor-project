package com.google.android.gms.internal.firebase_database;

public final class zzfk extends zzfl {
    private final zzbv zzmm;

    public zzfk(zzfn zzfn, zzch zzch, zzbv zzbv) {
        super(zzfm.Merge, zzfn, zzch);
        this.zzmm = zzbv;
    }

    public final String toString() {
        return String.format("Merge { path=%s, source=%s, children=%s }", new Object[]{this.zzap, this.zzmo, this.zzmm});
    }

    public final zzfl zzc(zzid zzid) {
        if (!this.zzap.isEmpty()) {
            return this.zzap.zzbw().equals(zzid) ? new zzfk(this.zzmo, this.zzap.zzbx(), this.zzmm) : null;
        } else {
            zzbv zzg = this.zzmm.zzg(new zzch(zzid));
            return zzg.isEmpty() ? null : zzg.zzbg() != null ? new zzfp(this.zzmo, zzch.zzbt(), zzg.zzbg()) : new zzfk(this.zzmo, zzch.zzbt(), zzg);
        }
    }

    public final zzbv zzcx() {
        return this.zzmm;
    }
}
