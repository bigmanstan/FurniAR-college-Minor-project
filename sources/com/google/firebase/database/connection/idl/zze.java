package com.google.firebase.database.connection.idl;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zze implements Creator<zzc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        boolean z = i;
        zzi zzi = null;
        List list = zzi;
        String str = list;
        String str2 = str;
        String str3 = str2;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    zzi = (zzi) SafeParcelReader.createParcelable(parcel, readHeader, zzi.CREATOR);
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 4:
                    list = SafeParcelReader.createStringList(parcel, readHeader);
                    break;
                case 5:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 6:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 7:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 8:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzc(zzi, i, list, z, str, str2, str3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzc[i];
    }
}
