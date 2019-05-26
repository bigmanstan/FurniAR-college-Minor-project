package com.google.p002a.p003a.p004a.p005a.p006a;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.google.a.a.a.a.a.c */
final class C0350c {
    /* renamed from: a */
    private final ConcurrentHashMap<C0351d, List<Throwable>> f12a = new ConcurrentHashMap(16, 0.75f, 10);
    /* renamed from: b */
    private final ReferenceQueue<Throwable> f13b = new ReferenceQueue();

    C0350c() {
    }

    /* renamed from: a */
    public final List<Throwable> m6a(Throwable th, boolean z) {
        while (true) {
            Reference poll = this.f13b.poll();
            if (poll == null) {
                break;
            }
            this.f12a.remove(poll);
        }
        List<Throwable> list = (List) this.f12a.get(new C0351d(th, null));
        if (list != null) {
            return list;
        }
        List vector = new Vector(2);
        List<Throwable> list2 = (List) this.f12a.putIfAbsent(new C0351d(th, this.f13b), vector);
        return list2 == null ? vector : list2;
    }
}
