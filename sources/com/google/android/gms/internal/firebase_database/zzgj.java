package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.StandardComparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class zzgj<T> implements Iterable<Entry<zzch, T>> {
    private static final ImmutableSortedMap zzoc = Builder.emptyMap(StandardComparator.getComparator(zzid.class));
    private static final zzgj zzod = new zzgj(null, zzoc);
    private final T value;
    private final ImmutableSortedMap<zzid, zzgj<T>> zzob;

    public zzgj(T t) {
        this(t, zzoc);
    }

    private zzgj(T t, ImmutableSortedMap<zzid, zzgj<T>> immutableSortedMap) {
        this.value = t;
        this.zzob = immutableSortedMap;
    }

    private final <R> R zza(zzch zzch, zzgm<? super T, R> zzgm, R r) {
        Iterator it = this.zzob.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            r = ((zzgj) entry.getValue()).zza(zzch.zza((zzid) entry.getKey()), zzgm, r);
        }
        return this.value != null ? zzgm.zza(zzch, this.value, r) : r;
    }

    public static <V> zzgj<V> zzdl() {
        return zzod;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
        r4 = this;
        r0 = 1;
        if (r4 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 0;
        if (r5 == 0) goto L_0x003d;
    L_0x0007:
        r2 = r4.getClass();
        r3 = r5.getClass();
        if (r2 == r3) goto L_0x0012;
    L_0x0011:
        goto L_0x003d;
    L_0x0012:
        r5 = (com.google.android.gms.internal.firebase_database.zzgj) r5;
        r2 = r4.zzob;
        if (r2 == 0) goto L_0x0023;
    L_0x0018:
        r2 = r4.zzob;
        r3 = r5.zzob;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0028;
    L_0x0022:
        goto L_0x0027;
    L_0x0023:
        r2 = r5.zzob;
        if (r2 == 0) goto L_0x0028;
    L_0x0027:
        return r1;
    L_0x0028:
        r2 = r4.value;
        if (r2 == 0) goto L_0x0037;
    L_0x002c:
        r2 = r4.value;
        r5 = r5.value;
        r5 = r2.equals(r5);
        if (r5 != 0) goto L_0x003c;
    L_0x0036:
        goto L_0x003b;
    L_0x0037:
        r5 = r5.value;
        if (r5 == 0) goto L_0x003c;
    L_0x003b:
        return r1;
    L_0x003c:
        return r0;
    L_0x003d:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzgj.equals(java.lang.Object):boolean");
    }

    public final T getValue() {
        return this.value;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.value != null ? this.value.hashCode() : 0) * 31;
        if (this.zzob != null) {
            i = this.zzob.hashCode();
        }
        return hashCode + i;
    }

    public final boolean isEmpty() {
        return this.value == null && this.zzob.isEmpty();
    }

    public final Iterator<Entry<zzch, T>> iterator() {
        List arrayList = new ArrayList();
        zza(new zzgl(this, arrayList));
        return arrayList.iterator();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImmutableTree { value=");
        stringBuilder.append(this.value);
        stringBuilder.append(", children={");
        Iterator it = this.zzob.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            stringBuilder.append(((zzid) entry.getKey()).zzfg());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
        }
        stringBuilder.append("} }");
        return stringBuilder.toString();
    }

    public final Collection<T> values() {
        Collection arrayList = new ArrayList();
        zza(new zzgk(this, arrayList));
        return arrayList;
    }

    public final zzch zza(zzch zzch, zzgn<? super T> zzgn) {
        if (this.value != null && zzgn.zzd(this.value)) {
            return zzch.zzbt();
        }
        if (zzch.isEmpty()) {
            return null;
        }
        zzgj zzgj = (zzgj) this.zzob.get(zzch.zzbw());
        if (zzgj != null) {
            zzch = zzgj.zza(zzch.zzbx(), (zzgn) zzgn);
            if (zzch != null) {
                return new zzch(r0).zzh(zzch);
            }
        }
        return null;
    }

    public final zzgj<T> zza(zzch zzch, zzgj<T> zzgj) {
        if (zzch.isEmpty()) {
            return zzgj;
        }
        zzid zzbw = zzch.zzbw();
        zzgj zzgj2 = (zzgj) this.zzob.get(zzbw);
        if (zzgj2 == null) {
            zzgj2 = zzod;
        }
        zzgj zza = zzgj2.zza(zzch.zzbx(), (zzgj) zzgj);
        return new zzgj(this.value, zza.isEmpty() ? this.zzob.remove(zzbw) : this.zzob.insert(zzbw, zza));
    }

    public final void zza(zzgm<T, Void> zzgm) {
        zza(zzch.zzbt(), zzgm, null);
    }

    public final zzch zzae(zzch zzch) {
        return zza(zzch, zzgn.zzof);
    }

    public final T zzaf(zzch zzch) {
        zzgn zzgn = zzgn.zzof;
        T t = (this.value == null || !zzgn.zzd(this.value)) ? null : this.value;
        Iterator it = zzch.iterator();
        T t2 = t;
        zzgj zzgj = this;
        while (it.hasNext()) {
            zzgj = (zzgj) zzgj.zzob.get((zzid) it.next());
            if (zzgj == null) {
                break;
            } else if (zzgj.value != null && zzgn.zzd(zzgj.value)) {
                t2 = zzgj.value;
            }
        }
        return t2;
    }

    public final zzgj<T> zzag(zzch zzch) {
        zzgj<T> zzgj = this;
        while (!zzch.isEmpty()) {
            zzgj = (zzgj) zzgj.zzob.get(zzch.zzbw());
            if (zzgj == null) {
                return zzod;
            }
            zzch = zzch.zzbx();
        }
        return zzgj;
    }

    public final zzgj<T> zzah(zzch zzch) {
        if (zzch.isEmpty()) {
            return this.zzob.isEmpty() ? zzod : new zzgj(null, this.zzob);
        } else {
            zzid zzbw = zzch.zzbw();
            zzgj zzgj = (zzgj) this.zzob.get(zzbw);
            if (zzgj == null) {
                return this;
            }
            zzgj zzah = zzgj.zzah(zzch.zzbx());
            ImmutableSortedMap remove = zzah.isEmpty() ? this.zzob.remove(zzbw) : this.zzob.insert(zzbw, zzah);
            return (this.value == null && remove.isEmpty()) ? zzod : new zzgj(this.value, remove);
        }
    }

    public final T zzai(zzch zzch) {
        zzgj zzgj = this;
        while (!zzch.isEmpty()) {
            zzgj = (zzgj) zzgj.zzob.get(zzch.zzbw());
            if (zzgj == null) {
                return null;
            }
            zzch = zzch.zzbx();
        }
        return zzgj.value;
    }

    public final zzgj<T> zzb(zzch zzch, T t) {
        if (zzch.isEmpty()) {
            return new zzgj(t, this.zzob);
        }
        zzid zzbw = zzch.zzbw();
        zzgj zzgj = (zzgj) this.zzob.get(zzbw);
        if (zzgj == null) {
            zzgj = zzod;
        }
        return new zzgj(this.value, this.zzob.insert(zzbw, zzgj.zzb(zzch.zzbx(), (Object) t)));
    }

    public final T zzb(zzch zzch, zzgn<? super T> zzgn) {
        if (this.value != null && zzgn.zzd(this.value)) {
            return this.value;
        }
        Iterator it = zzch.iterator();
        zzgj zzgj = this;
        while (it.hasNext()) {
            zzgj = (zzgj) zzgj.zzob.get((zzid) it.next());
            if (zzgj == null) {
                return null;
            }
            if (zzgj.value != null && zzgn.zzd(zzgj.value)) {
                return zzgj.value;
            }
        }
        return null;
    }

    public final <R> R zzb(R r, zzgm<? super T, R> zzgm) {
        return zza(zzch.zzbt(), zzgm, r);
    }

    public final boolean zzb(zzgn<? super T> zzgn) {
        if (this.value != null && zzgn.zzd(this.value)) {
            return true;
        }
        Iterator it = this.zzob.iterator();
        while (it.hasNext()) {
            if (((zzgj) ((Entry) it.next()).getValue()).zzb(zzgn)) {
                return true;
            }
        }
        return false;
    }

    public final ImmutableSortedMap<zzid, zzgj<T>> zzdm() {
        return this.zzob;
    }

    public final zzgj<T> zze(zzid zzid) {
        zzgj<T> zzgj = (zzgj) this.zzob.get(zzid);
        return zzgj != null ? zzgj : zzod;
    }
}
