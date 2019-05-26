package com.google.firebase.database.connection.idl;

import android.content.Context;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.firebase_database.zzaf;
import com.google.android.gms.internal.firebase_database.zzai;
import com.google.android.gms.internal.firebase_database.zzaj;
import com.google.android.gms.internal.firebase_database.zzak;
import com.google.android.gms.internal.firebase_database.zzbb;
import java.util.List;
import java.util.Map;

public final class zzf implements zzaj {
    private final zzt zzfp;

    private zzf(zzt zzt) {
        this.zzfp = zzt;
    }

    private static zzah zza(zzbb zzbb) {
        return new zzh(zzbb);
    }

    public static zzf zza(Context context, zzc zzc, zzaf zzaf, zzak zzak) {
        return new zzf(IPersistentConnectionImpl.loadDynamic(context, zzc, zzaf.zzr(), zzaf.zzs(), zzak));
    }

    public final void initialize() {
        try {
            this.zzfp.initialize();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void interrupt(String str) {
        try {
            this.zzfp.interrupt(str);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final boolean isInterrupted(String str) {
        try {
            return this.zzfp.isInterrupted(str);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void purgeOutstandingWrites() {
        try {
            this.zzfp.purgeOutstandingWrites();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void refreshAuthToken() {
        try {
            this.zzfp.refreshAuthToken();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void resume(String str) {
        try {
            this.zzfp.resume(str);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void shutdown() {
        try {
            this.zzfp.shutdown();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, zzbb zzbb) {
        try {
            this.zzfp.onDisconnectCancel(list, zza(zzbb));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, Object obj, zzbb zzbb) {
        try {
            this.zzfp.put(list, ObjectWrapper.wrap(obj), zza(zzbb));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, Object obj, String str, zzbb zzbb) {
        try {
            this.zzfp.compareAndPut(list, ObjectWrapper.wrap(obj), str, zza(zzbb));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, Map<String, Object> map) {
        try {
            this.zzfp.unlisten(list, ObjectWrapper.wrap(map));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zza(List<String> list, Map<String, Object> map, zzai zzai, Long l, zzbb zzbb) {
        long longValue;
        zzq zzg = new zzg(this, zzai);
        if (l != null) {
            try {
                longValue = l.longValue();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        longValue = -1;
        List<String> list2 = list;
        this.zzfp.listen(list2, ObjectWrapper.wrap(map), zzg, longValue, zza(zzbb));
    }

    public final void zza(List<String> list, Map<String, Object> map, zzbb zzbb) {
        try {
            this.zzfp.merge(list, ObjectWrapper.wrap(map), zza(zzbb));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(List<String> list, Object obj, zzbb zzbb) {
        try {
            this.zzfp.onDisconnectPut(list, ObjectWrapper.wrap(obj), zza(zzbb));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzb(List<String> list, Map<String, Object> map, zzbb zzbb) {
        try {
            this.zzfp.onDisconnectMerge(list, ObjectWrapper.wrap(map), zza(zzbb));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void zzh(String str) {
        try {
            this.zzfp.refreshAuthToken2(str);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
