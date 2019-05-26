package android.support.v4.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize) {
        if (maxSize > 0) {
            this.maxSize = maxSize;
            this.map = new LinkedHashMap(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public void resize(int maxSize) {
        if (maxSize > 0) {
            synchronized (this) {
                this.maxSize = maxSize;
            }
            trimToSize(maxSize);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r6) {
        /*
        r5 = this;
        if (r6 == 0) goto L_0x005a;
    L_0x0002:
        monitor-enter(r5);
        r0 = 0;
        r1 = r5.map;	 Catch:{ all -> 0x0052 }
        r1 = r1.get(r6);	 Catch:{ all -> 0x0052 }
        if (r1 == 0) goto L_0x0014;
    L_0x000c:
        r0 = r5.hitCount;	 Catch:{ all -> 0x0058 }
        r0 = r0 + 1;
        r5.hitCount = r0;	 Catch:{ all -> 0x0058 }
        monitor-exit(r5);	 Catch:{ all -> 0x0058 }
        return r1;
    L_0x0014:
        r2 = r5.missCount;	 Catch:{ all -> 0x0058 }
        r2 = r2 + 1;
        r5.missCount = r2;	 Catch:{ all -> 0x0058 }
        monitor-exit(r5);	 Catch:{ all -> 0x0058 }
        r2 = r5.create(r6);
        if (r2 != 0) goto L_0x0022;
    L_0x0021:
        return r0;
    L_0x0022:
        monitor-enter(r5);
        r0 = r5.createCount;	 Catch:{ all -> 0x004f }
        r0 = r0 + 1;
        r5.createCount = r0;	 Catch:{ all -> 0x004f }
        r0 = r5.map;	 Catch:{ all -> 0x004f }
        r0 = r0.put(r6, r2);	 Catch:{ all -> 0x004f }
        r1 = r0;
        if (r1 == 0) goto L_0x0038;
    L_0x0032:
        r0 = r5.map;	 Catch:{ all -> 0x004f }
        r0.put(r6, r1);	 Catch:{ all -> 0x004f }
        goto L_0x0041;
    L_0x0038:
        r0 = r5.size;	 Catch:{ all -> 0x004f }
        r3 = r5.safeSizeOf(r6, r2);	 Catch:{ all -> 0x004f }
        r0 = r0 + r3;
        r5.size = r0;	 Catch:{ all -> 0x004f }
    L_0x0041:
        monitor-exit(r5);	 Catch:{ all -> 0x004f }
        if (r1 == 0) goto L_0x0049;
    L_0x0044:
        r0 = 0;
        r5.entryRemoved(r0, r6, r2, r1);
        return r1;
    L_0x0049:
        r0 = r5.maxSize;
        r5.trimToSize(r0);
        return r2;
    L_0x004f:
        r0 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x004f }
        throw r0;
    L_0x0052:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0056:
        monitor-exit(r5);	 Catch:{ all -> 0x0058 }
        throw r0;
    L_0x0058:
        r0 = move-exception;
        goto L_0x0056;
    L_0x005a:
        r0 = new java.lang.NullPointerException;
        r1 = "key == null";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.get(java.lang.Object):V");
    }

    public final V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        V previous;
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(key, value);
            previous = this.map.put(key, value);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize);
        return previous;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r7) {
        /*
        r6 = this;
        r0 = 0;
        r1 = r0;
        r2 = r1;
    L_0x0003:
        monitor-enter(r6);
        r3 = r6.size;	 Catch:{ all -> 0x0075 }
        if (r3 < 0) goto L_0x0056;
    L_0x0008:
        r3 = r6.map;	 Catch:{ all -> 0x0075 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0075 }
        if (r3 == 0) goto L_0x0014;
    L_0x0010:
        r3 = r6.size;	 Catch:{ all -> 0x0075 }
        if (r3 != 0) goto L_0x0056;
    L_0x0014:
        r3 = r6.size;	 Catch:{ all -> 0x0075 }
        if (r3 <= r7) goto L_0x0054;
    L_0x0018:
        r3 = r6.map;	 Catch:{ all -> 0x0075 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0075 }
        if (r3 == 0) goto L_0x0021;
    L_0x0020:
        goto L_0x0054;
    L_0x0021:
        r3 = r6.map;	 Catch:{ all -> 0x0075 }
        r3 = r3.entrySet();	 Catch:{ all -> 0x0075 }
        r3 = r3.iterator();	 Catch:{ all -> 0x0075 }
        r3 = r3.next();	 Catch:{ all -> 0x0075 }
        r3 = (java.util.Map.Entry) r3;	 Catch:{ all -> 0x0075 }
        r4 = r3.getKey();	 Catch:{ all -> 0x0075 }
        r1 = r4;
        r4 = r3.getValue();	 Catch:{ all -> 0x0075 }
        r2 = r4;
        r4 = r6.map;	 Catch:{ all -> 0x0075 }
        r4.remove(r1);	 Catch:{ all -> 0x0075 }
        r4 = r6.size;	 Catch:{ all -> 0x0075 }
        r5 = r6.safeSizeOf(r1, r2);	 Catch:{ all -> 0x0075 }
        r4 = r4 - r5;
        r6.size = r4;	 Catch:{ all -> 0x0075 }
        r4 = r6.evictionCount;	 Catch:{ all -> 0x0075 }
        r5 = 1;
        r4 = r4 + r5;
        r6.evictionCount = r4;	 Catch:{ all -> 0x0075 }
        monitor-exit(r6);	 Catch:{ all -> 0x0075 }
        r6.entryRemoved(r5, r1, r2, r0);
        goto L_0x0003;
    L_0x0054:
        monitor-exit(r6);	 Catch:{ all -> 0x0075 }
        return;
    L_0x0056:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0075 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0075 }
        r3.<init>();	 Catch:{ all -> 0x0075 }
        r4 = r6.getClass();	 Catch:{ all -> 0x0075 }
        r4 = r4.getName();	 Catch:{ all -> 0x0075 }
        r3.append(r4);	 Catch:{ all -> 0x0075 }
        r4 = ".sizeOf() is reporting inconsistent results!";
        r3.append(r4);	 Catch:{ all -> 0x0075 }
        r3 = r3.toString();	 Catch:{ all -> 0x0075 }
        r0.<init>(r3);	 Catch:{ all -> 0x0075 }
        throw r0;	 Catch:{ all -> 0x0075 }
    L_0x0075:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0075 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.trimToSize(int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V remove(K r6) {
        /*
        r5 = this;
        if (r6 == 0) goto L_0x0025;
    L_0x0002:
        monitor-enter(r5);
        r0 = 0;
        r1 = r5.map;	 Catch:{ all -> 0x001d }
        r1 = r1.remove(r6);	 Catch:{ all -> 0x001d }
        if (r1 == 0) goto L_0x0015;
    L_0x000c:
        r2 = r5.size;	 Catch:{ all -> 0x0023 }
        r3 = r5.safeSizeOf(r6, r1);	 Catch:{ all -> 0x0023 }
        r2 = r2 - r3;
        r5.size = r2;	 Catch:{ all -> 0x0023 }
    L_0x0015:
        monitor-exit(r5);	 Catch:{ all -> 0x0023 }
        if (r1 == 0) goto L_0x001c;
    L_0x0018:
        r2 = 0;
        r5.entryRemoved(r2, r6, r1, r0);
    L_0x001c:
        return r1;
    L_0x001d:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0021:
        monitor-exit(r5);	 Catch:{ all -> 0x0023 }
        throw r0;
    L_0x0023:
        r0 = move-exception;
        goto L_0x0021;
    L_0x0025:
        r0 = new java.lang.NullPointerException;
        r1 = "key == null";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.remove(java.lang.Object):V");
    }

    protected void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    protected V create(K k) {
        return null;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative size: ");
        stringBuilder.append(key);
        stringBuilder.append("=");
        stringBuilder.append(value);
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected int sizeOf(K k, V v) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int hitPercent;
        int accesses = this.hitCount + this.missCount;
        hitPercent = accesses != 0 ? (this.hitCount * 100) / accesses : 0;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent)});
    }
}
