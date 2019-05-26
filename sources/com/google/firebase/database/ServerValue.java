package com.google.firebase.database;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerValue {
    @NonNull
    public static final Map<String, String> TIMESTAMP;

    static {
        Map hashMap = new HashMap();
        hashMap.put(".sv", "timestamp");
        TIMESTAMP = Collections.unmodifiableMap(hashMap);
    }
}
