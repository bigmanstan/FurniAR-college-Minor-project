package com.google.android.gms.internal.firebase_database;

public final class zzjf extends zzis {
    private static final zzjf zzsn = new zzjf();

    private zzjf() {
    }

    public static zzjf zzgf() {
        return zzsn;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zziz zziz = (zziz) obj;
        zziz zziz2 = (zziz) obj2;
        zzja zzfl = zziz.zzd().zzfl();
        zzja zzfl2 = zziz2.zzd().zzfl();
        zzid zzge = zziz.zzge();
        zzid zzge2 = zziz2.zzge();
        int compareTo = zzfl.compareTo(zzfl2);
        return compareTo != 0 ? compareTo : zzge.zzi(zzge2);
    }

    public final boolean equals(Object obj) {
        return obj instanceof zzjf;
    }

    public final int hashCode() {
        return 3155577;
    }

    public final String toString() {
        return "PriorityIndex";
    }

    public final zziz zzf(zzid zzid, zzja zzja) {
        return new zziz(zzid, new zzji("[PRIORITY-POST]", zzja));
    }

    public final zziz zzfw() {
        return zzf(zzid.zzfd(), zzja.zzsi);
    }

    public final String zzfx() {
        throw new IllegalArgumentException("Can't get query definition on priority index!");
    }

    public final boolean zzi(zzja zzja) {
        return !zzja.zzfl().isEmpty();
    }
}
