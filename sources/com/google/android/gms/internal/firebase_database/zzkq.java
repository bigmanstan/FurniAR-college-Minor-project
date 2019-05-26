package com.google.android.gms.internal.firebase_database;

import android.util.Base64;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;

public final class zzkq {
    private static final char[] zzuq = "0123456789abcdef".toCharArray();

    public static int zza(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    public static void zza(boolean z, String str) {
        if (!z) {
            String str2 = "hardAssert failed: ";
            str = String.valueOf(str);
            throw new AssertionError(str.length() != 0 ? str2.concat(str) : new String(str2));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Integer zzaa(java.lang.String r8) {
        /*
        r0 = r8.length();
        r1 = 0;
        r2 = 11;
        if (r0 > r2) goto L_0x005c;
    L_0x0009:
        r0 = r8.length();
        if (r0 != 0) goto L_0x0010;
    L_0x000f:
        goto L_0x005c;
    L_0x0010:
        r0 = 0;
        r2 = r8.charAt(r0);
        r3 = 45;
        r4 = 1;
        if (r2 != r3) goto L_0x0023;
    L_0x001a:
        r0 = r8.length();
        if (r0 != r4) goto L_0x0021;
    L_0x0020:
        return r1;
    L_0x0021:
        r0 = r4;
        goto L_0x0024;
    L_0x0023:
        r4 = r0;
    L_0x0024:
        r2 = 0;
    L_0x0026:
        r5 = r8.length();
        if (r0 >= r5) goto L_0x0044;
    L_0x002c:
        r5 = r8.charAt(r0);
        r6 = 48;
        if (r5 < r6) goto L_0x0043;
    L_0x0034:
        r6 = 57;
        if (r5 <= r6) goto L_0x0039;
    L_0x0038:
        goto L_0x0043;
    L_0x0039:
        r6 = 10;
        r2 = r2 * r6;
        r5 = r5 + -48;
        r5 = (long) r5;
        r2 = r2 + r5;
        r0 = r0 + 1;
        goto L_0x0026;
    L_0x0043:
        return r1;
    L_0x0044:
        if (r4 == 0) goto L_0x0055;
    L_0x0046:
        r2 = -r2;
        r4 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r8 >= 0) goto L_0x004f;
    L_0x004e:
        return r1;
    L_0x004f:
        r8 = (int) r2;
        r8 = java.lang.Integer.valueOf(r8);
        return r8;
    L_0x0055:
        r4 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r8 <= 0) goto L_0x004f;
    L_0x005c:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzkq.zzaa(java.lang.String):java.lang.Integer");
    }

    public static int zzb(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        return i < 0 ? -1 : i == 0 ? 0 : 1;
    }

    public static zzkn<Task<Void>, CompletionListener> zzb(CompletionListener completionListener) {
        if (completionListener != null) {
            return new zzkn(null, completionListener);
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        return new zzkn(taskCompletionSource.getTask(), new zzkr(taskCompletionSource));
    }

    public static String zzc(double d) {
        StringBuilder stringBuilder = new StringBuilder(16);
        long doubleToLongBits = Double.doubleToLongBits(d);
        for (int i = 7; i >= 0; i--) {
            int i2 = (int) ((doubleToLongBits >>> (i << 3)) & 255);
            int i3 = (i2 >> 4) & 15;
            i2 &= 15;
            stringBuilder.append(zzuq[i3]);
            stringBuilder.append(zzuq[i2]);
        }
        return stringBuilder.toString();
    }

    public static void zzf(boolean z) {
        zza(z, "");
    }

    public static zzko zzx(String str) throws DatabaseException {
        try {
            int indexOf = str.indexOf("//");
            if (indexOf != -1) {
                indexOf += 2;
                int indexOf2 = str.substring(indexOf).indexOf("/");
                if (indexOf2 != -1) {
                    indexOf2 += indexOf;
                    String[] split = str.substring(indexOf2).split("/");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < split.length; i++) {
                        if (!split[i].equals("")) {
                            stringBuilder.append("/");
                            stringBuilder.append(URLEncoder.encode(split[i], "UTF-8"));
                        }
                    }
                    str = String.valueOf(str.substring(0, indexOf2));
                    String valueOf = String.valueOf(stringBuilder.toString());
                    str = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
                }
                URI uri = new URI(str);
                str = uri.getPath().replace("+", " ");
                zzks.zzac(str);
                zzch zzch = new zzch(str);
                str = uri.getScheme();
                zzdn zzdn = new zzdn();
                zzdn.zzct = uri.getHost().toLowerCase();
                indexOf = uri.getPort();
                if (indexOf != -1) {
                    zzdn.zzcv = str.equals("https");
                    str = String.valueOf(zzdn.zzct);
                    StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(str).length() + 12);
                    stringBuilder2.append(str);
                    stringBuilder2.append(":");
                    stringBuilder2.append(indexOf);
                    zzdn.zzct = stringBuilder2.toString();
                } else {
                    zzdn.zzcv = true;
                }
                zzdn.zzcu = zzdn.zzct.split("\\.")[0].toLowerCase();
                zzdn.zzka = zzdn.zzct;
                zzko zzko = new zzko();
                zzko.zzap = zzch;
                zzko.zzag = zzdn;
                return zzko;
            }
            throw new URISyntaxException(str, "Invalid scheme specified");
        } catch (Throwable e) {
            throw new DatabaseException("Invalid Firebase Database url specified", e);
        } catch (Throwable e2) {
            throw new DatabaseException("Failed to URLEncode the path", e2);
        }
    }

    public static String zzy(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            return Base64.encodeToString(instance.digest(), 2);
        } catch (Throwable e) {
            throw new RuntimeException("Missing SHA-1 MessageDigest provider.", e);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UTF-8 encoding is required for Firebase Database to run!");
        }
    }

    public static String zzz(String str) {
        String replace = str.indexOf(92) != -1 ? str.replace("\\", "\\\\") : str;
        if (str.indexOf(34) != -1) {
            replace = replace.replace("\"", "\\\"");
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(replace).length() + 2);
        stringBuilder.append('\"');
        stringBuilder.append(replace);
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }
}
