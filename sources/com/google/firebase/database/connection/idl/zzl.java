package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_database.zzb;
import com.google.android.gms.internal.firebase_database.zzc;

public abstract class zzl extends zzb implements zzk {
    public zzl() {
        super("com.google.firebase.database.connection.idl.IConnectionAuthTokenProvider");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        zzn zzn;
        boolean zza = zzc.zza(parcel);
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder == null) {
            zzn = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IGetTokenCallback");
            zzn = queryLocalInterface instanceof zzn ? (zzn) queryLocalInterface : new zzp(readStrongBinder);
        }
        zza(zza, zzn);
        parcel2.writeNoException();
        return true;
    }
}
