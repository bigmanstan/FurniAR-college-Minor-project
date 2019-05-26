package com.google.android.gms.internal.firebase_database;

public final class zzfx {
    private static final zzgn<Boolean> zznk = new zzfy();
    private static final zzgn<Boolean> zznl = new zzfz();
    private static final zzgj<Boolean> zznm = new zzgj(Boolean.valueOf(true));
    private static final zzgj<Boolean> zznn = new zzgj(Boolean.valueOf(false));
    private final zzgj<Boolean> zznj;

    public zzfx() {
        this.zznj = zzgj.zzdl();
    }

    private zzfx(zzgj<Boolean> zzgj) {
        this.zznj = zzgj;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfx)) {
            return false;
        }
        return this.zznj.equals(((zzfx) obj).zznj);
    }

    public final int hashCode() {
        return this.zznj.hashCode();
    }

    public final String toString() {
        String zzgj = this.zznj.toString();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzgj).length() + 14);
        stringBuilder.append("{PruneForest:");
        stringBuilder.append(zzgj);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final <T> T zza(T t, zzgm<Void, T> zzgm) {
        return this.zznj.zzb((Object) t, new zzga(this, zzgm));
    }

    public final zzfx zzd(zzid zzid) {
        zzgj zze = this.zznj.zze(zzid);
        if (zze == null) {
            zze = new zzgj((Boolean) this.zznj.getValue());
        } else if (zze.getValue() == null && this.zznj.getValue() != null) {
            zze = zze.zzb(zzch.zzbt(), (Boolean) this.zznj.getValue());
        }
        return new zzfx(zze);
    }

    public final boolean zzdh() {
        return this.zznj.zzb(zznl);
    }

    public final boolean zzv(zzch zzch) {
        Boolean bool = (Boolean) this.zznj.zzaf(zzch);
        return bool != null && bool.booleanValue();
    }

    public final boolean zzw(zzch zzch) {
        Boolean bool = (Boolean) this.zznj.zzaf(zzch);
        return (bool == null || bool.booleanValue()) ? false : true;
    }

    public final zzfx zzx(zzch zzch) {
        if (this.zznj.zzb(zzch, zznk) == null) {
            return this.zznj.zzb(zzch, zznl) != null ? this : new zzfx(this.zznj.zza(zzch, zznm));
        } else {
            throw new IllegalArgumentException("Can't prune path that was kept previously!");
        }
    }

    public final zzfx zzy(zzch zzch) {
        return this.zznj.zzb(zzch, zznk) != null ? this : new zzfx(this.zznj.zza(zzch, zznn));
    }
}
