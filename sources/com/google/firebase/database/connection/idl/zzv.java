package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.firebase_database.zza;
import com.google.android.gms.internal.firebase_database.zzc;
import java.util.List;

public final class zzv extends zza implements zzt {
    zzv(IBinder iBinder) {
        super(iBinder, "com.google.firebase.database.connection.idl.IPersistentConnection");
    }

    public final void compareAndPut(List<String> list, IObjectWrapper iObjectWrapper, String str, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        obtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(9, obtainAndWriteInterfaceToken);
    }

    public final void initialize() throws RemoteException {
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken());
    }

    public final void interrupt(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(14, obtainAndWriteInterfaceToken);
    }

    public final boolean isInterrupted(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        Parcel transactAndReadException = transactAndReadException(16, obtainAndWriteInterfaceToken);
        boolean zza = zzc.zza(transactAndReadException);
        transactAndReadException.recycle();
        return zza;
    }

    public final void listen(List<String> list, IObjectWrapper iObjectWrapper, zzq zzq, long j, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzq);
        obtainAndWriteInterfaceToken.writeLong(j);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken);
    }

    public final void merge(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken);
    }

    public final void onDisconnectCancel(List<String> list, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken);
    }

    public final void onDisconnectMerge(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken);
    }

    public final void onDisconnectPut(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(11, obtainAndWriteInterfaceToken);
    }

    public final void purgeOutstandingWrites() throws RemoteException {
        transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken());
    }

    public final void put(List<String> list, IObjectWrapper iObjectWrapper, zzah zzah) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzah);
        transactAndReadExceptionReturnVoid(8, obtainAndWriteInterfaceToken);
    }

    public final void refreshAuthToken() throws RemoteException {
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken());
    }

    public final void refreshAuthToken2(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(17, obtainAndWriteInterfaceToken);
    }

    public final void resume(String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(15, obtainAndWriteInterfaceToken);
    }

    public final void setup(zzc zzc, zzk zzk, IObjectWrapper iObjectWrapper, zzw zzw) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzc);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzk);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzw);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }

    public final void shutdown() throws RemoteException {
        transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken());
    }

    public final void unlisten(List<String> list, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeStringList(list);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken);
    }
}
