package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;

final class zzeu extends zzce {
    private zzhh zzgu;

    public zzeu(zzhh zzhh) {
        this.zzgu = zzhh;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzeu) && ((zzeu) obj).zzgu.equals(this.zzgu);
    }

    public final int hashCode() {
        return this.zzgu.hashCode();
    }

    public final zzce zza(zzhh zzhh) {
        return new zzeu(zzhh);
    }

    public final zzgx zza(zzgw zzgw, zzhh zzhh) {
        return null;
    }

    public final void zza(zzgx zzgx) {
    }

    public final void zza(DatabaseError databaseError) {
    }

    public final boolean zza(zzgz zzgz) {
        return false;
    }

    public final zzhh zzbe() {
        return this.zzgu;
    }

    public final boolean zzc(zzce zzce) {
        return zzce instanceof zzeu;
    }
}
