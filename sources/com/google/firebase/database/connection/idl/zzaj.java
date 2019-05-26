package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zza;

public final class zzaj extends zza implements zzah {
    zzaj(IBinder iBinder) {
        super(iBinder, "com.google.firebase.database.connection.idl.IRequestResultCallback");
    }

    public final void zzb(String str, String str2) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }
}
