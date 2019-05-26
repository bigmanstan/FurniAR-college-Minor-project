package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.zzh;
import java.util.ArrayList;
import java.util.List;

final class zzcs implements zzbb {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ List zzis;
    private final /* synthetic */ zzck zzit;

    zzcs(zzck zzck, zzch zzch, List list, zzck zzck2) {
        this.zzil = zzck;
        this.zzgy = zzch;
        this.zzis = list;
        this.zzit = zzck2;
    }

    public final void zzb(String str, String str2) {
        DatabaseError zzd = zzck.zzc(str, str2);
        this.zzil.zza("Transaction", this.zzgy, zzd);
        List arrayList = new ArrayList();
        if (zzd == null) {
            List arrayList2 = new ArrayList();
            for (zzdl zzdl : this.zzis) {
                zzdl.zzjl = zzdm.zzjw;
                arrayList.addAll(this.zzil.zzih.zza(zzdl.zzjp, false, false, this.zzil.zzhu));
                arrayList2.add(new zzct(this, zzdl, zzh.zza(zzh.zza(this.zzit, zzdl.zzap), zzit.zzj(zzdl.zzjs))));
                this.zzil.zze(new zzfc(this.zzil, zzdl.zzjk, zzhh.zzal(zzdl.zzap)));
            }
            this.zzil.zzb(this.zzil.zzhx.zzaj(this.zzgy));
            this.zzil.zzcf();
            this.zzit.zzc(arrayList);
            for (int i = 0; i < arrayList2.size(); i++) {
                this.zzil.zza((Runnable) arrayList2.get(i));
            }
            return;
        }
        if (zzd.getCode() == -1) {
            for (zzdl zzdl2 : this.zzis) {
                zzdl2.zzjl = zzdl2.zzjl == zzdm.zzjx ? zzdm.zzjy : zzdm.zzju;
            }
        } else {
            for (zzdl zzdl3 : this.zzis) {
                zzdl3.zzjl = zzdm.zzjy;
                zzdl3.zzjo = zzd;
            }
        }
        this.zzil.zzn(this.zzgy);
    }
}
