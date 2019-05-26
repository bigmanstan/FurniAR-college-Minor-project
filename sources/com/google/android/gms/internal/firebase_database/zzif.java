package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzif implements zzja {
    public static Comparator<zzid> zzrc = new zzig();
    private final ImmutableSortedMap<zzid, zzja> zzob;
    private final zzja zzrd;
    private String zzre;

    protected zzif() {
        this.zzre = null;
        this.zzob = Builder.emptyMap(zzrc);
        this.zzrd = zzir.zzfv();
    }

    protected zzif(ImmutableSortedMap<zzid, zzja> immutableSortedMap, zzja zzja) {
        this.zzre = null;
        if (immutableSortedMap.isEmpty()) {
            if (!zzja.isEmpty()) {
                throw new IllegalArgumentException("Can't create empty ChildrenNode with priority!");
            }
        }
        this.zzrd = zzja;
        this.zzob = immutableSortedMap;
    }

    private static void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append(" ");
        }
    }

    private final void zzb(StringBuilder stringBuilder, int i) {
        String str;
        if (this.zzob.isEmpty() && this.zzrd.isEmpty()) {
            str = "{ }";
        } else {
            stringBuilder.append("{\n");
            Iterator it = this.zzob.iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                int i2 = i + 2;
                zza(stringBuilder, i2);
                stringBuilder.append(((zzid) entry.getKey()).zzfg());
                stringBuilder.append("=");
                if (entry.getValue() instanceof zzif) {
                    ((zzif) entry.getValue()).zzb(stringBuilder, i2);
                } else {
                    stringBuilder.append(((zzja) entry.getValue()).toString());
                }
                stringBuilder.append("\n");
            }
            if (!this.zzrd.isEmpty()) {
                zza(stringBuilder, i + 2);
                stringBuilder.append(".priority=");
                stringBuilder.append(this.zzrd.toString());
                stringBuilder.append("\n");
            }
            zza(stringBuilder, i);
            str = "}";
        }
        stringBuilder.append(str);
    }

    public /* synthetic */ int compareTo(Object obj) {
        return zzg((zzja) obj);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzif)) {
            return false;
        }
        zzif zzif = (zzif) obj;
        if (!zzfl().equals(zzif.zzfl()) || this.zzob.size() != zzif.zzob.size()) {
            return false;
        }
        Iterator it = this.zzob.iterator();
        Iterator it2 = zzif.zzob.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Entry entry = (Entry) it.next();
            Entry entry2 = (Entry) it2.next();
            if (((zzid) entry.getKey()).equals(entry2.getKey())) {
                if (!((zzja) entry.getValue()).equals(entry2.getValue())) {
                }
            }
            return false;
        }
        if (!it.hasNext() && !it2.hasNext()) {
            return true;
        }
        throw new IllegalStateException("Something went wrong internally.");
    }

    public int getChildCount() {
        return this.zzob.size();
    }

    public Object getValue() {
        return getValue(false);
    }

    public Object getValue(boolean z) {
        if (isEmpty()) {
            return null;
        }
        Map hashMap = new HashMap();
        Iterator it = this.zzob.iterator();
        int i = 0;
        int i2 = 1;
        int i3 = 0;
        int i4 = i3;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String zzfg = ((zzid) entry.getKey()).zzfg();
            hashMap.put(zzfg, ((zzja) entry.getValue()).getValue(z));
            i3++;
            if (i2 != 0) {
                if (zzfg.length() <= 1 || zzfg.charAt(0) != '0') {
                    Integer zzaa = zzkq.zzaa(zzfg);
                    if (zzaa != null && zzaa.intValue() >= 0) {
                        if (zzaa.intValue() > i4) {
                            i4 = zzaa.intValue();
                        }
                    }
                }
                i2 = 0;
            }
        }
        if (z || i2 == 0 || i4 >= i3 * 2) {
            if (z && !this.zzrd.isEmpty()) {
                hashMap.put(".priority", this.zzrd.getValue());
            }
            return hashMap;
        }
        List arrayList = new ArrayList(i4 + 1);
        while (i <= i4) {
            StringBuilder stringBuilder = new StringBuilder(11);
            stringBuilder.append(i);
            arrayList.add(hashMap.get(stringBuilder.toString()));
            i++;
        }
        return arrayList;
    }

    public int hashCode() {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            zziz zziz = (zziz) it.next();
            i = (((i * 31) + zziz.zzge().hashCode()) * 17) + zziz.zzd().hashCode();
        }
        return i;
    }

    public boolean isEmpty() {
        return this.zzob.isEmpty();
    }

    public Iterator<zziz> iterator() {
        return new zzij(this.zzob.iterator());
    }

    public Iterator<zziz> reverseIterator() {
        return new zzij(this.zzob.reverseIterator());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        zzb(stringBuilder, 0);
        return stringBuilder.toString();
    }

    public String zza(zzjc zzjc) {
        if (zzjc == zzjc.V1) {
            int i;
            StringBuilder stringBuilder = new StringBuilder();
            if (!this.zzrd.isEmpty()) {
                stringBuilder.append("priority:");
                stringBuilder.append(this.zzrd.zza(zzjc.V1));
                stringBuilder.append(":");
            }
            List arrayList = new ArrayList();
            Iterator it = iterator();
            int i2 = 0;
            loop0:
            while (true) {
                i = 0;
                while (it.hasNext()) {
                    zziz zziz = (zziz) it.next();
                    arrayList.add(zziz);
                    if (i != 0 || !zziz.zzd().zzfl().isEmpty()) {
                        i = 1;
                    }
                }
                break loop0;
            }
            if (i != 0) {
                Collections.sort(arrayList, zzjf.zzgf());
            }
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                zziz zziz2 = (zziz) obj;
                String zzfj = zziz2.zzd().zzfj();
                if (!zzfj.equals("")) {
                    stringBuilder.append(":");
                    stringBuilder.append(zziz2.zzge().zzfg());
                    stringBuilder.append(":");
                    stringBuilder.append(zzfj);
                }
            }
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Hashes on children nodes only supported for V1");
    }

    public final void zza(zzii zzii, boolean z) {
        if (z) {
            if (!zzfl().isEmpty()) {
                this.zzob.inOrderTraversal(new zzih(this, zzii));
                return;
            }
        }
        this.zzob.inOrderTraversal(zzii);
    }

    public zzja zzam(zzch zzch) {
        zzid zzbw = zzch.zzbw();
        return zzbw == null ? this : zzm(zzbw).zzam(zzch.zzbx());
    }

    public zzja zze(zzid zzid, zzja zzja) {
        if (zzid.zzfh()) {
            return zzf(zzja);
        }
        ImmutableSortedMap immutableSortedMap = this.zzob;
        if (immutableSortedMap.containsKey(zzid)) {
            immutableSortedMap = immutableSortedMap.remove(zzid);
        }
        if (!zzja.isEmpty()) {
            immutableSortedMap = immutableSortedMap.insert(zzid, zzja);
        }
        return immutableSortedMap.isEmpty() ? zzir.zzfv() : new zzif(immutableSortedMap, this.zzrd);
    }

    public zzja zzf(zzja zzja) {
        return this.zzob.isEmpty() ? zzir.zzfv() : new zzif(this.zzob, zzja);
    }

    public String zzfj() {
        if (this.zzre == null) {
            String zza = zza(zzjc.V1);
            this.zzre = zza.isEmpty() ? "" : zzkq.zzy(zza);
        }
        return this.zzre;
    }

    public boolean zzfk() {
        return false;
    }

    public zzja zzfl() {
        return this.zzrd;
    }

    public final zzid zzfm() {
        return (zzid) this.zzob.getMinKey();
    }

    public final zzid zzfn() {
        return (zzid) this.zzob.getMaxKey();
    }

    public int zzg(zzja zzja) {
        return isEmpty() ? zzja.isEmpty() ? 0 : -1 : (zzja.zzfk() || zzja.isEmpty()) ? 1 : zzja == zzja.zzsi ? -1 : 0;
    }

    public boolean zzk(zzid zzid) {
        return !zzm(zzid).isEmpty();
    }

    public zzid zzl(zzid zzid) {
        return (zzid) this.zzob.getPredecessorKey(zzid);
    }

    public zzja zzl(zzch zzch, zzja zzja) {
        zzid zzbw = zzch.zzbw();
        return zzbw == null ? zzja : zzbw.zzfh() ? zzf(zzja) : zze(zzbw, zzm(zzbw).zzl(zzch.zzbx(), zzja));
    }

    public zzja zzm(zzid zzid) {
        return (!zzid.zzfh() || this.zzrd.isEmpty()) ? this.zzob.containsKey(zzid) ? (zzja) this.zzob.get(zzid) : zzir.zzfv() : this.zzrd;
    }
}
