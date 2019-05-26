package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.database.collection.LLRBNode.Color;

@KeepForSdk
public class LLRBBlackValueNode<K, V> extends LLRBValueNode<K, V> {
    private int size = -1;

    LLRBBlackValueNode(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        super(k, v, lLRBNode, lLRBNode2);
    }

    @KeepForSdk
    public boolean isRed() {
        return false;
    }

    @KeepForSdk
    public int size() {
        if (this.size == -1) {
            this.size = (getLeft().size() + 1) + getRight().size();
        }
        return this.size;
    }

    protected final Color zza() {
        return Color.BLACK;
    }

    protected final LLRBValueNode<K, V> zza(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        Object key;
        Object value;
        LLRBNode left;
        LLRBNode right;
        if (k == null) {
            key = getKey();
        }
        if (v == null) {
            value = getValue();
        }
        if (lLRBNode == null) {
            left = getLeft();
        }
        if (lLRBNode2 == null) {
            right = getRight();
        }
        return new LLRBBlackValueNode(key, value, left, right);
    }

    final void zza(LLRBNode<K, V> lLRBNode) {
        if (this.size == -1) {
            super.zza(lLRBNode);
            return;
        }
        throw new IllegalStateException("Can't set left after using size");
    }
}
