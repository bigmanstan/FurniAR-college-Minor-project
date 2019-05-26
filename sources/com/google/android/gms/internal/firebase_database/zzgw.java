package com.google.android.gms.internal.firebase_database;

public final class zzgw {
    private final zzit zzol;
    private final zzgz zzoq;
    private final zzit zzor;
    private final zzid zzos;
    private final zzid zzot;

    private zzgw(zzgz zzgz, zzit zzit, zzid zzid, zzid zzid2, zzit zzit2) {
        this.zzoq = zzgz;
        this.zzol = zzit;
        this.zzos = zzid;
        this.zzot = zzid2;
        this.zzor = zzit2;
    }

    public static zzgw zza(zzid zzid, zzit zzit) {
        return new zzgw(zzgz.CHILD_ADDED, zzit, zzid, null, null);
    }

    public static zzgw zza(zzid zzid, zzit zzit, zzit zzit2) {
        return new zzgw(zzgz.CHILD_CHANGED, zzit, zzid, null, zzit2);
    }

    public static zzgw zza(zzid zzid, zzja zzja, zzja zzja2) {
        return zza(zzid, zzit.zzj(zzja), zzit.zzj(zzja2));
    }

    public static zzgw zza(zzit zzit) {
        return new zzgw(zzgz.VALUE, zzit, null, null, null);
    }

    public static zzgw zzb(zzid zzid, zzit zzit) {
        return new zzgw(zzgz.CHILD_REMOVED, zzit, zzid, null, null);
    }

    public static zzgw zzc(zzid zzid, zzit zzit) {
        return new zzgw(zzgz.CHILD_MOVED, zzit, zzid, null, null);
    }

    public static zzgw zzc(zzid zzid, zzja zzja) {
        return zza(zzid, zzit.zzj(zzja));
    }

    public static zzgw zzd(zzid zzid, zzja zzja) {
        return zzb(zzid, zzit.zzj(zzja));
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzoq);
        String valueOf2 = String.valueOf(this.zzos);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 9) + String.valueOf(valueOf2).length());
        stringBuilder.append("Change: ");
        stringBuilder.append(valueOf);
        stringBuilder.append(" ");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }

    public final zzit zzdq() {
        return this.zzol;
    }

    public final zzid zzds() {
        return this.zzos;
    }

    public final zzgz zzdt() {
        return this.zzoq;
    }

    public final zzid zzdu() {
        return this.zzot;
    }

    public final zzit zzdv() {
        return this.zzor;
    }

    public final zzgw zzg(zzid zzid) {
        return new zzgw(this.zzoq, this.zzol, this.zzos, zzid, this.zzor);
    }
}
