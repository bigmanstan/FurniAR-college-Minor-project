package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class zzfd {
    private static final zzgn<zzfa> zzmc = new zzff();
    private zzbv zzlz = zzbv.zzbf();
    private List<zzfa> zzma = new ArrayList();
    private Long zzmb = Long.valueOf(-1);

    private static zzbv zza(List<zzfa> list, zzgn<zzfa> zzgn, zzch zzch) {
        zzbv zzbf = zzbv.zzbf();
        for (zzfa zzfa : list) {
            if (zzgn.zzd(zzfa)) {
                zzja zzf;
                zzch zzg = zzfa.zzg();
                if (!zzfa.zzcq()) {
                    if (zzch.zzi(zzg)) {
                        zzg = zzch.zza(zzch, zzg);
                    } else if (zzg.zzi(zzch)) {
                        zzg = zzch.zza(zzg, zzch);
                        if (zzg.isEmpty()) {
                            zzg = zzch.zzbt();
                        } else {
                            zzf = zzfa.zzcp().zzf(zzg);
                            if (zzf != null) {
                                zzg = zzch.zzbt();
                            }
                        }
                    }
                    zzbf = zzbf.zzb(zzg, zzfa.zzcp());
                } else if (zzch.zzi(zzg)) {
                    zzg = zzch.zza(zzch, zzg);
                    zzf = zzfa.zzco();
                } else if (zzg.zzi(zzch)) {
                    zzbf = zzbf.zze(zzch.zzbt(), zzfa.zzco().zzam(zzch.zza(zzg, zzch)));
                }
                zzbf = zzbf.zze(zzg, zzf);
            }
        }
        return zzbf;
    }

    public final zziz zza(zzch zzch, zzja zzja, zziz zziz, boolean z, zzis zzis) {
        zzbv zzg = this.zzlz.zzg(zzch);
        zzja zzf = zzg.zzf(zzch.zzbt());
        zziz zziz2 = null;
        if (zzf == null) {
            if (zzja != null) {
                zzf = zzg.zzb(zzja);
            }
            return zziz2;
        }
        for (zziz zziz3 : r0) {
            if (zzis.zza(zziz3, zziz, z) > 0 && (r1 == null || zzis.zza(zziz3, r1, z) < 0)) {
                zziz2 = zziz3;
            }
        }
        return zziz2;
    }

    public final zzja zza(zzch zzch, zzch zzch2, zzja zzja, zzja zzja2) {
        zzch = zzch.zzh(zzch2);
        if (this.zzlz.zze(zzch)) {
            return null;
        }
        zzbv zzg = this.zzlz.zzg(zzch);
        return zzg.isEmpty() ? zzja2.zzam(zzch2) : zzg.zzb(zzja2.zzam(zzch2));
    }

    public final zzja zza(zzch zzch, zzid zzid, zzgu zzgu) {
        zzch = zzch.zza(zzid);
        zzja zzf = this.zzlz.zzf(zzch);
        return zzf != null ? zzf : zzgu.zzf(zzid) ? this.zzlz.zzg(zzch).zzb(zzgu.zzd().zzm(zzid)) : null;
    }

    public final zzja zza(zzch zzch, zzja zzja, List<Long> list, boolean z) {
        if (!list.isEmpty() || z) {
            zzbv zzg = this.zzlz.zzg(zzch);
            if (!z && zzg.isEmpty()) {
                return zzja;
            }
            if (!z && zzja == null && !zzg.zze(zzch.zzbt())) {
                return null;
            }
            zzbv zza = zza(this.zzma, new zzfe(this, z, list, zzch), zzch);
            if (zzja == null) {
                zzja = zzir.zzfv();
            }
            return zza.zzb(zzja);
        }
        zzja zzf = this.zzlz.zzf(zzch);
        if (zzf != null) {
            return zzf;
        }
        zza = this.zzlz.zzg(zzch);
        if (zza.isEmpty()) {
            return zzja;
        }
        if (zzja == null && !zza.zze(zzch.zzbt())) {
            return null;
        }
        if (zzja == null) {
            zzja = zzir.zzfv();
        }
        return zza.zzb(zzja);
    }

    public final void zza(zzch zzch, zzbv zzbv, Long l) {
        this.zzma.add(new zzfa(l.longValue(), zzch, zzbv));
        this.zzlz = this.zzlz.zzb(zzch, zzbv);
        this.zzmb = l;
    }

    public final void zza(zzch zzch, zzja zzja, Long l, boolean z) {
        this.zzma.add(new zzfa(l.longValue(), zzch, zzja, z));
        if (z) {
            this.zzlz = this.zzlz.zze(zzch, zzja);
        }
        this.zzmb = l;
    }

    public final List<zzfa> zzct() {
        List arrayList = new ArrayList(this.zzma);
        this.zzlz = zzbv.zzbf();
        this.zzma = new ArrayList();
        return arrayList;
    }

    public final zzja zzj(zzch zzch, zzja zzja) {
        zzja zzfv = zzir.zzfv();
        zzja<zziz> zzf = this.zzlz.zzf(zzch);
        if (zzf != null) {
            if (!zzf.zzfk()) {
                for (zziz zziz : zzf) {
                    zzfv = zzfv.zze(zziz.zzge(), zziz.zzd());
                }
            }
            return zzfv;
        }
        zzbv zzg = this.zzlz.zzg(zzch);
        for (zziz zziz2 : zzja) {
            zzfv = zzfv.zze(zziz2.zzge(), zzg.zzg(new zzch(zziz2.zzge())).zzb(zziz2.zzd()));
        }
        for (zziz zziz3 : zzg.zzbh()) {
            zzfv = zzfv.zze(zziz3.zzge(), zziz3.zzd());
        }
        return zzfv;
    }

    public final zzfa zzk(long j) {
        for (zzfa zzfa : this.zzma) {
            if (zzfa.zzcn() == j) {
                return zzfa;
            }
        }
        return null;
    }

    public final boolean zzl(long j) {
        int i = 0;
        for (zzfa zzfa : this.zzma) {
            if (zzfa.zzcn() == j) {
                break;
            }
            i++;
        }
        zzfa zzfa2 = null;
        this.zzma.remove(zzfa2);
        boolean isVisible = zzfa2.isVisible();
        int size = this.zzma.size() - 1;
        boolean z = false;
        while (isVisible && size >= 0) {
            zzfa zzfa3 = (zzfa) this.zzma.get(size);
            if (zzfa3.isVisible()) {
                if (size >= i) {
                    boolean zzi;
                    zzch zzg = zzfa2.zzg();
                    if (zzfa3.zzcq()) {
                        zzi = zzfa3.zzg().zzi(zzg);
                    } else {
                        Iterator it = zzfa3.zzcp().iterator();
                        while (it.hasNext()) {
                            if (zzfa3.zzg().zzh((zzch) ((Entry) it.next()).getKey()).zzi(zzg)) {
                                zzi = true;
                                break;
                            }
                        }
                        zzi = false;
                    }
                    if (zzi) {
                        isVisible = false;
                    }
                }
                if (zzfa2.zzg().zzi(zzfa3.zzg())) {
                    z = true;
                }
            }
            size--;
        }
        if (!isVisible) {
            return false;
        }
        if (z) {
            this.zzlz = zza(this.zzma, zzmc, zzch.zzbt());
            this.zzmb = Long.valueOf(this.zzma.size() > 0 ? ((zzfa) this.zzma.get(this.zzma.size() - 1)).zzcn() : -1);
            return true;
        }
        if (zzfa2.zzcq()) {
            this.zzlz = this.zzlz.zzd(zzfa2.zzg());
        } else {
            Iterator it2 = zzfa2.zzcp().iterator();
            while (it2.hasNext()) {
                this.zzlz = this.zzlz.zzd(zzfa2.zzg().zzh((zzch) ((Entry) it2.next()).getKey()));
            }
        }
        return true;
    }

    public final zzfg zzt(zzch zzch) {
        return new zzfg(zzch, this);
    }

    public final zzja zzu(zzch zzch) {
        return this.zzlz.zzf(zzch);
    }
}
