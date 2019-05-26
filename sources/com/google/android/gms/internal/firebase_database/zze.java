package com.google.android.gms.internal.firebase_database;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class zze {
    private static final zzf zze;
    private static final int zzf;

    static final class zza extends zzf {
        zza() {
        }

        public final void zza(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }

    static {
        Integer zza;
        zzf zzj;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        int i = 1;
        try {
            zza = zza();
            if (zza != null) {
                try {
                    if (zza.intValue() >= 19) {
                        zzj = new zzj();
                        zze = zzj;
                        if (zza != null) {
                            i = zza.intValue();
                        }
                        zzf = i;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zza.class.getName();
                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
                    stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                    stringBuilder.append(name);
                    stringBuilder.append("will be used. The error is: ");
                    printStream.println(stringBuilder.toString());
                    th.printStackTrace(System.err);
                    zzj = new zza();
                    zze = zzj;
                    if (zza != null) {
                        i = zza.intValue();
                    }
                    zzf = i;
                }
            }
            zzj = (Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1) != 0 ? new zzi() : new zza();
        } catch (Throwable th3) {
            th = th3;
            zza = null;
            printStream = System.err;
            name = zza.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            zzj = new zza();
            zze = zzj;
            if (zza != null) {
                i = zza.intValue();
            }
            zzf = i;
        }
        zze = zzj;
        if (zza != null) {
            i = zza.intValue();
        }
        zzf = i;
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static void zza(Throwable th, PrintWriter printWriter) {
        zze.zza(th, printWriter);
    }
}
