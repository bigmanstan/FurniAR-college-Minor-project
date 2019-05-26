package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

final class zza extends CancellationToken {
    private final zzu<Void> zzafh = new zzu();

    zza() {
    }

    public final void cancel() {
        this.zzafh.trySetResult(null);
    }

    public final boolean isCancellationRequested() {
        return this.zzafh.isComplete();
    }

    public final CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
        this.zzafh.addOnSuccessListener(new zzb(this, onTokenCanceledListener));
        return this;
    }
}
