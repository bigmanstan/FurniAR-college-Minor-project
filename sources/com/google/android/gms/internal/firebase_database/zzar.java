package com.google.android.gms.internal.firebase_database;

import java.util.List;
import java.util.Map;

final class zzar implements zzau {
    private final /* synthetic */ zzal zzdu;
    private final /* synthetic */ zzay zzeb;

    zzar(zzal zzal, zzay zzay) {
        this.zzdu = zzal;
        this.zzeb = zzay;
    }

    public final void zzd(Map<String, Object> map) {
        String str = (String) map.get("s");
        if (str.equals("ok")) {
            Map map2 = (Map) map.get("d");
            if (map2.containsKey("w")) {
                this.zzdu.zza((List) map2.get("w"), this.zzeb.zzen);
            }
        }
        if (((zzay) this.zzdu.zzdi.get(this.zzeb.zzaj())) == this.zzeb) {
            if (str.equals("ok")) {
                this.zzeb.zzem.zzb(null, null);
            } else {
                this.zzdu.zza(this.zzeb.zzaj());
                this.zzeb.zzem.zzb(str, (String) map.get("d"));
            }
        }
    }
}
