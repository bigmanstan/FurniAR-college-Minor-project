package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zza;
import com.google.android.gms.internal.firebase_database.zzc;

public final class zzs extends zza implements zzq {
    zzs(IBinder iBinder) {
        super(iBinder, "com.google.firebase.database.connection.idl.IListenHashProvider");
    }

    public final zza zzaw() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(3, obtainAndWriteInterfaceToken());
        zza zza = (zza) zzc.zza(transactAndReadException, zza.CREATOR);
        transactAndReadException.recycle();
        return zza;
    }

    public final String zzx() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken());
        String readString = transactAndReadException.readString();
        transactAndReadException.recycle();
        return readString;
    }

    public final boolean zzy() throws RemoteException {
        Parcel transactAndReadException = transactAndReadException(2, obtainAndWriteInterfaceToken());
        boolean zza = zzc.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }
}
