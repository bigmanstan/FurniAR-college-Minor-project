package com.google.firebase.database.collection;

import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder.KeyTranslator;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zza<K, V> extends ImmutableSortedMap<K, V> {
    private final K[] zza;
    private final V[] zzb;
    private final Comparator<K> zzc;

    public zza(Comparator<K> comparator) {
        this.zza = new Object[0];
        this.zzb = new Object[0];
        this.zzc = comparator;
    }

    private zza(Comparator<K> comparator, K[] kArr, V[] vArr) {
        this.zza = kArr;
        this.zzb = vArr;
        this.zzc = comparator;
    }

    private final int zza(K k) {
        int i = 0;
        while (i < this.zza.length && this.zzc.compare(this.zza[i], k) < 0) {
            i++;
        }
        return i;
    }

    public static <A, B, C> zza<A, C> zza(List<A> list, Map<B, C> map, KeyTranslator<A, B> keyTranslator, Comparator<A> comparator) {
        Collections.sort(list, comparator);
        int size = list.size();
        Object[] objArr = new Object[size];
        Object[] objArr2 = new Object[size];
        int i = 0;
        for (Object next : list) {
            objArr[i] = next;
            objArr2[i] = map.get(keyTranslator.translate(next));
            i++;
        }
        return new zza(comparator, objArr, objArr2);
    }

    public static <K, V> zza<K, V> zza(Map<K, V> map, Comparator<K> comparator) {
        return zza(new ArrayList(map.keySet()), map, Builder.identityTranslator(), comparator);
    }

    private final Iterator<Entry<K, V>> zza(int i, boolean z) {
        return new zzb(this, i, z);
    }

    private static <T> T[] zza(T[] tArr, int i) {
        int length = tArr.length - 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        System.arraycopy(tArr, i + 1, obj, i, length - i);
        return obj;
    }

    private static <T> T[] zza(T[] tArr, int i, T t) {
        int length = tArr.length + 1;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, i);
        obj[i] = t;
        System.arraycopy(tArr, i, obj, i + 1, (length - i) - 1);
        return obj;
    }

    private final int zzb(K k) {
        Object[] objArr = this.zza;
        int length = objArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (this.zzc.compare(k, objArr[i]) == 0) {
                return i2;
            }
            i2++;
            i++;
        }
        return -1;
    }

    private static <T> T[] zzb(T[] tArr, int i, T t) {
        int length = tArr.length;
        Object obj = new Object[length];
        System.arraycopy(tArr, 0, obj, 0, length);
        obj[i] = t;
        return obj;
    }

    public final boolean containsKey(K k) {
        return zzb((Object) k) != -1;
    }

    public final V get(K k) {
        int zzb = zzb((Object) k);
        return zzb != -1 ? this.zzb[zzb] : null;
    }

    public final Comparator<K> getComparator() {
        return this.zzc;
    }

    public final K getMaxKey() {
        return this.zza.length > 0 ? this.zza[this.zza.length - 1] : null;
    }

    public final K getMinKey() {
        return this.zza.length > 0 ? this.zza[0] : null;
    }

    public final K getPredecessorKey(K k) {
        int zzb = zzb((Object) k);
        if (zzb != -1) {
            return zzb > 0 ? this.zza[zzb - 1] : null;
        } else {
            throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
        }
    }

    public final K getSuccessorKey(K k) {
        int zzb = zzb((Object) k);
        if (zzb != -1) {
            return zzb < this.zza.length + -1 ? this.zza[zzb + 1] : null;
        } else {
            throw new IllegalArgumentException("Can't find successor of nonexistent key");
        }
    }

    public final void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        for (int i = 0; i < this.zza.length; i++) {
            nodeVisitor.visitEntry(this.zza[i], this.zzb[i]);
        }
    }

    public final int indexOf(K k) {
        return zzb((Object) k);
    }

    public final ImmutableSortedMap<K, V> insert(K k, V v) {
        int zzb = zzb((Object) k);
        if (zzb != -1) {
            if (this.zza[zzb] == k && this.zzb[zzb] == v) {
                return this;
            }
            return new zza(this.zzc, zzb(this.zza, zzb, k), zzb(this.zzb, zzb, v));
        } else if (this.zza.length > 25) {
            Map hashMap = new HashMap(this.zza.length + 1);
            for (int i = 0; i < this.zza.length; i++) {
                hashMap.put(this.zza[i], this.zzb[i]);
            }
            hashMap.put(k, v);
            return zzc.zzb(hashMap, this.zzc);
        } else {
            zzb = zza((Object) k);
            return new zza(this.zzc, zza(this.zza, zzb, k), zza(this.zzb, zzb, v));
        }
    }

    public final boolean isEmpty() {
        return this.zza.length == 0;
    }

    public final Iterator<Entry<K, V>> iterator() {
        return zza(0, false);
    }

    public final Iterator<Entry<K, V>> iteratorFrom(K k) {
        return zza(zza((Object) k), false);
    }

    public final ImmutableSortedMap<K, V> remove(K k) {
        int zzb = zzb((Object) k);
        if (zzb == -1) {
            return this;
        }
        return new zza(this.zzc, zza(this.zza, zzb), zza(this.zzb, zzb));
    }

    public final Iterator<Entry<K, V>> reverseIterator() {
        return zza(this.zza.length - 1, true);
    }

    public final Iterator<Entry<K, V>> reverseIteratorFrom(K k) {
        int zza = zza((Object) k);
        return (zza >= this.zza.length || this.zzc.compare(this.zza[zza], k) != 0) ? zza(zza - 1, true) : zza(zza, true);
    }

    public final int size() {
        return this.zza.length;
    }
}
