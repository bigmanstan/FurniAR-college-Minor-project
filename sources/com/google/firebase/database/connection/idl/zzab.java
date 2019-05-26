package com.google.firebase.database.connection.idl;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.firebase_database.zzak;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class zzab extends zzx {
    private final /* synthetic */ zzak zzfv;

    zzab(zzak zzak) {
        this.zzfv = zzak;
    }

    public final void onDisconnect() {
        this.zzfv.onDisconnect();
    }

    public final void zza(IObjectWrapper iObjectWrapper) {
        this.zzfv.zzc((Map) ObjectWrapper.unwrap(iObjectWrapper));
    }

    public final void zza(List<String> list, IObjectWrapper iObjectWrapper, boolean z, long j) {
        this.zzfv.zza(list, ObjectWrapper.unwrap(iObjectWrapper), z, IPersistentConnectionImpl.zzf(j));
    }

    public final void zza(List<String> list, List<zzak> list2, IObjectWrapper iObjectWrapper, long j) {
        List list3 = (List) ObjectWrapper.unwrap(iObjectWrapper);
        List arrayList = new ArrayList(list2.size());
        for (int i = 0; i < list2.size(); i++) {
            arrayList.add(zzak.zza((zzak) list2.get(i), list3.get(i)));
        }
        this.zzfv.zza(list, arrayList, IPersistentConnectionImpl.zzf(j));
    }

    public final void zzaa() {
        this.zzfv.zzaa();
    }

    public final void zzb(boolean z) {
        this.zzfv.zzb(z);
    }
}
