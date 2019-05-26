package com.google.android.gms.internal.firebase_database;

public final class zzji extends zziv<zzji> {
    private final String value;

    public zzji(String str, zzja zzja) {
        super(zzja);
        this.value = str;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzji)) {
            return false;
        }
        zzji zzji = (zzji) obj;
        return this.value.equals(zzji.value) && this.zzrd.equals(zzji.zzrd);
    }

    public final Object getValue() {
        return this.value;
    }

    public final int hashCode() {
        return this.value.hashCode() + this.zzrd.hashCode();
    }

    protected final /* synthetic */ int zza(zziv zziv) {
        return this.value.compareTo(((zzji) zziv).value);
    }

    public final String zza(zzjc zzjc) {
        String zzb;
        String str;
        StringBuilder stringBuilder;
        switch (zzjj.zzsa[zzjc.ordinal()]) {
            case 1:
                zzb = zzb(zzjc);
                str = this.value;
                stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 7) + String.valueOf(str).length());
                break;
            case 2:
                zzb = zzb(zzjc);
                str = zzkq.zzz(this.value);
                stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 7) + String.valueOf(str).length());
                break;
            default:
                zzb = String.valueOf(zzjc);
                stringBuilder = new StringBuilder(String.valueOf(zzb).length() + 38);
                stringBuilder.append("Invalid hash version for string node: ");
                stringBuilder.append(zzb);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
        stringBuilder.append(zzb);
        stringBuilder.append("string:");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public final /* synthetic */ zzja zzf(zzja zzja) {
        return new zzji(this.value, zzja);
    }

    protected final zzix zzfb() {
        return zzix.String;
    }
}
