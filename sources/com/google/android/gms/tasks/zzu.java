package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.GuardedBy;

final class zzu<TResult> extends Task<TResult> {
    private final Object mLock = new Object();
    private final zzr<TResult> zzage = new zzr();
    @GuardedBy("mLock")
    private boolean zzagf;
    @GuardedBy("mLock")
    private TResult zzagg;
    @GuardedBy("mLock")
    private Exception zzagh;
    private volatile boolean zzfi;

    private static class zza extends LifecycleCallback {
        private final List<WeakReference<zzq<?>>> zzagi = new ArrayList();

        private zza(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("TaskOnStopCallback", this);
        }

        public static zza zze(Activity activity) {
            LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
            zza zza = (zza) fragment.getCallbackOrNull("TaskOnStopCallback", zza.class);
            return zza == null ? new zza(fragment) : zza;
        }

        @MainThread
        public void onStop() {
            synchronized (this.zzagi) {
                for (WeakReference weakReference : this.zzagi) {
                    zzq zzq = (zzq) weakReference.get();
                    if (zzq != null) {
                        zzq.cancel();
                    }
                }
                this.zzagi.clear();
            }
        }

        public final <T> void zzb(zzq<T> zzq) {
            synchronized (this.zzagi) {
                this.zzagi.add(new WeakReference(zzq));
            }
        }
    }

    zzu() {
    }

    @GuardedBy("mLock")
    private final void zzdq() {
        Preconditions.checkState(this.zzagf, "Task is not yet complete");
    }

    @GuardedBy("mLock")
    private final void zzdr() {
        Preconditions.checkState(this.zzagf ^ 1, "Task is already complete");
    }

    @GuardedBy("mLock")
    private final void zzds() {
        if (this.zzfi) {
            throw new CancellationException("Task is already canceled.");
        }
    }

    private final void zzdt() {
        synchronized (this.mLock) {
            if (this.zzagf) {
                this.zzage.zza((Task) this);
                return;
            }
        }
    }

    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull Activity activity, @NonNull OnCanceledListener onCanceledListener) {
        zzq zzg = new zzg(TaskExecutors.MAIN_THREAD, onCanceledListener);
        this.zzage.zza(zzg);
        zza.zze(activity).zzb(zzg);
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull OnCanceledListener onCanceledListener) {
        return addOnCanceledListener(TaskExecutors.MAIN_THREAD, onCanceledListener);
    }

    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull Executor executor, @NonNull OnCanceledListener onCanceledListener) {
        this.zzage.zza(new zzg(executor, onCanceledListener));
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzq zzi = new zzi(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzage.zza(zzi);
        zza.zze(activity).zzb(zzi);
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, (OnCompleteListener) onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzage.zza(new zzi(executor, onCompleteListener));
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzq zzk = new zzk(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzage.zza(zzk);
        zza.zze(activity).zzb(zzk);
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzage.zza(new zzk(executor, onFailureListener));
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzq zzm = new zzm(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzage.zza(zzm);
        zza.zze(activity).zzb(zzm);
        zzdt();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, (OnSuccessListener) onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzage.zza(new zzm(executor, onSuccessListener));
        zzdt();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        Task zzu = new zzu();
        this.zzage.zza(new zzc(executor, continuation, zzu));
        zzdt();
        return zzu;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        Task zzu = new zzu();
        this.zzage.zza(new zze(executor, continuation, zzu));
        zzdt();
        return zzu;
    }

    @Nullable
    public final Exception getException() {
        Exception exception;
        synchronized (this.mLock) {
            exception = this.zzagh;
        }
        return exception;
    }

    public final TResult getResult() {
        TResult tResult;
        synchronized (this.mLock) {
            zzdq();
            zzds();
            if (this.zzagh == null) {
                tResult = this.zzagg;
            } else {
                throw new RuntimeExecutionException(this.zzagh);
            }
        }
        return tResult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tResult;
        synchronized (this.mLock) {
            zzdq();
            zzds();
            if (cls.isInstance(this.zzagh)) {
                throw ((Throwable) cls.cast(this.zzagh));
            } else if (this.zzagh == null) {
                tResult = this.zzagg;
            } else {
                throw new RuntimeExecutionException(this.zzagh);
            }
        }
        return tResult;
    }

    public final boolean isCanceled() {
        return this.zzfi;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzagf;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzagf && !this.zzfi && this.zzagh == null;
        }
        return z;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(@NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return onSuccessTask(TaskExecutors.MAIN_THREAD, successContinuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        Task zzu = new zzu();
        this.zzage.zza(new zzo(executor, successContinuation, zzu));
        zzdt();
        return zzu;
    }

    public final void setException(@NonNull Exception exception) {
        Preconditions.checkNotNull(exception, "Exception must not be null");
        synchronized (this.mLock) {
            zzdr();
            this.zzagf = true;
            this.zzagh = exception;
        }
        this.zzage.zza((Task) this);
    }

    public final void setResult(TResult tResult) {
        synchronized (this.mLock) {
            zzdr();
            this.zzagf = true;
            this.zzagg = tResult;
        }
        this.zzage.zza((Task) this);
    }

    public final boolean trySetException(@NonNull Exception exception) {
        Preconditions.checkNotNull(exception, "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzagf) {
                return false;
            }
            this.zzagf = true;
            this.zzagh = exception;
            this.zzage.zza((Task) this);
            return true;
        }
    }

    public final boolean trySetResult(TResult tResult) {
        synchronized (this.mLock) {
            if (this.zzagf) {
                return false;
            }
            this.zzagf = true;
            this.zzagg = tResult;
            this.zzage.zza((Task) this);
            return true;
        }
    }

    public final boolean zzdp() {
        synchronized (this.mLock) {
            if (this.zzagf) {
                return false;
            }
            this.zzagf = true;
            this.zzfi = true;
            this.zzage.zza((Task) this);
            return true;
        }
    }
}
