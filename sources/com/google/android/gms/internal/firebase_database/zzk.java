package com.google.android.gms.internal.firebase_database;

import android.support.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import java.util.concurrent.ScheduledExecutorService;

public final class zzk implements zzbq {
    private final ScheduledExecutorService zzbc;
    private final FirebaseApp zzbd;

    public zzk(@NonNull FirebaseApp firebaseApp, @NonNull ScheduledExecutorService scheduledExecutorService) {
        this.zzbd = firebaseApp;
        this.zzbc = scheduledExecutorService;
    }

    public final void zza(zzbs zzbs) {
        this.zzbd.addIdTokenListener(new zzn(this, zzbs));
    }

    public final void zza(boolean z, @NonNull zzbr zzbr) {
        this.zzbd.getToken(z).addOnSuccessListener(this.zzbc, new zzm(this, zzbr)).addOnFailureListener(this.zzbc, new zzl(this, zzbr));
    }
}
