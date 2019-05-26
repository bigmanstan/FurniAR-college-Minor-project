package com.google.android.gms.internal.firebase_database;

public class zzid implements Comparable<zzid> {
    private static final zzid zzqx = new zzid("[MIN_KEY]");
    private static final zzid zzqy = new zzid("[MAX_KEY]");
    private static final zzid zzqz = new zzid(".priority");
    private static final zzid zzra = new zzid(".info");
    private final String zzqw;

    private static class zza extends zzid {
        private final int zzrb;

        zza(String str, int i) {
            super(str);
            this.zzrb = i;
        }

        protected final int intValue() {
            return this.zzrb;
        }

        public final String toString() {
            String zzj = this.zzqw;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzj).length() + 20);
            stringBuilder.append("IntegerChildName(\"");
            stringBuilder.append(zzj);
            stringBuilder.append("\")");
            return stringBuilder.toString();
        }

        protected final boolean zzfi() {
            return true;
        }
    }

    private zzid(String str) {
        this.zzqw = str;
    }

    public static zzid zzfc() {
        return zzqx;
    }

    public static zzid zzfd() {
        return zzqy;
    }

    public static zzid zzfe() {
        return zzqz;
    }

    public static zzid zzff() {
        return zzra;
    }

    public static zzid zzt(String str) {
        Integer zzaa = zzkq.zzaa(str);
        return zzaa != null ? new zza(str, zzaa.intValue()) : str.equals(".priority") ? zzqz : new zzid(str);
    }

    public /* synthetic */ int compareTo(Object obj) {
        return zzi((zzid) obj);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzid)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return this.zzqw.equals(((zzid) obj).zzqw);
    }

    public int hashCode() {
        return this.zzqw.hashCode();
    }

    protected int intValue() {
        return 0;
    }

    public String toString() {
        String str = this.zzqw;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 12);
        stringBuilder.append("ChildKey(\"");
        stringBuilder.append(str);
        stringBuilder.append("\")");
        return stringBuilder.toString();
    }

    public final String zzfg() {
        return this.zzqw;
    }

    public final boolean zzfh() {
        return this == zzqz;
    }

    protected boolean zzfi() {
        return false;
    }

    public final int zzi(zzid zzid) {
        if (this == zzid) {
            return 0;
        }
        if (this != zzqx) {
            if (zzid != zzqy) {
                if (zzid != zzqx) {
                    if (this != zzqy) {
                        if (!zzfi()) {
                            return zzid.zzfi() ? 1 : this.zzqw.compareTo(zzid.zzqw);
                        } else {
                            if (!zzid.zzfi()) {
                                return -1;
                            }
                            int zza = zzkq.zza(intValue(), zzid.intValue());
                            return zza == 0 ? zzkq.zza(this.zzqw.length(), zzid.zzqw.length()) : zza;
                        }
                    }
                }
                return 1;
            }
        }
        return -1;
    }
}
