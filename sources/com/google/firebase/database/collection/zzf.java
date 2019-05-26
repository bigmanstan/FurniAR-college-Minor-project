package com.google.firebase.database.collection;

import java.util.Iterator;

final class zzf implements Iterable<zzh> {
    private final int length;
    private long value;

    public zzf(int i) {
        i++;
        this.length = (int) Math.floor(Math.log((double) i) / Math.log(2.0d));
        this.value = (((long) Math.pow(2.0d, (double) this.length)) - 1) & ((long) i);
    }

    public final Iterator<zzh> iterator() {
        return new zzg(this);
    }
}
