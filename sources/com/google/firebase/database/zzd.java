package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzid;
import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzd implements Runnable {
    private final /* synthetic */ zzkn zzx;
    private final /* synthetic */ DatabaseReference zzy;
    private final /* synthetic */ zzja zzz;

    zzd(DatabaseReference databaseReference, zzja zzja, zzkn zzkn) {
        this.zzy = databaseReference;
        this.zzz = zzja;
        this.zzx = zzkn;
    }

    public final void run() {
        this.zzy.zzai.zza(this.zzy.zzap.zza(zzid.zzfe()), this.zzz, (CompletionListener) this.zzx.zzgv());
    }
}
