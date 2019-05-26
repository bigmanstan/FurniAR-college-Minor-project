package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class zzch implements Comparable<zzch>, Iterable<zzid> {
    private static final zzch zzhs = new zzch("");
    private final int end;
    private final int start;
    private final zzid[] zzhr;

    public zzch(String str) {
        String[] split = str.split("/");
        int length = split.length;
        int i = 0;
        int i2 = i;
        while (i < length) {
            if (split[i].length() > 0) {
                i2++;
            }
            i++;
        }
        this.zzhr = new zzid[i2];
        length = split.length;
        i = 0;
        i2 = i;
        while (i < length) {
            String str2 = split[i];
            if (str2.length() > 0) {
                int i3 = i2 + 1;
                this.zzhr[i2] = zzid.zzt(str2);
                i2 = i3;
            }
            i++;
        }
        this.start = 0;
        this.end = this.zzhr.length;
    }

    public zzch(List<String> list) {
        this.zzhr = new zzid[list.size()];
        int i = 0;
        for (String zzt : list) {
            int i2 = i + 1;
            this.zzhr[i] = zzid.zzt(zzt);
            i = i2;
        }
        this.start = 0;
        this.end = list.size();
    }

    public zzch(zzid... zzidArr) {
        this.zzhr = (zzid[]) Arrays.copyOf(zzidArr, zzidArr.length);
        this.start = 0;
        this.end = zzidArr.length;
    }

    private zzch(zzid[] zzidArr, int i, int i2) {
        this.zzhr = zzidArr;
        this.start = i;
        this.end = i2;
    }

    public static zzch zza(zzch zzch, zzch zzch2) {
        while (true) {
            zzid zzbw = zzch.zzbw();
            zzid zzbw2 = zzch2.zzbw();
            if (zzbw == null) {
                return zzch2;
            }
            if (zzbw.equals(zzbw2)) {
                zzch = zzch.zzbx();
                zzch2 = zzch2.zzbx();
            } else {
                String valueOf = String.valueOf(zzch2);
                String valueOf2 = String.valueOf(zzch);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 37) + String.valueOf(valueOf2).length());
                stringBuilder.append("INTERNAL ERROR: ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" is not contained in ");
                stringBuilder.append(valueOf2);
                throw new DatabaseException(stringBuilder.toString());
            }
        }
    }

    public static zzch zzbt() {
        return zzhs;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return zzj((zzch) obj);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzch)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzch zzch = (zzch) obj;
        if (size() != zzch.size()) {
            return false;
        }
        int i = this.start;
        int i2 = zzch.start;
        while (i < this.end && i2 < zzch.end) {
            if (!this.zzhr[i].equals(zzch.zzhr[i2])) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        for (int i2 = this.start; i2 < this.end; i2++) {
            i = (i * 37) + this.zzhr[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.start >= this.end;
    }

    public final Iterator<zzid> iterator() {
        return new zzci(this);
    }

    public final int size() {
        return this.end - this.start;
    }

    public final String toString() {
        if (isEmpty()) {
            return "/";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            stringBuilder.append("/");
            stringBuilder.append(this.zzhr[i].zzfg());
        }
        return stringBuilder.toString();
    }

    public final zzch zza(zzid zzid) {
        int size = size();
        int i = size + 1;
        Object obj = new zzid[i];
        System.arraycopy(this.zzhr, this.start, obj, 0, size);
        obj[size] = zzid;
        return new zzch(obj, 0, i);
    }

    public final String zzbu() {
        if (isEmpty()) {
            return "/";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            if (i > this.start) {
                stringBuilder.append("/");
            }
            stringBuilder.append(this.zzhr[i].zzfg());
        }
        return stringBuilder.toString();
    }

    public final List<String> zzbv() {
        List<String> arrayList = new ArrayList(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            arrayList.add(((zzid) it.next()).zzfg());
        }
        return arrayList;
    }

    public final zzid zzbw() {
        return isEmpty() ? null : this.zzhr[this.start];
    }

    public final zzch zzbx() {
        int i = this.start;
        if (!isEmpty()) {
            i++;
        }
        return new zzch(this.zzhr, i, this.end);
    }

    public final zzch zzby() {
        return isEmpty() ? null : new zzch(this.zzhr, this.start, this.end - 1);
    }

    public final zzid zzbz() {
        return !isEmpty() ? this.zzhr[this.end - 1] : null;
    }

    public final zzch zzh(zzch zzch) {
        int size = size() + zzch.size();
        Object obj = new zzid[size];
        System.arraycopy(this.zzhr, this.start, obj, 0, size());
        System.arraycopy(zzch.zzhr, zzch.start, obj, size(), zzch.size());
        return new zzch(obj, 0, size);
    }

    public final boolean zzi(zzch zzch) {
        if (size() > zzch.size()) {
            return false;
        }
        int i = this.start;
        int i2 = zzch.start;
        while (i < this.end) {
            if (!this.zzhr[i].equals(zzch.zzhr[i2])) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public final int zzj(zzch zzch) {
        int i = this.start;
        int i2 = zzch.start;
        while (i < this.end && i2 < zzch.end) {
            int zzi = this.zzhr[i].zzi(zzch.zzhr[i2]);
            if (zzi != 0) {
                return zzi;
            }
            i++;
            i2++;
        }
        return (i == this.end && i2 == zzch.end) ? 0 : i == this.end ? -1 : 1;
    }
}
