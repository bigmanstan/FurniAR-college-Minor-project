package com.google.firebase.database.connection.idl;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.List;

public interface zzw extends IInterface {
    void onDisconnect() throws RemoteException;

    void zza(IObjectWrapper iObjectWrapper) throws RemoteException;

    void zza(List<String> list, IObjectWrapper iObjectWrapper, boolean z, long j) throws RemoteException;

    void zza(List<String> list, List<zzak> list2, IObjectWrapper iObjectWrapper, long j) throws RemoteException;

    void zzaa() throws RemoteException;

    void zzb(boolean z) throws RemoteException;
}
