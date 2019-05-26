package com.google.flatbuffers;

import java.nio.ByteBuffer;
import java.util.Comparator;

/* renamed from: com.google.flatbuffers.c */
final class C0424c implements Comparator<Integer> {
    /* renamed from: a */
    private final /* synthetic */ ByteBuffer f143a;
    /* renamed from: b */
    private final /* synthetic */ Table f144b;

    C0424c(Table table, ByteBuffer byteBuffer) {
        this.f144b = table;
        this.f143a = byteBuffer;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return this.f144b.keysCompare((Integer) obj, (Integer) obj2, this.f143a);
    }
}
