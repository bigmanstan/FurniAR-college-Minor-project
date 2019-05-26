package com.google.p002a.p007b.p008a.p009a.p010a;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.p002a.p003a.C0352a;
import com.google.p002a.p003a.C0354c;
import java.util.List;

/* renamed from: com.google.a.b.a.a.a.c */
public final class C0520c extends C0352a implements C0356a {
    C0520c(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.install.protocol.IInstallService");
    }

    /* renamed from: a */
    public final void mo1249a(String str, Bundle bundle, C0357d c0357d) throws RemoteException {
        Parcel a = m7a();
        a.writeString(str);
        C0354c.m13a(a, (Parcelable) bundle);
        C0354c.m12a(a, (IInterface) c0357d);
        m9b(2, a);
    }

    /* renamed from: a */
    public final void mo1250a(String str, List<Bundle> list, Bundle bundle, C0357d c0357d) throws RemoteException {
        Parcel a = m7a();
        a.writeString(str);
        a.writeTypedList(list);
        C0354c.m13a(a, (Parcelable) bundle);
        C0354c.m12a(a, (IInterface) c0357d);
        m9b(1, a);
    }
}
