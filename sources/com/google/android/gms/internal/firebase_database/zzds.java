package com.google.android.gms.internal.firebase_database;

import java.util.Map;

final class zzds implements Runnable {
    private final /* synthetic */ zzbz zzkd;
    private final /* synthetic */ zzdo zzke;

    zzds(zzdo zzdo, zzbz zzbz) {
        this.zzke = zzdo;
        this.zzkd = zzbz;
    }

    public final void run() {
        synchronized (this.zzke.zzkc) {
            if (this.zzke.zzkc.containsKey(this.zzkd)) {
                for (zzck resume : ((Map) this.zzke.zzkc.get(this.zzkd)).values()) {
                    resume.resume();
                }
            }
        }
    }
}
