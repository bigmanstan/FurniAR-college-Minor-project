package com.google.p002a.p003a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.view.ViewCompat;

/* renamed from: com.google.a.a.b */
public class C0353b extends Binder implements IInterface {
    /* renamed from: a */
    private static C0355d f17a = null;

    protected C0353b(String str) {
        attachInterface(this, str);
    }

    /* renamed from: a */
    protected boolean mo1251a(int i, Parcel parcel) throws RemoteException {
        return false;
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        boolean onTransact;
        if (i > ViewCompat.MEASURED_SIZE_MASK) {
            onTransact = super.onTransact(i, parcel, parcel2, i2);
        } else {
            parcel.enforceInterface(getInterfaceDescriptor());
            onTransact = false;
        }
        return onTransact ? true : mo1251a(i, parcel);
    }
}
