package com.google.android.gms.internal.firebase_database;

import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.support.annotation.Nullable;
import java.io.File;
import java.lang.Thread.State;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;

public final class zzjr {
    private static final AtomicInteger zzsx = new AtomicInteger(0);
    private static final Charset zzsy = Charset.forName("UTF-8");
    private static ThreadFactory zzti = Executors.defaultThreadFactory();
    private static zzjq zztj = new zzjs();
    private final zzhz zzbs;
    @Nullable
    private final String zzcs;
    private volatile int zzsz = zzjv.zztm;
    private volatile Socket zzta = null;
    private zzjw zztb = null;
    private final URI zztc;
    private final zzka zztd;
    private final zzkb zzte;
    private final zzjy zztf;
    private final int zztg = zzsx.incrementAndGet();
    private final Thread zzth = zzti.newThread(new zzjt(this));

    public zzjr(zzaf zzaf, URI uri, String str, Map<String, String> map) {
        this.zztc = uri;
        this.zzcs = zzaf.zzw();
        int i = this.zztg;
        StringBuilder stringBuilder = new StringBuilder(14);
        stringBuilder.append("sk_");
        stringBuilder.append(i);
        this.zzbs = new zzhz(zzaf.zzq(), "WebSocket", stringBuilder.toString());
        this.zztf = new zzjy(uri, null, map);
        this.zztd = new zzka(this);
        this.zzte = new zzkb(this, "TubeSock", this.zztg);
    }

