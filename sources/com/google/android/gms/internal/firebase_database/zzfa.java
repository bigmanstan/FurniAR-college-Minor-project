package com.google.android.gms.internal.firebase_database;

public final class zzfa {
    private final zzch zzap;
    private final long zzls;
    private final zzja zzlt;
    private final zzbv zzlu;
    private final boolean zzlv;

    public zzfa(long j, zzch zzch, zzbv zzbv) {
        this.zzls = j;
        this.zzap = zzch;
        this.zzlt = null;
        this.zzlu = zzbv;
        this.zzlv = true;
    }

    public zzfa(long j, zzch zzch, zzja zzja, boolean z) {
        this.zzls = j;
        this.zzap = zzch;
        this.zzlt = zzja;
        this.zzlu = null;
        this.zzlv = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r7) {
        /*
        r6 = this;
        r0 = 1;
        if (r6 != r7) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 0;
        if (r7 == 0) goto L_0x0058;
    L_0x0007:
        r2 = r6.getClass();
        r3 = r7.getClass();
        if (r2 == r3) goto L_0x0012;
    L_0x0011:
        goto L_0x0058;
    L_0x0012:
        r7 = (com.google.android.gms.internal.firebase_database.zzfa) r7;
        r2 = r6.zzls;
        r4 = r7.zzls;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 == 0) goto L_0x001d;
    L_0x001c:
        return r1;
    L_0x001d:
        r2 = r6.zzap;
        r3 = r7.zzap;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0028;
    L_0x0027:
        return r1;
    L_0x0028:
        r2 = r6.zzlv;
        r3 = r7.zzlv;
        if (r2 == r3) goto L_0x002f;
    L_0x002e:
        return r1;
    L_0x002f:
        r2 = r6.zzlt;
        if (r2 == 0) goto L_0x003e;
    L_0x0033:
        r2 = r6.zzlt;
        r3 = r7.zzlt;
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0042;
    L_0x003d:
        goto L_0x0043;
    L_0x003e:
        r2 = r7.zzlt;
        if (r2 == 0) goto L_0x0043;
    L_0x0042:
        return r1;
    L_0x0043:
        r2 = r6.zzlu;
        if (r2 == 0) goto L_0x0052;
    L_0x0047:
        r2 = r6.zzlu;
        r7 = r7.zzlu;
        r7 = r2.equals(r7);
        if (r7 == 0) goto L_0x0056;
    L_0x0051:
        goto L_0x0057;
    L_0x0052:
        r7 = r7.zzlu;
        if (r7 == 0) goto L_0x0057;
    L_0x0056:
        return r1;
    L_0x0057:
        return r0;
    L_0x0058:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzfa.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((Long.valueOf(this.zzls).hashCode() * 31) + Boolean.valueOf(this.zzlv).hashCode()) * 31) + this.zzap.hashCode()) * 31) + (this.zzlt != null ? this.zzlt.hashCode() : 0)) * 31;
        if (this.zzlu != null) {
            i = this.zzlu.hashCode();
        }
        return hashCode + i;
    }

    public final boolean isVisible() {
        return this.zzlv;
    }

    public final String toString() {
        long j = this.zzls;
        String valueOf = String.valueOf(this.zzap);
        boolean z = this.zzlv;
        String valueOf2 = String.valueOf(this.zzlt);
        String valueOf3 = String.valueOf(this.zzlu);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(valueOf).length() + 78) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length());
        stringBuilder.append("UserWriteRecord{id=");
        stringBuilder.append(j);
        stringBuilder.append(" path=");
        stringBuilder.append(valueOf);
        stringBuilder.append(" visible=");
        stringBuilder.append(z);
        stringBuilder.append(" overwrite=");
        stringBuilder.append(valueOf2);
        stringBuilder.append(" merge=");
        stringBuilder.append(valueOf3);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final long zzcn() {
        return this.zzls;
    }

    public final zzja zzco() {
        if (this.zzlt != null) {
            return this.zzlt;
        }
        throw new IllegalArgumentException("Can't access overwrite when write is a merge!");
    }

    public final zzbv zzcp() {
        if (this.zzlu != null) {
            return this.zzlu;
        }
        throw new IllegalArgumentException("Can't access merge when write is an overwrite!");
    }

    public final boolean zzcq() {
        return this.zzlt != null;
    }

    public final zzch zzg() {
        return this.zzap;
    }
}
