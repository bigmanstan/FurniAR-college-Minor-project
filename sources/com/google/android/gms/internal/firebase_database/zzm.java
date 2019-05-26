package com.google.android.gms.internal.firebase_database;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.GetTokenResult;

final class zzm implements OnSuccessListener<GetTokenResult> {
    private final /* synthetic */ zzbr zzbe;

    zzm(zzk zzk, zzbr zzbr) {
        this.zzbe = zzbr;
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzbe.zzf(((GetTokenResult) obj).getToken());
    }
}
