package com.google.firebase.database.collection;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Comparator;

@KeepForSdk
public class StandardComparator<A extends Comparable<A>> implements Comparator<A> {
    private static final StandardComparator<?> zzac = new StandardComparator();

    private StandardComparator() {
    }

    @KeepForSdk
    public static <T extends Comparable<T>> StandardComparator<T> getComparator(Class<T> cls) {
        return zzac;
    }

    @KeepForSdk
    public int compare(A a, A a2) {
        return a.compareTo(a2);
    }
}
