package com.google.android.gms.internal.firebase_database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class zzdu {
    public static zzbv zza(zzbv zzbv, Map<String, Object> map) {
        zzbv zzbf = zzbv.zzbf();
        Iterator it = zzbv.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzbf = zzbf.zze((zzch) entry.getKey(), zza((zzja) entry.getValue(), (Map) map));
        }
        return zzbf;
    }

    public static zzja zza(zzja zzja, Map<String, Object> map) {
        Object value = zzja.zzfl().getValue();
        if (value instanceof Map) {
            Map map2 = (Map) value;
            if (map2.containsKey(".sv")) {
                value = map.get((String) map2.get(".sv"));
            }
        }
        zzja zzc = zzjg.zzc(null, value);
        if (zzja.zzfk()) {
            Object value2 = zzja.getValue();
            if (value2 instanceof Map) {
                Map map3 = (Map) value2;
                if (map3.containsKey(".sv")) {
                    String str = (String) map3.get(".sv");
                    if (map.containsKey(str)) {
                        value2 = map.get(str);
                    }
                }
            }
            if (value2.equals(zzja.getValue())) {
                if (zzc.equals(zzja.zzfl())) {
                    return zzja;
                }
            }
            return zzjd.zza(value2, zzc);
        } else if (zzja.isEmpty()) {
            return zzja;
        } else {
            zzif zzif = (zzif) zzja;
            zzdx zzdx = new zzdx(zzif);
            zzif.zza(new zzdw(map, zzdx), false);
            return !zzdx.zzcg().zzfl().equals(zzc) ? zzdx.zzcg().zzf(zzc) : zzdx.zzcg();
        }
    }

    public static Map<String, Object> zza(zzkf zzkf) {
        Map<String, Object> hashMap = new HashMap();
        hashMap.put("timestamp", Long.valueOf(zzkf.millis()));
        return hashMap;
    }
}
