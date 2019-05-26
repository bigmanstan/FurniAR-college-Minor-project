package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Comparator;

@KeepForSdk
public interface LLRBNode<K, V> {

    @KeepForSdk
    public enum Color {
        RED,
        BLACK
    }

    @KeepForSdk
    public interface ShortCircuitingNodeVisitor<K, V> {
        boolean shouldContinue(K k, V v);
    }

    @KeepForSdk
    public static abstract class NodeVisitor<K, V> implements ShortCircuitingNodeVisitor<K, V> {
        @KeepForSdk
        public boolean shouldContinue(K k, V v) {
            visitEntry(k, v);
            return true;
        }

        @KeepForSdk
        public abstract void visitEntry(K k, V v);
    }

    @KeepForSdk
    LLRBNode<K, V> copy(K k, V v, Color color, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2);

    @KeepForSdk
    K getKey();

    @KeepForSdk
    LLRBNode<K, V> getLeft();

    @KeepForSdk
    LLRBNode<K, V> getMax();

    @KeepForSdk
    LLRBNode<K, V> getMin();

    @KeepForSdk
    LLRBNode<K, V> getRight();

    @KeepForSdk
    V getValue();

    @KeepForSdk
    void inOrderTraversal(NodeVisitor<K, V> nodeVisitor);

    @KeepForSdk
    LLRBNode<K, V> insert(K k, V v, Comparator<K> comparator);

    @KeepForSdk
    boolean isEmpty();

    @KeepForSdk
    boolean isRed();

    @KeepForSdk
    LLRBNode<K, V> remove(K k, Comparator<K> comparator);

    @KeepForSdk
    boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor);

    @KeepForSdk
    boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor);

    @KeepForSdk
    int size();
}
