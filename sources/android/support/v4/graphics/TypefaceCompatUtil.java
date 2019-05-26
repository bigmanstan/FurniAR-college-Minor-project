package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

@RestrictTo({Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    @Nullable
    public static File getTempFile(Context context) {
        String prefix = new StringBuilder();
        prefix.append(CACHE_FILE_PREFIX);
        prefix.append(Process.myPid());
        prefix.append("-");
        prefix.append(Process.myTid());
        prefix.append("-");
        prefix = prefix.toString();
        int i = 0;
        while (i < 100) {
            File cacheDir = context.getCacheDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append(i);
            File file = new File(cacheDir, stringBuilder.toString());
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Nullable
    @RequiresApi(19)
    private static ByteBuffer mmap(File file) {
        Throwable th;
        Throwable th2;
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                FileChannel channel = fis.getChannel();
                ByteBuffer map = channel.map(MapMode.READ_ONLY, 0, channel.size());
                fis.close();
                return map;
            } catch (Throwable th22) {
                Throwable th3 = th22;
                th22 = th;
                th = th3;
            }
            throw th;
            if (th22 != null) {
                try {
                    fis.close();
                } catch (Throwable th4) {
                    th22.addSuppressed(th4);
                }
            } else {
                fis.close();
            }
            throw th;
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.Nullable
    @android.support.annotation.RequiresApi(19)
    public static java.nio.ByteBuffer mmap(android.content.Context r11, android.os.CancellationSignal r12, android.net.Uri r13) {
        /*
        r0 = r11.getContentResolver();
        r1 = 0;
        r2 = "r";
        r2 = r0.openFileDescriptor(r13, r2, r12);	 Catch:{ IOException -> 0x0068 }
        if (r2 != 0) goto L_0x0014;
        if (r2 == 0) goto L_0x0013;
    L_0x0010:
        r2.close();	 Catch:{ IOException -> 0x0068 }
    L_0x0013:
        return r1;
    L_0x0014:
        r3 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
        r4 = r2.getFileDescriptor();	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
        r3.<init>(r4);	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
        r4 = r3.getChannel();	 Catch:{ Throwable -> 0x0039, all -> 0x0036 }
        r8 = r4.size();	 Catch:{ Throwable -> 0x0039, all -> 0x0036 }
        r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ Throwable -> 0x0039, all -> 0x0036 }
        r6 = 0;
        r5 = r4.map(r5, r6, r8);	 Catch:{ Throwable -> 0x0039, all -> 0x0036 }
        r3.close();	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r2.close();	 Catch:{ IOException -> 0x0068 }
    L_0x0035:
        return r5;
    L_0x0036:
        r4 = move-exception;
        r5 = r1;
        goto L_0x003f;
    L_0x0039:
        r4 = move-exception;
        throw r4;	 Catch:{ all -> 0x003b }
    L_0x003b:
        r5 = move-exception;
        r10 = r5;
        r5 = r4;
        r4 = r10;
    L_0x003f:
        if (r5 == 0) goto L_0x004a;
    L_0x0041:
        r3.close();	 Catch:{ Throwable -> 0x0045, all -> 0x004e }
        goto L_0x004d;
    L_0x0045:
        r6 = move-exception;
        r5.addSuppressed(r6);	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
        goto L_0x004d;
    L_0x004a:
        r3.close();	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
    L_0x004d:
        throw r4;	 Catch:{ Throwable -> 0x0051, all -> 0x004e }
    L_0x004e:
        r3 = move-exception;
        r4 = r1;
        goto L_0x0057;
    L_0x0051:
        r3 = move-exception;
        throw r3;	 Catch:{ all -> 0x0053 }
    L_0x0053:
        r4 = move-exception;
        r10 = r4;
        r4 = r3;
        r3 = r10;
    L_0x0057:
        if (r2 == 0) goto L_0x0067;
    L_0x0059:
        if (r4 == 0) goto L_0x0064;
    L_0x005b:
        r2.close();	 Catch:{ Throwable -> 0x005f }
        goto L_0x0067;
    L_0x005f:
        r5 = move-exception;
        r4.addSuppressed(r5);	 Catch:{ IOException -> 0x0068 }
        goto L_0x0067;
    L_0x0064:
        r2.close();	 Catch:{ IOException -> 0x0068 }
    L_0x0067:
        throw r3;	 Catch:{ IOException -> 0x0068 }
    L_0x0068:
        r2 = move-exception;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatUtil.mmap(android.content.Context, android.os.CancellationSignal, android.net.Uri):java.nio.ByteBuffer");
    }

    @Nullable
    @RequiresApi(19)
    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        ByteBuffer byteBuffer = null;
        if (tmpFile == null) {
            return null;
        }
        try {
            if (copyToFile(tmpFile, res, id)) {
                byteBuffer = mmap(tmpFile);
            }
            tmpFile.delete();
            return byteBuffer;
        } catch (Throwable th) {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        FileOutputStream os = null;
        boolean z = false;
        try {
            os = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                int readLen = read;
                if (read == -1) {
                    break;
                }
                os.write(buffer, 0, readLen);
            }
            z = true;
        } catch (IOException e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error copying resource contents to temp file: ");
            stringBuilder.append(e.getMessage());
            Log.e(str, stringBuilder.toString());
        } catch (Throwable th) {
            closeQuietly(null);
        }
        closeQuietly(os);
        return z;
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            boolean copyToFile = copyToFile(file, is);
            return copyToFile;
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
}
