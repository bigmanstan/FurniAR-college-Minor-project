package com.google.android.gms.common;

import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;

final class zzi extends zzg {
    private final String packageName;
    private final CertData zzbn;
    private final boolean zzbo;
    private final boolean zzbp;

    private zzi(String str, CertData certData, boolean z, boolean z2) {
        super(false, null, null);
        this.packageName = str;
        this.zzbn = certData;
        this.zzbo = z;
        this.zzbp = z2;
    }

    final String getErrorMessage() {
        String str = this.zzbp ? "debug cert rejected" : "not whitelisted";
        String str2 = this.packageName;
        String bytesToStringLowercase = Hex.bytesToStringLowercase(AndroidUtilsLight.getMessageDigest("SHA-1").digest(this.zzbn.getBytes()));
        boolean z = this.zzbo;
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 44) + String.valueOf(str2).length()) + String.valueOf(bytesToStringLowercase).length());
        stringBuilder.append(str);
        stringBuilder.append(": pkg=");
        stringBuilder.append(str2);
        stringBuilder.append(", sha1=");
        stringBuilder.append(bytesToStringLowercase);
        stringBuilder.append(", atk=");
        stringBuilder.append(z);
        stringBuilder.append(", ver=12451009.false");
        return stringBuilder.toString();
    }
}
