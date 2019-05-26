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
import com.google.android.gms.internal.firebase_database.zzba;
import java.util.List;

@Class(creator = "RangeParcelableCreator")
@Reserved({1})
final class zzak extends AbstractSafeParcelable {
    public static final Creator<zzak> CREATOR = new zzal();
    @Field(id = 2)
    private final List<String> zzes;
    @Field(id = 3)
    private final List<String> zzet;

    @Constructor
    public zzak(@Param(id = 2) List<String> list, @Param(id = 3) List<String> list2) {
        this.zzes = list;
        this.zzet = list2;
    }

    public static zzba zza(zzak zzak, Object obj) {
        return new zzba(zzak.zzes, zzak.zzet, obj);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringList(parcel, 2, this.zzes, false);
        SafeParcelWriter.writeStringList(parcel, 3, this.zzet, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
