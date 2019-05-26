package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

final class zzei implements Callable<List<zzgy>> {
    private final /* synthetic */ zzhh zziz;
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzce zzle;
    private final /* synthetic */ DatabaseError zzlf;

    zzei(zzee zzee, zzhh zzhh, zzce zzce, DatabaseError databaseError) {
        this.zzlb = zzee;
        this.zziz = zzhh;
        this.zzle = zzce;
        this.zzlf = databaseError;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzch zzg = this.zziz.zzg();
        zzed zzed = (zzed) this.zzlb.zzkq.zzai(zzg);
        Object arrayList = new ArrayList();
        if (zzed != null && (this.zziz.isDefault() || zzed.zzc(this.zziz))) {
            Object obj;
            zzkn zza = zzed.zza(this.zziz, this.zzle, this.zzlf);
            if (zzed.isEmpty()) {
                this.zzlb.zzkq = this.zzlb.zzkq.zzah(zzg);
            }
            List<zzhh> list = (List) zza.getFirst();
            arrayList = (List) zza.zzgv();
            loop0:
            while (true) {
                obj = null;
                for (zzhh zzhh : list) {
                    this.zzlb.zzkp.zzh(this.zziz);
                    if (obj != null || zzhh.zzek()) {
                        int i = 1;
                    }
                }
                break loop0;
            }
            zzgj zzd = this.zzlb.zzkq;
            Object obj2 = (zzd.getValue() == null || !((zzed) zzd.getValue()).zzci()) ? null : 1;
            Iterator it = zzg.iterator();
            while (it.hasNext()) {
                zzd = zzd.zze((zzid) it.next());
                if (obj2 == null) {
                    if (zzd.getValue() == null || !((zzed) zzd.getValue()).zzci()) {
                        obj2 = null;
                        if (obj2 == null) {
                            if (zzd.isEmpty()) {
                                break;
                            }
                        }
                        break;
                    }
                }
                obj2 = 1;
                if (obj2 == null) {
                    if (zzd.isEmpty()) {
                        break;
                    }
                }
                break;
            }
            if (obj != null && obj2 == null) {
                zzgj zzag = this.zzlb.zzkq.zzag(zzg);
                if (!zzag.isEmpty()) {
                    for (zzhi zzhi : this.zzlb.zza(zzag)) {
                        Object zzev = new zzev(this.zzlb, zzhi);
                        this.zzlb.zzkv.zza(zzee.zzd(zzhi.zzeo()), zzev.zzlp, zzev, zzev);
                    }
                }
            }
            if (obj2 == null && !list.isEmpty() && this.zzlf == null) {
                if (obj != null) {
                    this.zzlb.zzkv.zza(zzee.zzd(this.zziz), null);
                } else {
                    for (zzhh zzhh2 : list) {
                        this.zzlb.zzkv.zza(zzee.zzd(zzhh2), this.zzlb.zze(zzhh2));
                    }
                }
            }
            this.zzlb.zzd((List) list);
        }
        return arrayList;
    }
}
