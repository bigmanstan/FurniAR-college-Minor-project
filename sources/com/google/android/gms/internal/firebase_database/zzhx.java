package com.google.android.gms.internal.firebase_database;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class zzhx implements zzia {
    private final Set<String> zzqm;
    private final zzib zzqn;

    public zzhx(zzib zzib, List<String> list) {
        if (list != null) {
            this.zzqm = new HashSet(list);
        } else {
            this.zzqm = null;
        }
        this.zzqn = zzib;
    }

    protected String zza(zzib zzib, String str, String str2, long j) {
        String date = new Date(j).toString();
        String valueOf = String.valueOf(zzib);
        StringBuilder stringBuilder = new StringBuilder((((String.valueOf(date).length() + 6) + String.valueOf(valueOf).length()) + String.valueOf(str).length()) + String.valueOf(str2).length());
        stringBuilder.append(date);
        stringBuilder.append(" [");
        stringBuilder.append(valueOf);
        stringBuilder.append("] ");
        stringBuilder.append(str);
        stringBuilder.append(": ");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    public final void zzb(zzib zzib, String str, String str2, long j) {
        Object obj = (zzib.ordinal() < this.zzqn.ordinal() || !(this.zzqm == null || zzib.ordinal() > zzib.DEBUG.ordinal() || this.zzqm.contains(str))) ? null : 1;
        if (obj != null) {
            str2 = zza(zzib, str, str2, j);
            switch (zzhy.zzfo[zzib.ordinal()]) {
                case 1:
                    zze(str, str2);
                    break;
                case 2:
                    zzf(str, str2);
                    return;
                case 3:
                    zzg(str, str2);
                    return;
                case 4:
                    zzh(str, str2);
                    return;
                default:
                    throw new RuntimeException("Should not reach here!");
            }
        }
    }

    public final zzib zzbn() {
        return this.zzqn;
    }

    protected void zze(String str, String str2) {
        System.err.println(str2);
    }

    protected void zzf(String str, String str2) {
        System.out.println(str2);
    }

    protected void zzg(String str, String str2) {
        System.out.println(str2);
    }

    protected void zzh(String str, String str2) {
        System.out.println(str2);
    }
}
