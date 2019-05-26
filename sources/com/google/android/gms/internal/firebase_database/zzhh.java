package com.google.android.gms.internal.firebase_database;

public final class zzhh {
    private final zzch zzap;
    private final zzhe zzat;

    public zzhh(zzch zzch, zzhe zzhe) {
        this.zzap = zzch;
        this.zzat = zzhe;
    }

    public static zzhh zzal(zzch zzch) {
        return new zzhh(zzch, zzhe.zzph);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                zzhh zzhh = (zzhh) obj;
                return this.zzap.equals(zzhh.zzap) && this.zzat.equals(zzhh.zzat);
            }
        }
        return false;
    }

    public final int hashCode() {
        return (this.zzap.hashCode() * 31) + this.zzat.hashCode();
    }

    public final boolean isDefault() {
        return this.zzat.isDefault();
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzap);
        String valueOf2 = String.valueOf(this.zzat);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length());
        stringBuilder.append(valueOf);
        stringBuilder.append(":");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }

    public final zzis zzeg() {
        return this.zzat.zzeg();
    }

    public final boolean zzek() {
        return this.zzat.zzek();
    }

    public final zzhe zzen() {
        return this.zzat;
    }

    public final zzch zzg() {
        return this.zzap;
    }
}
