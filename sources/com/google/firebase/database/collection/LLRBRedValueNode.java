package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.database.collection.LLRBNode.Color;

@KeepForSdk
public class LLRBRedValueNode<K, V> extends LLRBValueNode<K, V> {
    LLRBRedValueNode(K k, V v) {
        super(k, v, LLRBEmptyNode.getInstance(), LLRBEmptyNode.getInstance());
    }

    LLRBRedValueNode(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        super(k, v, lLRBNode, lLRBNode2);
    }

    @KeepForSdk
    public boolean isRed() {
        return true;
    }

    @KeepForSdk
    public int size() {
        return (getLeft().size() + 1) + getRight().size();
    }

    protected final Color zza() {
        return Color.RED;
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
        return new LLRBRedValueNode(key, value, left, right);
    }
}
