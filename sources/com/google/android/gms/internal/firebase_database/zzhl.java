package com.google.android.gms.internal.firebase_database;

import java.util.Map;
import java.util.Map.Entry;

public final class zzhl {
    private static zzhu zzqc = new zzhm();
    private final zzht zzqb;

    public zzhl(zzht zzht) {
        this.zzqb = zzht;
    }

    private final zzhk zza(zzhk zzhk, zzch zzch, zzbv zzbv, zzfg zzfg, zzja zzja, boolean z, zzhq zzhq) {
        if (zzhk.zzet().zzd().isEmpty() && !zzhk.zzet().zzdo()) {
            return zzhk;
        }
        zzbv zzb = zzch.isEmpty() ? zzbv : zzbv.zzbf().zzb(zzch, zzbv);
        zzja zzd = zzhk.zzet().zzd();
        Map zzbi = zzb.zzbi();
        zzhk zzhk2 = zzhk;
        for (Entry entry : zzbi.entrySet()) {
            zzid zzid = (zzid) entry.getKey();
            if (zzd.zzk(zzid)) {
                zzhk2 = zza(zzhk2, new zzch(zzid), ((zzbv) entry.getValue()).zzb(zzd.zzm(zzid)), zzfg, zzja, z, zzhq);
            }
        }
        zzhk zzhk3 = zzhk2;
        for (Entry entry2 : zzbi.entrySet()) {
            zzid zzid2 = (zzid) entry2.getKey();
            int i = (zzhk.zzet().zzf(zzid2) || ((zzbv) entry2.getValue()).zzbg() != null) ? 0 : 1;
            if (!zzd.zzk(zzid2) && i == 0) {
                zzhk3 = zza(zzhk3, new zzch(zzid2), ((zzbv) entry2.getValue()).zzb(zzd.zzm(zzid2)), zzfg, zzja, z, zzhq);
            }
        }
        return zzhk3;
    }

    private final zzhk zza(zzhk zzhk, zzch zzch, zzfg zzfg, zzhu zzhu, zzhq zzhq) {
        zzgu zzer = zzhk.zzer();
        if (zzfg.zzu(zzch) != null) {
            return zzhk;
        }
        zzit zza;
        boolean z;
        zzja zzd;
        if (zzch.isEmpty()) {
            if (zzhk.zzet().zzdp()) {
                zzja zzeu = zzhk.zzeu();
                if (!(zzeu instanceof zzif)) {
                    zzeu = zzir.zzfv();
                }
                zzd = zzfg.zzd(zzeu);
            } else {
                zzd = zzfg.zzc(zzhk.zzeu());
            }
            zza = this.zzqb.zza(zzhk.zzer().zzdq(), zzit.zza(zzd, this.zzqb.zzeg()), zzhq);
        } else {
            zzid zzbw = zzch.zzbw();
            if (zzbw.zzfh()) {
                zzd = zzfg.zza(zzch, zzer.zzd(), zzhk.zzet().zzd());
                if (zzd != null) {
                    zza = this.zzqb.zza(zzer.zzdq(), zzd);
                }
            } else {
                zzch zzbx = zzch.zzbx();
                if (zzer.zzf(zzbw)) {
                    zzd = zzfg.zza(zzch, zzer.zzd(), zzhk.zzet().zzd());
                    zzd = zzd != null ? zzer.zzd().zzm(zzbw).zzl(zzbx, zzd) : zzer.zzd().zzm(zzbw);
                } else {
                    zzd = zzfg.zza(zzbw, zzhk.zzet());
                }
                zzja zzja = zzd;
                if (zzja != null) {
                    zza = this.zzqb.zza(zzer.zzdq(), zzbw, zzja, zzbx, zzhu, zzhq);
                }
            }
            zza = zzer.zzdq();
        }
        if (!zzer.zzdo()) {
            if (!zzch.isEmpty()) {
                z = false;
                return zzhk.zza(zza, z, this.zzqb.zzex());
            }
        }
        z = true;
        return zzhk.zza(zza, z, this.zzqb.zzex());
    }

