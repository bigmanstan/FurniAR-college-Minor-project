package com.google.flatbuffers;

import java.nio.charset.Charset;

/* renamed from: com.google.flatbuffers.b */
final class C0423b extends ThreadLocal<Charset> {
    C0423b() {
    }

    protected final /* synthetic */ Object initialValue() {
        return Charset.forName("UTF-8");
    }
}
