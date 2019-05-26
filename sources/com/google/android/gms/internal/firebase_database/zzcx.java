package com.google.android.gms.internal.firebase_database;

final class zzcx implements zzbs {
    private final /* synthetic */ zzck zzil;

    zzcx(zzck zzck) {
        this.zzil = zzck;
    }

    public final void zzo(String str) {
        this.zzil.zzib.zza("Auth token changed, triggering auth token refresh", null, new Object[0]);
        this.zzil.zzfs.zzh(str);
    }
}