    private final zzhk zza(zzhk zzhk, zzch zzch, zzja zzja, zzfg zzfg, zzja zzja2, zzhq zzhq) {
        zzit zza;
        boolean z;
        boolean zzdp;
        zzgu zzer = zzhk.zzer();
        zzhu zzhp = new zzhp(zzfg, zzhk, zzja2);
        if (zzch.isEmpty()) {
            zza = this.zzqb.zza(zzhk.zzer().zzdq(), zzit.zza(zzja, this.zzqb.zzeg()), zzhq);
            z = true;
        } else {
            zzid zzbw = zzch.zzbw();
            if (zzbw.zzfh()) {
                zza = this.zzqb.zza(zzhk.zzer().zzdq(), zzja);
                z = zzer.zzdo();
                zzdp = zzer.zzdp();
                return zzhk.zza(zza, z, zzdp);
            }
            zzja zzja3;
            zzch zzbx = zzch.zzbx();
            zzja zzm = zzer.zzd().zzm(zzbw);
            if (!zzbx.isEmpty()) {
                zzja zzh = zzhp.zzh(zzbw);
                if (zzh == null) {
                    zzja = zzir.zzfv();
                } else if (zzbx.zzbz().zzfh() && zzh.zzam(zzbx.zzby()).isEmpty()) {
                    zzja3 = zzh;
                    if (!zzm.equals(zzja3)) {
                        return zzhk;
                    }
                    zza = this.zzqb.zza(zzer.zzdq(), zzbw, zzja3, zzbx, zzhp, zzhq);
                    z = zzer.zzdo();
                } else {
                    zzja = zzh.zzl(zzbx, zzja);
                }
            }
            zzja3 = zzja;
            if (!zzm.equals(zzja3)) {
                return zzhk;
            }
            zza = this.zzqb.zza(zzer.zzdq(), zzbw, zzja3, zzbx, zzhp, zzhq);
            z = zzer.zzdo();
        }
        zzdp = this.zzqb.zzex();
        return zzhk.zza(zza, z, zzdp);
    }

    private final zzhk zza(zzhk zzhk, zzch zzch, zzja zzja, zzfg zzfg, zzja zzja2, boolean z, zzhq zzhq) {
        zzit zzdq;
        zzit zza;
        zzfg zzfg2;
        zzhl zzhl = this;
        zzhk zzhk2 = zzhk;
        zzja zzja3 = zzja;
        zzgu zzet = zzhk.zzet();
        zzht zzew = z ? zzhl.zzqb : zzhl.zzqb.zzew();
        boolean z2 = true;
        if (zzch.isEmpty()) {
            zzdq = zzet.zzdq();
            zza = zzit.zza(zzja3, zzew.zzeg());
        } else if (!zzew.zzex() || zzet.zzdp()) {
            zzid zzbw = zzch.zzbw();
            if (!zzet.zzak(zzch) && zzch.size() > 1) {
                return zzhk2;
            }
            zzch zzbx = zzch.zzbx();
            zzja zzl = zzet.zzd().zzm(zzbw).zzl(zzbx, zzja3);
            zza = zzbw.zzfh() ? zzew.zza(zzet.zzdq(), zzl) : zzew.zza(zzet.zzdq(), zzbw, zzl, zzbx, zzqc, null);
            if (!zzet.zzdo()) {
                if (zzch.isEmpty()) {
                    z2 = false;
                }
            }
            zzhk2 = zzhk.zzb(zza, z2, zzew.zzex());
            zzfg2 = zzfg;
            return zza(zzhk2, zzch, zzfg2, new zzhp(zzfg2, zzhk2, zzja2), zzhq);
        } else {
            zzid zzbw2 = zzch.zzbw();
            zza = zzet.zzdq().zzg(zzbw2, zzet.zzd().zzm(zzbw2).zzl(zzch.zzbx(), zzja3));
            zzdq = zzet.zzdq();
        }
        zza = zzew.zza(zzdq, zza, null);
        zzch zzch2 = zzch;
        if (zzet.zzdo()) {
            if (zzch.isEmpty()) {
                z2 = false;
            }
        }
        zzhk2 = zzhk.zzb(zza, z2, zzew.zzex());
        zzfg2 = zzfg;
        return zza(zzhk2, zzch, zzfg2, new zzhp(zzfg2, zzhk2, zzja2), zzhq);
    }

