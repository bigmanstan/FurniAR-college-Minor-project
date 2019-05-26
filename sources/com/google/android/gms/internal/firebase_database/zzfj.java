package com.google.android.gms.internal.firebase_database;

public final class zzfj extends zzfl {
    public zzfj(zzfn zzfn, zzch zzch) {
        super(zzfm.ListenComplete, zzfn, zzch);
    }

    public final String toString() {
        return String.format("ListenComplete { path=%s, source=%s }", new Object[]{this.zzap, this.zzmo});
    }

    public final zzfl zzc(zzid zzid) {
        return this.zzap.isEmpty() ? new zzfj(this.zzmo, zzch.zzbt()) : new zzfj(this.zzmo, this.zzap.zzbx());
    }
}
