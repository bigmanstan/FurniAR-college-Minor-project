package com.google.android.gms.internal.firebase_database;

import android.support.annotation.NonNull;
import com.google.firebase.FirebaseApp.IdTokenListener;
import com.google.firebase.internal.InternalTokenResult;

final class zzn implements IdTokenListener {
    final /* synthetic */ zzbs zzbf;
    private final /* synthetic */ zzk zzbg;

    zzn(zzk zzk, zzbs zzbs) {
        this.zzbg = zzk;
        this.zzbf = zzbs;
    }

    public final void onIdTokenChanged(@NonNull InternalTokenResult internalTokenResult) {
        this.zzbg.zzbc.execute(new zzo(this, internalTokenResult));
    }
}
