package com.google.android.gms.internal.firebase_database;

public final class zzgb {
    public final long id;
    public final zzhh zznp;
    public final long zznq;
    public final boolean zznr;
    public final boolean zzns;

    public zzgb(long j, zzhh zzhh, long j2, boolean z, boolean z2) {
        this.id = j;
        if (zzhh.zzek()) {
            if (!zzhh.isDefault()) {
                throw new IllegalArgumentException("Can't create TrackedQuery for a non-default query that loads all data");
            }
        }
        this.zznp = zzhh;
        this.zznq = j2;
        this.zznr = z;
        this.zzns = z2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                zzgb zzgb = (zzgb) obj;
                return this.id == zzgb.id && this.zznp.equals(zzgb.zznp) && this.zznq == zzgb.zznq && this.zznr == zzgb.zznr && this.zzns == zzgb.zzns;
            }
        }
    }

    public final int hashCode() {
        return (((((((Long.valueOf(this.id).hashCode() * 31) + this.zznp.hashCode()) * 31) + Long.valueOf(this.zznq).hashCode()) * 31) + Boolean.valueOf(this.zznr).hashCode()) * 31) + Boolean.valueOf(this.zzns).hashCode();
    }

    public final String toString() {
        long j = this.id;
        String valueOf = String.valueOf(this.zznp);
        long j2 = this.zznq;
        boolean z = this.zznr;
        boolean z2 = this.zzns;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 109);
        stringBuilder.append("TrackedQuery{id=");
        stringBuilder.append(j);
        stringBuilder.append(", querySpec=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", lastUse=");
        stringBuilder.append(j2);
        stringBuilder.append(", complete=");
        stringBuilder.append(z);
        stringBuilder.append(", active=");
        stringBuilder.append(z2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final zzgb zzdi() {
        return new zzgb(this.id, this.zznp, this.zznq, true, this.zzns);
    }
}
