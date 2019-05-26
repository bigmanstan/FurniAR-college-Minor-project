package com.google.android.gms.internal.firebase_database;

import java.util.Map;

final class zzao implements zzau {
    private final /* synthetic */ zzbb zzdx;

    zzao(zzal zzal, zzbb zzbb) {
        this.zzdx = zzbb;
    }

    public final void zzd(Map<String, Object> map) {
        String str = (String) map.get("s");
        String str2 = null;
        if (str.equals("ok")) {
            str = null;
        } else {
            str2 = (String) map.get("d");
        }
        if (this.zzdx != null) {
            this.zzdx.zzb(str, str2);
        }
    }
}
