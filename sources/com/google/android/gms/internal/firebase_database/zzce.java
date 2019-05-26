package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class zzce {
    private AtomicBoolean zzho = new AtomicBoolean(false);
    private zzcf zzhp;
    private boolean zzhq = false;

    public abstract zzce zza(zzhh zzhh);

    public abstract zzgx zza(zzgw zzgw, zzhh zzhh);

    public final void zza(zzcf zzcf) {
        this.zzhp = zzcf;
    }

    public abstract void zza(zzgx zzgx);

    public abstract void zza(DatabaseError databaseError);

    public abstract boolean zza(zzgz zzgz);

    public abstract zzhh zzbe();

    public final void zzbr() {
        if (this.zzho.compareAndSet(false, true) && this.zzhp != null) {
            this.zzhp.zzd(this);
            this.zzhp = null;
        }
    }

    public final boolean zzbs() {
        return this.zzho.get();
    }

    public abstract boolean zzc(zzce zzce);

    public final void zze(boolean z) {
        this.zzhq = true;
    }
}
