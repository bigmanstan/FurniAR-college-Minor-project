package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzha {
    private final zzhh zzpc;
    private final zzis zzpd;

    public zzha(zzhh zzhh) {
        this.zzpc = zzhh;
        this.zzpd = zzhh.zzeg();
    }

    private final void zza(List<zzgx> list, zzgz zzgz, List<zzgw> list2, List<zzce> list3, zzit zzit) {
        List arrayList = new ArrayList();
        for (zzgw zzgw : list2) {
            if (zzgw.zzdt().equals(zzgz)) {
                arrayList.add(zzgw);
            }
        }
        Collections.sort(arrayList, new zzhb(this));
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzgw zzgw2 = (zzgw) obj;
            for (zzce zzce : list3) {
                if (zzce.zza(zzgz)) {
                    zzgw zzg;
                    if (!zzgw2.zzdt().equals(zzgz.VALUE)) {
                        if (!zzgw2.zzdt().equals(zzgz.CHILD_REMOVED)) {
                            zzg = zzgw2.zzg(zzit.zza(zzgw2.zzds(), zzgw2.zzdq().zzd(), this.zzpd));
                            list.add(zzce.zza(zzg, this.zzpc));
                        }
                    }
                    zzg = zzgw2;
                    list.add(zzce.zza(zzg, this.zzpc));
                }
            }
        }
    }

    public final List<zzgx> zza(List<zzgw> list, zzit zzit, List<zzce> list2) {
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        for (zzgw zzgw : list) {
            if (zzgw.zzdt().equals(zzgz.CHILD_CHANGED)) {
                if ((this.zzpd.compare(new zziz(zzid.zzfc(), zzgw.zzdv().zzd()), new zziz(zzid.zzfc(), zzgw.zzdq().zzd())) != 0 ? 1 : null) != null) {
                    arrayList2.add(zzgw.zzc(zzgw.zzds(), zzgw.zzdq()));
                }
            }
        }
        List list3 = arrayList;
        List<zzgw> list4 = list;
        List<zzce> list5 = list2;
        zzit zzit2 = zzit;
        zza(list3, zzgz.CHILD_REMOVED, list4, list5, zzit2);
        zza(list3, zzgz.CHILD_ADDED, list4, list5, zzit2);
        zza(list3, zzgz.CHILD_MOVED, arrayList2, list5, zzit2);
        list4 = list;
        zza(list3, zzgz.CHILD_CHANGED, list4, list5, zzit2);
        zza(list3, zzgz.VALUE, list4, list5, zzit2);
        return arrayList;
    }
}
