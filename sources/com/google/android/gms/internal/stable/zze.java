package com.google.android.gms.internal.stable;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import java.util.HashMap;

public final class zze {

    public static class zza implements BaseColumns {
        private static HashMap<Uri, zzh> zzagq = new HashMap();

        private static zzh zza(ContentResolver contentResolver, Uri uri) {
            zzh zzh = (zzh) zzagq.get(uri);
            if (zzh == null) {
                zzh = new zzh();
                zzagq.put(uri, zzh);
                contentResolver.registerContentObserver(uri, true, new zzf(null, zzh));
                return zzh;
            } else if (!zzh.zzagu.getAndSet(false)) {
                return zzh;
            } else {
                synchronized (zzh) {
                    zzh.zzags.clear();
                    zzh.zzagt = new Object();
                }
                return zzh;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected static java.lang.String zza(android.content.ContentResolver r10, android.net.Uri r11, java.lang.String r12) {
            /*
            r0 = com.google.android.gms.internal.stable.zze.zza.class;
            monitor-enter(r0);
            r1 = zza(r10, r11);	 Catch:{ all -> 0x008a }
            monitor-exit(r0);	 Catch:{ all -> 0x008a }
            monitor-enter(r1);
            r0 = r1.zzagt;	 Catch:{ all -> 0x0087 }
            r2 = r1.zzags;	 Catch:{ all -> 0x0087 }
            r2 = r2.containsKey(r12);	 Catch:{ all -> 0x0087 }
            if (r2 == 0) goto L_0x001d;
        L_0x0013:
            r10 = r1.zzags;	 Catch:{ all -> 0x0087 }
            r10 = r10.get(r12);	 Catch:{ all -> 0x0087 }
            r10 = (java.lang.String) r10;	 Catch:{ all -> 0x0087 }
            monitor-exit(r1);	 Catch:{ all -> 0x0087 }
            return r10;
        L_0x001d:
            monitor-exit(r1);	 Catch:{ all -> 0x0087 }
            r2 = 0;
            r3 = "value";
            r6 = new java.lang.String[]{r3};	 Catch:{ SQLException -> 0x005e }
            r7 = "name=?";
            r3 = 1;
            r8 = new java.lang.String[r3];	 Catch:{ SQLException -> 0x005e }
            r3 = 0;
            r8[r3] = r12;	 Catch:{ SQLException -> 0x005e }
            r9 = 0;
            r4 = r10;
            r5 = r11;
            r10 = r4.query(r5, r6, r7, r8, r9);	 Catch:{ SQLException -> 0x005e }
            if (r10 == 0) goto L_0x0053;
        L_0x0036:
            r4 = r10.moveToFirst();	 Catch:{ SQLException -> 0x004f, all -> 0x004c }
            if (r4 != 0) goto L_0x003d;
        L_0x003c:
            goto L_0x0053;
        L_0x003d:
            r3 = r10.getString(r3);	 Catch:{ SQLException -> 0x004f, all -> 0x004c }
            zza(r1, r0, r12, r3);	 Catch:{ SQLException -> 0x004a, all -> 0x004c }
            if (r10 == 0) goto L_0x0080;
        L_0x0046:
            r10.close();
            goto L_0x0080;
        L_0x004a:
            r0 = move-exception;
            goto L_0x0051;
        L_0x004c:
            r11 = move-exception;
            r2 = r10;
            goto L_0x0081;
        L_0x004f:
            r0 = move-exception;
            r3 = r2;
        L_0x0051:
            r2 = r10;
            goto L_0x0060;
        L_0x0053:
            zza(r1, r0, r12, r2);	 Catch:{ SQLException -> 0x004f, all -> 0x004c }
            if (r10 == 0) goto L_0x005b;
        L_0x0058:
            r10.close();
        L_0x005b:
            return r2;
        L_0x005c:
            r11 = move-exception;
            goto L_0x0081;
        L_0x005e:
            r0 = move-exception;
            r3 = r2;
        L_0x0060:
            r10 = "GoogleSettings";
            r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005c }
            r4 = "Can't get key ";
            r1.<init>(r4);	 Catch:{ all -> 0x005c }
            r1.append(r12);	 Catch:{ all -> 0x005c }
            r12 = " from ";
            r1.append(r12);	 Catch:{ all -> 0x005c }
            r1.append(r11);	 Catch:{ all -> 0x005c }
            r11 = r1.toString();	 Catch:{ all -> 0x005c }
            android.util.Log.e(r10, r11, r0);	 Catch:{ all -> 0x005c }
            if (r2 == 0) goto L_0x0080;
        L_0x007d:
            r2.close();
        L_0x0080:
            return r3;
        L_0x0081:
            if (r2 == 0) goto L_0x0086;
        L_0x0083:
            r2.close();
        L_0x0086:
            throw r11;
        L_0x0087:
            r10 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0087 }
            throw r10;
        L_0x008a:
            r10 = move-exception;
            monitor-exit(r0);	 Catch:{ all -> 0x008a }
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.stable.zze.zza.zza(android.content.ContentResolver, android.net.Uri, java.lang.String):java.lang.String");
        }

        private static void zza(zzh zzh, Object obj, String str, String str2) {
            synchronized (zzh) {
                if (obj == zzh.zzagt) {
                    zzh.zzags.put(str, str2);
                }
            }
        }
    }
}
