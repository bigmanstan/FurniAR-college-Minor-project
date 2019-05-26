package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.Iterator;

public final class zzir extends zzif implements zzja {
    private static final zzir zzrv = new zzir();

    private zzir() {
    }

    public static zzir zzfv() {
        return zzrv;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return zzg((zzja) obj);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzir) {
            return true;
        }
        if (obj instanceof zzja) {
            zzja zzja = (zzja) obj;
            if (zzja.isEmpty() && equals(zzja.zzfl())) {
                return true;
            }
        }
        return false;
    }

    public final int getChildCount() {
        return 0;
    }

    public final Object getValue() {
        return null;
    }

    public final Object getValue(boolean z) {
        return null;
    }

    public final int hashCode() {
        return 0;
    }

    public final boolean isEmpty() {
        return true;
    }

    public final Iterator<zziz> iterator() {
        return Collections.emptyList().iterator();
    }

    public final Iterator<zziz> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    public final String toString() {
        return "<Empty Node>";
    }

    public final String zza(zzjc zzjc) {
        return "";
    }

    public final zzja zzam(zzch zzch) {
        return this;
    }

    public final zzja zze(zzid zzid, zzja zzja) {
        return (zzja.isEmpty() || zzid.zzfh()) ? this : new zzif().zze(zzid, zzja);
    }

    public final /* bridge */ /* synthetic */ zzja zzf(zzja zzja) {
        return this;
    }

    public final String zzfj() {
        return "";
    }

    public final boolean zzfk() {
        return false;
    }

    public final zzja zzfl() {
        return this;
    }

    public final int zzg(zzja zzja) {
        return zzja.isEmpty() ? 0 : -1;
    }

    public final boolean zzk(zzid zzid) {
        return false;
    }

    public final zzid zzl(zzid zzid) {
        return null;
    }

    public final zzja zzl(zzch zzch, zzja zzja) {
        return zzch.isEmpty() ? zzja : zze(zzch.zzbw(), zzl(zzch.zzbx(), zzja));
    }

    public final zzja zzm(zzid zzid) {
        return this;
    }
}