    private final Socket createSocket() {
        Socket socket;
        String scheme = this.zztc.getScheme();
        String host = this.zztc.getHost();
        int port = this.zztc.getPort();
        String valueOf;
        StringBuilder stringBuilder;
        if (scheme != null && scheme.equals("ws")) {
            if (port == -1) {
                port = 80;
            }
            try {
                socket = new Socket(host, port);
            } catch (Throwable e) {
                String str = "unknown host: ";
                host = String.valueOf(host);
                throw new zzjx(host.length() != 0 ? str.concat(host) : new String(str), e);
            } catch (Throwable e2) {
                valueOf = String.valueOf(this.zztc);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 31);
                stringBuilder.append("error while creating socket to ");
                stringBuilder.append(valueOf);
                throw new zzjx(stringBuilder.toString(), e2);
            }
        } else if (scheme == null || !scheme.equals("wss")) {
            valueOf = "unsupported protocol: ";
            scheme = String.valueOf(scheme);
            throw new zzjx(scheme.length() != 0 ? valueOf.concat(scheme) : new String(valueOf));
        } else {
            if (port == -1) {
                port = 443;
            }
            SSLSessionCache sSLSessionCache = null;
            try {
                if (this.zzcs != null) {
                    sSLSessionCache = new SSLSessionCache(new File(this.zzcs));
                }
            } catch (Throwable e3) {
                this.zzbs.zza("Failed to initialize SSL session cache", e3, new Object[0]);
            }
            try {
                socket = (SSLSocket) SSLCertificateSocketFactory.getDefault(60000, sSLSessionCache).createSocket(host, port);
                if (!HttpsURLConnection.getDefaultHostnameVerifier().verify(host, socket.getSession())) {
                    valueOf = String.valueOf(this.zztc);
                    stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 39);
                    stringBuilder.append("Error while verifying secure socket to ");
                    stringBuilder.append(valueOf);
                    throw new zzjx(stringBuilder.toString());
                }
            } catch (Throwable e22) {
                str = "unknown host: ";
                host = String.valueOf(host);
                throw new zzjx(host.length() != 0 ? str.concat(host) : new String(str), e22);
            } catch (Throwable e222) {
                valueOf = String.valueOf(this.zztc);
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 38);
                stringBuilder.append("error while creating secure socket to ");
                stringBuilder.append(valueOf);
                throw new zzjx(stringBuilder.toString(), e222);
            }
        }
        return socket;
    }

    static ThreadFactory getThreadFactory() {
        return zzti;
    }

    private final synchronized void zza(byte b, byte[] bArr) {
        if (this.zzsz != zzjv.zzto) {
            this.zztb.zza(new zzjx("error while sending data: not connected"));
            return;
        }
        try {
            this.zzte.zza(b, true, bArr);
        } catch (Throwable e) {
            this.zztb.zza(new zzjx("Failed to send frame", e));
            close();
        }
    }

    static zzjq zzgi() {
        return zztj;
    }

    private final synchronized void zzgl() {
        if (this.zzsz != zzjv.zztq) {
            this.zztd.zzgp();
            this.zzte.zzgr();
            if (this.zzta != null) {
                try {
                    this.zzta.close();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            this.zzsz = zzjv.zztq;
            this.zztb.onClose();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzgn() {
        /*
        r11 = this;
        r0 = r11.createSocket();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        monitor-enter(r11);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r11.zzta = r0;	 Catch:{ all -> 0x0181 }
        r1 = r11.zzsz;	 Catch:{ all -> 0x0181 }
        r2 = com.google.android.gms.internal.firebase_database.zzjv.zztq;	 Catch:{ all -> 0x0181 }
        if (r1 != r2) goto L_0x0021;
    L_0x000d:
        r0 = r11.zzta;	 Catch:{ IOException -> 0x001a }
        r0.close();	 Catch:{ IOException -> 0x001a }
        r0 = 0;
        r11.zzta = r0;	 Catch:{ all -> 0x0181 }
        monitor-exit(r11);	 Catch:{ all -> 0x0181 }
        r11.close();
        return;
    L_0x001a:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0181 }
        r1.<init>(r0);	 Catch:{ all -> 0x0181 }
        throw r1;	 Catch:{ all -> 0x0181 }
    L_0x0021:
        monitor-exit(r11);	 Catch:{ all -> 0x0181 }
        r1 = new java.io.DataInputStream;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = r0.getInputStream();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1.<init>(r2);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = r0.getOutputStream();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = r11.zztf;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = r2.zzgo();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.write(r2);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r3 = new byte[r2];	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r4 = new java.util.ArrayList;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r4.<init>();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r5 = 0;
        r6 = r3;
        r3 = r5;
        r7 = r3;
    L_0x0045:
        r8 = 1;
        if (r3 != 0) goto L_0x00b1;
    L_0x0048:
        r9 = r1.read();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r10 = -1;
        if (r9 == r10) goto L_0x00a9;
    L_0x004f:
        r9 = (byte) r9;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r6[r7] = r9;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r7 = r7 + 1;
        r9 = r7 + -1;
        r9 = r6[r9];	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r10 = 10;
        if (r9 != r10) goto L_0x0084;
    L_0x005c:
        r9 = r7 + -2;
        r9 = r6[r9];	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r10 = 13;
        if (r9 != r10) goto L_0x0084;
    L_0x0064:
        r7 = new java.lang.String;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r9 = zzsy;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r7.<init>(r6, r9);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r6 = r7.trim();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r9 = "";
        r6 = r6.equals(r9);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        if (r6 == 0) goto L_0x0079;
    L_0x0077:
        r3 = r8;
        goto L_0x0080;
    L_0x0079:
        r6 = r7.trim();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r4.add(r6);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0080:
        r6 = new byte[r2];	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r7 = r5;
        goto L_0x0045;
    L_0x0084:
        if (r7 != r2) goto L_0x0045;
    L_0x0086:
        r0 = new java.lang.String;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = zzsy;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.<init>(r6, r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = "Unexpected long line in handshake: ";
        r0 = java.lang.String.valueOf(r0);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = r0.length();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        if (r3 == 0) goto L_0x00a0;
    L_0x009b:
        r0 = r2.concat(r0);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        goto L_0x00a5;
    L_0x00a0:
        r0 = new java.lang.String;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.<init>(r2);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x00a5:
        r1.<init>(r0);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r1;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x00a9:
        r0 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = "Connection closed before handshake was complete";
        r0.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x00b1:
        r2 = r4.get(r5);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = (java.lang.String) r2;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = 9;
        r6 = 12;
        r2 = r2.substring(r3, r6);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = java.lang.Integer.parseInt(r2);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = 407; // 0x197 float:5.7E-43 double:2.01E-321;
        if (r2 == r3) goto L_0x0179;
    L_0x00c7:
        r3 = 404; // 0x194 float:5.66E-43 double:1.996E-321;
        if (r2 == r3) goto L_0x0171;
    L_0x00cb:
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r2 != r3) goto L_0x0158;
    L_0x00cf:
        r4.remove(r5);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = new java.util.HashMap;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2.<init>();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r4 = (java.util.ArrayList) r4;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = r4.size();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r6 = r5;
    L_0x00de:
        if (r6 >= r3) goto L_0x00f7;
    L_0x00e0:
        r7 = r4.get(r6);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r6 = r6 + 1;
        r7 = (java.lang.String) r7;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r9 = ": ";
        r10 = 2;
        r7 = r7.split(r9, r10);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r9 = r7[r5];	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r7 = r7[r8];	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2.put(r9, r7);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        goto L_0x00de;
    L_0x00f7:
        r3 = "Upgrade";
        r3 = r2.get(r3);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = (java.lang.String) r3;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r4 = java.util.Locale.US;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = r3.toLowerCase(r4);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r4 = "websocket";
        r3 = r3.equals(r4);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        if (r3 == 0) goto L_0x0150;
    L_0x010d:
        r3 = "Connection";
        r2 = r2.get(r3);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = (java.lang.String) r2;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = java.util.Locale.US;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2 = r2.toLowerCase(r3);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3 = "upgrade";
        r2 = r2.equals(r3);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        if (r2 == 0) goto L_0x0148;
    L_0x0123:
        r2 = r11.zzte;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r2.zza(r0);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = r11.zztd;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.zza(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = com.google.android.gms.internal.firebase_database.zzjv.zzto;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r11.zzsz = r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = r11.zzte;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = r0.zzgt();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.start();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = r11.zztb;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.zzav();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0 = r11.zztd;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.run();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r11.close();
        return;
    L_0x0148:
        r0 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = "connection failed: missing header field in server handshake: Connection";
        r0.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0150:
        r0 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = "connection failed: missing header field in server handshake: Upgrade";
        r0.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0158:
        r0 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = 50;
        r3 = new java.lang.StringBuilder;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = "connection failed: unknown status code ";
        r3.append(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r3.append(r2);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = r3.toString();	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r0.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0171:
        r0 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = "connection failed: 404 not found";
        r0.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0179:
        r0 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        r1 = "connection failed: proxy authentication not supported";
        r0.<init>(r1);	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0181:
        r0 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x0181 }
        throw r0;	 Catch:{ zzjx -> 0x01b0, IOException -> 0x0186 }
    L_0x0184:
        r0 = move-exception;
        goto L_0x01ba;
    L_0x0186:
        r0 = move-exception;
        r1 = r11.zztb;	 Catch:{ all -> 0x0184 }
        r2 = new com.google.android.gms.internal.firebase_database.zzjx;	 Catch:{ all -> 0x0184 }
        r3 = "error while connecting: ";
        r4 = r0.getMessage();	 Catch:{ all -> 0x0184 }
        r4 = java.lang.String.valueOf(r4);	 Catch:{ all -> 0x0184 }
        r5 = r4.length();	 Catch:{ all -> 0x0184 }
        if (r5 == 0) goto L_0x01a0;
    L_0x019b:
        r3 = r3.concat(r4);	 Catch:{ all -> 0x0184 }
        goto L_0x01a6;
    L_0x01a0:
        r4 = new java.lang.String;	 Catch:{ all -> 0x0184 }
        r4.<init>(r3);	 Catch:{ all -> 0x0184 }
        r3 = r4;
    L_0x01a6:
        r2.<init>(r3, r0);	 Catch:{ all -> 0x0184 }
        r1.zza(r2);	 Catch:{ all -> 0x0184 }
        r11.close();
        return;
    L_0x01b0:
        r0 = move-exception;
        r1 = r11.zztb;	 Catch:{ all -> 0x0184 }
        r1.zza(r0);	 Catch:{ all -> 0x0184 }
        r11.close();
        return;
    L_0x01ba:
        r11.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzjr.zzgn():void");
    }

    public final synchronized void close() {
        switch (zzju.zztl[this.zzsz - 1]) {
            case 1:
                this.zzsz = zzjv.zztq;
                return;
            case 2:
                zzgl();
                return;
            case 3:
                try {
                    this.zzsz = zzjv.zztp;
                    this.zzte.zzgr();
                    this.zzte.zza((byte) 8, true, new byte[0]);
                    return;
                } catch (Throwable e) {
                    this.zztb.zza(new zzjx("Failed to send close frame", e));
                    return;
                }
            case 4:
                return;
            case 5:
                return;
            default:
                return;
        }
    }

    public final synchronized void connect() {
        if (this.zzsz != zzjv.zztm) {
            this.zztb.zza(new zzjx("connect() already called"));
            close();
            return;
        }
        zzjq zzjq = zztj;
        Thread thread = this.zzth;
        int i = this.zztg;
        StringBuilder stringBuilder = new StringBuilder(26);
        stringBuilder.append("TubeSockReader-");
        stringBuilder.append(i);
        zzjq.zza(thread, stringBuilder.toString());
        this.zzsz = zzjv.zztn;
        this.zzth.start();
    }

    public final void zza(zzjw zzjw) {
        this.zztb = zzjw;
    }

    final void zzb(zzjx zzjx) {
        this.zztb.zza(zzjx);
        if (this.zzsz == zzjv.zzto) {
            close();
        }
        zzgl();
    }

    final synchronized void zzd(byte[] bArr) {
        zza((byte) 10, bArr);
    }

    final zzjw zzgj() {
        return this.zztb;
    }

    final void zzgk() {
        zzgl();
    }

    public final void zzgm() throws InterruptedException {
        if (this.zzte.zzgt().getState() != State.NEW) {
            this.zzte.zzgt().join();
        }
        this.zzth.join();
    }

    public final synchronized void zzm(String str) {
        zza((byte) 1, str.getBytes(zzsy));
    }
}
