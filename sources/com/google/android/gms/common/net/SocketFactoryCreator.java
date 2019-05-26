package com.google.android.gms.common.net;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.net.ISocketFactoryCreator.Stub;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.RemoteCreator;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SocketFactoryCreator extends RemoteCreator<ISocketFactoryCreator> {
    private static SocketFactoryCreator zzvr;

    protected SocketFactoryCreator() {
        super("com.google.android.gms.common.net.SocketFactoryCreatorImpl");
    }

    public static SocketFactoryCreator getInstance() {
        if (zzvr == null) {
            zzvr = new SocketFactoryCreator();
        }
        return zzvr;
    }

    protected ISocketFactoryCreator getRemoteCreator(IBinder iBinder) {
        return Stub.asInterface(iBinder);
    }

    public SSLSocketFactory makeSocketFactory(Context context, KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, boolean z) {
        try {
            return (SSLSocketFactory) ObjectWrapper.unwrap(((ISocketFactoryCreator) getRemoteCreatorInstance(context)).newSocketFactory(ObjectWrapper.wrap(context), ObjectWrapper.wrap(keyManagerArr), ObjectWrapper.wrap(trustManagerArr), z));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public SSLSocketFactory makeSocketFactoryWithCacheDir(Context context, KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, String str) {
        try {
            return (SSLSocketFactory) ObjectWrapper.unwrap(((ISocketFactoryCreator) getRemoteCreatorInstance(context)).newSocketFactoryWithCacheDir(ObjectWrapper.wrap(context), ObjectWrapper.wrap(keyManagerArr), ObjectWrapper.wrap(trustManagerArr), str));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
