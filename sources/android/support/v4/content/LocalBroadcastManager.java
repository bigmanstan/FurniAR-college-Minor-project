package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
            this.filter = _filter;
            this.receiver = _receiver;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(128);
            builder.append("Receiver{");
            builder.append(this.receiver);
            builder.append(" filter=");
            builder.append(this.filter);
            if (this.dead) {
                builder.append(" DEAD");
            }
            builder.append("}");
            return builder.toString();
        }
    }

    @NonNull
    public static LocalBroadcastManager getInstance(@NonNull Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what != 1) {
                    super.handleMessage(msg);
                } else {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                }
            }
        };
    }

    public void registerReceiver(@NonNull BroadcastReceiver receiver, @NonNull IntentFilter filter) {
        synchronized (this.mReceivers) {
            ReceiverRecord entry = new ReceiverRecord(filter, receiver);
            ArrayList<ReceiverRecord> filters = (ArrayList) this.mReceivers.get(receiver);
            if (filters == null) {
                filters = new ArrayList(1);
                this.mReceivers.put(receiver, filters);
            }
            filters.add(entry);
            for (int i = 0; i < filter.countActions(); i++) {
                String action = filter.getAction(i);
                ArrayList<ReceiverRecord> entries = (ArrayList) this.mActions.get(action);
                if (entries == null) {
                    entries = new ArrayList(1);
                    this.mActions.put(action, entries);
                }
                entries.add(entry);
            }
        }
    }

    public void unregisterReceiver(@NonNull BroadcastReceiver receiver) {
        synchronized (this.mReceivers) {
            ArrayList<ReceiverRecord> filters = (ArrayList) this.mReceivers.remove(receiver);
            if (filters == null) {
                return;
            }
            for (int i = filters.size() - 1; i >= 0; i--) {
                ReceiverRecord filter = (ReceiverRecord) filters.get(i);
                filter.dead = true;
                for (int j = 0; j < filter.filter.countActions(); j++) {
                    String action = filter.filter.getAction(j);
                    ArrayList<ReceiverRecord> receivers = (ArrayList) this.mActions.get(action);
                    if (receivers != null) {
                        for (int k = receivers.size() - 1; k >= 0; k--) {
                            ReceiverRecord rec = (ReceiverRecord) receivers.get(k);
                            if (rec.receiver == receiver) {
                                rec.dead = true;
                                receivers.remove(k);
                            }
                        }
                        if (receivers.size() <= 0) {
                            this.mActions.remove(action);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(@android.support.annotation.NonNull android.content.Intent r19) {
        /*
        r18 = this;
        r1 = r18;
        r2 = r19;
        r3 = r1.mReceivers;
        monitor-enter(r3);
        r5 = r19.getAction();	 Catch:{ all -> 0x0164 }
        r0 = r1.mAppContext;	 Catch:{ all -> 0x0164 }
        r0 = r0.getContentResolver();	 Catch:{ all -> 0x0164 }
        r0 = r2.resolveTypeIfNeeded(r0);	 Catch:{ all -> 0x0164 }
        r8 = r19.getData();	 Catch:{ all -> 0x0164 }
        r4 = r19.getScheme();	 Catch:{ all -> 0x0164 }
        r11 = r4;
        r9 = r19.getCategories();	 Catch:{ all -> 0x0164 }
        r4 = r19.getFlags();	 Catch:{ all -> 0x0164 }
        r4 = r4 & 8;
        if (r4 == 0) goto L_0x002d;
    L_0x002b:
        r4 = 1;
        goto L_0x002e;
    L_0x002d:
        r4 = 0;
    L_0x002e:
        r14 = r4;
        if (r14 == 0) goto L_0x0057;
    L_0x0031:
        r4 = "LocalBroadcastManager";
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0164 }
        r6.<init>();	 Catch:{ all -> 0x0164 }
        r7 = "Resolving type ";
        r6.append(r7);	 Catch:{ all -> 0x0164 }
        r6.append(r0);	 Catch:{ all -> 0x0164 }
        r7 = " scheme ";
        r6.append(r7);	 Catch:{ all -> 0x0164 }
        r6.append(r11);	 Catch:{ all -> 0x0164 }
        r7 = " of intent ";
        r6.append(r7);	 Catch:{ all -> 0x0164 }
        r6.append(r2);	 Catch:{ all -> 0x0164 }
        r6 = r6.toString();	 Catch:{ all -> 0x0164 }
        android.util.Log.v(r4, r6);	 Catch:{ all -> 0x0164 }
    L_0x0057:
        r4 = r1.mActions;	 Catch:{ all -> 0x0164 }
        r6 = r19.getAction();	 Catch:{ all -> 0x0164 }
        r4 = r4.get(r6);	 Catch:{ all -> 0x0164 }
        r4 = (java.util.ArrayList) r4;	 Catch:{ all -> 0x0164 }
        r15 = r4;
        if (r15 == 0) goto L_0x0161;
    L_0x0066:
        if (r14 == 0) goto L_0x007e;
    L_0x0068:
        r4 = "LocalBroadcastManager";
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0164 }
        r6.<init>();	 Catch:{ all -> 0x0164 }
        r7 = "Action list: ";
        r6.append(r7);	 Catch:{ all -> 0x0164 }
        r6.append(r15);	 Catch:{ all -> 0x0164 }
        r6 = r6.toString();	 Catch:{ all -> 0x0164 }
        android.util.Log.v(r4, r6);	 Catch:{ all -> 0x0164 }
    L_0x007e:
        r4 = 0;
        r10 = r4;
        r4 = 0;
    L_0x0081:
        r7 = r4;
        r4 = r15.size();	 Catch:{ all -> 0x0164 }
        if (r7 >= r4) goto L_0x012e;
    L_0x0088:
        r4 = r15.get(r7);	 Catch:{ all -> 0x0164 }
        r4 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r4;	 Catch:{ all -> 0x0164 }
        r6 = r4;
        if (r14 == 0) goto L_0x00a9;
    L_0x0091:
        r4 = "LocalBroadcastManager";
        r12 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0164 }
        r12.<init>();	 Catch:{ all -> 0x0164 }
        r13 = "Matching against filter ";
        r12.append(r13);	 Catch:{ all -> 0x0164 }
        r13 = r6.filter;	 Catch:{ all -> 0x0164 }
        r12.append(r13);	 Catch:{ all -> 0x0164 }
        r12 = r12.toString();	 Catch:{ all -> 0x0164 }
        android.util.Log.v(r4, r12);	 Catch:{ all -> 0x0164 }
    L_0x00a9:
        r4 = r6.broadcasting;	 Catch:{ all -> 0x0164 }
        if (r4 == 0) goto L_0x00bd;
    L_0x00ad:
        if (r14 == 0) goto L_0x00b6;
    L_0x00af:
        r4 = "LocalBroadcastManager";
        r12 = "  Filter's target already added";
        android.util.Log.v(r4, r12);	 Catch:{ all -> 0x0164 }
    L_0x00b6:
        r17 = r0;
        r16 = r7;
        r0 = r10;
        goto L_0x0127;
    L_0x00bd:
        r4 = r6.filter;	 Catch:{ all -> 0x0164 }
        r12 = "LocalBroadcastManager";
        r13 = r6;
        r6 = r0;
        r16 = r7;
        r7 = r11;
        r17 = r0;
        r0 = r10;
        r10 = r12;
        r4 = r4.match(r5, r6, r7, r8, r9, r10);	 Catch:{ all -> 0x0164 }
        if (r4 < 0) goto L_0x00fd;
    L_0x00d0:
        if (r14 == 0) goto L_0x00ec;
    L_0x00d2:
        r6 = "LocalBroadcastManager";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0164 }
        r7.<init>();	 Catch:{ all -> 0x0164 }
        r10 = "  Filter matched!  match=0x";
        r7.append(r10);	 Catch:{ all -> 0x0164 }
        r10 = java.lang.Integer.toHexString(r4);	 Catch:{ all -> 0x0164 }
        r7.append(r10);	 Catch:{ all -> 0x0164 }
        r7 = r7.toString();	 Catch:{ all -> 0x0164 }
        android.util.Log.v(r6, r7);	 Catch:{ all -> 0x0164 }
    L_0x00ec:
        if (r0 != 0) goto L_0x00f5;
    L_0x00ee:
        r6 = new java.util.ArrayList;	 Catch:{ all -> 0x0164 }
        r6.<init>();	 Catch:{ all -> 0x0164 }
        r10 = r6;
        r0 = r10;
    L_0x00f5:
        r0.add(r13);	 Catch:{ all -> 0x0164 }
        r6 = 1;
        r13.broadcasting = r6;	 Catch:{ all -> 0x0164 }
        r10 = r0;
        goto L_0x0128;
    L_0x00fd:
        if (r14 == 0) goto L_0x0127;
    L_0x00ff:
        switch(r4) {
            case -4: goto L_0x010e;
            case -3: goto L_0x010b;
            case -2: goto L_0x0108;
            case -1: goto L_0x0105;
            default: goto L_0x0102;
        };	 Catch:{ all -> 0x0164 }
    L_0x0102:
        r6 = "unknown reason";
        goto L_0x0110;
    L_0x0105:
        r6 = "type";
        goto L_0x0110;
    L_0x0108:
        r6 = "data";
        goto L_0x0110;
    L_0x010b:
        r6 = "action";
        goto L_0x0110;
    L_0x010e:
        r6 = "category";
        r7 = "LocalBroadcastManager";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0164 }
        r10.<init>();	 Catch:{ all -> 0x0164 }
        r12 = "  Filter did not match: ";
        r10.append(r12);	 Catch:{ all -> 0x0164 }
        r10.append(r6);	 Catch:{ all -> 0x0164 }
        r10 = r10.toString();	 Catch:{ all -> 0x0164 }
        android.util.Log.v(r7, r10);	 Catch:{ all -> 0x0164 }
    L_0x0127:
        r10 = r0;
    L_0x0128:
        r4 = r16 + 1;
        r0 = r17;
        goto L_0x0081;
    L_0x012e:
        r17 = r0;
        r0 = r10;
        if (r0 == 0) goto L_0x0161;
    L_0x0133:
        r4 = 0;
    L_0x0134:
        r6 = r0.size();	 Catch:{ all -> 0x0164 }
        if (r4 >= r6) goto L_0x0146;
    L_0x013a:
        r6 = r0.get(r4);	 Catch:{ all -> 0x0164 }
        r6 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r6;	 Catch:{ all -> 0x0164 }
        r7 = 0;
        r6.broadcasting = r7;	 Catch:{ all -> 0x0164 }
        r4 = r4 + 1;
        goto L_0x0134;
    L_0x0146:
        r4 = r1.mPendingBroadcasts;	 Catch:{ all -> 0x0164 }
        r6 = new android.support.v4.content.LocalBroadcastManager$BroadcastRecord;	 Catch:{ all -> 0x0164 }
        r6.<init>(r2, r0);	 Catch:{ all -> 0x0164 }
        r4.add(r6);	 Catch:{ all -> 0x0164 }
        r4 = r1.mHandler;	 Catch:{ all -> 0x0164 }
        r6 = 1;
        r4 = r4.hasMessages(r6);	 Catch:{ all -> 0x0164 }
        if (r4 != 0) goto L_0x015e;
    L_0x0159:
        r4 = r1.mHandler;	 Catch:{ all -> 0x0164 }
        r4.sendEmptyMessage(r6);	 Catch:{ all -> 0x0164 }
    L_0x015e:
        monitor-exit(r3);	 Catch:{ all -> 0x0164 }
        r3 = 1;
        return r3;
    L_0x0161:
        monitor-exit(r3);	 Catch:{ all -> 0x0164 }
        r0 = 0;
        return r0;
    L_0x0164:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0164 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(@NonNull Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void executePendingBroadcasts() {
        /*
        r10 = this;
        r0 = 0;
    L_0x0001:
        r1 = r10.mReceivers;
        monitor-enter(r1);
        r2 = r10.mPendingBroadcasts;	 Catch:{ all -> 0x0048 }
        r2 = r2.size();	 Catch:{ all -> 0x0048 }
        if (r2 > 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        return;
    L_0x000e:
        r3 = new android.support.v4.content.LocalBroadcastManager.BroadcastRecord[r2];	 Catch:{ all -> 0x0048 }
        r0 = r3;
        r3 = r10.mPendingBroadcasts;	 Catch:{ all -> 0x0048 }
        r3.toArray(r0);	 Catch:{ all -> 0x0048 }
        r3 = r10.mPendingBroadcasts;	 Catch:{ all -> 0x0048 }
        r3.clear();	 Catch:{ all -> 0x0048 }
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        r1 = 0;
        r2 = r1;
    L_0x001e:
        r3 = r0.length;
        if (r2 >= r3) goto L_0x0047;
    L_0x0021:
        r3 = r0[r2];
        r4 = r3.receivers;
        r4 = r4.size();
        r5 = r1;
    L_0x002a:
        if (r5 >= r4) goto L_0x0044;
    L_0x002c:
        r6 = r3.receivers;
        r6 = r6.get(r5);
        r6 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r6;
        r7 = r6.dead;
        if (r7 != 0) goto L_0x0041;
    L_0x0038:
        r7 = r6.receiver;
        r8 = r10.mAppContext;
        r9 = r3.intent;
        r7.onReceive(r8, r9);
    L_0x0041:
        r5 = r5 + 1;
        goto L_0x002a;
    L_0x0044:
        r2 = r2 + 1;
        goto L_0x001e;
    L_0x0047:
        goto L_0x0001;
    L_0x0048:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}
