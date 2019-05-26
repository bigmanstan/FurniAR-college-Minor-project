package com.google.android.gms.internal.firebase_database;

final class zzdd implements Runnable {
    private final /* synthetic */ zzhh zziz;
    private final /* synthetic */ zzet zzja;
    private final /* synthetic */ zzdc zzjb;

    zzdd(zzdc zzdc, zzhh zzhh, zzet zzet) {
        this.zzjb = zzdc;
        this.zziz = zzhh;
        this.zzja = zzet;
    }

    public final void run() {
        zzja zzp = this.zzjb.zzil.zzhv.zzp(this.zziz.zzg());
        if (!zzp.isEmpty()) {
            this.zzjb.zzil.zzc(this.zzjb.zzil.zzig.zzi(this.zziz.zzg(), zzp));
            this.zzja.zzb(null);
        }
    }
}
