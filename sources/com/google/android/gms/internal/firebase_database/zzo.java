package com.google.android.gms.internal.firebase_database;

import com.google.firebase.internal.InternalTokenResult;

final class zzo implements Runnable {
    private final /* synthetic */ InternalTokenResult zzbh;
    private final /* synthetic */ zzn zzbi;

    zzo(zzn zzn, InternalTokenResult internalTokenResult) {
        this.zzbi = zzn;
        this.zzbh = internalTokenResult;
    }

    public final void run() {
        this.zzbi.zzbf.zzo(this.zzbh.getToken());
    }
}
