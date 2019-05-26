package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.ValueEventListener;

final class zzdl implements Comparable<zzdl> {
    private int retryCount;
    private zzch zzap;
    private Handler zzjj;
    private ValueEventListener zzjk;
    private int zzjl;
    private long zzjm;
    private boolean zzjn;
    private DatabaseError zzjo;
    private long zzjp;
    private zzja zzjq;
    private zzja zzjr;
    private zzja zzjs;

    private zzdl(zzch zzch, Handler handler, ValueEventListener valueEventListener, int i, boolean z, long j) {
        this.zzap = zzch;
        this.zzjj = handler;
        this.zzjk = valueEventListener;
        this.zzjl = i;
        this.retryCount = 0;
        this.zzjn = z;
        this.zzjm = j;
        this.zzjo = null;
        this.zzjq = null;
        this.zzjr = null;
        this.zzjs = null;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        zzdl zzdl = (zzdl) obj;
        return this.zzjm < zzdl.zzjm ? -1 : this.zzjm == zzdl.zzjm ? 0 : 1;
    }
}
