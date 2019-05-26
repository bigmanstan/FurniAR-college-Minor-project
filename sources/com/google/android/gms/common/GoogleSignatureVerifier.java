package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.ICertData;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.ProviderConstants;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
public class GoogleSignatureVerifier {
    private static GoogleSignatureVerifier zzbv;
    private final Context mContext;

    private GoogleSignatureVerifier(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static GoogleSignatureVerifier getInstance(Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zzbv == null) {
                GoogleCertificates.init(context);
                zzbv = new GoogleSignatureVerifier(context);
            }
        }
        return zzbv;
    }

    @VisibleForTesting
    public static synchronized void resetForTests() {
        synchronized (GoogleSignatureVerifier.class) {
            zzbv = null;
        }
    }

    private static CertData zza(PackageInfo packageInfo, CertData... certDataArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        int i = 0;
        zzb zzb = new zzb(packageInfo.signatures[0].toByteArray());
        while (i < certDataArr.length) {
            if (certDataArr[i].equals(zzb)) {
                return certDataArr[i];
            }
            i++;
        }
        return null;
    }

    private final zzg zza(PackageInfo packageInfo) {
        String str;
        boolean honorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext);
        if (packageInfo == null) {
            str = "null pkg";
        } else if (packageInfo.signatures.length != 1) {
            str = "single cert required";
        } else {
            CertData zzb = new zzb(packageInfo.signatures[0].toByteArray());
            String str2 = packageInfo.packageName;
            zzg zza = GoogleCertificates.zza(str2, zzb, honorsDebugCertificates);
            if (!zza.zzbl || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == 0 || (honorsDebugCertificates && !GoogleCertificates.zza(str2, zzb, false).zzbl)) {
                return zza;
            }
            str = "debuggable release cert app rejected";
        }
        return zzg.zze(str);
    }

    private final zzg zzb(int i) {
        String[] packagesForUid = Wrappers.packageManager(this.mContext).getPackagesForUid(i);
        if (packagesForUid != null) {
            if (packagesForUid.length != 0) {
                zzg zzg = null;
                for (String zzf : packagesForUid) {
                    zzg = zzf(zzf);
                    if (zzg.zzbl) {
                        return zzg;
                    }
                }
                return zzg;
            }
        }
        return zzg.zze("no pkgs");
    }

    private final zzg zzf(String str) {
        try {
            return zza(Wrappers.packageManager(this.mContext).getPackageInfo(str, 64));
        } catch (NameNotFoundException e) {
            String str2 = "no pkg ";
            str = String.valueOf(str);
            return zzg.zze(str.length() != 0 ? str2.concat(str) : new String(str2));
        }
    }

    @Deprecated
    public Set<byte[]> getAllGoogleSignatures(boolean z) {
        Set<ICertData> zzd = z ? GoogleCertificates.zzd() : GoogleCertificates.zze();
        Set<byte[]> hashSet = new HashSet(zzd.size());
        try {
            for (ICertData bytesWrapped : zzd) {
                hashSet.add((byte[]) ObjectWrapper.unwrap(bytesWrapped.getBytesWrapped()));
            }
        } catch (Throwable e) {
            Log.e("GoogleSignatureVerifier", "Failed to get Google certificates from remote", e);
        }
        return hashSet;
    }

    public boolean isChimeraSigned(PackageManager packageManager, PackageInfo packageInfo) {
        String str = packageInfo.packageName;
        packageInfo.packageName = ProviderConstants.API_PROVIDER_NAME;
        boolean isPackageGoogleSigned = isPackageGoogleSigned(packageInfo);
        packageInfo.packageName = str;
        return isPackageGoogleSigned;
    }

    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (isGooglePublicSignedPackage(packageInfo, false)) {
            return true;
        }
        if (isGooglePublicSignedPackage(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (zza(packageInfo, z ? zzd.zzbg : new CertData[]{zzd.zzbg[0]}) != null) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public boolean isGooglePublicSignedPackage(PackageManager packageManager, PackageInfo packageInfo) {
        return isGooglePublicSignedPackage(packageInfo);
    }

    public boolean isPackageGoogleSigned(PackageInfo packageInfo) {
        zzg zza = zza(packageInfo);
        zza.zzi();
        return zza.zzbl;
    }

    @Deprecated
    public boolean isPackageGoogleSigned(PackageManager packageManager, PackageInfo packageInfo) {
        return isPackageGoogleSigned(packageInfo);
    }

    @Deprecated
    public boolean isPackageGoogleSigned(PackageManager packageManager, String str) {
        return isPackageGoogleSigned(str);
    }

    public boolean isPackageGoogleSigned(String str) {
        zzg zzf = zzf(str);
        zzf.zzi();
        return zzf.zzbl;
    }

    public boolean isUidGoogleSigned(int i) {
        zzg zzb = zzb(i);
        zzb.zzi();
        return zzb.zzbl;
    }

    @Deprecated
    public boolean isUidGoogleSigned(PackageManager packageManager, int i) {
        return isUidGoogleSigned(i);
    }

    @Deprecated
    public void verifyPackageIsGoogleSigned(PackageManager packageManager, String str) throws SecurityException {
        verifyPackageIsGoogleSigned(str);
    }

    public void verifyPackageIsGoogleSigned(String str) throws SecurityException {
        zzf(str).zzh();
    }

    public void verifyUidIsGoogleSigned(int i) throws SecurityException {
        zzb(i).zzh();
    }

    @Deprecated
    public void verifyUidIsGoogleSigned(PackageManager packageManager, int i) throws SecurityException {
        verifyUidIsGoogleSigned(i);
    }
}
