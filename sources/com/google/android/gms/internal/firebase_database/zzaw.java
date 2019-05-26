package com.google.android.gms.internal.firebase_database;

import java.util.List;
import java.util.Map;

final class zzaw {
    private final List<String> zzei;
    private final Map<String, Object> zzej;

    public zzaw(List<String> list, Map<String, Object> map) {
        this.zzei = list;
        this.zzej = map;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaw)) {
            return false;
        }
        zzaw zzaw = (zzaw) obj;
        return !this.zzei.equals(zzaw.zzei) ? false : this.zzej.equals(zzaw.zzej);
    }

    public final int hashCode() {
        return (this.zzei.hashCode() * 31) + this.zzej.hashCode();
    }

    public final String toString() {
        String zzb = zzag.zzb(this.zzei);
        String valueOf = String.valueOf(this.zzej);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 11) + String.valueOf(valueOf).length());
        stringBuilder.append(zzb);
        stringBuilder.append(" (params: ");
        stringBuilder.append(valueOf);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
