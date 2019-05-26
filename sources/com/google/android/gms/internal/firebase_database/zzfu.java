package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public final class zzfu implements zzfv {
    private boolean zzbt = false;

    private final void zzn() {
        zzkq.zza(this.zzbt, "Transaction expected to already be in progress.");
    }

    public final <T> T zza(Callable<T> callable) {
        zzkq.zza(this.zzbt ^ true, "runInTransaction called when an existing transaction is already in progress.");
        this.zzbt = true;
        try {
            T call = callable.call();
            this.zzbt = false;
            return call;
        } catch (Throwable th) {
            this.zzbt = false;
        }
    }

    public final void zza(long j) {
        zzn();
    }

    public final void zza(zzch zzch, zzbv zzbv, long j) {
        zzn();
    }

    public final void zza(zzch zzch, zzja zzja, long j) {
        zzn();
    }

    public final void zza(zzhh zzhh, zzja zzja) {
        zzn();
    }

    public final void zza(zzhh zzhh, Set<zzid> set) {
        zzn();
    }

    public final void zza(zzhh zzhh, Set<zzid> set, Set<zzid> set2) {
        zzn();
    }

    public final void zzc(zzch zzch, zzbv zzbv) {
        zzn();
    }

    public final void zzd(zzch zzch, zzbv zzbv) {
        zzn();
    }

    public final zzgu zzf(zzhh zzhh) {
        return new zzgu(zzit.zza(zzir.zzfv(), zzhh.zzeg()), false, false);
    }

    public final void zzg(zzhh zzhh) {
        zzn();
    }

    public final void zzh(zzhh zzhh) {
        zzn();
    }

    public final void zzi(zzhh zzhh) {
        zzn();
    }

    public final List<zzfa> zzj() {
        return Collections.emptyList();
    }

    public final void zzk(zzch zzch, zzja zzja) {
        zzn();
    }

    public final void zzm() {
        zzn();
    }
}
