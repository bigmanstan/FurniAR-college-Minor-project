package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

final class zzim {
    private StringBuilder zzrk = null;
    private Stack<zzid> zzrl = new Stack();
    private int zzrm = -1;
    private int zzrn;
    private boolean zzro = true;
    private final List<zzch> zzrp = new ArrayList();
    private final List<String> zzrq = new ArrayList();
    private final zzio zzrr;

    public zzim(zzio zzio) {
        this.zzrr = zzio;
    }

    private static void zza(StringBuilder stringBuilder, zzid zzid) {
        stringBuilder.append(zzkq.zzz(zzid.zzfg()));
    }

    private final void zzb(zziv<?> zziv) {
        zzfr();
        this.zzrm = this.zzrn;
        this.zzrk.append(zziv.zza(zzjc.V2));
        this.zzro = true;
        if (this.zzrr.zze(this)) {
            zzfu();
        }
    }

    private final zzch zze(int i) {
        zzid[] zzidArr = new zzid[i];
        for (int i2 = 0; i2 < i; i2++) {
            zzidArr[i2] = (zzid) this.zzrl.get(i2);
        }
        return new zzch(zzidArr);
    }

    private final boolean zzfo() {
        return this.zzrk != null;
    }

    private final void zzfr() {
        if (!zzfo()) {
            this.zzrk = new StringBuilder();
            this.zzrk.append("(");
            Iterator it = zze(this.zzrn).iterator();
            while (it.hasNext()) {
                zza(this.zzrk, (zzid) it.next());
                this.zzrk.append(":(");
            }
            this.zzro = false;
        }
    }

    private final void zzfs() {
        this.zzrn--;
        if (zzfo()) {
            this.zzrk.append(")");
        }
        this.zzro = true;
    }

    private final void zzft() {
        zzkq.zza(this.zzrn == 0, "Can't finish hashing in the middle processing a child");
        if (zzfo()) {
            zzfu();
        }
        this.zzrq.add("");
    }

    private final void zzfu() {
        zzkq.zza(zzfo(), "Can't end range without starting a range!");
        for (int i = 0; i < this.zzrn; i++) {
            this.zzrk.append(")");
        }
        this.zzrk.append(")");
        zzch zze = zze(this.zzrm);
        this.zzrq.add(zzkq.zzy(this.zzrk.toString()));
        this.zzrp.add(zze);
        this.zzrk = null;
    }

    private final void zzn(zzid zzid) {
        zzfr();
        if (this.zzro) {
            this.zzrk.append(",");
        }
        zza(this.zzrk, zzid);
        this.zzrk.append(":(");
        if (this.zzrn == this.zzrl.size()) {
            this.zzrl.add(zzid);
        } else {
            this.zzrl.set(this.zzrn, zzid);
        }
        this.zzrn++;
        this.zzro = false;
    }

    public final int zzfp() {
        return this.zzrk.length();
    }

    public final zzch zzfq() {
        return zze(this.zzrn);
    }
}
