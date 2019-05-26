package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class zziv<T extends zziv> implements zzja {
    protected final zzja zzrd;
    private String zzre;

    zziv(zzja zzja) {
        this.zzrd = zzja;
    }

    private static int zza(zziy zziy, zziq zziq) {
        return Double.valueOf((double) ((Long) zziy.getValue()).longValue()).compareTo((Double) zziq.getValue());
    }

    public /* synthetic */ int compareTo(Object obj) {
        zzja zzja = (zzja) obj;
        if (zzja.isEmpty()) {
            return 1;
        }
        if (zzja instanceof zzif) {
            return -1;
        }
        if ((this instanceof zziy) && (zzja instanceof zziq)) {
            return zza((zziy) this, (zziq) zzja);
        }
        if ((this instanceof zziq) && (zzja instanceof zziy)) {
            return zza((zziy) zzja, (zziq) this) * -1;
        }
        zziv zziv = (zziv) zzja;
        zzix zzfb = zzfb();
        Enum zzfb2 = zziv.zzfb();
        return zzfb.equals(zzfb2) ? zza(zziv) : zzfb.compareTo(zzfb2);
    }

    public final int getChildCount() {
        return 0;
    }

    public final Object getValue(boolean z) {
        if (z) {
            if (!this.zzrd.isEmpty()) {
                Map hashMap = new HashMap();
                hashMap.put(".value", getValue());
                hashMap.put(".priority", this.zzrd.getValue());
                return hashMap;
            }
        }
        return getValue();
    }

    public final boolean isEmpty() {
        return false;
    }

    public Iterator<zziz> iterator() {
        return Collections.emptyList().iterator();
    }

    public final Iterator<zziz> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    public String toString() {
        String obj = getValue(true).toString();
        return obj.length() <= 100 ? obj : String.valueOf(obj.substring(0, 100)).concat("...");
    }

    protected abstract int zza(T t);

    public final zzja zzam(zzch zzch) {
        return zzch.isEmpty() ? this : zzch.zzbw().zzfh() ? this.zzrd : zzir.zzfv();
    }

    protected final String zzb(zzjc zzjc) {
        String zza;
        switch (zziw.zzsa[zzjc.ordinal()]) {
            case 1:
            case 2:
                if (this.zzrd.isEmpty()) {
                    return "";
                }
                zza = this.zzrd.zza(zzjc);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(zza).length() + 10);
                stringBuilder.append("priority:");
                stringBuilder.append(zza);
                stringBuilder.append(":");
                return stringBuilder.toString();
            default:
                zza = String.valueOf(zzjc);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(zza).length() + 22);
                stringBuilder2.append("Unknown hash version: ");
                stringBuilder2.append(zza);
                throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }

    public final zzja zze(zzid zzid, zzja zzja) {
        return zzid.zzfh() ? zzf(zzja) : zzja.isEmpty() ? this : zzir.zzfv().zze(zzid, zzja).zzf(this.zzrd);
    }

    protected abstract zzix zzfb();

    public final String zzfj() {
        if (this.zzre == null) {
            this.zzre = zzkq.zzy(zza(zzjc.V1));
        }
        return this.zzre;
    }

    public final boolean zzfk() {
        return true;
    }

    public final zzja zzfl() {
        return this.zzrd;
    }

    public final boolean zzk(zzid zzid) {
        return false;
    }

    public final zzid zzl(zzid zzid) {
        return null;
    }

    public final zzja zzl(zzch zzch, zzja zzja) {
        zzid zzbw = zzch.zzbw();
        return zzbw == null ? zzja : (!zzja.isEmpty() || zzbw.zzfh()) ? zze(zzbw, zzir.zzfv().zzl(zzch.zzbx(), zzja)) : this;
    }

    public final zzja zzm(zzid zzid) {
        return zzid.zzfh() ? this.zzrd : zzir.zzfv();
    }
}
