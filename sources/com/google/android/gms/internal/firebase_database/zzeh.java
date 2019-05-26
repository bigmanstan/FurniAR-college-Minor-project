package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

final class zzeh implements Callable<List<? extends zzgy>> {
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzce zzle;

    zzeh(zzee zzee, zzce zzce) {
        this.zzlb = zzee;
        this.zzle = zzce;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzgu zzgu;
        zzhh zzbe = this.zzle.zzbe();
        zzch zzg = zzbe.zzg();
        zzgj zzd = this.zzlb.zzkq;
        zzja zzja = null;
        zzch zzch = zzg;
        boolean z = false;
        while (true) {
            boolean z2 = true;
            if (zzd.isEmpty()) {
                break;
            }
            zzed zzed = (zzed) zzd.getValue();
            if (zzed != null) {
                if (zzja == null) {
                    zzja = zzed.zzr(zzch);
                }
                if (!z) {
                    if (!zzed.zzci()) {
                        z2 = false;
                    }
                }
                z = z2;
            }
            zzd = zzd.zze(zzch.isEmpty() ? zzid.zzt("") : zzch.zzbw());
            zzch = zzch.zzbx();
        }
        zzed zzed2 = (zzed) this.zzlb.zzkq.zzai(zzg);
        if (zzed2 == null) {
            zzed2 = new zzed(this.zzlb.zzkp);
            this.zzlb.zzkq = this.zzlb.zzkq.zzb(zzg, (Object) zzed2);
        } else {
            if (!z) {
                if (!zzed2.zzci()) {
                    z = false;
                    if (zzja != null) {
                        zzja = zzed2.zzr(zzch.zzbt());
                    }
                }
            }
            z = true;
            if (zzja != null) {
                zzja = zzed2.zzr(zzch.zzbt());
            }
        }
        this.zzlb.zzkp.zzg(zzbe);
        if (zzja != null) {
            zzgu = new zzgu(zzit.zza(zzja, zzbe.zzeg()), true, false);
        } else {
            zzgu = this.zzlb.zzkp.zzf(zzbe);
            if (!zzgu.zzdo()) {
                zzja = zzir.zzfv();
                Iterator it = this.zzlb.zzkq.zzag(zzg).zzdm().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    zzed zzed3 = (zzed) ((zzgj) entry.getValue()).getValue();
                    if (zzed3 != null) {
                        zzja zzr = zzed3.zzr(zzch.zzbt());
                        if (zzr != null) {
                            zzja = zzja.zze((zzid) entry.getKey(), zzr);
                        }
                    }
                }
                for (zziz zziz : zzgu.zzd()) {
                    if (!zzja.zzk(zziz.zzge())) {
                        zzja = zzja.zze(zziz.zzge(), zziz.zzd());
                    }
                }
                zzgu = new zzgu(zzit.zza(zzja, zzbe.zzeg()), false, false);
            }
        }
        boolean zzc = zzed2.zzc(zzbe);
        if (!(zzc || zzbe.zzek())) {
            zzex zzf = this.zzlb.zzcl();
            this.zzlb.zzkt.put(zzbe, zzf);
            this.zzlb.zzks.put(zzf, zzbe);
        }
        List zza = zzed2.zza(this.zzle, this.zzlb.zzkr.zzt(zzg), zzgu);
        if (!(zzc || r5)) {
            this.zzlb.zza(zzbe, zzed2.zzb(zzbe));
        }
        return zza;
    }
}
