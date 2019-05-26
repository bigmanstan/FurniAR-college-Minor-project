package com.google.firebase.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.firebase_database.zzbt;
import com.google.android.gms.internal.firebase_database.zzce;
import com.google.android.gms.internal.firebase_database.zzch;
import com.google.android.gms.internal.firebase_database.zzck;
import com.google.android.gms.internal.firebase_database.zzfc;
import com.google.android.gms.internal.firebase_database.zzfh;
import com.google.android.gms.internal.firebase_database.zzhe;
import com.google.android.gms.internal.firebase_database.zzhh;
import com.google.android.gms.internal.firebase_database.zzic;
import com.google.android.gms.internal.firebase_database.zzid;
import com.google.android.gms.internal.firebase_database.zziq;
import com.google.android.gms.internal.firebase_database.zzir;
import com.google.android.gms.internal.firebase_database.zziu;
import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzje;
import com.google.android.gms.internal.firebase_database.zzjf;
import com.google.android.gms.internal.firebase_database.zzjg;
import com.google.android.gms.internal.firebase_database.zzji;
import com.google.android.gms.internal.firebase_database.zzjk;
import com.google.android.gms.internal.firebase_database.zzkq;
import com.google.android.gms.internal.firebase_database.zzks;

public class Query {
    protected final zzck zzai;
    protected final zzch zzap;
    private final zzhe zzat;
    private final boolean zzau;

    Query(zzck zzck, zzch zzch) {
        this.zzai = zzck;
        this.zzap = zzch;
        this.zzat = zzhe.zzph;
        this.zzau = false;
    }

    private Query(zzck zzck, zzch zzch, zzhe zzhe, boolean z) throws DatabaseException {
        boolean z2;
        this.zzai = zzck;
        this.zzap = zzch;
        this.zzat = zzhe;
        this.zzau = z;
        if (zzhe.zzdy() && zzhe.zzeb() && zzhe.zzee()) {
            if (!zzhe.zzef()) {
                z2 = false;
                zzkq.zza(z2, "Validation of queries failed.");
            }
        }
        z2 = true;
        zzkq.zza(z2, "Validation of queries failed.");
    }

    private final Query zza(zzja zzja, String str) {
        zzks.zzad(str);
        if (!zzja.zzfk()) {
            if (!zzja.isEmpty()) {
                throw new IllegalArgumentException("Can only use simple values for startAt()");
            }
        }
        if (this.zzat.zzdy()) {
            throw new IllegalArgumentException("Can't call startAt() or equalTo() multiple times");
        }
        zzhe zza = this.zzat.zza(zzja, str != null ? zzid.zzt(str) : null);
        zzb(zza);
        zza(zza);
        return new Query(this.zzai, this.zzap, zza, this.zzau);
    }

    private final void zza(zzce zzce) {
        zzfh.zzcu().zzj(zzce);
        this.zzai.zzc(new zzq(this, zzce));
    }

    private static void zza(zzhe zzhe) {
        if (zzhe.zzeg().equals(zziu.zzgb())) {
            zzja zzdz;
            String str = "You must use startAt(String value), endAt(String value) or equalTo(String value) in combination with orderByKey(). Other type of values or using the version with 2 parameters is not supported";
            if (zzhe.zzdy()) {
                zzdz = zzhe.zzdz();
                if (zzhe.zzea() != zzid.zzfc() || !(zzdz instanceof zzji)) {
                    throw new IllegalArgumentException(str);
                }
            }
            if (zzhe.zzeb()) {
                zzdz = zzhe.zzec();
                if (zzhe.zzed() != zzid.zzfd() || !(zzdz instanceof zzji)) {
                    throw new IllegalArgumentException(str);
                }
            }
            return;
        }
        if (zzhe.zzeg().equals(zzjf.zzgf())) {
            if (!zzhe.zzdy() || zzjg.zzl(zzhe.zzdz())) {
                if (zzhe.zzeb()) {
                    if (zzjg.zzl(zzhe.zzec())) {
                    }
                }
            }
            throw new IllegalArgumentException("When using orderByPriority(), values provided to startAt(), endAt(), or equalTo() must be valid priorities.");
        }
    }

