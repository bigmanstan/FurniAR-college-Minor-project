package com.google.android.gms.internal.firebase_database;

final class zzam implements Runnable {
    private final /* synthetic */ boolean zzdt;
    final /* synthetic */ zzal zzdu;

    zzam(zzal zzal, boolean z) {
        this.zzdu = zzal;
        this.zzdt = z;
    }

    public final void run() {
        this.zzdu.zzbs.zza("Trying to fetch auth token", null, new Object[0]);
        zzag.zza(this.zzdu.zzdc == zzav.Disconnected, "Not in disconnected state: %s", this.zzdu.zzdc);
        this.zzdu.zzdc = zzav.GettingToken;
        this.zzdu.zzdo = 1 + this.zzdu.zzdo;
        this.zzdu.zzcn.zza(this.zzdt, new zzan(this, this.zzdu.zzdo));
    }
}
