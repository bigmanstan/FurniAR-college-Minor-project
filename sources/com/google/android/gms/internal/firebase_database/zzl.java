package com.google.android.gms.internal.firebase_database;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

final class zzl implements OnFailureListener {
    private final /* synthetic */ zzbr zzbe;

    zzl(zzk zzk, zzbr zzbr) {
        this.zzbe = zzbr;
    }

    public final void onFailure(@NonNull Exception exception) {
        Object obj;
        if (!(exception instanceof FirebaseApiNotAvailableException)) {
            if (!(exception instanceof FirebaseNoSignedInUserException)) {
                obj = null;
                if (obj == null) {
                    this.zzbe.zzf(null);
                } else {
                    this.zzbe.onError(exception.getMessage());
                }
            }
        }
        obj = 1;
        if (obj == null) {
            this.zzbe.onError(exception.getMessage());
        } else {
            this.zzbe.zzf(null);
        }
    }
}
