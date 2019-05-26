package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements zzq<TResult> {
    private final Executor zzafk;
    private final Continuation<TResult, TContinuationResult> zzafl;
    private final zzu<TContinuationResult> zzafm;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzu<TContinuationResult> zzu) {
        this.zzafk = executor;
        this.zzafl = continuation;
        this.zzafm = zzu;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzafk.execute(new zzd(this, task));
    }
}
