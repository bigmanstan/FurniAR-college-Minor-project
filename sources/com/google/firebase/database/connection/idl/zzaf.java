package com.google.firebase.database.connection.idl;

import com.google.android.gms.internal.firebase_database.zzad;
import com.google.android.gms.internal.firebase_database.zzae;

final class zzaf implements zzad {
    private final /* synthetic */ zzk zzfz;

    zzaf(zzk zzk) {
        this.zzfz = zzk;
    }

    public final void zza(boolean z, zzae zzae) {
        try {
            this.zzfz.zza(z, new zzag(this, zzae));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
