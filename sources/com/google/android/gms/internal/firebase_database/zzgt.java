package com.google.android.gms.internal.firebase_database;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzgt<T> {
    public T value;
    public Map<zzid, zzgt<T>> zzkk = new HashMap();

    final String toString(String str) {
        String valueOf = String.valueOf(this.value);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 10) + String.valueOf(valueOf).length());
        stringBuilder.append(str);
        stringBuilder.append("<value>: ");
        stringBuilder.append(valueOf);
        stringBuilder.append("\n");
        valueOf = stringBuilder.toString();
        if (this.zzkk.isEmpty()) {
            stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 7) + String.valueOf(str).length());
            stringBuilder.append(valueOf);
            stringBuilder.append(str);
            stringBuilder.append("<empty>");
            return stringBuilder.toString();
        }
        for (Entry entry : this.zzkk.entrySet()) {
            valueOf = String.valueOf(valueOf);
            String valueOf2 = String.valueOf(entry.getKey());
            String zzgt = ((zzgt) entry.getValue()).toString(String.valueOf(str).concat("\t"));
            StringBuilder stringBuilder2 = new StringBuilder((((String.valueOf(valueOf).length() + 3) + String.valueOf(str).length()) + String.valueOf(valueOf2).length()) + String.valueOf(zzgt).length());
            stringBuilder2.append(valueOf);
            stringBuilder2.append(str);
            stringBuilder2.append(valueOf2);
            stringBuilder2.append(":\n");
            stringBuilder2.append(zzgt);
            stringBuilder2.append("\n");
            valueOf = stringBuilder2.toString();
        }
        return valueOf;
    }
}
