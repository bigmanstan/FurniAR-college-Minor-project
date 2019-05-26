package com.google.firebase.database;

import java.util.Iterator;

final class zza implements Iterable<DataSnapshot> {
    final /* synthetic */ Iterator zzn;
    final /* synthetic */ DataSnapshot zzo;

    zza(DataSnapshot dataSnapshot, Iterator it) {
        this.zzo = dataSnapshot;
        this.zzn = it;
    }

    public final Iterator<DataSnapshot> iterator() {
        return new zzb(this);
    }
}
