package com.google.android.gms.internal.firebase_database;

import java.util.Map;

final class zzap implements zzau {
    private final /* synthetic */ zzal zzdu;
    private final /* synthetic */ boolean zzdy;

    zzap(zzal zzal, boolean z) {
        this.zzdu = zzal;
        this.zzdy = z;
    }

    public final void zzd(Map<String, Object> map) {
        this.zzdu.zzdc = zzav.Connected;
        String str = (String) map.get("s");
        if (str.equals("ok")) {
            this.zzdu.zzdp = 0;
            this.zzdu.zzcw.zzb(true);
            if (this.zzdy) {
                this.zzdu.zzaf();
                return;
            }
        }
        this.zzdu.zzdj = null;
        this.zzdu.zzdk = true;
        this.zzdu.zzcw.zzb(false);
        String str2 = (String) map.get("d");
        zzhz zza = this.zzdu.zzbs;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 26) + String.valueOf(str2).length());
        stringBuilder.append("Authentication failed: ");
        stringBuilder.append(str);
        stringBuilder.append(" (");
        stringBuilder.append(str2);
        stringBuilder.append(")");
        zza.zza(stringBuilder.toString(), null, new Object[0]);
        this.zzdu.zzdb.close();
        if (str.equals("invalid_token")) {
            this.zzdu.zzdp = this.zzdu.zzdp + 1;
            if (((long) this.zzdu.zzdp) >= 3) {
                this.zzdu.zzdm.zzay();
                this.zzdu.zzbs.zzb("Provided authentication credentials are invalid. This usually indicates your FirebaseApp instance was not initialized correctly. Make sure your google-services.json file has the correct firebase_url and api_key. You can re-download google-services.json from https://console.firebase.google.com/.", null);
            }
        }
    }
}
