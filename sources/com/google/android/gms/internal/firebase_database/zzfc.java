package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.zzh;

public final class zzfc extends zzce {
    private final zzck zzai;
    private final zzhh zzgu;
    private final ValueEventListener zzly;

    public zzfc(zzck zzck, ValueEventListener valueEventListener, zzhh zzhh) {
        this.zzai = zzck;
        this.zzly = valueEventListener;
        this.zzgu = zzhh;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzfc) {
            zzfc zzfc = (zzfc) obj;
            if (zzfc.zzly.equals(this.zzly) && zzfc.zzai.equals(this.zzai) && zzfc.zzgu.equals(this.zzgu)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (((this.zzly.hashCode() * 31) + this.zzai.hashCode()) * 31) + this.zzgu.hashCode();
    }

    public final String toString() {
        return "ValueEventRegistration";
    }

    public final zzce zza(zzhh zzhh) {
        return new zzfc(this.zzai, this.zzly, zzhh);
    }

    public final zzgx zza(zzgw zzgw, zzhh zzhh) {
        return new zzgx(zzgz.VALUE, this, zzh.zza(zzh.zza(this.zzai, zzhh.zzg()), zzgw.zzdq()), null);
    }

    public final void zza(zzgx zzgx) {
        if (!zzbs()) {
            this.zzly.onDataChange(zzgx.zzdw());
        }
    }

    public final void zza(DatabaseError databaseError) {
        this.zzly.onCancelled(databaseError);
    }

    public final boolean zza(zzgz zzgz) {
        return zzgz == zzgz.VALUE;
    }

    public final zzhh zzbe() {
        return this.zzgu;
    }

    public final boolean zzc(zzce zzce) {
        return (zzce instanceof zzfc) && ((zzfc) zzce).zzly.equals(this.zzly);
    }
}
