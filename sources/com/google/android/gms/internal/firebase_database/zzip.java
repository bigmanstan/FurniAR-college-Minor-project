package com.google.android.gms.internal.firebase_database;

import java.util.Map;

public final class zzip extends zziv<zzip> {
    private Map<Object, Object> zzrt;

    public zzip(Map<Object, Object> map, zzja zzja) {
        super(zzja);
        this.zzrt = map;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzip)) {
            return false;
        }
        zzip zzip = (zzip) obj;
        return this.zzrt.equals(zzip.zzrt) && this.zzrd.equals(zzip.zzrd);
    }

    public final Object getValue() {
        return this.zzrt;
    }

    public final int hashCode() {
        return this.zzrt.hashCode() + this.zzrd.hashCode();
    }

    public final String zza(zzjc zzjc) {
        String zzb = zzb(zzjc);
        String valueOf = String.valueOf(this.zzrt);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 14) + String.valueOf(valueOf).length());
        stringBuilder.append(zzb);
        stringBuilder.append("deferredValue:");
        stringBuilder.append(valueOf);
        return stringBuilder.toString();
    }

    public final /* synthetic */ zzja zzf(zzja zzja) {
        return new zzip(this.zzrt, zzja);
    }

    protected final zzix zzfb() {
        return zzix.DeferredValue;
    }
}
