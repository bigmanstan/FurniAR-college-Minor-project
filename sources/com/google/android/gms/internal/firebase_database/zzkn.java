package com.google.android.gms.internal.firebase_database;

public final class zzkn<T, U> {
    private final T first;
    private final U second;

    public zzkn(T t, U u) {
        this.first = t;
        this.second = u;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
        r4 = this;
        r0 = 1;
        if (r4 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 0;
        if (r5 == 0) goto L_0x003d;
    L_0x0007:
        r2 = r4.getClass();
        r3 = r5.getClass();
        if (r2 == r3) goto L_0x0012;
    L_0x0011:
        goto L_0x003d;
    L_0x0012:
        r5 = (com.google.android.gms.internal.firebase_database.zzkn) r5;
        r2 = r4.first;
        if (r2 == 0) goto L_0x0023;
    L_0x0018:
        r2 = r4.first;
        r3 = r5.first;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0028;
    L_0x0022:
        goto L_0x0027;
    L_0x0023:
        r2 = r5.first;
        if (r2 == 0) goto L_0x0028;
    L_0x0027:
        return r1;
    L_0x0028:
        r2 = r4.second;
        if (r2 == 0) goto L_0x0037;
    L_0x002c:
        r2 = r4.second;
        r5 = r5.second;
        r5 = r2.equals(r5);
        if (r5 != 0) goto L_0x003c;
    L_0x0036:
        goto L_0x003b;
    L_0x0037:
        r5 = r5.second;
        if (r5 == 0) goto L_0x003c;
    L_0x003b:
        return r1;
    L_0x003c:
        return r0;
    L_0x003d:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzkn.equals(java.lang.Object):boolean");
    }

    public final T getFirst() {
        return this.first;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.first != null ? this.first.hashCode() : 0) * 31;
        if (this.second != null) {
            i = this.second.hashCode();
        }
        return hashCode + i;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.first);
        String valueOf2 = String.valueOf(this.second);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 7) + String.valueOf(valueOf2).length());
        stringBuilder.append("Pair(");
        stringBuilder.append(valueOf);
        stringBuilder.append(",");
        stringBuilder.append(valueOf2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final U zzgv() {
        return this.second;
    }
}
