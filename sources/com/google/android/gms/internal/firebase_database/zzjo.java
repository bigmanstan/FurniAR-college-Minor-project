package com.google.android.gms.internal.firebase_database;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

final class zzjo extends ThreadLocal<CharsetDecoder> {
    zzjo() {
    }

    protected final /* synthetic */ Object initialValue() {
        CharsetDecoder newDecoder = Charset.forName("UTF8").newDecoder();
        newDecoder.onMalformedInput(CodingErrorAction.REPORT);
        newDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return newDecoder;
    }
}
