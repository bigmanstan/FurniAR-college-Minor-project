package com.google.flatbuffers;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/* renamed from: com.google.flatbuffers.a */
final class C0422a extends ThreadLocal<CharsetDecoder> {
    C0422a() {
    }

    protected final /* synthetic */ Object initialValue() {
        return Charset.forName("UTF-8").newDecoder();
    }
}
