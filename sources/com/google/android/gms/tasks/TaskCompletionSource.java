package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzu<TResult> zzafh = new zzu();

    public TaskCompletionSource(@NonNull CancellationToken cancellationToken) {
        cancellationToken.onCanceledRequested(new zzs(this));
    }

    @NonNull
    public Task<TResult> getTask() {
        return this.zzafh;
    }

    public void setException(@NonNull Exception exception) {
        this.zzafh.setException(exception);
    }

    public void setResult(TResult tResult) {
        this.zzafh.setResult(tResult);
    }

    public boolean trySetException(@NonNull Exception exception) {
        return this.zzafh.trySetException(exception);
    }

    public boolean trySetResult(TResult tResult) {
        return this.zzafh.trySetResult(tResult);
    }
}
