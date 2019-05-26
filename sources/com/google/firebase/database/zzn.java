package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import java.util.Map;

final class zzn implements Runnable {
    private final /* synthetic */ OnDisconnect zzaq;
    private final /* synthetic */ Map zzar;
    private final /* synthetic */ Map zzas;
    private final /* synthetic */ zzkn zzx;

    zzn(OnDisconnect onDisconnect, Map map, zzkn zzkn, Map map2) {
        this.zzaq = onDisconnect;
        this.zzar = map;
        this.zzx = zzkn;
        this.zzas = map2;
    }

    public final void run() {
        this.zzaq.zzai.zza(this.zzaq.zzap, this.zzar, (CompletionListener) this.zzx.zzgv(), this.zzas);
    }
}
