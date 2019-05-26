package com.google.android.gms.internal.firebase_database;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

final class zzjn implements zzjm {
    private static ThreadLocal<CharsetDecoder> zzsu = new zzjo();
    private static ThreadLocal<CharsetEncoder> zzsv = new zzjp();
    private StringBuilder zzsw = new StringBuilder();

    zzjn() {
    }

    private static String zzc(byte[] bArr) {
        try {
            return ((CharsetDecoder) zzsu.get()).decode(ByteBuffer.wrap(bArr)).toString();
        } catch (CharacterCodingException e) {
            return null;
        }
    }

    public final boolean zzb(byte[] bArr) {
        String zzc = zzc(bArr);
        if (zzc == null) {
            return false;
        }
        this.zzsw.append(zzc);
        return true;
    }

    public final zzjz zzgh() {
        return new zzjz(this.zzsw.toString());
    }
}
