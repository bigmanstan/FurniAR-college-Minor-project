package com.google.android.gms.internal.firebase_database;

import java.util.HashMap;
import java.util.Map;

public final class zzhe {
    public static final zzhe zzph = new zzhe();
    private zzis zzpd = zzjf.zzgf();
    private Integer zzpi;
    private int zzpj;
    private zzja zzpk = null;
    private zzid zzpl = null;
    private zzja zzpm = null;
    private zzid zzpn = null;
    private String zzpo = null;

    private static zzja zze(zzja zzja) {
        if (!((zzja instanceof zzji) || (zzja instanceof zzic) || (zzja instanceof zziq))) {
            if (!(zzja instanceof zzir)) {
                if (zzja instanceof zziy) {
                    return new zziq(Double.valueOf(((Long) zzja.getValue()).doubleValue()), zzir.zzfv());
                }
                String valueOf = String.valueOf(zzja.getValue());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 43);
                stringBuilder.append("Unexpected value passed to normalizeValue: ");
                stringBuilder.append(valueOf);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return zzja;
    }

    private final zzhe zzeh() {
        zzhe zzhe = new zzhe();
        zzhe.zzpi = this.zzpi;
        zzhe.zzpk = this.zzpk;
        zzhe.zzpl = this.zzpl;
        zzhe.zzpm = this.zzpm;
        zzhe.zzpn = this.zzpn;
        zzhe.zzpj = this.zzpj;
        zzhe.zzpd = this.zzpd;
        return zzhe;
    }

    public static zzhe zzh(Map<String, Object> map) {
        String str;
        zzhe zzhe = new zzhe();
        zzhe.zzpi = (Integer) map.get("l");
        if (map.containsKey("sp")) {
            zzhe.zzpk = zze(zzjd.zza(map.get("sp"), zzir.zzfv()));
            str = (String) map.get("sn");
            if (str != null) {
                zzhe.zzpl = zzid.zzt(str);
            }
        }
        if (map.containsKey("ep")) {
            zzhe.zzpm = zze(zzjd.zza(map.get("ep"), zzir.zzfv()));
            str = (String) map.get("en");
            if (str != null) {
                zzhe.zzpn = zzid.zzt(str);
            }
        }
        str = (String) map.get("vf");
        if (str != null) {
            zzhe.zzpj = str.equals("l") ? zzhg.zzpq : zzhg.zzpr;
        }
        String str2 = (String) map.get("i");
        if (str2 != null) {
            zzis zzgg;
            if (str2.equals(".value")) {
                zzgg = zzjk.zzgg();
            } else if (str2.equals(".key")) {
                zzgg = zziu.zzgb();
            } else if (str2.equals(".priority")) {
                throw new IllegalStateException("queryDefinition shouldn't ever be .priority since it's the default");
            } else {
                zzgg = new zzje(new zzch(str2));
            }
            zzhe.zzpd = zzgg;
        }
        return zzhe;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
        r4 = this;
        r0 = 1;
        if (r4 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = 0;
        if (r5 == 0) goto L_0x0099;
    L_0x0007:
        r2 = r4.getClass();
        r3 = r5.getClass();
        if (r2 == r3) goto L_0x0013;
    L_0x0011:
        goto L_0x0099;
    L_0x0013:
        r5 = (com.google.android.gms.internal.firebase_database.zzhe) r5;
        r2 = r4.zzpi;
        if (r2 == 0) goto L_0x0024;
    L_0x0019:
        r2 = r4.zzpi;
        r3 = r5.zzpi;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0029;
    L_0x0023:
        goto L_0x0028;
    L_0x0024:
        r2 = r5.zzpi;
        if (r2 == 0) goto L_0x0029;
    L_0x0028:
        return r1;
    L_0x0029:
        r2 = r4.zzpd;
        if (r2 == 0) goto L_0x0038;
    L_0x002d:
        r2 = r4.zzpd;
        r3 = r5.zzpd;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x003d;
    L_0x0037:
        goto L_0x003c;
    L_0x0038:
        r2 = r5.zzpd;
        if (r2 == 0) goto L_0x003d;
    L_0x003c:
        return r1;
    L_0x003d:
        r2 = r4.zzpn;
        if (r2 == 0) goto L_0x004c;
    L_0x0041:
        r2 = r4.zzpn;
        r3 = r5.zzpn;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0051;
    L_0x004b:
        goto L_0x0050;
    L_0x004c:
        r2 = r5.zzpn;
        if (r2 == 0) goto L_0x0051;
    L_0x0050:
        return r1;
    L_0x0051:
        r2 = r4.zzpm;
        if (r2 == 0) goto L_0x0060;
    L_0x0055:
        r2 = r4.zzpm;
        r3 = r5.zzpm;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0065;
    L_0x005f:
        goto L_0x0064;
    L_0x0060:
        r2 = r5.zzpm;
        if (r2 == 0) goto L_0x0065;
    L_0x0064:
        return r1;
    L_0x0065:
        r2 = r4.zzpl;
        if (r2 == 0) goto L_0x0074;
    L_0x0069:
        r2 = r4.zzpl;
        r3 = r5.zzpl;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x0079;
    L_0x0073:
        goto L_0x0078;
    L_0x0074:
        r2 = r5.zzpl;
        if (r2 == 0) goto L_0x0079;
    L_0x0078:
        return r1;
    L_0x0079:
        r2 = r4.zzpk;
        if (r2 == 0) goto L_0x0088;
    L_0x007d:
        r2 = r4.zzpk;
        r3 = r5.zzpk;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x008d;
    L_0x0087:
        goto L_0x008c;
    L_0x0088:
        r2 = r5.zzpk;
        if (r2 == 0) goto L_0x008d;
    L_0x008c:
        return r1;
    L_0x008d:
        r2 = r4.zzei();
        r5 = r5.zzei();
        if (r2 == r5) goto L_0x0098;
    L_0x0097:
        return r1;
    L_0x0098:
        return r0;
    L_0x0099:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzhe.equals(java.lang.Object):boolean");
    }

    public final int getLimit() {
        if (zzee()) {
            return this.zzpi.intValue();
        }
        throw new IllegalArgumentException("Cannot get limit if limit has not been set");
    }

    public final int hashCode() {
        int i = 0;
        int intValue = (((((((((((this.zzpi != null ? this.zzpi.intValue() : 0) * 31) + (zzei() ? 1231 : 1237)) * 31) + (this.zzpk != null ? this.zzpk.hashCode() : 0)) * 31) + (this.zzpl != null ? this.zzpl.hashCode() : 0)) * 31) + (this.zzpm != null ? this.zzpm.hashCode() : 0)) * 31) + (this.zzpn != null ? this.zzpn.hashCode() : 0)) * 31;
        if (this.zzpd != null) {
            i = this.zzpd.hashCode();
        }
        return intValue + i;
    }

    public final boolean isDefault() {
        return zzek() && this.zzpd.equals(zzjf.zzgf());
    }

    public final String toString() {
        return zzej().toString();
    }

    public final zzhe zza(zzis zzis) {
        zzhe zzeh = zzeh();
        zzeh.zzpd = zzis;
        return zzeh;
    }

    public final zzhe zza(zzja zzja, zzid zzid) {
        zzkq.zzf(!(zzja instanceof zziy));
        zzhe zzeh = zzeh();
        zzeh.zzpk = zzja;
        zzeh.zzpl = zzid;
        return zzeh;
    }

    public final zzhe zzb(zzja zzja, zzid zzid) {
        zzkq.zzf(!(zzja instanceof zziy));
        zzhe zzeh = zzeh();
        zzeh.zzpm = zzja;
        zzeh.zzpn = zzid;
        return zzeh;
    }

    public final zzhe zzc(int i) {
        zzhe zzeh = zzeh();
        zzeh.zzpi = Integer.valueOf(i);
        zzeh.zzpj = zzhg.zzpq;
        return zzeh;
    }

    public final zzhe zzd(int i) {
        zzhe zzeh = zzeh();
        zzeh.zzpi = Integer.valueOf(i);
        zzeh.zzpj = zzhg.zzpr;
        return zzeh;
    }

    public final boolean zzdy() {
        return this.zzpk != null;
    }

    public final zzja zzdz() {
        if (zzdy()) {
            return this.zzpk;
        }
        throw new IllegalArgumentException("Cannot get index start value if start has not been set");
    }

    public final zzid zzea() {
        if (zzdy()) {
            return this.zzpl != null ? this.zzpl : zzid.zzfc();
        } else {
            throw new IllegalArgumentException("Cannot get index start name if start has not been set");
        }
    }

    public final boolean zzeb() {
        return this.zzpm != null;
    }

    public final zzja zzec() {
        if (zzeb()) {
            return this.zzpm;
        }
        throw new IllegalArgumentException("Cannot get index end value if start has not been set");
    }

    public final zzid zzed() {
        if (zzeb()) {
            return this.zzpn != null ? this.zzpn : zzid.zzfd();
        } else {
            throw new IllegalArgumentException("Cannot get index end name if start has not been set");
        }
    }

    public final boolean zzee() {
        return this.zzpi != null;
    }

    public final boolean zzef() {
        return zzee() && this.zzpj != 0;
    }

    public final zzis zzeg() {
        return this.zzpd;
    }

    public final boolean zzei() {
        return this.zzpj != 0 ? this.zzpj == zzhg.zzpq : zzdy();
    }

    public final Map<String, Object> zzej() {
        Map<String, Object> hashMap = new HashMap();
        if (zzdy()) {
            hashMap.put("sp", this.zzpk.getValue());
            if (this.zzpl != null) {
                hashMap.put("sn", this.zzpl.zzfg());
            }
        }
        if (zzeb()) {
            hashMap.put("ep", this.zzpm.getValue());
            if (this.zzpn != null) {
                hashMap.put("en", this.zzpn.zzfg());
            }
        }
        if (this.zzpi != null) {
            Object obj;
            Object obj2;
            hashMap.put("l", this.zzpi);
            int i = this.zzpj;
            if (i == 0) {
                i = zzdy() ? zzhg.zzpq : zzhg.zzpr;
            }
            switch (zzhf.zzpp[i - 1]) {
                case 1:
                    obj = "vf";
                    obj2 = "l";
                    break;
                case 2:
                    obj = "vf";
                    obj2 = "r";
                    break;
                default:
                    break;
            }
            hashMap.put(obj, obj2);
        }
        if (!this.zzpd.equals(zzjf.zzgf())) {
            hashMap.put("i", this.zzpd.zzfx());
        }
        return hashMap;
    }

    public final boolean zzek() {
        return (zzdy() || zzeb() || zzee()) ? false : true;
    }

    public final String zzel() {
        if (this.zzpo == null) {
            try {
                this.zzpo = zzke.zze(zzej());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return this.zzpo;
    }

    public final zzht zzem() {
        return zzek() ? new zzhr(this.zzpd) : zzee() ? new zzhs(this) : new zzhv(this);
    }
}
