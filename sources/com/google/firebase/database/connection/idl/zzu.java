package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;
import com.google.android.gms.internal.firebase_database.zzb;
import com.google.android.gms.internal.firebase_database.zzc;
import java.util.List;

public abstract class zzu extends zzb implements zzt {
    public zzu() {
        super("com.google.firebase.database.connection.idl.IPersistentConnection");
    }

    public static zzt asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IPersistentConnection");
        return queryLocalInterface instanceof zzt ? (zzt) queryLocalInterface : new zzv(iBinder);
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzw zzw = null;
        IBinder readStrongBinder;
        IInterface queryLocalInterface;
        zzah zzaj;
        List createStringArrayList;
        IObjectWrapper asInterface;
        switch (i) {
            case 1:
                zzk zzk;
                zzc zzc = (zzc) zzc.zza(parcel, zzc.CREATOR);
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 == null) {
                    zzk = null;
                } else {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.google.firebase.database.connection.idl.IConnectionAuthTokenProvider");
                    zzk = queryLocalInterface2 instanceof zzk ? (zzk) queryLocalInterface2 : new zzm(readStrongBinder2);
                }
                IObjectWrapper asInterface2 = Stub.asInterface(parcel.readStrongBinder());
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IPersistentConnectionDelegate");
                    zzw = queryLocalInterface instanceof zzw ? (zzw) queryLocalInterface : new zzy(readStrongBinder);
                }
                setup(zzc, zzk, asInterface2, zzw);
                break;
            case 2:
                initialize();
                break;
            case 3:
                shutdown();
                break;
            case 4:
                refreshAuthToken();
                break;
            case 5:
                zzq zzq;
                List createStringArrayList2 = parcel.createStringArrayList();
                IObjectWrapper asInterface3 = Stub.asInterface(parcel.readStrongBinder());
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                if (readStrongBinder3 == null) {
                    zzq = null;
                } else {
                    IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.google.firebase.database.connection.idl.IListenHashProvider");
                    zzq = queryLocalInterface3 instanceof zzq ? (zzq) queryLocalInterface3 : new zzs(readStrongBinder3);
                }
                long readLong = parcel.readLong();
                readStrongBinder3 = parcel.readStrongBinder();
                if (readStrongBinder3 != null) {
                    IInterface queryLocalInterface4 = readStrongBinder3.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface4 instanceof zzah ? (zzah) queryLocalInterface4 : new zzaj(readStrongBinder3);
                }
                listen(createStringArrayList2, asInterface3, zzq, readLong, zzaj);
                break;
            case 6:
                unlisten(parcel.createStringArrayList(), Stub.asInterface(parcel.readStrongBinder()));
                break;
            case 7:
                purgeOutstandingWrites();
                break;
            case 8:
                createStringArrayList = parcel.createStringArrayList();
                asInterface = Stub.asInterface(parcel.readStrongBinder());
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface instanceof zzah ? (zzah) queryLocalInterface : new zzaj(readStrongBinder);
                }
                put(createStringArrayList, asInterface, zzaj);
                break;
            case 9:
                createStringArrayList = parcel.createStringArrayList();
                asInterface = Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface instanceof zzah ? (zzah) queryLocalInterface : new zzaj(readStrongBinder);
                }
                compareAndPut(createStringArrayList, asInterface, readString, zzaj);
                break;
            case 10:
                createStringArrayList = parcel.createStringArrayList();
                asInterface = Stub.asInterface(parcel.readStrongBinder());
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface instanceof zzah ? (zzah) queryLocalInterface : new zzaj(readStrongBinder);
                }
                merge(createStringArrayList, asInterface, zzaj);
                break;
            case 11:
                createStringArrayList = parcel.createStringArrayList();
                asInterface = Stub.asInterface(parcel.readStrongBinder());
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface instanceof zzah ? (zzah) queryLocalInterface : new zzaj(readStrongBinder);
                }
                onDisconnectPut(createStringArrayList, asInterface, zzaj);
                break;
            case 12:
                createStringArrayList = parcel.createStringArrayList();
                asInterface = Stub.asInterface(parcel.readStrongBinder());
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface instanceof zzah ? (zzah) queryLocalInterface : new zzaj(readStrongBinder);
                }
                onDisconnectMerge(createStringArrayList, asInterface, zzaj);
                break;
            case 13:
                createStringArrayList = parcel.createStringArrayList();
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IRequestResultCallback");
                    zzaj = queryLocalInterface instanceof zzah ? (zzah) queryLocalInterface : new zzaj(readStrongBinder);
                }
                onDisconnectCancel(createStringArrayList, zzaj);
                break;
            case 14:
                interrupt(parcel.readString());
                break;
            case 15:
                resume(parcel.readString());
                break;
            case 16:
                boolean isInterrupted = isInterrupted(parcel.readString());
                parcel2.writeNoException();
                zzc.writeBoolean(parcel2, isInterrupted);
                break;
            case 17:
                refreshAuthToken2(parcel.readString());
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
