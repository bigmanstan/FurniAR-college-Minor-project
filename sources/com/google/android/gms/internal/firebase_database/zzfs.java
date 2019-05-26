package com.google.android.gms.internal.firebase_database;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

public final class zzfs implements zzfv {
    private final zzhz zzbs;
    private final zzfw zzne;
    private final zzgc zznf;
    private final zzfq zzng;
    private long zznh;

    public zzfs(zzbz zzbz, zzfw zzfw, zzfq zzfq) {
        this(zzbz, zzfw, zzfq, new zzkg());
    }

    private zzfs(zzbz zzbz, zzfw zzfw, zzfq zzfq, zzkf zzkf) {
        this.zznh = 0;
        this.zzne = zzfw;
        this.zzbs = zzbz.zzp("Persistence");
        this.zznf = new zzgc(this.zzne, this.zzbs, zzkf);
        this.zzng = zzfq;
    }

    private final void zzdg() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1997963191.run(Unknown Source)
*/
        /*
        r8 = this;
        r0 = r8.zznh;
        r2 = 1;
        r0 = r0 + r2;
        r8.zznh = r0;
        r0 = r8.zzng;
        r1 = r8.zznh;
        r0 = r0.zzm(r1);
        if (r0 == 0) goto L_0x0094;
    L_0x0011:
        r0 = r8.zzbs;
        r0 = r0.zzfa();
        r1 = 0;
        r2 = 0;
        if (r0 == 0) goto L_0x0024;
    L_0x001b:
        r0 = r8.zzbs;
        r3 = "Reached prune check threshold.";
        r4 = new java.lang.Object[r2];
        r0.zza(r3, r1, r4);
    L_0x0024:
        r3 = 0;
        r8.zznh = r3;
        r0 = 1;
        r3 = r8.zzne;
        r3 = r3.zzk();
        r5 = r8.zzbs;
        r5 = r5.zzfa();
        if (r5 == 0) goto L_0x0051;
    L_0x0037:
        r5 = r8.zzbs;
        r6 = 32;
        r7 = new java.lang.StringBuilder;
        r7.<init>(r6);
        r6 = "Cache size: ";
    L_0x0042:
        r7.append(r6);
        r7.append(r3);
        r6 = r7.toString();
        r7 = new java.lang.Object[r2];
        r5.zza(r6, r1, r7);
    L_0x0051:
        if (r0 == 0) goto L_0x0094;
    L_0x0053:
        r5 = r8.zzng;
        r6 = r8.zznf;
        r6 = r6.zzdj();
        r3 = r5.zza(r3, r6);
        if (r3 == 0) goto L_0x0094;
    L_0x0061:
        r3 = r8.zznf;
        r4 = r8.zzng;
        r3 = r3.zza(r4);
        r4 = r3.zzdh();
        if (r4 == 0) goto L_0x0079;
    L_0x006f:
        r4 = r8.zzne;
        r5 = com.google.android.gms.internal.firebase_database.zzch.zzbt();
        r4.zza(r5, r3);
        goto L_0x007a;
    L_0x0079:
        r0 = r2;
    L_0x007a:
        r3 = r8.zzne;
        r3 = r3.zzk();
        r5 = r8.zzbs;
        r5 = r5.zzfa();
        if (r5 == 0) goto L_0x0051;
    L_0x0088:
        r5 = r8.zzbs;
        r6 = 44;
        r7 = new java.lang.StringBuilder;
        r7.<init>(r6);
        r6 = "Cache size after prune: ";
        goto L_0x0042;
    L_0x0094:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzfs.zzdg():void");
    }

    public final <T> T zza(Callable<T> callable) {
        this.zzne.beginTransaction();
        try {
            T call = callable.call();
            this.zzne.setTransactionSuccessful();
            this.zzne.endTransaction();
            return call;
        } catch (Throwable th) {
            this.zzne.endTransaction();
        }
    }

    public final void zza(long j) {
        this.zzne.zza(j);
    }

    public final void zza(zzch zzch, zzbv zzbv, long j) {
        this.zzne.zza(zzch, zzbv, j);
    }

    public final void zza(zzch zzch, zzja zzja, long j) {
        this.zzne.zza(zzch, zzja, j);
    }

    public final void zza(zzhh zzhh, zzja zzja) {
        if (zzhh.zzek()) {
            this.zzne.zza(zzhh.zzg(), zzja);
        } else {
            this.zzne.zzb(zzhh.zzg(), zzja);
        }
        zzi(zzhh);
        zzdg();
    }

    public final void zza(zzhh zzhh, Set<zzid> set) {
        this.zzne.zza(this.zznf.zzk(zzhh).id, (Set) set);
    }

    public final void zza(zzhh zzhh, Set<zzid> set, Set<zzid> set2) {
        this.zzne.zza(this.zznf.zzk(zzhh).id, (Set) set, (Set) set2);
    }

    public final void zzc(zzch zzch, zzbv zzbv) {
        Iterator it = zzbv.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzk(zzch.zzh((zzch) entry.getKey()), (zzja) entry.getValue());
        }
    }

    public final void zzd(zzch zzch, zzbv zzbv) {
        this.zzne.zza(zzch, zzbv);
        zzdg();
    }

    public final zzgu zzf(zzhh zzhh) {
        boolean z;
        Set zzd;
        if (this.zznf.zzm(zzhh)) {
            zzgb zzk = this.zznf.zzk(zzhh);
            zzd = (zzhh.zzek() || zzk == null || !zzk.zznr) ? null : this.zzne.zzd(zzk.id);
            z = true;
        } else {
            zzd = this.zznf.zzaa(zzhh.zzg());
            z = false;
        }
        zzja zza = this.zzne.zza(zzhh.zzg());
        if (r0 == null) {
            return new zzgu(zzit.zza(zza, zzhh.zzeg()), true, false);
        }
        zzja zzfv = zzir.zzfv();
        for (zzid zzid : r0) {
            zzfv = zzfv.zze(zzid, zza.zzm(zzid));
        }
        return new zzgu(zzit.zza(zzfv, zzhh.zzeg()), z, true);
    }

    public final void zzg(zzhh zzhh) {
        this.zznf.zzg(zzhh);
    }

    public final void zzh(zzhh zzhh) {
        this.zznf.zzh(zzhh);
    }

    public final void zzi(zzhh zzhh) {
        if (zzhh.zzek()) {
            this.zznf.zzz(zzhh.zzg());
        } else {
            this.zznf.zzl(zzhh);
        }
    }

    public final List<zzfa> zzj() {
        return this.zzne.zzj();
    }

    public final void zzk(zzch zzch, zzja zzja) {
        if (!this.zznf.zzac(zzch)) {
            this.zzne.zza(zzch, zzja);
            this.zznf.zzab(zzch);
        }
    }

    public final void zzm() {
        this.zzne.zzm();
    }
}
