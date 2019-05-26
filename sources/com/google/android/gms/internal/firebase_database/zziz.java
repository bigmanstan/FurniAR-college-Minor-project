package com.google.android.gms.internal.firebase_database;

public final class zziz {
    private static final zziz zzsg = new zziz(zzid.zzfc(), zzir.zzfv());
    private static final zziz zzsh = new zziz(zzid.zzfd(), zzja.zzsi);
    private final zzid zzog;
    private final zzja zzrx;

    public zziz(zzid zzid, zzja zzja) {
        this.zzog = zzid;
        this.zzrx = zzja;
    }

    public static zziz zzgc() {
        return zzsg;
    }

    public static zziz zzgd() {
        return zzsh;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                zziz zziz = (zziz) obj;
                return this.zzog.equals(zziz.zzog) && this.zzrx.equals(zziz.zzrx);
            }
        }
        return false;
    }

    public final int hashCode() {
        return (this.zzog.hashCode() * 31) + this.zzrx.hashCode();
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzog);
        String valueOf2 = String.valueOf(this.zzrx);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 23) + String.valueOf(valueOf2).length());
        stringBuilder.append("NamedNode{name=");
        stringBuilder.append(valueOf);
        stringBuilder.append(", node=");
        stringBuilder.append(valueOf2);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public final zzja zzd() {
        return this.zzrx;
    }

    public final zzid zzge() {
        return this.zzog;
    }
}
