package com.google.android.gms.internal.firebase_database;

public final class zzic extends zziv<zzic> {
    private final boolean value;

    public zzic(Boolean bool, zzja zzja) {
        super(zzja);
        this.value = bool.booleanValue();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzic)) {
            return false;
        }
        zzic zzic = (zzic) obj;
        return this.value == zzic.value && this.zzrd.equals(zzic.zzrd);
    }

    public final Object getValue() {
        return Boolean.valueOf(this.value);
    }

    public final int hashCode() {
        return this.value + this.zzrd.hashCode();
    }

    protected final /* synthetic */ int zza(zziv zziv) {
        return this.value == ((zzic) zziv).value ? 0 : this.value ? 1 : -1;
    }

    public final String zza(zzjc zzjc) {
        String zzb = zzb(zzjc);
        boolean z = this.value;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzb).length() + 13);
        stringBuilder.append(zzb);
        stringBuilder.append("boolean:");
        stringBuilder.append(z);
        return stringBuilder.toString();
    }

    public final /* synthetic */ zzja zzf(zzja zzja) {
        return new zzic(Boolean.valueOf(this.value), zzja);
    }

    protected final zzix zzfb() {
        return zzix.Boolean;
    }
}
