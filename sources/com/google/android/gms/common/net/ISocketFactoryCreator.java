package com.google.android.gms.common.net;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

public interface ISocketFactoryCreator extends IInterface {

    public static abstract class Stub extends zzb implements ISocketFactoryCreator {

        public static class Proxy extends zza implements ISocketFactoryCreator {
            Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.common.net.ISocketFactoryCreator");
            }

            public IObjectWrapper newSocketFactory(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, boolean z) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper2);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper3);
                zzc.zza(obtainAndWriteInterfaceToken, z);
                Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken);
                iObjectWrapper2 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return iObjectWrapper2;
            }

            public IObjectWrapper newSocketFactoryWithCacheDir(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, String str) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper2);
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper3);
                obtainAndWriteInterfaceToken.writeString(str);
                Parcel transactAndReadException = transactAndReadException(2, obtainAndWriteInterfaceToken);
                iObjectWrapper2 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return iObjectWrapper2;
            }
        }

        public Stub() {
            super("com.google.android.gms.common.net.ISocketFactoryCreator");
        }

        public static ISocketFactoryCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.net.ISocketFactoryCreator");
            return queryLocalInterface instanceof ISocketFactoryCreator ? (ISocketFactoryCreator) queryLocalInterface : new Proxy(iBinder);
        }

        protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            IInterface newSocketFactory;
            switch (i) {
                case 1:
                    newSocketFactory = newSocketFactory(com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), zzc.zza(parcel));
                    break;
                case 2:
                    newSocketFactory = newSocketFactoryWithCacheDir(com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                    break;
                default:
                    return false;
            }
            parcel2.writeNoException();
            zzc.zza(parcel2, newSocketFactory);
            return true;
        }
    }

    IObjectWrapper newSocketFactory(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, boolean z) throws RemoteException;

    IObjectWrapper newSocketFactoryWithCacheDir(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, String str) throws RemoteException;
}
