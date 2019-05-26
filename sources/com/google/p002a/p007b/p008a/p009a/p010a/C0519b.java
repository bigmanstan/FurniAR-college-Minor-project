package com.google.p002a.p007b.p008a.p009a.p010a;

import android.os.IBinder;
import android.os.IInterface;
import com.google.p002a.p003a.C0353b;

/* renamed from: com.google.a.b.a.a.a.b */
public abstract class C0519b extends C0353b implements C0356a {
    /* renamed from: a */
    public static C0356a m105a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.install.protocol.IInstallService");
        return queryLocalInterface instanceof C0356a ? (C0356a) queryLocalInterface : new C0520c(iBinder);
    }
}
