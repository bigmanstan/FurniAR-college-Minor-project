package com.google.android.gms.internal.firebase_database;

import java.util.Collections;
import java.util.List;

public final class zzik {
    private final List<zzch> zzbz;
    private final List<String> zzca;

    private zzik(List<zzch> list, List<String> list2) {
        if (list.size() == list2.size() - 1) {
            this.zzbz = list;
            this.zzca = list2;
            return;
        }
        throw new IllegalArgumentException("Number of posts need to be n-1 for n hashes in CompoundHash");
    }

    private static void zza(zzja zzja, zzim zzim) {
        if (zzja.zzfk()) {
            zzim.zzb((zziv) zzja);
        } else if (zzja.isEmpty()) {
            throw new IllegalArgumentException("Can't calculate hash on empty node!");
        } else if (zzja instanceof zzif) {
            ((zzif) zzja).zza(new zzil(zzim), true);
        } else {
            String valueOf = String.valueOf(zzja);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 33);
            stringBuilder.append("Expected children node, but got: ");
            stringBuilder.append(valueOf);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public static zzik zzh(zzja zzja) {
        zzio zzin = new zzin(zzja);
        if (zzja.isEmpty()) {
            return new zzik(Collections.emptyList(), Collections.singletonList(""));
        }
        zzim zzim = new zzim(zzin);
        zza(zzja, zzim);
        zzim.zzft();
        return new zzik(zzim.zzrp, zzim.zzrq);
    }

    public final List<zzch> zzo() {
        return Collections.unmodifiableList(this.zzbz);
    }

    public final List<String> zzp() {
        return Collections.unmodifiableList(this.zzca);
    }
}
