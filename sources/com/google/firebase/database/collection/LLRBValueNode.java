package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.database.collection.LLRBNode.Color;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.collection.LLRBNode.ShortCircuitingNodeVisitor;
import java.util.Comparator;

@KeepForSdk
public abstract class LLRBValueNode<K, V> implements LLRBNode<K, V> {
    private final V value;
    private final K zzq;
    private LLRBNode<K, V> zzr;
    private final LLRBNode<K, V> zzs;

    LLRBValueNode(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        LLRBNode instance;
        LLRBNode instance2;
        this.zzq = k;
        this.value = v;
        if (lLRBNode == null) {
            instance = LLRBEmptyNode.getInstance();
        }
        this.zzr = instance;
        if (lLRBNode2 == null) {
            instance2 = LLRBEmptyNode.getInstance();
        }
        this.zzs = instance2;
    }

    private static Color zzb(LLRBNode lLRBNode) {
        return lLRBNode.isRed() ? Color.BLACK : Color.RED;
    }

    private final LLRBNode<K, V> zzb() {
        if (this.zzr.isEmpty()) {
            return LLRBEmptyNode.getInstance();
        }
        LLRBValueNode zzc = (getLeft().isRed() || getLeft().getLeft().isRed()) ? this : zzc();
        return zzc.zza(null, null, ((LLRBValueNode) zzc.zzr).zzb(), null).zzd();
    }

    private final LLRBValueNode<K, V> zzc() {
        LLRBValueNode<K, V> zzg = zzg();
        return zzg.getRight().getLeft().isRed() ? zzg.zza(null, null, null, ((LLRBValueNode) zzg.getRight()).zzf()).zze().zzg() : zzg;
    }

    private final LLRBValueNode<K, V> zzd() {
        LLRBValueNode<K, V> zze = (!this.zzs.isRed() || this.zzr.isRed()) ? this : zze();
        if (zze.zzr.isRed() && ((LLRBValueNode) zze.zzr).zzr.isRed()) {
            zze = zze.zzf();
        }
        return (zze.zzr.isRed() && zze.zzs.isRed()) ? zze.zzg() : zze;
    }

    private final LLRBValueNode<K, V> zze() {
        return (LLRBValueNode) this.zzs.copy(null, null, zza(), (LLRBValueNode) copy(null, null, Color.RED, null, ((LLRBValueNode) this.zzs).zzr), null);
    }

    private final LLRBValueNode<K, V> zzf() {
        return (LLRBValueNode) this.zzr.copy(null, null, zza(), null, (LLRBValueNode) copy(null, null, Color.RED, ((LLRBValueNode) this.zzr).zzs, null));
    }

    private final LLRBValueNode<K, V> zzg() {
        return (LLRBValueNode) copy(null, null, zzb(this), this.zzr.copy(null, null, zzb(this.zzr), null, null), this.zzs.copy(null, null, zzb(this.zzs), null, null));
    }

    @KeepForSdk
    public LLRBValueNode<K, V> copy(K k, V v, Color color, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2) {
        Object obj;
        Object obj2;
        LLRBNode lLRBNode3;
        LLRBNode lLRBNode4;
        if (k == null) {
            obj = this.zzq;
        }
        if (v == null) {
            obj2 = this.value;
        }
        if (lLRBNode == null) {
            lLRBNode3 = this.zzr;
        }
        if (lLRBNode2 == null) {
            lLRBNode4 = this.zzs;
        }
        return color == Color.RED ? new LLRBRedValueNode(obj, obj2, lLRBNode3, lLRBNode4) : new LLRBBlackValueNode(obj, obj2, lLRBNode3, lLRBNode4);
    }

    @KeepForSdk
    public K getKey() {
        return this.zzq;
    }

    @KeepForSdk
    public LLRBNode<K, V> getLeft() {
        return this.zzr;
    }

    @KeepForSdk
    public LLRBNode<K, V> getMax() {
        return this.zzs.isEmpty() ? this : this.zzs.getMax();
    }

    @KeepForSdk
    public LLRBNode<K, V> getMin() {
        return this.zzr.isEmpty() ? this : this.zzr.getMin();
    }

    @KeepForSdk
    public LLRBNode<K, V> getRight() {
        return this.zzs;
    }

    @KeepForSdk
    public V getValue() {
        return this.value;
    }

    @KeepForSdk
    public void inOrderTraversal(NodeVisitor<K, V> nodeVisitor) {
        this.zzr.inOrderTraversal(nodeVisitor);
        nodeVisitor.visitEntry(this.zzq, this.value);
        this.zzs.inOrderTraversal(nodeVisitor);
    }

    @KeepForSdk
    public LLRBNode<K, V> insert(K k, V v, Comparator<K> comparator) {
        int compare = comparator.compare(k, this.zzq);
        LLRBValueNode zza = compare < 0 ? zza(null, null, this.zzr.insert(k, v, comparator), null) : compare == 0 ? zza(k, v, null, null) : zza(null, null, null, this.zzs.insert(k, v, comparator));
        return zza.zzd();
    }

    @KeepForSdk
    public boolean isEmpty() {
        return false;
    }

    @KeepForSdk
    public LLRBNode<K, V> remove(K k, Comparator<K> comparator) {
        LLRBValueNode zza;
        LLRBValueNode zzc;
        if (comparator.compare(k, this.zzq) < 0) {
            zzc = (this.zzr.isEmpty() || this.zzr.isRed() || ((LLRBValueNode) this.zzr).zzr.isRed()) ? this : zzc();
            zza = zzc.zza(null, null, zzc.zzr.remove(k, comparator), null);
        } else {
            zzc = this.zzr.isRed() ? zzf() : this;
            if (!(zzc.zzs.isEmpty() || zzc.zzs.isRed() || ((LLRBValueNode) zzc.zzs).zzr.isRed())) {
                zzc = zzc.zzg();
                if (zzc.getLeft().getLeft().isRed()) {
                    zzc = zzc.zzf().zzg();
                }
            }
            if (comparator.compare(k, zzc.zzq) == 0) {
                if (zzc.zzs.isEmpty()) {
                    return LLRBEmptyNode.getInstance();
                }
                LLRBNode min = zzc.zzs.getMin();
                zzc = zzc.zza(min.getKey(), min.getValue(), null, ((LLRBValueNode) zzc.zzs).zzb());
            }
            zza = zzc.zza(null, null, null, zzc.zzs.remove(k, comparator));
        }
        return zza.zzd();
    }

    @KeepForSdk
    public boolean shortCircuitingInOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        return (this.zzr.shortCircuitingInOrderTraversal(shortCircuitingNodeVisitor) && shortCircuitingNodeVisitor.shouldContinue(this.zzq, this.value)) ? this.zzs.shortCircuitingInOrderTraversal(shortCircuitingNodeVisitor) : false;
    }

    @KeepForSdk
    public boolean shortCircuitingReverseOrderTraversal(ShortCircuitingNodeVisitor<K, V> shortCircuitingNodeVisitor) {
        return (this.zzs.shortCircuitingReverseOrderTraversal(shortCircuitingNodeVisitor) && shortCircuitingNodeVisitor.shouldContinue(this.zzq, this.value)) ? this.zzr.shortCircuitingReverseOrderTraversal(shortCircuitingNodeVisitor) : false;
    }

    protected abstract Color zza();

    protected abstract LLRBValueNode<K, V> zza(K k, V v, LLRBNode<K, V> lLRBNode, LLRBNode<K, V> lLRBNode2);

    void zza(LLRBNode<K, V> lLRBNode) {
        this.zzr = lLRBNode;
    }
}
