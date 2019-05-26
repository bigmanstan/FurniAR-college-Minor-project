package com.google.android.gms.internal.firebase_database;

public final class zzfn {
    public static final zzfn zzmu = new zzfn(zzfo.User, null, false);
    public static final zzfn zzmv = new zzfn(zzfo.Server, null, false);
    private final zzfo zzmw;
    private final zzhe zzmx;
    private final boolean zzmy;

    private zzfn(zzfo zzfo, zzhe zzhe, boolean z) {
        this.zzmw = zzfo;
        this.zzmx = zzhe;
        this.zzmy = z;
    }

    public static zzfn zzc(zzhe zzhe) {
        return new zzfn(zzfo.Server, zzhe, true);
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzmw);
        String valueOf2 = String.valueOf(this.zzmx);
        boolean z = this.zzmy;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 52) + String.valueOf(valueOf2).length());
        stringBuilder.append("OperationSource{source=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", queryParams=");
        stringBuilder.append(valueOf2);
        stringBuilder.append(", tagged=");
        stringBuilder.append(z);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public final boolean zzda() {
        return this.zzmw == zzfo.User;
    }

    public final boolean zzdb() {
        return this.zzmy;
    }

    public final zzhe zzdc() {
        return this.zzmx;
    }
}
