package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;

final class zzdg implements zzbb {
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ zzfa zzje;

    zzdg(zzck zzck, zzfa zzfa) {
        this.zzil = zzck;
        this.zzje = zzfa;
    }

    public final void zzb(String str, String str2) {
        DatabaseError zzd = zzck.zzc(str, str2);
        this.zzil.zza("Persisted write", this.zzje.zzg(), zzd);
        this.zzil.zza(this.zzje.zzcn(), this.zzje.zzg(), zzd);
    }
}