    private final Query zzb(zzja zzja, String str) {
        zzks.zzad(str);
        if (!zzja.zzfk()) {
            if (!zzja.isEmpty()) {
                throw new IllegalArgumentException("Can only use simple values for endAt()");
            }
        }
        zzid zzt = str != null ? zzid.zzt(str) : null;
        if (this.zzat.zzeb()) {
            throw new IllegalArgumentException("Can't call endAt() or equalTo() multiple times");
        }
        zzhe zzb = this.zzat.zzb(zzja, zzt);
        zzb(zzb);
        zza(zzb);
        return new Query(this.zzai, this.zzap, zzb, this.zzau);
    }

    private final void zzb(zzce zzce) {
        zzfh.zzcu().zzi(zzce);
        this.zzai.zzc(new zzr(this, zzce));
    }

    private static void zzb(zzhe zzhe) {
        if (!zzhe.zzdy() || !zzhe.zzeb() || !zzhe.zzee()) {
            return;
        }
        if (!zzhe.zzef()) {
            throw new IllegalArgumentException("Can't combine startAt(), endAt() and limit(). Use limitToFirst() or limitToLast() instead");
        }
    }

    private final void zze() {
        if (this.zzat.zzdy()) {
            throw new IllegalArgumentException("Can't call equalTo() and startAt() combined");
        } else if (this.zzat.zzeb()) {
            throw new IllegalArgumentException("Can't call equalTo() and endAt() combined");
        }
    }

    private final void zzf() {
        if (this.zzau) {
            throw new IllegalArgumentException("You can't combine multiple orderBy calls!");
        }
    }

    @NonNull
    public ChildEventListener addChildEventListener(@NonNull ChildEventListener childEventListener) {
        zzb(new zzbt(this.zzai, childEventListener, zzh()));
        return childEventListener;
    }

    public void addListenerForSingleValueEvent(@NonNull ValueEventListener valueEventListener) {
        zzb(new zzfc(this.zzai, new zzp(this, valueEventListener), zzh()));
    }

    @NonNull
    public ValueEventListener addValueEventListener(@NonNull ValueEventListener valueEventListener) {
        zzb(new zzfc(this.zzai, valueEventListener, zzh()));
        return valueEventListener;
    }

    @NonNull
    public Query endAt(double d) {
        return endAt(d, null);
    }

    @NonNull
    public Query endAt(double d, @Nullable String str) {
        return zzb(new zziq(Double.valueOf(d), zzir.zzfv()), str);
    }

    @NonNull
    public Query endAt(@Nullable String str) {
        return endAt(str, null);
    }

    @NonNull
    public Query endAt(@Nullable String str, @Nullable String str2) {
        return zzb(str != null ? new zzji(str, zzir.zzfv()) : zzir.zzfv(), str2);
    }

    @NonNull
    public Query endAt(boolean z) {
        return endAt(z, null);
    }

    @NonNull
    public Query endAt(boolean z, @Nullable String str) {
        return zzb(new zzic(Boolean.valueOf(z), zzir.zzfv()), str);
    }

    @NonNull
    public Query equalTo(double d) {
        zze();
        return startAt(d).endAt(d);
    }

    @NonNull
    public Query equalTo(double d, @Nullable String str) {
        zze();
        return startAt(d, str).endAt(d, str);
    }

    @NonNull
    public Query equalTo(@Nullable String str) {
        zze();
        return startAt(str).endAt(str);
    }

    @NonNull
    public Query equalTo(@Nullable String str, @Nullable String str2) {
        zze();
        return startAt(str, str2).endAt(str, str2);
    }

    @NonNull
    public Query equalTo(boolean z) {
        zze();
        return startAt(z).endAt(z);
    }

    @NonNull
    public Query equalTo(boolean z, @Nullable String str) {
        zze();
        return startAt(z, str).endAt(z, str);
    }

    @NonNull
    public DatabaseReference getRef() {
        return new DatabaseReference(this.zzai, this.zzap);
    }

