package com.google.android.gms.internal.firebase_database;

import com.google.firebase.database.collection.ImmutableSortedSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class zzit implements Iterable<zziz> {
    private static final ImmutableSortedSet<zziz> zzrw = new ImmutableSortedSet(Collections.emptyList(), null);
    private final zzis zzpd;
    private final zzja zzrx;
    private ImmutableSortedSet<zziz> zzry;

    private zzit(zzja zzja, zzis zzis) {
        this.zzpd = zzis;
        this.zzrx = zzja;
        this.zzry = null;
    }

    private zzit(zzja zzja, zzis zzis, ImmutableSortedSet<zziz> immutableSortedSet) {
        this.zzpd = zzis;
        this.zzrx = zzja;
        this.zzry = immutableSortedSet;
    }

    public static zzit zza(zzja zzja, zzis zzis) {
        return new zzit(zzja, zzis);
    }

    private final void zzfy() {
        if (this.zzry == null) {
            if (!this.zzpd.equals(zziu.zzgb())) {
                List arrayList = new ArrayList();
                Object obj = null;
                for (zziz zziz : this.zzrx) {
                    if (obj == null) {
                        if (!this.zzpd.zzi(zziz.zzd())) {
                            obj = null;
                            arrayList.add(new zziz(zziz.zzge(), zziz.zzd()));
                        }
                    }
                    obj = 1;
                    arrayList.add(new zziz(zziz.zzge(), zziz.zzd()));
                }
                if (obj != null) {
                    this.zzry = new ImmutableSortedSet(arrayList, this.zzpd);
                    return;
                }
            }
            this.zzry = zzrw;
        }
    }

    public static zzit zzj(zzja zzja) {
        return new zzit(zzja, zzjf.zzgf());
    }

    public final Iterator<zziz> iterator() {
        zzfy();
        return this.zzry == zzrw ? this.zzrx.iterator() : this.zzry.iterator();
    }

    public final Iterator<zziz> reverseIterator() {
        zzfy();
        return this.zzry == zzrw ? this.zzrx.reverseIterator() : this.zzry.reverseIterator();
    }

    public final zzid zza(zzid zzid, zzja zzja, zzis zzis) {
        if (!this.zzpd.equals(zziu.zzgb())) {
            if (!this.zzpd.equals(zzis)) {
                throw new IllegalArgumentException("Index not available in IndexedNode!");
            }
        }
        zzfy();
        if (this.zzry == zzrw) {
            return this.zzrx.zzl(zzid);
        }
        zziz zziz = (zziz) this.zzry.getPredecessorEntry(new zziz(zzid, zzja));
        return zziz != null ? zziz.zzge() : null;
    }

    public final zzja zzd() {
        return this.zzrx;
    }

    public final zziz zzfz() {
        if (!(this.zzrx instanceof zzif)) {
            return null;
        }
        zzfy();
        if (this.zzry != zzrw) {
            return (zziz) this.zzry.getMinEntry();
        }
        zzid zzfm = ((zzif) this.zzrx).zzfm();
        return new zziz(zzfm, this.zzrx.zzm(zzfm));
    }

    public final zzit zzg(zzid zzid, zzja zzja) {
        zzja zze = this.zzrx.zze(zzid, zzja);
        if (this.zzry == zzrw && !this.zzpd.zzi(zzja)) {
            return new zzit(zze, this.zzpd, zzrw);
        }
        if (this.zzry != null) {
            if (this.zzry != zzrw) {
                ImmutableSortedSet remove = this.zzry.remove(new zziz(zzid, this.zzrx.zzm(zzid)));
                if (!zzja.isEmpty()) {
                    remove = remove.insert(new zziz(zzid, zzja));
                }
                return new zzit(zze, this.zzpd, remove);
            }
        }
        return new zzit(zze, this.zzpd, null);
    }

    public final zziz zzga() {
        if (!(this.zzrx instanceof zzif)) {
            return null;
        }
        zzfy();
        if (this.zzry != zzrw) {
            return (zziz) this.zzry.getMaxEntry();
        }
        zzid zzfn = ((zzif) this.zzrx).zzfn();
        return new zziz(zzfn, this.zzrx.zzm(zzfn));
    }

    public final zzit zzk(zzja zzja) {
        return new zzit(this.zzrx.zzf(zzja), this.zzpd, this.zzry);
    }
}
