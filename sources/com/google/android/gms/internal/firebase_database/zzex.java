package com.google.android.gms.internal.firebase_database;

public final class zzex {
    private final long zzlq;

    public zzex(long j) {
        this.zzlq = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                return this.zzlq == ((zzex) obj).zzlq;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (int) (this.zzlq ^ (this.zzlq >>> 32));
    }

    public final String toString() {
        long j = this.zzlq;
        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("Tag{tagNumber=");
        stringBuilder.append(j);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public final long zzcm() {
        return this.zzlq;
    }
}
