package com.google.android.gms.internal.firebase_database;

import java.util.Map;

final class zzas implements zzau {
    private final /* synthetic */ zzal zzdu;

    zzas(zzal zzal) {
        this.zzdu = zzal;
    }

    public final void zzd(Map<String, Object> map) {
        String str = (String) map.get("s");
        if (!str.equals("ok")) {
            String str2 = (String) map.get("d");
            if (this.zzdu.zzbs.zzfa()) {
                zzhz zza = this.zzdu.zzbs;
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 34) + String.valueOf(str2).length());
                stringBuilder.append("Failed to send stats: ");
                stringBuilder.append(str);
                stringBuilder.append(" (message: ");
                stringBuilder.append(str2);
                stringBuilder.append(")");
                zza.zza(stringBuilder.toString(), null, new Object[0]);
            }
        }
    }
}
