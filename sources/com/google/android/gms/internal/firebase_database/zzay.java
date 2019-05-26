package com.google.android.gms.internal.firebase_database;

final class zzay {
    private final zzbb zzem;
    private final zzaw zzen;
    private final zzai zzeo;
    private final Long zzep;

    private zzay(zzbb zzbb, zzaw zzaw, Long l, zzai zzai) {
        this.zzem = zzbb;
        this.zzen = zzaw;
        this.zzeo = zzai;
        this.zzep = l;
    }

    public final String toString() {
        String zzaw = this.zzen.toString();
        String valueOf = String.valueOf(this.zzep);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzaw).length() + 8) + String.valueOf(valueOf).length());
        stringBuilder.append(zzaw);
        stringBuilder.append(" (Tag: ");
        stringBuilder.append(valueOf);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final zzaw zzaj() {
        return this.zzen;
    }

    public final Long zzak() {
        return this.zzep;
    }

    public final zzai zzal() {
        return this.zzeo;
    }
}
