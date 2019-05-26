package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzcn implements zzbb {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ CompletionListener zzio;

    zzcn(zzck zzck, zzch zzch, CompletionListener completionListener) {
        this.zzil = zzck;
        this.zzgy = zzch;
        this.zzio = completionListener;
    }

    public final void zzb(String str, String str2) {
        DatabaseError zzd = zzck.zzc(str, str2);
        if (zzd == null) {
            this.zzil.zzhw.zzq(this.zzgy);
        }
        this.zzil.zza(this.zzio, zzd, this.zzgy);
    }
}
