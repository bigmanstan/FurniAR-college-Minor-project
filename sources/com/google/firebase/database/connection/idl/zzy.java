package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.firebase_database.zza;
import com.google.android.gms.internal.firebase_database.zzc;
import java.util.List;

public final class zzy extends zza implements zzw {
    zzy(IBinder iBinder) {
        super(iBinder, "com.google.firebase.database.connection.idl.IPersistentConnectionDelegate");
    }

    public final void onDisconnect() throws RemoteException {
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken());
    }

    public final void zza(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken);
    }

    public final void zza(List<String> list, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.writeBoolean(obtainAndWriteInterfaceToken, z);
        obtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }

    public final void zza(List<String> list, List<zzak> list2, IObjectWrapper iObjectWrapper, long j) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        obtainAndWriteInterfaceToken.writeTypedList(list2);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        obtainAndWriteInterfaceToken.writeLong(j);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final void zzaa() throws RemoteException {
        transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken());
    }

    public final void zzb(boolean z) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.writeBoolean(obtainAndWriteInterfaceToken, z);
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken);
    }
}