    public void keepSynced(boolean z) {
        if (!this.zzap.isEmpty()) {
            if (this.zzap.zzbw().equals(zzid.zzff())) {
                throw new DatabaseException("Can't call keepSynced() on .info paths.");
            }
        }
        this.zzai.zzc(new zzs(this, z));
    }

    @NonNull
    public Query limitToFirst(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer!");
        } else if (!this.zzat.zzee()) {
            return new Query(this.zzai, this.zzap, this.zzat.zzc(i), this.zzau);
        } else {
            throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
        }
    }

    @NonNull
    public Query limitToLast(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer!");
        } else if (!this.zzat.zzee()) {
            return new Query(this.zzai, this.zzap, this.zzat.zzd(i), this.zzau);
        } else {
            throw new IllegalArgumentException("Can't call limitToLast on query with previously set limit!");
        }
    }

    @NonNull
    public Query orderByChild(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Key can't be null");
        } else if (str.equals("$key") || str.equals(".key")) {
            r2 = new StringBuilder(String.valueOf(str).length() + 54);
            r2.append("Can't use '");
            r2.append(str);
            r2.append("' as path, please use orderByKey() instead!");
            throw new IllegalArgumentException(r2.toString());
        } else if (str.equals("$priority") || str.equals(".priority")) {
            r2 = new StringBuilder(String.valueOf(str).length() + 59);
            r2.append("Can't use '");
            r2.append(str);
            r2.append("' as path, please use orderByPriority() instead!");
            throw new IllegalArgumentException(r2.toString());
        } else if (str.equals("$value") || str.equals(".value")) {
            r2 = new StringBuilder(String.valueOf(str).length() + 56);
            r2.append("Can't use '");
            r2.append(str);
            r2.append("' as path, please use orderByValue() instead!");
            throw new IllegalArgumentException(r2.toString());
        } else {
            zzks.zzab(str);
            zzf();
            zzch zzch = new zzch(str);
            if (zzch.size() != 0) {
                return new Query(this.zzai, this.zzap, this.zzat.zza(new zzje(zzch)), true);
            }
            throw new IllegalArgumentException("Can't use empty path, use orderByValue() instead!");
        }
    }

    @NonNull
    public Query orderByKey() {
        zzf();
        zzhe zza = this.zzat.zza(zziu.zzgb());
        zza(zza);
        return new Query(this.zzai, this.zzap, zza, true);
    }

    @NonNull
    public Query orderByPriority() {
        zzf();
        zzhe zza = this.zzat.zza(zzjf.zzgf());
        zza(zza);
        return new Query(this.zzai, this.zzap, zza, true);
    }

    @NonNull
    public Query orderByValue() {
        zzf();
        return new Query(this.zzai, this.zzap, this.zzat.zza(zzjk.zzgg()), true);
    }

    public void removeEventListener(@NonNull ChildEventListener childEventListener) {
        if (childEventListener != null) {
            zza(new zzbt(this.zzai, childEventListener, zzh()));
            return;
        }
        throw new NullPointerException("listener must not be null");
    }

    public void removeEventListener(@NonNull ValueEventListener valueEventListener) {
        if (valueEventListener != null) {
            zza(new zzfc(this.zzai, valueEventListener, zzh()));
            return;
        }
        throw new NullPointerException("listener must not be null");
    }

    @NonNull
    public Query startAt(double d) {
        return startAt(d, null);
    }

    @NonNull
    public Query startAt(double d, @Nullable String str) {
        return zza(new zziq(Double.valueOf(d), zzir.zzfv()), str);
    }

    @NonNull
    public Query startAt(@Nullable String str) {
        return startAt(str, null);
    }

    @NonNull
    public Query startAt(@Nullable String str, @Nullable String str2) {
        return zza(str != null ? new zzji(str, zzir.zzfv()) : zzir.zzfv(), str2);
    }

    @NonNull
    public Query startAt(boolean z) {
        return startAt(z, null);
    }

    @NonNull
    public Query startAt(boolean z, @Nullable String str) {
        return zza(new zzic(Boolean.valueOf(z), zzir.zzfv()), str);
    }

    public final zzch zzg() {
        return this.zzap;
    }

    public final zzhh zzh() {
        return new zzhh(this.zzap, this.zzat);
    }
}
