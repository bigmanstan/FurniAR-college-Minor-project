package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;

public interface zzja extends Comparable<zzja>, Iterable<zziz> {
    public static final zzif zzsi = new zzjb();

    int getChildCount();

    Object getValue();

    Object getValue(boolean z);

    boolean isEmpty();

    Iterator<zziz> reverseIterator();

    String zza(zzjc zzjc);

    zzja zzam(zzch zzch);

    zzja zze(zzid zzid, zzja zzja);

    zzja zzf(zzja zzja);

    String zzfj();

    boolean zzfk();

    zzja zzfl();

    boolean zzk(zzid zzid);

    zzid zzl(zzid zzid);

    zzja zzl(zzch zzch, zzja zzja);

    zzja zzm(zzid zzid);
}
