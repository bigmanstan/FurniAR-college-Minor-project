package com.google.android.gms.internal.firebase_database;

public final class zziq extends zziv<zziq> {
    private final Double zzru;

    public zziq(Double d, zzja zzja) {
        super(zzja);
        this.zzru = d;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zziq)) {
            return false;
        }
        zziq zziq = (zziq) obj;
        return this.zzru.equals(zziq.zzru) && this.zzrd.equals(zziq.zzrd);
    }

    public final Object getValue() {
        return this.zzru;
    }

    public final int hashCode() {
        return this.zzru.hashCode() + this.zzrd.hashCode();
    }

    protected final /* synthetic */ int zza(zziv zziv) {
        return this.zzru.compareTo(((zziq) zziv).zzru);
    }

    public final String zza(zzjc zzjc) {
        String valueOf = String.valueOf(String.valueOf(zzb(zzjc)).concat("number:"));
        String valueOf2 = String.valueOf(zzkq.zzc(this.zzru.doubleValue()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final /* synthetic */ zzja zzf(zzja zzja) {
        return new zziq(this.zzru, zzja);
    }

    protected final zzix zzfb() {
        return zzix.Number;
    }
}
