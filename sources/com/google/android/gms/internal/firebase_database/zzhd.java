package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;

final class zzhd implements Runnable {
    private final /* synthetic */ ArrayList zzpf;
    private final /* synthetic */ zzhc zzpg;

    zzhd(zzhc zzhc, ArrayList arrayList) {
        this.zzpg = zzhc;
        this.zzpf = arrayList;
    }

    public final void run() {
        ArrayList arrayList = this.zzpf;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            zzgy zzgy = (zzgy) obj;
            if (this.zzpg.zzbs.zzfa()) {
                zzhz zza = this.zzpg.zzbs;
                String str = "Raising ";
                String valueOf = String.valueOf(zzgy.toString());
                zza.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
            }
            zzgy.zzdr();
        }
    }
}
