package com.google.android.gms.internal.firebase_database;

import java.util.ArrayList;
import java.util.List;

final class zzjl implements zzjm {
    private List<byte[]> zzss = new ArrayList();
    private int zzst = 0;

    zzjl() {
    }

    public final boolean zzb(byte[] bArr) {
        this.zzss.add(bArr);
        this.zzst += bArr.length;
        return true;
    }

    public final zzjz zzgh() {
        byte[] bArr = new byte[this.zzst];
        int i = 0;
        int i2 = i;
        while (i < this.zzss.size()) {
            byte[] bArr2 = (byte[]) this.zzss.get(i);
            System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
            i2 += bArr2.length;
            i++;
        }
        return new zzjz(bArr);
    }
}
