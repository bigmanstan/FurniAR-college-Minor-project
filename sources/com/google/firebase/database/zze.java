package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzbv;
import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import java.util.Map;

final class zze implements Runnable {
    private final /* synthetic */ zzbv zzaa;
    private final /* synthetic */ Map zzab;
    private final /* synthetic */ zzkn zzx;
    private final /* synthetic */ DatabaseReference zzy;

    zze(DatabaseReference databaseReference, zzbv zzbv, zzkn zzkn, Map map) {
        this.zzy = databaseReference;
        this.zzaa = zzbv;
        this.zzx = zzkn;
        this.zzab = map;
    }

    public final void run() {
        this.zzy.zzai.zza(this.zzy.zzap, this.zzaa, (CompletionListener) this.zzx.zzgv(), this.zzab);
    }
}
