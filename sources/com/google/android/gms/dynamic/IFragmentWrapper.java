package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

public interface IFragmentWrapper extends IInterface {

    public static abstract class Stub extends zzb implements IFragmentWrapper {

        public static class Proxy extends zza implements IFragmentWrapper {
            Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.dynamic.IFragmentWrapper");
            }

            public IObjectWrapper getActivity() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(2, obtainAndWriteInterfaceToken());
                IObjectWrapper asInterface = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return asInterface;
            }

            public Bundle getArguments() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(3, obtainAndWriteInterfaceToken());
                Bundle bundle = (Bundle) zzc.zza(transactAndReadException, Bundle.CREATOR);
                transactAndReadException.recycle();
                return bundle;
            }

            public int getId() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(4, obtainAndWriteInterfaceToken());
                int readInt = transactAndReadException.readInt();
                transactAndReadException.recycle();
                return readInt;
            }

            public IFragmentWrapper getParentFragment() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(5, obtainAndWriteInterfaceToken());
                IFragmentWrapper asInterface = Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return asInterface;
            }

            public IObjectWrapper getResources() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(6, obtainAndWriteInterfaceToken());
                IObjectWrapper asInterface = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return asInterface;
            }

            public boolean getRetainInstance() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(7, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public String getTag() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(8, obtainAndWriteInterfaceToken());
                String readString = transactAndReadException.readString();
                transactAndReadException.recycle();
                return readString;
            }

            public IFragmentWrapper getTargetFragment() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(9, obtainAndWriteInterfaceToken());
                IFragmentWrapper asInterface = Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return asInterface;
            }

            public int getTargetRequestCode() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(10, obtainAndWriteInterfaceToken());
                int readInt = transactAndReadException.readInt();
                transactAndReadException.recycle();
                return readInt;
            }

            public boolean getUserVisibleHint() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(11, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public IObjectWrapper getView() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(12, obtainAndWriteInterfaceToken());
                IObjectWrapper asInterface = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(transactAndReadException.readStrongBinder());
                transactAndReadException.recycle();
                return asInterface;
            }

            public boolean isAdded() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(13, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public boolean isDetached() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(14, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public boolean isHidden() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(15, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public boolean isInLayout() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(16, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public boolean isRemoving() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(17, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public boolean isResumed() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(18, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public boolean isVisible() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(19, obtainAndWriteInterfaceToken());
                boolean zza = zzc.zza(transactAndReadException);
                transactAndReadException.recycle();
                return zza;
            }

            public void registerForContextMenu(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
                transactAndReadExceptionReturnVoid(20, obtainAndWriteInterfaceToken);
            }

            public void setHasOptionsMenu(boolean z) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(21, obtainAndWriteInterfaceToken);
            }

            public void setMenuVisibility(boolean z) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(22, obtainAndWriteInterfaceToken);
            }

            public void setRetainInstance(boolean z) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(23, obtainAndWriteInterfaceToken);
            }

            public void setUserVisibleHint(boolean z) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, z);
                transactAndReadExceptionReturnVoid(24, obtainAndWriteInterfaceToken);
            }

            public void startActivity(Intent intent) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) intent);
                transactAndReadExceptionReturnVoid(25, obtainAndWriteInterfaceToken);
            }

            public void startActivityForResult(Intent intent, int i) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) intent);
                obtainAndWriteInterfaceToken.writeInt(i);
                transactAndReadExceptionReturnVoid(26, obtainAndWriteInterfaceToken);
            }

            public void unregisterForContextMenu(IObjectWrapper iObjectWrapper) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
                transactAndReadExceptionReturnVoid(27, obtainAndWriteInterfaceToken);
            }
        }

        public Stub() {
            super("com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static IFragmentWrapper asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            return queryLocalInterface instanceof IFragmentWrapper ? (IFragmentWrapper) queryLocalInterface : new Proxy(iBinder);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected boolean dispatchTransaction(int r1, android.os.Parcel r2, android.os.Parcel r3, int r4) throws android.os.RemoteException {
            /*
            r0 = this;
            switch(r1) {
                case 2: goto L_0x00ca;
                case 3: goto L_0x00bf;
                case 4: goto L_0x00b4;
                case 5: goto L_0x00af;
                case 6: goto L_0x00aa;
                case 7: goto L_0x009f;
                case 8: goto L_0x0094;
                case 9: goto L_0x008f;
                case 10: goto L_0x008a;
                case 11: goto L_0x0085;
                case 12: goto L_0x0080;
                case 13: goto L_0x007b;
                case 14: goto L_0x0076;
                case 15: goto L_0x0071;
                case 16: goto L_0x006c;
                case 17: goto L_0x0067;
                case 18: goto L_0x0062;
                case 19: goto L_0x005d;
                case 20: goto L_0x004d;
                case 21: goto L_0x0045;
                case 22: goto L_0x003d;
                case 23: goto L_0x0035;
                case 24: goto L_0x002d;
                case 25: goto L_0x0021;
                case 26: goto L_0x0011;
                case 27: goto L_0x0005;
                default: goto L_0x0003;
            };
        L_0x0003:
            r1 = 0;
            return r1;
        L_0x0005:
            r1 = r2.readStrongBinder();
            r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1);
            r0.unregisterForContextMenu(r1);
            goto L_0x0058;
        L_0x0011:
            r1 = android.content.Intent.CREATOR;
            r1 = com.google.android.gms.internal.stable.zzc.zza(r2, r1);
            r1 = (android.content.Intent) r1;
            r2 = r2.readInt();
            r0.startActivityForResult(r1, r2);
            goto L_0x0058;
        L_0x0021:
            r1 = android.content.Intent.CREATOR;
            r1 = com.google.android.gms.internal.stable.zzc.zza(r2, r1);
            r1 = (android.content.Intent) r1;
            r0.startActivity(r1);
            goto L_0x0058;
        L_0x002d:
            r1 = com.google.android.gms.internal.stable.zzc.zza(r2);
            r0.setUserVisibleHint(r1);
            goto L_0x0058;
        L_0x0035:
            r1 = com.google.android.gms.internal.stable.zzc.zza(r2);
            r0.setRetainInstance(r1);
            goto L_0x0058;
        L_0x003d:
            r1 = com.google.android.gms.internal.stable.zzc.zza(r2);
            r0.setMenuVisibility(r1);
            goto L_0x0058;
        L_0x0045:
            r1 = com.google.android.gms.internal.stable.zzc.zza(r2);
            r0.setHasOptionsMenu(r1);
            goto L_0x0058;
        L_0x004d:
            r1 = r2.readStrongBinder();
            r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1);
            r0.registerForContextMenu(r1);
        L_0x0058:
            r3.writeNoException();
            goto L_0x00d4;
        L_0x005d:
            r1 = r0.isVisible();
            goto L_0x00a3;
        L_0x0062:
            r1 = r0.isResumed();
            goto L_0x00a3;
        L_0x0067:
            r1 = r0.isRemoving();
            goto L_0x00a3;
        L_0x006c:
            r1 = r0.isInLayout();
            goto L_0x00a3;
        L_0x0071:
            r1 = r0.isHidden();
            goto L_0x00a3;
        L_0x0076:
            r1 = r0.isDetached();
            goto L_0x00a3;
        L_0x007b:
            r1 = r0.isAdded();
            goto L_0x00a3;
        L_0x0080:
            r1 = r0.getView();
            goto L_0x00ce;
        L_0x0085:
            r1 = r0.getUserVisibleHint();
            goto L_0x00a3;
        L_0x008a:
            r1 = r0.getTargetRequestCode();
            goto L_0x00b8;
        L_0x008f:
            r1 = r0.getTargetFragment();
            goto L_0x00ce;
        L_0x0094:
            r1 = r0.getTag();
            r3.writeNoException();
            r3.writeString(r1);
            goto L_0x00d4;
        L_0x009f:
            r1 = r0.getRetainInstance();
        L_0x00a3:
            r3.writeNoException();
            com.google.android.gms.internal.stable.zzc.zza(r3, r1);
            goto L_0x00d4;
        L_0x00aa:
            r1 = r0.getResources();
            goto L_0x00ce;
        L_0x00af:
            r1 = r0.getParentFragment();
            goto L_0x00ce;
        L_0x00b4:
            r1 = r0.getId();
        L_0x00b8:
            r3.writeNoException();
            r3.writeInt(r1);
            goto L_0x00d4;
        L_0x00bf:
            r1 = r0.getArguments();
            r3.writeNoException();
            com.google.android.gms.internal.stable.zzc.zzb(r3, r1);
            goto L_0x00d4;
        L_0x00ca:
            r1 = r0.getActivity();
        L_0x00ce:
            r3.writeNoException();
            com.google.android.gms.internal.stable.zzc.zza(r3, r1);
        L_0x00d4:
            r1 = 1;
            return r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamic.IFragmentWrapper.Stub.dispatchTransaction(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    IObjectWrapper getActivity() throws RemoteException;

    Bundle getArguments() throws RemoteException;

    int getId() throws RemoteException;

    IFragmentWrapper getParentFragment() throws RemoteException;

    IObjectWrapper getResources() throws RemoteException;

    boolean getRetainInstance() throws RemoteException;

    String getTag() throws RemoteException;

    IFragmentWrapper getTargetFragment() throws RemoteException;

    int getTargetRequestCode() throws RemoteException;

    boolean getUserVisibleHint() throws RemoteException;

    IObjectWrapper getView() throws RemoteException;

    boolean isAdded() throws RemoteException;

    boolean isDetached() throws RemoteException;

    boolean isHidden() throws RemoteException;

    boolean isInLayout() throws RemoteException;

    boolean isRemoving() throws RemoteException;

    boolean isResumed() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void registerForContextMenu(IObjectWrapper iObjectWrapper) throws RemoteException;

    void setHasOptionsMenu(boolean z) throws RemoteException;

    void setMenuVisibility(boolean z) throws RemoteException;

    void setRetainInstance(boolean z) throws RemoteException;

    void setUserVisibleHint(boolean z) throws RemoteException;

    void startActivity(Intent intent) throws RemoteException;

    void startActivityForResult(Intent intent, int i) throws RemoteException;

    void unregisterForContextMenu(IObjectWrapper iObjectWrapper) throws RemoteException;
}
