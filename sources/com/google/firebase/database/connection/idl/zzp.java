package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zza;

public final class zzp extends zza implements zzn {
    zzp(IBinder iBinder) {
        super(iBinder, "com.google.firebase.database.connection.idl.IGetTokenCallback");
    }

    public final void onError(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final void zzf(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }
}
