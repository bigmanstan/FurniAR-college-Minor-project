package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzo implements Runnable {
    private final /* synthetic */ OnDisconnect zzaq;
    private final /* synthetic */ zzkn zzx;

    zzo(OnDisconnect onDisconnect, zzkn zzkn) {
        this.zzaq = onDisconnect;
        this.zzx = zzkn;
    }

    public final void run() {
        this.zzaq.zzai.zza(this.zzaq.zzap, (CompletionListener) this.zzx.zzgv());
    }
}
