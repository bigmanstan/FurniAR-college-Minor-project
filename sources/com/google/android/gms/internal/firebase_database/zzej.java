package com.google.android.gms.internal.firebase_database;

final class zzej implements zzgm<zzed, Void> {
    private final /* synthetic */ zzee zzlb;

    zzej(zzee zzee) {
        this.zzlb = zzee;
    }

    public final /* synthetic */ Object zza(zzch zzch, Object obj, Object obj2) {
        zzed zzed = (zzed) obj;
        if (zzch.isEmpty() || !zzed.zzci()) {
            for (zzhi zzeo : zzed.zzch()) {
                zzhh zzeo2 = zzeo.zzeo();
                this.zzlb.zzkv.zza(zzee.zzd(zzeo2), this.zzlb.zze(zzeo2));
            }
        } else {
            zzhh zzeo3 = zzed.zzcj().zzeo();
            this.zzlb.zzkv.zza(zzee.zzd(zzeo3), this.zzlb.zze(zzeo3));
        }
        return null;
    }
}
