package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zze<TResult, TContinuationResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TContinuationResult>, zzq<TResult> {
    private final Executor zzafk;
    private final Continuation<TResult, Task<TContinuationResult>> zzafl;
    private final zzu<TContinuationResult> zzafm;

    public zze(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzu<TContinuationResult> zzu) {
        this.zzafk = executor;
        this.zzafl = continuation;
        this.zzafm = zzu;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onCanceled() {
        this.zzafm.zzdp();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzafk.execute(new zzf(this, task));
    }

    public final void onFailure(@NonNull Exception exception) {
        this.zzafm.setException(exception);
    }

    public final void onSuccess(TContinuationResult tContinuationResult) {
        this.zzafm.setResult(tContinuationResult);
    }
}
