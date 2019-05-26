package com.google.firebase.database.collection;

import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.LLRBNode.Color;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class zzc<K, V> extends ImmutableSortedMap<K, V> {
    private Comparator<K> zzc;
    private LLRBNode<K, V> zzt;

    private zzc(LLRBNode<K, V> lLRBNode, Comparator<K> comparator) {
        this.zzt = lLRBNode;
        this.zzc = comparator;
    }

    public static <A, B> zzc<A, B> zzb(Map<A, B> map, Comparator<A> comparator) {
        return zze.zzb(new ArrayList(map.keySet()), map, Builder.identityTranslator(), comparator);
    }

    private final LLRBNode<K, V> zzc(K k) {
        LLRBNode<K, V> lLRBNode = this.zzt;
        while (!lLRBNode.isEmpty()) {
            int compare = this.zzc.compare(k, lLRBNode.getKey());
            if (compare < 0) {
                lLRBNode = lLRBNode.getLeft();
            } else if (compare == 0) {
                return lLRBNode;
            } else {
                lLRBNode = lLRBNode.getRight();
            }
        }
        return null;
    }

    public final boolean containsKey(K k) {
        return zzc(k) != null;
    }

    public final V get(K k) {
        LLRBNode zzc = zzc(k);
        return zzc != null ? zzc.getValue() : null;
    }

    public final Comparator<K> getComparator() {
        return this.zzc;
    }

    public final K getMaxKey() {
        return this.zzt.getMax().getKey();
    }

    public final K getMinKey() {
        return this.zzt.getMin().getKey();
    }

    public final K getPredecessorKey(K k) {
        LLRBNode lLRBNode = this.zzt;
        LLRBNode lLRBNode2 = null;
        while (!lLRBNode.isEmpty()) {
            int compare = this.zzc.compare(k, lLRBNode.getKey());
            if (compare == 0) {
                if (lLRBNode.getLeft().isEmpty()) {
                    return lLRBNode2 != null ? lLRBNode2.getKey() : null;
                } else {
                    LLRBNode left = lLRBNode.getLeft();
                    while (!left.getRight().isEmpty()) {
                        left = left.getRight();
                    }
                    return left.getKey();
                }
            } else if (compare < 0) {
                lLRBNode = lLRBNode.getLeft();
            } else {
                lLRBNode2 = lLRBNode;
                lLRBNode = lLRBNode.getRight();
            }
        }
        String valueOf = String.valueOf(k);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 50);
        stringBuilder.append("Couldn't find predecessor key of non-present key: ");
        stringBuilder.append(valueOf);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final K getSuccessorKey(K k) {
        LLRBNode lLRBNode = this.zzt;
        LLRBNode lLRBNode2 = null;
        while (!lLRBNode.isEmpty()) {
            int compare = this.zzc.compare(lLRBNode.getKey(), k);
            if (compare == 0) {
                if (lLRBNode.getRight().isEmpty()) {
                    return lLRBNode2 != null ? lLRBNode2.getKey() : null;
                } else {
                    LLRBNode right = lLRBNode.getRight();
                    while (!right.getLeft().isEmpty()) {
                        right = right.getLeft();
                    }
                    return right.getKey();
                }
            } else if (compare < 0) {
                lLRBNode = lLRBNode.getRight();
            } else {
                lLRBNode2 = lLRBNode;
                lLRBNode = lLRBNode.getLeft();
            }
        }
        String valueOf = String.valueOf(k);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 48);
        stringBuilder.append("Couldn't find successor key of non-present key: ");
        stringBuilder.append(valueOf);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        this.zzt.inOrderTraversal(nodeVisitor);
    }

    public final int indexOf(K k) {
        LLRBNode lLRBNode = this.zzt;
        int i = 0;
        while (!lLRBNode.isEmpty()) {
            int compare = this.zzc.compare(k, lLRBNode.getKey());
            if (compare == 0) {
                return i + lLRBNode.getLeft().size();
            }
            if (compare < 0) {
                lLRBNode = lLRBNode.getLeft();
            } else {
                i += lLRBNode.getLeft().size() + 1;
                lLRBNode = lLRBNode.getRight();
            }
        }
        return -1;
    }

    public final ImmutableSortedMap<K, V> insert(K k, V v) {
        return new zzc(this.zzt.insert(k, v, this.zzc).copy(null, null, Color.BLACK, null, null), this.zzc);
    }

    public final boolean isEmpty() {
        return this.zzt.isEmpty();
    }

    public final Iterator<Entry<K, V>> iterator() {
        return new ImmutableSortedMapIterator(this.zzt, null, this.zzc, false);
    }

    public final Iterator<Entry<K, V>> iteratorFrom(K k) {
        return new ImmutableSortedMapIterator(this.zzt, k, this.zzc, false);
    }

    public final ImmutableSortedMap<K, V> remove(K k) {
        return !containsKey(k) ? this : new zzc(this.zzt.remove(k, this.zzc).copy(null, null, Color.BLACK, null, null), this.zzc);
    }

    public final Iterator<Entry<K, V>> reverseIterator() {
        return new ImmutableSortedMapIterator(this.zzt, null, this.zzc, true);
    }

    public final Iterator<Entry<K, V>> reverseIteratorFrom(K k) {
        return new ImmutableSortedMapIterator(this.zzt, k, this.zzc, true);
    }

    public final int size() {
        return this.zzt.size();
    }
}
