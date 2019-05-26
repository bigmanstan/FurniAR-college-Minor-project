package com.google.android.gms.internal.stable;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

final class zzm {
    private final ConcurrentHashMap<zzn, List<Throwable>> zzahj = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzahk = new ReferenceQueue();

    zzm() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        while (true) {
            Reference poll = this.zzahk.poll();
            if (poll == null) {
                break;
            }
            this.zzahj.remove(poll);
        }
        List<Throwable> list = (List) this.zzahj.get(new zzn(th, null));
        if (list != null) {
            return list;
        }
        List vector = new Vector(2);
        List<Throwable> list2 = (List) this.zzahj.putIfAbsent(new zzn(th, this.zzahk), vector);
        return list2 == null ? vector : list2;
    }
}
