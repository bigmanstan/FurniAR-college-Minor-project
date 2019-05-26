package com.google.android.gms.internal.firebase_database;

import com.google.firebase.FirebaseApp.BackgroundStateChangeListener;

final class zzt implements BackgroundStateChangeListener {
    private final /* synthetic */ zzaj zzbp;

    zzt(zzq zzq, zzaj zzaj) {
        this.zzbp = zzaj;
    }

    public final void onBackgroundStateChanged(boolean z) {
        if (z) {
            this.zzbp.interrupt("app_in_background");
        } else {
            this.zzbp.resume("app_in_background");
        }
    }
}
