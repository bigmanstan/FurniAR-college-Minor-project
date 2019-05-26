package com.google.android.gms.internal.firebase_database;

import java.util.HashMap;
import java.util.Map;

final class zzz implements zzbf {
    private static long zzcb = 0;
    private final zzhz zzbs;
    private zzah zzcc;
    private zzbc zzcd;
    private zzaa zzce;
    private int zzcf = zzac.zzcj;

    public zzz(zzaf zzaf, zzah zzah, String str, zzaa zzaa, String str2) {
        long j = zzcb;
        zzcb = 1 + j;
        this.zzcc = zzah;
        this.zzce = zzaa;
        StringBuilder stringBuilder = new StringBuilder(25);
        stringBuilder.append("conn_");
        stringBuilder.append(j);
        this.zzbs = new zzhz(zzaf.zzq(), "Connection", stringBuilder.toString());
        this.zzcd = new zzbc(zzaf, zzah, str, this, str2);
    }

    private final void zza(zzab zzab) {
        if (this.zzcf != zzac.zzcl) {
            if (this.zzbs.zzfa()) {
                this.zzbs.zza("closing realtime connection", null, new Object[0]);
            }
            this.zzcf = zzac.zzcl;
            if (this.zzcd != null) {
                this.zzcd.close();
                this.zzcd = null;
            }
            this.zzce.zzb(zzab);
        }
    }

    public final void close() {
        zza(zzab.OTHER);
    }

    public final void open() {
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("Opening a connection", null, new Object[0]);
        }
        this.zzcd.open();
    }

    public final void zza(Map<String, Object> map) {
        zzhz zzhz;
        String str;
        String valueOf;
        try {
            String str2 = (String) map.get("t");
            if (str2 == null) {
                if (this.zzbs.zzfa()) {
                    zzhz = this.zzbs;
                    str = "Failed to parse server message: missing message type:";
                    valueOf = String.valueOf(map.toString());
                    zzhz.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
                }
                zza(zzab.OTHER);
            } else if (str2.equals("d")) {
                r8 = (Map) map.get("d");
                if (this.zzbs.zzfa()) {
                    zzhz = this.zzbs;
                    str = "received data message: ";
                    r4 = String.valueOf(r8.toString());
                    zzhz.zza(r4.length() != 0 ? str.concat(r4) : new String(str), null, new Object[0]);
                }
                this.zzce.zzb(r8);
            } else if (str2.equals("c")) {
                r8 = (Map) map.get("d");
                if (this.zzbs.zzfa()) {
                    zzhz = this.zzbs;
                    str = "Got control message: ";
                    r4 = String.valueOf(r8.toString());
                    zzhz.zza(r4.length() != 0 ? str.concat(r4) : new String(str), null, new Object[0]);
                }
                try {
                    str2 = (String) r8.get("t");
                    if (str2 != null) {
                        zzab zzab;
                        if (str2.equals("s")) {
                            valueOf = (String) r8.get("d");
                            if (this.zzbs.zzfa()) {
                                this.zzbs.zza("Connection shutdown command received. Shutting down...", null, new Object[0]);
                            }
                            this.zzce.zze(valueOf);
                            zzab = zzab.OTHER;
                        } else if (str2.equals("r")) {
                            valueOf = (String) r8.get("d");
                            if (this.zzbs.zzfa()) {
                                zzhz = this.zzbs;
                                str = this.zzcc.getHost();
                                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 62) + String.valueOf(valueOf).length());
                                stringBuilder.append("Got a reset; killing connection to ");
                                stringBuilder.append(str);
                                stringBuilder.append("; Updating internalHost to ");
                                stringBuilder.append(valueOf);
                                zzhz.zza(stringBuilder.toString(), null, new Object[0]);
                            }
                            this.zzce.zzd(valueOf);
                            zzab = zzab.SERVER_RESET;
                        } else {
                            if (str2.equals("h")) {
                                r8 = (Map) r8.get("d");
                                long longValue = ((Long) r8.get("ts")).longValue();
                                this.zzce.zzd((String) r8.get("h"));
                                valueOf = (String) r8.get("s");
                                if (this.zzcf == zzac.zzcj) {
                                    if (this.zzbs.zzfa()) {
                                        this.zzbs.zza("realtime connection established", null, new Object[0]);
                                    }
                                    this.zzcf = zzac.zzck;
                                    this.zzce.zza(longValue, valueOf);
                                }
                            } else if (this.zzbs.zzfa()) {
                                r8 = this.zzbs;
                                str = "Ignoring unknown control message: ";
                                str2 = String.valueOf(str2);
                                r8.zza(str2.length() != 0 ? str.concat(str2) : new String(str), null, new Object[0]);
                            }
                            return;
                        }
                        zza(zzab);
                        return;
                    }
                    if (this.zzbs.zzfa()) {
                        zzhz = this.zzbs;
                        str = "Got invalid control message: ";
                        valueOf = String.valueOf(r8.toString());
                        zzhz.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
                    }
                    zza(zzab.OTHER);
                } catch (ClassCastException e) {
                    if (this.zzbs.zzfa()) {
                        zzhz = this.zzbs;
                        str = "Failed to parse control message: ";
                        valueOf = String.valueOf(e.toString());
                        zzhz.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
                    }
                    zza(zzab.OTHER);
                }
            } else if (this.zzbs.zzfa()) {
                r8 = this.zzbs;
                str = "Ignoring unknown server message type: ";
                str2 = String.valueOf(str2);
                r8.zza(str2.length() != 0 ? str.concat(str2) : new String(str), null, new Object[0]);
            }
        } catch (ClassCastException e2) {
            if (this.zzbs.zzfa()) {
                zzhz = this.zzbs;
                str = "Failed to parse server message: ";
                valueOf = String.valueOf(e2.toString());
                zzhz.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
            }
            zza(zzab.OTHER);
        }
    }

    public final void zza(Map<String, Object> map, boolean z) {
        Map hashMap = new HashMap();
        hashMap.put("t", "d");
        hashMap.put("d", map);
        if (this.zzcf != zzac.zzck) {
            this.zzbs.zza("Tried to send on an unconnected connection", null, new Object[0]);
            return;
        }
        zzhz zzhz;
        String str;
        Object[] objArr;
        if (z) {
            zzhz = this.zzbs;
            str = "Sending data (contents hidden)";
            objArr = new Object[0];
        } else {
            zzhz = this.zzbs;
            str = "Sending data: %s";
            objArr = new Object[]{hashMap};
        }
        zzhz.zza(str, null, objArr);
        this.zzcd.zze(hashMap);
    }

    public final void zza(boolean z) {
        zzhz zzhz;
        String str;
        this.zzcd = null;
        if (z || this.zzcf != zzac.zzcj) {
            if (this.zzbs.zzfa()) {
                zzhz = this.zzbs;
                str = "Realtime connection lost";
            }
            zza(zzab.OTHER);
        }
        if (this.zzbs.zzfa()) {
            zzhz = this.zzbs;
            str = "Realtime connection failed";
        }
        zza(zzab.OTHER);
        zzhz.zza(str, null, new Object[0]);
        zza(zzab.OTHER);
    }
}
