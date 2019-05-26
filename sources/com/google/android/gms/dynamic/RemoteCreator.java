package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;

public abstract class RemoteCreator<T> {
    private final String zzabo;
    private T zzabp;

    public static class RemoteCreatorException extends Exception {
        public RemoteCreatorException(String str) {
            super(str);
        }

        public RemoteCreatorException(String str, Throwable th) {
            super(str, th);
        }
    }

    protected RemoteCreator(String str) {
        this.zzabo = str;
    }

    protected abstract T getRemoteCreator(IBinder iBinder);

    protected final T getRemoteCreatorInstance(Context context) throws RemoteCreatorException {
        if (this.zzabp == null) {
            Preconditions.checkNotNull(context);
            context = GooglePlayServicesUtilLight.getRemoteContext(context);
            if (context != null) {
                try {
                    this.zzabp = getRemoteCreator((IBinder) context.getClassLoader().loadClass(this.zzabo).newInstance());
                } catch (Throwable e) {
                    throw new RemoteCreatorException("Could not load creator class.", e);
                } catch (Throwable e2) {
                    throw new RemoteCreatorException("Could not instantiate creator.", e2);
                } catch (Throwable e22) {
                    throw new RemoteCreatorException("Could not access creator.", e22);
                }
            }
            throw new RemoteCreatorException("Could not get remote context.");
        }
        return this.zzabp;
    }
}
