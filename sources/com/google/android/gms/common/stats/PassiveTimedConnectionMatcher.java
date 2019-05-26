package com.google.android.gms.common.stats;

import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

public class PassiveTimedConnectionMatcher {
    private final long zzym;
    private final int zzyn;
    private final SimpleArrayMap<String, Long> zzyo;

    public PassiveTimedConnectionMatcher() {
        this.zzym = 60000;
        this.zzyn = 10;
        this.zzyo = new SimpleArrayMap(10);
    }

    public PassiveTimedConnectionMatcher(int i, long j) {
        this.zzym = j;
        this.zzyn = i;
        this.zzyo = new SimpleArrayMap();
    }

    public Long get(String str) {
        Long l;
        synchronized (this) {
            l = (Long) this.zzyo.get(str);
        }
        return l;
    }

    public Long put(String str) {
        Long l;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.zzym;
        synchronized (this) {
            while (this.zzyo.size() >= this.zzyn) {
                for (int size = this.zzyo.size() - 1; size >= 0; size--) {
                    if (elapsedRealtime - ((Long) this.zzyo.valueAt(size)).longValue() > j) {
                        this.zzyo.removeAt(size);
                    }
                }
                j /= 2;
                int i = this.zzyn;
                StringBuilder stringBuilder = new StringBuilder(94);
                stringBuilder.append("The max capacity ");
                stringBuilder.append(i);
                stringBuilder.append(" is not enough. Current durationThreshold is: ");
                stringBuilder.append(j);
                Log.w("ConnectionTracker", stringBuilder.toString());
            }
            l = (Long) this.zzyo.put(str, Long.valueOf(elapsedRealtime));
        }
        return l;
    }

    public boolean remove(String str) {
        boolean z;
        synchronized (this) {
            z = this.zzyo.remove(str) != null;
        }
        return z;
    }

    public boolean removeByPrefix(String str) {
        boolean z;
        synchronized (this) {
            int i = 0;
            z = false;
            while (i < size()) {
                String str2 = (String) this.zzyo.keyAt(i);
                if (str2 != null && str2.startsWith(str)) {
                    this.zzyo.remove(str2);
                    z = true;
                }
                i++;
            }
        }
        return z;
    }

    public int size() {
        int size;
        synchronized (this) {
            size = this.zzyo.size();
        }
        return size;
    }
}
