package com.google.android.gms.internal.firebase_database;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class zzhz {
    private final zzia zzco;
    private final String zzqo;
    private final String zzqp;

    public zzhz(zzia zzia, String str) {
        this(zzia, str, null);
    }

    public zzhz(zzia zzia, String str, String str2) {
        this.zzco = zzia;
        this.zzqo = str;
        this.zzqp = str2;
    }

    private final String zza(String str, Object... objArr) {
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        if (this.zzqp == null) {
            return str;
        }
        String str2 = this.zzqp;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str2).length() + 3) + String.valueOf(str).length());
        stringBuilder.append(str2);
        stringBuilder.append(" - ");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    private static String zzb(Throwable th) {
        Writer stringWriter = new StringWriter();
        zze.zza(th, new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public final void info(String str) {
        this.zzco.zzb(zzib.INFO, this.zzqo, zza(str, new Object[0]), System.currentTimeMillis());
    }

    public final void zza(String str, Throwable th) {
        str = zza(str, new Object[0]);
        String zzb = zzb(th);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(zzb).length());
        stringBuilder.append(str);
        stringBuilder.append("\n");
        stringBuilder.append(zzb);
        this.zzco.zzb(zzib.ERROR, this.zzqo, stringBuilder.toString(), System.currentTimeMillis());
    }

    public final void zza(String str, Throwable th, Object... objArr) {
        if (zzfa()) {
            str = zza(str, objArr);
            if (th != null) {
                String zzb = zzb(th);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(zzb).length());
                stringBuilder.append(str);
                stringBuilder.append("\n");
                stringBuilder.append(zzb);
                str = stringBuilder.toString();
            }
            this.zzco.zzb(zzib.DEBUG, this.zzqo, str, System.currentTimeMillis());
        }
    }

    public final void zzb(String str, Throwable th) {
        this.zzco.zzb(zzib.WARN, this.zzqo, zza(str, new Object[0]), System.currentTimeMillis());
    }

    public final boolean zzfa() {
        return this.zzco.zzbn().ordinal() <= zzib.DEBUG.ordinal();
    }
}
