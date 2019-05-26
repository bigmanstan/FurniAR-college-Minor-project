package com.google.android.gms.internal.firebase_database;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.connection.idl.zzc;
import com.google.firebase.database.connection.idl.zzf;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

public final class zzq implements zzcj {
    private final FirebaseApp zzbd;
    private final Context zzbj;
    private final Set<String> zzbk = new HashSet();

    public zzq(FirebaseApp firebaseApp) {
        this.zzbd = firebaseApp;
        if (this.zzbd != null) {
            this.zzbj = this.zzbd.getApplicationContext();
            return;
        }
        Log.e("FirebaseDatabase", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.e("FirebaseDatabase", "ERROR: You must call FirebaseApp.initializeApp() before using Firebase Database.");
        Log.e("FirebaseDatabase", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        throw new RuntimeException("You need to call FirebaseApp.initializeApp() before using Firebase Database.");
    }

    public final zzaj zza(zzbz zzbz, zzaf zzaf, zzah zzah, zzak zzak) {
        zzaj zza = zzf.zza(this.zzbj, new zzc(zzah, zzbz.zzbn(), null, zzbz.zzt(), FirebaseDatabase.getSdkVersion(), zzbz.zzv(), zzi()), zzaf, zzak);
        this.zzbd.addBackgroundStateChangeListener(new zzt(this, zza));
        return zza;
    }

    public final zzbq zza(ScheduledExecutorService scheduledExecutorService) {
        return new zzk(this.zzbd, scheduledExecutorService);
    }

    public final zzcg zza(zzbz zzbz) {
        return new zzp();
    }

    public final zzfv zza(zzbz zzbz, String str) {
        String zzbq = zzbz.zzbq();
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(zzbq).length());
        stringBuilder.append(str);
        stringBuilder.append("_");
        stringBuilder.append(zzbq);
        str = stringBuilder.toString();
        if (this.zzbk.contains(str)) {
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(zzbq).length() + 47);
            stringBuilder2.append("SessionPersistenceKey '");
            stringBuilder2.append(zzbq);
            stringBuilder2.append("' has already been used.");
            throw new DatabaseException(stringBuilder2.toString());
        }
        this.zzbk.add(str);
        return new zzfs(zzbz, new zzu(this.zzbj, zzbz, str), new zzft(zzbz.zzbo()));
    }

    public final zzia zza(zzbz zzbz, zzib zzib, List<String> list) {
        return new zzhw(zzib, null);
    }

    public final zzdt zzb(zzbz zzbz) {
        return new zzr(this, zzbz.zzp("RunLoop"));
    }

    public final String zzc(zzbz zzbz) {
        int i = VERSION.SDK_INT;
        StringBuilder stringBuilder = new StringBuilder(19);
        stringBuilder.append(i);
        stringBuilder.append("/Android");
        return stringBuilder.toString();
    }

    public final File zzi() {
        return this.zzbj.getApplicationContext().getDir("sslcache", 0);
    }
}
