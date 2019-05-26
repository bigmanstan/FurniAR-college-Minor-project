package com.google.vr.dynamite.client;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.os.IInterface;
import java.lang.reflect.InvocationTargetException;

/* renamed from: com.google.vr.dynamite.client.d */
final class C0426d {
    /* renamed from: a */
    private Context f147a;
    /* renamed from: b */
    private ILoadedInstanceCreator f148b;
    /* renamed from: c */
    private final C0427e f149c;

    public C0426d(C0427e c0427e) {
        this.f149c = c0427e;
    }

    /* renamed from: a */
    private static IBinder m98a(ClassLoader classLoader, String str) {
        String str2;
        try {
            return (IBinder) classLoader.loadClass(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (NoSuchMethodException e) {
            str2 = "No constructor for dynamic class ";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        } catch (InvocationTargetException e2) {
            str2 = "Unable to invoke constructor of dynamic class ";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        } catch (ClassNotFoundException e3) {
            str2 = "Unable to find dynamic class ";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        } catch (InstantiationException e4) {
            str2 = "Unable to instantiate the remote class ";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        } catch (IllegalAccessException e5) {
            str2 = "Unable to call the default constructor of ";
            str = String.valueOf(str);
            throw new IllegalStateException(str.length() != 0 ? str2.concat(str) : new String(str2));
        }
    }

    /* renamed from: a */
    public final synchronized ILoadedInstanceCreator m99a(Context context) throws C0425c {
        if (this.f148b == null) {
            ILoadedInstanceCreator iLoadedInstanceCreator;
            IBinder a = C0426d.m98a(m100b(context).getClassLoader(), "com.google.vr.dynamite.LoadedInstanceCreator");
            if (a == null) {
                iLoadedInstanceCreator = null;
            } else {
                IInterface queryLocalInterface = a.queryLocalInterface("com.google.vr.dynamite.client.ILoadedInstanceCreator");
                iLoadedInstanceCreator = queryLocalInterface instanceof ILoadedInstanceCreator ? (ILoadedInstanceCreator) queryLocalInterface : new C0561a(a);
            }
            this.f148b = iLoadedInstanceCreator;
        }
        return this.f148b;
    }

    /* renamed from: b */
    public final synchronized Context m100b(Context context) throws C0425c {
        if (this.f147a == null) {
            try {
                this.f147a = context.createPackageContext(this.f149c.m101a(), 3);
            } catch (NameNotFoundException e) {
                throw new C0425c(1);
            }
        }
        return this.f147a;
    }
}
