package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.collection.LLRBNode.NodeVisitor;

final class zzih extends NodeVisitor<zzid, zzja> {
    private boolean zzrf = false;
    private final /* synthetic */ zzii zzrg;
    private final /* synthetic */ zzif zzrh;

    zzih(zzif zzif, zzii zzii) {
        this.zzrh = zzif;
        this.zzrg = zzii;
    }

    public final /* synthetic */ void visitEntry(Object obj, Object obj2) {
        zzid zzid = (zzid) obj;
        zzja zzja = (zzja) obj2;
        if (!this.zzrf && zzid.zzi(zzid.zzfe()) > 0) {
            this.zzrf = true;
            this.zzrg.zzb(zzid.zzfe(), this.zzrh.zzfl());
        }
        this.zzrg.zzb(zzid, zzja);
    }
}
