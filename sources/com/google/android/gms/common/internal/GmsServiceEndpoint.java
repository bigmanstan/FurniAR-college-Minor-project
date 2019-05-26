package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;

public class GmsServiceEndpoint {
    @NonNull
    private final String mPackageName;
    private final int zztq;
    @NonNull
    private final String zzue;
    private final boolean zzuf;

    public GmsServiceEndpoint(@NonNull String str, @NonNull String str2, boolean z, int i) {
        this.mPackageName = str;
        this.zzue = str2;
        this.zzuf = z;
        this.zztq = i;
    }

    final int getBindFlags() {
        return this.zztq;
    }

    @NonNull
    final String getPackageName() {
        return this.mPackageName;
    }

    @NonNull
    final String zzcw() {
        return this.zzue;
    }
}
