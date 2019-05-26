package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class zzfb {
    private final List<String> zzlw = new ArrayList();
    private int zzlx;

    private zzfb(zzch zzch) throws DatabaseException {
        int i = 0;
        this.zzlx = 0;
        Iterator it = zzch.iterator();
        while (it.hasNext()) {
            this.zzlw.add(((zzid) it.next()).zzfg());
        }
        this.zzlx = Math.max(1, this.zzlw.size());
        while (i < this.zzlw.size()) {
            this.zzlx += zza((CharSequence) this.zzlw.get(i));
            i++;
        }
        zzcs();
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt <= '') {
                i2++;
            } else if (charAt <= '߿') {
                i2 += 2;
            } else if (Character.isHighSurrogate(charAt)) {
                i2 += 4;
                i++;
            } else {
                i2 += 3;
            }
            i++;
        }
        return i2;
    }

    public static void zza(zzch zzch, Object obj) throws DatabaseException {
        new zzfb(zzch).zzc(obj);
    }

    private final void zzc(Object obj) throws DatabaseException {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            for (String str : map.keySet()) {
                if (!str.startsWith(".")) {
                    zzs(str);
                    zzc(map.get(str));
                    zzcr();
                }
            }
            return;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                zzs(Integer.toString(i));
                zzc(list.get(i));
                zzcr();
            }
        }
    }

    private final String zzcr() {
        String str = (String) this.zzlw.remove(this.zzlw.size() - 1);
        this.zzlx -= zza(str);
        if (this.zzlw.size() > 0) {
            this.zzlx--;
        }
        return str;
    }

    private final void zzcs() throws DatabaseException {
        if (this.zzlx > 768) {
            int i = this.zzlx;
            StringBuilder stringBuilder = new StringBuilder(56);
            stringBuilder.append("Data has a key path longer than 768 bytes (");
            stringBuilder.append(i);
            stringBuilder.append(").");
            throw new DatabaseException(stringBuilder.toString());
        } else if (this.zzlw.size() > 32) {
            Object obj;
            String str;
            String valueOf = String.valueOf("Path specified exceeds the maximum depth that can be written (32) or object contains a cycle ");
            if (this.zzlw.size() == 0) {
                obj = "";
            } else {
                str = "/";
                List list = this.zzlw;
                StringBuilder stringBuilder2 = new StringBuilder();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (i2 > 0) {
                        stringBuilder2.append(str);
                    }
                    stringBuilder2.append((String) list.get(i2));
                }
                str = stringBuilder2.toString();
                stringBuilder2 = new StringBuilder(String.valueOf(str).length() + 10);
                stringBuilder2.append("in path '");
                stringBuilder2.append(str);
                stringBuilder2.append("'");
                obj = stringBuilder2.toString();
            }
            str = String.valueOf(obj);
            throw new DatabaseException(str.length() != 0 ? valueOf.concat(str) : new String(valueOf));
        }
    }

    private final void zzs(String str) throws DatabaseException {
        if (this.zzlw.size() > 0) {
            this.zzlx++;
        }
        this.zzlw.add(str);
        this.zzlx += zza(str);
        zzcs();
    }
}
