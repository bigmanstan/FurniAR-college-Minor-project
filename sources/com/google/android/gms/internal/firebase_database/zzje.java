package com.google.android.gms.internal.firebase_database;

public final class zzje extends zzis {
    private final zzch zzsm;

    public zzje(zzch zzch) {
        if (zzch.size() == 1) {
            if (zzch.zzbw().zzfh()) {
                throw new IllegalArgumentException("Can't create PathIndex with '.priority' as key. Please use PriorityIndex instead!");
            }
        }
        this.zzsm = zzch;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zziz zziz = (zziz) obj;
        zziz zziz2 = (zziz) obj2;
        int compareTo = zziz.zzd().zzam(this.zzsm).compareTo(zziz2.zzd().zzam(this.zzsm));
        return compareTo == 0 ? zziz.zzge().zzi(zziz2.zzge()) : compareTo;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                return this.zzsm.equals(((zzje) obj).zzsm);
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zzsm.hashCode();
    }

    public final zziz zzf(zzid zzid, zzja zzja) {
        return new zziz(zzid, zzir.zzfv().zzl(this.zzsm, zzja));
    }

    public final zziz zzfw() {
        return new zziz(zzid.zzfd(), zzir.zzfv().zzl(this.zzsm, zzja.zzsi));
    }

    public final String zzfx() {
        return this.zzsm.zzbu();
    }

    public final boolean zzi(zzja zzja) {
        return !zzja.zzam(this.zzsm).isEmpty();
    }
}