    private static boolean zza(zzhk zzhk, zzid zzid) {
        return zzhk.zzer().zzf(zzid);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.firebase_database.zzho zza(com.google.android.gms.internal.firebase_database.zzhk r18, com.google.android.gms.internal.firebase_database.zzfl r19, com.google.android.gms.internal.firebase_database.zzfg r20, com.google.android.gms.internal.firebase_database.zzja r21) {
        /*
        r17 = this;
        r7 = r17;
        r8 = r18;
        r9 = r20;
        r10 = new com.google.android.gms.internal.firebase_database.zzhq;
        r10.<init>();
        r1 = com.google.android.gms.internal.firebase_database.zzhn.zzqd;
        r2 = r19.zzcz();
        r2 = r2.ordinal();
        r1 = r1[r2];
        r11 = 0;
        r12 = 1;
        switch(r1) {
            case 1: goto L_0x02d7;
            case 2: goto L_0x021e;
            case 3: goto L_0x0076;
            case 4: goto L_0x0045;
            default: goto L_0x001c;
        };
    L_0x001c:
        r1 = new java.lang.AssertionError;
        r0 = r19.zzcz();
        r0 = java.lang.String.valueOf(r0);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 19;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Unknown operation: ";
        r3.append(r2);
        r3.append(r0);
        r0 = r3.toString();
        r1.<init>(r0);
        throw r1;
    L_0x0045:
        r2 = r19.zzg();
        r0 = r18.zzet();
        r1 = r0.zzdq();
        r3 = r0.zzdo();
        if (r3 != 0) goto L_0x0060;
    L_0x0057:
        r3 = r2.isEmpty();
        if (r3 == 0) goto L_0x005e;
    L_0x005d:
        goto L_0x0060;
    L_0x005e:
        r3 = r11;
        goto L_0x0061;
    L_0x0060:
        r3 = r12;
    L_0x0061:
        r0 = r0.zzdp();
        r1 = r8.zzb(r1, r3, r0);
        r4 = zzqc;
        r0 = r17;
        r3 = r20;
        r5 = r10;
        r0 = r0.zza(r1, r2, r3, r4, r5);
        goto L_0x0334;
    L_0x0076:
        r0 = r19;
        r0 = (com.google.android.gms.internal.firebase_database.zzfi) r0;
        r1 = r0.zzcw();
        if (r1 != 0) goto L_0x012f;
    L_0x0080:
        r2 = r0.zzg();
        r0 = r0.zzcv();
        r1 = r9.zzu(r2);
        if (r1 == 0) goto L_0x008f;
    L_0x008e:
        goto L_0x00f1;
    L_0x008f:
        r1 = r18.zzet();
        r6 = r1.zzdp();
        r1 = r18.zzet();
        r3 = r0.getValue();
        if (r3 == 0) goto L_0x00f4;
    L_0x00a1:
        r0 = r2.isEmpty();
        if (r0 == 0) goto L_0x00ad;
    L_0x00a7:
        r0 = r1.zzdo();
        if (r0 != 0) goto L_0x00b3;
    L_0x00ad:
        r0 = r1.zzak(r2);
        if (r0 == 0) goto L_0x00c5;
    L_0x00b3:
        r0 = r1.zzd();
        r3 = r0.zzam(r2);
        r0 = r17;
        r1 = r18;
        r4 = r20;
        r5 = r21;
        goto L_0x032f;
    L_0x00c5:
        r0 = r2.isEmpty();
        if (r0 == 0) goto L_0x00f1;
    L_0x00cb:
        r0 = com.google.android.gms.internal.firebase_database.zzbv.zzbf();
        r1 = r1.zzd();
        r1 = r1.iterator();
        r3 = r0;
    L_0x00d8:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0125;
    L_0x00de:
        r0 = r1.next();
        r0 = (com.google.android.gms.internal.firebase_database.zziz) r0;
        r4 = r0.zzge();
        r0 = r0.zzd();
        r3 = r3.zza(r4, r0);
        goto L_0x00d8;
    L_0x00f1:
        r0 = r8;
        goto L_0x0334;
    L_0x00f4:
        r3 = com.google.android.gms.internal.firebase_database.zzbv.zzbf();
        r0 = r0.iterator();
    L_0x00fc:
        r4 = r0.hasNext();
        if (r4 == 0) goto L_0x0125;
    L_0x0102:
        r4 = r0.next();
        r4 = (java.util.Map.Entry) r4;
        r4 = r4.getKey();
        r4 = (com.google.android.gms.internal.firebase_database.zzch) r4;
        r5 = r2.zzh(r4);
        r13 = r1.zzak(r5);
        if (r13 == 0) goto L_0x00fc;
    L_0x0118:
        r13 = r1.zzd();
        r5 = r13.zzam(r5);
        r3 = r3.zze(r4, r5);
        goto L_0x00fc;
    L_0x0125:
        r0 = r17;
        r1 = r18;
        r4 = r20;
        r5 = r21;
        goto L_0x02d1;
    L_0x012f:
        r0 = r0.zzg();
        r1 = r9.zzu(r0);
        if (r1 == 0) goto L_0x013a;
    L_0x0139:
        goto L_0x00f1;
    L_0x013a:
        r5 = new com.google.android.gms.internal.firebase_database.zzhp;
        r13 = r21;
        r5.<init>(r9, r8, r13);
        r1 = r18.zzer();
        r1 = r1.zzdq();
        r2 = r0.isEmpty();
        if (r2 != 0) goto L_0x01cb;
    L_0x014f:
        r2 = r0.zzbw();
        r2 = r2.zzfh();
        if (r2 == 0) goto L_0x015b;
    L_0x0159:
        goto L_0x01cb;
    L_0x015b:
        r2 = r0.zzbw();
        r3 = r18.zzet();
        r3 = r9.zza(r2, r3);
        if (r3 != 0) goto L_0x017b;
    L_0x0169:
        r4 = r18.zzet();
        r4 = r4.zzf(r2);
        if (r4 == 0) goto L_0x017b;
    L_0x0173:
        r3 = r1.zzd();
        r3 = r3.zzm(r2);
    L_0x017b:
        if (r3 == 0) goto L_0x018b;
    L_0x017d:
        r4 = r7.zzqb;
        r6 = r0.zzbx();
        r0 = r4;
    L_0x0184:
        r4 = r6;
        r6 = r10;
        r1 = r0.zza(r1, r2, r3, r4, r5, r6);
        goto L_0x01a8;
    L_0x018b:
        if (r3 != 0) goto L_0x01a8;
    L_0x018d:
        r3 = r18.zzer();
        r3 = r3.zzd();
        r3 = r3.zzk(r2);
        if (r3 == 0) goto L_0x01a8;
    L_0x019b:
        r3 = r7.zzqb;
        r4 = com.google.android.gms.internal.firebase_database.zzir.zzfv();
        r6 = r0.zzbx();
        r0 = r3;
        r3 = r4;
        goto L_0x0184;
    L_0x01a8:
        r0 = r1.zzd();
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x01fa;
    L_0x01b2:
        r0 = r18.zzet();
        r0 = r0.zzdo();
        if (r0 == 0) goto L_0x01fa;
    L_0x01bc:
        r0 = r18.zzeu();
        r0 = r9.zzc(r0);
        r2 = r0.zzfk();
        if (r2 == 0) goto L_0x01fa;
    L_0x01ca:
        goto L_0x01ea;
    L_0x01cb:
        r0 = r18.zzet();
        r0 = r0.zzdo();
        if (r0 == 0) goto L_0x01de;
    L_0x01d5:
        r0 = r18.zzeu();
        r0 = r9.zzc(r0);
        goto L_0x01ea;
    L_0x01de:
        r0 = r18.zzet();
        r0 = r0.zzd();
        r0 = r9.zzd(r0);
    L_0x01ea:
        r2 = r7.zzqb;
        r2 = r2.zzeg();
        r0 = com.google.android.gms.internal.firebase_database.zzit.zza(r0, r2);
        r2 = r7.zzqb;
        r1 = r2.zza(r1, r0, r10);
    L_0x01fa:
        r0 = r18.zzet();
        r0 = r0.zzdo();
        if (r0 != 0) goto L_0x0211;
    L_0x0204:
        r0 = com.google.android.gms.internal.firebase_database.zzch.zzbt();
        r0 = r9.zzu(r0);
        if (r0 == 0) goto L_0x020f;
    L_0x020e:
        goto L_0x0211;
    L_0x020f:
        r0 = r11;
        goto L_0x0212;
    L_0x0211:
        r0 = r12;
    L_0x0212:
        r2 = r7.zzqb;
        r2 = r2.zzex();
        r0 = r8.zza(r1, r0, r2);
        goto L_0x0334;
    L_0x021e:
        r13 = r21;
        r0 = r19;
        r0 = (com.google.android.gms.internal.firebase_database.zzfk) r0;
        r1 = r0.zzcy();
        r1 = r1.zzda();
        if (r1 == 0) goto L_0x02a9;
    L_0x022e:
        r14 = r0.zzg();
        r15 = r0.zzcx();
        r16 = r15.iterator();
        r1 = r8;
    L_0x023b:
        r0 = r16.hasNext();
        if (r0 == 0) goto L_0x026f;
    L_0x0241:
        r0 = r16.next();
        r0 = (java.util.Map.Entry) r0;
        r2 = r0.getKey();
        r2 = (com.google.android.gms.internal.firebase_database.zzch) r2;
        r2 = r14.zzh(r2);
        r3 = r2.zzbw();
        r3 = zza(r8, r3);
        if (r3 == 0) goto L_0x023b;
    L_0x025b:
        r0 = r0.getValue();
        r3 = r0;
        r3 = (com.google.android.gms.internal.firebase_database.zzja) r3;
        r0 = r17;
        r4 = r20;
        r5 = r21;
        r6 = r10;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6);
        r1 = r0;
        goto L_0x023b;
    L_0x026f:
        r15 = r15.iterator();
    L_0x0273:
        r0 = r15.hasNext();
        if (r0 == 0) goto L_0x02a6;
    L_0x0279:
        r0 = r15.next();
        r0 = (java.util.Map.Entry) r0;
        r2 = r0.getKey();
        r2 = (com.google.android.gms.internal.firebase_database.zzch) r2;
        r2 = r14.zzh(r2);
        r3 = r2.zzbw();
        r3 = zza(r8, r3);
        if (r3 != 0) goto L_0x0273;
    L_0x0293:
        r0 = r0.getValue();
        r3 = r0;
        r3 = (com.google.android.gms.internal.firebase_database.zzja) r3;
        r0 = r17;
        r4 = r20;
        r5 = r21;
        r6 = r10;
        r1 = r0.zza(r1, r2, r3, r4, r5, r6);
        goto L_0x0273;
    L_0x02a6:
        r0 = r1;
        goto L_0x0334;
    L_0x02a9:
        r1 = r0.zzcy();
        r1 = r1.zzdb();
        if (r1 != 0) goto L_0x02c0;
    L_0x02b3:
        r1 = r18.zzet();
        r1 = r1.zzdp();
        if (r1 == 0) goto L_0x02be;
    L_0x02bd:
        goto L_0x02c0;
    L_0x02be:
        r6 = r11;
        goto L_0x02c1;
    L_0x02c0:
        r6 = r12;
    L_0x02c1:
        r2 = r0.zzg();
        r3 = r0.zzcx();
        r0 = r17;
        r1 = r18;
        r4 = r20;
        r5 = r21;
    L_0x02d1:
        r7 = r10;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7);
        goto L_0x0334;
    L_0x02d7:
        r13 = r21;
        r0 = r19;
        r0 = (com.google.android.gms.internal.firebase_database.zzfp) r0;
        r1 = r0.zzcy();
        r1 = r1.zzda();
        if (r1 == 0) goto L_0x02fd;
    L_0x02e7:
        r2 = r0.zzg();
        r3 = r0.zzdd();
        r0 = r17;
        r1 = r18;
        r4 = r20;
        r5 = r21;
        r6 = r10;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6);
        goto L_0x0334;
    L_0x02fd:
        r1 = r0.zzcy();
        r1 = r1.zzdb();
        if (r1 != 0) goto L_0x031e;
    L_0x0307:
        r1 = r18.zzet();
        r1 = r1.zzdp();
        if (r1 == 0) goto L_0x031c;
    L_0x0311:
        r1 = r0.zzg();
        r1 = r1.isEmpty();
        if (r1 != 0) goto L_0x031c;
    L_0x031b:
        goto L_0x031e;
    L_0x031c:
        r6 = r11;
        goto L_0x031f;
    L_0x031e:
        r6 = r12;
    L_0x031f:
        r2 = r0.zzg();
        r3 = r0.zzdd();
        r0 = r17;
        r1 = r18;
        r4 = r20;
        r5 = r21;
    L_0x032f:
        r7 = r10;
        r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7);
    L_0x0334:
        r1 = new java.util.ArrayList;
        r2 = r10.zzev();
        r1.<init>(r2);
        r2 = r0.zzer();
        r3 = r2.zzdo();
        if (r3 == 0) goto L_0x039d;
    L_0x0347:
        r3 = r2.zzd();
        r3 = r3.zzfk();
        if (r3 != 0) goto L_0x035b;
    L_0x0351:
        r3 = r2.zzd();
        r3 = r3.isEmpty();
        if (r3 == 0) goto L_0x035c;
    L_0x035b:
        r11 = r12;
    L_0x035c:
        r3 = r1.isEmpty();
        if (r3 == 0) goto L_0x0392;
    L_0x0362:
        r3 = r18.zzer();
        r3 = r3.zzdo();
        if (r3 == 0) goto L_0x0392;
    L_0x036c:
        if (r11 == 0) goto L_0x037c;
    L_0x036e:
        r3 = r2.zzd();
        r4 = r18.zzes();
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0392;
    L_0x037c:
        r3 = r2.zzd();
        r3 = r3.zzfl();
        r4 = r18.zzes();
        r4 = r4.zzfl();
        r3 = r3.equals(r4);
        if (r3 != 0) goto L_0x039d;
    L_0x0392:
        r2 = r2.zzdq();
        r2 = com.google.android.gms.internal.firebase_database.zzgw.zza(r2);
        r1.add(r2);
    L_0x039d:
        r2 = new com.google.android.gms.internal.firebase_database.zzho;
        r2.<init>(r0, r1);
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_database.zzhl.zza(com.google.android.gms.internal.firebase_database.zzhk, com.google.android.gms.internal.firebase_database.zzfl, com.google.android.gms.internal.firebase_database.zzfg, com.google.android.gms.internal.firebase_database.zzja):com.google.android.gms.internal.firebase_database.zzho");
    }
}
