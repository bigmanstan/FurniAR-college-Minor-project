package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zza;
import com.google.android.gms.internal.firebase_database.zzc;

public final class zzm extends zza implements zzk {
    zzm(IBinder iBinder) {
        super(iBinder, "com.google.firebase.database.connection.idl.IConnectionAuthTokenProvider");
    }

    public final void zza(boolean z, zzn zzn) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.writeBoolean(obtainAndWriteInterfaceToken, z);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzn);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }
}
