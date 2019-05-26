package com.google.firebase.database.connection.idl;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.firebase_database.zzag;
import com.google.android.gms.internal.firebase_database.zzy;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "CompoundHashParcelableCreator")
@Reserved({1})
final class zza extends AbstractSafeParcelable {
    public static final Creator<zza> CREATOR = new zzb();
    @Field(id = 2)
    private final List<String> zzbz;
    @Field(id = 3)
    private final List<String> zzca;

    @Constructor
    public zza(@Param(id = 2) List<String> list, @Param(id = 3) List<String> list2) {
        this.zzbz = list;
        this.zzca = list2;
    }

    public static zzy zza(zza zza) {
        List arrayList = new ArrayList(zza.zzbz.size());
        for (String zzg : zza.zzbz) {
            arrayList.add(zzag.zzg(zzg));
        }
        return new zzy(arrayList, zza.zzca);
    }

    public static zza zza(zzy zzy) {
        List<List> zzo = zzy.zzo();
        List arrayList = new ArrayList(zzo.size());
        for (List zzb : zzo) {
            arrayList.add(zzag.zzb(zzb));
        }
        return new zza(arrayList, zzy.zzp());
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringList(parcel, 2, this.zzbz, false);
        SafeParcelWriter.writeStringList(parcel, 3, this.zzca, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
