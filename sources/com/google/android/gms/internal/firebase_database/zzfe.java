package com.google.android.gms.internal.firebase_database;

import java.util.List;

final class zzfe implements zzgn<zzfa> {
    private final /* synthetic */ boolean zzmd;
    private final /* synthetic */ List zzme;
    private final /* synthetic */ zzch zzmf;

    zzfe(zzfd zzfd, boolean z, List list, zzch zzch) {
        this.zzmd = z;
        this.zzme = list;
        this.zzmf = zzch;
    }

    public final /* synthetic */ boolean zzd(Object obj) {
        zzfa zzfa = (zzfa) obj;
        return (zzfa.isVisible() || this.zzmd) && !this.zzme.contains(Long.valueOf(zzfa.zzcn())) && (zzfa.zzg().zzi(this.zzmf) || this.zzmf.zzi(zzfa.zzg()));
    }
}
