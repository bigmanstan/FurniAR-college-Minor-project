package com.google.android.gms.internal.firebase_database;

public final class zziu extends zzis {
    private static final zziu zzrz = new zziu();

    private zziu() {
    }

    public static zziu zzgb() {
        return zzrz;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((zziz) obj).zzge().zzi(((zziz) obj2).zzge());
    }

    public final boolean equals(Object obj) {
        return obj instanceof zziu;
    }

    public final int hashCode() {
        return 37;
    }

    public final String toString() {
        return "KeyIndex";
    }

    public final zziz zzf(zzid zzid, zzja zzja) {
        return new zziz(zzid.zzt((String) zzja.getValue()), zzir.zzfv());
    }

    public final zziz zzfw() {
        return zziz.zzgd();
    }

    public final String zzfx() {
        return ".key";
    }

    public final boolean zzi(zzja zzja) {
        return true;
    }
}
