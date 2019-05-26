package com.google.vr.dynamite.client;

import java.util.Objects;

/* renamed from: com.google.vr.dynamite.client.f */
public final class C0428f {
    /* renamed from: a */
    private final int f152a;
    /* renamed from: b */
    private final int f153b;
    /* renamed from: c */
    private final int f154c;

    public C0428f(int i, int i2, int i3) {
        this.f152a = i;
        this.f153b = i2;
        this.f154c = i3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0428f)) {
            return false;
        }
        C0428f c0428f = (C0428f) obj;
        return this.f152a == c0428f.f152a && this.f153b == c0428f.f153b && this.f154c == c0428f.f154c;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.f152a), Integer.valueOf(this.f153b), Integer.valueOf(this.f154c)});
    }

    public final String toString() {
        return String.format("%d.%d.%d", new Object[]{Integer.valueOf(this.f152a), Integer.valueOf(this.f153b), Integer.valueOf(this.f154c)});
    }
}
