package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzci implements Iterator<zzid> {
    private int offset = this.zzht.start;
    private final /* synthetic */ zzch zzht;

    zzci(zzch zzch) {
        this.zzht = zzch;
    }

    public final boolean hasNext() {
        return this.offset < this.zzht.end;
    }

    public final /* synthetic */ Object next() {
        if (hasNext()) {
            Object obj = this.zzht.zzhr[this.offset];
            this.offset++;
            return obj;
        }
        throw new NoSuchElementException("No more elements.");
    }

    public final void remove() {
        throw new UnsupportedOperationException("Can't remove component from immutable Path!");
    }
}
