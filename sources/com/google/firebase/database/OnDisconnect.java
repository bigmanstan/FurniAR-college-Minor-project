package com.google.firebase.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.firebase_database.zzch;
import com.google.android.gms.internal.firebase_database.zzck;
import com.google.android.gms.internal.firebase_database.zzfb;
import com.google.android.gms.internal.firebase_database.zzir;
import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzjd;
import com.google.android.gms.internal.firebase_database.zzjg;
import com.google.android.gms.internal.firebase_database.zzkn;
import com.google.android.gms.internal.firebase_database.zzkq;
import com.google.android.gms.internal.firebase_database.zzks;
import com.google.android.gms.internal.firebase_database.zzkt;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import java.util.Map;

public class OnDisconnect {
    private zzck zzai;
    private zzch zzap;

    OnDisconnect(zzck zzck, zzch zzch) {
        this.zzai = zzck;
        this.zzap = zzch;
    }

    private final Task<Void> zza(CompletionListener completionListener) {
        zzkn zzb = zzkq.zzb(completionListener);
        this.zzai.zzc(new zzo(this, zzb));
        return (Task) zzb.getFirst();
    }

    private final Task<Void> zza(Map<String, Object> map, CompletionListener completionListener) {
        Map zzb = zzks.zzb(this.zzap, map);
        zzkn zzb2 = zzkq.zzb(completionListener);
        this.zzai.zzc(new zzn(this, zzb, zzb2, map));
        return (Task) zzb2.getFirst();
    }

    private final Task<Void> zzb(Object obj, zzja zzja, CompletionListener completionListener) {
        zzks.zzan(this.zzap);
        zzfb.zza(this.zzap, obj);
        obj = zzkt.zzh(obj);
        zzks.zzg(obj);
        zzja zza = zzjd.zza(obj, zzja);
        zzkn zzb = zzkq.zzb(completionListener);
        this.zzai.zzc(new zzm(this, zza, zzb));
        return (Task) zzb.getFirst();
    }

    @NonNull
    public Task<Void> cancel() {
        return zza(null);
    }

    public void cancel(@NonNull CompletionListener completionListener) {
        zza(completionListener);
    }

    @NonNull
    public Task<Void> removeValue() {
        return setValue(null);
    }

    public void removeValue(@Nullable CompletionListener completionListener) {
        setValue(null, completionListener);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object obj) {
        return zzb(obj, zzir.zzfv(), null);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object obj, double d) {
        return zzb(obj, zzjg.zzc(this.zzap, Double.valueOf(d)), null);
    }

    @NonNull
    public Task<Void> setValue(@Nullable Object obj, @Nullable String str) {
        return zzb(obj, zzjg.zzc(this.zzap, str), null);
    }

    public void setValue(@Nullable Object obj, double d, @Nullable CompletionListener completionListener) {
        zzb(obj, zzjg.zzc(this.zzap, Double.valueOf(d)), completionListener);
    }

    public void setValue(@Nullable Object obj, @Nullable CompletionListener completionListener) {
        zzb(obj, zzir.zzfv(), completionListener);
    }

    public void setValue(@Nullable Object obj, @Nullable String str, @Nullable CompletionListener completionListener) {
        zzb(obj, zzjg.zzc(this.zzap, str), completionListener);
    }

    public void setValue(@Nullable Object obj, @Nullable Map map, @Nullable CompletionListener completionListener) {
        zzb(obj, zzjg.zzc(this.zzap, map), completionListener);
    }

    @NonNull
    public Task<Void> updateChildren(@NonNull Map<String, Object> map) {
        return zza(map, null);
    }

    public void updateChildren(@NonNull Map<String, Object> map, @Nullable CompletionListener completionListener) {
        zza(map, completionListener);
    }
}
