package com.google.android.gms.internal.firebase_database;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.view.ViewCompat;

public class zzb extends Binder implements IInterface {
    private static zzd zzc = null;

    protected zzb(String str) {
        attachInterface(this, str);
    }

    public IBinder asBinder() {
        return this;
    }

    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        return false;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        boolean onTransact;
        if (i > ViewCompat.MEASURED_SIZE_MASK) {
            onTransact = super.onTransact(i, parcel, parcel2, i2);
        } else {
            parcel.enforceInterface(getInterfaceDescriptor());
            onTransact = false;
        }
        return onTransact ? true : dispatchTransaction(i, parcel, parcel2, i2);
    }
}
