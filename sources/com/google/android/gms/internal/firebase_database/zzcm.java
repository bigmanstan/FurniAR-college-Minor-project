package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import java.util.Map;
import java.util.Map.Entry;

final class zzcm implements zzbb {
    private final /* synthetic */ zzch zzgy;
    private final /* synthetic */ zzck zzil;
    private final /* synthetic */ Map zzim;
    private final /* synthetic */ CompletionListener zzin;

    zzcm(zzck zzck, zzch zzch, Map map, CompletionListener completionListener) {
        this.zzil = zzck;
        this.zzgy = zzch;
        this.zzim = map;
        this.zzin = completionListener;
    }

    public final void zzb(String str, String str2) {
        DatabaseError zzd = zzck.zzc(str, str2);
        this.zzil.zza("onDisconnect().updateChildren", this.zzgy, zzd);
        if (zzd == null) {
            for (Entry entry : this.zzim.entrySet()) {
                this.zzil.zzhw.zzh(this.zzgy.zzh((zzch) entry.getKey()), (zzja) entry.getValue());
            }
        }
        this.zzil.zza(this.zzin, zzd, this.zzgy);
    }
}
