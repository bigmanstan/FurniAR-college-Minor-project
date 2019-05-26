package com.google.android.gms.internal.firebase_database;

import java.lang.Thread.UncaughtExceptionHandler;

final class zzez implements zzey {
    zzez() {
    }

    public final void zza(Thread thread, String str) {
        thread.setName(str);
    }

    public final void zza(Thread thread, UncaughtExceptionHandler uncaughtExceptionHandler) {
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    public final void zza(Thread thread, boolean z) {
        thread.setDaemon(true);
    }
}
