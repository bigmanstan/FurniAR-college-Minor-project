package com.google.android.gms.internal.firebase_database;

public final class zziy extends zziv<zziy> {
    private final long value;

    public zziy(Long l, zzja zzja) {
        super(zzja);
        this.value = l.longValue();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zziy)) {
            return false;
        }
        zziy zziy = (zziy) obj;
        return this.value == zziy.value && this.zzrd.equals(zziy.zzrd);
    }

    public final Object getValue() {
        return Long.valueOf(this.value);
    }

    public final int hashCode() {
        return ((int) (this.value ^ (this.value >>> 32))) + this.zzrd.hashCode();
    }

    protected final /* synthetic */ int zza(zziv zziv) {
        return zzkq.zzb(this.value, ((zziy) zziv).value);
    }

    public final String zza(zzjc zzjc) {
        String valueOf = String.valueOf(String.valueOf(zzb(zzjc)).concat("number:"));
        String valueOf2 = String.valueOf(zzkq.zzc((double) this.value));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final /* synthetic */ zzja zzf(zzja zzja) {
        return new zziy(Long.valueOf(this.value), zzja);
    }

    protected final zzix zzfb() {
        return zzix.Number;
    }
}
