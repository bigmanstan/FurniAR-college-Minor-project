package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class zzks {
    private static final Pattern zzus = Pattern.compile("[\\[\\]\\.#$]");
    private static final Pattern zzut = Pattern.compile("[\\[\\]\\.#\\$\\/\\u0000-\\u001F\\u007F]");

    public static void zzab(String str) throws DatabaseException {
        if ((zzus.matcher(str).find() ^ 1) == 0) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 101);
            stringBuilder.append("Invalid Firebase Database path: ");
            stringBuilder.append(str);
            stringBuilder.append(". Firebase Database paths must not contain '.', '#', '$', '[', or ']'");
            throw new DatabaseException(stringBuilder.toString());
        }
    }

    public static void zzac(String str) throws DatabaseException {
        int i;
        if (str.startsWith(".info")) {
            i = 5;
        } else if (str.startsWith("/.info")) {
            i = 6;
        } else {
            zzab(str);
            return;
        }
        zzab(str.substring(i));
    }

    public static void zzad(String str) throws DatabaseException {
        if (str != null) {
            Object obj;
            StringBuilder stringBuilder;
            if (!str.equals(".info")) {
                if (zzut.matcher(str).find()) {
                    obj = null;
                    if (obj != null) {
                        stringBuilder = new StringBuilder(String.valueOf(str).length() + 68);
                        stringBuilder.append("Invalid key: ");
                        stringBuilder.append(str);
                        stringBuilder.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
                        throw new DatabaseException(stringBuilder.toString());
                    }
                }
            }
            obj = 1;
            if (obj != null) {
                stringBuilder = new StringBuilder(String.valueOf(str).length() + 68);
                stringBuilder.append("Invalid key: ");
                stringBuilder.append(str);
                stringBuilder.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
                throw new DatabaseException(stringBuilder.toString());
            }
        }
    }

    public static void zzan(zzch zzch) throws DatabaseException {
        Object obj;
        zzid zzbw = zzch.zzbw();
        if (zzbw != null) {
            if (zzbw.zzfg().startsWith(".")) {
                obj = null;
                if (obj == null) {
                    String str = "Invalid write location: ";
                    String valueOf = String.valueOf(zzch.toString());
                    throw new DatabaseException(valueOf.length() == 0 ? str.concat(valueOf) : new String(str));
                }
            }
        }
        obj = 1;
        if (obj == null) {
            String str2 = "Invalid write location: ";
            String valueOf2 = String.valueOf(zzch.toString());
            if (valueOf2.length() == 0) {
            }
            throw new DatabaseException(valueOf2.length() == 0 ? str2.concat(valueOf2) : new String(str2));
        }
    }

    public static Map<zzch, zzja> zzb(zzch zzch, Map<String, Object> map) throws DatabaseException {
        Map treeMap = new TreeMap();
        for (Entry entry : map.entrySet()) {
            zzch zzch2 = new zzch((String) entry.getKey());
            Object value = entry.getValue();
            zzfb.zza(zzch.zzh(zzch2), value);
            String zzfg = !zzch2.isEmpty() ? zzch2.zzbz().zzfg() : "";
            if (zzfg.equals(".sv") || zzfg.equals(".value")) {
                String valueOf = String.valueOf(zzch2);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 40) + String.valueOf(zzfg).length());
                stringBuilder.append("Path '");
                stringBuilder.append(valueOf);
                stringBuilder.append("' contains disallowed child name: ");
                stringBuilder.append(zzfg);
                throw new DatabaseException(stringBuilder.toString());
            }
            Object zzc = zzfg.equals(".priority") ? zzjg.zzc(zzch2, value) : zzjd.zza(value, zzir.zzfv());
            zzg(value);
            treeMap.put(zzch2, zzc);
        }
        zzch = null;
        for (zzch zzch3 : treeMap.keySet()) {
            boolean z;
            if (zzch != null) {
                if (zzch.zzj(zzch3) >= 0) {
                    z = false;
                    zzkq.zzf(z);
                    if (zzch == null) {
                        if (!zzch.zzi(zzch3)) {
                            String valueOf2 = String.valueOf(zzch);
                            String valueOf3 = String.valueOf(zzch3);
                            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf2).length() + 42) + String.valueOf(valueOf3).length());
                            stringBuilder2.append("Path '");
                            stringBuilder2.append(valueOf2);
                            stringBuilder2.append("' is an ancestor of '");
                            stringBuilder2.append(valueOf3);
                            stringBuilder2.append("' in an update.");
                            throw new DatabaseException(stringBuilder2.toString());
                        }
                    }
                    zzch = zzch3;
                }
            }
            z = true;
            zzkq.zzf(z);
            if (zzch == null) {
                if (!zzch.zzi(zzch3)) {
                    String valueOf22 = String.valueOf(zzch);
                    String valueOf32 = String.valueOf(zzch3);
                    StringBuilder stringBuilder22 = new StringBuilder((String.valueOf(valueOf22).length() + 42) + String.valueOf(valueOf32).length());
                    stringBuilder22.append("Path '");
                    stringBuilder22.append(valueOf22);
                    stringBuilder22.append("' is an ancestor of '");
                    stringBuilder22.append(valueOf32);
                    stringBuilder22.append("' in an update.");
                    throw new DatabaseException(stringBuilder22.toString());
                }
            }
            zzch = zzch3;
        }
        return treeMap;
    }

    public static void zzg(Object obj) {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (!map.containsKey(".sv")) {
                for (Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    Object obj2 = (str == null || str.length() <= 0 || !(str.equals(".value") || str.equals(".priority") || (!str.startsWith(".") && !zzut.matcher(str).find()))) ? null : 1;
                    if (obj2 != null) {
                        zzg(entry.getValue());
                    } else {
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 68);
                        stringBuilder.append("Invalid key: ");
                        stringBuilder.append(str);
                        stringBuilder.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
                        throw new DatabaseException(stringBuilder.toString());
                    }
                }
            }
        } else if (obj instanceof List) {
            for (Object zzg : (List) obj) {
                zzg(zzg);
            }
        } else {
            if ((obj instanceof Double) || (obj instanceof Float)) {
                double doubleValue = ((Double) obj).doubleValue();
                if (Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) {
                    throw new DatabaseException("Invalid value: Value cannot be NaN, Inf or -Inf.");
                }
            }
        }
    }
}
