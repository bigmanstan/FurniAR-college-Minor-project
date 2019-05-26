package com.google.firebase.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.firebase_database.zzch;
import com.google.android.gms.internal.firebase_database.zzdx;
import com.google.android.gms.internal.firebase_database.zzfb;
import com.google.android.gms.internal.firebase_database.zzid;
import com.google.android.gms.internal.firebase_database.zzir;
import com.google.android.gms.internal.firebase_database.zzit;
import com.google.android.gms.internal.firebase_database.zzja;
import com.google.android.gms.internal.firebase_database.zzjd;
import com.google.android.gms.internal.firebase_database.zzjg;
import com.google.android.gms.internal.firebase_database.zzks;
import com.google.android.gms.internal.firebase_database.zzkt;

public class MutableData {
    private final zzdx zzal;
    private final zzch zzam;

    private MutableData(zzdx zzdx, zzch zzch) {
        this.zzal = zzdx;
        this.zzam = zzch;
        zzfb.zza(this.zzam, getValue());
    }

    MutableData(zzja zzja) {
        this(new zzdx(zzja), new zzch(""));
    }

    @NonNull
    public MutableData child(@NonNull String str) {
        zzks.zzab(str);
        return new MutableData(this.zzal, this.zzam.zzh(new zzch(str)));
    }

    public boolean equals(Object obj) {
        if (obj instanceof MutableData) {
            MutableData mutableData = (MutableData) obj;
            if (this.zzal.equals(mutableData.zzal) && this.zzam.equals(mutableData.zzam)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public Iterable<MutableData> getChildren() {
        zzja zzd = zzd();
        if (!zzd.isEmpty()) {
            if (!zzd.zzfk()) {
                return new zzk(this, zzit.zzj(zzd).iterator());
            }
        }
        return new zzi(this);
    }

    public long getChildrenCount() {
        return (long) zzd().getChildCount();
    }

    @Nullable
    public String getKey() {
        return this.zzam.zzbz() != null ? this.zzam.zzbz().zzfg() : null;
    }

    @Nullable
    public Object getPriority() {
        return zzd().zzfl().getValue();
    }

    @Nullable
    public Object getValue() {
        return zzd().getValue();
    }

    @Nullable
    public <T> T getValue(@NonNull GenericTypeIndicator<T> genericTypeIndicator) {
        return zzkt.zza(zzd().getValue(), (GenericTypeIndicator) genericTypeIndicator);
    }

    @Nullable
    public <T> T getValue(@NonNull Class<T> cls) {
        return zzkt.zza(zzd().getValue(), (Class) cls);
    }

    public boolean hasChild(String str) {
        return !zzd().zzam(new zzch(str)).isEmpty();
    }

    public boolean hasChildren() {
        zzja zzd = zzd();
        return (zzd.zzfk() || zzd.isEmpty()) ? false : true;
    }

    public void setPriority(@Nullable Object obj) {
        this.zzal.zzg(this.zzam, zzd().zzf(zzjg.zzc(this.zzam, obj)));
    }

    public void setValue(@Nullable Object obj) throws DatabaseException {
        zzfb.zza(this.zzam, obj);
        obj = zzkt.zzh(obj);
        zzks.zzg(obj);
        this.zzal.zzg(this.zzam, zzjd.zza(obj, zzir.zzfv()));
    }

    public String toString() {
        zzid zzbw = this.zzam.zzbw();
        String zzfg = zzbw != null ? zzbw.zzfg() : "<none>";
        String valueOf = String.valueOf(this.zzal.zzcg().getValue(true));
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzfg).length() + 32) + String.valueOf(valueOf).length());
        stringBuilder.append("MutableData { key = ");
        stringBuilder.append(zzfg);
        stringBuilder.append(", value = ");
        stringBuilder.append(valueOf);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    final zzja zzd() {
        return this.zzal.zzp(this.zzam);
    }
}
