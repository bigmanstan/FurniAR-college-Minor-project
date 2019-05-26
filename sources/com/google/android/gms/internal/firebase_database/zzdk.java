package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference.CompletionListener;

final class zzdk implements zzbb {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ CompletionListener zzio;
    private final /* synthetic */ zzja zzji;

    zzdk(zzck zzck, zzch zzch, zzja zzja, CompletionListener completionListener) {
        this.zzil = zzck;
        this.zzgy = zzch;
        this.zzji = zzja;
        this.zzio = completionListener;
    }

    public final void zzb(String str, String str2) {
        DatabaseError zzd = zzck.zzc(str, str2);
        this.zzil.zza("onDisconnect().setValue", this.zzgy, zzd);
        if (zzd == null) {
            this.zzil.zzhw.zzh(this.zzgy, this.zzji);
        }
        this.zzil.zza(this.zzio, zzd, this.zzgy);
    }
}
