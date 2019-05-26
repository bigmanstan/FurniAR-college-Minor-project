package com.google.android.gms.internal.firebase_database;

final class zzbh implements zzbg, zzjw {
    final /* synthetic */ zzbc zzff;
    private zzjr zzfg;

    private zzbh(zzbc zzbc, zzjr zzjr) {
        this.zzff = zzbc;
        this.zzfg = zzjr;
        this.zzfg.zza((zzjw) this);
    }

    public final void close() {
        this.zzfg.close();
    }

    public final void connect() {
        try {
            this.zzfg.connect();
        } catch (Throwable e) {
            if (this.zzff.zzbs.zzfa()) {
                this.zzff.zzbs.zza("Error connecting", e, new Object[0]);
            }
            this.zzfg.close();
            try {
                this.zzfg.zzgm();
            } catch (Throwable e2) {
                this.zzff.zzbs.zza("Interrupted while shutting down websocket threads", e2);
            }
        }
    }

    public final void onClose() {
        this.zzff.zzbc.execute(new zzbk(this));
    }

    public final void zza(zzjx zzjx) {
        this.zzff.zzbc.execute(new zzbl(this, zzjx));
    }

    public final void zza(zzjz zzjz) {
        String text = zzjz.getText();
        if (this.zzff.zzbs.zzfa()) {
            zzhz zzb = this.zzff.zzbs;
            String str = "ws message: ";
            String valueOf = String.valueOf(text);
            zzb.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), null, new Object[0]);
        }
        this.zzff.zzbc.execute(new zzbj(this, text));
    }

    public final void zzav() {
        this.zzff.zzbc.execute(new zzbi(this));
    }

    public final void zzm(String str) {
        this.zzfg.zzm(str);
    }
}
