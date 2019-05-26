package com.google.firebase.database.connection.idl;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.internal.firebase_database.zzb;
import com.google.android.gms.internal.firebase_database.zzc;

public abstract class zzx extends zzb implements zzw {
    public zzx() {
        super("com.google.firebase.database.connection.idl.IPersistentConnectionDelegate");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza(parcel.createStringArrayList(), Stub.asInterface(parcel.readStrongBinder()), zzc.zza(parcel), parcel.readLong());
                break;
            case 2:
                zza(parcel.createStringArrayList(), parcel.createTypedArrayList(zzak.CREATOR), Stub.asInterface(parcel.readStrongBinder()), parcel.readLong());
                break;
            case 3:
                zzaa();
                break;
            case 4:
                onDisconnect();
                break;
            case 5:
                zzb(zzc.zza(parcel));
                break;
            case 6:
                zza(Stub.asInterface(parcel.readStrongBinder()));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
