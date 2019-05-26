package com.google.android.gms.internal.firebase_database;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;

final class zzgl implements zzgm<T, Void> {
    private final /* synthetic */ List val$list;

    zzgl(zzgj zzgj, List list) {
        this.val$list = list;
    }

    public final /* synthetic */ Object zza(zzch zzch, Object obj, Object obj2) {
        this.val$list.add(new SimpleImmutableEntry(zzch, obj));
        return null;
    }
}
