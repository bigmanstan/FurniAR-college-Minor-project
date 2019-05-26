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
import com.google.android.gms.internal.firebase_database.zzib;
import java.io.File;
import java.util.List;

@Class(creator = "ConnectionConfigCreator")
@Reserved({1})
public final class zzc extends AbstractSafeParcelable {
    public static final Creator<zzc> CREATOR = new zze();
    @Field(id = 5)
    final boolean zzcp;
    @Field(id = 7)
    final String zzcr;
    @Field(id = 8)
    final String zzcs;
    @Field(id = 2)
    final zzi zzfk;
    @Field(id = 3)
    final int zzfl;
    @Field(id = 4)
    final List<String> zzfm;
    @Field(id = 6)
    final String zzfn;

    public zzc(zzah zzah, zzib zzib, List<String> list, boolean z, String str, String str2, File file) {
        int i;
        switch (zzd.zzfo[zzib.ordinal()]) {
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            case 3:
                i = 3;
                break;
            case 4:
                i = 4;
                break;
            default:
                i = 0;
                break;
        }
        this.zzfk = new zzi(zzah.getHost(), zzah.getNamespace(), zzah.isSecure());
        this.zzfl = i;
        this.zzfm = null;
        this.zzcp = z;
        this.zzfn = str;
        this.zzcr = str2;
        this.zzcs = file.getAbsolutePath();
    }

    @Constructor
    public zzc(@Param(id = 2) zzi zzi, @Param(id = 3) int i, @Param(id = 4) List<String> list, @Param(id = 5) boolean z, @Param(id = 6) String str, @Param(id = 7) String str2, @Param(id = 8) String str3) {
        this.zzfk = zzi;
        this.zzfl = i;
        this.zzfm = list;
        this.zzcp = z;
        this.zzfn = str;
        this.zzcr = str2;
        this.zzcs = str3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzfk, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzfl);
        SafeParcelWriter.writeStringList(parcel, 4, this.zzfm, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzcp);
        SafeParcelWriter.writeString(parcel, 6, this.zzfn, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzcr, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzcs, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
