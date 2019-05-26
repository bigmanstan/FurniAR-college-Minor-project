package com.google.firebase.database.connection.idl;

import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zzae;

final class zzag extends zzo {
    private final /* synthetic */ zzae zzga;

    zzag(zzaf zzaf, zzae zzae) {
        this.zzga = zzae;
    }

    public final void onError(String str) throws RemoteException {
        this.zzga.onError(str);
    }

    public final void zzf(String str) throws RemoteException {
        this.zzga.zzf(str);
    }
}
