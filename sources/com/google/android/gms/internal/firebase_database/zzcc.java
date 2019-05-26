package com.google.android.gms.internal.firebase_database;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Logger.Level;

public final class zzcc extends zzbz {
    public final synchronized void setLogLevel(Level level) {
        zzbm();
        switch (zzcd.zzhn[level.ordinal()]) {
            case 1:
                this.zzhj = zzib.DEBUG;
                return;
            case 2:
                this.zzhj = zzib.INFO;
                return;
            case 3:
                this.zzhj = zzib.WARN;
                return;
            case 4:
                this.zzhj = zzib.ERROR;
                return;
            case 5:
                this.zzhj = zzib.NONE;
                return;
            default:
                String valueOf = String.valueOf(level);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 19);
                stringBuilder.append("Unknown log level: ");
                stringBuilder.append(valueOf);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public final synchronized void setPersistenceCacheSizeBytes(long j) {
        zzbm();
        if (j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            throw new DatabaseException("The minimum cache size must be at least 1MB");
        } else if (j <= 104857600) {
            this.cacheSize = j;
        } else {
            throw new DatabaseException("Firebase Database currently doesn't support a cache size larger than 100MB");
        }
    }

    public final synchronized void setPersistenceEnabled(boolean z) {
        zzbm();
        this.zzcp = z;
    }

    public final synchronized void zza(FirebaseApp firebaseApp) {
        this.zzbd = firebaseApp;
    }

    public final synchronized void zzr(String str) {
        zzbm();
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Session identifier is not allowed to be empty or null!");
        }
        this.zzhi = str;
    }
}
