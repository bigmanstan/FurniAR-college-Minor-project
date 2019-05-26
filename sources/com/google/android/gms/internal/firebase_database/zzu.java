package com.google.android.gms.internal.firebase_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.firebase.database.DatabaseException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzu implements zzfw {
    private static final Charset zzbq = Charset.forName("UTF-8");
    private final SQLiteDatabase zzbr;
    private final zzhz zzbs;
    private boolean zzbt;
    private long zzbu = 0;

    public zzu(Context context, zzbz zzbz, String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");
            this.zzbs = zzbz.zzp("Persistence");
            this.zzbr = zza(context, str);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private final int zza(String str, zzch zzch) {
        String zzc = zzc(zzc(zzch));
        return this.zzbr.delete(str, "path >= ? AND path < ?", new String[]{r7, zzc});
    }

    private final Cursor zza(zzch zzch, String[] strArr) {
        String zzc = zzc(zzch);
        String zzc2 = zzc(zzc);
        String[] strArr2 = new String[(zzch.size() + 3)];
        StringBuilder stringBuilder = new StringBuilder("(");
        int i = 0;
        zzch zzch2 = zzch;
        while (!zzch2.isEmpty()) {
            stringBuilder.append("path");
            stringBuilder.append(" = ? OR ");
            strArr2[i] = zzc(zzch2);
            zzch2 = zzch2.zzby();
            i++;
        }
        stringBuilder.append("path");
        stringBuilder.append(" = ?)");
        strArr2[i] = zzc(zzch.zzbt());
        String valueOf = String.valueOf(stringBuilder.toString());
        String valueOf2 = String.valueOf(" OR (path > ? AND path < ?)");
        String concat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        strArr2[zzch.size() + 1] = zzc;
        strArr2[zzch.size() + 2] = zzc2;
        return this.zzbr.query("serverCache", strArr, concat, strArr2, null, null, "path");
    }

    private static SQLiteDatabase zza(Context context, String str) {
        try {
            SQLiteDatabase writableDatabase = new zzx(context, str).getWritableDatabase();
            writableDatabase.rawQuery("PRAGMA locking_mode = EXCLUSIVE", null).close();
            writableDatabase.beginTransaction();
            writableDatabase.endTransaction();
            return writableDatabase;
        } catch (Throwable e) {
            if (e instanceof SQLiteDatabaseLockedException) {
                throw new DatabaseException("Failed to gain exclusive lock to Firebase Database's offline persistence. This generally means you are using Firebase Database from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing FirebaseDatabase in your Application class. If you are intentionally using Firebase Database from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", e);
            }
            throw e;
        }
    }

    private static zzja zza(byte[] bArr) {
        try {
            return zzjd.zza(zzke.zzw(new String(bArr, zzbq)), zzir.zzfv());
        } catch (Throwable e) {
            String str = "Could not deserialize node: ";
            String valueOf = String.valueOf(new String(bArr, zzbq));
            throw new RuntimeException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
        }
    }

    private static String zza(zzch zzch, int i) {
        String valueOf = String.valueOf(zzc(zzch));
        String valueOf2 = String.valueOf(String.format(".part-%04d", new Object[]{Integer.valueOf(i)}));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private static String zza(Collection<Long> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (Long longValue : collection) {
            long longValue2 = longValue.longValue();
            if (obj == null) {
                stringBuilder.append(",");
            }
            obj = null;
            stringBuilder.append(longValue2);
        }
        return stringBuilder.toString();
    }

    private static List<byte[]> zza(byte[] bArr, int i) {
        i = ((bArr.length - 1) / 262144) + 1;
        List<byte[]> arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 262144;
            int min = Math.min(262144, bArr.length - i3);
            Object obj = new byte[min];
            System.arraycopy(bArr, i3, obj, 0, min);
            arrayList.add(obj);
        }
        return arrayList;
    }

    private final void zza(zzch zzch, long j, String str, byte[] bArr) {
        zzn();
        String[] strArr = new String[1];
        int i = 0;
        strArr[0] = String.valueOf(j);
        this.zzbr.delete("writes", "id = ?", strArr);
        if (bArr.length >= 262144) {
            List zza = zza(bArr, 262144);
            while (i < zza.size()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", Long.valueOf(j));
                contentValues.put("path", zzc(zzch));
                contentValues.put("type", str);
                contentValues.put("part", Integer.valueOf(i));
                contentValues.put("node", (byte[]) zza.get(i));
                this.zzbr.insertWithOnConflict("writes", null, contentValues, 5);
                i++;
            }
            return;
        }
        contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(j));
        contentValues.put("path", zzc(zzch));
        contentValues.put("type", str);
        contentValues.put("part", null);
        contentValues.put("node", bArr);
        this.zzbr.insertWithOnConflict("writes", null, contentValues, 5);
    }

    private final void zza(zzch zzch, zzch zzch2, zzgj<Long> zzgj, zzgj<Long> zzgj2, zzfx zzfx, List<zzkn<zzch, zzja>> list) {
        zzu zzu = this;
        zzgj<Long> zzgj3 = zzgj2;
        zzfx zzfx2 = zzfx;
        if (zzgj.getValue() != null) {
            if (((Integer) zzfx2.zza(Integer.valueOf(0), new zzv(this, zzgj3))).intValue() > 0) {
                zzch zzh = zzch.zzh(zzch2);
                if (zzu.zzbs.zzfa()) {
                    zzu.zzbs.zza(String.format("Need to rewrite %d nodes below path %s", new Object[]{Integer.valueOf(r1), zzh}), null, new Object[0]);
                }
                zzfx2.zza(null, new zzw(this, zzgj2, list, zzch2, zzb(zzh)));
            }
            return;
        }
        Iterator it = zzgj.zzdm().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzid zzid = (zzid) entry.getKey();
            zzfx zzd = zzfx2.zzd((zzid) entry.getKey());
            zzch zzch3 = zzch2;
            zza(zzch, zzch2.zza(zzid), (zzgj) entry.getValue(), zzgj3.zze(zzid), zzd, list);
        }
    }

    private final void zza(zzch zzch, zzja zzja, boolean z) {
        int i;
        int i2;
        long currentTimeMillis = System.currentTimeMillis();
        if (z) {
            i = 0;
            int i3 = i;
            for (zziz zziz : zzja) {
                i3 += zza("serverCache", zzch.zza(zziz.zzge()));
                i += zzc(zzch.zza(zziz.zzge()), zziz.zzd());
            }
            i2 = i;
            i = i3;
        } else {
            i = zza("serverCache", zzch);
            i2 = zzc(zzch, zzja);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Persisted a total of %d rows and deleted %d rows for a set at %s in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), zzch.toString(), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    private static byte[] zza(Object obj) {
        try {
            return zzke.zze(obj).getBytes(zzbq);
        } catch (Throwable e) {
            throw new RuntimeException("Could not serialize leaf node", e);
        }
    }

    private static byte[] zza(List<byte[]> list) {
        int i = 0;
        for (byte[] length : list) {
            i += length.length;
        }
        Object obj = new byte[i];
        i = 0;
        for (byte[] length2 : list) {
            System.arraycopy(length2, 0, obj, i, length2.length);
            i += length2.length;
        }
        return obj;
    }

    private final zzja zzb(zzch zzch) {
        long j;
        long j2;
        zzja zzam;
        zzch zzch2 = zzch;
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        Cursor zza = zza(zzch2, new String[]{"path", "value"});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        long currentTimeMillis3 = System.currentTimeMillis();
        while (zza.moveToNext()) {
            try {
                arrayList.add(zza.getString(0));
                arrayList2.add(zza.getBlob(1));
            } finally {
                zza.close();
            }
        }
        long currentTimeMillis4 = System.currentTimeMillis() - currentTimeMillis3;
        currentTimeMillis3 = System.currentTimeMillis();
        zzir zzfv = zzir.zzfv();
        Map hashMap = new HashMap();
        Object obj = zzfv;
        int i = 0;
        int i2 = i;
        while (i < arrayList2.size()) {
            zzch zzch3;
            int i3;
            if (((String) arrayList.get(i)).endsWith(".part-0000")) {
                String str = (String) arrayList.get(i);
                j = currentTimeMillis4;
                zzch3 = new zzch(str.substring(0, str.length() - 10));
                int i4 = i + 1;
                String zzc = zzc(zzch3);
                if (((String) arrayList.get(i)).startsWith(zzc)) {
                    while (i4 < arrayList.size()) {
                        j2 = currentTimeMillis2;
                        if (!((String) arrayList.get(i4)).equals(zza(zzch3, i4 - i))) {
                            break;
                        }
                        i4++;
                        currentTimeMillis2 = j2;
                    }
                    j2 = currentTimeMillis2;
                    if (i4 < arrayList.size()) {
                        String str2 = (String) arrayList.get(i4);
                        String valueOf = String.valueOf(zzc);
                        zzc = String.valueOf(".part-");
                        if (str2.startsWith(zzc.length() != 0 ? valueOf.concat(zzc) : new String(valueOf))) {
                            throw new IllegalStateException("Run did not finish with all parts");
                        }
                    }
                    i4 -= i;
                    if (r0.zzbs.zzfa()) {
                        zzhz zzhz = r0.zzbs;
                        StringBuilder stringBuilder = new StringBuilder(42);
                        stringBuilder.append("Loading split node with ");
                        stringBuilder.append(i4);
                        stringBuilder.append(" parts.");
                        zzhz.zza(stringBuilder.toString(), null, new Object[0]);
                    }
                    i4 += i;
                    zzja zza2 = zza(zza(arrayList2.subList(i, i4)));
                    i3 = i4 - 1;
                } else {
                    throw new IllegalStateException("Extracting split nodes needs to start with path prefix");
                }
            }
            j2 = currentTimeMillis2;
            j = currentTimeMillis4;
            zzja zza3 = zza((byte[]) arrayList2.get(i));
            zzch3 = new zzch((String) arrayList.get(i));
            i3 = i;
            zza2 = zza3;
            if (zzch3.zzbz() != null && zzch3.zzbz().zzfh()) {
                hashMap.put(zzch3, zza2);
            } else if (zzch3.zzi(zzch2)) {
                zzkq.zza(i2 ^ 1, "Descendants of path must come after ancestors.");
                zzam = zza2.zzam(zzch.zza(zzch3, zzch2));
            } else if (zzch2.zzi(zzch3)) {
                zzam = zzam.zzl(zzch.zza(zzch2, zzch3), zza2);
                i2 = 1;
                i = i3 + 1;
                currentTimeMillis4 = j;
                currentTimeMillis2 = j2;
            } else {
                throw new IllegalStateException(String.format("Loading an unrelated row with path %s for %s", new Object[]{zzch3, zzch2}));
            }
            i = i3 + 1;
            currentTimeMillis4 = j;
            currentTimeMillis2 = j2;
        }
        j2 = currentTimeMillis2;
        j = currentTimeMillis4;
        zzja zzja = zzam;
        for (Entry entry : hashMap.entrySet()) {
            zzja = zzja.zzl(zzch.zza(zzch2, (zzch) entry.getKey()), (zzja) entry.getValue());
        }
        long currentTimeMillis5 = System.currentTimeMillis() - currentTimeMillis3;
        long currentTimeMillis6 = System.currentTimeMillis() - currentTimeMillis;
        if (r0.zzbs.zzfa()) {
            r0.zzbs.zza(String.format("Loaded a total of %d rows for a total of %d nodes at %s in %dms (Query: %dms, Loading: %dms, Serializing: %dms)", new Object[]{Integer.valueOf(arrayList2.size()), Integer.valueOf(zzkl.zzo(zzja)), zzch2, Long.valueOf(currentTimeMillis6), Long.valueOf(j2), Long.valueOf(j), Long.valueOf(currentTimeMillis5)}), null, new Object[0]);
        }
        return zzja;
    }

    private final int zzc(zzch zzch, zzja zzja) {
        long zzn = zzkl.zzn(zzja);
        if (!(zzja instanceof zzif) || zzn <= PlaybackStateCompat.ACTION_PREPARE) {
            zzd(zzch, zzja);
            return 1;
        }
        int i = 0;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up.", new Object[]{zzch, Long.valueOf(zzn), Integer.valueOf(16384)}), null, new Object[0]);
        }
        for (zziz zziz : zzja) {
            i += zzc(zzch.zza(zziz.zzge()), zziz.zzd());
        }
        if (!zzja.zzfl().isEmpty()) {
            zzd(zzch.zza(zzid.zzfe()), zzja.zzfl());
            i++;
        }
        zzd(zzch, zzir.zzfv());
        return i + 1;
    }

    private static String zzc(zzch zzch) {
        return zzch.isEmpty() ? "/" : String.valueOf(zzch.toString()).concat("/");
    }

    private static String zzc(String str) {
        str = str.substring(0, str.length() - 1);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 1);
        stringBuilder.append(str);
        stringBuilder.append('0');
        return stringBuilder.toString();
    }

    private final void zzd(zzch zzch, zzja zzja) {
        byte[] zza = zza(zzja.getValue(true));
        if (zza.length >= 262144) {
            List zza2 = zza(zza, 262144);
            int i = 0;
            if (this.zzbs.zzfa()) {
                zzhz zzhz = this.zzbs;
                int size = zza2.size();
                StringBuilder stringBuilder = new StringBuilder(45);
                stringBuilder.append("Saving huge leaf node with ");
                stringBuilder.append(size);
                stringBuilder.append(" parts.");
                zzhz.zza(stringBuilder.toString(), null, new Object[0]);
            }
            while (i < zza2.size()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("path", zza(zzch, i));
                contentValues.put("value", (byte[]) zza2.get(i));
                this.zzbr.insertWithOnConflict("serverCache", null, contentValues, 5);
                i++;
            }
            return;
        }
        contentValues = new ContentValues();
        contentValues.put("path", zzc(zzch));
        contentValues.put("value", zza);
        this.zzbr.insertWithOnConflict("serverCache", null, contentValues, 5);
    }

    private final void zzn() {
        zzkq.zza(this.zzbt, "Transaction expected to already be in progress.");
    }

    public final void beginTransaction() {
        zzkq.zza(this.zzbt ^ true, "runInTransaction called when an existing transaction is already in progress.");
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("Starting transaction.", null, new Object[0]);
        }
        this.zzbr.beginTransaction();
        this.zzbt = true;
        this.zzbu = System.currentTimeMillis();
    }

    public final void endTransaction() {
        this.zzbr.endTransaction();
        this.zzbt = false;
        long currentTimeMillis = System.currentTimeMillis() - this.zzbu;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Transaction completed. Elapsed: %dms", new Object[]{Long.valueOf(currentTimeMillis)}), null, new Object[0]);
        }
    }

    public final void setTransactionSuccessful() {
        this.zzbr.setTransactionSuccessful();
    }

    public final zzja zza(zzch zzch) {
        return zzb(zzch);
    }

    public final Set<zzid> zza(Set<Long> set) {
        String[] strArr = new String[]{"key"};
        long currentTimeMillis = System.currentTimeMillis();
        String zza = zza((Collection) set);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zza).length() + 8);
        stringBuilder.append("id IN (");
        stringBuilder.append(zza);
        stringBuilder.append(")");
        Cursor query = this.zzbr.query(true, "trackedKeys", strArr, stringBuilder.toString(), null, null, null, null, null);
        Set<zzid> hashSet = new HashSet();
        while (query.moveToNext()) {
            try {
                hashSet.add(zzid.zzt(query.getString(0)));
            } catch (Throwable th) {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[]{Integer.valueOf(hashSet.size()), set.toString(), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
        query.close();
        return hashSet;
    }

    public final void zza(long j) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.zzbr.delete("writes", "id = ?", new String[]{String.valueOf(j)});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Deleted %d write(s) with writeId %d in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zza(long j, Set<zzid> set) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        this.zzbr.delete("trackedKeys", "id = ?", new String[]{valueOf});
        for (zzid zzid : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Long.valueOf(j));
            contentValues.put("key", zzid.zzfg());
            this.zzbr.insertWithOnConflict("trackedKeys", null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Set %d tracked query keys for tracked query %d in %dms", new Object[]{Integer.valueOf(set.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zza(long j, Set<zzid> set, Set<zzid> set2) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        String str = "id = ? AND key = ?";
        String valueOf = String.valueOf(j);
        for (zzid zzid : set2) {
            this.zzbr.delete("trackedKeys", str, new String[]{valueOf, zzid.zzfg()});
        }
        for (zzid zzid2 : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Long.valueOf(j));
            contentValues.put("key", zzid2.zzfg());
            this.zzbr.insertWithOnConflict("trackedKeys", null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[]{Integer.valueOf(set.size()), Integer.valueOf(set2.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zza(zzch zzch, zzbv zzbv) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = zzbv.iterator();
        int i = 0;
        int i2 = i;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            i += zza("serverCache", zzch.zzh((zzch) entry.getKey()));
            i2 += zzc(zzch.zzh((zzch) entry.getKey()), (zzja) entry.getValue());
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), zzch.toString(), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zza(zzch zzch, zzbv zzbv, long j) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        zzch zzch2 = zzch;
        long j2 = j;
        zza(zzch2, j2, "m", zza(zzbv.zzd(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Persisted user merge in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zza(zzch zzch, zzfx zzfx) {
        zzu zzu = this;
        zzch zzch2 = zzch;
        zzfx zzfx2 = zzfx;
        if (zzfx.zzdh()) {
            int i;
            int i2;
            zzn();
            long currentTimeMillis = System.currentTimeMillis();
            Cursor zza = zza(zzch2, new String[]{"rowid", "path"});
            zzgj zzgj = new zzgj(null);
            zzgj zzgj2 = new zzgj(null);
            while (zza.moveToNext()) {
                zzhz zzhz;
                String valueOf;
                StringBuilder stringBuilder;
                long j = zza.getLong(0);
                zzch zzch3 = new zzch(zza.getString(1));
                String valueOf2;
                if (zzch2.zzi(zzch3)) {
                    zzch zza2 = zzch.zza(zzch2, zzch3);
                    if (zzfx2.zzv(zza2)) {
                        zzgj = zzgj.zzb(zza2, Long.valueOf(j));
                    } else if (zzfx2.zzw(zza2)) {
                        zzgj2 = zzgj2.zzb(zza2, Long.valueOf(j));
                    } else {
                        zzhz = zzu.zzbs;
                        valueOf = String.valueOf(zzch);
                        valueOf2 = String.valueOf(zzch3);
                        stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 88) + String.valueOf(valueOf2).length());
                        stringBuilder.append("We are pruning at ");
                        stringBuilder.append(valueOf);
                        stringBuilder.append(" and have data at ");
                        stringBuilder.append(valueOf2);
                        valueOf = " that isn't marked for pruning or keeping. Ignoring.";
                    }
                } else {
                    zzhz = zzu.zzbs;
                    valueOf = String.valueOf(zzch);
                    valueOf2 = String.valueOf(zzch3);
                    stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 67) + String.valueOf(valueOf2).length());
                    stringBuilder.append("We are pruning at ");
                    stringBuilder.append(valueOf);
                    stringBuilder.append(" but we have data stored higher up at ");
                    stringBuilder.append(valueOf2);
                    valueOf = ". Ignoring.";
                }
                stringBuilder.append(valueOf);
                zzhz.zzb(stringBuilder.toString(), null);
            }
            if (zzgj.isEmpty()) {
                i = 0;
                i2 = i;
            } else {
                List arrayList = new ArrayList();
                zza(zzch, zzch.zzbt(), zzgj, zzgj2, zzfx, arrayList);
                Collection values = zzgj.values();
                String zza3 = zza(values);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(zza3).length() + 11);
                stringBuilder2.append("rowid IN (");
                stringBuilder2.append(zza3);
                stringBuilder2.append(")");
                zzu.zzbr.delete("serverCache", stringBuilder2.toString(), null);
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList2.get(i3);
                    i3++;
                    zzkn zzkn = (zzkn) obj;
                    zzc(zzch2.zzh((zzch) zzkn.getFirst()), (zzja) zzkn.zzgv());
                }
                i = values.size();
                i2 = arrayList.size();
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (zzu.zzbs.zzfa()) {
                zzu.zzbs.zza(String.format("Pruned %d rows with %d nodes resaved in %dms", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
            }
        }
    }

    public final void zza(zzch zzch, zzja zzja) {
        zzn();
        zza(zzch, zzja, false);
    }

    public final void zza(zzch zzch, zzja zzja, long j) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        zzch zzch2 = zzch;
        long j2 = j;
        zza(zzch2, j2, "o", zza(zzja.getValue(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Persisted user overwrite in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zza(zzgb zzgb) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(zzgb.id));
        contentValues.put("path", zzc(zzgb.zznp.zzg()));
        contentValues.put("queryParams", zzgb.zznp.zzen().zzel());
        contentValues.put("lastUse", Long.valueOf(zzgb.zznq));
        contentValues.put("complete", Boolean.valueOf(zzgb.zznr));
        contentValues.put("active", Boolean.valueOf(zzgb.zzns));
        this.zzbr.insertWithOnConflict("trackedQueries", null, contentValues, 5);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Saved new tracked query in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }

    public final void zzb(long j) {
        zzn();
        String valueOf = String.valueOf(j);
        this.zzbr.delete("trackedQueries", "id = ?", new String[]{valueOf});
        this.zzbr.delete("trackedKeys", "id = ?", new String[]{valueOf});
    }

    public final void zzb(zzch zzch, zzja zzja) {
        zzn();
        zza(zzch, zzja, true);
    }

    public final void zzc(long j) {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("active", Boolean.valueOf(false));
        contentValues.put("lastUse", Long.valueOf(j));
        this.zzbr.updateWithOnConflict("trackedQueries", contentValues, "active = 1", new String[0], 5);
        j = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Reset active tracked queries in %dms", new Object[]{Long.valueOf(j)}), null, new Object[0]);
        }
    }

    public final Set<zzid> zzd(long j) {
        return zza(Collections.singleton(Long.valueOf(j)));
    }

    public final List<zzfa> zzj() {
        String[] strArr = new String[]{"id", "path", "type", "part", "node"};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.zzbr.query("writes", strArr, null, null, null, null, "id, part");
        List<zzfa> arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                byte[] blob;
                Object zzfa;
                long j = query.getLong(0);
                zzch zzch = new zzch(query.getString(1));
                String string = query.getString(2);
                if (query.isNull(3)) {
                    blob = query.getBlob(4);
                } else {
                    List arrayList2 = new ArrayList();
                    do {
                        arrayList2.add(query.getBlob(4));
                        if (!query.moveToNext()) {
                            break;
                        }
                    } while (query.getLong(0) == j);
                    query.moveToPrevious();
                    blob = zza(arrayList2);
                }
                Object zzw = zzke.zzw(new String(blob, zzbq));
                if ("o".equals(string)) {
                    zzfa zzfa2 = new zzfa(j, zzch, zzjd.zza(zzw, zzir.zzfv()), true);
                } else if ("m".equals(string)) {
                    zzfa = new zzfa(j, zzch, zzbv.zzf((Map) zzw));
                } else {
                    String str = "Got invalid write type: ";
                    String valueOf = String.valueOf(string);
                    throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                }
                arrayList.add(zzfa);
            } catch (Throwable e) {
                throw new RuntimeException("Failed to load writes", e);
            } catch (Throwable th) {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Loaded %d writes in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
        query.close();
        return arrayList;
    }

    public final long zzk() {
        Cursor rawQuery = this.zzbr.rawQuery(String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[]{"value", "path", "serverCache"}), null);
        try {
            if (rawQuery.moveToFirst()) {
                long j = rawQuery.getLong(0);
                return j;
            }
            throw new IllegalStateException("Couldn't read database result!");
        } finally {
            rawQuery.close();
        }
    }

    public final List<zzgb> zzl() {
        String[] strArr = new String[]{"id", "path", "queryParams", "lastUse", "complete", "active"};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.zzbr.query("trackedQueries", strArr, null, null, null, null, "id");
        List<zzgb> arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzgb(query.getLong(0), new zzhh(new zzch(query.getString(1)), zzhe.zzh(zzke.zzv(query.getString(2)))), query.getLong(3), query.getInt(4) != 0, query.getInt(5) != 0));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (r0.zzbs.zzfa()) {
            r0.zzbs.zza(String.format("Loaded %d tracked queries in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
        query.close();
        return arrayList;
    }

    public final void zzm() {
        zzn();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.zzbr.delete("writes", null, null);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbs.zzfa()) {
            this.zzbs.zza(String.format("Deleted %d (all) write(s) in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(currentTimeMillis2)}), null, new Object[0]);
        }
    }
}
