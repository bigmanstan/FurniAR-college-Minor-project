package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.zzh;

public final class zzbt extends zzce {
    private final zzck zzai;
    private final ChildEventListener zzgt;
    private final zzhh zzgu;

    public zzbt(zzck zzck, ChildEventListener childEventListener, zzhh zzhh) {
        this.zzai = zzck;
        this.zzgt = childEventListener;
        this.zzgu = zzhh;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzbt) {
            zzbt zzbt = (zzbt) obj;
            if (zzbt.zzgt.equals(this.zzgt) && zzbt.zzai.equals(this.zzai) && zzbt.zzgu.equals(this.zzgu)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (((this.zzgt.hashCode() * 31) + this.zzai.hashCode()) * 31) + this.zzgu.hashCode();
    }

    public final String toString() {
        return "ChildEventRegistration";
    }

    public final zzce zza(zzhh zzhh) {
        return new zzbt(this.zzai, this.zzgt, zzhh);
    }

    public final zzgx zza(zzgw zzgw, zzhh zzhh) {
        return new zzgx(zzgw.zzdt(), this, zzh.zza(zzh.zza(this.zzai, zzhh.zzg().zza(zzgw.zzds())), zzgw.zzdq()), zzgw.zzdu() != null ? zzgw.zzdu().zzfg() : null);
    }

    public final void zza(zzgx zzgx) {
        if (!zzbs()) {
            switch (zzbu.zzgv[zzgx.zzdt().ordinal()]) {
                case 1:
                    this.zzgt.onChildAdded(zzgx.zzdw(), zzgx.zzdx());
                    break;
                case 2:
                    this.zzgt.onChildChanged(zzgx.zzdw(), zzgx.zzdx());
                    return;
                case 3:
                    this.zzgt.onChildMoved(zzgx.zzdw(), zzgx.zzdx());
                    return;
                case 4:
                    this.zzgt.onChildRemoved(zzgx.zzdw());
                    break;
                default:
                    break;
            }
        }
    }

    public final void zza(DatabaseError databaseError) {
        this.zzgt.onCancelled(databaseError);
    }

    public final boolean zza(zzgz zzgz) {
        return zzgz != zzgz.VALUE;
    }

    public final zzhh zzbe() {
        return this.zzgu;
    }

    public final boolean zzc(zzce zzce) {
        return (zzce instanceof zzbt) && ((zzbt) zzce).zzgt.equals(this.zzgt);
    }
}
