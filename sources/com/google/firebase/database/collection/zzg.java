package com.google.firebase.database.collection;

import java.util.Iterator;

final class zzg implements Iterator<zzh> {
    private int zzy = (this.zzz.length - 1);
    private final /* synthetic */ zzf zzz;

    zzg(zzf zzf) {
        this.zzz = zzf;
    }

    public final boolean hasNext() {
        return this.zzy >= 0;
    }

    public final /* synthetic */ Object next() {
        long zzb = this.zzz.value & ((long) (1 << this.zzy));
        zzh zzh = new zzh();
        zzh.zzaa = zzb == 0;
        zzh.zzab = (int) Math.pow(2.0d, (double) this.zzy);
        this.zzy--;
        return zzh;
    }

    public final void remove() {
    }
}
