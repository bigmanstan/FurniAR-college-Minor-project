package com.google.firebase.database;

import java.util.Iterator;

final class zzk implements Iterable<MutableData> {
    final /* synthetic */ MutableData zzan;
    final /* synthetic */ Iterator zzn;

    zzk(MutableData mutableData, Iterator it) {
        this.zzan = mutableData;
        this.zzn = it;
    }

    public final Iterator<MutableData> iterator() {
        return new zzl(this);
    }
}
