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
import com.google.android.gms.internal.firebase_database.zzah;

@Class(creator = "HostInfoParcelableCreator")
@Reserved({1})
final class zzi extends AbstractSafeParcelable {
    public static final Creator<zzi> CREATOR = new zzj();
    @Field(id = 2)
    private final String zzct;
    @Field(id = 3)
    private final String zzcu;
    @Field(id = 4)
    private final boolean zzcv;

    @Constructor
    public zzi(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) boolean z) {
        this.zzct = str;
        this.zzcu = str2;
        this.zzcv = z;
    }

    public static zzah zza(zzi zzi) {
        return new zzah(zzi.zzct, zzi.zzcu, zzi.zzcv);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzct, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzcu, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzcv);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
