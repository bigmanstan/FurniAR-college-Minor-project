package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzij implements Iterator<zziz> {
    private final Iterator<Entry<zzid, zzja>> zzri;

    public zzij(Iterator<Entry<zzid, zzja>> it) {
        this.zzri = it;
    }

    public final boolean hasNext() {
        return this.zzri.hasNext();
    }

    public final /* synthetic */ Object next() {
        Entry entry = (Entry) this.zzri.next();
        return new zziz((zzid) entry.getKey(), (zzja) entry.getValue());
    }

    public final void remove() {
        this.zzri.remove();
    }
}
