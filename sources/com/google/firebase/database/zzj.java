package com.google.firebase.database;

import android.support.annotation.NonNull;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzj implements Iterator<MutableData> {
    zzj(zzi zzi) {
    }

    public final boolean hasNext() {
        return false;
    }

    @NonNull
    public final /* synthetic */ Object next() {
        throw new NoSuchElementException();
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
