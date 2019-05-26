package com.google.android.gms.common.util;

import android.os.Binder;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

public class ProcessUtils {
    private static String zzaai = null;
    private static int zzaaj = 0;

    public static class SystemGroupsNotAvailableException extends Exception {
        SystemGroupsNotAvailableException(String str) {
            super(str);
        }

        SystemGroupsNotAvailableException(String str, Throwable th) {
            super(str, th);
        }
    }

    private ProcessUtils() {
    }

    @Nullable
    public static String getCallingProcessName() {
        int callingPid = Binder.getCallingPid();
        return callingPid == zzde() ? getMyProcessName() : zzl(callingPid);
    }

    @Nullable
    public static String getMyProcessName() {
        if (zzaai == null) {
            zzaai = zzl(zzde());
        }
        return zzaai;
    }

    public static boolean hasSystemGroups() throws SystemGroupsNotAvailableException {
        Closeable closeable;
        Throwable th;
        Closeable closeable2 = null;
        try {
            int zzde = zzde();
            StringBuilder stringBuilder = new StringBuilder(24);
            stringBuilder.append("/proc/");
            stringBuilder.append(zzde);
            stringBuilder.append("/status");
            Closeable zzm = zzm(stringBuilder.toString());
            try {
                boolean zzk = zzk(zzm);
                IOUtils.closeQuietly(zzm);
                return zzk;
            } catch (Throwable e) {
                closeable = zzm;
                th = e;
                closeable2 = closeable;
                try {
                    throw new SystemGroupsNotAvailableException("Unable to access /proc/pid/status.", th);
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly(closeable2);
                    throw th;
                }
            } catch (Throwable e2) {
                closeable = zzm;
                th = e2;
                closeable2 = closeable;
                IOUtils.closeQuietly(closeable2);
                throw th;
            }
        } catch (IOException e3) {
            th = e3;
            throw new SystemGroupsNotAvailableException("Unable to access /proc/pid/status.", th);
        }
    }

    private static int zzde() {
        if (zzaaj == 0) {
            zzaaj = Process.myPid();
        }
        return zzaaj;
    }

    private static boolean zzk(BufferedReader bufferedReader) throws IOException, SystemGroupsNotAvailableException {
        String readLine;
        do {
            readLine = bufferedReader.readLine();
            if (readLine != null) {
                readLine = readLine.trim();
            } else {
                throw new SystemGroupsNotAvailableException("Missing Groups entry from proc/pid/status.");
            }
        } while (!readLine.startsWith("Groups:"));
        for (String parseLong : readLine.substring(7).trim().split("\\s", -1)) {
            try {
                long parseLong2 = Long.parseLong(parseLong);
                if (parseLong2 >= 1000 && parseLong2 < 2000) {
                    return true;
                }
            } catch (NumberFormatException e) {
            }
        }
        return false;
    }

    @Nullable
    private static String zzl(int i) {
        Closeable zzm;
        Closeable closeable;
        Throwable th;
        String str = null;
        if (i <= 0) {
            return null;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder(25);
            stringBuilder.append("/proc/");
            stringBuilder.append(i);
            stringBuilder.append("/cmdline");
            zzm = zzm(stringBuilder.toString());
            try {
                String trim = zzm.readLine().trim();
                IOUtils.closeQuietly(zzm);
                str = trim;
            } catch (IOException e) {
                IOUtils.closeQuietly(zzm);
                return str;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                closeable = zzm;
                th = th3;
                IOUtils.closeQuietly(closeable);
                throw th;
            }
        } catch (IOException e2) {
            zzm = null;
            IOUtils.closeQuietly(zzm);
            return str;
        } catch (Throwable th4) {
            th = th4;
            IOUtils.closeQuietly(closeable);
            throw th;
        }
        return str;
    }

    private static BufferedReader zzm(String str) throws IOException {
        ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            return bufferedReader;
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
