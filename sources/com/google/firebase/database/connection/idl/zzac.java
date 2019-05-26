package com.google.firebase.database.connection.idl;

import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.firebase_database.zzak;
import com.google.android.gms.internal.firebase_database.zzba;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class zzac implements zzak {
    private final /* synthetic */ zzw zzfw;

    zzac(zzw zzw) {
        this.zzfw = zzw;
    }

    public final void onDisconnect() {
        try {
            this.zzfw.onDisconnect();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, Object obj, boolean z, Long l) {
        try {
            this.zzfw.zza((List) list, ObjectWrapper.wrap(obj), z, IPersistentConnectionImpl.zza(l));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, List<zzba> list2, Long l) {
        List arrayList = new ArrayList(list2.size());
        List arrayList2 = new ArrayList(list2.size());
        for (zzba zzba : list2) {
            arrayList.add(new zzak(zzba.zzap(), zzba.zzaq()));
            arrayList2.add(zzba.zzar());
        }
        try {
            this.zzfw.zza((List) list, arrayList, ObjectWrapper.wrap(arrayList2), IPersistentConnectionImpl.zza(l));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzaa() {
        try {
            this.zzfw.zzaa();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(boolean z) {
        try {
            this.zzfw.zzb(z);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzc(Map<String, Object> map) {
        try {
            this.zzfw.zza(ObjectWrapper.wrap(map));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
