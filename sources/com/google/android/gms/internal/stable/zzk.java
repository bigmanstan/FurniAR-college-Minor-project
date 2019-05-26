package com.google.android.gms.internal.stable;

import java.io.PrintStream;

public final class zzk {
    private static final zzl zzahg;
    private static final int zzahh;

    static final class zza extends zzl {
        zza() {
        }

        public final void zza(Throwable th, Throwable th2) {
        }
    }

    static {
        Integer zzdw;
        zzl zzp;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        int i = 1;
        try {
            zzdw = zzdw();
            if (zzdw != null) {
                try {
                    if (zzdw.intValue() >= 19) {
                        zzp = new zzp();
                        zzahg = zzp;
                        if (zzdw != null) {
                            i = zzdw.intValue();
                        }
                        zzahh = i;
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
                    zzp = new zza();
                    zzahg = zzp;
                    if (zzdw != null) {
                        i = zzdw.intValue();
                    }
                    zzahh = i;
                }
            }
            zzp = (Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1) != 0 ? new zzo() : new zza();
        } catch (Throwable th3) {
            th = th3;
            zzdw = null;
            printStream = System.err;
            name = zza.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            zzp = new zza();
            zzahg = zzp;
            if (zzdw != null) {
                i = zzdw.intValue();
            }
            zzahh = i;
        }
        zzahg = zzp;
        if (zzdw != null) {
            i = zzdw.intValue();
        }
        zzahh = i;
    }

    public static void zza(Throwable th, Throwable th2) {
        zzahg.zza(th, th2);
    }

    private static Integer zzdw() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
