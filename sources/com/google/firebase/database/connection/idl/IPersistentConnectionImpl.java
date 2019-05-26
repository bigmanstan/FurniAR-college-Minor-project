package com.google.firebase.database.connection.idl;

import android.content.Context;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.firebase_database.ModuleDescriptor;
import com.google.android.gms.internal.firebase_database.zzad;
import com.google.android.gms.internal.firebase_database.zzaf;
import com.google.android.gms.internal.firebase_database.zzah;
import com.google.android.gms.internal.firebase_database.zzaj;
import com.google.android.gms.internal.firebase_database.zzak;
import com.google.android.gms.internal.firebase_database.zzal;
import com.google.android.gms.internal.firebase_database.zzbb;
import com.google.android.gms.internal.firebase_database.zzhw;
import com.google.android.gms.internal.firebase_database.zzib;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@DynamiteApi
public class IPersistentConnectionImpl extends zzu {
    private zzaj zzfs;

    public static zzt loadDynamic(Context context, zzc zzc, zzad zzad, ScheduledExecutorService scheduledExecutorService, zzak zzak) {
        try {
            zzt asInterface = zzu.asInterface(DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_REMOTE_VERSION, ModuleDescriptor.MODULE_ID).instantiate("com.google.firebase.database.connection.idl.IPersistentConnectionImpl"));
            asInterface.setup(zzc, new zzad(zzad), ObjectWrapper.wrap(scheduledExecutorService), new zzab(zzak));
            return asInterface;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    private static long zza(Long l) {
        if (l == null) {
            return -1;
        }
        if (l.longValue() != -1) {
            return l.longValue();
        }
        throw new IllegalArgumentException("Tag parameter clashed with NO_TAG value");
    }

    private static zzbb zza(zzah zzah) {
        return new zzaa(zzah);
    }

    private static Long zzf(long j) {
        return j == -1 ? null : Long.valueOf(j);
    }

    public void compareAndPut(List<String> list, IObjectWrapper iObjectWrapper, String str, zzah zzah) {
        this.zzfs.zza(list, ObjectWrapper.unwrap(iObjectWrapper), str, zza(zzah));
    }

    public void initialize() {
        this.zzfs.initialize();
    }

    public void interrupt(String str) {
        this.zzfs.interrupt(str);
    }

    public boolean isInterrupted(String str) {
        return this.zzfs.isInterrupted(str);
    }

    public void listen(List<String> list, IObjectWrapper iObjectWrapper, zzq zzq, long j, zzah zzah) {
        Long zzf = zzf(j);
        List<String> list2 = list;
        this.zzfs.zza(list2, (Map) ObjectWrapper.unwrap(iObjectWrapper), new zzz(this, zzq), zzf, zza(zzah));
    }

    public void merge(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) {
        this.zzfs.zza((List) list, (Map) ObjectWrapper.unwrap(iObjectWrapper), zza(zzah));
    }

    public void onDisconnectCancel(List<String> list, zzah zzah) {
        this.zzfs.zza((List) list, zza(zzah));
    }

    public void onDisconnectMerge(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) {
        this.zzfs.zzb((List) list, (Map) ObjectWrapper.unwrap(iObjectWrapper), zza(zzah));
    }

    public void onDisconnectPut(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) {
        this.zzfs.zzb((List) list, ObjectWrapper.unwrap(iObjectWrapper), zza(zzah));
    }

    public void purgeOutstandingWrites() {
        this.zzfs.purgeOutstandingWrites();
    }

    public void put(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) {
        this.zzfs.zza((List) list, ObjectWrapper.unwrap(iObjectWrapper), zza(zzah));
    }

    public void refreshAuthToken() {
        this.zzfs.refreshAuthToken();
    }

    public void refreshAuthToken2(String str) {
        this.zzfs.zzh(str);
    }

    public void resume(String str) {
        this.zzfs.resume(str);
    }

    public void setup(zzc zzc, zzk zzk, IObjectWrapper iObjectWrapper, zzw zzw) {
        zzib zzib;
        zzah zza = zzi.zza(zzc.zzfk);
        ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) ObjectWrapper.unwrap(iObjectWrapper);
        zzak zzac = new zzac(zzw);
        switch (zzc.zzfl) {
            case 1:
                zzib = zzib.DEBUG;
                break;
            case 2:
                zzib = zzib.INFO;
                break;
            case 3:
                zzib = zzib.WARN;
                break;
            case 4:
                zzib = zzib.ERROR;
                break;
            default:
                zzib = zzib.NONE;
                break;
        }
        this.zzfs = new zzal(new zzaf(new zzhw(zzib, zzc.zzfm), new zzaf(zzk), scheduledExecutorService, zzc.zzcp, zzc.zzfn, zzc.zzcr, zzc.zzcs), zza, zzac);
    }

    public void shutdown() {
        this.zzfs.shutdown();
    }

    public void unlisten(List<String> list, IObjectWrapper iObjectWrapper) {
        this.zzfs.zza((List) list, (Map) ObjectWrapper.unwrap(iObjectWrapper));
    }
}
