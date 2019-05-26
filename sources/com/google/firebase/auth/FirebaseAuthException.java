package com.google.firebase.auth;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseException;

public class FirebaseAuthException extends FirebaseException {
    private final String zza;

    public FirebaseAuthException(@NonNull String errorCode, @NonNull String detailMessage) {
        super(detailMessage);
        this.zza = Preconditions.checkNotEmpty(errorCode);
    }

    @NonNull
    public String getErrorCode() {
        return this.zza;
    }
}
