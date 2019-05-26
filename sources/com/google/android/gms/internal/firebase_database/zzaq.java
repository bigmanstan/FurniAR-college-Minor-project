package com.google.android.gms.internal.firebase_database;

import java.util.Map;

final class zzaq implements zzau {
    private final /* synthetic */ String val$action;
    private final /* synthetic */ zzal zzdu;
    private final /* synthetic */ zzbb zzdx;
    private final /* synthetic */ long zzdz;
    private final /* synthetic */ zzaz zzea;

    zzaq(zzal zzal, String str, long j, zzaz zzaz, zzbb zzbb) {
        this.zzdu = zzal;
        this.val$action = str;
        this.zzdz = j;
        this.zzea = zzaz;
        this.zzdx = zzbb;
    }

    public final void zzd(Map<String, Object> map) {
        if (this.zzdu.zzbs.zzfa()) {
            zzhz zza = this.zzdu.zzbs;
            String str = this.val$action;
            String valueOf = String.valueOf(map);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 11) + String.valueOf(valueOf).length());
            stringBuilder.append(str);
            stringBuilder.append(" response: ");
            stringBuilder.append(valueOf);
            zza.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (((zzaz) this.zzdu.zzdh.get(Long.valueOf(this.zzdz))) == this.zzea) {
            this.zzdu.zzdh.remove(Long.valueOf(this.zzdz));
            if (this.zzdx != null) {
                String str2 = (String) map.get("s");
                if (str2.equals("ok")) {
                    this.zzdx.zzb(null, null);
                } else {
                    this.zzdx.zzb(str2, (String) map.get("d"));
                }
            }
        } else if (this.zzdu.zzbs.zzfa()) {
            zzhz zza2 = this.zzdu.zzbs;
            long j = this.zzdz;
            StringBuilder stringBuilder2 = new StringBuilder(81);
            stringBuilder2.append("Ignoring on complete for put ");
            stringBuilder2.append(j);
            stringBuilder2.append(" because it was removed already.");
            zza2.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        this.zzdu.zzag();
    }
}
