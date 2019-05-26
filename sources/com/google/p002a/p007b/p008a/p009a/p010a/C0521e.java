package com.google.p002a.p007b.p008a.p009a.p010a;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.p002a.p003a.C0353b;
import com.google.p002a.p003a.C0354c;

/* renamed from: com.google.a.b.a.a.a.e */
public abstract class C0521e extends C0353b implements C0357d {
    public C0521e() {
        super("com.google.android.play.core.install.protocol.IInstallServiceCallback");
    }

    /* renamed from: a */
    protected final boolean mo1251a(int i, Parcel parcel) throws RemoteException {
        switch (i) {
            case 1:
                mo1968a((Bundle) C0354c.m11a(parcel, Bundle.CREATOR));
                break;
            case 2:
                mo1969b((Bundle) C0354c.m11a(parcel, Bundle.CREATOR));
                break;
            case 3:
                C0354c.m11a(parcel, Bundle.CREATOR);
                mo1967a();
                break;
            default:
                return false;
        }
        return true;
    }
}
