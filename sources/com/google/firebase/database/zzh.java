package com.google.firebase.database;

import com.google.android.gms.internal.firebase_database.zzch;
import com.google.android.gms.internal.firebase_database.zzck;
import com.google.android.gms.internal.firebase_database.zzit;
import com.google.android.gms.internal.firebase_database.zzja;

public final class zzh {
    public static DataSnapshot zza(DatabaseReference databaseReference, zzit zzit) {
        return new DataSnapshot(databaseReference, zzit);
    }

    public static DatabaseReference zza(zzck zzck, zzch zzch) {
        return new DatabaseReference(zzck, zzch);
    }

    public static MutableData zza(zzja zzja) {
        return new MutableData(zzja);
    }
}
