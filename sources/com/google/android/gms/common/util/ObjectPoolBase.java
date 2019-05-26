package com.google.android.gms.common.util;

import java.util.ArrayList;

public abstract class ObjectPoolBase<T> {
    private final ArrayList<T> zzaag;
    private final int zzaah;

    public ObjectPoolBase(int i) {
        this.zzaag = new ArrayList(i);
        this.zzaah = i;
    }

    public final T aquire() {
        synchronized (this.zzaag) {
            int size = this.zzaag.size();
            if (size > 0) {
                T remove = this.zzaag.remove(size - 1);
                return remove;
            }
            remove = newObject();
            return remove;
        }
    }

    protected boolean cleanUpObject(T t) {
        return true;
    }

    protected abstract T newObject();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean release(T r6) {
        /*
        r5 = this;
        r0 = r5.zzaag;
        monitor-enter(r0);
        r1 = r5.zzaag;	 Catch:{ all -> 0x0051 }
        r1 = r1.size();	 Catch:{ all -> 0x0051 }
        r2 = 0;
        r3 = r2;
    L_0x000b:
        if (r3 >= r1) goto L_0x003d;
    L_0x000d:
        r4 = r5.zzaag;	 Catch:{ all -> 0x0051 }
        r4 = r4.get(r3);	 Catch:{ all -> 0x0051 }
        if (r4 == r6) goto L_0x0018;
    L_0x0015:
        r3 = r3 + 1;
        goto L_0x000b;
    L_0x0018:
        r1 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0051 }
        r6 = java.lang.String.valueOf(r6);	 Catch:{ all -> 0x0051 }
        r2 = java.lang.String.valueOf(r6);	 Catch:{ all -> 0x0051 }
        r2 = r2.length();	 Catch:{ all -> 0x0051 }
        r2 = r2 + 25;
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0051 }
        r3.<init>(r2);	 Catch:{ all -> 0x0051 }
        r2 = "Object released already: ";
        r3.append(r2);	 Catch:{ all -> 0x0051 }
        r3.append(r6);	 Catch:{ all -> 0x0051 }
        r6 = r3.toString();	 Catch:{ all -> 0x0051 }
        r1.<init>(r6);	 Catch:{ all -> 0x0051 }
        throw r1;	 Catch:{ all -> 0x0051 }
    L_0x003d:
        r3 = r5.zzaah;	 Catch:{ all -> 0x0051 }
        if (r1 >= r3) goto L_0x004f;
    L_0x0041:
        r1 = r5.cleanUpObject(r6);	 Catch:{ all -> 0x0051 }
        if (r1 == 0) goto L_0x004f;
    L_0x0047:
        r1 = r5.zzaag;	 Catch:{ all -> 0x0051 }
        r1.add(r6);	 Catch:{ all -> 0x0051 }
        monitor-exit(r0);	 Catch:{ all -> 0x0051 }
        r6 = 1;
        return r6;
    L_0x004f:
        monitor-exit(r0);	 Catch:{ all -> 0x0051 }
        return r2;
    L_0x0051:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0051 }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.ObjectPoolBase.release(java.lang.Object):boolean");
    }
}
