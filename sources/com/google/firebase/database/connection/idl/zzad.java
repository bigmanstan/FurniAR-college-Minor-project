package com.google.firebase.database.connection.idl;

import android.os.RemoteException;

final class zzad extends zzl {
    private final /* synthetic */ com.google.android.gms.internal.firebase_database.zzad zzfx;

    zzad(com.google.android.gms.internal.firebase_database.zzad zzad) {
        this.zzfx = zzad;
    }

    public final void zza(boolean z, zzn zzn) throws RemoteException {
        this.zzfx.zza(z, new zzae(this, zzn));
    }
}
