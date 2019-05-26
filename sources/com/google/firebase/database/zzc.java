package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzc implements Runnable {
    private final /* synthetic */ zzja zzw;
    private final /* synthetic */ zzkn zzx;
    private final /* synthetic */ DatabaseReference zzy;

    zzc(DatabaseReference databaseReference, zzja zzja, zzkn zzkn) {
        this.zzy = databaseReference;
        this.zzw = zzja;
        this.zzx = zzkn;
    }

    public final void run() {
        this.zzy.zzai.zza(this.zzy.zzap, this.zzw, (CompletionListener) this.zzx.zzgv());
    }
}
