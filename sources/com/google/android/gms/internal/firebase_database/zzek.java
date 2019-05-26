package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import java.util.List;

final class zzek extends NodeVisitor<zzid, zzgj<zzed>> {
    private final /* synthetic */ List zzip;
    private final /* synthetic */ zzee zzlb;
    private final /* synthetic */ zzja zzlg;
    private final /* synthetic */ zzfg zzlh;
    private final /* synthetic */ zzfl zzli;

    zzek(zzee zzee, zzja zzja, zzfg zzfg, zzfl zzfl, List list) {
        this.zzlb = zzee;
        this.zzlg = zzja;
        this.zzlh = zzfg;
        this.zzli = zzfl;
        this.zzip = list;
    }

    public final /* synthetic */ void visitEntry(Object obj, Object obj2) {
        zzid zzid = (zzid) obj;
        zzgj zzgj = (zzgj) obj2;
        zzja zzm = this.zzlg != null ? this.zzlg.zzm(zzid) : null;
        zzfg zzb = this.zzlh.zzb(zzid);
        zzfl zzc = this.zzli.zzc(zzid);
        if (zzc != null) {
            this.zzip.addAll(this.zzlb.zzb(zzc, zzgj, zzm, zzb));
        }
    }
}
