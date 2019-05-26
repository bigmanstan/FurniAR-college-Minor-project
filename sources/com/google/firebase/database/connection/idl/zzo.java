package com.google.firebase.database.connection.idl;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zzb;

public abstract class zzo extends zzb implements zzn {
    public zzo() {
        super("com.google.firebase.database.connection.idl.IGetTokenCallback");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zzf(parcel.readString());
                break;
            case 2:
                onError(parcel.readString());
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
