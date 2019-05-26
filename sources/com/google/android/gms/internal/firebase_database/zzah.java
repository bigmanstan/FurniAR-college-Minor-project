package com.google.android.gms.internal.firebase_database;

public final class zzah {
    private final String zzct;
    private final String zzcu;
    private final boolean zzcv;

    public zzah(String str, String str2, boolean z) {
        this.zzct = str;
        this.zzcu = str2;
        this.zzcv = z;
    }

    public final String getHost() {
        return this.zzct;
    }

    public final String getNamespace() {
        return this.zzcu;
    }

    public final boolean isSecure() {
        return this.zzcv;
    }

    public final String toString() {
        String str = this.zzcv ? "s" : "";
        String str2 = this.zzct;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 7) + String.valueOf(str2).length());
        stringBuilder.append("http");
        stringBuilder.append(str);
        stringBuilder.append("://");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }
}
