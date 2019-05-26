package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DataSnapshot;

public final class zzgx implements zzgy {
    private final zzce zzoo;
    private final zzgz zzoq;
    private final DataSnapshot zzou;
    private final String zzov;

    public zzgx(zzgz zzgz, zzce zzce, DataSnapshot dataSnapshot, String str) {
        this.zzoq = zzgz;
        this.zzoo = zzce;
        this.zzou = dataSnapshot;
        this.zzov = str;
    }

    private final zzch zzg() {
        zzch zzg = this.zzou.getRef().zzg();
        return this.zzoq == zzgz.VALUE ? zzg : zzg.zzby();
    }

    public final String toString() {
        if (this.zzoq == zzgz.VALUE) {
            String valueOf = String.valueOf(zzg());
            String valueOf2 = String.valueOf(this.zzoq);
            String valueOf3 = String.valueOf(this.zzou.getValue(true));
            StringBuilder stringBuilder = new StringBuilder(((String.valueOf(valueOf).length() + 4) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length());
            stringBuilder.append(valueOf);
            stringBuilder.append(": ");
            stringBuilder.append(valueOf2);
            stringBuilder.append(": ");
            stringBuilder.append(valueOf3);
            return stringBuilder.toString();
        }
        valueOf = String.valueOf(zzg());
        valueOf2 = String.valueOf(this.zzoq);
        String key = this.zzou.getKey();
        valueOf3 = String.valueOf(this.zzou.getValue(true));
        StringBuilder stringBuilder2 = new StringBuilder((((String.valueOf(valueOf).length() + 10) + String.valueOf(valueOf2).length()) + String.valueOf(key).length()) + String.valueOf(valueOf3).length());
        stringBuilder2.append(valueOf);
        stringBuilder2.append(": ");
        stringBuilder2.append(valueOf2);
        stringBuilder2.append(": { ");
        stringBuilder2.append(key);
        stringBuilder2.append(": ");
        stringBuilder2.append(valueOf3);
        stringBuilder2.append(" }");
        return stringBuilder2.toString();
    }

    public final void zzdr() {
        this.zzoo.zza(this);
    }

    public final zzgz zzdt() {
        return this.zzoq;
    }

    public final DataSnapshot zzdw() {
        return this.zzou;
    }

    public final String zzdx() {
        return this.zzov;
    }
}
