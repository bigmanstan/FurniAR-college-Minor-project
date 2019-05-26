package com.google.android.gms.common;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.ICertData;
import com.google.android.gms.common.internal.ICertData.Stub;
import com.google.android.gms.common.internal.IGoogleCertificatesApi;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.concurrent.GuardedBy;

@CheckReturnValue
final class GoogleCertificates {
    private static volatile IGoogleCertificatesApi zzax;
    private static final Object zzay = new Object();
    private static Context zzaz;
    @GuardedBy("GoogleCertificates.class")
    private static Set<ICertData> zzba;
    @GuardedBy("GoogleCertificates.class")
    private static Set<ICertData> zzbb;

    static abstract class CertData extends Stub {
        private int zzbc;

        protected CertData(byte[] bArr) {
            Preconditions.checkArgument(bArr.length == 25);
            this.zzbc = Arrays.hashCode(bArr);
        }

        protected static byte[] zzd(String str) {
            try {
                return str.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }

        public boolean equals(Object obj) {
            if (obj != null) {
                if (obj instanceof ICertData) {
                    try {
                        ICertData iCertData = (ICertData) obj;
                        if (iCertData.getHashCode() != hashCode()) {
                            return false;
                        }
                        IObjectWrapper bytesWrapped = iCertData.getBytesWrapped();
                        if (bytesWrapped == null) {
                            return false;
                        }
                        return Arrays.equals(getBytes(), (byte[]) ObjectWrapper.unwrap(bytesWrapped));
                    } catch (Throwable e) {
                        Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                    }
                }
            }
            return false;
        }

        abstract byte[] getBytes();

        public IObjectWrapper getBytesWrapped() {
            return ObjectWrapper.wrap(getBytes());
        }

        public int getHashCode() {
            return hashCode();
        }

        public int hashCode() {
            return this.zzbc;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void init(android.content.Context r2) {
        /*
        r0 = com.google.android.gms.common.GoogleCertificates.class;
        monitor-enter(r0);
        r1 = zzaz;	 Catch:{ all -> 0x001a }
        if (r1 != 0) goto L_0x0011;
    L_0x0007:
        if (r2 == 0) goto L_0x0018;
    L_0x0009:
        r2 = r2.getApplicationContext();	 Catch:{ all -> 0x001a }
        zzaz = r2;	 Catch:{ all -> 0x001a }
        monitor-exit(r0);
        return;
    L_0x0011:
        r2 = "GoogleCertificates";
        r1 = "GoogleCertificates has been initialized already";
        android.util.Log.w(r2, r1);	 Catch:{ all -> 0x001a }
    L_0x0018:
        monitor-exit(r0);
        return;
    L_0x001a:
        r2 = move-exception;
        monitor-exit(r0);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GoogleCertificates.init(android.content.Context):void");
    }

    static zzg zza(String str, CertData certData, boolean z) {
        Throwable e;
        String str2;
        try {
            zzc();
            Preconditions.checkNotNull(zzaz);
            try {
                if (zzax.isGoogleOrPlatformSigned(new GoogleCertificatesQuery(str, certData, z), ObjectWrapper.wrap(zzaz.getPackageManager()))) {
                    return zzg.zzg();
                }
                boolean z2 = true;
                if (z || !zza(str, certData, true).zzbl) {
                    z2 = false;
                }
                return zzg.zza(str, certData, z, z2);
            } catch (RemoteException e2) {
                e = e2;
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                str2 = "module call";
                return zzg.zza(str2, e);
            }
        } catch (LoadingException e3) {
            e = e3;
            str2 = "module init";
            return zzg.zza(str2, e);
        }
    }

    private static Set<ICertData> zza(IBinder[] iBinderArr) throws RemoteException {
        Set<ICertData> hashSet = new HashSet(r0);
        for (IBinder asInterface : iBinderArr) {
            ICertData asInterface2 = Stub.asInterface(asInterface);
            if (asInterface2 != null) {
                hashSet.add(asInterface2);
            }
        }
        return hashSet;
    }

    private static void zzc() throws LoadingException {
        if (zzax == null) {
            Preconditions.checkNotNull(zzaz);
            synchronized (zzay) {
                if (zzax == null) {
                    zzax = IGoogleCertificatesApi.Stub.asInterface(DynamiteModule.load(zzaz, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
                }
            }
        }
    }

    static synchronized Set<ICertData> zzd() {
        synchronized (GoogleCertificates.class) {
            if (zzba != null) {
                Set<ICertData> set = zzba;
                return set;
            }
            try {
                zzc();
                try {
                    IObjectWrapper googleCertificates = zzax.getGoogleCertificates();
                    if (googleCertificates == null) {
                        Log.e("GoogleCertificates", "Failed to get Google certificates from remote");
                        set = Collections.emptySet();
                        return set;
                    }
                    zzba = zza((IBinder[]) ObjectWrapper.unwrap(googleCertificates));
                    set = zzba;
                    return set;
                } catch (Throwable e) {
                    Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                    return Collections.emptySet();
                }
            } catch (Throwable e2) {
                Log.e("GoogleCertificates", "Failed to load com.google.android.gms.googlecertificates", e2);
                return Collections.emptySet();
            }
        }
    }

    static synchronized Set<ICertData> zze() {
        synchronized (GoogleCertificates.class) {
            if (zzbb != null) {
                Set<ICertData> set = zzbb;
                return set;
            }
            try {
                zzc();
                try {
                    IObjectWrapper googleReleaseCertificates = zzax.getGoogleReleaseCertificates();
                    if (googleReleaseCertificates == null) {
                        Log.e("GoogleCertificates", "Failed to get Google certificates from remote");
                        set = Collections.emptySet();
                        return set;
                    }
                    zzbb = zza((IBinder[]) ObjectWrapper.unwrap(googleReleaseCertificates));
                    set = zzbb;
                    return set;
                } catch (Throwable e) {
                    Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                    return Collections.emptySet();
                }
            } catch (Throwable e2) {
                Log.e("GoogleCertificates", "Failed to load com.google.android.gms.googlecertificates", e2);
                return Collections.emptySet();
            }
        }
    }
}
