package com.google.firebase.database;

import android.support.annotation.NonNull;
import com.google.android.gms.internal.firebase_database.zze;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class DatabaseError {
    public static final int DATA_STALE = -1;
    public static final int DISCONNECTED = -4;
    public static final int EXPIRED_TOKEN = -6;
    public static final int INVALID_TOKEN = -7;
    public static final int MAX_RETRIES = -8;
    public static final int NETWORK_ERROR = -24;
    public static final int OPERATION_FAILED = -2;
    public static final int OVERRIDDEN_BY_SET = -9;
    public static final int PERMISSION_DENIED = -3;
    public static final int UNAVAILABLE = -10;
    public static final int UNKNOWN_ERROR = -999;
    public static final int USER_CODE_EXCEPTION = -11;
    public static final int WRITE_CANCELED = -25;
    private static final Map<Integer, String> zzq;
    private static final Map<String, Integer> zzr;
    private final int zzs;
    private final String zzt;
    private final String zzu;

    static {
        Map hashMap = new HashMap();
        zzq = hashMap;
        hashMap.put(Integer.valueOf(-1), "The transaction needs to be run again with current data");
        zzq.put(Integer.valueOf(-2), "The server indicated that this operation failed");
        zzq.put(Integer.valueOf(-3), "This client does not have permission to perform this operation");
        zzq.put(Integer.valueOf(-4), "The operation had to be aborted due to a network disconnect");
        zzq.put(Integer.valueOf(-6), "The supplied auth token has expired");
        zzq.put(Integer.valueOf(-7), "The supplied auth token was invalid");
        zzq.put(Integer.valueOf(-8), "The transaction had too many retries");
        zzq.put(Integer.valueOf(-9), "The transaction was overridden by a subsequent set");
        zzq.put(Integer.valueOf(-10), "The service is unavailable");
        zzq.put(Integer.valueOf(-11), "User code called from the Firebase Database runloop threw an exception:\n");
        zzq.put(Integer.valueOf(-24), "The operation could not be performed due to a network error");
        zzq.put(Integer.valueOf(-25), "The write was canceled by the user.");
        zzq.put(Integer.valueOf(UNKNOWN_ERROR), "An unknown error occurred");
        hashMap = new HashMap();
        zzr = hashMap;
        hashMap.put("datastale", Integer.valueOf(-1));
        zzr.put("failure", Integer.valueOf(-2));
        zzr.put("permission_denied", Integer.valueOf(-3));
        zzr.put("disconnected", Integer.valueOf(-4));
        zzr.put("expired_token", Integer.valueOf(-6));
        zzr.put("invalid_token", Integer.valueOf(-7));
        zzr.put("maxretries", Integer.valueOf(-8));
        zzr.put("overriddenbyset", Integer.valueOf(-9));
        zzr.put("unavailable", Integer.valueOf(-10));
        zzr.put("network_error", Integer.valueOf(-24));
        zzr.put("write_canceled", Integer.valueOf(-25));
    }

    private DatabaseError(int i, String str) {
        this(-11, str, null);
    }

    private DatabaseError(int i, String str, String str2) {
        this.zzs = i;
        this.zzt = str;
        this.zzu = "";
    }

    public static DatabaseError fromException(Throwable th) {
        Writer stringWriter = new StringWriter();
        zze.zza(th, new PrintWriter(stringWriter));
        String valueOf = String.valueOf((String) zzq.get(Integer.valueOf(-11)));
        String valueOf2 = String.valueOf(stringWriter.toString());
        return new DatabaseError(-11, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static DatabaseError zza(int i) {
        if (zzq.containsKey(Integer.valueOf(-25))) {
            return new DatabaseError(-25, (String) zzq.get(Integer.valueOf(-25)), null);
        }
        StringBuilder stringBuilder = new StringBuilder(49);
        stringBuilder.append("Invalid Firebase Database error code: -25");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static DatabaseError zza(String str) {
        Integer num = (Integer) zzr.get(str.toLowerCase());
        if (num == null) {
            num = Integer.valueOf(UNKNOWN_ERROR);
        }
        return new DatabaseError(num.intValue(), (String) zzq.get(num), null);
    }

    public static DatabaseError zza(String str, String str2) {
        Integer num = (Integer) zzr.get(str.toLowerCase());
        if (num == null) {
            num = Integer.valueOf(UNKNOWN_ERROR);
        }
        if (str2 == null) {
            str2 = (String) zzq.get(num);
        }
        return new DatabaseError(num.intValue(), str2, null);
    }

    public int getCode() {
        return this.zzs;
    }

    @NonNull
    public String getDetails() {
        return this.zzu;
    }

    @NonNull
    public String getMessage() {
        return this.zzt;
    }

    public DatabaseException toException() {
        String str = "Firebase Database error: ";
        String valueOf = String.valueOf(this.zzt);
        return new DatabaseException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    public String toString() {
        String str = "DatabaseError: ";
        String valueOf = String.valueOf(this.zzt);
        return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }
}
