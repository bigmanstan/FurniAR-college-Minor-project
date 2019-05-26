package com.google.vr.dynamite.client;

import java.util.Objects;

/* renamed from: com.google.vr.dynamite.client.e */
final class C0427e {
    /* renamed from: a */
    private final String f150a;
    /* renamed from: b */
    private final String f151b;

    public C0427e(String str, String str2) {
        this.f150a = str;
        this.f151b = str2;
    }

    /* renamed from: a */
    public final String m101a() {
        return this.f150a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0427e) {
            C0427e c0427e = (C0427e) obj;
            return Objects.equals(this.f150a, c0427e.f150a) && Objects.equals(this.f151b, c0427e.f151b);
        }
    }

    public final int hashCode() {
        return (Objects.hashCode(this.f150a) * 37) + Objects.hashCode(this.f151b);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("[packageName=");
        stringBuilder.append(this.f150a);
        stringBuilder.append(",libraryName=");
        stringBuilder.append(this.f151b);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
