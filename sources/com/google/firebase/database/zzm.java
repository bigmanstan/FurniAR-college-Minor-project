package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzm implements Runnable {
    private final /* synthetic */ OnDisconnect zzaq;
    private final /* synthetic */ zzja zzw;
    private final /* synthetic */ zzkn zzx;

    zzm(OnDisconnect onDisconnect, zzja zzja, zzkn zzkn) {
        this.zzaq = onDisconnect;
        this.zzw = zzja;
        this.zzx = zzkn;
    }

    public final void run() {
        this.zzaq.zzai.zzb(this.zzaq.zzap, this.zzw, (CompletionListener) this.zzx.zzgv());
    }
}
