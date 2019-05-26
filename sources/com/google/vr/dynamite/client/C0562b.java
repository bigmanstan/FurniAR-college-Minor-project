package com.google.vr.dynamite.client;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.p002a.p003a.C0352a;

/* renamed from: com.google.vr.dynamite.client.b */
public final class C0562b extends C0352a implements INativeLibraryLoader {
    C0562b(IBinder iBinder) {
        super(iBinder, "com.google.vr.dynamite.client.INativeLibraryLoader");
    }

    public final int checkVersion(String str) throws RemoteException {
        Parcel a = m7a();
        a.writeString(str);
        Parcel a2 = m8a(2, a);
        int readInt = a2.readInt();
        a2.recycle();
        return readInt;
    }

    public final long initializeAndLoadNativeLibrary(String str) throws RemoteException {
        Parcel a = m7a();
        a.writeString(str);
        Parcel a2 = m8a(1, a);
        long readLong = a2.readLong();
        a2.recycle();
        return readLong;
    }
}
