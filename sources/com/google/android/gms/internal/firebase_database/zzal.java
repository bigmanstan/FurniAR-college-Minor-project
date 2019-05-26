package com.google.android.gms.internal.firebase_database;

import android.support.v4.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class zzal implements zzaa, zzaj {
    private static long zzcb = 0;
    private final ScheduledExecutorService zzbc;
    private final zzhz zzbs;
    private final zzah zzcc;
    private final zzad zzcn;
    private final zzak zzcw;
    private String zzcx;
    private HashSet<String> zzcy = new HashSet();
    private boolean zzcz = true;
    private long zzda;
    private zzz zzdb;
    private zzav zzdc = zzav.Disconnected;
    private long zzdd = 0;
    private long zzde = 0;
    private Map<Long, zzau> zzdf;
    private List<zzax> zzdg;
    private Map<Long, zzaz> zzdh;
    private Map<zzaw, zzay> zzdi;
    private String zzdj;
    private boolean zzdk;
    private final zzaf zzdl;
    private final zzbm zzdm;
    private String zzdn;
    private long zzdo = 0;
    private int zzdp = 0;
    private ScheduledFuture<?> zzdq = null;
    private long zzdr;
    private boolean zzds;

    public zzal(zzaf zzaf, zzah zzah, zzak zzak) {
        this.zzcw = zzak;
        this.zzdl = zzaf;
        this.zzbc = zzaf.zzs();
        this.zzcn = zzaf.zzr();
        this.zzcc = zzah;
        this.zzdi = new HashMap();
        this.zzdf = new HashMap();
        this.zzdh = new HashMap();
        this.zzdg = new ArrayList();
        this.zzdm = new zzbo(this.zzbc, zzaf.zzq(), "ConnectionRetryHelper").zzh(1000).zza(1.3d).zzi(30000).zzb(0.7d).zzaz();
        long j = zzcb;
        zzcb = 1 + j;
        StringBuilder stringBuilder = new StringBuilder(23);
        stringBuilder.append("pc_");
        stringBuilder.append(j);
        this.zzbs = new zzhz(zzaf.zzq(), "PersistentConnection", stringBuilder.toString());
        this.zzdn = null;
        zzag();
    }

    private final boolean isIdle() {
        return this.zzdi.isEmpty() && this.zzdf.isEmpty() && !this.zzds && this.zzdh.isEmpty();
    }

    private final zzay zza(zzaw zzaw) {
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String valueOf = String.valueOf(zzaw);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 15);
            stringBuilder.append("removing query ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        if (this.zzdi.containsKey(zzaw)) {
            zzay zzay = (zzay) this.zzdi.get(zzaw);
            this.zzdi.remove(zzaw);
            zzag();
            return zzay;
        }
        if (this.zzbs.zzfa()) {
            zzhz = this.zzbs;
            String valueOf2 = String.valueOf(zzaw);
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf2).length() + 64);
            stringBuilder2.append("Trying to remove listener for QuerySpec ");
            stringBuilder2.append(valueOf2);
            stringBuilder2.append(" but no listener exists.");
            zzhz.zza(stringBuilder2.toString(), null, new Object[0]);
        }
        return null;
    }

    private final void zza(zzay zzay) {
        Map hashMap = new HashMap();
        hashMap.put("p", zzag.zzb(zzay.zzaj().zzei));
        Long zzak = zzay.zzak();
        if (zzak != null) {
            hashMap.put("q", zzay.zzen.zzej);
            hashMap.put("t", zzak);
        }
        zzai zzal = zzay.zzal();
        hashMap.put("h", zzal.zzx());
        if (zzal.zzy()) {
            zzy zzz = zzal.zzz();
            List arrayList = new ArrayList();
            for (List zzb : zzz.zzo()) {
                arrayList.add(zzag.zzb(zzb));
            }
            Map hashMap2 = new HashMap();
            hashMap2.put("hs", zzz.zzp());
            hashMap2.put("ps", arrayList);
            hashMap.put("ch", hashMap2);
        }
        zza("q", hashMap, new zzar(this, zzay));
    }

    private final void zza(String str, List<String> list, Object obj, zzbb zzbb) {
        Map hashMap = new HashMap();
        hashMap.put("p", zzag.zzb((List) list));
        hashMap.put("d", obj);
        zza(str, hashMap, new zzao(this, zzbb));
    }

    private final void zza(String str, List<String> list, Object obj, String str2, zzbb zzbb) {
        Map hashMap = new HashMap();
        hashMap.put("p", zzag.zzb((List) list));
        hashMap.put("d", obj);
        if (str2 != null) {
            hashMap.put("h", str2);
        }
        long j = this.zzdd;
        this.zzdd = 1 + j;
        this.zzdh.put(Long.valueOf(j), new zzaz(str, hashMap, zzbb));
        if (zzac()) {
            zze(j);
        }
        this.zzdr = System.currentTimeMillis();
        zzag();
    }

    private final void zza(String str, Map<String, Object> map, zzau zzau) {
        zza(str, false, (Map) map, zzau);
    }

    private final void zza(String str, boolean z, Map<String, Object> map, zzau zzau) {
        long j = this.zzde;
        this.zzde = 1 + j;
        Map hashMap = new HashMap();
        hashMap.put("r", Long.valueOf(j));
        hashMap.put("a", str);
        hashMap.put("b", map);
        this.zzdb.zza(hashMap, z);
        this.zzdf.put(Long.valueOf(j), zzau);
    }

    private final void zza(List<String> list, zzaw zzaw) {
        if (list.contains("no_index")) {
            String valueOf = String.valueOf(zzaw.zzej.get("i"));
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 14);
            stringBuilder.append("\".indexOn\": \"");
            stringBuilder.append(valueOf);
            stringBuilder.append('\"');
            valueOf = stringBuilder.toString();
            zzhz zzhz = this.zzbs;
            String zzb = zzag.zzb(zzaw.zzei);
            StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(valueOf).length() + 175) + String.valueOf(zzb).length());
            stringBuilder2.append("Using an unspecified index. Your data will be downloaded and filtered on the client. Consider adding '");
            stringBuilder2.append(valueOf);
            stringBuilder2.append("' at ");
            stringBuilder2.append(zzb);
            stringBuilder2.append(" to your security and Firebase Database rules for better performance");
            zzhz.zzb(stringBuilder2.toString(), null);
        }
    }

    private final boolean zzab() {
        if (this.zzdc != zzav.Authenticating) {
            if (this.zzdc != zzav.Connected) {
                return false;
            }
        }
        return true;
    }

    private final boolean zzac() {
        return this.zzdc == zzav.Connected;
    }

    private final boolean zzad() {
        return this.zzcy.size() == 0;
    }

    private final void zzae() {
        if (zzad()) {
            zzag.zza(this.zzdc == zzav.Disconnected, "Not in disconnected state: %s", this.zzdc);
            boolean z = this.zzdk;
            this.zzbs.zza("Scheduling connection attempt", null, new Object[0]);
            this.zzdk = false;
            this.zzdm.zzb(new zzam(this, z));
        }
    }

    private final void zzaf() {
        int i = 0;
        zzag.zza(this.zzdc == zzav.Connected, "Should be connected if we're restoring state, but we are: %s", this.zzdc);
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("Restoring outstanding listens", null, new Object[0]);
        }
        for (zzay zzay : this.zzdi.values()) {
            if (this.zzbs.zzfa()) {
                zzhz zzhz = this.zzbs;
                String valueOf = String.valueOf(zzay.zzaj());
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 17);
                stringBuilder.append("Restoring listen ");
                stringBuilder.append(valueOf);
                zzhz.zza(stringBuilder.toString(), null, new Object[0]);
            }
            zza(zzay);
        }
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("Restoring writes.", null, new Object[0]);
        }
        List arrayList = new ArrayList(this.zzdh.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zze(((Long) obj).longValue());
        }
        for (zzax zzax : this.zzdg) {
            zza(zzax.getAction(), zzax.getPath(), zzax.getData(), zzax.zzai());
        }
        this.zzdg.clear();
    }

    private final void zzag() {
        if (isIdle()) {
            if (this.zzdq != null) {
                this.zzdq.cancel(false);
            }
            this.zzdq = this.zzbc.schedule(new zzat(this), 60000, TimeUnit.MILLISECONDS);
            return;
        }
        if (isInterrupted("connection_idle")) {
            zzag.zza(isIdle() ^ 1, "", new Object[0]);
            resume("connection_idle");
        }
    }

    private final boolean zzah() {
        return isIdle() && System.currentTimeMillis() > this.zzdr + 60000;
    }

    private final void zzc(boolean z) {
        String str;
        zzag.zza(zzab(), "Must be connected to send auth, but was: %s", this.zzdc);
        zzag.zza(this.zzdj != null, "Auth token must be set to authenticate!", new Object[0]);
        zzau zzap = new zzap(this, z);
        Map hashMap = new HashMap();
        zzkd zzu = zzkd.zzu(this.zzdj);
        if (zzu != null) {
            hashMap.put("cred", zzu.getToken());
            if (zzu.zzgu() != null) {
                hashMap.put("authvar", zzu.zzgu());
            }
            str = "gauth";
        } else {
            hashMap.put("cred", this.zzdj);
            str = "auth";
        }
        zza(str, true, hashMap, zzap);
    }

    private final void zze(long j) {
        zzaz zzaz = (zzaz) this.zzdh.get(Long.valueOf(j));
        zzbb zzai = zzaz.zzai();
        String action = zzaz.getAction();
        zzaz.zzan();
        zza(action, zzaz.zzam(), new zzaq(this, action, j, zzaz, zzai));
    }

    public final void initialize() {
        zzae();
    }

    public final void interrupt(String str) {
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String str2 = "Connection interrupted for: ";
            String valueOf = String.valueOf(str);
            zzhz.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), null, new Object[0]);
        }
        this.zzcy.add(str);
        if (this.zzdb != null) {
            this.zzdb.close();
            this.zzdb = null;
        } else {
            this.zzdm.cancel();
            this.zzdc = zzav.Disconnected;
        }
        this.zzdm.zzax();
    }

    public final boolean isInterrupted(String str) {
        return this.zzcy.contains(str);
    }

    public final void purgeOutstandingWrites() {
        for (zzaz zzaz : this.zzdh.values()) {
            if (zzaz.zzel != null) {
                zzaz.zzel.zzb("write_canceled", null);
            }
        }
        for (zzax zzax : this.zzdg) {
            if (zzax.zzel != null) {
                zzax.zzel.zzb("write_canceled", null);
            }
        }
        this.zzdh.clear();
        this.zzdg.clear();
        if (!zzab()) {
            this.zzds = false;
        }
        zzag();
    }

    public final void refreshAuthToken() {
        this.zzbs.zza("Auth token refresh requested", null, new Object[0]);
        interrupt("token_refresh");
        resume("token_refresh");
    }

    public final void resume(String str) {
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String str2 = "Connection no longer interrupted for: ";
            String valueOf = String.valueOf(str);
            zzhz.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), null, new Object[0]);
        }
        this.zzcy.remove(str);
        if (zzad() && this.zzdc == zzav.Disconnected) {
            zzae();
        }
    }

    public final void shutdown() {
        interrupt("shutdown");
    }

    public final void zza(long j, String str) {
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("onReady", null, new Object[0]);
        }
        this.zzda = System.currentTimeMillis();
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("handling timestamp", null, new Object[0]);
        }
        j -= System.currentTimeMillis();
        Map hashMap = new HashMap();
        hashMap.put("serverTimeOffset", Long.valueOf(j));
        this.zzcw.zzc(hashMap);
        if (this.zzcz) {
            Map hashMap2 = new HashMap();
            if (this.zzdl.zzt()) {
                hashMap2.put("persistence.android.enabled", Integer.valueOf(1));
            }
            String str2 = "sdk.android.";
            String valueOf = String.valueOf(this.zzdl.zzu().replace('.', '-'));
            hashMap2.put(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), Integer.valueOf(1));
            if (this.zzbs.zzfa()) {
                this.zzbs.zza("Sending first connection stats", null, new Object[0]);
            }
            if (!hashMap2.isEmpty()) {
                hashMap = new HashMap();
                hashMap.put("c", hashMap2);
                zza("s", hashMap, new zzas(this));
            } else if (this.zzbs.zzfa()) {
                this.zzbs.zza("Not sending stats because stats are empty", null, new Object[0]);
            }
        }
        if (this.zzbs.zzfa()) {
            this.zzbs.zza("calling restore state", null, new Object[0]);
        }
        zzag.zza(this.zzdc == zzav.Connecting, "Wanted to restore auth, but was in wrong state: %s", this.zzdc);
        if (this.zzdj == null) {
            if (this.zzbs.zzfa()) {
                this.zzbs.zza("Not restoring auth because token is null.", null, new Object[0]);
            }
            this.zzdc = zzav.Connected;
            zzaf();
        } else {
            if (this.zzbs.zzfa()) {
                this.zzbs.zza("Restoring auth.", null, new Object[0]);
            }
            this.zzdc = zzav.Authenticating;
            zzc(true);
        }
        this.zzcz = false;
        this.zzdn = str;
        this.zzcw.zzaa();
    }

    public final void zza(List<String> list, zzbb zzbb) {
        if (zzac()) {
            zza("oc", (List) list, null, zzbb);
        } else {
            this.zzdg.add(new zzax("oc", list, null, zzbb));
        }
        zzag();
    }

    public final void zza(List<String> list, Object obj, zzbb zzbb) {
        zza("p", (List) list, obj, null, zzbb);
    }

    public final void zza(List<String> list, Object obj, String str, zzbb zzbb) {
        zza("p", (List) list, obj, str, zzbb);
    }

    public final void zza(List<String> list, Map<String, Object> map) {
        zzaw zzaw = new zzaw(list, map);
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String valueOf = String.valueOf(zzaw);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 15);
            stringBuilder.append("unlistening on ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        zzay zza = zza(zzaw);
        if (zza != null && zzab()) {
            Map hashMap = new HashMap();
            hashMap.put("p", zzag.zzb(zza.zzen.zzei));
            Long zzak = zza.zzak();
            if (zzak != null) {
                hashMap.put("q", zza.zzaj().zzej);
                hashMap.put("t", zzak);
            }
            zza("n", hashMap, null);
        }
        zzag();
    }

    public final void zza(List<String> list, Map<String, Object> map, zzai zzai, Long l, zzbb zzbb) {
        zzaw zzaw = new zzaw(list, map);
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String valueOf = String.valueOf(zzaw);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 13);
            stringBuilder.append("Listening on ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        zzag.zza(this.zzdi.containsKey(zzaw) ^ 1, "listen() called twice for same QuerySpec.", new Object[0]);
        if (this.zzbs.zzfa()) {
            zzhz = this.zzbs;
            valueOf = String.valueOf(zzaw);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 21);
            stringBuilder.append("Adding listen query: ");
            stringBuilder.append(valueOf);
            zzhz.zza(stringBuilder.toString(), null, new Object[0]);
        }
        zzay zzay = new zzay(zzbb, zzaw, l, zzai);
        this.zzdi.put(zzaw, zzay);
        if (zzab()) {
            zza(zzay);
        }
        zzag();
    }

    public final void zza(List<String> list, Map<String, Object> map, zzbb zzbb) {
        zza("m", (List) list, (Object) map, null, zzbb);
    }

    public final void zzb(zzab zzab) {
        boolean z = false;
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String str = "Got on disconnect due to ";
            String valueOf = String.valueOf(zzab.name());
            zzhz.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
        }
        this.zzdc = zzav.Disconnected;
        this.zzdb = null;
        this.zzds = false;
        this.zzdf.clear();
        List arrayList = new ArrayList();
        Iterator it = this.zzdh.entrySet().iterator();
        while (it.hasNext()) {
            zzaz zzaz = (zzaz) ((Entry) it.next()).getValue();
            if (zzaz.zzam().containsKey("h") && zzaz.zzao()) {
                arrayList.add(zzaz);
                it.remove();
            }
        }
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzaz) obj).zzai().zzb("disconnected", null);
        }
        if (zzad()) {
            long currentTimeMillis = System.currentTimeMillis() - this.zzda;
            if (this.zzda > 0 && currentTimeMillis > 30000) {
                z = true;
            }
            if (zzab == zzab.SERVER_RESET || r2) {
                this.zzdm.zzax();
            }
            zzae();
        }
        this.zzda = 0;
        this.zzcw.onDisconnect();
    }

    public final void zzb(List<String> list, Object obj, zzbb zzbb) {
        this.zzds = true;
        if (zzac()) {
            zza("o", (List) list, obj, zzbb);
        } else {
            this.zzdg.add(new zzax("o", list, obj, zzbb));
        }
        zzag();
    }

    public final void zzb(List<String> list, Map<String, Object> map, zzbb zzbb) {
        this.zzds = true;
        if (zzac()) {
            zza("om", (List) list, (Object) map, zzbb);
        } else {
            this.zzdg.add(new zzax("om", list, map, zzbb));
        }
        zzag();
    }

    public final void zzb(Map<String, Object> map) {
        if (map.containsKey("r")) {
            zzau zzau = (zzau) this.zzdf.remove(Long.valueOf((long) ((Integer) map.get("r")).intValue()));
            if (zzau != null) {
                zzau.zzd((Map) map.get("b"));
            }
            return;
        }
        if (!map.containsKey("error")) {
            int i = 0;
            zzhz zzhz;
            String str;
            if (map.containsKey("a")) {
                zzhz zzhz2;
                Object obj;
                Long zzb;
                zzhz zzhz3;
                String str2;
                String str3 = (String) map.get("a");
                Map map2 = (Map) map.get("b");
                if (this.zzbs.zzfa()) {
                    zzhz2 = this.zzbs;
                    String valueOf = String.valueOf(map2);
                    StringBuilder stringBuilder = new StringBuilder((String.valueOf(str3).length() + 22) + String.valueOf(valueOf).length());
                    stringBuilder.append("handleServerMessage: ");
                    stringBuilder.append(str3);
                    stringBuilder.append(" ");
                    stringBuilder.append(valueOf);
                    zzhz2.zza(stringBuilder.toString(), null, new Object[0]);
                }
                if (!str3.equals("d")) {
                    if (!str3.equals("m")) {
                        if (str3.equals("rm")) {
                            str3 = (String) map2.get("p");
                            List zzg = zzag.zzg(str3);
                            obj = map2.get("d");
                            zzb = zzag.zzb(map2.get("t"));
                            List<Map> list = (List) obj;
                            List arrayList = new ArrayList();
                            for (Map map3 : list) {
                                String str4 = (String) map3.get("s");
                                String str5 = (String) map3.get("e");
                                arrayList.add(new zzba(str4 != null ? zzag.zzg(str4) : null, str5 != null ? zzag.zzg(str5) : null, map3.get("m")));
                            }
                            if (!arrayList.isEmpty()) {
                                this.zzcw.zza(zzg, arrayList, zzb);
                            } else if (this.zzbs.zzfa()) {
                                zzhz3 = this.zzbs;
                                str2 = "Ignoring empty range merge for path ";
                                str3 = String.valueOf(str3);
                                zzhz3.zza(str3.length() != 0 ? str2.concat(str3) : new String(str2), null, new Object[0]);
                                return;
                            }
                            return;
                        } else if (str3.equals("c")) {
                            List zzg2 = zzag.zzg((String) map2.get("p"));
                            if (this.zzbs.zzfa()) {
                                zzhz = this.zzbs;
                                str2 = String.valueOf(zzg2);
                                r5 = new StringBuilder(String.valueOf(str2).length() + 29);
                                r5.append("removing all listens at path ");
                                r5.append(str2);
                                zzhz.zza(r5.toString(), null, new Object[0]);
                            }
                            List arrayList2 = new ArrayList();
                            for (Entry entry : this.zzdi.entrySet()) {
                                zzaw zzaw = (zzaw) entry.getKey();
                                zzay zzay = (zzay) entry.getValue();
                                if (zzaw.zzei.equals(zzg2)) {
                                    arrayList2.add(zzay);
                                }
                            }
                            ArrayList arrayList3 = (ArrayList) arrayList2;
                            int size = arrayList3.size();
                            int i2 = 0;
                            while (i2 < size) {
                                obj = arrayList3.get(i2);
                                i2++;
                                this.zzdi.remove(((zzay) obj).zzaj());
                            }
                            zzag();
                            size = arrayList3.size();
                            while (i < size) {
                                Object obj2 = arrayList3.get(i);
                                i++;
                                ((zzay) obj2).zzem.zzb("permission_denied", null);
                            }
                            return;
                        } else if (str3.equals("ac")) {
                            str3 = (String) map2.get("s");
                            str = (String) map2.get("d");
                            zzhz2 = this.zzbs;
                            r5 = new StringBuilder((String.valueOf(str3).length() + 23) + String.valueOf(str).length());
                            r5.append("Auth token revoked: ");
                            r5.append(str3);
                            r5.append(" (");
                            r5.append(str);
                            r5.append(")");
                            zzhz2.zza(r5.toString(), null, new Object[0]);
                            this.zzdj = null;
                            this.zzdk = true;
                            this.zzcw.zzb(false);
                            this.zzdb.close();
                            return;
                        } else if (str3.equals("sd")) {
                            this.zzbs.info((String) map2.get(NotificationCompat.CATEGORY_MESSAGE));
                            return;
                        } else {
                            if (this.zzbs.zzfa()) {
                                zzhz3 = this.zzbs;
                                str2 = "Unrecognized action from server: ";
                                str3 = String.valueOf(str3);
                                zzhz3.zza(str3.length() != 0 ? str2.concat(str3) : new String(str2), null, new Object[0]);
                            }
                            return;
                        }
                    }
                }
                boolean equals = str3.equals("m");
                str2 = (String) map2.get("p");
                obj = map2.get("d");
                zzb = zzag.zzb(map2.get("t"));
                if (!equals || !(obj instanceof Map) || ((Map) obj).size() != 0) {
                    this.zzcw.zza(zzag.zzg(str2), obj, equals, zzb);
                } else if (this.zzbs.zzfa()) {
                    zzhz3 = this.zzbs;
                    str3 = "ignoring empty merge for path ";
                    str2 = String.valueOf(str2);
                    zzhz3.zza(str2.length() != 0 ? str3.concat(str2) : new String(str3), null, new Object[0]);
                }
            } else if (this.zzbs.zzfa()) {
                zzhz = this.zzbs;
                str = String.valueOf(map);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(str).length() + 26);
                stringBuilder2.append("Ignoring unknown message: ");
                stringBuilder2.append(str);
                zzhz.zza(stringBuilder2.toString(), null, new Object[0]);
            }
        }
    }

    public final void zzd(String str) {
        this.zzcx = str;
    }

    public final void zze(String str) {
        if (this.zzbs.zzfa()) {
            zzhz zzhz = this.zzbs;
            String str2 = "Firebase Database connection was forcefully killed by the server. Will not attempt reconnect. Reason: ";
            str = String.valueOf(str);
            zzhz.zza(str.length() != 0 ? str2.concat(str) : new String(str2), null, new Object[0]);
        }
        interrupt("server_kill");
    }

    public final void zzh(String str) {
        this.zzbs.zza("Auth token refreshed.", null, new Object[0]);
        this.zzdj = str;
        if (zzab()) {
            if (str != null) {
                zzc(false);
                return;
            }
            zzag.zza(zzab(), "Must be connected to send unauth.", new Object[0]);
            zzag.zza(this.zzdj == null, "Auth token must not be set.", new Object[0]);
            zza("unauth", Collections.emptyMap(), null);
        }
    }

    public final void zzi(String str) {
        zzag.zza(this.zzdc == zzav.GettingToken, "Trying to open network connection while in the wrong state: %s", this.zzdc);
        if (str == null) {
            this.zzcw.zzb(false);
        }
        this.zzdj = str;
        this.zzdc = zzav.Connecting;
        this.zzdb = new zzz(this.zzdl, this.zzcc, this.zzcx, this, this.zzdn);
        this.zzdb.open();
    }
}
