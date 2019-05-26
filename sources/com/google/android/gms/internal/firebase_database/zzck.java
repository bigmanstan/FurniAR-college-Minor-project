package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.zzh;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzck implements zzak {
    private final zzdn zzag;
    private zzaj zzfs;
    private final zzkm zzhu = new zzkm(new zzkg(), 0);
    private zzdx zzhv;
    private zzdy zzhw;
    private zzgp<List<zzdl>> zzhx;
    private boolean zzhy = false;
    private final zzhc zzhz;
    private final zzbz zzia;
    private final zzhz zzib;
    private final zzhz zzic;
    private final zzhz zzid;
    private long zzie = 0;
    private long zzif = 1;
    private zzee zzig;
    private zzee zzih;
    private FirebaseDatabase zzii;
    private boolean zzij = false;
    private long zzik = 0;

    zzck(zzdn zzdn, zzbz zzbz, FirebaseDatabase firebaseDatabase) {
        this.zzag = zzdn;
        this.zzia = zzbz;
        this.zzii = firebaseDatabase;
        this.zzib = this.zzia.zzp("RepoOperation");
        this.zzic = this.zzia.zzp("Transaction");
        this.zzid = this.zzia.zzp("DataOperation");
        this.zzhz = new zzhc(this.zzia);
        zzc(new zzcl(this));
    }

    private final zzja zza(zzch zzch, List<Long> list) {
        zzja zzc = this.zzih.zzc(zzch, list);
        return zzc == null ? zzir.zzfv() : zzc;
    }

    private final void zza(long j, zzch zzch, DatabaseError databaseError) {
        if (databaseError == null || databaseError.getCode() != -25) {
            List zza = this.zzih.zza(j, (databaseError == null ? 1 : 0) ^ 1, true, this.zzhu);
            if (zza.size() > 0) {
                zzn(zzch);
            }
            zzc(zza);
        }
    }

    private final void zza(zzgp<List<zzdl>> zzgp) {
        if (((List) zzgp.getValue()) != null) {
            List<zzdl> zzc = zzc((zzgp) zzgp);
            Boolean valueOf = Boolean.valueOf(true);
            for (zzdl zzc2 : zzc) {
                if (zzc2.zzjl != zzdm.zzju) {
                    valueOf = Boolean.valueOf(false);
                    break;
                }
            }
            if (valueOf.booleanValue()) {
                zzch zzg = zzgp.zzg();
                List arrayList = new ArrayList();
                for (zzdl zzc22 : zzc) {
                    arrayList.add(Long.valueOf(zzc22.zzjp));
                }
                zzja zza = zza(zzg, arrayList);
                String zzfj = zza.zzfj();
                for (zzdl zzdl : zzc) {
                    zzdl.zzjl = zzdm.zzjv;
                    zzdl.retryCount = zzdl.retryCount + 1;
                    zza = zza.zzl(zzch.zza(zzg, zzdl.zzap), zzdl.zzjr);
                }
                this.zzfs.zza(zzg.zzbv(), zza.getValue(true), zzfj, new zzcs(this, zzg, zzc, this));
            }
            return;
        }
        if (zzgp.hasChildren()) {
            zzgp.zza(new zzcr(this));
        }
    }

    private final void zza(zzgp<List<zzdl>> zzgp, int i) {
        zzck zzck = this;
        zzgp<List<zzdl>> zzgp2 = zzgp;
        int i2 = i;
        List list = (List) zzgp.getValue();
        List arrayList = new ArrayList();
        if (list != null) {
            DatabaseError zza;
            List arrayList2 = new ArrayList();
            if (i2 == -9) {
                zza = DatabaseError.zza("overriddenBySet");
            } else {
                boolean z = i2 == -25;
                StringBuilder stringBuilder = new StringBuilder(45);
                stringBuilder.append("Unknown transaction abort reason: ");
                stringBuilder.append(i2);
                zzkq.zza(z, stringBuilder.toString());
                zza = DatabaseError.zza(-25);
            }
            int i3 = -1;
            for (int i4 = 0; i4 < list.size(); i4++) {
                zzdl zzdl = (zzdl) list.get(i4);
                if (zzdl.zzjl != zzdm.zzjx) {
                    if (zzdl.zzjl == zzdm.zzjv) {
                        zzdl.zzjl = zzdm.zzjx;
                        zzdl.zzjo = zza;
                        i3 = i4;
                    } else {
                        zze(new zzfc(zzck, zzdl.zzjk, zzhh.zzal(zzdl.zzap)));
                        if (i2 == -9) {
                            arrayList.addAll(zzck.zzih.zza(zzdl.zzjp, true, false, (zzkf) zzck.zzhu));
                        } else {
                            boolean z2 = i2 == -25;
                            StringBuilder stringBuilder2 = new StringBuilder(45);
                            stringBuilder2.append("Unknown transaction abort reason: ");
                            stringBuilder2.append(i2);
                            zzkq.zza(z2, stringBuilder2.toString());
                        }
                        arrayList2.add(new zzdb(zzck, zzdl, zza));
                    }
                }
            }
            if (i3 == -1) {
                zzgp2.setValue(null);
                i2 = 0;
            } else {
                i2 = 0;
                zzgp2.setValue(list.subList(0, i3 + 1));
            }
            zzc(arrayList);
            ArrayList arrayList3 = (ArrayList) arrayList2;
            int size = arrayList3.size();
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                zza((Runnable) obj);
            }
        }
    }

    private final void zza(zzid zzid, Object obj) {
        if (zzid.equals(zzby.zzhc)) {
            this.zzhu.zzn(((Long) obj).longValue());
        }
        zzch zzch = new zzch(zzby.zzhb, zzid);
        try {
            zzja zza = zzjd.zza(obj, zzir.zzfv());
            this.zzhv.zzg(zzch, zza);
            zzc(this.zzig.zzi(zzch, zza));
        } catch (Throwable e) {
            this.zzib.zza("Failed to parse info update", e);
        }
    }

    private final void zza(String str, zzch zzch, DatabaseError databaseError) {
        if (databaseError != null && databaseError.getCode() != -1 && databaseError.getCode() != -25) {
            zzhz zzhz = this.zzib;
            String zzch2 = zzch.toString();
            String databaseError2 = databaseError.toString();
            StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 13) + String.valueOf(zzch2).length()) + String.valueOf(databaseError2).length());
            stringBuilder.append(str);
            stringBuilder.append(" at ");
            stringBuilder.append(zzch2);
            stringBuilder.append(" failed: ");
            stringBuilder.append(databaseError2);
            zzhz.zzb(stringBuilder.toString(), null);
        }
    }

    private final void zza(List<zzdl> list, zzgp<List<zzdl>> zzgp) {
        List list2 = (List) zzgp.getValue();
        if (list2 != null) {
            list.addAll(list2);
        }
        zzgp.zza(new zzcy(this, list));
    }

    private final zzch zzb(zzch zzch, int i) {
        zzch zzg = zzo(zzch).zzg();
        if (this.zzic.zzfa()) {
            zzhz zzhz = this.zzib;
            String valueOf = String.valueOf(zzch);
            String valueOf2 = String.valueOf(zzg);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 44) + String.valueOf(valueOf2).length());
            stringBuilder.append("Aborting transactions for path: ");
            stringBuilder.append(valueOf);
            stringBuilder.append(". Affected: ");
            stringBuilder.append(valueOf2);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        zzgp zzaj = this.zzhx.zzaj(zzch);
        zzaj.zza(new zzcz(this, i), false);
        zza(zzaj, i);
        zzaj.zza(new zzda(this, i), false, false);
        return zzg;
    }

    private final void zzb(zzgp<List<zzdl>> zzgp) {
        Object obj = (List) zzgp.getValue();
        if (obj != null) {
            int i = 0;
            while (i < obj.size()) {
                if (((zzdl) obj.get(i)).zzjl == zzdm.zzjw) {
                    obj.remove(i);
                } else {
                    i++;
                }
            }
            if (obj.size() <= 0) {
                obj = null;
            }
            zzgp.setValue(obj);
        }
        zzgp.zza(new zzcu(this));
    }

    private static DatabaseError zzc(String str, String str2) {
        return str != null ? DatabaseError.zza(str, str2) : null;
    }

    private final List<zzdl> zzc(zzgp<List<zzdl>> zzgp) {
        List arrayList = new ArrayList();
        zza(arrayList, (zzgp) zzgp);
        Collections.sort(arrayList);
        return arrayList;
    }

    private final void zzc(List<? extends zzgy> list) {
        if (!list.isEmpty()) {
            this.zzhz.zze(list);
        }
    }

    private final void zzca() {
        this.zzfs = this.zzia.zza(new zzah(this.zzag.zzct, this.zzag.zzcu, this.zzag.zzcv), this);
        this.zzia.zzhg.zza(new zzcx(this));
        this.zzfs.initialize();
        zzfv zzq = this.zzia.zzq(this.zzag.zzct);
        this.zzhv = new zzdx();
        this.zzhw = new zzdy();
        this.zzhx = new zzgp();
        this.zzig = new zzee(this.zzia, new zzfu(), new zzdc(this));
        this.zzih = new zzee(this.zzia, zzq, new zzde(this));
        List<zzfa> zzj = zzq.zzj();
        Map zza = zzdu.zza(this.zzhu);
        long j = Long.MIN_VALUE;
        for (zzfa zzfa : zzj) {
            zzbb zzdg = new zzdg(this, zzfa);
            if (j < zzfa.zzcn()) {
                j = zzfa.zzcn();
                this.zzif = zzfa.zzcn() + 1;
                zzhz zzhz;
                long zzcn;
                StringBuilder stringBuilder;
                if (zzfa.zzcq()) {
                    if (this.zzib.zzfa()) {
                        zzhz = this.zzib;
                        zzcn = zzfa.zzcn();
                        stringBuilder = new StringBuilder(48);
                        stringBuilder.append("Restoring overwrite with id ");
                        stringBuilder.append(zzcn);
                        zzhz.zza(stringBuilder.toString(), null, new Object[0]);
                    }
                    this.zzfs.zza(zzfa.zzg().zzbv(), zzfa.zzco().getValue(true), zzdg);
                    this.zzih.zza(zzfa.zzg(), zzfa.zzco(), zzdu.zza(zzfa.zzco(), zza), zzfa.zzcn(), true, false);
                } else {
                    if (this.zzib.zzfa()) {
                        zzhz = this.zzib;
                        zzcn = zzfa.zzcn();
                        stringBuilder = new StringBuilder(44);
                        stringBuilder.append("Restoring merge with id ");
                        stringBuilder.append(zzcn);
                        zzhz.zza(stringBuilder.toString(), null, new Object[0]);
                    }
                    this.zzfs.zza(zzfa.zzg().zzbv(), zzfa.zzcp().zzd(true), zzdg);
                    this.zzih.zza(zzfa.zzg(), zzfa.zzcp(), zzdu.zza(zzfa.zzcp(), zza), zzfa.zzcn(), false);
                }
            } else {
                throw new IllegalStateException("Write ids were not in order.");
            }
        }
        zza(zzby.zzhd, Boolean.valueOf(false));
        zza(zzby.zzhe, Boolean.valueOf(false));
    }

    private final long zzce() {
        long j = this.zzif;
        this.zzif = 1 + j;
        return j;
    }

    private final void zzcf() {
        zzgp zzgp = this.zzhx;
        zzb(zzgp);
        zza(zzgp);
    }

    private final zzch zzn(zzch zzch) {
        zzgp zzo = zzo(zzch);
        zzch zzg = zzo.zzg();
        List<zzdl> zzc = zzc(zzo);
        if (!zzc.isEmpty()) {
            int i;
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            for (zzdl zzb : zzc) {
                arrayList2.add(Long.valueOf(zzb.zzjp));
            }
            Iterator it = zzc.iterator();
            while (true) {
                i = 0;
                if (!it.hasNext()) {
                    break;
                }
                DataSnapshot zza;
                zzdl zzdl = (zzdl) it.next();
                zzch.zza(zzg, zzdl.zzap);
                List arrayList3 = new ArrayList();
                DatabaseError databaseError = null;
                int i2 = 1;
                if (zzdl.zzjl == zzdm.zzjy) {
                    databaseError = zzdl.zzjo;
                    if (databaseError.getCode() != -25) {
                    }
                    zzc(arrayList3);
                    if (i2 == 0) {
                        zzdl.zzjl = zzdm.zzjw;
                        zza = zzh.zza(zzh.zza(r1, zzdl.zzap), zzit.zzj(zzdl.zzjq));
                        zzc(new zzcv(r1, zzdl));
                        arrayList.add(new zzcw(r1, zzdl, databaseError, zza));
                    }
                } else {
                    if (zzdl.zzjl == zzdm.zzju) {
                        if (zzdl.retryCount >= 25) {
                            databaseError = DatabaseError.zza("maxretries");
                        } else {
                            Result doTransaction;
                            DatabaseError databaseError2;
                            zzja zza2 = zza(zzdl.zzap, arrayList2);
                            zzdl.zzjq = zza2;
                            try {
                                doTransaction = zzdl.zzjj.doTransaction(zzh.zza(zza2));
                                databaseError2 = null;
                            } catch (Throwable th) {
                                r1.zzib.zza("Caught Throwable.", th);
                                DatabaseError fromException = DatabaseError.fromException(th);
                                databaseError2 = fromException;
                                doTransaction = Transaction.abort();
                            }
                            if (doTransaction.isSuccess()) {
                                Long valueOf = Long.valueOf(zzdl.zzjp);
                                Map zza3 = zzdu.zza(r1.zzhu);
                                zzja zzd = doTransaction.zzd();
                                zzja zza4 = zzdu.zza(zzd, zza3);
                                zzdl.zzjr = zzd;
                                zzdl.zzjs = zza4;
                                zzdl.zzjp = zzce();
                                arrayList2.remove(valueOf);
                                arrayList3.addAll(r1.zzih.zza(zzdl.zzap, zzd, zza4, zzdl.zzjp, zzdl.zzjn, false));
                                arrayList3.addAll(r1.zzih.zza(valueOf.longValue(), true, false, (zzkf) r1.zzhu));
                            } else {
                                arrayList3.addAll(r1.zzih.zza(zzdl.zzjp, true, false, (zzkf) r1.zzhu));
                                databaseError = databaseError2;
                                zzc(arrayList3);
                                if (i2 == 0) {
                                    zzdl.zzjl = zzdm.zzjw;
                                    zza = zzh.zza(zzh.zza(r1, zzdl.zzap), zzit.zzj(zzdl.zzjq));
                                    zzc(new zzcv(r1, zzdl));
                                    arrayList.add(new zzcw(r1, zzdl, databaseError, zza));
                                }
                            }
                        }
                    }
                    i2 = 0;
                    zzc(arrayList3);
                    if (i2 == 0) {
                        zzdl.zzjl = zzdm.zzjw;
                        zza = zzh.zza(zzh.zza(r1, zzdl.zzap), zzit.zzj(zzdl.zzjq));
                        zzc(new zzcv(r1, zzdl));
                        arrayList.add(new zzcw(r1, zzdl, databaseError, zza));
                    }
                }
                arrayList3.addAll(r1.zzih.zza(zzdl.zzjp, true, false, (zzkf) r1.zzhu));
                zzc(arrayList3);
                if (i2 == 0) {
                    zzdl.zzjl = zzdm.zzjw;
                    zza = zzh.zza(zzh.zza(r1, zzdl.zzap), zzit.zzj(zzdl.zzjq));
                    zzc(new zzcv(r1, zzdl));
                    arrayList.add(new zzcw(r1, zzdl, databaseError, zza));
                }
            }
            zzb(r1.zzhx);
            while (i < arrayList.size()) {
                zza((Runnable) arrayList.get(i));
                i++;
            }
            zzcf();
        }
        return zzg;
    }

    private final zzgp<List<zzdl>> zzo(zzch zzch) {
        zzgp<List<zzdl>> zzgp = this.zzhx;
        while (!zzch.isEmpty() && zzgp.getValue() == null) {
            zzgp = zzgp.zzaj(new zzch(zzch.zzbw()));
            zzch = zzch.zzbx();
        }
        return zzgp;
    }

    public final FirebaseDatabase getDatabase() {
        return this.zzii;
    }

    final void interrupt() {
        this.zzfs.interrupt("repo_interrupt");
    }

    public final void onDisconnect() {
        zza(zzby.zzhe, Boolean.valueOf(false));
        Map zza = zzdu.zza(this.zzhu);
        zzdy zzdy = this.zzhw;
        zzdy zzdy2 = new zzdy();
        zzdy.zza(new zzch(""), new zzdv(zzdy2, zza));
        List arrayList = new ArrayList();
        zzdy2.zza(zzch.zzbt(), new zzco(this, arrayList));
        this.zzhw = new zzdy();
        zzc(arrayList);
    }

    public final void purgeOutstandingWrites() {
        if (this.zzib.zzfa()) {
            this.zzib.zza("Purging writes", null, new Object[0]);
        }
        zzc(this.zzih.zzck());
        zzb(zzch.zzbt(), -25);
        this.zzfs.purgeOutstandingWrites();
    }

    final void resume() {
        this.zzfs.resume("repo_interrupt");
    }

    public final String toString() {
        return this.zzag.toString();
    }

    public final void zza(zzch zzch, zzbv zzbv, CompletionListener completionListener, Map<String, Object> map) {
        if (this.zzib.zzfa()) {
            zzhz zzhz = this.zzib;
            String valueOf = String.valueOf(zzch);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 8);
            stringBuilder.append("update: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (this.zzid.zzfa()) {
            zzhz = this.zzid;
            valueOf = String.valueOf(zzch);
            String valueOf2 = String.valueOf(map);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 9) + String.valueOf(valueOf2).length());
            stringBuilder2.append("update: ");
            stringBuilder2.append(valueOf);
            stringBuilder2.append(" ");
            stringBuilder2.append(valueOf2);
            zzhz.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        if (zzbv.isEmpty()) {
            if (this.zzib.zzfa()) {
                this.zzib.zza("update called with no changes. No-op", null, new Object[0]);
            }
            zza(completionListener, null, zzch);
            return;
        }
        zzbv zza = zzdu.zza(zzbv, zzdu.zza(this.zzhu));
        long zzce = zzce();
        zzc(this.zzih.zza(zzch, zzbv, zza, zzce, true));
        this.zzfs.zza(zzch.zzbv(), (Map) map, new zzdj(this, zzch, zzce, completionListener));
        Iterator it = zzbv.iterator();
        while (it.hasNext()) {
            zzn(zzb(zzch.zzh((zzch) ((Entry) it.next()).getKey()), -9));
        }
    }

    public final void zza(zzch zzch, zzja zzja, CompletionListener completionListener) {
        if (this.zzib.zzfa()) {
            zzhz zzhz = this.zzib;
            String valueOf = String.valueOf(zzch);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 5);
            stringBuilder.append("set: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (this.zzid.zzfa()) {
            zzhz = this.zzid;
            valueOf = String.valueOf(zzch);
            String valueOf2 = String.valueOf(zzja);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 6) + String.valueOf(valueOf2).length());
            stringBuilder2.append("set: ");
            stringBuilder2.append(valueOf);
            stringBuilder2.append(" ");
            stringBuilder2.append(valueOf2);
            zzhz.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        zzja zza = zzdu.zza(zzja, zzdu.zza(this.zzhu));
        long zzce = zzce();
        zzc(this.zzih.zza(zzch, zzja, zza, zzce, true, true));
        this.zzfs.zza(zzch.zzbv(), zzja.getValue(true), new zzdi(this, zzch, zzce, completionListener));
        zzn(zzb(zzch, -9));
    }

    public final void zza(zzch zzch, CompletionListener completionListener) {
        this.zzfs.zza(zzch.zzbv(), new zzcn(this, zzch, completionListener));
    }

    public final void zza(zzch zzch, Handler handler, boolean z) {
        zzch zzch2 = zzch;
        Handler handler2 = handler;
        if (this.zzib.zzfa()) {
            zzhz zzhz = r1.zzib;
            String valueOf = String.valueOf(zzch);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 13);
            stringBuilder.append("transaction: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (r1.zzid.zzfa()) {
            zzhz = r1.zzib;
            valueOf = String.valueOf(zzch);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 13);
            stringBuilder.append("transaction: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (r1.zzia.zzcp && !r1.zzij) {
            r1.zzij = true;
            r1.zzic.info("runTransaction() usage detected while persistence is enabled. Please be aware that transactions *will not* be persisted across database restarts.  See https://www.firebase.com/docs/android/guide/offline-capabilities.html#section-handling-transactions-offline for more details.");
        }
        DatabaseReference zza = zzh.zza(this, zzch);
        ValueEventListener zzcp = new zzcp(r1);
        zzf(new zzfc(r1, zzcp, zza.zzh()));
        int i = zzdm.zzjt;
        long j = r1.zzik;
        r1.zzik = 1 + j;
        zzdl zzdl = new zzdl(zzch, handler, zzcp, i, z, j);
        zzja zza2 = zza(zzch2, new ArrayList());
        zzdl.zzjq = zza2;
        Result doTransaction;
        DatabaseError databaseError;
        try {
            doTransaction = handler2.doTransaction(zzh.zza(zza2));
            if (doTransaction != null) {
                databaseError = null;
                if (doTransaction.isSuccess()) {
                    zzdl.zzjl = zzdm.zzju;
                    zzgp zzaj = r1.zzhx.zzaj(zzch2);
                    List list = (List) zzaj.getValue();
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(zzdl);
                    zzaj.setValue(list);
                    Map zza3 = zzdu.zza(r1.zzhu);
                    zzja zzd = doTransaction.zzd();
                    zzja zza4 = zzdu.zza(zzd, zza3);
                    zzdl.zzjr = zzd;
                    zzdl.zzjs = zza4;
                    zzdl.zzjp = zzce();
                    zzc(r1.zzih.zza(zzch, zzd, zza4, zzdl.zzjp, z, false));
                    zzcf();
                    return;
                }
                zzdl.zzjr = null;
                zzdl.zzjs = null;
                zza(new zzcq(r1, handler2, databaseError, zzh.zza(zza, zzit.zzj(zzdl.zzjq))));
                return;
            }
            throw new NullPointerException("Transaction returned null as result");
        } catch (Throwable th) {
            r1.zzib.zza("Caught Throwable.", th);
            DatabaseError fromException = DatabaseError.fromException(th);
            databaseError = fromException;
            doTransaction = Transaction.abort();
        }
    }

    public final void zza(zzch zzch, Map<zzch, zzja> map, CompletionListener completionListener, Map<String, Object> map2) {
        this.zzfs.zzb(zzch.zzbv(), (Map) map2, new zzcm(this, zzch, map, completionListener));
    }

    public final void zza(zzhh zzhh, boolean z) {
        this.zzih.zza(zzhh, z);
    }

    final void zza(CompletionListener completionListener, DatabaseError databaseError, zzch zzch) {
        if (completionListener != null) {
            zzid zzbz = zzch.zzbz();
            if (zzbz != null && zzbz.zzfh()) {
                zzch = zzch.zzby();
            }
            zza(new zzdh(this, completionListener, databaseError, zzh.zza(this, zzch)));
        }
    }

    public final void zza(Runnable runnable) {
        this.zzia.zzbl();
        this.zzia.zzhf.zza(runnable);
    }

    public final void zza(List<String> list, Object obj, boolean z, Long l) {
        List zza;
        zzch zzch = new zzch((List) list);
        if (this.zzib.zzfa()) {
            zzhz zzhz = this.zzib;
            String valueOf = String.valueOf(zzch);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 14);
            stringBuilder.append("onDataUpdate: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (this.zzid.zzfa()) {
            zzhz = this.zzib;
            valueOf = String.valueOf(zzch);
            String valueOf2 = String.valueOf(obj);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 15) + String.valueOf(valueOf2).length());
            stringBuilder2.append("onDataUpdate: ");
            stringBuilder2.append(valueOf);
            stringBuilder2.append(" ");
            stringBuilder2.append(valueOf2);
            zzhz.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        this.zzie++;
        if (l != null) {
            try {
                zzex zzex = new zzex(l.longValue());
                if (z) {
                    Map hashMap = new HashMap();
                    for (Entry entry : ((Map) obj).entrySet()) {
                        hashMap.put(new zzch((String) entry.getKey()), zzjd.zza(entry.getValue(), zzir.zzfv()));
                    }
                    zza = this.zzih.zza(zzch, hashMap, zzex);
                } else {
                    zza = this.zzih.zza(zzch, zzjd.zza(obj, zzir.zzfv()), zzex);
                }
            } catch (Throwable e) {
                this.zzib.zza("FIREBASE INTERNAL ERROR", e);
                return;
            }
        } else if (z) {
            Map hashMap2 = new HashMap();
            for (Entry entry2 : ((Map) obj).entrySet()) {
                hashMap2.put(new zzch((String) entry2.getKey()), zzjd.zza(entry2.getValue(), zzir.zzfv()));
            }
            zza = this.zzih.zza(zzch, hashMap2);
        } else {
            zza = this.zzih.zzi(zzch, zzjd.zza(obj, zzir.zzfv()));
        }
        if (zza.size() > 0) {
            zzn(zzch);
        }
        zzc(zza);
    }

    public final void zza(List<String> list, List<zzba> list2, Long l) {
        zzch zzch = new zzch((List) list);
        if (this.zzib.zzfa()) {
            zzhz zzhz = this.zzib;
            String valueOf = String.valueOf(zzch);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 20);
            stringBuilder.append("onRangeMergeUpdate: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (this.zzid.zzfa()) {
            zzhz = this.zzib;
            valueOf = String.valueOf(zzch);
            String valueOf2 = String.valueOf(list2);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 21) + String.valueOf(valueOf2).length());
            stringBuilder2.append("onRangeMergeUpdate: ");
            stringBuilder2.append(valueOf);
            stringBuilder2.append(" ");
            stringBuilder2.append(valueOf2);
            zzhz.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        this.zzie++;
        List arrayList = new ArrayList(list2.size());
        for (zzba zzjh : list2) {
            arrayList.add(new zzjh(zzjh));
        }
        arrayList = l != null ? this.zzih.zza(zzch, arrayList, new zzex(l.longValue())) : this.zzih.zzb(zzch, arrayList);
        if (arrayList.size() > 0) {
            zzn(zzch);
        }
        zzc(arrayList);
    }

    public final void zzaa() {
        zza(zzby.zzhe, Boolean.valueOf(true));
    }

    public final void zzb(zzch zzch, zzja zzja, CompletionListener completionListener) {
        this.zzfs.zzb(zzch.zzbv(), zzja.getValue(true), new zzdk(this, zzch, zzja, completionListener));
    }

    public final void zzb(boolean z) {
        zza(zzby.zzhd, Boolean.valueOf(z));
    }

    public final void zzc(Runnable runnable) {
        this.zzia.zzbl();
        this.zzia.zzhh.zzc(runnable);
    }

    public final void zzc(Map<String, Object> map) {
        for (Entry entry : map.entrySet()) {
            zza(zzid.zzt((String) entry.getKey()), entry.getValue());
        }
    }

    public final zzdn zzcb() {
        return this.zzag;
    }

    public final long zzcc() {
        return this.zzhu.millis();
    }

    final boolean zzcd() {
        if (this.zzig.isEmpty()) {
            if (this.zzih.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public final void zze(zzce zzce) {
        zzc((zzby.zzhb.equals(zzce.zzbe().zzg().zzbw()) ? this.zzig : this.zzih).zzh(zzce));
    }

    public final void zzf(zzce zzce) {
        zzid zzbw = zzce.zzbe().zzg().zzbw();
        zzee zzee = (zzbw == null || !zzbw.equals(zzby.zzhb)) ? this.zzih : this.zzig;
        zzc(zzee.zzg(zzce));
    }
}
