package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.database.collection.LLRBNode.Color;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.collection.LLRBNode.ShortCircuitingNodeVisitor;
import java.util.Comparator;

@KeepForSdk
public class LLRBEmptyNode<K, V> implements LLRBNode<K, V> {
    private static final LLRBEmptyNode zzm = new LLRBEmptyNode();

    private LLRBEmptyNode() {
    }

    @KeepForSdk
    public static <K, V> LLRBEmptyNode<K, V> getInstance() {
        return zzm;
    }

    @KeepForSdk
    public LLRBNode<K, V> copy(K k, V v, Color color, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        return this;
    }

    @KeepForSdk
    public K getKey() {
        return null;
    }

    @KeepForSdk
    public LLRBNode<K, V> getLeft() {
        return this;
    }

    @KeepForSdk
    public LLRBNode<K, V> getMax() {
        return this;
    }

    @KeepForSdk
    public LLRBNode<K, V> getMin() {
        return this;
    }

    @KeepForSdk
    public LLRBNode<K, V> getRight() {
        return this;
    }

    @KeepForSdk
    public V getValue() {
        return null;
    }

    @KeepForSdk
    public void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
    }

    @KeepForSdk
    public LLRBNode<K, V> insert(K k, V v, Comparator<K> comparator) {
        return new LLRBRedValueNode(k, v);
    }

    @KeepForSdk
    public boolean isEmpty() {
        return true;
    }

    @KeepForSdk
    public boolean isRed() {
        return false;
    }

    @KeepForSdk
    public LLRBNode<K, V> remove(K k, Comparator<K> comparator) {
        return this;
    }

    @KeepForSdk
    public boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        return true;
    }

    @KeepForSdk
    public boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        return true;
    }

    @KeepForSdk
    public int size() {
        return 0;
    }
}
