package com.google.vr.dynamite.client;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.p002a.p003a.C0352a;
import com.google.p002a.p003a.C0354c;

/* renamed from: com.google.vr.dynamite.client.a */
public final class C0561a extends C0352a implements ILoadedInstanceCreator {
    C0561a(IBinder iBinder) {
        super(iBinder, "com.google.vr.dynamite.client.ILoadedInstanceCreator");
    }

    public final INativeLibraryLoader newNativeLibraryLoader(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) throws RemoteException {
        INativeLibraryLoader iNativeLibraryLoader;
        Parcel a = m7a();
        C0354c.m12a(a, (IInterface) iObjectWrapper);
        C0354c.m12a(a, (IInterface) iObjectWrapper2);
        Parcel a2 = m8a(1, a);
        IBinder readStrongBinder = a2.readStrongBinder();
        if (readStrongBinder == null) {
            iNativeLibraryLoader = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.vr.dynamite.client.INativeLibraryLoader");
            iNativeLibraryLoader = queryLocalInterface instanceof INativeLibraryLoader ? (INativeLibraryLoader) queryLocalInterface : new C0562b(readStrongBinder);
        }
        a2.recycle();
        return iNativeLibraryLoader;
    }
}
