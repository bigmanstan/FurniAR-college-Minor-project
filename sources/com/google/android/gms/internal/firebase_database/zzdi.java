package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzdi implements zzbb {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ CompletionListener zzio;
    private final /* synthetic */ long zzjh;

    zzdi(zzck zzck, zzch zzch, long j, CompletionListener completionListener) {
        this.zzil = zzck;
        this.zzgy = zzch;
        this.zzjh = j;
        this.zzio = completionListener;
    }

    public final void zzb(String str, String str2) {
        DatabaseError zzd = zzck.zzc(str, str2);
        this.zzil.zza("setValue", this.zzgy, zzd);
        this.zzil.zza(this.zzjh, this.zzgy, zzd);
        this.zzil.zza(this.zzio, zzd, this.zzgy);
    }
}
