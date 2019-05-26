package com.google.firebase.database;

import android.support.annotation.NonNull;
import com.google.android.gms.internal.firebase_database.zziz;
import java.util.Iterator;

final class zzl implements Iterator<MutableData> {
    private final /* synthetic */ zzk zzao;

    zzl(zzk zzk) {
        this.zzao = zzk;
    }

    public final boolean hasNext() {
        return this.zzao.zzn.hasNext();
    }

    @NonNull
    public final /* synthetic */ Object next() {
        return new MutableData(this.zzao.zzan.zzal, this.zzao.zzan.zzam.zza(((zziz) this.zzao.zzn.next()).zzge()));
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
