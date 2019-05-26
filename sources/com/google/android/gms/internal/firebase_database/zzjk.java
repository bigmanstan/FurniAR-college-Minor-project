package com.google.android.gms.internal.firebase_database;

public final class zzjk extends zzis {
    private static final zzjk zzsr = new zzjk();

    private zzjk() {
    }

    public static zzjk zzgg() {
        return zzsr;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zziz zziz = (zziz) obj;
        zziz zziz2 = (zziz) obj2;
        int compareTo = zziz.zzd().compareTo(zziz2.zzd());
        return compareTo == 0 ? zziz.zzge().zzi(zziz2.zzge()) : compareTo;
    }

    public final boolean equals(Object obj) {
        return obj instanceof zzjk;
    }

    public final int hashCode() {
        return 4;
    }

    public final String toString() {
        return "ValueIndex";
    }

    public final zziz zzf(zzid zzid, zzja zzja) {
        return new zziz(zzid, zzja);
    }

    public final zziz zzfw() {
        return new zziz(zzid.zzfd(), zzja.zzsi);
    }

    public final String zzfx() {
        return ".value";
    }

    public final boolean zzi(zzja zzja) {
        return true;
    }
}
