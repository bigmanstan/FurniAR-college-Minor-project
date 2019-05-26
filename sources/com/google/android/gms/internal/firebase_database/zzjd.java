package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzjd {
    public static zzja zza(Object obj, zzja zzja) throws DatabaseException {
        try {
            Map map;
            if (obj instanceof Map) {
                map = (Map) obj;
                if (map.containsKey(".priority")) {
                    zzja = zzjg.zzc(null, map.get(".priority"));
                }
                if (map.containsKey(".value")) {
                    obj = map.get(".value");
                }
            }
            if (obj == null) {
                return zzir.zzfv();
            }
            if (obj instanceof String) {
                return new zzji((String) obj, zzja);
            }
            if (obj instanceof Long) {
                return new zziy((Long) obj, zzja);
            }
            if (obj instanceof Integer) {
                return new zziy(Long.valueOf((long) ((Integer) obj).intValue()), zzja);
            }
            if (obj instanceof Double) {
                return new zziq((Double) obj, zzja);
            }
            if (obj instanceof Boolean) {
                return new zzic((Boolean) obj, zzja);
            }
            if (!(obj instanceof Map)) {
                if (!(obj instanceof List)) {
                    String str = "Failed to parse node with class ";
                    String valueOf = String.valueOf(obj.getClass().toString());
                    throw new DatabaseException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                }
            }
            String stringBuilder;
            zzja zza;
            if (obj instanceof Map) {
                Map map2 = (Map) obj;
                if (map2.containsKey(".sv")) {
                    return new zzip(map2, zzja);
                }
                map = new HashMap(map2.size());
                for (String stringBuilder2 : map2.keySet()) {
                    if (!stringBuilder2.startsWith(".")) {
                        zza = zza(map2.get(stringBuilder2), zzir.zzfv());
                        if (!zza.isEmpty()) {
                            map.put(zzid.zzt(stringBuilder2), zza);
                        }
                    }
                }
            } else {
                List list = (List) obj;
                map = new HashMap(list.size());
                for (int i = 0; i < list.size(); i++) {
                    StringBuilder stringBuilder3 = new StringBuilder(11);
                    stringBuilder3.append(i);
                    stringBuilder2 = stringBuilder3.toString();
                    zza = zza(list.get(i), zzir.zzfv());
                    if (!zza.isEmpty()) {
                        map.put(zzid.zzt(stringBuilder2), zza);
                    }
                }
            }
            return map.isEmpty() ? zzir.zzfv() : new zzif(Builder.fromMap(map, zzif.zzrc), zzja);
        } catch (Throwable e) {
            throw new DatabaseException("Failed to parse node", e);
        }
    }
}
