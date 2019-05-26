package com.google.firebase.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.firebase_database.zzbv;
import com.google.android.gms.internal.firebase_database.zzcc;
import com.google.android.gms.internal.firebase_database.zzch;
import com.google.android.gms.internal.firebase_database.zzck;
import com.google.android.gms.internal.firebase_database.zzdo;
import com.google.android.gms.internal.firebase_database.zzfb;
import com.google.android.gms.internal.firebase_database.zzid;
import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzjd;
import com.google.android.gms.internal.firebase_database.zzjg;
import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.android.gms.internal.firebase_database.zzkp;
import com.google.android.gms.internal.firebase_database.zzkq;
import com.google.android.gms.internal.firebase_database.zzks;
import com.google.android.gms.internal.firebase_database.zzkt;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Transaction.Handler;
import java.net.URLEncoder;
import java.util.Map;

public class DatabaseReference extends Query {
    private static zzcc zzv;

    public interface CompletionListener {
        void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference);
    }

    DatabaseReference(zzck zzck, zzch zzch) {
        super(zzck, zzch);
    }

    public static void goOffline() {
        zzdo.zzd(zzb());
    }

    public static void goOnline() {
        zzdo.zze(zzb());
    }

    private final Task<Void> zza(zzja zzja, CompletionListener completionListener) {
        zzks.zzan(this.zzap);
        zzkn zzb = zzkq.zzb(completionListener);
        this.zzai.zzc(new zzd(this, zzja, zzb));
        return (Task) zzb.getFirst();
    }

    private final Task<Void> zza(Object obj, zzja zzja, CompletionListener completionListener) {
        zzks.zzan(this.zzap);
        zzfb.zza(this.zzap, obj);
        obj = zzkt.zzh(obj);
        zzks.zzg(obj);
        zzja zza = zzjd.zza(obj, zzja);
        zzkn zzb = zzkq.zzb(completionListener);
        this.zzai.zzc(new zzc(this, zza, zzb));
        return (Task) zzb.getFirst();
    }

    private final Task<Void> zza(Map<String, Object> map, CompletionListener completionListener) {
        if (map != null) {
            Map zzi = zzkt.zzi((Map) map);
            zzbv zzg = zzbv.zzg(zzks.zzb(this.zzap, zzi));
            zzkn zzb = zzkq.zzb(completionListener);
            this.zzai.zzc(new zze(this, zzg, zzb, zzi));
            return (Task) zzb.getFirst();
        }
        throw new NullPointerException("Can't pass null for argument 'update' in updateChildren()");
    }

    private static synchronized zzcc zzb() {
        zzcc zzcc;
        synchronized (DatabaseReference.class) {
            if (zzv == null) {
                zzv = new zzcc();
            }
            zzcc = zzv;
        }
        return zzcc;
    }

    @NonNull
    public DatabaseReference child(@NonNull String str) {
        if (str != null) {
            if (this.zzap.isEmpty()) {
                zzks.zzac(str);
            } else {
                zzks.zzab(str);
            }
            return new DatabaseReference(this.zzai, this.zzap.zzh(new zzch(str)));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in child()");
    }

    public boolean equals(Object obj) {
        return (obj instanceof DatabaseReference) && toString().equals(obj.toString());
    }

    @NonNull
    public FirebaseDatabase getDatabase() {
        return this.zzai.getDatabase();
    }

    @Nullable
    public String getKey() {
        return this.zzap.isEmpty() ? null : this.zzap.zzbz().zzfg();
    }

    @Nullable
    public DatabaseReference getParent() {
        zzch zzby = this.zzap.zzby();
        return zzby != null ? new DatabaseReference(this.zzai, zzby) : null;
    }

    @NonNull
    public DatabaseReference getRoot() {
        return new DatabaseReference(this.zzai, new zzch(""));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @NonNull
    public OnDisconnect onDisconnect() {
        zzks.zzan(this.zzap);
        return new OnDisconnect(this.zzai, this.zzap);
    }

    @NonNull
    public DatabaseReference push() {
        return new DatabaseReference(this.zzai, this.zzap.zza(zzid.zzt(zzkp.zzo(this.zzai.zzcc()))));
    }

    @NonNull
    public Task<Void> removeValue() {
        return setValue(null);
    }

    public void removeValue(@Nullable CompletionListener completionListener) {
        setValue(null, completionListener);
    }

    public void runTransaction(@NonNull Handler handler) {
        runTransaction(handler, true);
    }

    public void runTransaction(@NonNull Handler handler, boolean z) {
        if (handler != null) {
            zzks.zzan(this.zzap);
            this.zzai.zzc(new zzf(this, handler, z));
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'handler' in runTransaction()");
    }

    @NonNull
    public Task<Void> setPriority(@Nullable Object obj) {
        return zza(zzjg.zzc(this.zzap, obj), null);
    }

    public void setPriority(@Nullable Object obj, @Nullable CompletionListener completionListener) {
        zza(zzjg.zzc(this.zzap, obj), completionListener);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object obj) {
        return zza(obj, zzjg.zzc(this.zzap, null), null);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object obj, @Nullable Object obj2) {
        return zza(obj, zzjg.zzc(this.zzap, obj2), null);
    }

    public void setValue(@Nullable Object obj, @Nullable CompletionListener completionListener) {
        zza(obj, zzjg.zzc(this.zzap, null), completionListener);
    }

    public void setValue(@Nullable Object obj, @Nullable Object obj2, @Nullable CompletionListener completionListener) {
        zza(obj, zzjg.zzc(this.zzap, obj2), completionListener);
    }

    public String toString() {
        DatabaseReference parent = getParent();
        if (parent == null) {
            return this.zzai.toString();
        }
        try {
            String databaseReference = parent.toString();
            String replace = URLEncoder.encode(getKey(), "UTF-8").replace("+", "%20");
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(databaseReference).length() + 1) + String.valueOf(replace).length());
            stringBuilder.append(databaseReference);
            stringBuilder.append("/");
            stringBuilder.append(replace);
            return stringBuilder.toString();
        } catch (Throwable e) {
            String str = "Failed to URLEncode key: ";
            String valueOf = String.valueOf(getKey());
            throw new DatabaseException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
        }
    }

    @NonNull
    public Task<Void> updateChildren(@NonNull Map<String, Object> map) {
        return zza((Map) map, null);
    }

    public void updateChildren(@NonNull Map<String, Object> map, @Nullable CompletionListener completionListener) {
        zza((Map) map, completionListener);
    }
}
