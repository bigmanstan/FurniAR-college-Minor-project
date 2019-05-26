package com.google.firebase.database.collection;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzb implements Iterator<Entry<K, V>> {
    private int zzd = this.zze;
    private final /* synthetic */ int zze;
    private final /* synthetic */ boolean zzf;
    private final /* synthetic */ zza zzg;

    zzb(zza zza, int i, boolean z) {
        this.zzg = zza;
        this.zze = i;
        this.zzf = z;
    }

    public final boolean hasNext() {
        return this.zzf ? this.zzd >= 0 : this.zzd < this.zzg.zza.length;
    }

    public final /* synthetic */ Object next() {
        Object obj = this.zzg.zza[this.zzd];
        Object obj2 = this.zzg.zzb[this.zzd];
        this.zzd = this.zzf ? this.zzd - 1 : this.zzd + 1;
        return new SimpleImmutableEntry(obj, obj2);
    }

    public final void remove() {
        throw new UnsupportedOperationException("Can't remove elements from ImmutableSortedMap");
    }
}
