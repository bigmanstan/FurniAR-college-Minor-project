package com.google.android.gms.common.stats;

import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.common.stats.StatisticalEventTrackerProvider.StatisticalEventTracker;

public class StatsUtils {
    public static String getEventKey(AbstractThreadedSyncAdapter abstractThreadedSyncAdapter, String str) {
        Object obj;
        String valueOf = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(abstractThreadedSyncAdapter))));
        if (TextUtils.isEmpty(str)) {
            obj = "";
        }
        str = String.valueOf(obj);
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }

    public static String getEventKey(Context context, Intent intent) {
        return String.valueOf(((long) System.identityHashCode(intent)) | (((long) System.identityHashCode(context)) << 32));
    }

    public static String getEventKey(WakeLock wakeLock, String str) {
        Object obj;
        String valueOf = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(wakeLock))));
        if (TextUtils.isEmpty(str)) {
            obj = "";
        }
        str = String.valueOf(obj);
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }

    public static boolean isLoggingEnabled() {
        StatisticalEventTracker impl = StatisticalEventTrackerProvider.getImpl();
        return impl != null && impl.isEnabled() && (zza(Integer.valueOf(impl.getLogLevel(3))) || zza(Integer.valueOf(impl.getLogLevel(2))) || zza(Integer.valueOf(impl.getLogLevel(1))));
    }

    public static boolean isTimeOutEvent(StatsEvent statsEvent) {
        int eventType = statsEvent.getEventType();
        return eventType == 6 || eventType == 9 || eventType == 12;
    }

    private static boolean zza(Integer num) {
        return !num.equals(Integer.valueOf(LoggingConstants.LOG_LEVEL_OFF));
    }
}
