package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;

public final class zzy {
    private final List<List<String>> zzbz;
    private final List<String> zzca;

    public zzy(List<List<String>> list, List<String> list2) {
        if (list.size() == list2.size() - 1) {
            this.zzbz = list;
            this.zzca = list2;
            return;
        }
        throw new IllegalArgumentException("Number of posts need to be n-1 for n hashes in CompoundHash");
    }

    public final List<List<String>> zzo() {
        return Collections.unmodifiableList(this.zzbz);
    }

    public final List<String> zzp() {
        return Collections.unmodifiableList(this.zzca);
    }
}
