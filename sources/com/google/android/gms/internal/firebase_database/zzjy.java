package com.google.android.gms.internal.firebase_database;

import android.util.Base64;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

final class zzjy {
    private String protocol = null;
    private URI zztc = null;
    private String zzts = null;
    private Map<String, String> zztt = null;

    public zzjy(URI uri, String str, Map<String, String> map) {
        this.zztc = uri;
        this.protocol = null;
        this.zztt = map;
        byte[] bArr = new byte[16];
        for (int i = 0; i < 16; i++) {
            bArr[i] = (byte) ((int) ((Math.random() * 255.0d) + 0.0d));
        }
        this.zzts = Base64.encodeToString(bArr, 2);
    }

    public final byte[] zzgo() {
        Object obj;
        String path = this.zztc.getPath();
        String query = this.zztc.getQuery();
        path = String.valueOf(path);
        if (query == null) {
            obj = "";
        } else {
            String str = "?";
            query = String.valueOf(query);
            obj = query.length() != 0 ? str.concat(query) : new String(str);
        }
        query = String.valueOf(obj);
        path = query.length() != 0 ? path.concat(query) : new String(path);
        obj = this.zztc.getHost();
        if (this.zztc.getPort() != -1) {
            query = String.valueOf(obj);
            int port = this.zztc.getPort();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(query).length() + 12);
            stringBuilder.append(query);
            stringBuilder.append(":");
            stringBuilder.append(port);
            obj = stringBuilder.toString();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Host", obj);
        linkedHashMap.put("Upgrade", "websocket");
        linkedHashMap.put("Connection", "Upgrade");
        linkedHashMap.put("Sec-WebSocket-Version", "13");
        linkedHashMap.put("Sec-WebSocket-Key", this.zzts);
        if (this.protocol != null) {
            linkedHashMap.put("Sec-WebSocket-Protocol", this.protocol);
        }
        if (this.zztt != null) {
            for (String str2 : this.zztt.keySet()) {
                if (!linkedHashMap.containsKey(str2)) {
                    linkedHashMap.put(str2, (String) this.zztt.get(str2));
                }
            }
        }
        StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(path).length() + 15);
        stringBuilder2.append("GET ");
        stringBuilder2.append(path);
        stringBuilder2.append(" HTTP/1.1\r\n");
        path = String.valueOf(stringBuilder2.toString());
        obj = new String();
        for (String str3 : linkedHashMap.keySet()) {
            query = String.valueOf(obj);
            String str4 = (String) linkedHashMap.get(str3);
            StringBuilder stringBuilder3 = new StringBuilder(((String.valueOf(query).length() + 4) + String.valueOf(str3).length()) + String.valueOf(str4).length());
            stringBuilder3.append(query);
            stringBuilder3.append(str3);
            stringBuilder3.append(": ");
            stringBuilder3.append(str4);
            stringBuilder3.append("\r\n");
            obj = stringBuilder3.toString();
        }
        query = String.valueOf(obj);
        path = String.valueOf(query.length() != 0 ? path.concat(query) : new String(path)).concat("\r\n");
        obj = new byte[path.getBytes().length];
        System.arraycopy(path.getBytes(), 0, obj, 0, path.getBytes().length);
        return obj;
    }
}
