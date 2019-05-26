package com.google.android.gms.internal.firebase_database;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

final class zzki extends ScheduledThreadPoolExecutor {
    private final /* synthetic */ zzkh zzuj;

    zzki(zzkh zzkh, int i, ThreadFactory threadFactory) {
        this.zzuj = zzkh;
        super(1, threadFactory);
    }

    protected final void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        if (th == null && (runnable instanceof Future)) {
            Future future = (Future) runnable;
            try {
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException e) {
            } catch (ExecutionException e2) {
                th = e2.getCause();
            } catch (InterruptedException e3) {
                Thread.currentThread().interrupt();
            }
        }
        if (th != null) {
            this.zzuj.zza(th);
        }
    }
}
