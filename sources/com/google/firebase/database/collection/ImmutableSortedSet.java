package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@KeepForSdk
public class ImmutableSortedSet<T> implements Iterable<T> {
    private final ImmutableSortedMap<T, Void> zzk;

    private static class WrappedEntryIterator<T> implements Iterator<T> {
        private final Iterator<Entry<T, Void>> zzl;

        @KeepForSdk
        public WrappedEntryIterator(Iterator<Entry<T, Void>> it) {
            this.zzl = it;
        }

        @KeepForSdk
        public boolean hasNext() {
            return this.zzl.hasNext();
        }

        @KeepForSdk
        public T next() {
            return ((Entry) this.zzl.next()).getKey();
        }

        @KeepForSdk
        public void remove() {
            this.zzl.remove();
        }
    }

    private ImmutableSortedSet(ImmutableSortedMap<T, Void> immutableSortedMap) {
        this.zzk = immutableSortedMap;
    }

    @KeepForSdk
    public ImmutableSortedSet(List<T> list, Comparator<T> comparator) {
        this.zzk = Builder.buildFrom(list, Collections.emptyMap(), Builder.identityTranslator(), comparator);
    }

    @KeepForSdk
    public boolean contains(T t) {
        return this.zzk.containsKey(t);
    }

    @KeepForSdk
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImmutableSortedSet)) {
            return false;
        }
        return this.zzk.equals(((ImmutableSortedSet) obj).zzk);
    }

    @KeepForSdk
    public T getMaxEntry() {
        return this.zzk.getMaxKey();
    }

    @KeepForSdk
    public T getMinEntry() {
        return this.zzk.getMinKey();
    }

    @KeepForSdk
    public T getPredecessorEntry(T t) {
        return this.zzk.getPredecessorKey(t);
    }

    @KeepForSdk
    public int hashCode() {
        return this.zzk.hashCode();
    }

    @KeepForSdk
    public int indexOf(T t) {
        return this.zzk.indexOf(t);
    }

    @KeepForSdk
    public ImmutableSortedSet<T> insert(T t) {
        return new ImmutableSortedSet(this.zzk.insert(t, null));
    }

    @KeepForSdk
    public boolean isEmpty() {
        return this.zzk.isEmpty();
    }

    @KeepForSdk
    public Iterator<T> iterator() {
        return new WrappedEntryIterator(this.zzk.iterator());
    }

    @KeepForSdk
    public Iterator<T> iteratorFrom(T t) {
        return new WrappedEntryIterator(this.zzk.iteratorFrom(t));
    }

    @KeepForSdk
    public ImmutableSortedSet<T> remove(T t) {
        ImmutableSortedMap remove = this.zzk.remove(t);
        return remove == this.zzk ? this : new ImmutableSortedSet(remove);
    }

    @KeepForSdk
    public Iterator<T> reverseIterator() {
        return new WrappedEntryIterator(this.zzk.reverseIterator());
    }

    @KeepForSdk
    public Iterator<T> reverseIteratorFrom(T t) {
        return new WrappedEntryIterator(this.zzk.reverseIteratorFrom(t));
    }

    @KeepForSdk
    public int size() {
        return this.zzk.size();
    }
}
