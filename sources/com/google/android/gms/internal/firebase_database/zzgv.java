package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;

public final class zzgv implements zzgy {
    private final zzch zzap;
    private final zzce zzoo;
    private final DatabaseError zzop;

    public zzgv(zzce zzce, DatabaseError databaseError, zzch zzch) {
        this.zzoo = zzce;
        this.zzap = zzch;
        this.zzop = databaseError;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzap);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 7);
        stringBuilder.append(valueOf);
        stringBuilder.append(":CANCEL");
        return stringBuilder.toString();
    }

    public final void zzdr() {
        this.zzoo.zza(this.zzop);
    }
}
