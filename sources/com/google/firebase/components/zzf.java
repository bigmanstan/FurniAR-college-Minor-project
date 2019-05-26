package com.google.firebase.components;

import android.support.annotation.GuardedBy;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

class zzf implements Publisher, Subscriber {
    @GuardedBy("this")
    private final Map<Class<?>, ConcurrentHashMap<EventHandler<Object>, Executor>> zza = new HashMap();
    @GuardedBy("this")
    private Queue<Event<?>> zzb = new ArrayDeque();
    private final Executor zzc;

    zzf(Executor executor) {
        this.zzc = executor;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void publish(com.google.firebase.events.Event<?> r5) {
        /*
        r4 = this;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r5);
        monitor-enter(r4);
        r0 = r4.zzb;	 Catch:{ all -> 0x0036 }
        if (r0 == 0) goto L_0x000f;
    L_0x0008:
        r0 = r4.zzb;	 Catch:{ all -> 0x0036 }
        r0.add(r5);	 Catch:{ all -> 0x0036 }
        monitor-exit(r4);	 Catch:{ all -> 0x0036 }
        return;
    L_0x000f:
        monitor-exit(r4);	 Catch:{ all -> 0x0036 }
        r0 = r4.zza(r5);
        r0 = r0.iterator();
    L_0x0019:
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x0035;
    L_0x001f:
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        r2 = r1.getValue();
        r2 = (java.util.concurrent.Executor) r2;
        r3 = new com.google.firebase.components.zzg;
        r3.<init>(r1, r5);
        r2.execute(r3);
        goto L_0x0019;
    L_0x0035:
        return;
    L_0x0036:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0036 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.components.zzf.publish(com.google.firebase.events.Event):void");
    }

    private synchronized Set<Entry<EventHandler<Object>, Executor>> zza(Event<?> event) {
        Map map;
        map = (Map) this.zza.get(event.getType());
        return map == null ? Collections.emptySet() : map.entrySet();
    }

    public synchronized <T> void subscribe(Class<T> type, EventHandler<? super T> eventHandler, Executor executor) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(eventHandler);
        Preconditions.checkNotNull(executor);
        if (!this.zza.containsKey(type)) {
            this.zza.put(type, new ConcurrentHashMap());
        }
        ((ConcurrentHashMap) this.zza.get(type)).put(eventHandler, executor);
    }

    public <T> void subscribe(Class<T> cls, EventHandler<? super T> eventHandler) {
        subscribe(cls, eventHandler, this.zzc);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T> void unsubscribe(java.lang.Class<T> r2, com.google.firebase.events.EventHandler<? super T> r3) {
        /*
        r1 = this;
        monitor-enter(r1);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r2);	 Catch:{ all -> 0x002b }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);	 Catch:{ all -> 0x002b }
        r0 = r1.zza;	 Catch:{ all -> 0x002b }
        r0 = r0.containsKey(r2);	 Catch:{ all -> 0x002b }
        if (r0 != 0) goto L_0x0011;
    L_0x000f:
        monitor-exit(r1);
        return;
    L_0x0011:
        r0 = r1.zza;	 Catch:{ all -> 0x002b }
        r0 = r0.get(r2);	 Catch:{ all -> 0x002b }
        r0 = (java.util.concurrent.ConcurrentHashMap) r0;	 Catch:{ all -> 0x002b }
        r0.remove(r3);	 Catch:{ all -> 0x002b }
        r3 = r0.isEmpty();	 Catch:{ all -> 0x002b }
        if (r3 == 0) goto L_0x0029;
        r3 = r1.zza;	 Catch:{ all -> 0x002b }
        r3.remove(r2);	 Catch:{ all -> 0x002b }
    L_0x0029:
        monitor-exit(r1);
        return;
    L_0x002b:
        r2 = move-exception;
        monitor-exit(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.components.zzf.unsubscribe(java.lang.Class, com.google.firebase.events.EventHandler):void");
    }

    final void zza() {
        synchronized (this) {
            Queue queue;
            if (this.zzb != null) {
                queue = this.zzb;
                this.zzb = null;
            } else {
                queue = null;
            }
        }
        if (r0 != null) {
            for (Event publish : r0) {
                publish(publish);
            }
        }
    }
}
