package com.google.android.gms.internal.firebase_database;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzbc {
    private static long zzev = 0;
    private final ScheduledExecutorService zzbc;
    private final zzhz zzbs;
    private zzbg zzew;
    private boolean zzex = false;
    private boolean zzey = false;
    private long zzez = 0;
    private zzbp zzfa;
    private zzbf zzfb;
    private ScheduledFuture<?> zzfc;
    private ScheduledFuture<?> zzfd;
    private final zzaf zzfe;

    public zzbc(zzaf zzaf, zzah zzah, String str, zzbf zzbf, String str2) {
        this.zzfe = zzaf;
        this.zzbc = zzaf.zzs();
        this.zzfb = zzbf;
        long j = zzev;
        zzev = 1 + j;
        StringBuilder stringBuilder = new StringBuilder(23);
        stringBuilder.append("ws_");
        stringBuilder.append(j);
        this.zzbs = new zzhz(zzaf.zzq(), "WebSocket", stringBuilder.toString());
        if (str == null) {
            str = zzah.getHost();
        }
        boolean isSecure = zzah.isSecure();
        String namespace = zzah.getNamespace();
        String str3 = isSecure ? "wss" : "ws";
        StringBuilder stringBuilder2 = new StringBuilder(((String.valueOf(str3).length() + 15) + String.valueOf(str).length()) + String.valueOf(namespace).length());
        stringBuilder2.append(str3);
        stringBuilder2.append("://");
        stringBuilder2.append(str);
        stringBuilder2.append("/.ws?ns=");
        stringBuilder2.append(namespace);
        stringBuilder2.append("&v=5");
        str3 = stringBuilder2.toString();
        if (str2 != null) {
            str3 = String.valueOf(str3);
            StringBuilder stringBuilder3 = new StringBuilder((String.valueOf(str3).length() + 4) + String.valueOf(str2).length());
            stringBuilder3.append(str3);
            stringBuilder3.append("&ls=");
            stringBuilder3.append(str2);
            str3 = stringBuilder3.toString();
        }
        URI create = URI.create(str3);
        Map hashMap = new HashMap();
        hashMap.put("User-Agent", this.zzfe.zzv());
        this.zzew = new zzbh(this, new zzjr(this.zzfe, create, null, hashMap), null);
    }

    private final void shutdown() {
        this.zzey = true;
        this.zzfb.zza(this.zzex);
    }

    private final void zzas() {
        if (!this.zzey) {
            zzhz zzhz;
            String stringBuilder;
            if (this.zzfc != null) {
                this.zzfc.cancel(false);
                if (this.zzbs.zzfa()) {
                    zzhz = this.zzbs;
                    long delay = this.zzfc.getDelay(TimeUnit.MILLISECONDS);
                    StringBuilder stringBuilder2 = new StringBuilder(48);
                    stringBuilder2.append("Reset keepAlive. Remaining: ");
                    stringBuilder2.append(delay);
                    stringBuilder = stringBuilder2.toString();
                }
                this.zzfc = this.zzbc.schedule(new zzbe(this), 45000, TimeUnit.MILLISECONDS);
            }
            if (this.zzbs.zzfa()) {
                zzhz = this.zzbs;
                stringBuilder = "Reset keepAlive";
            }
            this.zzfc = this.zzbc.schedule(new zzbe(this), 45000, TimeUnit.MILLISECONDS);
            zzhz.zza(stringBuilder, null, new Object[0]);
            this.zzfc = this.zzbc.schedule(new zzbe(this), 45000, TimeUnit.MILLISECONDS);
        }
    }

    private final void zzat() {
        if (!this.zzey) {
            if (this.zzbs.zzfa()) {
                this.zzbs.zza("closing itself", null, new Object[0]);
            }
            shutdown();
        }
        this.zzew = null;
        if (this.zzfc != null) {
            this.zzfc.cancel(false);
        }
    }

    private final void zzau() {
        if (!this.zzex && !this.zzey) {
            if (this.zzbs.zzfa()) {
                this.zzbs.zza("timed out on connect", null, new Object[0]);
            }
            this.zzew.close();
        }
    }

    private final void zzb(int i) {
        this.zzez = (long) i;
        this.zzfa = new zzbp();
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            long j = this.zzez;
            StringBuilder stringBuilder = new StringBuilder(41);
            stringBuilder.append("HandleNewFrameCount: ");
            stringBuilder.append(j);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
    }

    private final void zzj(String str) {
        zzhz zzhz;
        String str2;
        this.zzfa.zzn(str);
        this.zzez--;
        if (this.zzez == 0) {
            String valueOf;
            try {
                this.zzfa.zzba();
                Map zzv = zzke.zzv(this.zzfa.toString());
                this.zzfa = null;
                if (this.zzbs.zzfa()) {
                    zzhz zzhz2 = this.zzbs;
                    valueOf = String.valueOf(zzv);
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 36);
                    stringBuilder.append("handleIncomingFrame complete frame: ");
                    stringBuilder.append(valueOf);
                    zzhz2.zza(stringBuilder.toString(), null, new Object[0]);
                }
                this.zzfb.zza(zzv);
            } catch (Throwable e) {
                zzhz = this.zzbs;
                str2 = "Error parsing frame: ";
                valueOf = String.valueOf(this.zzfa.toString());
                zzhz.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
                close();
                shutdown();
            } catch (Throwable e2) {
                zzhz = this.zzbs;
                str2 = "Error parsing frame (cast error): ";
                valueOf = String.valueOf(this.zzfa.toString());
                zzhz.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e2);
                close();
                shutdown();
            }
        }
    }

    private final String zzk(String str) {
        if (str.length() <= 6) {
            try {
                int parseInt = Integer.parseInt(str);
                if (parseInt > 0) {
                    zzb(parseInt);
                }
                return null;
            } catch (NumberFormatException e) {
            }
        }
        zzb(1);
        return str;
    }

    private final void zzl(String str) {
        if (!this.zzey) {
            zzas();
            if ((this.zzfa != null ? 1 : null) != null) {
                zzj(str);
                return;
            }
            str = zzk(str);
            if (str != null) {
                zzj(str);
            }
        }
    }

    public final void close() {
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("websocket is being closed", null, new Object[0]);
        }
        this.zzey = true;
        this.zzew.close();
        if (this.zzfd != null) {
            this.zzfd.cancel(true);
        }
        if (this.zzfc != null) {
            this.zzfc.cancel(true);
        }
    }

    public final void open() {
        this.zzew.connect();
        this.zzfd = this.zzbc.schedule(new zzbd(this), 30000, TimeUnit.MILLISECONDS);
    }

    public final void zze(Map<String, Object> map) {
        zzas();
        try {
            String[] strArr;
            int i;
            String zze = zzke.zze(map);
            int i2 = 0;
            if (zze.length() <= 16384) {
                strArr = new String[]{zze};
            } else {
                ArrayList arrayList = new ArrayList();
                i = 0;
                while (i < zze.length()) {
                    int i3 = i + 16384;
                    arrayList.add(zze.substring(i, Math.min(i3, zze.length())));
                    i = i3;
                }
                strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            if (strArr.length > 1) {
                zzbg zzbg = this.zzew;
                i = strArr.length;
                StringBuilder stringBuilder = new StringBuilder(11);
                stringBuilder.append(i);
                zzbg.zzm(stringBuilder.toString());
            }
            while (i2 < strArr.length) {
                this.zzew.zzm(strArr[i2]);
                i2++;
            }
        } catch (Throwable e) {
            zzhz zzhz = this.zzbs;
            String str = "Failed to serialize message: ";
            String valueOf = String.valueOf(map.toString());
            zzhz.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e);
            shutdown();
        }
    }
}
