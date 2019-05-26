package com.google.android.gms.internal.firebase_database;

public final class zzdn {
    public String zzct;
    public String zzcu;
    public boolean zzcv;
    public String zzka;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                zzdn zzdn = (zzdn) obj;
                return (this.zzcv == zzdn.zzcv && this.zzct.equals(zzdn.zzct)) ? this.zzcu.equals(zzdn.zzcu) : false;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (((this.zzct.hashCode() * 31) + this.zzcv) * 31) + this.zzcu.hashCode();
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
