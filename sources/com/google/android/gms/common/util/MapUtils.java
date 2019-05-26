package com.google.android.gms.common.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    public static <K, V> K getKeyFromMap(Map<K, V> map, K k) {
        if (map.containsKey(k)) {
            for (K next : map.keySet()) {
                if (next.equals(k)) {
                    return next;
                }
            }
        }
        return null;
    }

    public static void writeStringMapToJson(StringBuilder stringBuilder, HashMap<String, String> hashMap) {
        stringBuilder.append("{");
        Object obj = 1;
        for (String str : hashMap.keySet()) {
            String str2;
            if (obj == null) {
                stringBuilder.append(",");
            } else {
                obj = null;
            }
            String str3 = (String) hashMap.get(str2);
            stringBuilder.append("\"");
            stringBuilder.append(str2);
            stringBuilder.append("\":");
            if (str3 == null) {
                str2 = "null";
            } else {
                stringBuilder.append("\"");
                stringBuilder.append(str3);
                str2 = "\"";
            }
            stringBuilder.append(str2);
        }
        stringBuilder.append("}");
    }
}
