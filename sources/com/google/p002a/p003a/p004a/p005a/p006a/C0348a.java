package com.google.p002a.p003a.p004a.p005a.p006a;

import java.io.PrintStream;

/* renamed from: com.google.a.a.a.a.a.a */
public final class C0348a {
    /* renamed from: a */
    private static final C0349b f11a;

    /* renamed from: com.google.a.a.a.a.a.a$a */
    static final class C0516a extends C0349b {
        C0516a() {
        }

        /* renamed from: a */
        public final void mo1248a(Throwable th, Throwable th2) {
        }
    }

    static {
        Integer a;
        C0349b c0518f;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        try {
            a = C0348a.m3a();
            if (a != null) {
                try {
                    if (a.intValue() >= 19) {
                        c0518f = new C0518f();
                        f11a = c0518f;
                        if (a == null) {
                            a.intValue();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = C0516a.class.getName();
                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
                    stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                    stringBuilder.append(name);
                    stringBuilder.append("will be used. The error is: ");
                    printStream.println(stringBuilder.toString());
                    th.printStackTrace(System.err);
                    c0518f = new C0516a();
                    f11a = c0518f;
                    if (a == null) {
                        a.intValue();
                    }
                }
            }
            c0518f = (Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1) != 0 ? new C0517e() : new C0516a();
        } catch (Throwable th3) {
            th = th3;
            a = null;
            printStream = System.err;
            name = C0516a.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            c0518f = new C0516a();
            f11a = c0518f;
            if (a == null) {
                a.intValue();
            }
        }
        f11a = c0518f;
        if (a == null) {
            a.intValue();
        }
    }

    /* renamed from: a */
    private static Integer m3a() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    /* renamed from: a */
    public static void m4a(Throwable th, Throwable th2) {
        f11a.mo1248a(th, th2);
    }
}
