package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@KeepForSdk
public abstract class ImmutableSortedMap<K, V> implements Iterable<Entry<K, V>> {

    @KeepForSdk
    public static class Builder {
        private static final KeyTranslator<?, ?> zzh = new C05591();

        @KeepForSdk
        public interface KeyTranslator<C, D> {
            @KeepForSdk
            D translate(C c);
        }

        /* renamed from: com.google.firebase.database.collection.ImmutableSortedMap$Builder$1 */
        class C05591 implements KeyTranslator {
            C05591() {
            }

            @KeepForSdk
            public Object translate(Object obj) {
                return obj;
            }
        }

        @KeepForSdk
        public static <A, B, C> ImmutableSortedMap<A, C> buildFrom(List<A> list, Map<B, C> map, KeyTranslator<A, B> keyTranslator, Comparator<A> comparator) {
            return list.size() < 25 ? zza.zza(list, map, keyTranslator, comparator) : zze.zzb(list, map, keyTranslator, comparator);
        }

        @KeepForSdk
        public static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<K> comparator) {
            return new zza(comparator);
        }

        @KeepForSdk
        public static <A, B> ImmutableSortedMap<A, B> fromMap(Map<A, B> map, Comparator<A> comparator) {
            return map.size() < 25 ? zza.zza((Map) map, (Comparator) comparator) : zzc.zzb(map, comparator);
        }

        @KeepForSdk
        public static <A> KeyTranslator<A, A> identityTranslator() {
            return zzh;
        }
    }

    @KeepForSdk
    public abstract boolean containsKey(K k);

    @KeepForSdk
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImmutableSortedMap)) {
            return false;
        }
        ImmutableSortedMap immutableSortedMap = (ImmutableSortedMap) obj;
        if (!getComparator().equals(immutableSortedMap.getComparator()) || size() != immutableSortedMap.size()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = immutableSortedMap.iterator();
        while (it.hasNext()) {
            if (!((Entry) it.next()).equals(it2.next())) {
                return false;
            }
        }
        return true;
    }

    @KeepForSdk
    public abstract V get(K k);

    @KeepForSdk
    public abstract Comparator<K> getComparator();

    @KeepForSdk
    public abstract K getMaxKey();

    @KeepForSdk
    public abstract K getMinKey();

    @KeepForSdk
    public abstract K getPredecessorKey(K k);

    @KeepForSdk
    public abstract K getSuccessorKey(K k);

    @KeepForSdk
    public int hashCode() {
        int hashCode = getComparator().hashCode();
        Iterator it = iterator();
        while (it.hasNext()) {
            hashCode = (hashCode * 31) + ((Entry) it.next()).hashCode();
        }
        return hashCode;
    }

    @KeepForSdk
    public abstract void inOrderTraversal(NodeVisitor<K, V> nodeVisitor);

    @KeepForSdk
    public abstract int indexOf(K k);

    @KeepForSdk
    public abstract ImmutableSortedMap<K, V> insert(K k, V v);

    @KeepForSdk
    public abstract boolean isEmpty();

    @KeepForSdk
    public abstract Iterator<Entry<K, V>> iterator();

    @KeepForSdk
    public abstract Iterator<Entry<K, V>> iteratorFrom(K k);

    @KeepForSdk
    public abstract ImmutableSortedMap<K, V> remove(K k);

    @KeepForSdk
    public abstract Iterator<Entry<K, V>> reverseIterator();

    @KeepForSdk
    public abstract Iterator<Entry<K, V>> reverseIteratorFrom(K k);

    @KeepForSdk
    public abstract int size();

    @KeepForSdk
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append("{");
        Iterator it = iterator();
        Object obj = 1;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append("(");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=>");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(")");
        }
        stringBuilder.append("};");
        return stringBuilder.toString();
    }
}
