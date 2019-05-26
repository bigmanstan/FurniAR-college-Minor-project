package com.google.android.gms.internal.firebase_database;

import android.os.Handler;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;

final class zzr extends zzkh {
    private final /* synthetic */ zzhz zzbl;
    private final /* synthetic */ zzq zzbm;

    zzr(zzq zzq, zzhz zzhz) {
        this.zzbm = zzq;
        this.zzbl = zzhz;
    }

    public final void zza(Throwable th) {
        String str;
        if (th instanceof OutOfMemoryError) {
            str = "Firebase Database encountered an OutOfMemoryError. You may need to reduce the amount of data you are syncing to the client (e.g. by using queries or syncing a deeper path). See https://firebase.google.com/docs/database/ios/structure-data#best_practices_for_data_structure and https://firebase.google.com/docs/database/android/retrieve-data#filtering_data";
        } else if (th instanceof DatabaseException) {
            str = "";
        } else {
            str = FirebaseDatabase.getSdkVersion();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 104);
            stringBuilder.append("Uncaught exception in Firebase Database runloop (");
            stringBuilder.append(str);
            stringBuilder.append("). Please report to firebase-database-client@google.com");
            str = stringBuilder.toString();
        }
        this.zzbl.zza(str, th);
        new Handler(this.zzbm.zzbj.getMainLooper()).post(new zzs(this, str, th));
        zzs().shutdownNow();
    }
}
