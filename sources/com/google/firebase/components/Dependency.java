package com.google.firebase.components;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public final class Dependency {
    private final Class<?> zza;
    private final int zzb;
    private final int zzc;

    private Dependency(Class<?> cls, int type, int injection) {
        this.zza = (Class) Preconditions.checkNotNull(cls, "Null dependency anInterface.");
        this.zzb = type;
        this.zzc = injection;
    }

    @KeepForSdk
    public static Dependency optional(Class<?> cls) {
        return new Dependency(cls, 0, 0);
    }

    @KeepForSdk
    public static Dependency required(Class<?> cls) {
        return new Dependency(cls, 1, 0);
    }

    @KeepForSdk
    public static Dependency optionalProvider(Class<?> cls) {
        return new Dependency(cls, 0, 1);
    }

    @KeepForSdk
    public static Dependency requiredProvider(Class<?> cls) {
        return new Dependency(cls, 1, 1);
    }

    public final Class<?> zza() {
        return this.zza;
    }

    public final boolean zzb() {
        return this.zzb == 1;
    }

    public final boolean zzc() {
        return this.zzc == 0;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof Dependency)) {
            return false;
        }
        Dependency dependency = (Dependency) o;
        if (this.zza == dependency.zza && this.zzb == dependency.zzb && this.zzc == dependency.zzc) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb) * 1000003) ^ this.zzc;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("Dependency{anInterface=");
        stringBuilder.append(this.zza);
        stringBuilder.append(", required=");
        boolean z = false;
        stringBuilder.append(this.zzb == 1);
        stringBuilder.append(", direct=");
        if (this.zzc == 0) {
            z = true;
        }
        stringBuilder.append(z);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
