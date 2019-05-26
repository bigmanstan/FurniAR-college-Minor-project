package com.google.android.gms.internal.firebase_database;

import android.os.Handler;
import android.os.Looper;

public final class zzp implements zzcg {
    private final Handler handler = new Handler(Looper.getMainLooper());

    public final void restart() {
    }

    public final void shutdown() {
    }

    public final void zza(Runnable runnable) {
        this.handler.post(runnable);
    }
}
