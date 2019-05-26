package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzed {
    private final Map<zzhe, zzhi> zzko = new HashMap();
    private final zzfv zzkp;

    public zzed(zzfv zzfv) {
        this.zzkp = zzfv;
    }

    private final List<zzgx> zza(zzhi zzhi, zzfl zzfl, zzfg zzfg, zzja zzja) {
        zzhj zzb = zzhi.zzb(zzfl, zzfg, zzja);
        if (!zzhi.zzeo().zzek()) {
            Set hashSet = new HashSet();
            Set hashSet2 = new HashSet();
            for (zzgw zzgw : zzb.zzpy) {
                zzgz zzdt = zzgw.zzdt();
                if (zzdt == zzgz.CHILD_ADDED) {
                    hashSet2.add(zzgw.zzds());
                } else if (zzdt == zzgz.CHILD_REMOVED) {
                    hashSet.add(zzgw.zzds());
                }
            }
            if (!(hashSet2.isEmpty() && hashSet.isEmpty())) {
                this.zzkp.zza(zzhi.zzeo(), hashSet2, hashSet);
            }
        }
        return zzb.zzpx;
    }

    public final boolean isEmpty() {
        return this.zzko.isEmpty();
    }

    public final zzkn<List<zzhh>, List<zzgy>> zza(zzhh zzhh, zzce zzce, DatabaseError databaseError) {
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        boolean zzci = zzci();
        if (zzhh.isDefault()) {
            Iterator it = this.zzko.entrySet().iterator();
            while (it.hasNext()) {
                zzhi zzhi = (zzhi) ((Entry) it.next()).getValue();
                arrayList2.addAll(zzhi.zza(zzce, databaseError));
                if (zzhi.isEmpty()) {
                    it.remove();
                    if (!zzhi.zzeo().zzek()) {
                        arrayList.add(zzhi.zzeo());
                    }
                }
            }
        } else {
            zzhi zzhi2 = (zzhi) this.zzko.get(zzhh.zzen());
            if (zzhi2 != null) {
                arrayList2.addAll(zzhi2.zza(zzce, databaseError));
                if (zzhi2.isEmpty()) {
                    this.zzko.remove(zzhh.zzen());
                    if (!zzhi2.zzeo().zzek()) {
                        arrayList.add(zzhi2.zzeo());
                    }
                }
            }
        }
        if (zzci && !zzci()) {
            arrayList.add(zzhh.zzal(zzhh.zzg()));
        }
        return new zzkn(arrayList, arrayList2);
    }

    public final List<zzgx> zza(zzce zzce, zzfg zzfg, zzgu zzgu) {
        zzhh zzbe = zzce.zzbe();
        zzhi zzhi = (zzhi) this.zzko.get(zzbe.zzen());
        if (zzhi == null) {
            boolean z;
            zzja zzc = zzfg.zzc(zzgu.zzdo() ? zzgu.zzd() : null);
            if (zzc != null) {
                z = true;
            } else {
                zzc = zzfg.zzd(zzgu.zzd());
                z = false;
            }
            zzhi = new zzhi(zzbe, new zzhk(new zzgu(zzit.zza(zzc, zzbe.zzeg()), z, false), zzgu));
            if (!zzbe.zzek()) {
                Set hashSet = new HashSet();
                for (zziz zzge : zzhi.zzeq()) {
                    hashSet.add(zzge.zzge());
                }
                this.zzkp.zza(zzbe, hashSet);
            }
            this.zzko.put(zzbe.zzen(), zzhi);
        }
        zzhi.zzb(zzce);
        return zzhi.zzk(zzce);
    }

    public final List<zzgx> zza(zzfl zzfl, zzfg zzfg, zzja zzja) {
        zzhe zzdc = zzfl.zzcy().zzdc();
        if (zzdc != null) {
            return zza((zzhi) this.zzko.get(zzdc), zzfl, zzfg, zzja);
        }
        List<zzgx> arrayList = new ArrayList();
        for (Entry value : this.zzko.entrySet()) {
            arrayList.addAll(zza((zzhi) value.getValue(), zzfl, zzfg, zzja));
        }
        return arrayList;
    }

    public final zzhi zzb(zzhh zzhh) {
        return zzhh.zzek() ? zzcj() : (zzhi) this.zzko.get(zzhh.zzen());
    }

    public final boolean zzc(zzhh zzhh) {
        return zzb(zzhh) != null;
    }

    public final List<zzhi> zzch() {
        List<zzhi> arrayList = new ArrayList();
        for (Entry value : this.zzko.entrySet()) {
            zzhi zzhi = (zzhi) value.getValue();
            if (!zzhi.zzeo().zzek()) {
                arrayList.add(zzhi);
            }
        }
        return arrayList;
    }

    public final boolean zzci() {
        return zzcj() != null;
    }

    public final zzhi zzcj() {
        for (Entry value : this.zzko.entrySet()) {
            zzhi zzhi = (zzhi) value.getValue();
            if (zzhi.zzeo().zzek()) {
                return zzhi;
            }
        }
        return null;
    }

    public final zzja zzr(zzch zzch) {
        for (zzhi zzhi : this.zzko.values()) {
            if (zzhi.zzr(zzch) != null) {
                return zzhi.zzr(zzch);
            }
        }
        return null;
    }
}
