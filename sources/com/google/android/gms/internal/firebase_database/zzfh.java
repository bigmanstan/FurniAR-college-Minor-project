package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class zzfh implements zzcf {
    private static zzfh zzmj = new zzfh();
    private final HashMap<zzce, List<zzce>> zzmi = new HashMap();

    private zzfh() {
    }

    public static zzfh zzcu() {
        return zzmj;
    }

    public final void zzd(zzce zzce) {
        synchronized (this.zzmi) {
            List list = (List) this.zzmi.get(zzce);
            int i = 0;
            if (list != null) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (list.get(i2) == zzce) {
                        list.remove(i2);
                        break;
                    }
                }
                if (list.isEmpty()) {
                    this.zzmi.remove(zzce);
                }
            }
            if (!zzce.zzbe().isDefault()) {
                zzce zza = zzce.zza(zzhh.zzal(zzce.zzbe().zzg()));
                List list2 = (List) this.zzmi.get(zza);
                if (list2 != null) {
                    while (i < list2.size()) {
                        if (list2.get(i) == zzce) {
                            list2.remove(i);
                            break;
                        }
                        i++;
                    }
                    if (list2.isEmpty()) {
                        this.zzmi.remove(zza);
                    }
                }
            }
        }
    }

    public final void zzi(zzce zzce) {
        synchronized (this.zzmi) {
            List list = (List) this.zzmi.get(zzce);
            if (list == null) {
                list = new ArrayList();
                this.zzmi.put(zzce, list);
            }
            list.add(zzce);
            if (!zzce.zzbe().isDefault()) {
                zzce zza = zzce.zza(zzhh.zzal(zzce.zzbe().zzg()));
                List list2 = (List) this.zzmi.get(zza);
                if (list2 == null) {
                    list2 = new ArrayList();
                    this.zzmi.put(zza, list2);
                }
                list2.add(zzce);
            }
            zzce.zze(true);
            zzce.zza((zzcf) this);
        }
    }

    public final void zzj(zzce zzce) {
        synchronized (this.zzmi) {
            List list = (List) this.zzmi.get(zzce);
            if (!(list == null || list.isEmpty())) {
                if (zzce.zzbe().isDefault()) {
                    HashSet hashSet = new HashSet();
                    for (int size = list.size() - 1; size >= 0; size--) {
                        zzce zzce2 = (zzce) list.get(size);
                        if (!hashSet.contains(zzce2.zzbe())) {
                            hashSet.add(zzce2.zzbe());
                            zzce2.zzbr();
                        }
                    }
                } else {
                    ((zzce) list.get(0)).zzbr();
                }
            }
        }
    }
}
