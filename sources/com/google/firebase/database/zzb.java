package com.google.firebase.database;

import android.support.annotation.NonNull;
import com.google.android.gms.internal.firebase_database.zzit;
import com.google.android.gms.internal.firebase_database.zziz;
import java.util.Iterator;

final class zzb implements Iterator<DataSnapshot> {
    private final /* synthetic */ zza zzp;

    zzb(zza zza) {
        this.zzp = zza;
    }

    public final boolean hasNext() {
        return this.zzp.zzn.hasNext();
    }

    @NonNull
    public final /* synthetic */ Object next() {
        zziz zziz = (zziz) this.zzp.zzn.next();
        return new DataSnapshot(this.zzp.zzo.zzm.child(zziz.zzge().zzfg()), zzit.zzj(zziz.zzd()));
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
